package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel 
{
	private static final long serialVersionUID = 5962242182743707191L;
	JButton loadButton;	
	JButton saveButton;
	JButton saveResultsButton;
	JButton startStopButton;

	public ButtonPanel(Simulation simulation) 
	{
		loadButton = new JButton(Language.words[3]);	
		saveButton = new JButton(Language.words[4]);
		saveResultsButton = new JButton(Language.words[5]);
		startStopButton = new JButton(Language.words[6]);  
		
		loadButton.setBackground(Color.GRAY);
		loadButton.setOpaque(true);
		saveButton.setBackground(Color.GRAY);
		saveButton.setOpaque(true);
		saveResultsButton.setBackground(Color.GRAY);
		saveResultsButton.setOpaque(true);
		
		ActionListener startStopAction=new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(Simulation.working)
				{
					Simulation.working=false;
					startStopButton.setText(Language.words[6]);
				}
				else
				{
					Simulation.working=true;
					startStopButton.setText(Language.words[7]);
				}
			}
		};
		startStopButton.addActionListener(startStopAction);
		
		add(loadButton);
		add(saveButton);
		add(saveResultsButton);
		add(startStopButton);
		setLayout(new GridLayout());
	}
}
