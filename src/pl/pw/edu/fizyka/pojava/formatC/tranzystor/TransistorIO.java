package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

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

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Localization;
/**
 * Class used to save and load transistor parameters.
 * @author Aleksander Szpakiewicz-Szatan
 */
public class TransistorIO
{
	InterFace frame;
	MatrixPanel hMatrix;
	ValuePanel maximumValuesPanel[];
	TranzistorLoadDialogFrame dialogFrame;
	
	/**
	 * Use TransistorIO(InterFace) as constructor
	 * @param frame_ - interface in which are settings
	 */
	public TransistorIO(InterFace frame_)
	{
		frame=frame_;
		hMatrix=frame.hybridMatrix;
		maximumValuesPanel=frame.maximumValuesSettingsPanel;
		dialogFrame=new TranzistorLoadDialogFrame(Localization.getString("chooseTransistor"),this);
		frame.buttonPanel.loadButton.addActionListener(new LoadTransistorListener(dialogFrame,frame.buttonPanel));
		frame.buttonPanel.saveButton.addActionListener(new SaveTransistorListener(this));
	}
	/**
	 * Use setHMatrix(double, double, double, double) to prepare h-matrix array
	 * @param h11Value - h11 matrix's value
	 * @param h12Value - h12 matrix's value
	 * @param h21Value - h21 matrix's value
	 * @param h22Value - h22 matrix's value
	 */
	void setHMatrix(double h11Value,double h12Value,double h21Value,double h22Value)
	{
		hMatrix.setH(h11Value,0);
		hMatrix.setH(h12Value,1);
		hMatrix.setH(h21Value,2);
		hMatrix.setH(h22Value,3);
	}
	/**
	 * Use setSaturationValues(double, double) to prepare saturations values
	 * @param voltageValue - saturation voltage value
	 * @param currentValue - saturation current value
	 */
	void setSaturationValues(double voltageValue, double currentValue)
	{
		hMatrix.setSaturationCurrent(currentValue);
		hMatrix.setSaturationVoltage(voltageValue);
	}
	/**
	 * Use setSaturationValues(double, double, double, double, double, double) to prepare saturations values
	 * @param maxVoltageCE - maximal allowed collector-emitter voltage
	 * @param maxVoltageBE - maximal allowed base-emitter voltage
	 * @param maxVoltageCB - maximal allowed collector-base voltage
	 * @param maxCollectorCurrent -maximal allowed collector current
	 * @param maxBaseCurrent -maximal allowed base current
	 * @param maxEmitterCurrent -maximal allowed emitter current
	 */
	void setMaximumValues(double maxVoltageCE, double maxVoltageBE, double maxVoltageCB, double maxCollectorCurrent, double maxBaseCurrent, double maxEmitterCurrent)
	{
		maximumValuesPanel[0].setValue(maxVoltageCE);
		maximumValuesPanel[1].setValue(maxVoltageBE);
		maximumValuesPanel[2].setValue(maxVoltageCB);
		maximumValuesPanel[3].setValue(maxCollectorCurrent);
		maximumValuesPanel[4].setValue(maxBaseCurrent);
		maximumValuesPanel[5].setValue(maxEmitterCurrent);
	}
	/**
	 * Listener used when user wants to load transistor
	 */
	public class LoadTransistorListener implements ActionListener
	{
		TranzistorLoadDialogFrame dialogFrame;
		ButtonPanel buttonPanel;
		/**
		 * Use LoadTransistorListener(TranzistorLoadDialogFrame, ButtonPanel) as constructor
		 * @param dialogFrame_ - frame with transistors options that should be shown
		 * @param buttonPanel_ - buttonPanel with button to which this listener is added (checks whether button is allowed to open dialogFrame)
		 */
		public LoadTransistorListener(TranzistorLoadDialogFrame dialogFrame_, ButtonPanel buttonPanel_)
		{
			dialogFrame=dialogFrame_;
			buttonPanel=buttonPanel_;
		}
		@Override
		/**
		 * Opens dialogFrame in which user chooses between custom and built-in transistors, not allowed if simulations already is working (which is shown by buttons background set to inactive
		 */
		public void actionPerformed(ActionEvent e)
		{
			if(buttonPanel.loadButton.getActive()==true)
				dialogFrame.setVisible(true);
		}
	};
	/**
	 * Listener used when user wants to save transistor
	 */
	public class SaveTransistorListener implements ActionListener
	{
		TransistorIO transistorIO;
		/**
		 * Use SaveTransistorListener(TransistorIO, ButtonPanel) as constructor
		 * @param transistorIO_ - transistorIO class that is made save transistor
		 */
		public SaveTransistorListener(TransistorIO transistorIO_)
		{
			transistorIO=transistorIO_;
		}
		@Override
		/**
		 * Opens dialogFrame in which user chooses where to save transistor, 
		 * 
		 */
		public void actionPerformed(ActionEvent e) 
		{
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()
			{
				protected Void doInBackground() throws Exception 
				{
					transistorIO.save();
					return null;
				}
			};
			worker.execute();
		}
	}
	/**
	 * Use LoadTransistor(File) to load transistor from file
	 * @param file - file from which transistor should be loaded
	 * @throws IOException - when there is exception while accessing the file
	 */
	void LoadTransistor(File file) throws IOException
	{
		Scanner scanner=new Scanner(file);
		scanner.useDelimiter(",");
		setHMatrix(Double.valueOf(scanner.next()), Double.valueOf(scanner.next()), Double.valueOf(scanner.next()), Double.valueOf(scanner.next()));
        setSaturationValues(Double.valueOf(scanner.next()), Double.valueOf(scanner.next()));
        setMaximumValues(Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()));
        
		scanner.close();
	}
	/**
	 * Use SaveTransistor(File) to save transistor to file
	 * @param file - file to which transistor should be saved
	 */
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
				bufferedWriter.write(maximumValuesPanel[ii].getValue()+",");
			bufferedWriter.flush();
			bufferedWriter.close();	
		} 
		catch (IOException e) 
		{	
			e.printStackTrace();
		}		
	}
	/**
	 * Use LoadDefaultTransistor(int) to load predefined transistor
	 * @param transistorModel - transistor id, allowed entries: 107 (BC107), 159 (BC159), 177 (BC177), 527 (BC527) if other entry is typed default BC107 will be loaded 
	 * @throws IOException - when there is exception while accessing the file
	 */
	void LoadDefaultTransistor(int transistorModel)
	{
		if(transistorModel!=107&&transistorModel!=159&&transistorModel!=177&&transistorModel!=527)
			transistorModel=107;
		Scanner scanner=new Scanner(openInternalFileStream("BuiltInTransistors.csv"));
		scanner.useDelimiter(",");
		boolean repeat=true;
		while(scanner.hasNext()&&repeat)
		{
			if(Double.valueOf(scanner.next())==(double)transistorModel)
				break;
		}
		setHMatrix(Double.valueOf(scanner.next()), Double.valueOf(scanner.next()), Double.valueOf(scanner.next()), Double.valueOf(scanner.next()));
        setSaturationValues(Double.valueOf(scanner.next()), Double.valueOf(scanner.next()));
        setMaximumValues(Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()),Double.valueOf(scanner.next()));
		scanner.close();
	}
	/**
	 * Use openInternalFileStream(String) to open file inside jar as a stream
	 * @param fileName - name (or path if in subdirectory) of file that should be opened
	 * @return stream made from chosen file
	 */
	InputStream openInternalFileStream(String fileName)
	{
	    return this.getClass().getResourceAsStream(fileName);
	}
	
	/**
	 * Let user choose file to which save file and save it. If user doesn't choose any file do nothing
	 */
	public void save() 
	{
		File chosenFile=chooseFile(Localization.getString("chooseSaveTransistor"),Localization.getString("save"),frame);
		if(chosenFile!=null)
			SaveTransistor(chosenFile);
	}
	/**
	 * Use chooseFile(String,String,JFrame)
	 * @param title - title of JFileChooser
	 * @param accept - label on accepting label of JFileChooser
	 * @param parent - frame that will be parent for JFileChooser
	 * @return File that was chosen by user. If no file was chosen returns null.
	 */
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

