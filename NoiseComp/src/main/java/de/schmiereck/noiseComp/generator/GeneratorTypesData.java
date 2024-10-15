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
	 * List of the available {@link GeneratorTypeInfoData}-Objects with the Class-Name-String
	 * {@link GeneratorTypeInfoData#getGeneratorTypeClassName()} as keys.<br/>
	 * If the GeneratorType is a generic type like {@link ModuleGenerator},
	 * the name of the generic type is appendet after a &quot;#&quot; on the class name.<br/>
	 * If the generator type is in a folder the key starts with a <code>"/"</code>,
	 * in older versions there is no slash if generator is in the root.
	 */
	private VectorHash<String, GeneratorTypeInfoData> generatorTypes;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public GeneratorTypesData()
	{
		//==========================================================================================
		this.generatorTypes = new VectorHash<String, GeneratorTypeInfoData>();

		//==========================================================================================
	}
	
	public void addGeneratorTypeData(GeneratorTypeInfoData generatorTypeInfoData)
	{
		//==========================================================================================
		String key = this.makeKeyName(generatorTypeInfoData);
		
		this.generatorTypes.add(key, generatorTypeInfoData);
		
		//==========================================================================================
	}

	/**
	 * @param generatorTypeInfoData
	 * 			is the generator Type-Data.
	 * @return
	 * 			the key.
	 */
	private String makeKeyName(GeneratorTypeInfoData generatorTypeInfoData)
	{
		//==========================================================================================
		String key;
		
		String typeClassName = generatorTypeInfoData.getGeneratorTypeClassName();
		
		String folderPath = generatorTypeInfoData.getFolderPath();
		
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
	public GeneratorTypeInfoData searchGeneratorTypeData(final String folderPath,
                                                         final String generatorTypeClassName) {
		//==========================================================================================
		final GeneratorTypeInfoData generatorTypeInfoData;
		
		if (generatorTypeClassName != null) {
			final String key = this.makeKeyName(folderPath, generatorTypeClassName);
			
			generatorTypeInfoData = this.generatorTypes.get(key);
		} else {
			generatorTypeInfoData = null;
		}
		//==========================================================================================
		return generatorTypeInfoData;
	}

	public GeneratorTypeInfoData get(int pos)
	{
		//==========================================================================================
		GeneratorTypeInfoData generatorTypeInfoData;
		
		generatorTypeInfoData = this.generatorTypes.get(pos);

		//==========================================================================================
		return generatorTypeInfoData;
	}
	
	public void removeGeneratorType(GeneratorTypeInfoData generatorTypeInfoData)
	{
		//==========================================================================================
		String key = this.makeKeyName(generatorTypeInfoData);
		
		this.generatorTypes.remove(key, generatorTypeInfoData);
		
		//==========================================================================================
	}
	
	public int getSize()
	{
		//==========================================================================================
		return this.generatorTypes.size();
	}

	public Iterator<GeneratorTypeInfoData> getGeneratorTypesIterator()
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
