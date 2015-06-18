package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Localization;

/**
 * Class being used to export simulation results to file
 * @author Aleksander Szpakiewicz-Szatan
 */
public class ExportToFile implements Runnable
{
	DataContainer data;
	Simulation simulation;
	InterFace frame;
	static Thread thread;
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
		frame.buttonPanel.exportButton.addActionListener(new ExportListener(frame, simulation,this,frame.buttonPanel.exportButton));
	}
	public void exportSingleCurrent(JFileChooser chooser_,int currentId_)
	{
		chooser=chooser_;
		currentId=currentId_;
		run();
	}
	public void exportCurrentsForSingleVoltage(JFileChooser chooser,int voltageId, int parameterId)
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
		ExportToFile fileIO;
		ExportOptionsFrame exportFrame;
		JButton thisButton;
		
		public ExportListener(InterFace frame_,Simulation simulation_, ExportToFile fileIO_,JButton thisButton_)
		{
			frame=frame_;
			simulation=simulation_;
			fileIO=fileIO_;
			thisButton=thisButton_;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(thisButton.getBackground()==frame.buttonPanel.activeColor)
				exportFrame=new ExportOptionsFrame(frame,fileIO);
		}
			
	}

	@Override
	public void run() 
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
};


class ExportOptionsFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	JScrollPane scrollablePane;
	JPanel masterPanel;
	ValuePanel vPanel;
	int decision;
	InterFace frame;
	ExportToFile fileIO;
	
	ExportOptionsFrame(InterFace frame_,ExportToFile fileIO_)
	{
		super(Localization.getString("exportOptions"));
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		frame=frame_;
		fileIO=fileIO_;
		decision=-1;
		setSize(320,240);
		setVisible(true);
		
		masterPanel = new JPanel();
		masterPanel.setLayout(new BorderLayout());
		scrollablePane=new JScrollPane(masterPanel);
		add(scrollablePane);	
		vPanel=new ValuePanel(Localization.getString("current"), frame.currentsNames);
		masterPanel.add(vPanel,BorderLayout.CENTER);
		
		JPanel buttonPanel= new JPanel(); 
		
		JButton readyButton=new JButton(Localization.getString("next"));
		buttonPanel.add(readyButton);
		readyButton.addActionListener(new ExportReadyListener(this,fileIO));
		
		JButton cancelButton=new CancelButton(Localization.getString("cancel"),this);
		buttonPanel.add(cancelButton,BorderLayout.SOUTH);
		masterPanel.add(buttonPanel,BorderLayout.SOUTH);
		
	}
	public class ExportReadyListener implements ActionListener
	{
		ExportOptionsFrame exportFrame;
		ExportToFile fileIO;
		public ExportReadyListener(ExportOptionsFrame exportFrame_, ExportToFile fileIO_)
		{
			exportFrame=exportFrame_;
			fileIO=fileIO_;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			exportFrame.setVisible(false);
			exportFrame.checkDecision();
			JFileChooser chooser=new JFileChooser();
		  	chooser.setDialogTitle(Localization.getString("chooseExportFile"));
		  	chooser.setFileFilter(new FileNameExtensionFilter(Localization.getString("csv"), "csv"));
		  	
		  	int result = chooser.showDialog(frame,Localization.getString("export"));  	
		  	if(result==JFileChooser.APPROVE_OPTION)
		  	{
		  		if(exportFrame.getDecision()==0) //Ib
		  			fileIO.exportSingleCurrent(chooser,0);
		  		if(exportFrame.getDecision()==1) //Ie
		  			fileIO.exportSingleCurrent(chooser,2);
		  		if(exportFrame.getDecision()==2) //Ic
		  			fileIO.exportSingleCurrent(chooser,1);
		  	}
			
		};
	};
	public int getDecision()
	{
		return decision;
	}
	public void checkDecision()
	{
		decision=vPanel.getSelectedIndex();
	}
};



