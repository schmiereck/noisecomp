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
 * 	File-Import Action.
 * </p>
 * 
 * @author smk
 * @version <p>14.02.2011:	created, smk</p>
 */
public class FileImportAction
implements Action {
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
	 */
	public FileImportAction(final AppController appController) {
		this.appController = appController;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Action#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(final PropertyChangeListener listener) {
	}

	/* (non-Javadoc)
	 * @see javax.swing.Action#getValue(java.lang.String)
	 */
	public Object getValue(final String key) {
		final Object value;
		
		if (Action.NAME.equals(key)) {
			value = "Import...";
		} else {
			if (Action.MNEMONIC_KEY.equals(key)) {
				value = KeyEvent.VK_I;
			} else {
				if (Action.ACCELERATOR_KEY.equals(key)) {
					value = KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK);
				} else {
					if (Action.SHORT_DESCRIPTION.equals(key)) {
						value = "Import the file \"*.noiseComp.xml\".";
					} else {
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
	public boolean isEnabled() {
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Action#putValue(java.lang.String, java.lang.Object)
	 */
	public void putValue(final String key, final Object value) {
	}

	/* (non-Javadoc)
	 * @see javax.swing.Action#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(final PropertyChangeListener listener) {
	}

	/* (non-Javadoc)
	 * @see javax.swing.Action#setEnabled(boolean)
	 */
	public void setEnabled(final boolean b) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent e) {
		this.appController.doFileImport();
	}

}
