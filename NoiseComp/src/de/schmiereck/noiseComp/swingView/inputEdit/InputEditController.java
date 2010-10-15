/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.MultiValue;
import de.schmiereck.noiseComp.swingView.SwingMain;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectEntryModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputsTabelModel;
import de.schmiereck.noiseComp.swingView.utils.InputUtils;
import de.schmiereck.noiseComp.swingView.utils.OutputUtils;
import de.schmiereck.noiseComp.timeline.Timeline;
import de.schmiereck.noiseComp.timeline.TimelineManagerLogic;

/**
 * <p>
 * 	Input-Edit Controller.
 * </p>
 * 
 * @author smk
 * @version <p>15.09.2010:	created, smk</p>
 */
public class InputEditController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Input-Edit Model.
	 */
	private final InputEditModel inputEditModel;
	
	/**
	 * Input-Edit View.
	 */
	private final InputEditView inputEditView;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public InputEditController(final InputSelectModel inputSelectModel)
	{
		//==========================================================================================
		this.inputEditModel = new InputEditModel();
		
		this.inputEditView = new InputEditView(this.inputEditModel);
		
		//------------------------------------------------------------------------------------------
		this.inputEditView.getInputTypeComboBox().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					JComboBox cb = (JComboBox)e.getSource();
					InputTypeSelectItem inputTypeSelectItem = (InputTypeSelectItem)cb.getSelectedItem();
					
					if (inputTypeSelectItem != null)
					{
						InputTypeData inputTypeData = inputTypeSelectItem.getInputTypeData();
						
						if (inputTypeData != null)
						{
							JTextField inputTypeValueTextField = inputEditView.getInputTypeValueTextField();
							
							String valueStr = InputUtils.makeStringValue(inputTypeValueTextField.getText());
							
							if (valueStr == null)
							{
								Float defaultValue = inputTypeData.getDefaultValue();
								
								String defaultValueStr = OutputUtils.makeFloatText(defaultValue);
								
								inputTypeValueTextField.setText(defaultValueStr);
							}
						}
					}
				}
		 	}
		);
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #inputEditModel}.
	 */
	public InputEditModel getInputEditModel()
	{
		return this.inputEditModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputEditView}.
	 */
	public InputEditView getInputEditView()
	{
		return this.inputEditView;
	}

	/**
	 * Update the Edited-Input in the Input-Edit-Model.
	 * 
	 * @param editedModulGeneratorTypeData 
	 * 			is the edited Modul-Generator-Type Data.
	 * @param selectedTimeline 
	 * 			is the Selected Timeline.
	 * @param inputData
	 * 			is the edited input data.
	 * @param editInput
	 * 			<code>true</code> if a input edited.
	 */
	public void updateEditedInput(ModulGeneratorTypeData editedModulGeneratorTypeData, 
	                              Timeline selectedTimeline, 
	                              InputData inputData,
	                              boolean editInput)
	{
		//==========================================================================================
		List<InputTypeSelectItem> inputTypeSelectItems;
		InputTypeData inputTypeData;
		List<GeneratorSelectItem> generatorSelectItems;
		Generator inputGenerator;
		String value;
		List<ModulInputTypeSelectItem> modulInputTypeSelectItems;
		InputTypeData modulInputTypeData;
		
		if (editInput == true)
		{
			// Make InputType-SelectItems:
			{
				inputTypeSelectItems = new Vector<InputTypeSelectItem>();
				Generator selectedGenerator = selectedTimeline.getGenerator();
				GeneratorTypeData generatorTypeData = selectedGenerator.getGeneratorTypeData();
				Iterator<InputTypeData> inputTypeIterator = generatorTypeData.getInputTypesIterator();
				if (inputTypeIterator != null)
				{
					InputTypeSelectItem noSelectItem = new InputTypeSelectItem(null);
					inputTypeSelectItems.add(noSelectItem);
					while (inputTypeIterator.hasNext())
					{
						InputTypeData inputTypeData2 = inputTypeIterator.next();
						
						inputTypeSelectItems.add(new InputTypeSelectItem(inputTypeData2));
					}
				}
			}
			if (inputData != null)
			{
				inputTypeData = inputData.getInputTypeData();
			}
			else
			{
				inputTypeData = null;
			}
			// Make Generator-SelectItems:
			{
				generatorSelectItems = new Vector<GeneratorSelectItem>();
				Iterator<Generator> generatorsIterator = editedModulGeneratorTypeData.getGeneratorsIterator();
				if (generatorsIterator != null)
				{
					GeneratorSelectItem noSelectItem = new GeneratorSelectItem(null);
					generatorSelectItems.add(noSelectItem);
					while (generatorsIterator.hasNext())
					{
						Generator generator = generatorsIterator.next();
						
						generatorSelectItems.add(new GeneratorSelectItem(generator));
					}
				}
			}
			if (inputData != null)
			{
				inputGenerator = inputData.getInputGenerator();
				
				MultiValue multiValue = new MultiValue();
				multiValue.floatValue = inputData.getInputValue();
				multiValue.stringValue = inputData.getInputStringValue();
				value = OutputUtils.makeMultiValueText(multiValue);
			}
			else
			{
				inputGenerator = null;
				value = null;
			}
			// Make ModulInputType-SelectItems:
			{
				modulInputTypeSelectItems = new Vector<ModulInputTypeSelectItem>();
				Iterator<InputTypeData> modulInputTypeIterator = editedModulGeneratorTypeData.getInputTypesIterator();
				if (modulInputTypeIterator != null)
				{
					ModulInputTypeSelectItem noSelectItem = new ModulInputTypeSelectItem(null);
					modulInputTypeSelectItems.add(noSelectItem);
					while (modulInputTypeIterator.hasNext())
					{
						InputTypeData inputTypeData2 = modulInputTypeIterator.next();
						
						modulInputTypeSelectItems.add(new ModulInputTypeSelectItem(inputTypeData2));
					}
				}
			}
			if (inputData != null)
			{
				modulInputTypeData = inputData.getInputModulInputTypeData();
			}
			else
			{
				modulInputTypeData = null;
			}
		}
		else
		{
			inputTypeSelectItems = null;
			inputTypeData = null;
			generatorSelectItems = null;
			inputGenerator = null;
			value = null;
			modulInputTypeSelectItems = null;
			modulInputTypeData = null;
		}

		this.inputEditModel.setInputTypeSelectItems(inputTypeSelectItems);
		this.inputEditModel.setInputTypeData(inputTypeData);
		this.inputEditModel.setGeneratorSelectItems(generatorSelectItems);
		this.inputEditModel.setInputGenerator(inputGenerator);
		this.inputEditModel.setValue(value);
		this.inputEditModel.setModulInputTypeSelectItems(modulInputTypeSelectItems);
		this.inputEditModel.setModulInputTypeData(modulInputTypeData);
		
		//==========================================================================================
	}

	/**
	 * Submit edited Input.
	 * 
	 * @param selectModel
	 * 			is the Select Model.
	 * @param selectedTimeline
	 * 			is the selected Timeline.
	 */
	public void doSubmit(final InputSelectModel selectModel,
	                     final Timeline selectedTimeline)
	{
		//==========================================================================================
		InputTypeSelectItem inputTypeSelectItem = (InputTypeSelectItem)this.inputEditView.getInputTypeComboBox().getSelectedItem();
		GeneratorSelectItem inputGeneratorSelectItem = (GeneratorSelectItem)this.inputEditView.getInputGeneratorComboBox().getSelectedItem();
		String valueStr = this.inputEditView.getInputTypeValueTextField().getText();
		ModulInputTypeSelectItem modulInputTypeSelectItem = (ModulInputTypeSelectItem)this.inputEditView.getModulInputTypeComboBox().getSelectedItem();
			
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		InputTypeData inputTypeData = inputTypeSelectItem.getInputTypeData();
		Generator inputGenerator = inputGeneratorSelectItem.getGenerator();
		MultiValue multiValue = InputUtils.makeMultiValue(valueStr);
		InputTypeData modulInputTypeData = modulInputTypeSelectItem.getInputTypeData();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Input-Data:
		
		Integer selectedRowNo = selectModel.getSelectedRowNo();
		
		// Input selected?
		if (selectedRowNo != null)
		{
			InputsTabelModel inputsTabelModel = selectModel.getInputsTabelModel();
			
			InputSelectEntryModel inputSelectEntryModel = inputsTabelModel.getRow(selectedRowNo);
			
			InputData inputData = inputSelectEntryModel.getInputData();
			
			// Existing Input selected?
			if (inputData != null)
			{
				// Update selected Input:
				
				inputData.setInputGenerator(inputGenerator);
				inputData.setInputValue(multiValue.floatValue, multiValue.stringValue);
				inputData.setInputModulInputTypeData(modulInputTypeData);
			}
			else
			{
				// Insert new Input:
				SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
				
				TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();
				
				inputData = 
					timelineManagerLogic.addInputGenerator(selectedTimeline,
					                                       inputGenerator, 
					                                       inputTypeData, 
					                                       multiValue.floatValue, multiValue.stringValue,
					                                       modulInputTypeData);
				
				inputSelectEntryModel.setInputData(inputData);
			}
		}
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Update Input-Edit-Model:
		
		this.inputEditModel.setInputTypeData(inputTypeSelectItem.getInputTypeData());
		this.inputEditModel.setInputGenerator(inputGeneratorSelectItem.getGenerator());
		this.inputEditModel.setValue(valueStr);
		this.inputEditModel.setModulInputTypeData(modulInputTypeSelectItem.getInputTypeData());
		
		//==========================================================================================
	}

}
