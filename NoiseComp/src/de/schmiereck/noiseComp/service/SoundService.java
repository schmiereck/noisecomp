/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.service;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;

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
	 * 			the list of {@link #generatorTypesData}.
	 */
	public List<GeneratorTypeData> retrieveGeneratorTypes()
	{
		//==========================================================================================
		List<GeneratorTypeData> generatorTypes = new Vector<GeneratorTypeData>();
		
		Iterator<GeneratorTypeData> generatorTypesIterator = this.generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext())
		{
			GeneratorTypeData generatorTypeData = generatorTypesIterator.next();
			
			generatorTypes.add(generatorTypeData);
		}
		
		//==========================================================================================
		return generatorTypes;
	}

	/**
	 * Remove all GeneratorTypes.
	 */
	public void removeAllGeneratorTypes()
	{
		this.generatorTypesData.clear();
	}

	/**
	 * @param generatorTypeData
	 * 			is the generator type.
	 */
	public void addGeneratorType(GeneratorTypeData generatorTypeData)
	{
		this.generatorTypesData.addGeneratorTypeData(generatorTypeData);
	}

	/**
	 * @param folderPath
	 * 			is the Folder-Path in format <code>"/folder1/folder2/"</code>.
	 * @param generatorTypeClassName
	 * 			is the generator type name.
	 * @return
	 * 			the generator type.
	 */
	public GeneratorTypeData searchGeneratorTypeData(String folderPath, 
	                                                 String generatorTypeClassName)
	{
		//==========================================================================================
		GeneratorTypeData generatorTypeData = 
			this.generatorTypesData.searchGeneratorTypeData(folderPath,
			                                                generatorTypeClassName);
		
		//==========================================================================================
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
		//==========================================================================================
		GeneratorTypeData ret;
		
		if (this.generatorTypesData != null)
		{
			ret = (GeneratorTypeData)this.generatorTypesData.get(pos);
		}
		else
		{
			ret = null;
		}
		//==========================================================================================
		return ret;
	}

	/**
	 * @return
	 * 			the count of {@link #generatorTypesData}.
	 */
	public int getGeneratorTypesCount()
	{
		//==========================================================================================
		int ret;
		
		if (this.generatorTypesData != null)
		{
			ret = this.generatorTypesData.getSize();
		}
		else
		{
			ret = 0;
		}
		//==========================================================================================
		return ret;
	}

	/**
	 * @return
	 * 			the iterator of {@link #generatorTypesData}.
	 */
	public Iterator<GeneratorTypeData> retrieveGeneratorTypesIterator()
	{
		//==========================================================================================
		Iterator<GeneratorTypeData> ret;
		
		if (this.generatorTypesData != null)
		{
			ret = this.generatorTypesData.getGeneratorTypesIterator();
		}
		else
		{
			ret = null;
		}
		
		//==========================================================================================
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

	/**
	 * @param folderPath
	 * 			is the Folder-Path in Format <code>"/folder1/folder2/"</code>.
	 * @param folderName
	 * 			is the folder name.
	 */
	public void addFolder(String folderPath, String folderName)
	{
		//==========================================================================================
		String path = folderPath + folderName + "/";
		
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(path,
		                                                            null,
		                                                            null,
		                                                            null);
		
		this.generatorTypesData.addGeneratorTypeData(generatorTypeData);
		
		//==========================================================================================
	}

	/**
	 * @param folderPath
	 * 			is the Folder-Path in format <code>"/folder1/folder2/"</code>.
	 * @param folderName
	 * 			is the Folder-Name.
	 */
	public void renameFolder(String folderPath, String folderName)
	{
		//==========================================================================================
		Iterator<GeneratorTypeData> generatorTypesIterator = this.generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext())
		{
			GeneratorTypeData generatorTypeData = generatorTypesIterator.next();
			
			String gtFolderPath = generatorTypeData.getFolderPath();
			
			if (gtFolderPath.startsWith(folderPath))
			{
				int beginIndex = gtFolderPath.lastIndexOf('/', folderPath.length() - 2);
				int endIndex = gtFolderPath.lastIndexOf('/', folderPath.length() - 1);
				
				String startPath = gtFolderPath.substring(0, beginIndex + 1);
				String endPath = gtFolderPath.substring(endIndex);
				
				generatorTypeData.setFolderPath(startPath + folderName + endPath);
			}
		}
		
		//==========================================================================================
	}

	/**
	 * @param cutFolderPath
	 * 			is the cut Folder-Path in format <code>"/folder1/folder2/"</code>.
	 * @param pasteFolderPath
	 * 			is the paste Folder-Path in format <code>"/folder1/folder2/"</code>.
	 */
	public void moveFolder(String cutFolderPath, 
	                       String pasteFolderPath)
	{
		//==========================================================================================
		Iterator<GeneratorTypeData> generatorTypesIterator = this.generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext())
		{
			GeneratorTypeData generatorTypeData = generatorTypesIterator.next();
			
			String gtFolderPath = generatorTypeData.getFolderPath();
			
			if (gtFolderPath.startsWith(cutFolderPath))
			{
				//----------------------------------------------------------------------------------
				int beginIndex = gtFolderPath.lastIndexOf('/', cutFolderPath.length() - 2);
				
				String endPath = gtFolderPath.substring(beginIndex + 1);
				
				generatorTypeData.setFolderPath(pasteFolderPath + endPath);
				
				//----------------------------------------------------------------------------------
			}
		}
		
		//==========================================================================================
	}

	/**
	 * @param cutFolderPath
	 * 			is the cut Folder-Path in format <code>"/folder1/folder2/"</code>.
	 * @param pasteFolderPath
	 * 			is the paste Module-Path in format <code>"/folder1/folder2/"</code>.
	 * @param modulGeneratorTypeData
	 * 			is the Module-Generator.
	 */
	public void moveModule(String cutFolderPath, 
	                       String pasteFolderPath,
	                       ModulGeneratorTypeData modulGeneratorTypeData)
	{
		//==========================================================================================
		String moduleName = modulGeneratorTypeData.getGeneratorTypeName();
		
		//------------------------------------------------------------------------------------------
		Iterator<GeneratorTypeData> generatorTypesIterator = this.generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext())
		{
			GeneratorTypeData generatorTypeData = generatorTypesIterator.next();
			
			String gtFolderPath = generatorTypeData.getFolderPath();
			String generatorTypeName = generatorTypeData.getGeneratorTypeName();
			
			if (gtFolderPath.equals(cutFolderPath) && moduleName.equals(generatorTypeName))
			{
				//----------------------------------------------------------------------------------
				generatorTypeData.setFolderPath(pasteFolderPath);

				//----------------------------------------------------------------------------------
				break;
			}
		}
		
		//==========================================================================================
	}

	/**
	 * Check Module is used by other modules.
	 * 
	 * @param modulGeneratorTypeData
	 * 			is the Module-Generator.
	 * @return
	 * 			<code>true</code> if module is in use.
	 */
	public boolean checkModuleIsUsed(ModulGeneratorTypeData modulGeneratorTypeData)
	{
		//==========================================================================================
		boolean moduleIsUsed;
		
		moduleIsUsed = false;
		
		//------------------------------------------------------------------------------------------
		Iterator<GeneratorTypeData> generatorTypesIterator = this.generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext())
		{
			GeneratorTypeData generatorTypeData = generatorTypesIterator.next();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			if (generatorTypeData instanceof ModulGeneratorTypeData)
			{
				ModulGeneratorTypeData checkedGeneratorTypeData = (ModulGeneratorTypeData)generatorTypeData;
				
				Iterator<Generator> tracksIterator = checkedGeneratorTypeData.getTracksIterator();
				
				while (tracksIterator.hasNext())
				{
					Generator trackGenerator = (Generator)tracksIterator.next();
					
					GeneratorTypeData trackGeneratorTypeData = trackGenerator.getGeneratorTypeData();
					
					if (modulGeneratorTypeData == trackGeneratorTypeData)
					{
						moduleIsUsed = true;
					}
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		
		//==========================================================================================
		return moduleIsUsed;
	}
}
