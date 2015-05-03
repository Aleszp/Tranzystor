package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class SettingsPanel extends JPanel 
{
	private static final long serialVersionUID = -8005482503180775986L;
	
	ValuePanel[] panels;
	int panelNumber;
	
	public SettingsPanel(Color frameColor,int panelNumberIn ,ValuePanel[] panelsIn,String name) 
	{
		panelNumber=panelNumberIn;
		setBorder(new LineBorder(frameColor));
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		add(new JLabel(name));
		panels=panelsIn;
		for(int i=0;i<panelNumber;i++)
			add(panels[i]);
	}
	/*
	public int getUnit(int number)
	{
		return panels[number].getUnit();
	}
	
	public double getValue(int number)
	{
		return panels[number].getValue();
	}
	
	public void setUnit(int number,int unitNumber){
		panels[number].setUnit(unitNumber);
	}
	
	public void setValue(int number,double newValue){
		panels[number].setValue(newValue);
	}
	*/
}
