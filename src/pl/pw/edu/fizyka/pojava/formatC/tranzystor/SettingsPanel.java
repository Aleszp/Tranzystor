package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
/**
 * Class used to make panel that contain valuePanels just to organize GUI better
 * @author Łukasz Krzysztkofik (documentation: Aleksander Szpakiewicz-Szatan)
 */
public class SettingsPanel extends JPanel 
{
	private static final long serialVersionUID = -8005482503180775986L;
	
	ValuePanel[] panels;
	int panelNumber;
	/**
	 * Use SettingsPanel(Color, int, ValuePanel[], String) as constructor
	 * @param borderColor - color of borders
	 * @param panelNumberIn - number of subpanels that it would contain
	 * @param panelsIn - array of ValuePanels that it would contain
	 * @param name - text to use on label that informs what group of ValuePanel is in this particular panel.
	 */
	public SettingsPanel(Color borderColor,int panelNumberIn ,ValuePanel[] panelsIn,String name) 
	{
		panelNumber=panelNumberIn;
		setBorder(new LineBorder(borderColor));
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		add(new JLabel(name));
		panels=panelsIn;
		for(int i=0;i<panelNumber;i++)
			add(panels[i]);
	}
	//Removed unused getters and setters during clean-up. Szatan
}
