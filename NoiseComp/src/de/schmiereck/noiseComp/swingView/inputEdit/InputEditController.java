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
import de.schmiereck.noiseComp.swingView.OutputUtils;

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
	public InputEditController()
	{
		//==========================================================================================
		this.inputEditModel = new InputEditModel();
		
		this.inputEditView = new InputEditView(this.inputEditModel);
		
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
	 */
	public void updateEditedInput(ModulGeneratorTypeData editedModulGeneratorTypeData, 
	                              Generator selectedTimelineGenerator, 
	                              InputData inputData)
	{
		//==========================================================================================
		List<InputTypeSelectItem> inputTypeSelectItems;
		InputTypeData inputTypeData;
		List<GeneratorSelectItem> generatorSelectItems;
		Generator inputGenerator;
		String value;
		List<ModulInputTypeSelectItem> modulInputTypeSelectItems;
		InputTypeData modulInputTypeData;
		
		if (inputData != null)
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
			inputTypeData = inputData.getInputTypeData();
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
			inputGenerator = inputData.getInputGenerator();
			value = OutputUtils.makeFloatText(inputData.getInputValue());
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
			modulInputTypeData = inputData.getInputModulInputTypeData();
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

}
