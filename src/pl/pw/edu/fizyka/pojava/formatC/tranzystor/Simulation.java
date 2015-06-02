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
		frame.setTitle(Language.words[0]+" ("+Language.version+")");
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
				
				clearGraph(frame.graph1Setting);
				clearGraph(frame.graph2Setting);
				
				for(int jj=0;jj<data.baseEmitterVoltegeSteps&&Simulation.working==true;jj++)
				{
					for(int ii=0;ii<data.collectorEmitterVoltegeSteps&&Simulation.working==true;ii++)
					{
						data.countCurrentsForSingleStep(ii, jj);
						System.out.println(data.getCollectorEmitterVoltage(ii)+"; "+data.getBaseEmitterVoltage(jj)+"; "+data.getBaseCurrent(ii, jj)+"; "+data.getCollectorCurrent(ii, jj)+"; "+data.getEmitterCurrent(ii, jj));
						data.checkMaximumValues(ii, jj);
						addToGraph(frame.graph1Setting, jj, ii);
						addToGraph(frame.graph2Setting, jj, ii);
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
	void addToGraph(GraphSettings graphSettings, int baseEmitterVoltageStep, int collectorEmitterVoltageStep)
	{
		double voltage, current;
		if(graphSettings.ox.unit.getSelectedIndex()==0&&(Math.abs(graphSettings.parameter.getValue()-data.getCollectorEmitterVoltage(collectorEmitterVoltageStep))<0.01)) //Ube
			voltage=data.getBaseEmitterVoltage(baseEmitterVoltageStep);
		else
			if(graphSettings.ox.unit.getSelectedIndex()==1&&(Math.abs(graphSettings.parameter.getValue()-data.getBaseEmitterVoltage(baseEmitterVoltageStep))<0.01)) //Uce
				voltage=data.getCollectorEmitterVoltage(collectorEmitterVoltageStep);
			else
				return;
		
		if(graphSettings.oy.unit.getSelectedIndex()==0) //Ib
			current=data.getBaseCurrent(collectorEmitterVoltageStep, baseEmitterVoltageStep);
		else
			if(graphSettings.oy.unit.getSelectedIndex()==1) //Ie
				current=data.getEmitterCurrent(collectorEmitterVoltageStep, baseEmitterVoltageStep);
			else
				current=data.getCollectorCurrent(collectorEmitterVoltageStep, baseEmitterVoltageStep);
		graphSettings.graph.addData(voltage, current);
	}
	void clearGraph(GraphSettings graphSettings)
	{
		graphSettings.graph.clearData();
	}
}
