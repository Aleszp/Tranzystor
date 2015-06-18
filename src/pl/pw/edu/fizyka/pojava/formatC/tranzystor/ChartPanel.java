package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * Panel in which charts are contained (itself being part of InterFace frame). 
 * @author Łukasz Krzysztofik (documentation: Aleksander Szpakiewicz-Szatan)
 */

public class ChartPanel extends JPanel 
{
	private static final long serialVersionUID = 3429864083441722430L;
	int number;
	Chart graph1;
	Chart graph2;
	
	/**
	 * Use ChartPanel(Color) as constructor
	 * @param frameColor - color that charts use in their constructors
	 */
	public ChartPanel(Color frameColor) 
	{
		GridLayout layout=new GridLayout(); //poprawiłem literówkę w nazwie zmiennej layot -> layout, Szatan
		setLayout(layout);
		layout.setVgap(10);
		layout.setHgap(10);
		
		graph1=new Chart(frameColor);
		add(graph1);
		graph2=new Chart(frameColor);
		add(graph2);
	}
}
