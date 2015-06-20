package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Localization;

/**
 * Contains settings for single chart.
 *  */
public class ChartSettings extends JPanel 
{
	private static final long serialVersionUID = -376298015343955111L;
	
	ValuePanel ox;
	ValuePanel oy;
	ValuePanel parameter;
	String[] oxin;
	String[] oyin;
	Chart chart;
	
	/**
	 * Use {@link #GraphSettings(Color, String[], String[], String[], Chart, String)} as constructor.
	 * 
	 * @param frameColor color of frame
	 * @param oxin_ names of voltages
	 * @param oyin_ names of currents
	 * @param voltagesUnits units in which voltage could be shown
	 * @param graph_ reference to Graph which's settings it contains
	 * @param title name that should be displayed as settings title
	 * */
	public ChartSettings(Color frameColor,String[] oxin_,String[] oyin_,Chart graph_, String title) 
	{
		setBorder(new LineBorder(frameColor));
		oxin=oxin_;
		oyin=oyin_;
		chart=graph_;
		
		add(new JLabel(title,JLabel.CENTER));
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		ox=new ValuePanel(Localization.getString("OX"),oxin);
		add(ox);
		oy=new ValuePanel(Localization.getString("OY"),oyin);
		add(oy);
		//Changed that dumb field into parameter, Szatan
		parameter=new ValuePanel(oxin[1],5,0,Localization.getString("volts"));
		add(parameter);
		//One voltage is argument, second one parameter
		ox.comboBox.addItemListener(new ItemListener()
		{
            @Override
            public void itemStateChanged(ItemEvent e)
            {
            	@SuppressWarnings("unchecked")
				JComboBox<Integer> cb = (JComboBox<Integer>)e.getSource();
            	if(cb.getSelectedIndex()==0)
            		parameter.label.setText(Localization.getString("Uce"));
            	else
            		parameter.label.setText(Localization.getString("Ube"));
            }
        });
		
	}
	/**
	 * Use it to refresh text displayed on chart after changing it's settings.
	 * @author Aleksander Szpakiewicz-Szatan
	 */
	public void refreshChart()
	{
		chart.chart.setTitle(Localization.getString("chartTitle1")+" "+oyin[oy.comboBox.getSelectedIndex()]+" "+Localization.getString("chartTitle2")+" "+oxin[ox.comboBox.getSelectedIndex()]+" "+Localization.getString("chartTitle3")+" "+oxin[1-ox.comboBox.getSelectedIndex()]+"="+parameter.value.getText()+"V");
		
	}
	//Removed unused getters and setters during clean-up. Szatan
}
