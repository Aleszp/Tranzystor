package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Localization;

public class ButtonPanel extends JPanel 
{
	private static final long serialVersionUID = 5962242182743707191L;
	JButton loadButton;	
	JButton saveButton;
	JButton exportButton;
	JButton startStopButton;
	Simulation simulation;
	Color activeColor;
	Color inActiveColor;

	public ButtonPanel(Simulation simulation_) 
	{
		simulation=simulation_;
		
		inActiveColor=Color.GRAY;
		loadButton = new JButton(Localization.getString("loadButton"));	
		saveButton = new JButton(Localization.getString("saveButton"));
		exportButton = new JButton(Localization.getString("exportButton"));
		startStopButton = new JButton(Localization.getString("startButton"));  
		
		loadButton.setBackground(inActiveColor);
		loadButton.setOpaque(true);
		saveButton.setBackground(inActiveColor);
		saveButton.setOpaque(true);
		activeColor=exportButton.getBackground();
		exportButton.setBackground(inActiveColor);
		exportButton.setOpaque(true);
		
		ActionListener startStopAction=new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
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
		
		add(loadButton);
		add(saveButton);
		add(exportButton);
		add(startStopButton);
		setLayout(new GridLayout());
	}
}
