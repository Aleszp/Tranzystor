package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

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
		loadButton = new JButton("Wczytaj Tranzystor");	
		saveButton = new JButton("Zapisz tranzystor");
		saveResultsButton = new JButton("Zapisz wyniki");
		startStopButton = new JButton("Start");  
		
		ActionListener startStopAction=new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(simulation.working)
				{
					simulation.working=false;
					startStopButton.setText("Start");
				}
				else
				{
					simulation.working=true;
					startStopButton.setText("Stop");
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
