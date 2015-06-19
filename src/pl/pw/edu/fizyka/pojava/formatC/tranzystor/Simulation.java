package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;

import pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Language;
import pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Localization;

/**
 *Main class of program. Here GUI and data containers are initialized.<br> 
 *@author Aleksander Szpakiewicz-Szatan
 */
public class Simulation implements Runnable
{ 
	boolean working;
	InterFace frame;
	Localization localization;
	DataContainer data;
	
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
		
		frame = new InterFace(Color.blue,this);
		frame.setTitle(Localization.getString("title")+" ("+Localization.getString("version")+")");
		frame.setVisible(true);
	
		data=new DataContainer();
		ExportToFile fileIO=new ExportToFile(frame,data,this);
		TransistorIO transistorIO= new TransistorIO(frame);
		transistorIO.LoadDefaultTransistor(107);
		fileIO.addExportButtonListener();
	}
	/**
	 * Use getWorking()
	 * @return true if simulation is working or not
	 */
	public boolean getWorking()
	{
		return working;
	}
	/**
	 * Use setWorking(boolean) to change simulation working status
	 * @param working - true if simulation is working
	 */
	public void setWorking(boolean working_)
	{
		working=working_;
	}
	/**
	 *Main method of program. Initializes GUI and data containers, in future will run threads for calculations.<br> 
	 *
	 **/
	public static void main(String[] args) 
	{
		Simulation simulation=new Simulation();
		simulation.run();
	}
	@Override
	/**
	 * Do all the necessary calculations, but only when allowed (siumlation is working) 
	 */
	public void run() 
	{
		while(true)
		{
			if(getWorking()==true)
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
				
				for(int jj=0;jj<data.baseEmitterVoltegeSteps&&working==true;jj++)
				{
					for(int ii=0;ii<data.collectorEmitterVoltegeSteps&&working==true;ii++)
					{
						data.countCurrentsForSingleStep(ii, jj);
						data.checkMaximumValues(ii, jj);
						addToGraph(frame.graph1Setting, jj, ii);
						addToGraph(frame.graph2Setting, jj, ii);
					}
				}
				setWorking(false);
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
	/**
	 * Method used to add simulations results to graphs (while simulation works)
	 * @param graphSettings -settings for chart to which data could be added (in order to check if this data should be added)
	 * @param baseEmitterVoltageStep - number of base-emitter voltage step (to check valid data)
	 * @param collectorEmitterVoltageStep - number of collector-emitter voltage step (to check valid data) 
	 */
	void addToGraph(ChartSettings graphSettings, int baseEmitterVoltageStep, int collectorEmitterVoltageStep)
	{
		double voltage, current;
		if(graphSettings.ox.comboBox.getSelectedIndex()==0&&(Math.abs(graphSettings.parameter.getValue()-data.getCollectorEmitterVoltage(collectorEmitterVoltageStep))<0.01)) //Ube
			voltage=data.getBaseEmitterVoltage(baseEmitterVoltageStep);
		else
			if(graphSettings.ox.comboBox.getSelectedIndex()==1&&(Math.abs(graphSettings.parameter.getValue()-data.getBaseEmitterVoltage(baseEmitterVoltageStep))<0.01)) //Uce
				voltage=data.getCollectorEmitterVoltage(collectorEmitterVoltageStep);
			else
				return;
		
		if(graphSettings.oy.comboBox.getSelectedIndex()==0) //Ib
			current=data.getBaseCurrent(collectorEmitterVoltageStep, baseEmitterVoltageStep);
		else
			if(graphSettings.oy.comboBox.getSelectedIndex()==1) //Ie
				current=data.getEmitterCurrent(collectorEmitterVoltageStep, baseEmitterVoltageStep);
			else											//Ic
				current=data.getCollectorCurrent(collectorEmitterVoltageStep, baseEmitterVoltageStep);
		graphSettings.graph.addData(voltage, current);
	}
	/**
	 * Use clearGraph(ChartSettings) to remove all data from chart.
	 */
	void clearGraph(ChartSettings graphSettings)
	{
		graphSettings.graph.clearData();
	}
}
