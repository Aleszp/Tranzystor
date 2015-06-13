package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
/*import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;*/

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
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
		ox=new ValuePanel(Localization.getString("OX"),oxin);
		add(ox);
		oy=new ValuePanel(Localization.getString("OY"),oyin);
		add(oy);
		//Zmieniłem to idiotyczne pole na parametr, Szatan
		parameter=new ValuePanel(oxin[1],5,voltagesUnits);
		add(parameter);
		parameter.value.setText("0");
		//Jedno napięcie to argument, drugie to parametr
		ox.unit.addItemListener(new ItemListener()
		{
            @Override
            public void itemStateChanged(ItemEvent e)
            {
            	@SuppressWarnings("unchecked")
				JComboBox<Integer> cb = (JComboBox<Integer>)e.getSource();
            	if(cb.getSelectedIndex()==0)
            		parameter.label.setText(Localization.getString("Ube"));
            	else
            		parameter.label.setText(Localization.getString("Uce"));
            }
        });
		
	}
	/**
	 * Use it to refresh text displayed on graph after changing it's settings.
	 * @author Aleksander Szpakiewicz-Szatan
	 */
	public void refreshGraph()
	{
		graph.chart.setTitle(Localization.getString("chartTitle1")+" "+oyin[oy.unit.getSelectedIndex()]+" "+Localization.getString("chartTitle2")+" "+oxin[ox.unit.getSelectedIndex()]+" "+Localization.getString("chartTitle3")+" "+oxin[1-ox.unit.getSelectedIndex()]+"="+parameter.value.getText()+"V");
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
