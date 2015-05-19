package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;

/**
 *Main class of program. Here GUI and data containers are initialised.<br> 
 *@author Aleksander Szpakiewicz-Szatan
 */
public class Simulation 
{
	boolean working;
	static InterFace frame;
	
	/**
	 * Use {@link #Data(} as constructor.<br>
	 * Sets working flag to false (as in start user needs to input data first)
	 */
	public Simulation()
	{
		working=false;
	}
	
	/**
	 *Main method of program. Initialises GUI and data containers, in future will run threads for calculations.<br> 
	 *
	 **/
	public static void main(String[] args) 
	{
		Simulation simulation=new Simulation();
		frame = new InterFace(Color.blue,simulation);
		frame.setVisible(true);
	}
}
