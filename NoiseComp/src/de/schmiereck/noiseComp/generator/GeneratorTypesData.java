package de.schmiereck.noiseComp.generator;

import java.util.Iterator;

import de.schmiereck.dataTools.VectorHash;

/**
 * TODO docu
 *
 * @author smk
 * @version 21.02.2004
 */
public class GeneratorTypesData
{
	/**
	 * List of the available {@link GeneratorTypeData}-Objects with the Class-Name-Strings as keys.
	 */
	private VectorHash generatorTypes = new VectorHash();
	
	public void addGeneratorTypeData(GeneratorTypeData generatorTypeData)
	{
		this.generatorTypes.add(generatorTypeData.getGeneratorClass().getName(), generatorTypeData);
	}

	/**
	 * @param generator
	 * @return
	 */
	public GeneratorTypeData searchGeneratorTypeData(String generatorTypeClassName)
	{
		GeneratorTypeData generatorTypeData;
		
		if (generatorTypeClassName != null)
		{	
			generatorTypeData = (GeneratorTypeData)this.generatorTypes.get(generatorTypeClassName);
		}
		else
		{	
			generatorTypeData = null;
		}

		return generatorTypeData;
	}

	public GeneratorTypeData get(int pos)
	{
		GeneratorTypeData generatorTypeData;
		
		generatorTypeData = (GeneratorTypeData)this.generatorTypes.get(pos);

		return generatorTypeData;
	}
	
	public void removeGeneratorType(String generatorTypeClassName)
	{
		this.generatorTypes.remove(generatorTypeClassName);
	}
	
	public int getSize()
	{
		return this.generatorTypes.size();
	}

	public Iterator getGeneratorTypesIterator()
	{
		return this.generatorTypes.iterator();
	}

	/**
	 * 
	 */
	public void clear()
	{
		this.generatorTypes.clear();
	}
}
