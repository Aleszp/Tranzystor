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
 *Displays calculated values.  <br>
 *
 *@author Aleksander Szpakiewicz-Szatan
 *
 *
 */
public class Graph extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	XYSeries dataSeries;
	XYSeriesCollection dataCollection;
	int voltageId;
	int currentId;

	/**
	 * Use {@link #Graph(Color, in, int)} as constructor.
	 * 
	 * @param frameColor_ sets color of frame
	 * @param voltageId_ selects which voltage is on OX
	 * @param currentId_ selects which current is on OY
	 */
	public Graph(int voltageId_, int currentId_, Color frameColor_) 
	{
		setBorder(new LineBorder(frameColor_));
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
	/**
	 * Use {@link #addData(double, double)} to add a point to chart
	 * 
	 * @param x argument of added point (voltage)
	 * @param y value of added point (current)
	 * 
	 * @see #Graph(Color, in, int)
	 */
	void addData(double x, double y)
	{
		dataSeries.add(x,y);
	}
}
