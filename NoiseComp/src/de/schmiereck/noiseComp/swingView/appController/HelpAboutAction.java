/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appController;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;

/**
 * <p>
 * 	Help-About Action.
 * </p>
 * 
 * @author smk
 * @version <p>21.09.2010:	created, smk</p>
 */
public class HelpAboutAction
implements Action
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Ist der Main-Controller der Applikation. 
	 */
	final private AppController appController;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param appController 
	 * 			ist der Main-Controller der Applikation. 
	 */
	public HelpAboutAction(AppController appController)
	{
		this.appController = appController;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
	}

	public Object getValue(String key)
	{
		Object value;
		
		if (Action.NAME.equals(key))
		{
			value = "About...";
		}
		else
		{
			if (Action.MNEMONIC_KEY.equals(key))
			{
				value = KeyEvent.VK_A;
			}
			else
			{
				if (Action.ACCELERATOR_KEY.equals(key))
				{
					value = null;
				}
				else
				{
					if (Action.SHORT_DESCRIPTION.equals(key))
					{
						value = "Informations about the application.";
					}
					else
					{
						value = null;
					}
				}
			}
		}
		
		return value;
	}

	public boolean isEnabled()
	{
		return true;
	}

	public void putValue(String key, Object value)
	{
	}

	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
	}

	public void setEnabled(boolean b)
	{
	}

	public void actionPerformed(ActionEvent e)
	{
		this.appController.doHelpAbout();
	}

}
