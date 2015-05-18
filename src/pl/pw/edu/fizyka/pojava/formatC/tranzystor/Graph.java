package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *Displays calculated values.  
 *@author Aleksander Szpakiewicz-Szatan
 */
public class Graph extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	XYSeries dataSeries;
	XYSeriesCollection dataCollection;
	int voltageId;
	int currentId;

	public Graph(Color frameColor, int voltageId_, int currentId_) 
	{
		setBorder(new LineBorder(frameColor));
		voltageId=voltageId_;
		currentId=currentId_;
			
		dataSeries =new XYSeries("");
		dataCollection = new XYSeriesCollection();
		dataCollection.addSeries(dataSeries);
		
		JFreeChart chart = ChartFactory.createXYLineChart
				(
				"Natężenie prądu",
				"Napięcie",
				"Prąd",
				dataCollection, 
				PlotOrientation.VERTICAL, 
				false, 
				true,
				false);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setMinimumSize(new java.awt.Dimension(320, 240));
		chartPanel.setPreferredSize(new java.awt.Dimension(640, 480));
		chartPanel.setMaximumSize(new java.awt.Dimension(1280, 1024));
		setLayout(new GridLayout());
		this.add(chartPanel);
	}
	void addData(double x, double y)
	{
		dataSeries.add(x,y);
	}
}
