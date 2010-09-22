/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.MultiValue;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectEntryModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputsTabelModel;
import de.schmiereck.noiseComp.swingView.utils.InputUtils;
import de.schmiereck.noiseComp.swingView.utils.OutputUtils;

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
		
//		//------------------------------------------------------------------------------------------
//		// Timeline-Edit Update-Button: Update Timeline-Generator-Model and Generator:
//		
//		this.inputEditView.getUpdateButton().addActionListener
//		(
//		 	new ActionListener()
//		 	{
//				@Override
//				public void actionPerformed(ActionEvent e)
//				{
//					InputData inputData = inputSelectModel.getSelectedRow();
////					TimelineGeneratorModel timelineGeneratorModel = timelinesDrawPanelModel.getSelectedTimelineGeneratorModel();
//					
//					InputTypeData inputTypeData = inputEditModel.getInputTypeData();
//					Generator inputGenerator = inputEditModel.getInputGenerator();
//					String valueStr = inputEditModel.getValue();
//					Float value = this.makeInputValue(valueStr);
//					InputTypeData modulInputTypeData = inputEditModel.getModulInputTypeData();
//					
//					if (inputData != null)
//					{
//						// Update Input.
//						//inputData.setInputTypeData(inputTypeData);
//						inputData.setInputGenerator(inputGenerator);
//						inputData.setInputValue(value, valueStr);
//						inputData.setInputModulInputTypeData(modulInputTypeData);
//
//						// Update Timeline-Model.
////						timelineGeneratorModel.setName(generatorName);
////						timelineGeneratorModel.setStartTimePos(generatorStartTimePos);
////						timelineGeneratorModel.setEndTimePos(generatorEndTimePos);
//					}
//					else
//					{
////						InputData inputData = selectedGenerator.addInputGenerator(inputGenerator, inputTypeData, 
////																				  inputGeneratorValue, inputGeneratorValueStr,
////																				  inputModulInputTypeData);
////						
////						this.mainPageData.getGeneratorInputsData().setSelectedInputData(inputData);
//					}
//				}
//
//				private Float makeInputValue(String valueStr)
//				{
//					Float value;
//					if (valueStr != null)
//					{
//						if (valueStr.length() > 0)
//						{	
//							try
//							{
//								value = Float.valueOf(valueStr);
//							}
//							catch (java.lang.NumberFormatException ex)
//							{
//								// Ignore and use String.
//								value = null;
//							}
//						}
//						else
//						{
//							value = null;
//						}
//					}
//					else
//					{
//						value = null;
//					}
//					return value;
//				}
//		 	}
//		);
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
	 * @param editedModulGeneratorTypeData 
	 * 			is the edited Modul-Generator-Type Data.
	 * @param selectedTimelineGenerator 
	 * 			is the Selected Timeline Generator.
	 * @param inputData
	 * 			is the edited input data.
	 * @param editInput
	 * 			<code>true</code> if a input edited.
	 */
	public void updateEditedInput(ModulGeneratorTypeData editedModulGeneratorTypeData, 
	                              Generator selectedTimelineGenerator, 
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
			{
				inputTypeSelectItems = new Vector<InputTypeSelectItem>();
				GeneratorTypeData generatorTypeData = selectedTimelineGenerator.getGeneratorTypeData();
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
				value = OutputUtils.makeFloatText(inputData.getInputValue());
			}
			else
			{
				inputGenerator = null;
				value = null;
			}
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

		inputEditModel.setInputTypeSelectItems(inputTypeSelectItems);
		inputEditModel.setInputTypeData(inputTypeData);
		inputEditModel.setGeneratorSelectItems(generatorSelectItems);
		inputEditModel.setInputGenerator(inputGenerator);
		inputEditModel.setValue(value);
		inputEditModel.setModulInputTypeSelectItems(modulInputTypeSelectItems);
		inputEditModel.setModulInputTypeData(modulInputTypeData);
		
		//==========================================================================================
	}

	/**
	 * @param selectModel
	 * 			is the Select Model.
	 * @param selectedGenerator
	 * 			is the selected Generator.
	 */
	public void doUpdate(final InputSelectModel selectModel,
	                     final Generator selectedGenerator)
	{
		InputEditModel inputEditModel = this.getInputEditModel();
		InputEditView inputEditView = this.getInputEditView();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		InputTypeSelectItem inputTypeSelectItem = (InputTypeSelectItem)inputEditView.getInputTypeComboBox().getSelectedItem();
		GeneratorSelectItem inputGeneratorSelectItem = (GeneratorSelectItem)inputEditView.getInputGeneratorComboBox().getSelectedItem();
		String valueStr = inputEditView.getValueTextField().getText();
		ModulInputTypeSelectItem modulInputTypeSelectItem = (ModulInputTypeSelectItem)inputEditView.getModulInputTypeComboBox().getSelectedItem();
			
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
				
				inputData = 
					selectedGenerator.addInputGenerator(inputGenerator, 
					                                    inputTypeData, 
					                                    multiValue.floatValue, multiValue.stringValue,
					                                    modulInputTypeData);
				
				inputSelectEntryModel.setInputData(inputData);
			}
		}
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Update Input-Edit-Model:
		
		inputEditModel.setInputTypeData(inputTypeSelectItem.getInputTypeData());
		inputEditModel.setInputGenerator(inputGeneratorSelectItem.getGenerator());
		inputEditModel.setValue(valueStr);
		inputEditModel.setModulInputTypeData(modulInputTypeSelectItem.getInputTypeData());
	}

}
