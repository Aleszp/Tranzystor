package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;

import pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Language;
import pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Localization;

/**
 *Main class of program. Here GUI and data containers are initialized.<br> 
 *@author Aleksander Szpakiewicz-Szatan
 */
public class Simulation 
{
	boolean working;
	InterFace frame;
	Localization localization;
	static Language lang;
	/**
	 * Use {@link #Data(} as constructor.<br>
	 * Sets working flag to false (as in start user needs to input data first)
	 */
	public Simulation()
	{
		working=false;
		lang=new Language();
		localization=new Localization(lang.initialise());
	}
	
	public boolean getWorking()
	{
		return working;
	}
	/**
	 *Main method of program. Initializes GUI and data containers, in future will run threads for calculations.<br> 
	 *
	 **/
	public static void main(String[] args) 
	{
		Simulation simulation=new Simulation();
		
		InterFace frame = new InterFace(Color.blue,simulation);
		frame.setTitle(Localization.getString("title")+" ("+Localization.getString("version")+")");
		frame.setVisible(true);
		Simulator simulator=new Simulator(frame, simulation);
		simulator.run();
	}
}
class Simulator implements Runnable
{
	InterFace frame;
	DataContainer data;
	Simulation simulation;
	
	public Simulator(InterFace frame_, Simulation simulation_)
	{
		frame=frame_;
		data=new DataContainer();
		ExportToFile fileIO=new ExportToFile(frame,data,simulation);
		TransistorIO transistorIO= new TransistorIO(frame);
		transistorIO.LoadDefaultTransistor(107);
		fileIO.dummy();
		simulation=simulation_;
	}
	@Override
	public void run() 
	{
		while(true)
		{
			if(simulation.working==true)
			{
				frame.buttonPanel.exportButton.setBackground(frame.buttonPanel.inactiveColor);
				frame.buttonPanel.loadButton.setBackground(frame.buttonPanel.inactiveColor);
				data.collectorEmitterVoltegeSteps=(int)frame.collectorEmitterVoltageSettingsPanel[1].getValue(); //Ucesteps
				data.baseEmitterVoltegeSteps=(int)frame.baseEmitterVoltageSettingsPanel[1].getValue(); //Ubesteps
				data.setMaximumValues(frame.maximumValuesSettingsPanel);
				data.loadArray(frame);
				data.setSaturationValues(frame.hybridMatrix);
				data.fillVoltageArrays(frame.collectorEmitterVoltageSettingsPanel[0].getValue(), frame.collectorEmitterVoltageSettingsPanel[2].getValue(), frame.baseEmitterVoltageSettingsPanel[0].getValue(), frame.baseEmitterVoltageSettingsPanel[2].getValue());
				
				clearGraph(frame.graph1Setting);
				clearGraph(frame.graph2Setting);
				
				for(int jj=0;jj<data.baseEmitterVoltegeSteps&&simulation.working==true;jj++)
				{
					for(int ii=0;ii<data.collectorEmitterVoltegeSteps&&simulation.working==true;ii++)
					{
						data.countCurrentsForSingleStep(ii, jj);
						data.checkMaximumValues(ii, jj);
						addToGraph(frame.graph1Setting, jj, ii);
						addToGraph(frame.graph2Setting, jj, ii);
					}
				}
				simulation.working=false;
				frame.buttonPanel.startStopButton.setText(Localization.getString("startButton"));
				frame.buttonPanel.loadButton.setBackground(frame.buttonPanel.activeColor);
				frame.buttonPanel.exportButton.setBackground(frame.buttonPanel.activeColor);
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
	void addToGraph(ChartSettings graphSettings, int baseEmitterVoltageStep, int collectorEmitterVoltageStep)
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
			else											//Ic
				current=data.getCollectorCurrent(collectorEmitterVoltageStep, baseEmitterVoltageStep);
		graphSettings.graph.addData(voltage, current);
	}
	void clearGraph(ChartSettings graphSettings)
	{
		graphSettings.graph.clearData();
	}
}
