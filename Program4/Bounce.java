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
		this.setLayout(null);
		this.setSize(width, height);
		this.setVisible(true);
		addWindowListener(this);
		this.add(runButton);
		runButton.setSize(100,20);
		runButton.setLocation(50, 900);
		runButton.setVisible(true);

	}

	public static void main(String args[]){
		Bounce gui = new Bounce(1000,1000);
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