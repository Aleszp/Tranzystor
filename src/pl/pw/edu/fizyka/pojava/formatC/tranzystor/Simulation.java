package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

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
	int baseEmitterVoltageStep;
	int collectorEmitterVoltageStep;
	TransistorIO transistorIO;
	ExecutorService exec;
	
	static Language lang;
	/**
	 * Use {@link #Data(} as constructor.<br>
	 * Sets working flag to false (as in start user needs to input data first)
	 */
	public Simulation()
	{
		working=false;
		exec=Executors.newFixedThreadPool(1);
		lang=new Language();
		localization=new Localization(lang.initialise());
		
		frame = new InterFace(Color.blue,this);
		frame.setTitle(Localization.getString("title")+" ("+Localization.getString("version")+")");
		frame.setVisible(true);
	
		data=new DataContainer();
		ExportToFile fileIO=new ExportToFile(frame,data,this);
		transistorIO=new TransistorIO(frame);
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
		simulation.exec.execute(simulation);
	}
	@Override
	/**
	 * Do all the necessary calculations, but only when allowed (siumlation is working) 
	 */
	public void run() 
	{
		frame.buttonPanel.exportButton.setActive(false,Localization.getString("exportInactive"));
		
		while(true)
		{
			if(getWorking()==true)
			{
				try
				{
					frame.clearCharts();
					frame.buttonPanel.exportButton.setActive(false,Localization.getString("exportInactive"));
					frame.buttonPanel.loadButton.setActive(false,Localization.getString("loadInactive"));
					data.collectorEmitterVoltegeSteps=(int)frame.collectorEmitterVoltageSettingsPanel[1].getValue(); //Ucesteps
					data.baseEmitterVoltegeSteps=(int)frame.baseEmitterVoltageSettingsPanel[1].getValue(); //Ubesteps
					data.setMaximumValues(frame.maximumValuesSettingsPanel);
					data.loadArray(frame);
					data.setSaturationValues(frame.hybridMatrix);
					data.fillVoltageArrays(frame.collectorEmitterVoltageSettingsPanel[0].getValue(), frame.collectorEmitterVoltageSettingsPanel[2].getValue(), frame.baseEmitterVoltageSettingsPanel[0].getValue(), frame.baseEmitterVoltageSettingsPanel[2].getValue());
				
					clearChart(frame.chart1Setting);
					clearChart(frame.chart2Setting);
				
					for(baseEmitterVoltageStep=0;baseEmitterVoltageStep<data.baseEmitterVoltegeSteps&&working==true;baseEmitterVoltageStep++)
					{
						for(collectorEmitterVoltageStep=0;collectorEmitterVoltageStep<data.collectorEmitterVoltegeSteps&&working==true;collectorEmitterVoltageStep++)
						{
							data.countCurrentsForSingleStep(collectorEmitterVoltageStep, baseEmitterVoltageStep);
							data.checkMaximumValues(collectorEmitterVoltageStep, baseEmitterVoltageStep);
							addToGraph(frame.chart1Setting, baseEmitterVoltageStep, collectorEmitterVoltageStep);
							addToGraph(frame.chart2Setting, baseEmitterVoltageStep, collectorEmitterVoltageStep);
						}
					}
				}
				catch(NumberFormatException e)
				{
					setWorking(false);
					frame.buttonPanel.startStopButton.setText(Localization.getString("startButton"));
					frame.buttonPanel.loadButton.setActive(true,"");
					frame.buttonPanel.exportButton.setActive(true,"");
					
					JOptionPane.showMessageDialog(
							frame, Localization.getString("wrongNumberDesc"),
							Localization.getString("wrongNumber"),
							JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception e)
				{
					setWorking(false);
					frame.buttonPanel.startStopButton.setText(Localization.getString("startButton"));
					frame.buttonPanel.loadButton.setActive(true,"");
					frame.buttonPanel.exportButton.setActive(true,"");
				}
				setWorking(false);
				frame.buttonPanel.startStopButton.setText(Localization.getString("startButton"));
				frame.buttonPanel.loadButton.setActive(true,"");
				frame.buttonPanel.exportButton.setActive(true,"");
			}
			else
			{
				frame.buttonPanel.startStopButton.setText(Localization.getString("startButton"));
				frame.buttonPanel.loadButton.setActive(true,"");
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
	 * @param chartSettings -settings for chart to which data could be added (in order to check if this data should be added)
	 * @param baseEmitterVoltageStep - number of base-emitter voltage step (to check valid data)
	 * @param collectorEmitterVoltageStep - number of collector-emitter voltage step (to check valid data) 
	 */
	void addToGraph(ChartSettings chartSettings, int baseEmitterVoltageStep, int collectorEmitterVoltageStep)
	{
		double voltage=0, current=0;
		if(chartSettings.ox.comboBox.getSelectedIndex()==0&&Math.abs(chartSettings.parameter.getValue()-data.getCollectorEmitterVoltage(collectorEmitterVoltageStep))<data.collectorEmitterVoltageStep/2) //Ube
			voltage=data.getBaseEmitterVoltage(baseEmitterVoltageStep);	
		else
			if(chartSettings.ox.comboBox.getSelectedIndex()==1&&(Math.abs(chartSettings.parameter.getValue()-data.getBaseEmitterVoltage(baseEmitterVoltageStep))<data.baseEmitterVoltageStep/2)) //Uce
				voltage=data.getCollectorEmitterVoltage(collectorEmitterVoltageStep);
			else
				return;
		if(chartSettings.oy.comboBox.getSelectedIndex()==0) //Ib
			current=data.getBaseCurrent(collectorEmitterVoltageStep, baseEmitterVoltageStep);
		else
			if(chartSettings.oy.comboBox.getSelectedIndex()==1) //Ie
				current=data.getEmitterCurrent(collectorEmitterVoltageStep, baseEmitterVoltageStep);
			else											//Ic
				current=data.getCollectorCurrent(collectorEmitterVoltageStep, baseEmitterVoltageStep);
		try
		{
			chartSettings.chart.addData(voltage, current);
		}
		catch(NullPointerException e)
		{
			
		}
		
	}
	/**
	 * Method used to add simulations results to graphs (force adding, ommit some checks)
	 * @param chartSettings -settings for chart to which data could be added (in order to check if this data should be added)
	 * @param baseEmitterVoltageStep - number of base-emitter voltage step (to check valid data)
	 * @param collectorEmitterVoltageStep - number of collector-emitter voltage step (to check valid data) 
	 */
	void forceAddToGraph(ChartSettings chartSettings, int baseEmitterVoltageStep, int collectorEmitterVoltageStep)
	{
		double voltage=0, current=0;
		if(chartSettings.ox.comboBox.getSelectedIndex()==0) //Ube
			voltage=data.getBaseEmitterVoltage(baseEmitterVoltageStep);	
		if(chartSettings.ox.comboBox.getSelectedIndex()==1) //Uce
			voltage=data.getCollectorEmitterVoltage(collectorEmitterVoltageStep);
		
		if(chartSettings.oy.comboBox.getSelectedIndex()==0) //Ib
			current=data.getBaseCurrent(collectorEmitterVoltageStep, baseEmitterVoltageStep);
		else
			if(chartSettings.oy.comboBox.getSelectedIndex()==1) //Ie
				current=data.getEmitterCurrent(collectorEmitterVoltageStep, baseEmitterVoltageStep);
			else											//Ic
				current=data.getCollectorCurrent(collectorEmitterVoltageStep, baseEmitterVoltageStep);
		try
		{
			chartSettings.chart.addData(voltage, current);
		}
		catch(NullPointerException e)
		{
			
		}
		
	}
	/**
	 * Use clearChart(ChartSettings) to remove all data from chart.
	 * @param chartSettings - settings of chart that should be cleared
	 */
	void clearChart(ChartSettings chartSettings){chartSettings.chart.clearData();}
	/**
	 * @return int - base-emitter voltage actual step
	 */
	int getBaseEmitterVoltageStep(){return baseEmitterVoltageStep;}
	/**
	 * @return int - collector-emitter voltage actual step
	 */
	int getCollectorEmitterVoltageStep(){return collectorEmitterVoltageStep;}
}
