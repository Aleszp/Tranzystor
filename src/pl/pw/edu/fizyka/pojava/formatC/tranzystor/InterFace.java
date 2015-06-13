package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
	
	String[] voltagesNames ={Localization.getString("Uce") ,Localization.getString("Ube") };
	String[] currentsNames ={Localization.getString("Ib"),Localization.getString("Ie"),Localization.getString("Ic")};
	
	String[] voltagesUnits ={"V" ,"mV"};
	String[] currentsUnits ={"A","mA"};
	
	MatrixPanel hybridMatrix;
	SettingsPanel settings1;
	SettingsPanel settings2;
	SettingsPanel settings3;
	GraphSettings graph1Setting;
	GraphSettings graph2Setting;
	
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
		super(Localization.getString("title"));
		setSize(640,480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout());
	
        frameColor=frameColorIn; 					//zmienna koloru ramek
		
        GraphsPanel graphs =new GraphsPanel(frameColor);
        
		graph1Setting=new GraphSettings(frameColor,voltagesNames,currentsNames,voltagesUnits,graphs.graph1);		
		graph2Setting=new GraphSettings(frameColor,voltagesNames,currentsNames,voltagesUnits,graphs.graph2);
		
		graph1Setting.refreshGraph();
		graph2Setting.refreshGraph();
		
		JPanel graphsSetttings =new JPanel();						
		graphsSetttings.add(graph1Setting);
		graphsSetttings.add(graph2Setting);
		
		ValuePanel[] settings1Panels_={
				new ValuePanel(Localization.getString("startVoltage"),6,0),
				new ValuePanel(Localization.getString("steps"),6,100),
				new ValuePanel(Localization.getString("endVoltage"),6,1)};
		collectorEmitterVoltageSettingsPanel=settings1Panels_;
		settings1 =new SettingsPanel(frameColor, 3 ,collectorEmitterVoltageSettingsPanel,"Ube");
		
		ValuePanel[] settings2Panels_={
				new ValuePanel(Localization.getString("startVoltage"),6,0),
				new ValuePanel(Localization.getString("steps"),6,1000),
				new ValuePanel(Localization.getString("endVoltage"),6,1)};
		baseEmitterVoltageSettingsPanel=settings2Panels_;
		settings2 =new SettingsPanel(frameColor, 3 ,baseEmitterVoltageSettingsPanel,Localization.getString("Uce"));
		
		ValuePanel[] settings3Panels_={
				new ValuePanel(Localization.getString("UceMax"),6,1),
				new ValuePanel(Localization.getString("UbeMax"),6,1),
				new ValuePanel(Localization.getString("UcbMax"),6,1),
				new ValuePanel(Localization.getString("IcMax"),6,10000),
				new ValuePanel(Localization.getString("IbMax"),6,10000),
				new ValuePanel(Localization.getString("IeMax"),6,10000)};
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
		
		JScrollPane settingScrollPane = new JScrollPane(settingPanel);
		
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
