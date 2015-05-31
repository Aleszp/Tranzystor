package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class MatrixPanel extends JPanel 
{
	private static final long serialVersionUID = -404291353413943280L;
	ValuePanel h11;
	ValuePanel h21;
	ValuePanel h12;
	ValuePanel h22;
	
	public MatrixPanel(Color frameColor ,int textFieldSize) 
	{
		h11 =new ValuePanel("H11",textFieldSize);
		h21 =new ValuePanel("H21",textFieldSize);

		JPanel Column1 =new JPanel();
		Column1.setLayout(new BoxLayout(Column1,BoxLayout.Y_AXIS));
		Column1.add(h11);
		Column1.add(h21);

		h12 =new ValuePanel("H12",textFieldSize);
		h22 =new ValuePanel("H22",textFieldSize);

		JPanel Column2 =new JPanel();
		Column2.setLayout(new BoxLayout(Column2,BoxLayout.Y_AXIS));
		Column2.add(h12);
		Column2.add(h22);
		
		setBorder(new LineBorder(frameColor));
		add(Column1);
		add(Column2);
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
	 * Use {@link #getH(int)} to get value of transistor's h-matrix
	 * @return value of h-matrix element with id as follows:
	 * <li>0 - h11 </li>
	 * <li>1 - h12 </li>
	 * <li>2 - h21 </li>
	 * <li>3 - h22 </li>
	 */
	public double getH(int id)
	{
		if(id==0)return h11.getValue();
		if(id==1)return h12.getValue();
		if(id==2)return h21.getValue();
		if(id==3)return h22.getValue();
		return -1; //returns -1 if no matrix element was chosen
	}
}
