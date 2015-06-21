package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

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
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()
		{
			protected Void doInBackground() throws Exception 
			{
				simulation.simulate();
				return null;
			}
		};
		worker.execute();
	}
	/**
	 * Do all the necessary calculations, but only when allowed (simulation is working) 
	 */
	public synchronized void simulate() 
	{
		frame.buttonPanel.exportButton.setActive(false,Localization.getString("exportInactive"));
		int ox1Index=frame.chart1Setting.ox.getSelectedIndex();
		int ox2Index=frame.chart2Setting.ox.getSelectedIndex();
		int oy1Index=frame.chart1Setting.oy.getSelectedIndex();
		int oy2Index=frame.chart2Setting.oy.getSelectedIndex();
		double parameter1=frame.chart1Setting.parameter.getValue(); 
		double parameter2=frame.chart2Setting.parameter.getValue();
		
		while(true)
		{
			if(getWorking()==true)
			{
				try
				{
					frame.clearCharts();
					frame.buttonPanel.exportButton.setActive(false,Localization.getString("exportInactive"));
					frame.buttonPanel.loadButton.setActive(false,Localization.getString("loadInactive"));
					doDataOperations();	
					computeValues(ox1Index,oy1Index,ox2Index,oy2Index,parameter1,parameter2);
				}
				catch(NumberFormatException e)
				{
					finish();
					JOptionPane.showMessageDialog(
							frame, Localization.getString("wrongNumberDesc"),
							Localization.getString("wrongNumber"),
							JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception e)
				{
					finish();
				}
				finish();
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
	 * Use computeValues(int,int,int,int,double,double) to calculate values and add them to charts
	 * @param ox1Index - index of first charts chosen voltage
	 * @param oy1Index - index of first charts chosen current
	 * @param ox2Index - index of second charts chosen voltage
	 * @param oy2Index - index of second charts chosen current
	 * @param parameter1 - first chart's parameter value 
	 * @param parameter2 - second chart's parameter value
	 */
	void computeValues(int ox1Index,int oy1Index,int ox2Index,int oy2Index, double parameter1,double parameter2)
	{
		for(baseEmitterVoltageStep=0;baseEmitterVoltageStep<data.baseEmitterVoltageSteps&&working==true;baseEmitterVoltageStep++)
		{
			for(collectorEmitterVoltageStep=0;collectorEmitterVoltageStep<data.collectorEmitterVoltageSteps&&working==true;collectorEmitterVoltageStep++)
			{
				data.countCurrentsForSingleStep(collectorEmitterVoltageStep, baseEmitterVoltageStep);
				data.checkMaximumValues(collectorEmitterVoltageStep, baseEmitterVoltageStep);
				addToGraph(frame.chart1Setting,ox1Index,oy1Index,parameter1, baseEmitterVoltageStep, collectorEmitterVoltageStep);
				addToGraph(frame.chart2Setting,ox2Index,oy2Index,parameter2, baseEmitterVoltageStep, collectorEmitterVoltageStep);
			}
		}
	}
	/**
	 * Method used to add simulations results to graphs (while simulation works)
	 * @param chartSettings -settings for chart to which data could be added (in order to check if this data should be added)
	 * @param oxIndex - index of chosen ox (tells whether its base-emitter or collector-emitter voltage)
	 * @param oxIndex - index of chosen oy (tells whether its base, collector or emitter current)
	 * @param parameter - value of parameter (base-emitter or collector-emitter voltage)
	 * @param baseEmitterVoltageStep - number of base-emitter voltage step (to check valid data)
	 * @param collectorEmitterVoltageStep - number of collector-emitter voltage step (to check valid data) 
	 */
	void addToGraph(ChartSettings chartSettings, int oxIndex, int oyIndex, double parameter, int baseEmitterVoltageStep, int collectorEmitterVoltageStep)
	{
		double voltage=0, current=0;
		if(oxIndex==0&&Math.abs(parameter-data.getCollectorEmitterVoltage(collectorEmitterVoltageStep))<data.collectorEmitterVoltageStep/2) //Ube
			voltage=data.getBaseEmitterVoltage(baseEmitterVoltageStep);	
		else
			if(oxIndex==1&&(Math.abs(parameter-data.getBaseEmitterVoltage(baseEmitterVoltageStep))<data.baseEmitterVoltageStep/2)) //Uce
				voltage=data.getCollectorEmitterVoltage(collectorEmitterVoltageStep);
			else
				return;
		if(oyIndex==0) //Ib
			current=data.getBaseCurrent(collectorEmitterVoltageStep, baseEmitterVoltageStep);
		else
			if(oyIndex==1) //Ie
				current=data.getEmitterCurrent(collectorEmitterVoltageStep, baseEmitterVoltageStep);
			else											//Ic
				current=data.getCollectorCurrent(collectorEmitterVoltageStep, baseEmitterVoltageStep);
		try
		{
			chartSettings.chart.addData(voltage, current);
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
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
	/**
	 * Method used when simulation finishes (or errors)
	 */
	void finish()
	{
		setWorking(false);
		frame.buttonPanel.startStopButton.setText(Localization.getString("startButton"));
		frame.buttonPanel.loadButton.setActive(true,"");
		frame.buttonPanel.exportButton.setActive(true,"");
	}
	/**
	 * This method is just grouped bunch of DataContainer class'es methods runned one after another. 
	 */
	void doDataOperations()
	{
		data.collectorEmitterVoltageSteps=(int)frame.collectorEmitterVoltageSettingsPanel[1].getValue(); //Ucesteps
		data.baseEmitterVoltageSteps=(int)frame.baseEmitterVoltageSettingsPanel[1].getValue(); //Ubesteps
		data.setMaximumValues(frame.maximumValuesSettingsPanel);
		data.loadArray(frame);
		data.setSaturationValues(frame.hybridMatrix);
		data.fillVoltageArrays(frame.collectorEmitterVoltageSettingsPanel[0].getValue(),
				frame.collectorEmitterVoltageSettingsPanel[2].getValue(),
				frame.baseEmitterVoltageSettingsPanel[0].getValue(), 
				frame.baseEmitterVoltageSettingsPanel[2].getValue());
	}
}
