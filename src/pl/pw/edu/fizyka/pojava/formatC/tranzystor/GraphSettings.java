package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
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
		
		//Łukasz, tak to wymyśliłeś, że wymyślenie do czego dokładnie dodać Listener to katorga. Twórz jakąkolwiek dokumentację. Szatan
		ItemListener OXListener=new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e) 
			{
				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				parameter.label.setText(oxin[1-cb.getSelectedIndex()]);			
				graph.chart.getXYPlot().getDomainAxis().setLabel(oxin[cb.getSelectedIndex()]+" /V");
				graph.chart.setTitle("Natężenie prądu "+oyin[oy.unit.getSelectedIndex()]+" w zależności od napięcia "+oxin[ox.unit.getSelectedIndex()]+" dla "+oxin[1-ox.unit.getSelectedIndex()]+"="+parameter.value.getText()+"V");
			}			
		};	
		ox.unit.addItemListener(OXListener);
		
		ItemListener OYListener=new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e) 
			{
				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				graph.chart.getXYPlot().getRangeAxis().setLabel(oyin[cb.getSelectedIndex()]+" /A");
				graph.chart.setTitle("Natężenie prądu "+oyin[oy.unit.getSelectedIndex()]+" w zależności od napięcia "+oxin[ox.unit.getSelectedIndex()]+" dla "+oxin[1-ox.unit.getSelectedIndex()]+"="+parameter.value.getText()+"V");
			}		
		};	
		oy.unit.addItemListener(OYListener);
		ActionListener parameterListener=new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				graph.chart.setTitle("Natężenie prądu "+oyin[oy.unit.getSelectedIndex()]+" w zależności od napięcia "+oxin[ox.unit.getSelectedIndex()]+" dla "+oxin[1-ox.unit.getSelectedIndex()]+"="+parameter.value.getText()+"V");
			}
		};
		parameter.value.addActionListener(parameterListener);
		graph.chart.setTitle("Natężenie prądu "+oyin[oy.unit.getSelectedIndex()]+" w zależności od napięcia "+oxin[ox.unit.getSelectedIndex()]+" dla "+oxin[1-ox.unit.getSelectedIndex()]+"="+parameter.value.getText()+"V");
	}
	public void refreshGraph()
	{
		graph.chart.setTitle("Natężenie prądu "+oyin[oy.unit.getSelectedIndex()]+" w zależności od napięcia "+oxin[ox.unit.getSelectedIndex()]+" dla "+oxin[1-ox.unit.getSelectedIndex()]+"="+parameter.value.getText()+"V");
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
