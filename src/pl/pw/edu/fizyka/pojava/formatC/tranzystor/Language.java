package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
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
	int chosen; //Id of chosen language, -1 means no language chosen yet 
	InputStream inputStream;
	Scanner scan;
	String langFilenmame[];	
	
	public Language()
	{
		frame =new JFrame("Wybierz język, choose language.");
		frame.setSize(320, 60);
		frame.setLayout(new FlowLayout());
		words=new String [20];
		chosen=-1;
		JButton button[]=new JButton[2];
		button[0]=new JButton("Polski");
		button[1]=new JButton("English");
		
		/*inputStream=this.getClass().getResourceAsStream("/lang/lang.lang");
		
		scan=new Scanner(inputStream);
		langFilenmame=new String[2];
		
		
		for(int ii=0;scan.hasNext();ii++)
		{
			button[ii]=new JButton(scan.next());
			langFilenmame[ii]=scan.next();
		}
		scan.close();
		
		*/
		for(int ii=0;ii<2;ii++)
		{
			frame.add(button[ii]);
			button[ii].addActionListener(new languageChosenListener(ii));
		}
	}
	public void initialise()
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
			LoadLang(chosen);
		}
		
		
	};
	void LoadLang(int chosen)
	{
		int ii=0;
		if(chosen==0)
		{
			words[ii++]="Symulacja Tranzystora";
			words[ii++]="Menu główne";
			words[ii++]="Menu ustawień";
			words[ii++]="Wczytaj Tranzystor";
			words[ii++]="Zapisz Tranzystor";
			words[ii++]="Zapisz wyniki";
			words[ii++]="Start";
			words[ii++]="Stop";
			words[ii++]="Natężenie prądu"; 
			words[ii++]=" w zależności od napięcia ";
			words[ii++]=" dla napięcia ";
			words[ii++]="Prąd /mA";
			words[ii++]="Napięcie /V";
			words[ii++]="Nastawy:";
			words[ii++]="Początkowe napięcie";
			words[ii++]="Liczba kroków";
			words[ii++]="Końcowe napięcie";
			words[ii++]="Wartości skrajne";
			words[ii++]="Macierz hybrydowa";
		}
		if(chosen==1)
		{
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
			words[ii++]="Beginning voltage";
			words[ii++]="Number of steps";
			words[ii++]="Final voltage";
			words[ii++]="Limiting values";
			words[ii++]="Hybrid matrix";
		}
	}
}
