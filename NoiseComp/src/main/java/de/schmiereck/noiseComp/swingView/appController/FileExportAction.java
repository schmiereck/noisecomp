package de.schmiereck.noiseComp.swingView.appController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

public class FileExportAction implements Action {
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
    public FileExportAction(final AppController appController) {
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
            value = "WAV-Export...";
        } else {
            if (Action.MNEMONIC_KEY.equals(key)) {
                value = KeyEvent.VK_E;
            } else {
                if (Action.ACCELERATOR_KEY.equals(key)) {
                    value = KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK);
                } else {
                    if (Action.SHORT_DESCRIPTION.equals(key)) {
                        value = "Export the file \"*.wav\".";
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
        this.appController.doFileExport();
    }

}
