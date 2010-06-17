/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.service;

import java.util.Iterator;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;

/**
 * <p>
 * 	Sound Service.
 * </p>
 * 
 * @author smk
 * @version <p>17.06.2010:	created, smk</p>
 */
public class SoundService
{
	//**********************************************************************************************
	// Fields:

	private static SoundService soundService = new SoundService();
	
	GeneratorTypesData generatorTypesData  = new GeneratorTypesData();

	//**********************************************************************************************
	// Functions:
	
	/**
	 * @return
	 * 			the singleton instance.
	 */
	public static SoundService getInstance()
	{
		return soundService;
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorTypesData}.
	 */
	public GeneratorTypesData retievesGeneratorTypes()
	{
		return this.generatorTypesData;
	}

	/**
	 * Remove all GeneratorTypes.
	 */
	public void removeAllGeneratorTypes()
	{
		this.generatorTypesData.clear();
	}

	/**
	 * @param createGeneratorTypeData
	 * 			is the generator type.
	 */
	public void addGeneratorType(GeneratorTypeData createGeneratorTypeData)
	{
		this.generatorTypesData.addGeneratorTypeData(createGeneratorTypeData);
	}

	/**
	 * @param generatorTypeClassName
	 * 			is the generator type name.
	 * @return
	 * 			the generator type.
	 */
	public GeneratorTypeData searchGeneratorTypeData(String generatorTypeClassName)
	{
		GeneratorTypeData generatorTypeData = 
			this.generatorTypesData.searchGeneratorTypeData(generatorTypeClassName);
		
		return generatorTypeData;
	}

	/**
	 * @param pos
	 * 			is the position.
	 * @return
	 * 			the generator of {@link #generatorTypesData} at given position.
	 */
	public GeneratorTypeData getGeneratorTypeData(int pos)
	{
		GeneratorTypeData ret;
		
		if (this.generatorTypesData != null)
		{
			ret = (GeneratorTypeData)this.generatorTypesData.get(pos);
		}
		else
		{
			ret = null;
		}
		return ret;
	}

	/**
	 * @return
	 * 			the count of {@link #generatorTypesData}.
	 */
	public int getGeneratorTypesCount()
	{
		int ret;
		
		if (this.generatorTypesData != null)
		{
			ret = this.generatorTypesData.getSize();
		}
		else
		{
			ret = 0;
		}
		return ret;
	}

	/**
	 * @return
	 * 			the iterator of {@link #generatorTypesData}.
	 */
	public Iterator<GeneratorTypeData> retrieveGeneratorTypesIterator()
	{
		Iterator<GeneratorTypeData> ret;
		
		if (this.generatorTypesData != null)
		{
			ret = this.generatorTypesData.getGeneratorTypesIterator();
		}
		else
		{
			ret = null;
		}
		
		return ret;
	}

	/**
	 * @param generatorTypeData
	 * 			is the generator type.
	 */
	public void removeGeneratorType(GeneratorTypeData generatorTypeData)
	{
		this.generatorTypesData.removeGeneratorType(generatorTypeData);
	}
}
