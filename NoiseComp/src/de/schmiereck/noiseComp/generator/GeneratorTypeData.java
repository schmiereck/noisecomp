package de.schmiereck.noiseComp.generator;

import java.util.Iterator;
import java.util.Vector;

import de.schmiereck.dataTools.VectorHash;

/**
 * TODO docu
 *
 * @author smk
 * @version 21.02.2004
 */
public class GeneratorTypeData
{
	private Class generatorClass;
	/**
	 * Name of the generator type.
	 */
	private String generatorTypeName;
	
	/**
	 * List of the allowed {@link InputTypeData}-Objects of this Generator-Type.
	 */
	private VectorHash inputTypes = new VectorHash();
	
	/**
	 * Constructor.
	 * 
	 * @param generatorTypeName
	 */
	public GeneratorTypeData(Class generatorClass, String generatorTypeName)
	{
		super();
		this.generatorClass = generatorClass;
		this.generatorTypeName = generatorTypeName;
	}

	/**
	 * @see #inputTypes
	 */
	public void addInputTypeData(InputTypeData inputTypeData)
	{
		this.inputTypes.add(Integer.valueOf(inputTypeData.getInputType()), inputTypeData);
	}
	/**
	 * @return the attribute {@link #generatorClass}.
	 */
	public Class getGeneratorClass()
	{
		return this.generatorClass;
	}

	public InputTypeData getInputTypeData(int inputType)
	{
		return (InputTypeData)this.inputTypes.get(Integer.valueOf(inputType));
	}
	/**
	 * @return the attribute {@link #generatorTypeName}.
	 */
	public String getGeneratorTypeName()
	{
		return this.generatorTypeName;
	}

	/**
	 * @return
	 */
	public Iterator getInputTypesDataIterator()
	{
		return this.inputTypes.iterator();
	}
}
