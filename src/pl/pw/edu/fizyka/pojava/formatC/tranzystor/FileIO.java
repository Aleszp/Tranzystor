package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FileIO 
{
	Data data;
	Simulation simulation;
	public FileIO(InterFace frame,Data data_, Simulation simulation_)
	{
		data=data_;
		simulation=simulation_;
		frame.buttons.exportButton.addActionListener(new ExportListener(frame, simulation));
	}
	public void exportSingleCurrent(JFileChooser chooser,int currentId)
	{
		try
		{
		    FileWriter writer = new FileWriter(chooser.getSelectedFile());
	 
		    writer.append(Localization.getString("Ube")+"\\"+Localization.getString("Uce")+",");
		    for(int ii=0;ii<data.collectorEmitterVoltegeSteps;ii++)
		    	writer.append(data.getCollectorEmitterVoltage(ii)+",");
		    writer.append('\n');
	 
		    for(int jj=0;jj<data.baseEmitterVoltegeSteps;jj++)
		    {
		    	writer.append(data.getBaseEmitterVoltage(jj)+",");
			    for(int ii=0;ii<data.collectorEmitterVoltegeSteps;ii++)
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
	public void exportCurrentsForSingleVoltage(File file, JFileChooser chooser,int voltageId, int parameterId)
	{
		try
		{
		    FileWriter writer = new FileWriter(chooser.getSelectedFile());
		    int collectorEmitterStep=0;
		    int baseEmitterStep=0;
		    int maxSteps=0;
		    
		    if(parameterId==0)
		    {
		    	collectorEmitterStep=parameterId;
		    	maxSteps=data.baseEmitterVoltegeSteps;
		    }
		    else
		    {
		    	baseEmitterStep=parameterId;
		    	maxSteps=data.collectorEmitterVoltegeSteps;
		    }
		    
		    for(int ii=0;ii<maxSteps;ii++)
		    {
		    	if(parameterId==0)
		    	{
		    		writer.append(data.getBaseEmitterVoltage(ii)+",");
		    		baseEmitterStep=ii;
		    	}
		    	else
		    	{
		    		writer.append(data.getCollectorEmitterVoltage(ii)+",");
		    		collectorEmitterStep=ii;
		    	}
			    for(int jj=0;jj<3;jj++)
			    	writer.append(data.getCurrent(collectorEmitterStep,baseEmitterStep,jj)+",");
				
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
	public void dummy()
	{
		System.out.println(""); //just make sure FileIO is initialized
	}
	
	public class ExportListener implements ActionListener
	{ 
		InterFace frame;
		Simulation simulation;
		public ExportListener(InterFace frame_,Simulation simulation_)
		{
			frame=frame_;
			simulation=simulation_;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
		  	JFileChooser chooser=new JFileChooser();
		  	chooser.setDialogTitle(Localization.getString("chooseFile"));
		  	int result = chooser.showDialog(frame,Localization.getString("export"));  	
		  	if(result==JFileChooser.APPROVE_OPTION)
		  	{
		  		exportSingleCurrent(chooser,0);
		  	}
		}
			
	}
};


class ExportOptionsFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	JPanel Panel;
	
	ExportOptionsFrame()
	{
		super(Localization.getString("exportOptions"));
		setSize(320,240);
		setVisible(true);
		
	}
	
};



