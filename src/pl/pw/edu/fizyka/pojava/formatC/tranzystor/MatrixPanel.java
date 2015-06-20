package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Localization;
/**
 * Class containing h-matrix Panel which contains of ValuePanels that hold h-matrix data and saturation data (as it well fitted in GUI)
 */
public class MatrixPanel extends JPanel 
{
	private static final long serialVersionUID = -404291353413943280L;
	ValuePanel h11;
	ValuePanel h21;
	ValuePanel h12;
	ValuePanel h22;
	ValuePanel saturationVoltage;
	ValuePanel saturationCurrent;
	
	/**
	 * Use MatrixPanel(Color,int) as constructor
	 * @param frameColor - color of borders
	 * @param textFieldSize - size (in chars) of text fields
	 */
	public MatrixPanel(Color frameColor ,int textFieldSize) 
	{
		h11 =new ValuePanel(Localization.getString("h11"),textFieldSize,Localization.getString("ohms"));
		h21 =new ValuePanel(Localization.getString("h21"),textFieldSize);

		JPanel Column1 =new JPanel();
		Column1.setLayout(new BoxLayout(Column1,BoxLayout.Y_AXIS));
		Column1.add(h11);
		Column1.add(h21);

		h12 =new ValuePanel(Localization.getString("h12"),textFieldSize);
		h22 =new ValuePanel(Localization.getString("h22"),textFieldSize,Localization.getString("siemens"));

		JPanel Column2 =new JPanel();
		Column2.setLayout(new BoxLayout(Column2,BoxLayout.Y_AXIS));
		Column2.add(h12);
		Column2.add(h22);
		
		saturationVoltage=new ValuePanel(Localization.getString("UceSAT"),textFieldSize,Localization.getString("volts"));
		saturationCurrent=new ValuePanel(Localization.getString("IcSAT"),textFieldSize,Localization.getString("miliampers"));
		
		JPanel Column3 =new JPanel();
		Column3.setLayout(new BoxLayout(Column3,BoxLayout.Y_AXIS));
		Column3.add(saturationVoltage);
		Column3.add(saturationCurrent);
		
		setBorder(new LineBorder(frameColor));
		add(Column1);
		add(Column2);
		add(Column3);
		this.setMinimumSize(new Dimension(120,120));
	}
	/**
	 * @return H11 element of transistor's hybrid matrix 
	 */
	public double getH11(){return h11.getValue();}
	/**
	 * @return H12 element of transistor's hybrid matrix 
	 */
	public double getH12(){return h12.getValue();}
	/**
	 * @return H11 element of transistor's hybrid matrix 
	 */
	public double getH21(){return h21.getValue();}
	/**
	 * @return H11 element of transistor's hybrid matrix 
	 */
	public double getH22(){return h22.getValue();}
	/**
	 * @return saturationVoltage element of transistor's hybrid matrix 
	 */
	public double getSaturationVoltage(){return saturationVoltage.getValue();}
	/**
	 * @return saturationVoltage element of transistor's hybrid matrix 
	 */
	public double getSaturationCurrent(){return saturationCurrent.getValue();}
	
	/**
	 * Use {@link #setSaturationVoltage(double)} to set value of transistor's saturation voltage
	 * @param value - value which should be set
	 */
	public void setSaturationVoltage(double value){saturationVoltage.setValue(value);}
	/**
	 * Use {@link #setSaturationCurrent(double)} to set value of transistor's saturation current
	 * @param value - value which should be set
	 */
	public void setSaturationCurrent(double value){saturationCurrent.setValue(value);}
	
	/**
	 * Use {@link #getH(int)} to get value of transistor's h-matrix
	 * @param id - h-matrix elemet's id:
	 * <li>0 - h11 </li>
	 * <li>1 - h12 </li>
	 * <li>2 - h21 </li>
	 * <li>3 - h22 </li>
	 * @return value of h-matrix element
	 */
	public double getH(int id)
	{
		if(id==0)return h11.getValue();
		if(id==1)return h12.getValue();
		if(id==2)return h21.getValue();
		if(id==3)return h22.getValue();
		return -1; //returns -1 if no matrix element was chosen
	}
	/**
	 * Use {@link #setH(double, int)} to set value of transistor's h-matrix
	 * @param value - value which should be set
	 * @param id - h-matrix elemet's id:
	 * <li>0 - h11 </li>
	 * <li>1 - h12 </li>
	 * <li>2 - h21 </li>
	 * <li>3 - h22 </li>
	 */
	public void setH(double value,int id)
	{
		if(id==0) h11.setValue(value);
		if(id==1) h12.setValue(value);
		if(id==2) h21.setValue(value);
		if(id==3) h22.setValue(value);
	}
}
