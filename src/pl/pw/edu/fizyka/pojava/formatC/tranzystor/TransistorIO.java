package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JFrame;

import pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Localization;

public class TransistorIO 
{
	InterFace frame;
	MatrixPanel hMatrix;
	ValuePanel vPanel[];
	JFrame dialogFrame;
	
	
	public TransistorIO(InterFace frame_)
	{
		frame=frame_;
		hMatrix=frame.hybridMatrix;
		vPanel=frame.maximumValuesSettingsPanel;
		frame.buttons.loadButton.addActionListener(new LoadTransistorListener());
		
		dialogFrame=new JFrame(Localization.getString("chooseTransistor"));
		dialogFrame.setSize(320, 240);
		String tranzistors[]={"BC107","BC159","BC177","BC527"};
		ValuePanel vPanel=new ValuePanel(Localization.getString("chooseTransistor"), tranzistors);
		dialogFrame.add(vPanel);
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
		vPanel[0].setValue(maxVoltageCE);
		vPanel[1].setValue(maxVoltageBE);
		vPanel[2].setValue(maxVoltageCB);
		vPanel[3].setValue(maxCollectorCurrent);
		vPanel[4].setValue(maxBaseCurrent);
		vPanel[5].setValue(maxEmitterCurrent);
	}
	public class LoadTransistorListener implements ActionListener
	{
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
