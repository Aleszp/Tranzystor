package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;

import javax.swing.JButton;

/**
 * Button that will only work if it's activated;
 * @author Aleksander Szpakiewicz-Szatan
 */
public class ActivableButton extends JButton
{
	private static final long serialVersionUID = 1L;
	boolean active;
	Color inactiveColor;
	Color activeColor;
	/**
	 * Use ActivableButton(String, Color, Color, boolean) as constructor
	 * @param title - button's title
	 * @param inactiveColor_ - which color should have inactive button
	 * @param activeColor_ - which color should have active button
	 * @param active_ - true if button should be active, false if inactive
	 */
	public ActivableButton(String title, Color inactiveColor_, Color activeColor_,boolean active_)
	{
		super(title);
		inactiveColor=inactiveColor_;
		activeColor=activeColor_;
		active=active_;
	}
	/**
	 * Use ActivableButton(String, Color, Color, boolean,String) as constructor
	 * @param title - button's title
	 * @param inactiveColor_ - which color should have inactive button
	 * @param activeColor_ - which color should have active button
	 * @param active_ - true if button should be active, false if inactive
	 * @param decription - description that shows as tooltip
	 */
	public ActivableButton(String title, Color inactiveColor_, Color activeColor_,boolean active_,String description)
	{
		super(title);
		inactiveColor=inactiveColor_;
		activeColor=activeColor_;
		active=active_;
		setToolTipText(description);
	}
	/**
	 * Use setActive(boolean, String) to change button's active status
	 * @param active_ - true to make button active, false to make button inactive
	 * @param description - description that shows as tooltip
	 */
	public void setActive(boolean active_, String description)
	{
		active=active_;
		
		if(active)
		{
			setBackground(activeColor);
		}
		else
		{
			setBackground(inactiveColor);
		}
		setToolTipText(description);
	}
	/**
	 * Use getActive() to determine whether button is active or not/
	 * @return boolean - true for active button, false for inactive.
	 */
	public boolean getActive()
	{
		return active;
	}
}