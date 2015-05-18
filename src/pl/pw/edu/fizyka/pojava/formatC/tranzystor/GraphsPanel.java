package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class GraphsPanel extends JPanel 
{
	private static final long serialVersionUID = 3429864083441722430L;
	int number;
	Graph graph1;
	Graph graph2;
	
	public GraphsPanel(Color frameColor) 
	{
		GridLayout layot=new GridLayout();
		setLayout(layot);
		layot.setVgap(10);
		layot.setHgap(10);
		
		graph1=new Graph(frameColor,0,0);
		add(graph1);
		graph2=new Graph(frameColor,0,0);
		add(graph2);
		
		//TEST
		graph1.addData(1,1);
		graph1.addData(2,2);
		graph1.addData(3,1);
		graph1.addData(4,5);
		graph1.addData(6,7);
		
		for(double ii=0.0;ii<10;ii+=0.1)
			graph2.addData(ii,ii*ii-7*ii+5);
				
		
	}
}
