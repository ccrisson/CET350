//import java.out.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;

class Main extends Frame implements WindowListener{
	Main(){
		// Hold the origin location for the frame
		int locX = 200;
		int locY = 200;
		int height = 500;
		int width = 500;

		// Create frame elements
		Button OKButton = new Button(" OK ");
		OKButton.setSize(50,25);
		OKButton.setLocation(locX + 50, locY + 50);

		// Set default frame properties
		this.setLayout(null);
		this.setVisible(true);
		this.setLocation(locX, locY);
		this.setSize(height, width);
		this.setTitle("Test Window");
		this.setBackground(Color.green); 
		addWindowListener(this);

		// Add elements
		this.add(OKButton);


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

	public static void main(String[] args){
		new Main();

		/* Without extending frame
		Frame f =  new Frame();
		
		*/	
	}

}