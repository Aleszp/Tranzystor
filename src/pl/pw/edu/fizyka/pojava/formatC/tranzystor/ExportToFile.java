package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Localization;

/**
 * Class being used to export simulation results to file
 * @author Aleksander Szpakiewicz-Szatan
 */
public class ExportToFile
{
	DataContainer data;
	Simulation simulation;
	InterFace frame;
	JFileChooser chooser;
	int currentId;
	
	/**
	 * Use ExportToFile(InterFace,DataContainer,Simulation) as constructor
	 * @param frame_ - interface (which contains settings)
	 * @param data_ - data class with simulation results
	 * @param simulation_ - simulation that should be exported
	 */
	public ExportToFile(InterFace frame_,DataContainer data_, Simulation simulation_)
	{
		data=data_;
		simulation=simulation_;
		frame=frame_;
	}
	/**
	 * Use exportSingleCurrent(JFileChooser, int)
	 * @param chooser_ - JFileChooser that is used to determine to which file export current
	 * @param currentId_ - id of current where 0 stands for base current, 1 for collector current and 2 for emitter current
	 */
	public void exportSingleCurrent(JFileChooser chooser_,int currentId_)
	{
		chooser=chooser_;
		currentId=currentId_;
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()
		{
			protected Void doInBackground() throws Exception 
			{
				export();
				return null;
			}
		};
		worker.execute();
	}
	/**
	 * Use this method to add exportButton listener when everything is properly initialized
	 */
	public void addExportButtonListener()
	{
		ExportListener exportListener=new ExportListener(frame,this,frame.buttonPanel.exportButton);
		frame.buttonPanel.exportButton.addActionListener(exportListener);
	}
	/**
	 * Listener added to exportButton which makes program export simulation results.
	 * @author Aleksander Szpakiewicz-Szatan
	 */
	public class ExportListener implements ActionListener
	{ 
		InterFace frame;
		ExportToFile fileIO;
		ExportOptionsFrame exportFrame;
		ActivableButton thisButton;
		/**
		 * Use ExportListener(InterFace, Simulation, ExportToFile, JButton) as constructor
		 * @param frame_ - InterFace in which settings are held 
		 * @param fileIO_ - ExportToFile class used by its frame
		 * @param thisButton_
		 */
		public ExportListener(InterFace frame_, ExportToFile fileIO_,ActivableButton thisButton_)
		{
			frame=frame_;
			fileIO=fileIO_;
			thisButton=thisButton_;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(thisButton.getActive()==true)
				exportFrame=new ExportOptionsFrame(frame,fileIO);
		}
			
	}
	
	/**
	 * Use this method to make program export selected by user current into selected by user file in separate thread.
	 */
	public void export() 
	{
		try
		{
		    FileWriter writer = new FileWriter(chooser.getSelectedFile());
	 
		    writer.append(Localization.getString("Ube")+"\\"+Localization.getString("Uce")+",");
		    for(int ii=0;ii<data.collectorEmitterVoltageSteps;ii++)
		    	writer.append(data.getCollectorEmitterVoltage(ii)+",");
		    writer.append('\n');
	 
		    for(int jj=0;jj<data.baseEmitterVoltageSteps;jj++)
		    {
		    	writer.append(data.getBaseEmitterVoltage(jj)+",");
			    for(int ii=0;ii<data.collectorEmitterVoltageSteps;ii++)
			    	writer.append(data.getCurrent(ii,jj,currentId)+",");
			    writer.append('\n');
		    }
		    writer.flush();
		    writer.close();
		}
		catch(IOException e)
		{
		     e.printStackTrace();
		}
	}
};



