package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;


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
	public InterFace() throws HeadlessException 
	{
		super("Symulacja tranzystora");
		setSize(640,480);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new GridLayout());
	
		Dimension sizeMinimal = new Dimension();
		sizeMinimal.setSize(300, 400);
		
		Dimension sizeOfWindow = this.getSize();	
		Dimension graphSizePreffered = new Dimension();
		graphSizePreffered.setSize(sizeOfWindow.getWidth()/2-25,sizeOfWindow.getHeight()-150);//	
		
        Color frameColor=Color.blue; 					//zmienna koloru ramek
		
		String[] voltagesNames ={"Uce" ,"Ube" };
		String[] currentsNames ={"Ib","Ie","Ic"};

		String[] voltagesUnits ={"V" ,"mV"};
		String[] currentsUnits ={"A","mA"};
		
		/*********************************************/	//elementy panelu nastaw lewego graphu
		
		JComboBox<String> graph1OXComboBox = new JComboBox<String>(voltagesNames);
		JPanel graph1OXPanel =new JPanel();
		graph1OXPanel.add(new JLabel("OX"));						
		graph1OXPanel.add(graph1OXComboBox);
		
		JComboBox<String> graph1OYComboBox = new JComboBox<String>(currentsNames);
		JPanel graph1OYPanel =new JPanel();
		graph1OYPanel.add(new JLabel("OY"));
		graph1OYPanel.add(graph1OYComboBox);
		
		JPanel graph1ParameterPanel =new JPanel();
		JLabel graph1ParameterValueLabel =new JLabel(voltagesNames[1]);					//elmenty w wersji końcowje będą modyfkowane przez Listener
		JLabel graph1ParameterNameLabel =new JLabel("");
		JLabel graph1ParameterUnitLabel =new JLabel(voltagesUnits[0]);
		
		graph1ParameterPanel.add(graph1ParameterValueLabel);
		graph1ParameterPanel.add(graph1ParameterNameLabel);
		graph1ParameterPanel.add(graph1ParameterUnitLabel);	
		
		
		JPanel graph1Setting =new JPanel();					// panel nastaw lewego graphu
		graph1Setting.setBorder(new LineBorder(frameColor));
		graph1Setting.setLayout(new BoxLayout(graph1Setting,BoxLayout.Y_AXIS));
		graph1Setting.add(new JLabel("Wykres1",JLabel.CENTER));
		graph1Setting.add(graph1OXPanel);
		graph1Setting.add(graph1OYPanel);
		graph1Setting.add(graph1ParameterPanel);
		
		/********************/							//elemty panelu nastaw prawego wykresu
		
		JComboBox<String> graph2OXComboBox = new JComboBox<String>(voltagesNames);
		JPanel graph2OXPanel =new JPanel();
		graph2OXPanel.add(new JLabel("OX"));
		graph2OXPanel.add(graph2OXComboBox);
		
		JComboBox<String> graph2OYComboBox = new JComboBox<String>(currentsNames);
		JPanel graph2OYPanel =new JPanel();
		graph2OYPanel.add(new JLabel("OY"));
		graph2OYPanel.add(graph2OYComboBox);
		
		JLabel graph2ParameterValueLabel =new JLabel(voltagesNames[1]);					//elementy w wersji końcowej będą modyfkowane przez Listener
		JLabel graph2ParameterNameLabel =new JLabel("");
		JLabel graph2ParameterUnitLabel =new JLabel(voltagesUnits[0]);
		JPanel graph2ParameterPanel =new JPanel();
		graph2ParameterPanel.add(graph2ParameterValueLabel);
		graph2ParameterPanel.add(graph2ParameterNameLabel);
		graph2ParameterPanel.add(graph2ParameterUnitLabel);
		
		
		JPanel graph2Setting =new JPanel();					// panel nastaw prawego wykresu
		graph2Setting.setBorder(new LineBorder(frameColor));
		graph2Setting.setLayout(new BoxLayout(graph2Setting,BoxLayout.Y_AXIS));
		graph2Setting.add(new JLabel("Wykres2",JLabel.CENTER));
		graph2Setting.add(graph2OXPanel);
		graph2Setting.add(graph2OYPanel);
		graph2Setting.add(graph2ParameterPanel);
		
		/********************/
		
		JPanel graphsSetttings =new JPanel();						//element okreslajcy polozenie nastaw wykrsów
		graphsSetttings.add(graph1Setting);
		graphsSetttings.add(graph2Setting);
		
		/* Elementy nastaw panelu */
		
		JTextField voltage1StartValueInput= new JTextField(3);
		JComboBox<String> voltage1StartValueLabel = new JComboBox<String>(voltagesUnits);
		JPanel voltage1StartPanel =new JPanel();
		voltage1StartPanel.add(new JLabel("Wartość początkowa"));
		voltage1StartPanel.add(voltage1StartValueInput);
		voltage1StartPanel.add(voltage1StartValueLabel);

		JTextField voltage1StepsInput= new JTextField(3);
		JPanel voltage1StepsPanel =new JPanel();
		voltage1StepsPanel.add(new JLabel("Liczba kroków"));
		voltage1StepsPanel.add(voltage1StepsInput);
		
		JTextField ku= new JTextField(3);
		JComboBox<String> voltage1EndValueUnits = new JComboBox<String>(voltagesUnits);
		JPanel voltage1EndValuePanel =new JPanel();
		voltage1EndValuePanel.add(new JLabel("Wartość końcowa"));
		voltage1EndValuePanel.add(ku);
		voltage1EndValuePanel.add(voltage1EndValueUnits);
		
		JPanel voltage1SettingsPanel =new JPanel();	
		JComboBox<String> voltage1SettingsVoltageName = new JComboBox<String>(voltagesNames);
		voltage1SettingsPanel.add(voltage1SettingsVoltageName);
		
		//
		
		JPanel settings1 =new JPanel();					//lewy panel ustawien
		settings1.setBorder(new LineBorder(frameColor));
		settings1.setLayout(new BoxLayout(settings1,BoxLayout.Y_AXIS));
		settings1.add(voltage1SettingsPanel);
		settings1.add(voltage1StartPanel);
		settings1.add(voltage1StepsPanel);
		settings1.add(voltage1EndValuePanel);
		
		/********************/
		
		JTextField colectorBaseVoltageMaximialValueInput= new JTextField(3);
		JComboBox<String> colectorBaseVoltageMaximialValueUnit = new JComboBox<String>(voltagesUnits);
		JPanel colectorBaseVoltageMaximialValuePanel =new JPanel();
		colectorBaseVoltageMaximialValuePanel.add(new JLabel("Ucemax"));
		colectorBaseVoltageMaximialValuePanel.add(colectorBaseVoltageMaximialValueInput);
		colectorBaseVoltageMaximialValuePanel.add(colectorBaseVoltageMaximialValueUnit);

		JTextField baseEmitterVoltageMaximialValueInput= new JTextField(3);
		JComboBox<String> baseEmitterVoltageMaximialValueUnit = new JComboBox<String>(voltagesUnits);
		JPanel baseEmitterVoltageMaximialValuePanel =new JPanel();
		baseEmitterVoltageMaximialValuePanel.add(new JLabel("Ubemax"));
		baseEmitterVoltageMaximialValuePanel.add(baseEmitterVoltageMaximialValueInput);
		baseEmitterVoltageMaximialValuePanel.add(baseEmitterVoltageMaximialValueUnit);

		JTextField collectorBaseVoltageMaximialValueInput= new JTextField(3);
		JComboBox<String> collectorBaseVoltageMaximialValueUnit = new JComboBox<String>(voltagesUnits);
		JPanel collectorBaseVoltageMaximialValuePanel =new JPanel();
		collectorBaseVoltageMaximialValuePanel.add(new JLabel("Ucbmax"));
		collectorBaseVoltageMaximialValuePanel.add(collectorBaseVoltageMaximialValueInput);
		collectorBaseVoltageMaximialValuePanel.add(collectorBaseVoltageMaximialValueUnit);
		
		JTextField collectorCurrentMaximialValueInput= new JTextField(3);
		JComboBox<String> collectorCurrentMaximialValueUnit = new JComboBox<String>(currentsUnits);
		JPanel collectorCurrentMaximialValuePanel =new JPanel();
		collectorCurrentMaximialValuePanel.add(new JLabel("Icmax"));
		collectorCurrentMaximialValuePanel.add(collectorCurrentMaximialValueInput);
		collectorCurrentMaximialValuePanel.add(collectorCurrentMaximialValueUnit);
		
		JTextField baseCurrentMaximialValueInput= new JTextField(3);
		JComboBox<String> baseCurrentMaximialValueUnit = new JComboBox<String>(currentsUnits);
		JPanel baseCurrentMaximialValuePanel =new JPanel();
		baseCurrentMaximialValuePanel.add(new JLabel("Ibmax"));
		baseCurrentMaximialValuePanel.add(baseCurrentMaximialValueInput);
		baseCurrentMaximialValuePanel.add(baseCurrentMaximialValueUnit);
		
		JTextField emitterCurrentMaximialValueInput= new JTextField(3);
		JComboBox<String> emitterCurrentMaximialValueUnit = new JComboBox<String>(currentsUnits);
		JPanel emitterCurrentMaximialValuePanel =new JPanel();
		emitterCurrentMaximialValuePanel.add(new JLabel("Icmax"));
		emitterCurrentMaximialValuePanel.add(emitterCurrentMaximialValueInput);
		emitterCurrentMaximialValuePanel.add(emitterCurrentMaximialValueUnit);
		
		JPanel settings2 =new JPanel();					//prawy panel ustawien
		settings2.setBorder(new LineBorder(frameColor));
		settings2.setLayout(new BoxLayout(settings2,BoxLayout.Y_AXIS));
		settings2.add(colectorBaseVoltageMaximialValuePanel);
		settings2.add(baseEmitterVoltageMaximialValuePanel);
		settings2.add(collectorBaseVoltageMaximialValuePanel);
		settings2.add(collectorCurrentMaximialValuePanel);
		settings2.add(baseCurrentMaximialValuePanel);
		settings2.add(emitterCurrentMaximialValuePanel);
		
		/********************/
		
		JPanel settings =new JPanel();						//elnent okreslajcy polozenie ustawien
		settings.add(settings1);
		settings.add(settings2);
		
		/*********************************************/

		JTextField h11TextField= new JTextField(3);
		JPanel h11Panel =new JPanel();
		h11Panel.add(new JLabel("H11"));
		h11Panel.add(h11TextField);

		JTextField h21TextField= new JTextField(3);
		JPanel h21Panel =new JPanel();
		h21Panel.add(new JLabel("H21"));
		h21Panel.add(h21TextField);
		
		JPanel hybridMatrixRow1 =new JPanel();
		hybridMatrixRow1.setLayout(new BoxLayout(hybridMatrixRow1,BoxLayout.Y_AXIS));
		hybridMatrixRow1.add(h11Panel);
		hybridMatrixRow1.add(h21Panel);
		
		/********************/

		JTextField h12TextField= new JTextField(3);
		JPanel h12Panel =new JPanel();
		h12Panel.add(new JLabel("H12"));
		h12Panel.add(h12TextField);
		
		JTextField h22TextField= new JTextField(3);
		JPanel h22Panel =new JPanel();
		h22Panel.add(new JLabel("H22"));
		h22Panel.add(h22TextField);

		JPanel hybridMatrixRow2 =new JPanel();
		hybridMatrixRow2.setLayout(new BoxLayout(hybridMatrixRow2,BoxLayout.Y_AXIS));
		hybridMatrixRow2.add(h12Panel);
		hybridMatrixRow2.add(h22Panel);
		
		JPanel hybridMatrix =new JPanel();
		hybridMatrix.setBorder(new LineBorder(frameColor));
		hybridMatrix.add(hybridMatrixRow1);
		hybridMatrix.add(hybridMatrixRow2);
		
		/*********************************************/

		JPanel graph1 =new JPanel();					// lewegy panel wykresu
		graph1.setMinimumSize(sizeMinimal);
		graph1.setPreferredSize(graphSizePreffered);
		graph1.setBorder(new LineBorder(frameColor));
		
		JPanel graph2 =new JPanel();					// prawegy panel graphu
		graph2.setMinimumSize(sizeMinimal);
		graph2.setPreferredSize(graphSizePreffered);
		graph2.setBorder(new LineBorder(frameColor));
		
		JPanel graphs =new JPanel();					//elnent okreslajcy polozenie wykrsow
		graphs.add(graph1);
		graphs.add(graph2);
		graphs.setLayout(new GridLayout());
		
		/*********************************************/
		
		
		JButton loadButton = new JButton("Wczytaj Tranzystor");	
		JButton saveButton = new JButton("Zapisz tranzystor");
		JButton saveResultsButton = new JButton("Zapisz wyniki");
		JButton startStopButton = new JButton("Zacznij/Zakończ symulację");  
		//w gotowej wersji programu będzie zmieniać nazwę zależnie od stanu symulacji na 
		//"Zacznij symulację" dla nieaktywnej i "Zakończ symulację" dla aktywnej
		
		JPanel buttons =new JPanel();	
		buttons.add(loadButton);
		buttons.add(saveButton);
		buttons.add(saveResultsButton);
		buttons.add(startStopButton);
		
		/*********************************************/

		JPanel mainPanel =new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		mainPanel.add(graphs);
		mainPanel.add(buttons);
		
		JPanel settingPanel =new JPanel();						//główna ramka
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

	public static void main(String[] args)  //klasa main przeznaczona do testów wyglądu InterFace'u
	{
		InterFace frame = new InterFace();
		frame.setVisible(true);
	}
}
