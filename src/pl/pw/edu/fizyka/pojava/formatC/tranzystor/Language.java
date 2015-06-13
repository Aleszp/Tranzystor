package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;

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
	static String words[];
	static String version;
	double versionNumer;
	
	int chosen; //Id of chosen language, -1 means no language chosen yet 
	InputStream inputStream;
	Scanner scan;
	String langFilenmame[];	
	
	public Language()
	{
		versionNumer=1.2;
		frame =new JFrame("Wybierz język, choose language.");
		frame.setSize(320, 60);
		frame.setLayout(new FlowLayout());
		words=new String [20];
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
			System.out.println(chosen);
			//LoadLang(chosen);
		}
	};
	void LoadLang(int chosen)
	{
		int ii=0;
		if(chosen==0)
		{
			version="Alfa "+versionNumer;
			words[0]="Symulacja Tranzystora";
			words[1]="Menu główne";
			words[2]="Menu ustawień";
			words[3]="Wczytaj Tranzystor";
			words[4]="Zapisz Tranzystor";
			words[5]="Zapisz wyniki";
			words[6]="Start";
			words[7]="Stop";
			words[8]="Natężenie prądu "; 
			words[9]=" w zależności od napięcia ";
			words[10]=" dla napięcia ";
			words[11]="Prąd /mA";
			words[12]="Napięcie /V";
			words[13]="Nastawy:";
			words[14]="Początkowe napięcie";
			words[15]="Liczba kroków";
			words[16]="Końcowe napięcie";
			words[17]="Wartości skrajne";
			words[18]="Macierz hybrydowa";
			
		}
		if(chosen==1)
		{
			version="Alpha "+versionNumer;;
			words[ii++]="Transistor Simulation";
			words[ii++]="Main Menu";
			words[ii++]="Settings Menu";
			words[ii++]="Load Transistor";
			words[ii++]="Save Transistor";
			words[ii++]="Save Results";
			words[ii++]="Start";
			words[ii++]="Stop";
			words[ii++]="Current "; 
			words[ii++]=" in function of voltage ";
			words[ii++]=" for voltage ";
			words[ii++]="Current /mA";
			words[ii++]="Voltage /V";
			words[ii++]="Setting:";
			words[ii++]="Initial voltage";
			words[ii++]="Number of steps";
			words[ii++]="Final voltage";
			words[ii++]="Limiting values";
			words[ii++]="Hybrid matrix";
		}
	}
}
