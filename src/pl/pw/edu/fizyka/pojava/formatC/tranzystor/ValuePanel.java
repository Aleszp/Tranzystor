package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*Łukasz, bardziej tego udziwnić nie mogłeś? 
Nie mogłeś zrobić trzech klas do różnych zastosowań? Przecież i tak narobiłeś ich chorą ilość. 
Szatan*/
/**
 * 
 * @author Łukasz Krzysztofik (człowieku, że też muszę za Ciebie pisać dokumentację. Szatan)
 *
 */
public class ValuePanel extends JPanel 
{
	private static final long serialVersionUID = 1397518244713660284L;
	
	JTextField value;
	JComboBox<String> unit;
	JLabel label;
	
	public ValuePanel(String name, int textFieldSize) 
	{
		value = new JTextField(textFieldSize);
		add(new JLabel(name));
		add(value);
	}
	
	public ValuePanel(String name, int textFieldSize ,String[] unitType) 
	{
		value = new JTextField(textFieldSize);
		//unit = new JComboBox<String>(unitType); //Różne jednostki raczej nie są potrzebne. Szatan
		label=new JLabel(name);
		add(label);
		add(value);
		//add(unit);  // j. w.
	}
	/**
	 * @author Aleksander Szpakiewicz-Szatan
	 * Use {@link #ValuePanel(String, int, double)} as constructor.
	 * @param name - name of field
	 * @param textFieldSize - lenght of field (in characters)
	 * @param defaultValue - default value
	 */
	public ValuePanel(String name, int textFieldSize ,double defaultValue) 
	{
		value = new JTextField(textFieldSize);
		value.setText(String.valueOf(defaultValue));
		label=new JLabel(name);
		add(label);
		add(value);
	}
	/**
	 * @author Aleksander Szpakiewicz-Szatan
	 * Use {@link #ValuePanel(String, int, int)} as constructor.
	 * @param name - name of field
	 * @param textFieldSize - lenght of field (in characters)
	 * @param defaultValue - default value
	 */
	public ValuePanel(String name, int textFieldSize ,int defaultValue) 
	{
		value = new JTextField(textFieldSize);		
		value.setText(String.valueOf(defaultValue));
		label=new JLabel(name);
		add(label);
		add(value);
	}
	
	/**
	 * @author Aleksander Szpakiewicz-Szatan
	 * Use {@link #ValuePanel(String, int, double)} as constructor.
	 * @param name - name of field
	 * @param textFieldSize - lenght of field (in characters)
	 * @param defaultValue - default value
	 * @param unit - unit of value (i.e. volts, miliampers)
	 */
	public ValuePanel(String name, int textFieldSize ,double defaultValue, String unit) 
	{
		value = new JTextField(textFieldSize);
		value.setText(String.valueOf(defaultValue));
		label=new JLabel(name);
		add(label);
		add(value);
		JLabel unitLabel=new JLabel(unit);
		add(unitLabel);
	}
	/**
	 * @author Aleksander Szpakiewicz-Szatan
	 * Use {@link #ValuePanel(String, int, int)} as constructor.
	 * @param name - name of field
	 * @param textFieldSize - lenght of field (in characters)
	 * @param defaultValue - default value
	 * @param unit - unit of value (i.e. volts, miliampers)
	 */
	public ValuePanel(String name, int textFieldSize ,int defaultValue, String unit) 
	{
		value = new JTextField(textFieldSize);		
		value.setText(String.valueOf(defaultValue));
		label=new JLabel(name);
		add(label);
		add(value);
		JLabel unitLabel=new JLabel(unit);
		add(unitLabel);
	}
	
	public ValuePanel(String name,String[] unitType) 
	{
		unit = new JComboBox<String>(unitType);
		add(new JLabel(name));
		add(unit);
	}
	
	/**
	 * @author Aleksander Szpakiewicz-Szatan
	 * Use {@link #ValuePanel(String, int, int)} as constructor.
	 * @param name - name of field
	 * @param textFieldSize - lenght of field (in characters)
	 * @param unit - unit of value (i.e. volts, miliampers)
	 */
	public ValuePanel(String name, int textFieldSize , String unit) 
	{
		value = new JTextField(textFieldSize);		
		value.setText("0");
		label=new JLabel(name);
		add(label);
		add(value);
		JLabel unitLabel=new JLabel(unit);
		add(unitLabel);
	}

	public int getSelectedIndex() //Zmieniłem nazwę z getUnit na getSelectedIndex() by lepiej wskazywała co robi. Szatan
	{
		return unit.getSelectedIndex();						//trzeba doda� throw i catch na wypadek nie istniena wrato�ci
	}
	
	public double getValue()
	{
		return Double.valueOf(value.getText());				//trzeba doda� throw i catch na wypadek nie istniena wrato�ci
	}
	public void setValue(double value_)
	{
		value.setText(""+value_);
	}
	
	public void setValue(int value_)
	{
		value.setText(""+value_);
	}
	/*
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
