package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Localization;


/**
 * This class prepares panel with buttons that let user save and load transistor, export simulation results and start and stop simulation.
 */
public class ButtonPanel extends JPanel 
{
	private static final long serialVersionUID = 5962242182743707191L;
	ActivableButton loadButton;	
	ActivableButton saveButton;
	ActivableButton exportButton;
	ActivableButton startStopButton;
	Simulation simulation;
	Color activeColor;
	Color inactiveColor;

	/**
	 * Use ButtonPanel(Simulation) as constructor
	 * 
	 * @param simulation_ - this is Simulation that is controlled by this panel's buttons
	 */
	public ButtonPanel(Simulation simulation_) 
	{
		simulation=simulation_;
		inactiveColor=Color.GRAY;
		
		activeColor=new JButton().getBackground(); //this way active buttons colors are the same in every Look'n'Feel
		loadButton = new ActivableButton(Localization.getString("loadButton"),inactiveColor,activeColor,true);	
		saveButton = new ActivableButton(Localization.getString("saveButton"), inactiveColor, activeColor,true);
		exportButton = new ActivableButton(Localization.getString("exportButton"), inactiveColor, activeColor,false,Localization.getString("exportInactive"));
		startStopButton = new ActivableButton(Localization.getString("startButton"), inactiveColor, activeColor,true);  
		
		/**
		 *  Listener that is used to start and stop simulation
		 */
		ActionListener startStopAction=new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(!startStopButton.getActive())
					return;
				if(simulation.working)
				{
					simulation.working=false;
					startStopButton.setText(Localization.getString("startButton"));
				}
				else
				{
					simulation.working=true;
					startStopButton.setText(Localization.getString("stopButton"));
				}
			}
		};
		startStopButton.addActionListener(startStopAction);
		//Note: other listeners are added by classes that are connected to those listeners
		
		add(loadButton);
		add(saveButton);
		add(exportButton);
		add(startStopButton);
		setLayout(new GridLayout());
	}
	
	
}
