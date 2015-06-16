package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class ChartPanel extends JPanel 
{
	private static final long serialVersionUID = 3429864083441722430L;
	int number;
	Chart graph1;
	Chart graph2;
	
	public ChartPanel(Color frameColor) 
	{
		GridLayout layot=new GridLayout();
		setLayout(layot);
		layot.setVgap(10);
		layot.setHgap(10);
		
		graph1=new Chart(frameColor);
		add(graph1);
		graph2=new Chart(frameColor);
		add(graph2);
	}
}
