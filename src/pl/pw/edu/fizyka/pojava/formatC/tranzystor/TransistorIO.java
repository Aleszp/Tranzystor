package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Localization;

public class TransistorIO 
{
	InterFace frame;
	MatrixPanel hMatrix;
	ValuePanel maximumValuesPanel[];
	DialogFrame dialogFrame;
	
	
	public TransistorIO(InterFace frame_)
	{
		frame=frame_;
		hMatrix=frame.hybridMatrix;
		maximumValuesPanel=frame.maximumValuesSettingsPanel;
		dialogFrame=new DialogFrame(Localization.getString("chooseTransistor"),this);
		frame.buttons.loadButton.addActionListener(new LoadTransistorListener(dialogFrame));
	}
	
	void prepareArray(double h11Value,double h12Value,double h21Value,double h22Value)
	{
		hMatrix.setH(h11Value,0);
		hMatrix.setH(h12Value,1);
		hMatrix.setH(h21Value,2);
		hMatrix.setH(h22Value,3);
	}
	void prepareSaturations(double voltageValue, double currentValue)
	{
		hMatrix.setSaturationCurrent(currentValue);
		hMatrix.setSaturationVoltage(voltageValue);
	}
	void prepareMaximumValues(double maxVoltageCE, double maxVoltageBE, double maxVoltageCB, double maxCollectorCurrent, double maxBaseCurrent, double maxEmitterCurrent)
	{
		maximumValuesPanel[0].setValue(maxVoltageCE);
		maximumValuesPanel[1].setValue(maxVoltageBE);
		maximumValuesPanel[2].setValue(maxVoltageCB);
		maximumValuesPanel[3].setValue(maxCollectorCurrent);
		maximumValuesPanel[4].setValue(maxBaseCurrent);
		maximumValuesPanel[5].setValue(maxEmitterCurrent);
	}
	public class LoadTransistorListener implements ActionListener
	{
		DialogFrame dialogFrame;
		public LoadTransistorListener(DialogFrame dialogFrame_)
		{
			dialogFrame=dialogFrame_;
		}
		@Override
		public void actionPerformed(ActionEvent e)
		{
			//open dialog in which user chooses between custom and built-in transistors
			dialogFrame.setVisible(true);
		}
		
	};
	void LoadTransistor(String fileName)
	{
		Scanner scanner=new Scanner(fileName);
		scanner.useDelimiter(",");
		prepareArray(Double.valueOf(scanner.next()), Double.valueOf(scanner.next()), Double.valueOf(scanner.next()), Double.valueOf(scanner.next()));
        prepareSaturations(Double.valueOf(scanner.next()), Double.valueOf(scanner.next()));
        prepareMaximumValues(Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()));
        
		scanner.close();
	}
	void LoadDefaultTransistor(int transistorModel)
	{
		Scanner scanner=new Scanner(openInternalFileStream("BuiltInTransistors.csv"));
		scanner.useDelimiter(",");
		boolean repeat=true;
		System.out.println(transistorModel+"\n");
		while(scanner.hasNext()&&repeat)
		{
			if(Double.valueOf(scanner.next())==(double)transistorModel)
				break;
		}
		prepareArray(Double.valueOf(scanner.next()), Double.valueOf(scanner.next()), Double.valueOf(scanner.next()), Double.valueOf(scanner.next()));
        prepareSaturations(Double.valueOf(scanner.next()), Double.valueOf(scanner.next()));
        prepareMaximumValues(Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()));
		scanner.close();
	}
	
	InputStream openInternalFileStream(String fileName)
	{
	    return this.getClass().getResourceAsStream(fileName);
	}
}
class DialogFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	ValuePanel valuePanel;
	public DialogFrame(String title,TransistorIO transistorIO)
	{
		super(title);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setSize(320, 120);
		setMinimumSize(new Dimension(320,120));
		setLayout(new BorderLayout());
		String tranzistors[]={"BC107","BC159","BC177","BC527",Localization.getString("customTransistor")};
		valuePanel=new ValuePanel(Localization.getString("chooseTransistor"), tranzistors);
		valuePanel.unit.setSize(300, 20);
		add(valuePanel, BorderLayout.CENTER);
		
		JPanel buttonPanel= new JPanel(); 
		
		JButton readyButton=new JButton(Localization.getString("load"));
		buttonPanel.add(readyButton);
		readyButton.addActionListener(new LoadTranaistorReadyListener(transistorIO, this));
		
		JButton cancelButton=new CancelButton(Localization.getString("cancel"),this);
		buttonPanel.add(cancelButton,BorderLayout.SOUTH);
		add(buttonPanel,BorderLayout.SOUTH);
	}
	public class LoadTranaistorReadyListener implements ActionListener 
	{
		TransistorIO transistorIO;
		DialogFrame dialogFrame;
		public LoadTranaistorReadyListener(TransistorIO transistorIO_, DialogFrame dialogFrame_)
		{
			transistorIO=transistorIO_;
			dialogFrame=dialogFrame_;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			int transistors[]={107,159,177,527};
			int index=dialogFrame.valuePanel.getSelectedIndex();
			if(index<4)transistorIO.LoadDefaultTransistor(transistors[index]);
			else System.out.println("Not yet implemented");
			dialogFrame.setVisible(false);
		}
		
	}
}
