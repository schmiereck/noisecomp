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
 * 	File-Save Action.
 * </p>
 * 
 * @author smk
 * @version <p>21.09.2010:	created, smk</p>
 */
public class FileSaveAction
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
	 */
	public FileSaveAction(AppController appController)
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
			value = "Save...";
		}
		else
		{
			if (Action.MNEMONIC_KEY.equals(key))
			{
				value = KeyEvent.VK_S;
			}
			else
			{
				if (Action.ACCELERATOR_KEY.equals(key))
				{
					value = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK);
				}
				else
				{
					if (Action.SHORT_DESCRIPTION.equals(key))
					{
						value = "Save the file \"test01.xml\".";
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
		this.appController.doFileSave();
	}

}
