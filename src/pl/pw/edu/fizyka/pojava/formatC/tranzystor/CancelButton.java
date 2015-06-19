package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Button that hides unwanted dialog frames in program.
 * 
 * @author Aleksander Szpakiewicz-Szatan
 */
public class CancelButton extends JButton
{
	private static final long serialVersionUID = 1L;
	/**
	 * Use CancelButton(String,JFrame) as constructor
	 * @param label - label that will be displayed on button
	 * @param frame - frame that the button should close
	 */
	public CancelButton(String label, JFrame frame)
	{
		super(label);
		addActionListener(new CancelListener(frame));
	}
	/**
	 * Listener used by CancelButtons to hide frames
	 * @author Aleksander Szpakiewicz-Szatan
	 */
	public class CancelListener implements ActionListener
	{
		JFrame frame;
		/**
		 * Use CancelListener(JFrame) as constructor
		 * @param frame_ - frame that listener will hide
		 */
		public CancelListener(JFrame frame_)
		{
			frame=frame_;
		}
		@Override
		/**
		 * Makes frame hide.
		 */
		public void actionPerformed(ActionEvent e) 
		{
			frame.setVisible(false);
		}
	}
}
