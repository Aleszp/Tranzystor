package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
/*import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;*/

import javax.swing.BoxLayout;
//import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


/**
 * Contains settings for single chart.
 *  */
public class GraphSettings extends JPanel 
{
	private static final long serialVersionUID = -376298015343955111L;
	
	ValuePanel ox;
	ValuePanel oy;
	ValuePanel parameter;
	String[] oxin;
	String[] oyin;
	Graph graph;
	
	/**
	 * Use {@link #GraphSettings(Color, String[], String[], String[], Graph)} as constructor.
	 * 
	 * @param frameColor color of frame
	 * @param oxin_ names of voltages
	 * @param oyin_ names of currents
	 * @param voltagesUnits units in which voltage could be shown
	 * @param graph_ reference to Graph which's settings it contains
	 * */
	public GraphSettings(Color frameColor,String[] oxin_,String[] oyin_,String[] voltagesUnits,Graph graph_) 
	{
		setBorder(new LineBorder(frameColor));
		oxin=oxin_;
		oyin=oyin_;
		graph=graph_;
		
		add(new JLabel("",JLabel.CENTER));
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		ox=new ValuePanel("OX",oxin);
		add(ox);
		oy=new ValuePanel("OY",oyin);
		add(oy);
		//Zmieniłem to idiotyczne pole na parametr, Szatan
		parameter=new ValuePanel(oxin[1],3,voltagesUnits);
		add(parameter);
		parameter.value.setText("0");
		//Listenery przeniesione do zmiany zakładek. Szatan
	}
	/**
	 * Use it to refresh text displayed on graph after changing it's settings.
	 */
	public void refreshGraph()
	{
		graph.chart.setTitle(Language.words[8]+oyin[oy.unit.getSelectedIndex()]+Language.words[9]+oxin[ox.unit.getSelectedIndex()]+Language.words[10]+oxin[1-ox.unit.getSelectedIndex()]+"="+parameter.value.getText()+"V");
	}
	
	/*
	public int getOX()
	{
		return ox.getUnit();
	}
	

	public int getOY()
	{
		return oy.getUnit();
	}
	
	public void setParameter(String parametr)
	{
		graphParameterLabel.setText(parametr);
	}
	*/
}
