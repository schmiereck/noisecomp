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
	//**********************************************************************************************
	// Fields:

	/**
	 * Main-Controller of Application. 
	 */
	final private AppController appController;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param appController 
	 * 			is the Main-Controller of Application. 
	 * @param mainController 
	 * 			is the Main-Controller of View. 
	 */
	public FileOpenAction(AppController appController)
	{
		this.appController = appController;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Action#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
	}

	/* (non-Javadoc)
	 * @see javax.swing.Action#getValue(java.lang.String)
	 */
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
						value = "Load the file \"*.noiseComp.xml\".";
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

	/* (non-Javadoc)
	 * @see javax.swing.Action#isEnabled()
	 */
	public boolean isEnabled()
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Action#putValue(java.lang.String, java.lang.Object)
	 */
	public void putValue(String key, Object value)
	{
	}

	/* (non-Javadoc)
	 * @see javax.swing.Action#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
	}

	/* (non-Javadoc)
	 * @see javax.swing.Action#setEnabled(boolean)
	 */
	public void setEnabled(boolean b)
	{
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		this.appController.doFileOpen();
	}

}
