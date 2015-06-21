package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Localization;

/**
 * Class that is graphical part of ExportToFile class.
 * @author Aleksander Szpakiewicz-Szatan
 */
public class ExportOptionsFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	JScrollPane scrollablePane;
	JPanel masterPanel;
	ValuePanel vPanel;
	int decision;
	InterFace frame;
	ExportToFile fileIO;
	 /**
	  * Use ExportOptionsFrame(InterFeca,ExportToFile) as constructor
	  * @param frame_ - InterFace that contains user chosen data
	  * @param fileIO_ - ExportToFile class for which this frame works as GUI
	  */
	public ExportOptionsFrame(InterFace frame_,ExportToFile fileIO_)
	{
		super(Localization.getString("exportOptions"));
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		frame=frame_;
		fileIO=fileIO_;
		decision=-1;
		setSize(320,144);
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
	/**
	 * Listener for exportReadyButton.
	 */
	public class ExportReadyListener implements ActionListener
	{
		ExportOptionsFrame exportFrame;
		ExportToFile fileIO;
		/**
		 * Use ExportReadyListener(ExportOptionsFrame,ExportToFile) as constructor
		 * @param exportFrame_ - ExportOptionsFrame which should be made visible
		 * @param fileIO_ - ExportToFile class for which it should choose current to export
		 */
		public ExportReadyListener(ExportOptionsFrame exportFrame_, ExportToFile fileIO_)
		{
			exportFrame=exportFrame_;
			fileIO=fileIO_;
		}
		
		@Override
		/**
		 * Shows dialog in which user may choose which current to export
		 */
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
	/**
	 * Use getDecision() to get which current was chosen.
	 * @return id of chosen current
	 */
	public int getDecision()
	{
		return decision;
	}
	/**
	 * Use checkDecision() to make program check which current was chosen
	 */
	public void checkDecision()
	{
		decision=vPanel.getSelectedIndex();
	}
};