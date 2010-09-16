/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;

/**
 * <p>
 * 	Input-Edit Model.
 * </p>
 * 
 * @author smk
 * @version <p>15.09.2010:	created, smk</p>
 */
public class InputEditModel
{
	//**********************************************************************************************
	// Fields:

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Input Generator.
	 */
	private InputTypeData inputTypeData = null;

	/**
	 * {@link #inputTypeData} changed listeners.
	 */
	private final ModelPropertyChangedNotifier inputTypeDataChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private List<InputTypeSelectItem> inputTypeSelectItems = new Vector<InputTypeSelectItem>();

	/**
	 * {@link #inputTypeSelectItems} changed listeners.
	 */
	private final ModelPropertyChangedNotifier inputTypeSelectItemsChangedNotifier = new ModelPropertyChangedNotifier();

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Input Generator.
	 */
	private Generator inputGenerator = null;

	/**
	 * {@link #inputGenerator} changed listeners.
	 */
	private final ModelPropertyChangedNotifier inputGeneratorChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private List<GeneratorSelectItem> generatorSelectItems = new Vector<GeneratorSelectItem>();

	/**
	 * {@link #generatorSelectItems} changed listeners.
	 */
	private final ModelPropertyChangedNotifier generatorSelectItemsChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Generator Name.
	 */
	private String value = null;

	/**
	 * {@link #value} changed listeners.
	 */
	private final ModelPropertyChangedNotifier valueChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Modul-Input Generator.
	 */
	private InputTypeData modulInputTypeData = null;

	/**
	 * {@link #modulInputTypeData} changed listeners.
	 */
	private final ModelPropertyChangedNotifier modulInputTypeDataChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private List<ModulInputTypeSelectItem> modulInputTypeSelectItems = new Vector<ModulInputTypeSelectItem>();

	/**
	 * {@link #modulInputTypeSelectItems} changed listeners.
	 */
	private final ModelPropertyChangedNotifier modulInputTypeSelectItemsChangedNotifier = new ModelPropertyChangedNotifier();

	//**********************************************************************************************
	// Functions:

	/**
	 * @return 
	 * 			returns the {@link #value}.
	 */
	public String getValue()
	{
		return this.value;
	}

	/**
	 * @param value 
	 * 			to set {@link #value}.
	 */
	public void setValue(String value)
	{
		this.value = value;
		
		// Notify listeners.
		this.valueChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #valueChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getValueChangedNotifier()
	{
		return this.valueChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorSelectItems}.
	 */
	public List<GeneratorSelectItem> getGeneratorSelectItems()
	{
		return this.generatorSelectItems;
	}

	/**
	 * @param generatorSelectItems 
	 * 			to set {@link #generatorSelectItems}.
	 */
	public void setGeneratorSelectItems(List<GeneratorSelectItem> generatorSelectItems)
	{
		this.generatorSelectItems = generatorSelectItems;
		
		// Notify Listeners.
		this.generatorSelectItemsChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorSelectItemsChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getGeneratorSelectItemsChangedNotifier()
	{
		return this.generatorSelectItemsChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputGenerator}.
	 */
	public Generator getInputGenerator()
	{
		return this.inputGenerator;
	}

	/**
	 * @param inputGenerator 
	 * 			to set {@link #inputGenerator}.
	 */
	public void setInputGenerator(Generator inputGenerator)
	{
		this.inputGenerator = inputGenerator;
		
		// Notify Listeners.
		this.inputGeneratorChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #inputGeneratorChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getInputGeneratorChangedNotifier()
	{
		return this.inputGeneratorChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeSelectItems}.
	 */
	public List<InputTypeSelectItem> getInputTypeSelectItems()
	{
		return this.inputTypeSelectItems;
	}

	/**
	 * @param inputTypeSelectItems 
	 * 			to set {@link #inputTypeSelectItems}.
	 */
	public void setInputTypeSelectItems(List<InputTypeSelectItem> inputTypeSelectItems)
	{
		this.inputTypeSelectItems = inputTypeSelectItems;
		
		// Notify Listeners.
		this.inputTypeSelectItemsChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeSelectItemsChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getInputTypeSelectItemsChangedNotifier()
	{
		return this.inputTypeSelectItemsChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeData}.
	 */
	public InputTypeData getInputTypeData()
	{
		return this.inputTypeData;
	}

	/**
	 * @param inputTypeData 
	 * 			to set {@link #inputTypeData}.
	 */
	public void setInputTypeData(InputTypeData inputTypeData)
	{
		this.inputTypeData = inputTypeData;
		
		// Notify Listeners.
		this.inputTypeDataChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeDataChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getInputTypeDataChangedNotifier()
	{
		return this.inputTypeDataChangedNotifier;
	}



















	/**
	 * @return 
	 * 			returns the {@link #modulInputTypeSelectItems}.
	 */
	public List<ModulInputTypeSelectItem> getModulInputTypeSelectItems()
	{
		return this.modulInputTypeSelectItems;
	}

	/**
	 * @param modulInputTypeSelectItems 
	 * 			to set {@link #modulInputTypeSelectItems}.
	 */
	public void setModulInputTypeSelectItems(List<ModulInputTypeSelectItem> modulInputTypeSelectItems)
	{
		this.modulInputTypeSelectItems = modulInputTypeSelectItems;
		
		// Notify Listeners.
		this.modulInputTypeSelectItemsChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #modulInputTypeSelectItemsChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getModulInputTypeSelectItemsChangedNotifier()
	{
		return this.modulInputTypeSelectItemsChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #modulInputTypeData}.
	 */
	public InputTypeData getModulInputTypeData()
	{
		return this.modulInputTypeData;
	}

	/**
	 * @param modulInputTypeData 
	 * 			to set {@link #modulInputTypeData}.
	 */
	public void setModulInputTypeData(InputTypeData modulInputTypeData)
	{
		this.modulInputTypeData = modulInputTypeData;
		
		// Notify Listeners.
		this.modulInputTypeDataChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #modulInputTypeDataChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getModulInputTypeDataChangedNotifier()
	{
		return this.modulInputTypeDataChangedNotifier;
	}
}
