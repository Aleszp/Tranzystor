package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ValuePanel extends JPanel 
{
	private static final long serialVersionUID = 1397518244713660284L;
	
	JTextField value;
	JComboBox<String> unit;
	
	public ValuePanel(String name, int textFieldSize) 
	{
		value = new JTextField(textFieldSize);
		add(new JLabel(name));
		add(value);
	}

	
	public ValuePanel(String name, int textFieldSize ,String[] unitType) 
	{
				
		value = new JTextField(textFieldSize);
		unit = new JComboBox<String>(unitType);
		add(new JLabel(name));
		add(value);
		add(unit);
	}
	
	public ValuePanel(String name,String[] unitType) 
	{
		unit = new JComboBox<String>(unitType);
		add(new JLabel(name));
		add(unit);
	}
	/*
	public int getUnit()
	{
		return unit.getSelectedIndex();						//trzeba doda� throw i catch na wypadek nie istniena wrato�ci
	}
	
	public double getValue()
	{
		return Double.valueOf(value.getText());				//trzeba doda� throw i catch na wypadek nie istniena wrato�ci
	}
	
	public void setUnit(int number)
	{
		unit.setSelectedIndex(number);
	}
	
	public void setValue(double number)
	{
		value.setText(String.valueOf(number));
	}
	*/
}
