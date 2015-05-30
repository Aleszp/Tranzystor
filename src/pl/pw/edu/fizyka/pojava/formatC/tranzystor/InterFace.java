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
	
	String[] voltagesNames ={"Uce" ,"Ube" };
	String[] currentsNames ={"Ib","Ie","Ic"};
	
	String[] voltagesUnits ={"V" ,"mV"};
	String[] currentsUnits ={"A","mA"};
	
	MatrixPanel hybridMatrix;
	SettingsPanel settings1;
	SettingsPanel settings2;
	SettingsPanel settings3;
	GraphSettings graph1Setting;
	GraphSettings graph2Setting;
	
	/**
	 * Default (and only constructor) which creates InterFace 
	 *  - first section with two graphs and basic simulation controls; <br>
	 *  - second section with control panel; <br>
	 *  */
	
	public InterFace(Color frameColorIn, Simulation simulation) throws HeadlessException 
	{
		super(Language.words[0]);
		setSize(640,480);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
		
		ValuePanel[] settings1Panels={
				new ValuePanel(Language.words[14],3,voltagesUnits),new ValuePanel(Language.words[15],3),new ValuePanel(Language.words[16],3,voltagesUnits)};
		
		settings1 =new SettingsPanel(frameColor, 3 ,settings1Panels,"Uce");
		
		ValuePanel[] settings2Panels={
				new ValuePanel(Language.words[14],3,voltagesUnits),new ValuePanel(Language.words[15],3),new ValuePanel(Language.words[16],3,voltagesUnits)};
		
		settings2 =new SettingsPanel(frameColor, 3 ,settings2Panels,"Ube");
		
		ValuePanel[] settings3Panels={
				new ValuePanel("Ucemax",3,voltagesUnits),new ValuePanel("Ubemax",3,voltagesUnits),new ValuePanel("Ucbmax",3,voltagesUnits),
				new ValuePanel("Icmax",3,currentsUnits),new ValuePanel("Ibmax",3,currentsUnits),new ValuePanel("Iemax",3,currentsUnits)};

		settings3=new SettingsPanel(frameColor, 6 ,settings3Panels,Language.words[17]);
		
		
		JPanel settings12 =new JPanel();
		settings12.setLayout(new BorderLayout());
		settings12.add(settings1,BorderLayout.NORTH);
		settings12.add(settings2,BorderLayout.SOUTH);
		
		JPanel settings =new JPanel();
		settings.setLayout(new GridLayout());
		settings.add(settings12);
		settings.add(settings3);
		
		hybridMatrix =new MatrixPanel(frameColor,3);

		ButtonPanel buttons =new ButtonPanel(simulation);
		
		JPanel mainPanel =new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(graphs,BorderLayout.CENTER);
		mainPanel.add(buttons,BorderLayout.SOUTH);
		
		JPanel settingPanel =new JPanel();						//g��wna ramka
		settingPanel.setLayout(new BoxLayout(settingPanel,BoxLayout.Y_AXIS));
		settingPanel.add(new JLabel(Language.words[13],JLabel.CENTER));
		settingPanel.add(graphsSetttings);
		settingPanel.add(settings);
		settingPanel.add(hybridMatrix);
		
		JScrollPane settingScrollPane = new JScrollPane(settingPanel);
		
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(Language.words[1],mainPanel);
        tabbedPane.addTab(Language.words[2],settingScrollPane);
        
        add(tabbedPane);
	}	
}
