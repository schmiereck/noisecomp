/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.InputData;
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
	 * @param inputData
	 * 			is the edited input data.
	 */
	public void updateEditedInput(ModulGeneratorTypeData editedModulGeneratorTypeData, 
	                              InputData inputData)
	{
		//==========================================================================================
		List<GeneratorSelectItem> generatorSelectItems;
		Generator inputGenerator;
		String value;
		
		if (inputData != null)
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
			inputGenerator = inputData.getInputGenerator();
			value = OutputUtils.makeFloatText(inputData.getInputValue());
		}
		else
		{
			generatorSelectItems = null;
			inputGenerator = null;
			value = null;
		}

		inputEditModel.setGeneratorSelectItems(generatorSelectItems);
		inputEditModel.setInputGenerator(inputGenerator);
		inputEditModel.setValue(value);
		
		//==========================================================================================
	}

}
