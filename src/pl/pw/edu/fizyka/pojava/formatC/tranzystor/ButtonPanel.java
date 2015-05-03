package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel 
{
	private static final long serialVersionUID = 5962242182743707191L;
	JButton loadButton;	
	JButton saveButton;
	JButton saveResultsButton;
	JButton startStopButton;

	public ButtonPanel() 
	{
		loadButton = new JButton("Wczytaj Tranzystor");	
		saveButton = new JButton("Zapisz tranzystor");
		saveResultsButton = new JButton("Zapisz wyniki");
		startStopButton = new JButton("Zacznij/Zakończ symulację");  
		
		add(loadButton);
		add(saveButton);
		add(saveResultsButton);
		add(startStopButton);
	}
}
