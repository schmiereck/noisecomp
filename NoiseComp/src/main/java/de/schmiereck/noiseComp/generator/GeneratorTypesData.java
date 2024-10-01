package de.schmiereck.noiseComp.generator;

import java.util.Iterator;

import de.schmiereck.dataTools.VectorHash;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;

/**
 * <p>
 * 	Generator-Types Data.
 * </p>
 *
 * @author smk
 * @version 21.02.2004
 */
public class GeneratorTypesData
{
//	//**********************************************************************************************
//	// Fields:
//
//	/**
//	 * Generator Folder-Path.
//	 */
//	public static final String	GENERATOR_FOLDER_PATH	= "/";
	
	//**********************************************************************************************
	// Fields:
	
	/**
	 * List of the available {@link GeneratorTypeData}-Objects with the Class-Name-String 
	 * {@link GeneratorTypeData#generatorTypeClassName} as keys.<br/>
	 * If the GeneratorType is a generic type like {@link ModuleGenerator},
	 * the name of the generic type is appendet after a &quot;#&quot; on the class name.<br/>
	 * If the generator type is in a folder the key starts with a <code>"/"</code>,
	 * in older versions there is no slash if generator is in the root.
	 */
	private VectorHash<String, GeneratorTypeData> generatorTypes;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public GeneratorTypesData()
	{
		//==========================================================================================
		this.generatorTypes = new VectorHash<String, GeneratorTypeData>();

		//==========================================================================================
	}
	
	public void addGeneratorTypeData(GeneratorTypeData generatorTypeData)
	{
		//==========================================================================================
		String key = this.makeKeyName(generatorTypeData);
		
		this.generatorTypes.add(key, generatorTypeData);
		
		//==========================================================================================
	}

	/**
	 * @param generatorTypeData
	 * 			is the generator Type-Data.
	 * @return
	 * 			the key.
	 */
	private String makeKeyName(GeneratorTypeData generatorTypeData)
	{
		//==========================================================================================
		String key;
		
		String typeClassName = generatorTypeData.getGeneratorTypeClassName();
		
		String folderPath = generatorTypeData.getFolderPath();
		
		key = this.makeKeyName(folderPath, typeClassName);
		
		//==========================================================================================
		return key;
	}

	/**
	 * @param folderPath
	 * 			is the Folder-Path in format <code>"/folder1/folder2/"</code>.
	 * @param generatorTypeClassName
	 * 			is the generator type name.
	 * @return
	 * 			the key.
	 */
	private String makeKeyName(String folderPath, String generatorTypeClassName)
	{
		//==========================================================================================
		String key;
		
//		if (folderPath == null)
//		{
//			key = StartupService.GENERATOR_FOLDER_PATH + generatorTypeClassName;
//		}
//		else
		{
			key = folderPath + generatorTypeClassName;
		}
		//==========================================================================================
		return key;
	}

	/**
	 * @see #generatorTypes
	 * 
	 * @param folderPath
	 * 			is the Folder-Path in format <code>"/folder1/folder2/"</code>.
	 * @param generatorTypeClassName
	 * 			is the generator type name.
	 */
	public GeneratorTypeData searchGeneratorTypeData(String folderPath,
	                                                 String generatorTypeClassName)
	{
		//==========================================================================================
		GeneratorTypeData generatorTypeData;
		
		if (generatorTypeClassName != null)
		{	
			String key = this.makeKeyName(folderPath, generatorTypeClassName);
			
			generatorTypeData = this.generatorTypes.get(key);
		}
		else
		{	
			generatorTypeData = null;
		}

		//==========================================================================================
		return generatorTypeData;
	}

	public GeneratorTypeData get(int pos)
	{
		//==========================================================================================
		GeneratorTypeData generatorTypeData;
		
		generatorTypeData = this.generatorTypes.get(pos);

		//==========================================================================================
		return generatorTypeData;
	}
	
	public void removeGeneratorType(GeneratorTypeData generatorTypeData)
	{
		//==========================================================================================
		String key = this.makeKeyName(generatorTypeData);
		
		this.generatorTypes.remove(key, generatorTypeData);
		
		//==========================================================================================
	}
	
	public int getSize()
	{
		//==========================================================================================
		return this.generatorTypes.size();
	}

	public Iterator<GeneratorTypeData> getGeneratorTypesIterator()
	{
		//==========================================================================================
		return this.generatorTypes.iterator();
	}

	/**
	 * 
	 */
	public void clear()
	{
		//==========================================================================================
		this.generatorTypes.clear();
		
		//==========================================================================================
	}
}
