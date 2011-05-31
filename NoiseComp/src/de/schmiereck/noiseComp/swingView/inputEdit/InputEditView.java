/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;

import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.basicEditView.BasicEditView;
import de.schmiereck.noiseComp.swingView.utils.OutputUtils;
import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * <p>
 * 	Input-Edit View.
 * </p>
 * 
 * @author smk
 * @version <p>15.09.2010:	created, smk</p>
 */
public class InputEditView
extends BasicEditView
{
	//**********************************************************************************************
	// Fields:

	@SuppressWarnings("unused")
	private final InputEditModel inputEditModel;
	
	private final JComboBox inputGeneratorComboBox;
	private final JComboBox inputTypeComboBox;
//	private final JTextField inputTypeValueTextField;
	private final JComboBox inputTypeValueTextField;
	private final JComboBox moduleInputTypeComboBox;

	/**
	 * Create-New-Input Button.
	 */
	private final JButton	createNewButton;

	/**
	 * Remove-Input Button.
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
	 * @param inputEditModel
	 * 			is the Input-Edit Model.
	 */
	public InputEditView(final InputEditModel inputEditModel)
	{
		//==========================================================================================
		this.inputEditModel = inputEditModel;
		
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createTitledBorder("Input:"));
		
		//------------------------------------------------------------------------------------------
		{
			this.inputTypeComboBox = this.addComboBox(0, "Input-Type:");
			
			inputEditModel.getInputTypeSelectItemsChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						inputTypeComboBox.removeAllItems();
						
						if (inputEditModel.getInputTypeSelectItems() != null)
						{
							for (InputTypeSelectItem inputTypeSelectItem : inputEditModel.getInputTypeSelectItems())
							{
								inputTypeComboBox.addItem(inputTypeSelectItem);
							}
						}
					}
			 	}
			);
			inputEditModel.getInputTypeDataChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						InputTypeSelectItem inputTypeSelectItem = null;
						
						InputTypeData inputTypeData = inputEditModel.getInputTypeData();

						List<InputTypeSelectItem> inputTypeSelectItems = inputEditModel.getInputTypeSelectItems();
						if (inputTypeSelectItems != null)
						{
							for (InputTypeSelectItem inputTypeSelectItem2 : inputTypeSelectItems)
							{
								if (inputTypeSelectItem2.getInputTypeData() == inputTypeData)
								{
									inputTypeSelectItem = inputTypeSelectItem2;
									break;
								}
							}
						}
						inputTypeComboBox.setSelectedItem(inputTypeSelectItem);
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.createNewButton = new JButton("Create new input");
			
			this.addField(1, this.createNewButton);
		}
		//------------------------------------------------------------------------------------------
		{
			this.inputGeneratorComboBox = this.addComboBox(2, "Generator:");
			
			inputEditModel.getGeneratorSelectItemsChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						//inputGeneratorComboBox.setText(OutputUtils.makeStringText(inputEditModel.getValue()));
						inputGeneratorComboBox.removeAllItems();
						
						if (inputEditModel.getGeneratorSelectItems() != null)
						{
							for (GeneratorSelectItem generatorSelectItem : inputEditModel.getGeneratorSelectItems())
							{
								inputGeneratorComboBox.addItem(generatorSelectItem);
							}
						}
					}
			 	}
			);
			inputEditModel.getInputTimelineChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						GeneratorSelectItem inputGeneratorSelectItem = null;
						
						Timeline inputTimeline = inputEditModel.getInputTimeline();

						List<GeneratorSelectItem> generatorSelectItems = inputEditModel.getGeneratorSelectItems();
						if (generatorSelectItems != null)
						{
							for (GeneratorSelectItem generatorSelectItem : generatorSelectItems)
							{
								if (generatorSelectItem.getTimeline() == inputTimeline)
								{
									inputGeneratorSelectItem = generatorSelectItem;
									break;
								}
							}
						}
						inputGeneratorComboBox.setSelectedItem(inputGeneratorSelectItem);
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.inputTypeValueTextField = this.addComboBox(3, "Value:");
			this.inputTypeValueTextField.setEditable(true);
			
			inputEditModel.getValueSelectItemsChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						inputTypeValueTextField.removeAllItems();
						
						if (inputEditModel.getValueSelectItems() != null)
						{
//							for (ModuleInputTypeSelectItem moduleInputTypeSelectItem : inputEditModel.getModuleInputTypeSelectItems())
							for (ValueSelectItem valueSelectItem : inputEditModel.getValueSelectItems())
							{
								inputTypeValueTextField.addItem(valueSelectItem);
							}
						}
					}
			 	}
			);
			inputEditModel.getValueChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						inputTypeValueTextField.setSelectedItem(OutputUtils.makeStringText(inputEditModel.getValue()));
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.moduleInputTypeComboBox = this.addComboBox(4, "ModuleInput-Type:");
			
			inputEditModel.getModuleInputTypeSelectItemsChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						moduleInputTypeComboBox.removeAllItems();
						
						if (inputEditModel.getModuleInputTypeSelectItems() != null)
						{
							for (ModuleInputTypeSelectItem moduleInputTypeSelectItem : inputEditModel.getModuleInputTypeSelectItems())
							{
								moduleInputTypeComboBox.addItem(moduleInputTypeSelectItem);
							}
						}
					}
			 	}
			);
			inputEditModel.getModuleInputTypeDataChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						ModuleInputTypeSelectItem moduleInputTypeSelectItem = null;
						
						InputTypeData inputTypeData = inputEditModel.getModuleInputTypeData();

						List<ModuleInputTypeSelectItem> moduleInputTypeSelectItems = inputEditModel.getModuleInputTypeSelectItems();
						if (moduleInputTypeSelectItems != null)
						{
							for (ModuleInputTypeSelectItem moduleInputTypeSelectItem2 : moduleInputTypeSelectItems)
							{
								if (moduleInputTypeSelectItem2.getInputTypeData() == inputTypeData)
								{
									moduleInputTypeSelectItem = moduleInputTypeSelectItem2;
									break;
								}
							}
						}
						moduleInputTypeComboBox.setSelectedItem(moduleInputTypeSelectItem);
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.updateButton = new JButton("Update");
			
			this.addField(5, this.updateButton);

//			this.getRootPane().setDefaultButton(this.updateButton);
			this.setDefaultButton(this.updateButton);
			this.updateButton.setDefaultCapable(true);
		}
		//------------------------------------------------------------------------------------------
		{
			this.removeButton = new JButton("Remove input");
			
			this.addField(6, this.removeButton);
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
	 * 			returns the {@link #inputGeneratorComboBox}.
	 */
	public JComboBox getInputGeneratorComboBox()
	{
		return this.inputGeneratorComboBox;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeComboBox}.
	 */
	public JComboBox getInputTypeComboBox()
	{
		return this.inputTypeComboBox;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeValueTextField}.
	 */
	public JComboBox getInputTypeValueTextField()
	{
		return this.inputTypeValueTextField;
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleInputTypeComboBox}.
	 */
	public JComboBox getModuleInputTypeComboBox()
	{
		return this.moduleInputTypeComboBox;
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
	 * 			returns the {@link #createNewButton}.
	 */
	public JButton getCreateNewButton()
	{
		return this.createNewButton;
	}
}
