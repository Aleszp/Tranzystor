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
	
	String[] voltagesUnits ={"V" ,"mV"};
	String[] currentsUnits ={"A","mA"};
	
	MatrixPanel hybridMatrix;
	SettingsPanel settings1;
	SettingsPanel settings2;
	SettingsPanel settings3;
	ChartSettings graph1Setting;
	ChartSettings graph2Setting;
	
	ValuePanel[] collectorEmitterVoltageSettingsPanel;
	ValuePanel[] baseEmitterVoltageSettingsPanel;
	ValuePanel[] maximumValuesSettingsPanel;
	
	ButtonPanel buttons;
	
	/**
	 * Default (and only constructor) which creates InterFace 
	 *  - first section with two graphs and basic simulation controls; <br>
	 *  - second section with control panel; <br>
	 *  */
	
	public InterFace(Color frameColorIn, Simulation simulation) throws HeadlessException 
	{
		super(Localization.getString("title")+" ("+Localization.getString("version")+")");
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout());
	
        frameColor=frameColorIn; 					//zmienna koloru ramek
		
        ChartPanel graphs =new ChartPanel(frameColor);
        
		graph1Setting=new ChartSettings(frameColor,voltagesNames,currentsNames,voltagesUnits,graphs.graph1,Localization.getString("grahp1Title"));		
		graph2Setting=new ChartSettings(frameColor,voltagesNames,currentsNames,voltagesUnits,graphs.graph2,Localization.getString("grahp2Title"));
		
		graph1Setting.refreshGraph();
		graph2Setting.refreshGraph();
		
		JPanel graphsSetttings =new JPanel();						
		graphsSetttings.add(graph1Setting);
		graphsSetttings.add(graph2Setting);
		
		ValuePanel[] settings1Panels_={
				new ValuePanel(Localization.getString("startVoltage"),6,0,Localization.getString("volts")),
				new ValuePanel(Localization.getString("steps"),6,100),
				new ValuePanel(Localization.getString("endVoltage"),6,1,Localization.getString("volts"))};
		collectorEmitterVoltageSettingsPanel=settings1Panels_;
		settings1 =new SettingsPanel(frameColor, 3 ,collectorEmitterVoltageSettingsPanel,Localization.getString("Uce"));
		
		ValuePanel[] settings2Panels_={
				new ValuePanel(Localization.getString("startVoltage"),6,0,Localization.getString("volts")),
				new ValuePanel(Localization.getString("steps"),6,1000),
				new ValuePanel(Localization.getString("endVoltage"),6,1,Localization.getString("volts"))};
		baseEmitterVoltageSettingsPanel=settings2Panels_;
		settings2 =new SettingsPanel(frameColor, 3 ,baseEmitterVoltageSettingsPanel,Localization.getString("Ube"));
		
		ValuePanel[] settings3Panels_={
				new ValuePanel(Localization.getString("UceMax"),6,45,Localization.getString("volts")),
				new ValuePanel(Localization.getString("UbeMax"),6,6,Localization.getString("volts")),
				new ValuePanel(Localization.getString("UcbMax"),6,50,Localization.getString("volts")),
				new ValuePanel(Localization.getString("IcMax"),6,100,Localization.getString("miliampers")),
				new ValuePanel(Localization.getString("IbMax"),6,100,Localization.getString("miliampers")),
				new ValuePanel(Localization.getString("IeMax"),6,100,Localization.getString("miliampers"))};
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
		
		hybridMatrix =new MatrixPanel(frameColor,6);
		buttons =new ButtonPanel(simulation);
		
		JPanel mainPanel =new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(graphs,BorderLayout.CENTER);
		mainPanel.add(buttons,BorderLayout.SOUTH);
		
		JPanel settingPanel =new JPanel();						//g��wna ramka
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
        
        ChangeListener changeListener=new ChangeListener() //Przy zmianie karty odświeżone zostają wykresy (by dostosować je do zmian w zakładce ustawień). Szatan
        {
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				graph1Setting.refreshGraph();
				graph2Setting.refreshGraph();
			}
        };
        tabbedPane.addChangeListener(changeListener);
        
        add(tabbedPane);
	}	
}
