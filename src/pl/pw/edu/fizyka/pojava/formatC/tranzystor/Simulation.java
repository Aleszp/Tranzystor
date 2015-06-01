package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;

/**
 *Main class of program. Here GUI and data containers are initialized.<br> 
 *@author Aleksander Szpakiewicz-Szatan
 */
public class Simulation 
{
	static boolean working;
	static InterFace frame;
	static Language lang;
	/**
	 * Use {@link #Data(} as constructor.<br>
	 * Sets working flag to false (as in start user needs to input data first)
	 */
	public Simulation()
	{
		working=false;
	}
	
	/**
	 *Main method of program. Initializes GUI and data containers, in future will run threads for calculations.<br> 
	 *
	 **/
	public static void main(String[] args) 
	{
		lang=new Language();
		lang.initialise();
		Simulation simulation=new Simulation();
		
		frame = new InterFace(Color.blue,simulation);
		frame.setTitle(Language.words[0]);
		frame.setVisible(true);
		Simulator simulator=new Simulator(frame);
		simulator.run();
	}
}
class Simulator implements Runnable
{
	InterFace frame;
	Data data;
	
	public Simulator(InterFace frame_)
	{
		frame=frame_;
		data=new Data();
	}
	@Override
	public void run() 
	{
		//frame.settings1Panels[0].getValue(); //Ucestart
		//frame.settings1Panels[2].getValue(); //Ucestop
		//frame.settings2Panels[0].getValue(); //Ubestart
		//frame.settings2Panels[2].getValue(); //Ubestop
		while(true)
		{
			if(Simulation.working==true)
			{
				data.collectorEmitterVoltegeSteps=(int)frame.collectorEmitterVoltageSettingsPanel[1].getValue(); //Ucesteps
				data.baseEmitterVoltegeSteps=(int)frame.baseEmitterVoltageSettingsPanel[1].getValue(); //Ubesteps
				data.setMaximumValues(frame.maximumValuesSettingsPanel);
				data.loadArray(frame);
				data.setSaturationValues(frame.hybridMatrix);
				data.fillVoltageArrays(frame.collectorEmitterVoltageSettingsPanel[0].getValue(), frame.collectorEmitterVoltageSettingsPanel[2].getValue(), frame.baseEmitterVoltageSettingsPanel[0].getValue(), frame.baseEmitterVoltageSettingsPanel[2].getValue());
				for(int ii=0;ii<data.collectorEmitterVoltegeSteps;ii++)
				{
					for(int jj=0;jj<data.baseEmitterVoltegeSteps;jj++)
					{
						data.countCurrentsForSingleStep(ii, jj);
						System.out.println(data.getBaseCurrent(ii, jj));
					}
				}
				Simulation.working=false;
				frame.buttons.startStopButton.setText(Language.words[6]);
			}
			else
			{
				try 
				{
					Thread.sleep(10);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
}
