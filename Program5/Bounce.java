/*Bounce.java
  Program 4
  CET-350 Technical Computing Using Java
  Group 9
  Chris Crisson CRI4537@calu.edu
  Matthew Bedillion BED9714@calu.edu
  Mark Blatnik BLA9072@calu.edu
  Josh Williams WIL6155@calu.edu 
*/
 import java.lang.*;
 import java.awt.*;
 import java.awt.event.*;
 import java.awt.Dimension;
 import java.awt.Graphics;
class Bounce extends Frame implements WindowListener, Runnable, ActionListener, AdjustmentListener, ComponentListener, MouseListener, MouseMotionListener{
	Insets I;
	private Thread thread;
	private Panel sheet = new Panel();
	private Panel control = new Panel();

	//sheet.Layout(new BorderLayout());

	Button runButton;
	Button shapeButton;
	Button tailButton;
	Button clearButton;
	Button quitButton;
	Scrollbar speedScroll;
	Scrollbar sizeScroll;
	Label speedLabel;
	Label sizeLabel;
	
	int screenHeight;
	int screenWidth;
	int buttonWidth = 60;
	int buttonHeight = 20;
	int scrollbarWidth = 100;
	int buttonGap = 10;
	int sideSpacer;
	int controlPanelHeight = buttonHeight + (3 * buttonGap);
	int windowWidth = (buttonWidth * 5) + (buttonGap * 8) + 
										(scrollbarWidth * 2);
	int windowHeight = 600;
	int minWindowWidth = (buttonGap * 8) + (scrollbarWidth * 2) +
							(buttonWidth * 5);
	int minWindowHeight = buttonHeight * 4 + 320;
	int scrollMin = 1;
	int scrollMax = 100;
	int scrollVisible = 10;
	int scrollUnitIncrement = 5;
	int scrollBlockIncrement = 25;
	int scrollValue = 25;
	int shapeSize = 25;
	int DELAY = 5;
	long delay = 5;
	boolean pause = false;
	Screen s = new Screen(shapeSize);

	GridBagLayout gbl = new GridBagLayout();
  
  	GridBagConstraints gbc = new GridBagConstraints();
	// gbc.anchor = GridBagConstraints.WEST;
	//gbc.weightx = 1.0;
	// gbc.weighty = 1.0;
	// gbc.gridwidth = 10;
	// gbc.gridheight = 10;
	// gbc.gridx = 0;
	// gbc.gridy = 0;

	public Bounce(){
		this.setLayout(new BorderLayout());
		this.sheet.setLayout(new BorderLayout());
		this.control.setLayout(gbl);
		this.setVisible(true);
		makeSheet();
		initComponents();
		sizeScreen();
		start();	
	}

	public void makeSheet(){
		I = getInsets();
		screenWidth = windowWidth - I.left - I.right;
		screenHeight = windowHeight - I.top - I.bottom - controlPanelHeight;
		sideSpacer = (screenWidth - (2 * scrollbarWidth) - 
						(5 * buttonWidth) - (6 * buttonGap)) /2;
	}

	public void initComponents(){
		runButton =  new Button("Pause");
		shapeButton = new Button("Circle");
		tailButton = new Button("Tail");
		clearButton = new Button("Clear");
		quitButton = new Button("Quit");
		speedLabel = new Label("Speed");
		sizeLabel = new Label("Size");

		// Scrollbar setup
		speedScroll = new Scrollbar(Scrollbar.HORIZONTAL);
		speedScroll.setMinimum(scrollMin);
		speedScroll.setMaximum(scrollMax);
		speedScroll.setVisibleAmount(scrollVisible);
		speedScroll.setUnitIncrement(scrollUnitIncrement);
		speedScroll.setBlockIncrement(scrollBlockIncrement);
		sizeScroll = new Scrollbar(Scrollbar.HORIZONTAL);
		sizeScroll.setMinimum(scrollMin);
		sizeScroll.setMaximum(scrollMax);
		sizeScroll.setVisibleAmount(scrollVisible);
		sizeScroll.setUnitIncrement(scrollUnitIncrement);
		sizeScroll.setBlockIncrement(scrollBlockIncrement);

		// Listeners
		this.addWindowListener(this);
		this.addComponentListener(this);
		speedScroll.addAdjustmentListener(this);
		sizeScroll.addAdjustmentListener(this);
		runButton.addActionListener(this);
		shapeButton.addActionListener(this);
		tailButton.addActionListener(this);
		clearButton.addActionListener(this);
		quitButton.addActionListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		// Add components
		sheet.add(s);
		this.add(s, BorderLayout.CENTER);
		this.add(control, BorderLayout.SOUTH);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbl.setConstraints(speedScroll, gbc);
		control.add(speedScroll);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbl.setConstraints(speedLabel, gbc);
		control.add(speedLabel);

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbl.setConstraints(runButton, gbc);
		control.add(runButton);

		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbl.setConstraints(shapeButton, gbc);
		control.add(shapeButton);

		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbl.setConstraints(tailButton, gbc);
		control.add(tailButton);

		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbl.setConstraints(clearButton, gbc);
		control.add(clearButton);

		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbl.setConstraints(quitButton, gbc);
		control.add(quitButton);

		gbc.gridx = 7;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbl.setConstraints(sizeScroll, gbc);
		control.add(sizeScroll);

		gbc.gridx = 7;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbl.setConstraints(sizeLabel, gbc);
		control.add(sizeLabel);
	}

	public void sizeScreen(){
		this.setMinimumSize(new Dimension(minWindowWidth,minWindowHeight));

		windowWidth = this.getWidth();
		windowHeight = this.getHeight();
		this.setSize(windowWidth, windowHeight);

		s.setLocation(I.left, I.top);
		s.setSize(windowWidth - I.left - I.right,
				  windowHeight - (buttonHeight * 4) - I.top);
		s.setScreenWidth(s.getWidth());
		s.setScreenHeight(s.getHeight());

		int screenWidth = s.getWidth();
		int screenHeight = s.getHeight();// - (2 * buttonHeight)
		int layoutX = (windowWidth / 2) - (buttonWidth / 2);
		int layoutY = I.top + windowHeight + buttonHeight;
		int buttonGap = (screenWidth - ((5 * buttonWidth) + (2 * scrollbarWidth))) / 8;

		/*speedScroll.setBounds((I.left + buttonGap), (I.top + screenHeight), scrollbarWidth, buttonHeight);
		speedLabel.setBounds((I.left + buttonGap + (buttonWidth/2)), I.top + screenHeight + buttonHeight, scrollbarWidth, buttonHeight);
		runButton.setBounds((I.left + (2 * buttonGap) + scrollbarWidth), (I.top + screenHeight), buttonWidth,buttonHeight);
		shapeButton.setBounds((I.left + (3 * buttonGap) + scrollbarWidth + buttonWidth), (I.top + screenHeight), buttonWidth,buttonHeight);
		tailButton.setBounds((I.left + (4 * buttonGap) + scrollbarWidth + (2 * buttonWidth)), (I.top + screenHeight), buttonWidth,buttonHeight);
		clearButton.setBounds((I.left + (5 * buttonGap) + scrollbarWidth + (3 * buttonWidth)), (I.top + screenHeight), buttonWidth,buttonHeight);
		quitButton.setBounds((I.left + (6 * buttonGap) + scrollbarWidth + (4 * buttonWidth)), (I.top + screenHeight), buttonWidth,buttonHeight);
		sizeScroll.setBounds((I.left + (7 * buttonGap) + scrollbarWidth + (5 * buttonWidth)), (I.top + screenHeight), scrollbarWidth,buttonHeight);
		sizeLabel.setBounds((I.left + (7 * buttonGap) + (buttonWidth/2) + scrollbarWidth + (5 * buttonWidth)), (I.top + screenHeight + buttonHeight), buttonWidth,buttonHeight);*/
	}

	public void start(){
		if (thread == null){
			thread = new Thread(this);
			thread.start();
			s.repaint();
		}
	}
	public void stop() {
		this.removeWindowListener(this);
		this.removeComponentListener(this);
		speedScroll.removeAdjustmentListener(this);
		sizeScroll.removeAdjustmentListener(this);
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		dispose();
		System.exit(0);
	}

	public void run(){
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		while(true){
			try{
				thread.sleep(delay);
				while(!pause){
					s.step();
					s.repaint();
					try{
						thread.sleep(delay);
					} catch(InterruptedException e){}
				}
			} catch(InterruptedException e){}	
		}
	}

	

	public static void main(String args[]){
		Bounce gui = new Bounce();
	}

	public void windowOpened(WindowEvent e){

	}

	public void windowClosing(WindowEvent e){
		stop();
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
        sizeScreen();
    }

    public void componentHidden(ComponentEvent e){

    }

    public void componentShown(ComponentEvent e){

    }

    public void componentMoved(ComponentEvent e){

    }

    public void actionPerformed(ActionEvent e){
    	Object obj = e.getSource();
    	
    	if(obj == runButton){
    		if(runButton.getLabel() == "Run"){
    			runButton.setLabel("Pause");
    			pause = false;
    		}else{
    			runButton.setLabel("Run");
    			pause = true;
    		}
    	}
    	if(obj == shapeButton){
    		if(shapeButton.getLabel() == "Circle"){
    			shapeButton.setLabel("Rectanlge");
    			s.setRectangle(false);
    		} else {
    			shapeButton.setLabel("Circle");
    			s.setRectangle(true);
    		}
    	}
    	if(obj == tailButton){
    		if(tailButton.getLabel() == "Tail"){
    			s.setTail(true);
    			tailButton.setLabel("No Tail");
    		} else {
    			s.setTail(false);
    			tailButton.setLabel("Tail");
    		}
    	}
    	if(obj == clearButton){
    		s.setClear(true);
    		s.repaint();
    	}
    	if(obj == quitButton){
    		stop();
    	}
    }

    public void adjustmentValueChanged(AdjustmentEvent e){
    	Object obj = e.getSource();
    	if(obj == speedScroll){
    		delay = (long)(DELAY * (double)(scrollMax - scrollVisible + 1) / 100);
    		thread.interrupt();
    	}
    	if(obj == sizeScroll){
    		shapeSize = e.getValue();
    		s.setShapeSize(shapeSize);
    	}
    }

    public void mouseExited(MouseEvent e){

    }

    public void mouseEntered(MouseEvent e){

    }

    public void mouseReleased(MouseEvent e){
	g.drawRect(startx.x, startx.y, e.getX(), e.getY();
		      startx = null;
		      endx = null;
		      repaint();
    }

    public void mousePressed(MouseEvent e){
    	startx = new Point(e.getX(), e.getY());
	endx = startx;
	repaint();
    }
    
    public void mouseClicked(MouseEvent e){
    	
    }

    public void mouseMoved(MouseEvent e){
    	
    }

    public void mouseDragged(MouseEvent e){
    	endx = new Point(e.getX(), e.getY());
	    repaint();
    }
	
	Point startx, endx;
	
	class Screen extends Canvas{
		private int screenWidth;
		private int screenHeight;
		private int shapeSize;
		private boolean rectangle = true;
		private boolean tail = false;
		private boolean clear = false;
		private int xPosition = 100, yPosition = 100;
		private int xSlope  = 1, ySlope = 1;
		private int xLast = xSlope, yLast = ySlope;
		Rectangle shape = new Rectangle(10,10,10,10);
		public Screen(int shapeSize){
			this.shapeSize = shapeSize;
		}
		public void setScreenWidth(int screenWidth){ 
            this.screenWidth = screenWidth;
        }

        public void setScreenHeight(int screenHeight){
            this.screenHeight = screenHeight;
        }
        public void setRectangle(boolean rectangle) {
            this.rectangle = rectangle;
        }
        public void setTail(boolean tail)
        {
            this.tail = tail;
        }
        public void setClear(boolean clear){
        	this.clear = clear;
        }
        public void setShapeSize(int shapeSize){
        	this.shapeSize = shapeSize;
        }
        public int getScreenHeight() {
            return screenHeight;
        }

        public int getScreenWidth () {
            return screenWidth;
        }
		public void paint(Graphics g){
			g.setColor(Color.red);
			g.drawRect(0,0,screenWidth-1,screenHeight-1);
			update(g);
		}
		public void update(Graphics g){	
			if(clear){
				super.paint(g);
				clear = false;
				g.setColor(Color.red);
				g.drawRect(0,0,screenWidth-1,screenHeight-1);
			}
			if(rectangle){
				if(!tail){
					g.setColor(Color.white);
					g.drawRect(xLast,yLast,shapeSize,shapeSize);
					g.fillRect(xLast,yLast,shapeSize,shapeSize);
				} 
				g.setColor(Color.black);
				g.drawRect(xPosition,yPosition,shapeSize,shapeSize);
				g.setColor(Color.gray);
				g.fillRect(xPosition,yPosition,shapeSize,shapeSize);
			} else{
				if(!tail){
					g.setColor(Color.white);
					g.drawOval(xLast,yLast,shapeSize,shapeSize);
					g.fillOval(xLast,yLast,shapeSize,shapeSize);
				} 
				g.setColor(Color.black);
				g.drawOval(xPosition,yPosition,shapeSize,shapeSize);
				g.setColor(Color.gray);
				g.fillOval(xPosition,yPosition,shapeSize,shapeSize);
			}
			//super.paint(g);  
			
		}
		public void step(){
			if(xPosition + xSlope + shapeSize > screenWidth - 2){
				xSlope = -1;
				setShapeSize(screenWidth - xPosition - 2);
			}
			if(xPosition < 2){
				xSlope = 1;
			}
			if(yPosition + ySlope + shapeSize > screenHeight - 2){
				ySlope = -1;
				setShapeSize(screenHeight - yPosition - 2);
			}
			if(yPosition < 2){
				ySlope = 1;
			}
			xLast = xPosition;
			yLast = yPosition;
			xPosition = xPosition + xSlope;
			yPosition = yPosition + ySlope;
			repaint();
		}
	}
}
