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
	 * List of the available {@link GeneratorTypeData}-Objects with the Class-Name-String 
	 * {@link GeneratorTypeData#generatorTypeClassName} as keys.<br/>
	 * If the GeneratorType is a generic type like {@link ModulGenerator},
	 * the name of the generic type is appendet after a &quot;#&quot; on the class name.
	 */
	private VectorHash generatorTypes = new VectorHash();
	
	public void addGeneratorTypeData(GeneratorTypeData generatorTypeData)
	{
		this.generatorTypes.add(generatorTypeData.getGeneratorTypeClassName(), generatorTypeData);
	}

	/**
	 * @see #generatorTypes
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
	
	public void removeGeneratorType(GeneratorTypeData generatorTypeData)
	{
		this.generatorTypes.remove(generatorTypeData.getGeneratorTypeClassName(), generatorTypeData);
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
