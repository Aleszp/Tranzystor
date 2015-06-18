package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*Łukasz, bardziej tego udziwnić nie mogłeś? 
Nie mogłeś zrobić trzech klas do różnych zastosowań? Przecież i tak narobiłeś ich chorą ilość. 
Szatan*/
/**
 * Class used to contain comboBoxes/textFields with labels
 * @author Łukasz Krzysztofik (documentation Aleksander Szpakiewicz-Szatan)
 *
 */
public class ValuePanel extends JPanel 
{
	private static final long serialVersionUID = 1397518244713660284L;
	
	JTextField value;
	JComboBox<String> comboBox; //changed name from 'unit' to 'comboBox', as in way it's used it makes more sense. Szatan
	JLabel label;
	
	/**
	 * Use ValuePanel(String,int) as constructor
	 * @param name - name of field being shown in labe
	 * @param textFieldSize - lenght of textField (in signs)
	 */
	public ValuePanel(String name, int textFieldSize) 
	{
		value = new JTextField(textFieldSize);
		add(new JLabel(name));
		add(value);
	}
	
	//Removed unused constructors, Szatan
	
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
	
	/**
	 * Use ValuePanel as constructor
	 * @param name - comboBox name
	 * @param comboBoxStrings - strings that comboBox should contain
	 */
	public ValuePanel(String name,String[] comboBoxStrings)  //renamed variable from 'unitType' to 'comboBoxString' as it's more logical
	{
		comboBox = new JComboBox<String>(comboBoxStrings);
		add(new JLabel(name));
		add(comboBox);
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

	/**
	 * @return index number of selected item in combobox
	 */
	public int getSelectedIndex() //Changed method name from 'getUnit' to 'getSelectedIndex' as it fits better it's purpose. Szatan
	{
		return comboBox.getSelectedIndex();
	}
	/**
	 * @return value typed in textField
	 */
	public double getValue()
	{
		return Double.valueOf(value.getText());
	}
	/**
	 * Use setValue(double) to set value inside textField
	 * @param value_ - value that should be set
	 */
	public void setValue(double value_)
	{
		value.setText(""+value_);
	}
	/**
	 * Use setValue(int) to set value inside textField
	 * @param value_ - value that should be set
	 */
	public void setValue(int value_)
	{
		value.setText(""+value_);
	}
	//Removed unused setters during cleanup. Szatan
}
