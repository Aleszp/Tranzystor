package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Localization;

/**
 * Renders programs InterFace that consists of two sections between which user may switch: <br> 
 *  - first section with two graphs and basic simulation controls; <br>
 *  - second section with control panel; <br>
 *  */
public class InterFace extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	Dimension sizeMinimal = new Dimension(300, 400);	
	Color frameColor=null;
	
	String[] voltagesNames ={Localization.getString("Ube"), Localization.getString("Uce")};
	String[] currentsNames ={Localization.getString("Ib"),Localization.getString("Ie"),Localization.getString("Ic")};
	
	MatrixPanel hybridMatrix;
	SettingsPanel settings1;
	SettingsPanel settings2;
	SettingsPanel settings3;
	ChartSettings chart1Setting;
	ChartSettings chart2Setting;
	
	Simulation simulation;
	
	ValuePanel[] collectorEmitterVoltageSettingsPanel;
	ValuePanel[] baseEmitterVoltageSettingsPanel;
	ValuePanel[] maximumValuesSettingsPanel;
	
	ButtonPanel buttonPanel; //Changed field name from buttons into buttonPanel to be more descriptive. Szatan
	
	/**
	 * Default (and only constructor) which creates InterFace 
	 *  - first section with two graphs and basic simulation controls; <br>
	 *  - second section with control panel; <br>
	 *  
	 */
	
	public InterFace(Color frameColorIn, Simulation simulation_) throws HeadlessException 
	{
		super(Localization.getString("title")+" ("+Localization.getString("version")+")");
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout());
	
		simulation=simulation_;
        frameColor=frameColorIn; 			
        ChartPanel graphs =new ChartPanel(frameColor);
        
        JPanel graphsSetttings=prepareChartsSettingsPanel(graphs);
        JPanel settings=prepareSettingsPanels();

		hybridMatrix =new MatrixPanel(frameColor,6);
		buttonPanel =new ButtonPanel(simulation);
		
		JPanel mainPanel =new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(graphs,BorderLayout.CENTER);
		mainPanel.add(buttonPanel,BorderLayout.SOUTH);
		
		JPanel settingPanel =new JPanel();
		settingPanel.setLayout(new BoxLayout(settingPanel,BoxLayout.Y_AXIS));
		settingPanel.add(new JLabel(Localization.getString("settingsTitle1"),JLabel.CENTER));
		settingPanel.add(graphsSetttings);
		settingPanel.add(settings);
		settingPanel.add(hybridMatrix);
		
		JPanel settingPanelPanel=new JPanel();
		settingPanelPanel.setLayout(new FlowLayout(FlowLayout.CENTER,1,1));
		settingPanelPanel.add(settingPanel);
		JScrollPane settingScrollPane = new JScrollPane(settingPanelPanel);
		settingScrollPane.setMaximumSize(new Dimension(640,480));
		
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(Localization.getString("simulationMenu"),mainPanel);
        tabbedPane.addTab(Localization.getString("settingsMenu"),settingScrollPane);
        
        ChangeListener changeListener=new ChangeListener() //When changing tab charts are refreshed (to fit their settings). Szatan
        {
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				if(tabbedPane.getSelectedIndex()==1)
					return;
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()
				{
					protected Void doInBackground() throws Exception 
					{
						refresh();
						return null;
					}
				};
				worker.execute();
			}
        };
        tabbedPane.addChangeListener(changeListener);
        
        add(tabbedPane);
        
	}
	/**
	 * Use prepareCharts(ChartPanel) to prepare panel with charts setting panels
	 * @param charts - chart panel that contains charts for which settings should be prepared.
	 * @return JPanel that contains charts settings panels
	 */
	JPanel prepareChartsSettingsPanel(ChartPanel charts)
	{
		JPanel chartsSetttings=new JPanel();
		
		chart1Setting=new ChartSettings(frameColor,voltagesNames,currentsNames,charts.chart1,Localization.getString("grahp1Title"));		
		chart2Setting=new ChartSettings(frameColor,voltagesNames,currentsNames,charts.chart2,Localization.getString("grahp2Title"));
		
		chart1Setting.refreshChart();
		chart2Setting.refreshChart();
							
		chartsSetttings.add(chart1Setting);
		chartsSetttings.add(chart2Setting);
		return chartsSetttings;
	}
	/**
	 * @return JPanel that contains simulation voltages parameters and limiting values' settings panels.
	 */
	JPanel prepareSettingsPanels()
	{
		ValuePanel[] settings1Panels_={
				new ValuePanel(Localization.getString("startVoltage"),6,Localization.getString("volts")),
				new ValuePanel(Localization.getString("steps"),6,100),
				new ValuePanel(Localization.getString("endVoltage"),6,1,Localization.getString("volts"))};
		collectorEmitterVoltageSettingsPanel=settings1Panels_;
		settings1 =new SettingsPanel(frameColor, 3 ,collectorEmitterVoltageSettingsPanel,Localization.getString("Uce"));
		
		ValuePanel[] settings2Panels_={
				new ValuePanel(Localization.getString("startVoltage"),6,Localization.getString("volts")),
				new ValuePanel(Localization.getString("steps"),6,1000),
				new ValuePanel(Localization.getString("endVoltage"),6,1,Localization.getString("volts"))};
		baseEmitterVoltageSettingsPanel=settings2Panels_;
		settings2 =new SettingsPanel(frameColor, 3 ,baseEmitterVoltageSettingsPanel,Localization.getString("Ube"));
		
		ValuePanel[] settings3Panels_={
				new ValuePanel(Localization.getString("UceMax"),6,Localization.getString("volts")),
				new ValuePanel(Localization.getString("UbeMax"),6,Localization.getString("volts")),
				new ValuePanel(Localization.getString("UcbMax"),6,Localization.getString("volts")),
				new ValuePanel(Localization.getString("IcMax"),6,Localization.getString("miliampers")),
				new ValuePanel(Localization.getString("IbMax"),6,Localization.getString("miliampers")),
				new ValuePanel(Localization.getString("IeMax"),6,Localization.getString("miliampers"))};
		maximumValuesSettingsPanel=settings3Panels_;
		settings3=new SettingsPanel(frameColor, 6 ,maximumValuesSettingsPanel,Localization.getString("limitingValues"));
		
		JPanel settings12 =new JPanel();
		settings12.setLayout(new BorderLayout());
		settings12.add(settings1,BorderLayout.NORTH);
		settings12.add(settings2,BorderLayout.SOUTH);
		
		JPanel settings =new JPanel();
		settings.setLayout(new GridLayout());
		settings.add(settings12);
		settings.add(settings3);
		return settings;
	}
	/**
	 * This method refreshes charts (only if simulation isn't working). Locks ability to start simulation while it works).
	 */
	public void refresh() 
	{	
		if(simulation.getWorking()||!simulation.frame.buttonPanel.startStopButton.getActive())
		{
			return;
		}
		simulation.frame.buttonPanel.startStopButton.setActive(false, Localization.getString("refreshing"));
		DataContainer data=simulation.data;
		chart1Setting.refreshChart();
		chart2Setting.refreshChart();
		clearCharts();
		int maximalBaseEmitterVoltageStep=data.baseEmitterVoltageSteps;
		int maximalCollectorEmitterVoltageStep=data.collectorEmitterVoltageSteps;	
		try
		{
			int chart1VoltageStep=0;
			int chart2VoltageStep=0;
			int chart1OX=chart1Setting.ox.getSelectedIndex();
			int chart2OX=chart2Setting.ox.getSelectedIndex();
			for(int ii=0;ii<maximalBaseEmitterVoltageStep;ii++)
			{
				if(chart1OX==1)
					if(Math.abs(data.getBaseEmitterVoltage(ii)-chart1Setting.parameter.getValue())<data.baseEmitterVoltageStep/2)
						chart1VoltageStep=ii;
				if(chart2OX==1)
					if(Math.abs(data.getBaseEmitterVoltage(ii)-chart2Setting.parameter.getValue())<data.baseEmitterVoltageStep/2)
						chart2VoltageStep=ii;
			}
			for(int ii=0;ii<maximalCollectorEmitterVoltageStep;ii++)
			{
				if(chart1OX==0)
					if(Math.abs(data.getBaseEmitterVoltage(ii)-chart1Setting.parameter.getValue())<data.baseEmitterVoltageStep/2)
						chart1VoltageStep=ii;
				if(chart2OX==0)
					if(Math.abs(data.getBaseEmitterVoltage(ii)-chart2Setting.parameter.getValue())<data.baseEmitterVoltageStep/2)
						chart2VoltageStep=ii;
			}
			for(int ii=0;ii<maximalBaseEmitterVoltageStep;ii++)
			{
				if(chart1OX==0)
					simulation.forceAddToGraph(chart1Setting, ii, chart1VoltageStep);
				if(chart2OX==0)
					simulation.forceAddToGraph(chart2Setting, ii, chart2VoltageStep);
			}
			for(int ii=0;ii<maximalCollectorEmitterVoltageStep;ii++)
			{
				if(simulation.getWorking())
				{
					maximalBaseEmitterVoltageStep=simulation.getBaseEmitterVoltageStep();
					maximalCollectorEmitterVoltageStep=simulation.getCollectorEmitterVoltageStep();
				}
				if(chart1OX==1)
					simulation.forceAddToGraph(chart1Setting, chart1VoltageStep, ii);
				if(chart2OX==1)
					simulation.forceAddToGraph(chart2Setting, chart2VoltageStep, ii);
			}
			simulation.frame.buttonPanel.startStopButton.setActive(true, "");
		}
		catch(Exception e)
		{		
			e.printStackTrace();
		}
	}
	/**
	 * This method clears both charts.
	 */
	public void clearCharts()
	{
		simulation.clearChart(chart1Setting);
		simulation.clearChart(chart2Setting);
	}
}
