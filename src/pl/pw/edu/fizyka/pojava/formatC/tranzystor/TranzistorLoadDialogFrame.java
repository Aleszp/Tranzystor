package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Localization;

/**
 * Dialog frame used while loading transistor, extension of TransistorIO class 
 * @author Aleksander Szpakiewicz-Szatan
 */
public class TranzistorLoadDialogFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	ValuePanel valuePanel;
	TransistorIO transistorIO;
	Thread thread;
	boolean load;
	/**
	 * Use TranzistorLoadDialogFrame(String,TransistorIO) as constructor
	 * @param title - title that dialogFrame should display
	 * @param transistorIO_ - transistorIO class that contain some data used for file interaction
	 */
	public TranzistorLoadDialogFrame(String title,TransistorIO transistorIO_)
	{
		super(title);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		load=false;
		transistorIO=transistorIO_;
		setSize(320, 120);
		setMinimumSize(new Dimension(320,120));
		setLayout(new BorderLayout());
		
		String tranzistors[]={"BC107 "+Localization.getString("default"),"BC159","BC177","BC527",Localization.getString("customTransistor")};
		valuePanel=new ValuePanel(Localization.getString("chooseTransistor"), tranzistors);
		valuePanel.comboBox.setSize(300, 20);
		add(valuePanel, BorderLayout.CENTER);
		
		JPanel buttonPanel= new JPanel(); 
		JButton readyButton=new JButton(Localization.getString("load"));
		buttonPanel.add(readyButton);
		readyButton.addActionListener(new LoadTransistorReadyListener(this));
		
		JButton cancelButton=new CancelButton(Localization.getString("cancel"),this);
		buttonPanel.add(cancelButton,BorderLayout.SOUTH);
		add(buttonPanel,BorderLayout.SOUTH);
		thread=new Thread();
	}
	/**
	 * Listener used by loadButton to tell program to load transistor
	 * @author Aleksander Szpakiewicz-Szatan
	 *
	 */
	public class LoadTransistorReadyListener implements ActionListener 
	{
		TransistorIO transistorIO;
		TranzistorLoadDialogFrame dialogFrame;
		/**
		 * Use LoadTransistorReadyListener(TransistorLoadDialogFrame) as constructor
		 * @param dialogFrame_ - class that should be run as separate thread to load transistor
		 */
		public LoadTransistorReadyListener(TranzistorLoadDialogFrame dialogFrame_)
		{
			dialogFrame=dialogFrame_;
		}
		@Override
		/**
		 * Just runs separate thread.
		 */
		public void actionPerformed(ActionEvent e) 
		{
			load();
		}
		
	}
	
	/**
	 * Loads one of predefined transistors or asks user to choose custom transistor's file (and tries to load it)
	 */
	public void load() 
	{
		int transistors[]={107,159,177,527};
			int index=valuePanel.getSelectedIndex();
			if(index<4)
				transistorIO.LoadDefaultTransistor(transistors[index]);
			else 
			{
				JFrame pointer=this;
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()
				{
					protected Void doInBackground() throws Exception 
					{
						File chosenFile=transistorIO.chooseFile(Localization.getString("chooseCustomTransistor"),Localization.getString("load"),pointer);
						if(chosenFile!=null)
							try 
							{
								transistorIO.LoadTransistor(chosenFile);
							} 
							catch (Exception e) 
							{
								JOptionPane.showMessageDialog(
									transistorIO.frame, Localization.getString("transistorSaveErrorDesc"),
										Localization.getString("transistorSaveError"),
										JOptionPane.ERROR_MESSAGE);
								transistorIO.LoadDefaultTransistor(107);
							}
						return null;
					}
				};
				worker.execute();
				
			}
		setVisible(false);
	}
}
