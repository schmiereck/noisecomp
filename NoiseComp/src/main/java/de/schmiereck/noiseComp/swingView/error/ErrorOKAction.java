/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.error;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;

import de.schmiereck.noiseComp.swingView.error.ErrorController;

/**
 * <p>
 * 	Error OK Action.
 * </p>
 * 
 * @author smk
 * @version <p>16.03.2011:	created, smk</p>
 */
public class ErrorOKAction
implements Action
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Ist der Controller des Error-Dialogs. 
	 */
	private ErrorController appController;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param appController 
	 * 			ist der Controller des Error-Dialogs. 
	 */
	public ErrorOKAction(ErrorController appController)
	{
		this.appController = appController;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Action#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
	}

	public Object getValue(String key)
	{
		Object value;
		
		if (Action.NAME.equals(key))
		{
			value = "OK";
		}
		else
		{
			if (Action.MNEMONIC_KEY.equals(key))
			{
				value = KeyEvent.VK_K;
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
						value = null;
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
		this.appController.doClose();
	}

}
