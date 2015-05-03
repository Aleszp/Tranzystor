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
import javax.swing.JTabbedPane;


/**
 * Renders programs InterFace that consists of two sections between which user may switch: <br> 
 *  - first section with two graphs and basic simulation controls; <br>
 *  - second section with control panel; <br>
 *  */
public class InterFace extends JFrame 
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default (and only constructor) which creates InterFace 
	 *  - first section with two graphs and basic simulation controls; <br>
	 *  - second section with control panel; <br>
	 *  */
	
	Dimension sizeMinimal = new Dimension(300, 400);	
	Color frameColor=null;
	
	String[] voltagesNames ={"Uce" ,"Ube" };
	String[] currentsNames ={"Ib","Ie","Ic"};
	
	String[] voltagesUnits ={"V" ,"mV"};
	String[] currentsUnits ={"A","mA"};
	
	public InterFace(Color frameColorIn) throws HeadlessException 
	{
		super("Symulacja tranzystora");
		setSize(640,480);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new GridLayout());
	
        frameColor=frameColorIn; 					//zmienna koloru ramek
		
		GraphSettings graph1Setting=new GraphSettings(frameColor,voltagesNames,currentsNames,"Wykres1");		

		GraphSettings graph2Setting=new GraphSettings(frameColor,voltagesNames,currentsNames,"Wykres2");
		graph2Setting.graphParameterLabel.setText("Voltage 1 V"); 		//przyk�ad odwo�nia do elementu wewn�tzengo
		
		JPanel graphsSetttings =new JPanel();						
		graphsSetttings.add(graph1Setting);
		graphsSetttings.add(graph2Setting);
		
		ValuePanel[] sentings1Panels={
				new ValuePanel("Początkowe napięcie",3,voltagesUnits),new ValuePanel("Liczba kroków",3),new ValuePanel("Ko�cowe napi�cie",3,voltagesUnits)};
		
		SettingsPanel settings1 =new SettingsPanel(frameColor, 3 ,sentings1Panels,"Uce");
		
		ValuePanel[] sentings2Panels={
				new ValuePanel("Ucemax",3,voltagesUnits),new ValuePanel("Ubemax",3,voltagesUnits),new ValuePanel("Ucbmax",3,voltagesUnits),
				new ValuePanel("Icmax",3,currentsUnits),new ValuePanel("Ibmax",3,currentsUnits),new ValuePanel("Iemax",3,currentsUnits)};

		SettingsPanel settings2=new SettingsPanel(frameColor, 6 ,sentings2Panels,"Uce");
		
		JPanel settings =new JPanel();
		settings.add(settings1);
		settings.add(settings2);
		
		MatrixPanel hybridMatrix =new MatrixPanel(frameColor,3);
		
		GraphsPanel graphs =new GraphsPanel(frameColor);

		ButtonPanel buttons =new ButtonPanel();
		
		JPanel mainPanel =new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(graphs,BorderLayout.CENTER);
		mainPanel.add(buttons,BorderLayout.SOUTH);
		
		JPanel settingPanel =new JPanel();						//g��wna ramka
		settingPanel.setLayout(new BoxLayout(settingPanel,BoxLayout.Y_AXIS));
		settingPanel.add(new JLabel("Nastawy:",JLabel.CENTER));
		settingPanel.add(graphsSetttings);
		settingPanel.add(settings);
		settingPanel.add(hybridMatrix);
		
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Menu główne",mainPanel);
        tabbedPane.addTab("Menu ustawień",settingPanel);
        
        add(tabbedPane);
	}	
	

	public static void main(String[] args)  //klasa main przeznaczona do test�w wygl�du InterFace'u
	{
		InterFace frame = new InterFace(Color.blue);
		frame.setVisible(true);
	}
}
