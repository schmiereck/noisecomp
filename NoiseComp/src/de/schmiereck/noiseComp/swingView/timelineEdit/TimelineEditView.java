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
		
		this.setBorder(BorderFactory.createTitledBorder("Generator:"));
		
		{
			this.generatorNameTextField = this.addTextField(0, "Name:");
			
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
		{
			this.generatorStartTimePosTextField = this.addTextField(1, "Start-Time:");
			
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
		{
			this.generatorEndTimePosTextField = this.addTextField(2, "End-Time:");
			
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
		{
			this.updateButton = new JButton("Update");
			
			this.addField(3, this.updateButton);
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
}
