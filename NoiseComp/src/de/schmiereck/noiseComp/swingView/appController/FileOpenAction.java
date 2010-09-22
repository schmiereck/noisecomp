/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appController;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 * <p>
 * 	File-Open Action.
 * </p>
 * 
 * @author smk
 * @version <p>21.09.2010:	created, smk</p>
 */
public class FileOpenAction
implements Action
{
	/**
	 * Ist der Main-Controller der Applikation. 
	 */
	private AppController appController;
	
	/**
	 * Constructor.
	 * 
	 * @param appController 
	 * 			ist der Main-Controller der Applikation. 
	 * @param mainController 
	 * 			ist der Main-Controller des View. 
	 */
	public FileOpenAction(AppController appController)
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
			value = "Open...";
		}
		else
		{
			if (Action.MNEMONIC_KEY.equals(key))
			{
				value = KeyEvent.VK_O;
			}
			else
			{
				if (Action.ACCELERATOR_KEY.equals(key))
				{
					value = KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK);
				}
				else
				{
					if (Action.SHORT_DESCRIPTION.equals(key))
					{
						value = "Load the file \"test01.xml\".";
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
		this.appController.doFileOpen();
	}

}
