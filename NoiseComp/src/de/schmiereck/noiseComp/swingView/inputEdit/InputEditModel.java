/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.Generator;
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
	 * Generator Name.
	 */
	private String value = null;

	/**
	 * {@link #value} changed listeners.
	 */
	private final ModelPropertyChangedNotifier valueChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private List<GeneratorSelectItem> generatorSelectItems = new Vector<GeneratorSelectItem>();

	/**
	 * {@link #generatorSelectItems} changed listeners.
	 */
	private final ModelPropertyChangedNotifier generatorSelectItemsChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Input Generator.
	 */
	private Generator inputGenerator = null;

	/**
	 * {@link #inputGenerator} changed listeners.
	 */
	private final ModelPropertyChangedNotifier inputGeneratorChangedNotifier = new ModelPropertyChangedNotifier();
	
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
}
