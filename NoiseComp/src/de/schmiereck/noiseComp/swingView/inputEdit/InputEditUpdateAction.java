/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;

import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel;
import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * <p>
 * 	Input-Edit Update Action.
 * </p>
 * 
 * @author smk
 * @version <p>21.06.2011:	created, smk</p>
 */
public class InputEditUpdateAction
implements Action
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Controller of InputEdit-Dialogs. 
	 */
	private final InputEditController inputEditController;
	
	private final InputSelectModel inputSelectModel;
	
	private final TimelinesDrawPanelModel timelinesDrawPanelModel;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param inputEditController 
	 * 			is the Controller of InputEdit-Dialogs. 
	 */
	public InputEditUpdateAction(final InputEditController inputEditController,
	                             final InputSelectModel inputSelectModel, 
	                             final TimelinesDrawPanelModel timelinesDrawPanelModel)
	{
		//==========================================================================================
		this.inputEditController = inputEditController;
		this.inputSelectModel = inputSelectModel;
		this.timelinesDrawPanelModel = timelinesDrawPanelModel;
		
		//==========================================================================================
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
		//==========================================================================================
		Object value;
		
		if (Action.NAME.equals(key))
		{
			value = "Update";
		}
		else
		{
			if (Action.MNEMONIC_KEY.equals(key))
			{
				value = KeyEvent.VK_U;
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
		
		//==========================================================================================
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
		//==========================================================================================
		Timeline selectedTimeline;
		
		SelectedTimelineModel selectedTimelineModel = this.timelinesDrawPanelModel.getSelectedTimelineModel();
		
		TimelineSelectEntryModel selectedTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();

		if (selectedTimelineSelectEntryModel != null)
		{
			selectedTimeline = selectedTimelineSelectEntryModel.getTimeline();
		}
		else
		{
			selectedTimeline = null;
		}
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		this.inputEditController.doSubmit(inputSelectModel, selectedTimeline);
		
		//==========================================================================================
	}

}
