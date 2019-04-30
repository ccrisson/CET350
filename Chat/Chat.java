/*CET 350
Group 9
Program 7
  Chris Crisson CRI4537@calu.edu
  Matthew Bedillion BED9714@calu.edu
  Mark Blatnik BLA9072@calu.edu
  Josh Williams WIL6155@calu.edu
*/

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
//implements Runnable, WindowListener, ComponentListener, ActionListener, ItemListener

public class Chat extends Frame implements WindowListener, ActionListener, ItemListener, Runnable, ComponentListener{

	MenuBar colorsMenuBar;
	Menu textColorsMenu;
	MenuItem colors;
	Checkbox Black, Red, Blue;
	CheckboxGroup colorChecks;
	TextArea chatTextArea;
	Label messageLabel;
	TextField messageTextField;
	Button sendButton;
	Label hostLabel;
	TextField hostTextField;
	Button changeHostButton;
	Button serverButton;
	Label portLabel;
	TextField portTextField;
	Button connectButton;
	Button changePortButton;
	Button disconnectButton;
	TextArea statusTextArea;

	Frame mainWindow = new Frame();
	Frame colorWindow = new Frame();

	Socket client;
	Socket server;
	ServerSocket listenSocket;

	int defaultPort = 44004;
	int port = defaultPort;
	String defaultHost = "127.0.0.1";
	String host = defaultHost;

	int timeout = 1000;
	int width = 650;
	int height = 650;

	InputStreamReader is;
	BufferedReader br;
	PrintWriter pr;

	int state = 0;

	public Chat(){
		// Initialize Components
		colorsMenuBar = new MenuBar();
		textColorsMenu = new Menu("Text Color");
		colors = new MenuItem("Colors");
		chatTextArea = new TextArea("Testing");
		chatTextArea.setEditable(false);
		chatTextArea.setBackground(Color.WHITE);
		chatTextArea.setColumns(width);
		messageLabel = new Label("Message");
		messageTextField = new TextField(30);
		sendButton = new Button("Send");
		hostLabel = new Label("Host");
		hostTextField = new TextField(defaultHost, 15);
		changeHostButton = new Button("Change Host");
		serverButton = new Button("Start Server");
		portLabel = new Label("Port");
		portTextField = new TextField(Integer.toString(defaultPort),15);
		changeHostButton = new Button("Change Host");
		connectButton = new Button("Connect");
		changePortButton = new Button("Change Port");
		disconnectButton = new Button("Disconnect");
		statusTextArea = new TextArea();

		int chatRows = 20;
		int colWidths[] = {1,1,1,1,1,1,1,1}; //8
		int rowHeights[] = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}; //28
		double colWeights[] = {1,1,1,1,1,1,1,1}; //8
		double rowWeights[] = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}; //28		
		
		//Set Layout and GridBag Constraints
		GridBagLayout gbl = new GridBagLayout(); //Create layout manager
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		mainWindow.setLayout(gbl); //set layout manager to the frame
		
		gbl.rowHeights = rowHeights;
		gbl.columnWidths = colWidths;
		gbl.rowWeights = rowWeights;
		gbl.columnWeights = colWeights;	
		
		// chatTextArea
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 8;
		gbc.gridheight = chatRows;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbl.setConstraints(chatTextArea, gbc);
		
		// messageLabel
		gbc.gridx = 0;
		gbc.gridy = chatRows;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbl.setConstraints(messageLabel, gbc);
		
		// messageTextField
		gbc.gridx = 1;
		gbc.gridy = chatRows;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbl.setConstraints(messageTextField, gbc);
		
		// sendButton
		gbc.gridx = 7;
		gbc.gridy = chatRows;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbl.setConstraints(sendButton, gbc);
		sendButton.setEnabled(false);
			
		// hostLabel
		gbc.gridx = 0;
		gbc.gridy = chatRows+1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbl.setConstraints(hostLabel, gbc);
		
		// hostTextField
		gbc.gridx = 1;
		gbc.gridy = chatRows+1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbl.setConstraints(hostTextField, gbc);

		// changeHostButton
		gbc.gridx = 6;
		gbc.gridy = chatRows+1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbl.setConstraints(changeHostButton, gbc);

		// serverButton
		gbc.gridx = 7;
		gbc.gridy = chatRows+1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbl.setConstraints(serverButton, gbc);
		
		// portLabel
		gbc.gridx = 0;
		gbc.gridy = chatRows+2;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbl.setConstraints(portLabel, gbc);
		
		// portTextField 
		gbc.gridx = 1;
		gbc.gridy = chatRows+2;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbl.setConstraints(portTextField, gbc);
		
		// changePortButton 
		gbc.gridx = 6;
		gbc.gridy = chatRows+2;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbl.setConstraints(changePortButton, gbc);

		// connectButton 
		gbc.gridx = 7;
		gbc.gridy = chatRows+2;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbl.setConstraints(connectButton, gbc);
		
		// disconnectButton
		gbc.gridx = 7;
		gbc.gridy = chatRows+3;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbl.setConstraints(disconnectButton, gbc);
		disconnectButton.setEnabled(false);
		
		// statusTextArea 
		gbc.gridx = 0;
		gbc.gridy = chatRows+4;
		gbc.gridheight = 1;
		gbc.gridwidth = 8;
		gbl.setConstraints(statusTextArea, gbc);
		statusTextArea.setEditable(false);
		statusTextArea.setRows(3);
		
		
		//Add components
		mainWindow.setMenuBar(colorsMenuBar);
		mainWindow.add(chatTextArea);
		mainWindow.add(messageLabel);
		mainWindow.add(messageTextField);
		mainWindow.add(sendButton);
		mainWindow.add(hostLabel);
		mainWindow.add(hostTextField);
		mainWindow.add(changeHostButton);
		mainWindow.add(serverButton);
		mainWindow.add(portLabel);
		mainWindow.add(portTextField);
		mainWindow.add(connectButton);
		mainWindow.add(changePortButton);
		mainWindow.add(disconnectButton);
		mainWindow.add(statusTextArea);
		textColorsMenu.add(colors);
		colorsMenuBar.add(textColorsMenu);
		mainWindow.pack();

		// Action Listeners
		colors.addActionListener(this);
		sendButton.addActionListener(this);
		changeHostButton.addActionListener(this);
		serverButton.addActionListener(this);
		changePortButton.addActionListener(this);
		connectButton.addActionListener(this);
		disconnectButton.addActionListener(this);

		// Set mainWindow Attributes
		mainWindow.setBounds(0,0,width,height);
		mainWindow.setVisible(true);
		mainWindow.addWindowListener(this);
		mainWindow.setTitle("Chat");
	}
	public void colorChooser(){
		colorWindow.setLayout(new GridLayout(0,1));
		colorWindow.setBounds(width,0,100,200);
		colorWindow.setVisible(true);
		colorWindow.addWindowListener(this);
		CheckboxGroup colorChecks = new CheckboxGroup();
		Black = new Checkbox("Black",colorChecks, true);
		Red = new Checkbox("Red", colorChecks, false);
		Blue = new Checkbox("Blue", colorChecks, false);
		Black.addItemListener(this);
		Red.addItemListener(this);
		Blue.addItemListener(this);
		colorWindow.add(Black);
		colorWindow.add(Red);
		colorWindow.add(Blue);
	}
	public static void main(String args[]) throws Exception{
		Chat app = new Chat();
	}
	public void stop() {
		mainWindow.removeWindowListener(this);
		mainWindow.removeComponentListener(this);
		colorWindow.removeWindowListener(this);
		//Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		dispose();
		System.exit(0);
	}
	public void itemStateChanged(ItemEvent e){
		Object obj = e.getSource();
		if(obj == Black){
			chatTextArea.setForeground(Color.BLACK);
		} 
		else if(obj == Red){
			chatTextArea.setForeground(Color.RED);
		}
		else if(obj == Blue){
			chatTextArea.setForeground(Color.BLUE);
		}
	}
	
	public void windowOpened(WindowEvent e){

	}

	public void windowClosing(WindowEvent e){
		Object obj = e.getSource();
		if (obj == mainWindow){
			stop();
		}
		else if (obj == colorWindow){
			colorWindow.dispose();
		}
		
	}

	public void windowClosed(WindowEvent e){

	}

	public void windowIconified(WindowEvent e){

	}

	public void windowDeiconified(WindowEvent e){

	}

	public void windowActivated(WindowEvent e){

	}

	public void windowDeactivated(WindowEvent e){

	}

	 public void componentResized(ComponentEvent e){
      
    }

    public void componentHidden(ComponentEvent e){

    }

    public void componentShown(ComponentEvent e){

    }

    public void componentMoved(ComponentEvent e){

    }

    public void actionPerformed(ActionEvent e){
    	Object obj = e.getSource();
    	if(obj == colors){
    		colorChooser();
    	}
    	else if(obj == changeHostButton){
    		host = hostTextField.getText();
    	}
    	else if(obj == changePortButton){
    		port = Integer.parseInt(portTextField.getText());
    	}
    	else if(obj == serverButton){
    		serverButton.setEnabled(false);
    		connectButton.setEnabled(false);
    		disconnectButton.setEnabled(false);
    		sendButton.setEnabled(false);
    		try{
    			try{
    				if(listenSocket != null){
    					listenSocket.close();
    					listenSocket = null;
    				}
    				listenSocket = new ServerSocket();
    			
    			}catch(IOException ioException){}
    			// send message here
    			listenSocket.setSoTimeout(10 * timeout);
    			try{
    				if(client != null){
    					client.close();
    					client = null;
    				}
    				client = new Socket();
    				client = listenSocket.accept();
    			}catch(IOException ioException){}
    			try{
    				client.getInetAddress();
    				is = new InputStreamReader(client.getInputStream());
    				br = new BufferedReader(is);
    				pr = new PrintWriter(client.getOutputStream(), true);
    				state = 1;
    			}catch(IOException ioException){}
    		}catch(SocketException sException){
    			// enable buttons
    			serverButton.setEnabled(true);
    		}
    	}
    	else if(obj == connectButton){
    		if(host == null){
    			host = defaultHost;
    		}
			//enable/disable buttons
			connectButton.setEnabled(false);
			serverButton.setEnabled(false);
			try{
				if(server != null){
					server.close();
					server = null;
				}
			server = new Socket();
			server.setSoTimeout(1000);
			}catch(IOException ioException){}
			
			try{
				server.connect(new InetSocketAddress(host,port));
				try{
					is = new InputStreamReader(client.getInputStream());
				br = new BufferedReader(is);
				pr = new PrintWriter(client.getOutputStream(), true);
				state = 2; 
				}catch(IOException ioException){}
			}catch(IOException ioException){}
    	}
    }
}
