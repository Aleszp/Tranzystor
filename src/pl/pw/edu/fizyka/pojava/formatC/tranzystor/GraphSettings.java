package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GraphSettings extends JPanel 
{
	private static final long serialVersionUID = -376298015343955111L;
	
	ValuePanel ox;
	ValuePanel oy;
	JPanel graphParameterPanel =new JPanel();
	JLabel graphParameterLabel;

	public GraphSettings(Color frameColor,String[] oxin,String[] oyin,String name) 
	{
		setBorder(new LineBorder(frameColor));
		add(new JLabel(name,JLabel.CENTER));
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		ox=new ValuePanel("OX",oxin);
		add(ox);
		oy=new ValuePanel("OY",oyin);
		add(oy);
		
		graphParameterLabel =new JLabel("aa");
		graphParameterPanel.add(graphParameterLabel);
		
		add(graphParameterPanel);
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
