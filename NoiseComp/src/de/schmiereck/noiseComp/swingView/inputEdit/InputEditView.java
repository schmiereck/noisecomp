/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.OutputUtils;
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
	
	private final JComboBox generatorComboBox;
	private final JComboBox typeComboBox;
	private final JTextField valueTextField;
	private final JComboBox modulInputComboBox;
	
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
			this.generatorComboBox = this.addComboBox(0, "Generator:");
			
			inputEditModel.getGeneratorSelectItemsChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						//generatorComboBox.setText(OutputUtils.makeStringText(inputEditModel.getValue()));
						generatorComboBox.removeAllItems();
						
						if (inputEditModel.getGeneratorSelectItems() != null)
						{
							for (GeneratorSelectItem generatorSelectItem : inputEditModel.getGeneratorSelectItems())
							{
								generatorComboBox.addItem(generatorSelectItem);
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
						generatorComboBox.setSelectedItem(inputGeneratorSelectItem);
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.typeComboBox = this.addComboBox(1, "Type:");
			
			inputEditModel.getValueChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						//typeComboBox.setText(OutputUtils.makeStringText(inputEditModel.getValue()));
					}
			 	}
			);
		}
		{
			this.valueTextField = this.addTextField(2, "Value:");
			
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
			this.modulInputComboBox = this.addComboBox(3, "Modul-Input:");
			
			inputEditModel.getValueChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						//modulInputComboBox.setText(OutputUtils.makeStringText(inputEditModel.getValue()));
					}
			 	}
			);
		}
		{
			this.updateButton = new JButton("Update");
			
			this.addField(4, this.updateButton);
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
	 * 			returns the {@link #generatorComboBox}.
	 */
	public JComboBox getGeneratorComboBox()
	{
		return this.generatorComboBox;
	}

	/**
	 * @return 
	 * 			returns the {@link #typeComboBox}.
	 */
	public JComboBox getTypeComboBox()
	{
		return this.typeComboBox;
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
	 * 			returns the {@link #modulInputComboBox}.
	 */
	public JComboBox getModulInputComboBox()
	{
		return this.modulInputComboBox;
	}
}
