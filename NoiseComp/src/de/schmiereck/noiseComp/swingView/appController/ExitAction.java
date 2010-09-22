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
 * 	App-Exit Action.
 * </p>
 * 
 * @author smk
 * @version <p>21.09.2010:	created, smk</p>
 */
public class ExitAction
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
	public ExitAction(AppController appController)
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
			value = "Exit";
		}
		else
		{
			if (Action.MNEMONIC_KEY.equals(key))
			{
				value = KeyEvent.VK_X;
			}
			else
			{
				if (Action.ACCELERATOR_KEY.equals(key))
				{
					value = KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK);
				}
				else
				{
					value = null;
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
		this.appController.doExit();
	}

}
