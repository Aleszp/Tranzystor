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
}
