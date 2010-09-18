/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.net.URL;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.OutputUtils;
import de.schmiereck.noiseComp.swingView.appView.AppView;
import de.schmiereck.noiseComp.swingView.basicEditView.BasicEditView;

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

	private final InputEditModel inputEditModel;
	
	private final JComboBox inputGeneratorComboBox;
	private final JComboBox inputTypeComboBox;
	private final JTextField valueTextField;
	private final JComboBox modulInputTypeComboBox;
	
	/**
	 * Update Button.
	 */
	private final JButton updateButton;

	/**
	 * Remove-Input Button.
	 */
	private final JButton	removeInputButton;

	/**
	 * Create-New-Input Button.
	 */
	private final JButton	createNewInputButton;
	
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
			this.removeInputButton = new JButton("Remove input");
			
			this.addField(0, this.removeInputButton);
		}
		//------------------------------------------------------------------------------------------
		{
			this.createNewInputButton = new JButton("Create new input");
			
			this.addField(1, this.createNewInputButton);
		}
		//------------------------------------------------------------------------------------------
		{
			this.inputTypeComboBox = this.addComboBox(2, "Input-Type:");
			
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
			this.inputGeneratorComboBox = this.addComboBox(3, "Generator:");
			
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
			inputEditModel.getInputGeneratorChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						GeneratorSelectItem inputGeneratorSelectItem = null;
						
						Generator inputGenerator = inputEditModel.getInputGenerator();

						List<GeneratorSelectItem> generatorSelectItems = inputEditModel.getGeneratorSelectItems();
						if (generatorSelectItems != null)
						{
							for (GeneratorSelectItem generatorSelectItem : generatorSelectItems)
							{
								if (generatorSelectItem.getGenerator() == inputGenerator)
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
			this.valueTextField = this.addTextField(4, "Value:");
			
			inputEditModel.getValueChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						valueTextField.setText(OutputUtils.makeStringText(inputEditModel.getValue()));
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.modulInputTypeComboBox = this.addComboBox(5, "Modul-Input-Type:");
			
			inputEditModel.getModulInputTypeDataChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						modulInputTypeComboBox.removeAllItems();
						
						if (inputEditModel.getModulInputTypeSelectItems() != null)
						{
							for (ModulInputTypeSelectItem modulInputTypeSelectItem : inputEditModel.getModulInputTypeSelectItems())
							{
								modulInputTypeComboBox.addItem(modulInputTypeSelectItem);
							}
						}
					}
			 	}
			);
			inputEditModel.getModulInputTypeSelectItemsChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						ModulInputTypeSelectItem modulInputTypeSelectItem = null;
						
						InputTypeData inputTypeData = inputEditModel.getModulInputTypeData();

						List<ModulInputTypeSelectItem> modulInputTypeSelectItems = inputEditModel.getModulInputTypeSelectItems();
						if (modulInputTypeSelectItem != null)
						{
							for (ModulInputTypeSelectItem modulInputTypeSelectItem2 : modulInputTypeSelectItems)
							{
								if (modulInputTypeSelectItem2.getInputTypeData() == inputTypeData)
								{
									modulInputTypeSelectItem = modulInputTypeSelectItem2;
									break;
								}
							}
						}
						modulInputTypeComboBox.setSelectedItem(modulInputTypeSelectItem);
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
	 * 			returns the {@link #valueTextField}.
	 */
	public JTextField getValueTextField()
	{
		return this.valueTextField;
	}

	/**
	 * @return 
	 * 			returns the {@link #modulInputTypeComboBox}.
	 */
	public JComboBox getModulInputTypeComboBox()
	{
		return this.modulInputTypeComboBox;
	}

	public JButton getRemoveInputButton()
	{
		return this.removeInputButton;
	}

	public JButton getCreateNewInputButton()
	{
		return this.createNewInputButton;
	}
}
