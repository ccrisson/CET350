/*Bounce.java
  Program 4
  CET-350 Technical Computing Using Java
  Group 9
  Chris Crisson CRI4537@calu.edu
  Matthew Bedillion BED9714@calu.edu
  Mark Blatnik BLA9072@calu.edu
  Josh Williams WIL6155@calu.edu
  implements WindowListener, ActionListener, AdjustmentListener, 
*/
 import java.awt.*;
 import java.awt.event.*;

class Bounce extends Frame implements WindowListener, Runnable{
	private Thread thread;
	Button runButton =  new Button("Run");
	Button shapeButton = new Button("Circle");
	Button tailButton = new Button("Tail");
	Button clearButton = new Button("Clear");
	Button quitButton = new Button("Quit");
	Scrollbar speedScroll = new Scrollbar(Scrollbar.HORIZONTAL);
	Scrollbar sizeScroll = new Scrollbar(Scrollbar.HORIZONTAL);
	Label speedLabel = new Label("Speed");
	Label sizeLabel = new Label("Size");

	public void start(){
		if (thread == null){
			thread = new Thread(this);
			thread.start();
			//obj.repaint();
		}
	}

	public void run(){
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
	}

	public Bounce(int width,int height){
		if(height < 100){
			height = 100;
		}
		if(width < 775){
			width = 775;
		}
		this.setLayout(null);
		this.setSize(width, height);
		this.setVisible(true);
		addWindowListener(this);
		// Speed scrollbar
		this.add(speedScroll);
		speedScroll.setSize(100, 20);
		speedScroll.setLocation(30, height-75);
		speedScroll.setVisible(true);
		// Speed label
		this.add(speedLabel);
		speedLabel.setSize(100, 20);
		speedLabel.setLocation(60, height-45);
		speedLabel.setVisible(true);
		// Run button
		this.add(runButton);
		runButton.setSize(75,20);
		runButton.setLocation(150, height-75);
		runButton.setVisible(true);
		// Shape button
		this.add(shapeButton);
		shapeButton.setSize(75,20);
		shapeButton.setLocation(250, height-75);
		shapeButton.setVisible(true);
		// Tail button
		this.add(tailButton);
		tailButton.setSize(75,20);
		tailButton.setLocation(350, height-75);
		tailButton.setVisible(true);
		// Clear button
		this.add(clearButton);
		clearButton.setSize(75,20);
		clearButton.setLocation(450, height-75);
		clearButton.setVisible(true);
		// Quit button
		this.add(quitButton);
		quitButton.setSize(75,20);
		quitButton.setLocation(550, height-75);
		quitButton.setVisible(true);
		// Size scrollbar
		this.add(sizeScroll);
		sizeScroll.setSize(100, 20);
		sizeScroll.setLocation(650, height-75);
		sizeScroll.setVisible(true);
		// Size label
		this.add(sizeLabel);
		sizeLabel.setSize(100, 20);
		sizeLabel.setLocation(680, height-45);
		sizeLabel.setVisible(true);
	}

	public static void main(String args[]){
		Bounce gui = new Bounce(650,650);
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
}