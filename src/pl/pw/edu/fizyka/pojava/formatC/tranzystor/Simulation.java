package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;

public class Simulation 
{
	boolean working;
	static InterFace frame;
	
	public Simulation()
	{
		working=false;
	}
	
	public static void main(String[] args) 
	{
		Simulation simulation=new Simulation();
		frame = new InterFace(Color.blue,simulation);
		frame.setVisible(true);
	}
}
