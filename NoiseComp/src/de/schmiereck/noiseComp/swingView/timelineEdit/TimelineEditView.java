/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineEdit;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.OutputUtils;
import de.schmiereck.noiseComp.swingView.basicEditView.BasicEditView;

/**
 * <p>
 * 	Timeline-Edit View.
 * </p>
 * 
 * @author smk
 * @version <p>07.09.2010:	created, smk</p>
 */
public class TimelineEditView
extends BasicEditView
{
	//**********************************************************************************************
	// Fields:

	private final JTextField generatorNameTextField;
	private final JTextField generatorStartTimePosTextField;
	private final JTextField generatorEndTimePosTextField;
	
	/**
	 * Create-New-Timeline Button.
	 */
	private final JButton	createNewTimelineButton;

	/**
	 * Remove-Timeline Button.
	 */
	private final JButton	removeTimelineButton;
	
	/**
	 * Update Button.
	 */
	private final JButton updateButton;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelineEditModel
	 * 			is the Timeline-Edit Model.
	 */
	public TimelineEditView(final TimelineEditModel timelineEditModel)
	{
		//==========================================================================================
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createTitledBorder("Timelines:"));
		
		//------------------------------------------------------------------------------------------
		{
			this.createNewTimelineButton = new JButton("Create new Timeline");
			
			this.addField(0, this.createNewTimelineButton);
		}
		//------------------------------------------------------------------------------------------
		{
			this.removeTimelineButton = new JButton("Remove Timeline");
			
			this.addField(1, this.removeTimelineButton);
		}
		//------------------------------------------------------------------------------------------
		{
			this.generatorNameTextField = this.addTextField(2, "Name:");
			
			timelineEditModel.getGeneratorNameChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						generatorNameTextField.setText(OutputUtils.makeStringText(timelineEditModel.getGeneratorName()));
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.generatorStartTimePosTextField = this.addTextField(3, "Start-Time:");
			
			timelineEditModel.getGeneratorStartTimePosChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						generatorStartTimePosTextField.setText(OutputUtils.makeFloatText(timelineEditModel.getGeneratorStartTimePos()));
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.generatorEndTimePosTextField = this.addTextField(4, "End-Time:");
			
			timelineEditModel.getGeneratorEndTimePosChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						generatorEndTimePosTextField.setText(OutputUtils.makeFloatText(timelineEditModel.getGeneratorEndTimePos()));
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.updateButton = new JButton("Update");
			
			this.addField(5, this.updateButton);
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #updateButton}.
	 */
	public JButton getUpdateButton()
	{
		return this.updateButton;
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorEndTimePosTextField}.
	 */
	public JTextField getGeneratorEndTimePosTextField()
	{
		return this.generatorEndTimePosTextField;
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorNameTextField}.
	 */
	public JTextField getGeneratorNameTextField()
	{
		return this.generatorNameTextField;
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorStartTimePosTextField}.
	 */
	public JTextField getGeneratorStartTimePosTextField()
	{
		return this.generatorStartTimePosTextField;
	}

	/**
	 * @return 
	 * 			returns the {@link #createNewTimelineButton}.
	 */
	public JButton getCreateNewTimelineButton()
	{
		return this.createNewTimelineButton;
	}

	/**
	 * @return 
	 * 			returns the {@link #removeTimelineButton}.
	 */
	public JButton getRemoveTimelineButton()
	{
		return this.removeTimelineButton;
	}
}
