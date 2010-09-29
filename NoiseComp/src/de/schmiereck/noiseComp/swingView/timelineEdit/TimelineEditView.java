/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineEdit;

import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.basicEditView.BasicEditView;
import de.schmiereck.noiseComp.swingView.utils.OutputUtils;

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

	private final JComboBox generatorTypeComboBox;
	private final JTextField generatorNameTextField;
	private final JTextField generatorStartTimePosTextField;
	private final JTextField generatorEndTimePosTextField;
	
	/**
	 * Create-New-Timeline Button.
	 */
	private final JButton	createNewButton;

	/**
	 * Remove-Timeline Button.
	 */
	private final JButton	removeButton;
	
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
			this.createNewButton = new JButton("Create new Timeline");
			
			this.addField(0, this.createNewButton);
		}
		//------------------------------------------------------------------------------------------
		{
			this.removeButton = new JButton("Remove Timeline");
			
			this.addField(1, this.removeButton);
		}
		//------------------------------------------------------------------------------------------
		{
			this.generatorTypeComboBox = this.addComboBox(2, "Input-Type:");
			
			timelineEditModel.getGeneratorTypeSelectItemsChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						generatorTypeComboBox.removeAllItems();
						
						if (timelineEditModel.getGeneratorTypeSelectItems() != null)
						{
							for (GeneratorTypeSelectItem generatorTypeSelectItem : timelineEditModel.getGeneratorTypeSelectItems())
							{
								generatorTypeComboBox.addItem(generatorTypeSelectItem);
							}
						}
					}
			 	}
			);
			timelineEditModel.getGeneratorTypeDataChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						GeneratorTypeSelectItem generatorTypeSelectItem = null;
						
						GeneratorTypeData generatorTypeData = timelineEditModel.getGeneratorTypeData();

						List<GeneratorTypeSelectItem> generatorTypeSelectItems = timelineEditModel.getGeneratorTypeSelectItems();
						if (generatorTypeSelectItems != null)
						{
							for (GeneratorTypeSelectItem generatorTypeSelectItem2 : generatorTypeSelectItems)
							{
								if (generatorTypeSelectItem2.getGeneratorTypeData() == generatorTypeData)
								{
									generatorTypeSelectItem = generatorTypeSelectItem2;
									break;
								}
							}
						}
						generatorTypeComboBox.setSelectedItem(generatorTypeSelectItem);
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.generatorNameTextField = this.addTextField(3, "Name:");
			
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
			this.generatorStartTimePosTextField = this.addTextField(4, "Start-Time:");
			
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
			this.generatorEndTimePosTextField = this.addTextField(5, "End-Time:");
			
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
			
			this.addField(6, this.updateButton);
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
	 * 			returns the {@link #createNewButton}.
	 */
	public JButton getCreateNewButton()
	{
		return this.createNewButton;
	}

	/**
	 * @return 
	 * 			returns the {@link #removeButton}.
	 */
	public JButton getRemoveButton()
	{
		return this.removeButton;
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorTypeComboBox}.
	 */
	public JComboBox getGeneratorTypeComboBox()
	{
		return this.generatorTypeComboBox;
	}
}
