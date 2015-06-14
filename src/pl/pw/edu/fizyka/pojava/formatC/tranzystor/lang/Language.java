package pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * @author Aleksander Szpakiewicz-Szatan
 * This class creates array of strings from which program takes strings to use in GUI.
 * It should be run during startup and let user choose a language, than appropriate strings are written into the array
 */
public class Language 
{
	JFrame frame;
	int chosen; //Id of chosen language, -1 means no language chosen yet 	
	
	public Language()
	{
		frame =new JFrame("Wybierz język, choose language.");
		frame.setSize(320, 60);
		frame.setLayout(new FlowLayout());
		chosen=-1;
		JButton button[]=new JButton[2];
		button[0]=new JButton("Polski");
		button[1]=new JButton("English");
		
		for(int ii=0;ii<2;ii++)
		{
			frame.add(button[ii]);
			button[ii].addActionListener(new languageChosenListener(ii));
		}
	}
	public Locale initialise()
	{
		frame.setVisible(true);
		while(chosen==-1)
		{
			try 
			{
				Thread.sleep(10);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			} 
		}
		if(chosen==0)
			return new Locale("pl");
		if(chosen==1)
			return Locale.ENGLISH;
		return null;
	}
	
	public class languageChosenListener implements ActionListener
	{ 
        int buttonId; 
        public languageChosenListener(int id) 
        {
            buttonId = id;
        }
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			chosen=buttonId;
			frame.dispose();
		}
	};
}

