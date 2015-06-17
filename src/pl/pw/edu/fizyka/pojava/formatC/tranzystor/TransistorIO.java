package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Localization;

public class TransistorIO implements Runnable
{
	InterFace frame;
	MatrixPanel hMatrix;
	ValuePanel maximumValuesPanel[];
	TranzistorLoadDialogFrame dialogFrame;
	
	
	public TransistorIO(InterFace frame_)
	{
		frame=frame_;
		hMatrix=frame.hybridMatrix;
		maximumValuesPanel=frame.maximumValuesSettingsPanel;
		dialogFrame=new TranzistorLoadDialogFrame(Localization.getString("chooseTransistor"),this);
		frame.buttonPanel.loadButton.addActionListener(new LoadTransistorListener(dialogFrame,frame.buttonPanel));
		frame.buttonPanel.saveButton.addActionListener(new SaveTransistorListener(this,frame.buttonPanel));
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
		TranzistorLoadDialogFrame dialogFrame;
		ButtonPanel buttonPanel;
		public LoadTransistorListener(TranzistorLoadDialogFrame dialogFrame_, ButtonPanel buttonPanel_)
		{
			dialogFrame=dialogFrame_;
			buttonPanel=buttonPanel_;
		}
		@Override
		public void actionPerformed(ActionEvent e)
		{
			//open dialog in which user chooses between custom and built-in transistors, not if simulations already was started
			if(buttonPanel.loadButton.getBackground()!=buttonPanel.inactiveColor)
				dialogFrame.setVisible(true);
		}
		
	};
	public class SaveTransistorListener implements ActionListener
	{
		TransistorIO transistorIO;
		ButtonPanel buttonPanel;
		public SaveTransistorListener(TransistorIO transistorIO_,ButtonPanel buttonPanel_)
		{
			transistorIO=transistorIO_;
			buttonPanel=buttonPanel_;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(buttonPanel.saveButton.getBackground()!=transistorIO.frame.buttonPanel.inactiveColor)
				transistorIO.run();
		}
	}
	void LoadTransistor(File file) throws IOException
	{
		Scanner scanner=new Scanner(file);
		scanner.useDelimiter(",");
		prepareArray(Double.valueOf(scanner.next()), Double.valueOf(scanner.next()), Double.valueOf(scanner.next()), Double.valueOf(scanner.next()));
        prepareSaturations(Double.valueOf(scanner.next()), Double.valueOf(scanner.next()));
        prepareMaximumValues(Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()));
        
		scanner.close();
	}
	void SaveTransistor(File file)
	{
		try 
		{
			BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()), Charset.forName("UTF-8")));
			for(int ii=0;ii<4;ii++)
				bufferedWriter.write(hMatrix.getH(ii)+",");
			bufferedWriter.write(hMatrix.getSaturationCurrent()+",");
			bufferedWriter.write(hMatrix.getSaturationVoltage()+",");
			for(int ii=0;ii<6;ii++)
				bufferedWriter.write(maximumValuesPanel[0].getValue()+",");
			bufferedWriter.flush();
			bufferedWriter.close();	
		} 
		catch (IOException e) 
		{	
			e.printStackTrace();
		}		
	}
	void LoadDefaultTransistor(int transistorModel)
	{
		Scanner scanner=new Scanner(openInternalFileStream("BuiltInTransistors.csv"));
		scanner.useDelimiter(",");
		boolean repeat=true;
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

	@Override
	public void run() 
	{
		File chosenFile=chooseFile(Localization.getString("chooseCustomTransistor"),Localization.getString("load"),frame);
		if(chosenFile!=null)
			SaveTransistor(chosenFile);
	}
	public File chooseFile(String title, String accept, JFrame parent)
	{
		File chosenFile=null;
		JFileChooser chooser=new JFileChooser();
	  	chooser.setDialogTitle(title);
	  	chooser.setFileFilter(new FileNameExtensionFilter(Localization.getString("csv"), "csv"));
	  	
	  	int result = chooser.showDialog(parent,accept);  	
	  	if(result==JFileChooser.APPROVE_OPTION)
	  		chosenFile=chooser.getSelectedFile();
	  	return chosenFile;
	}
	
}
class TranzistorLoadDialogFrame extends JFrame implements Runnable
{
	private static final long serialVersionUID = 1L;
	ValuePanel valuePanel;
	TransistorIO transistorIO;
	public TranzistorLoadDialogFrame(String title,TransistorIO transistorIO_)
	{
		super(title);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		transistorIO=transistorIO_;
		setSize(320, 120);
		setMinimumSize(new Dimension(320,120));
		setLayout(new BorderLayout());
		
		String tranzistors[]={"BC107 "+Localization.getString("default"),"BC159","BC177","BC527",Localization.getString("customTransistor")};
		valuePanel=new ValuePanel(Localization.getString("chooseTransistor"), tranzistors);
		valuePanel.unit.setSize(300, 20);
		add(valuePanel, BorderLayout.CENTER);
		
		JPanel buttonPanel= new JPanel(); 
		JButton readyButton=new JButton(Localization.getString("load"));
		buttonPanel.add(readyButton);
		readyButton.addActionListener(new LoadTranaistorReadyListener(this));
		
		JButton cancelButton=new CancelButton(Localization.getString("cancel"),this);
		buttonPanel.add(cancelButton,BorderLayout.SOUTH);
		add(buttonPanel,BorderLayout.SOUTH);
	}
	public class LoadTranaistorReadyListener implements ActionListener 
	{
		TransistorIO transistorIO;
		TranzistorLoadDialogFrame dialogFrame;
		public LoadTranaistorReadyListener(TranzistorLoadDialogFrame dialogFrame_)
		{
			dialogFrame=dialogFrame_;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			dialogFrame.run();
		}
		
	}
	@Override
	public void run() 
	{
		int transistors[]={107,159,177,527};
		int index=valuePanel.getSelectedIndex();
		if(index<4)
			transistorIO.LoadDefaultTransistor(transistors[index]);
		else 
		{
			//System.out.println("Not yet implemented");
			File chosenFile=transistorIO.chooseFile(Localization.getString("chooseCustomTransistor"),Localization.getString("load"),this);
			if(chosenFile!=null)
			try 
			{
				transistorIO.LoadTransistor(chosenFile);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		setVisible(false);
	}
}
