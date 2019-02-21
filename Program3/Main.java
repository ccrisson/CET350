//import java.out.*;
import java.io.*;
import java.io.File;
import java.util.*;
import java.lang.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;

class Main extends Frame implements ActionListener, WindowListener{
	String path = System.getProperty("user.dir");
	String sourcePath;
	String targetPath;
	File f = new File(path);
	ArrayList<String> fileNames = new ArrayList<String>(Arrays.asList(f.list()));;
	boolean targetMode = false;
	List fileView = new List();
	Label sourceLabel;
	Button targetButton;
	Label targetLabel;
	Label fileLabel;
	TextField fileTextField;
	Button OKButton;
	Label messageLabel;

	public Main(){
		// Hold the origin location for the frame
		// Set Frame size
		//int locX = 200;
		//int locY = 200;
		//super();
		int height = 400;
		int width = 500;

		
        int rHeight[] = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        int cWidth[] = {1,1,1,1,1,1,1,1,1,1};
		double cWeights[] = {1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0};
        double rWeights[] = {1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0};
		
		GridBagLayout gbl = new GridBagLayout();
        gbl.rowHeights = rHeight;
        gbl.columnWidths = cWidth;
        gbl.columnWeights = cWeights;
        gbl.rowWeights = rWeights;
        
        
        this.setLayout(gbl);

		// Set default frame properties
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;

		this.setTitle(path);
		addWindowListener(this);

		// File Viewer
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 10;
		gbc.gridheight = 10;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbl.setConstraints(fileView, gbc);
		try{
			if (f.getParentFile().exists()) {
				fileView.add("..");
			} 
		}catch (NullPointerException err){
			// Folder is root	
		}
		
		for(int i = 0; i < fileNames.size(); i++){
			fileView.add(fileNames.get(i));
		}
		
		fileView.addActionListener(this);
		this.add(fileView);
	
		// Source Label  
		sourceLabel = new Label("Source: ");
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 9;
		gbc.gridheight = 1;
		gbc.gridx = 0;
		gbc.gridy = 11;
		gbc.fill = GridBagConstraints.BOTH;
		gbl.setConstraints(sourceLabel, gbc);;
		this.add(sourceLabel);
		
		// Target Button
		targetButton = new Button("Target: ");
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.gridx = 0;
		gbc.gridy = 12;
		gbc.fill = GridBagConstraints.BOTH;
		gbl.setConstraints(targetButton, gbc);
		targetButton.setEnabled(false);
		targetButton.addActionListener(this);
		this.add(targetButton);
		
		// Target Label
		targetLabel = new Label("");
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 9;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 12;
		
		gbl.setConstraints(targetLabel, gbc);	
		this.add(targetLabel);

		// File Label
		fileLabel = new Label("Filename: ");
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.gridx = 0;
		gbc.gridy = 13;
		gbc.fill = GridBagConstraints.BOTH;
		gbl.setConstraints(fileLabel, gbc);
		this.add(fileLabel);

		// File text field
		fileTextField = new TextField("");
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 9;
		gbc.gridheight = 1;
		gbc.gridx = 1;
		gbc.gridy = 13;
		gbc.fill = GridBagConstraints.BOTH;
		gbl.setConstraints(fileTextField, gbc);
		this.add(fileTextField);

		OKButton = new Button(" OK ");
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.gridx = 0;
		gbc.gridy = 14;
		gbc.fill = GridBagConstraints.BOTH;
		gbl.setConstraints(OKButton, gbc);
		OKButton.addActionListener(this);
		this.add(OKButton);


		messageLabel = new Label("");
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 10;
		gbc.gridheight = 1;
		gbc.gridx = 0;
		gbc.gridy = 15;
		gbc.fill = GridBagConstraints.BOTH;
		gbl.setConstraints(messageLabel, gbc);
		this.add(messageLabel);

		this.setSize(width, height);
		this.setResizable(true);
		this.setVisible(true);
		//this.pack();
		
	}

	public void windowOpened(WindowEvent e){

	}

	public void windowClosing(WindowEvent e){
		this.removeWindowListener(this);
		System.exit(0);
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

	public void actionPerformed(ActionEvent e){
		Object source = e.getSource();
		File temp;
		// Clicked in file view
		if(source == this.fileView){
			// Not in target mode
			if(!targetMode){
				// Wants to move up a directory
				if(fileView.getSelectedItem() == ".."){
					f = new File(f.getParent());
					fileView.removeAll();	
					fileNames.removeAll(fileNames);
					fileNames = new ArrayList<String>(Arrays.asList(f.list()));
					try{
						if (f.getParentFile().exists()) {
							fileView.add("..");
						} 
					}catch (NullPointerException err){
						// Folder is root	
					}
					for(int i = 0; i < fileNames.size(); i++){
						fileView.add(fileNames.get(i));
					}
					
					path = f.getAbsolutePath();
					
				} else { // Clicked something else in file view
					temp = new File(path, fileView.getSelectedItem());

					if(temp.isDirectory()){
						f = temp;
						fileView.removeAll();	
						fileNames.removeAll(fileNames);
						fileNames = new ArrayList<String>(Arrays.asList(f.list()));
						try{
							if (f.getParentFile().exists()) {
								fileView.add("..");
							} 
						}catch (NullPointerException err){
							// Folder is root	
						}
						for(int i = 0; i < fileNames.size(); i++){
							fileView.add(fileNames.get(i));
						}
						path = f.getAbsolutePath();
									
					} 
					else if(temp.isFile()){
						f = temp;
						sourcePath = f.getAbsolutePath();
						sourceLabel.setText("Source: " + sourcePath);
		
						
						
					}
					
				} 
				this.setTitle(path);
				targetButton.setEnabled(true);
				
			} else {
				temp = new File(fileView.getSelectedItem());
				System.out.println(temp.getAbsolutePath());
				targetMode = true;
				if(temp.isFile()){
					
					targetPath = temp.getAbsolutePath();
					fileTextField.setText(targetPath);
				}
				targetPath = temp.getAbsolutePath();
				targetLabel.setText(targetPath);
				
				}
		} 
		else if(source == targetButton){
			temp = new File(fileView.getSelectedItem());
			System.out.println(temp.getAbsolutePath());
			targetMode = true;
			if(temp.isFile()){
				
					targetPath = temp.getAbsolutePath();
					fileTextField.setText(targetPath);
			}
			targetPath = temp.getAbsolutePath();
			targetLabel.setText(targetPath);
			
		}
		else if(source == OKButton){

			if(targetLabel.getText() != ""){
				if(targetPath != null){
					copy();
				}
			}
		}
		else if(source == fileTextField){
			if(fileTextField.getText() != null){
				targetPath = fileTextField.getText();
				copy();
			}
			
		}
	}

	public static void main(String[] args){
		Main m = new Main();

		/* Without extending frame
		Frame f =  new Frame();
		
		*/	
	}
	 public void copy(){
	 	try{
		 	File in = new File(sourcePath);
		 	File out = new File(targetPath);

	 		BufferedReader inFile = new BufferedReader(new FileReader(in));
	 		PrintWriter outFile = new PrintWriter(new FileWriter(out));
	 		String inBuffer = inFile.readLine();  //Read first line
            while(inBuffer != null){  
                outFile.write(inBuffer); 
                inBuffer = inFile.readLine();
            }
            inFile.close();
            outFile.close();
	 	
		} catch(IOException err){
			System.out.println(err);
		}
	}

}