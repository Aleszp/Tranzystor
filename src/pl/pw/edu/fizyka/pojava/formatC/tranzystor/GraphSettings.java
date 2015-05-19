package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GraphSettings extends JPanel 
{
	private static final long serialVersionUID = -376298015343955111L;
	
	ValuePanel ox;
	ValuePanel oy;
	ValuePanel parameter;

	public GraphSettings(Color frameColor,String[] oxin,String[] oyin,String name,String[] voltagesUnits) 
	{
		setBorder(new LineBorder(frameColor));
		add(new JLabel(name,JLabel.CENTER));
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		ox=new ValuePanel("OX",oxin);
		add(ox);
		oy=new ValuePanel("OY",oyin);
		add(oy);
		//Zmieniłem to idiotyczne pole na parametr, Szatan
		parameter=new ValuePanel(oxin[1],3,voltagesUnits);
		add(parameter);
		
		//Łukasz, tak to wymyśliłeś, że wymyślenie do czego dodać Listener to katorga. Twórz jakąkolwiek dokumentację. Szatan
		ItemListener OXListener=new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e) 
			{
				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				if(cb.getSelectedIndex()==0)
					parameter.label.setText(oxin[1]);
				else
					parameter.label.setText(oxin[0]);
			}			
		};	
		ox.unit.addItemListener(OXListener);
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
