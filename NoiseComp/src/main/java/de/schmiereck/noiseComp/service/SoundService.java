/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.service;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeInfoData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeInfoData;
import de.schmiereck.noiseComp.swingView.CompareUtils;

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

	private GeneratorTypesData generatorTypesData  = new GeneratorTypesData();

	/**
	 * Start time in seconds.
	 */
	private double startTime = 0.0D;
	
	/**
	 * End time in seconds.
	 */
	private double endTime = 0.0D;
	
	/**
	 * <code>true</code> if sound looped between start and end time.
	 */
	private boolean looped = false;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @return
	 * 			the list of {@link #generatorTypesData}.
	 */
	public List<GeneratorTypeInfoData> retrieveGeneratorTypes() {
		//==========================================================================================
		List<GeneratorTypeInfoData> generatorTypes = new Vector<GeneratorTypeInfoData>();
		
		Iterator<GeneratorTypeInfoData> generatorTypesIterator = this.generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext())
		{
			GeneratorTypeInfoData generatorTypeInfoData = generatorTypesIterator.next();
			
			generatorTypes.add(generatorTypeInfoData);
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
	 * @param generatorTypeInfoData
	 * 			is the generator type.
	 */
	public void addGeneratorType(GeneratorTypeInfoData generatorTypeInfoData)
	{
		this.generatorTypesData.addGeneratorTypeData(generatorTypeInfoData);
	}

	/**
	 * @param folderPath
	 * 			is the Folder-Path in format <code>"/folder1/folder2/"</code>.
	 * @param generatorTypeClassName
	 * 			is the generator type name.
	 * @return
	 * 			the generator type.
	 */
	public GeneratorTypeInfoData searchGeneratorTypeData(String folderPath,
														 String generatorTypeClassName) {
		//==========================================================================================
		GeneratorTypeInfoData generatorTypeInfoData =
			this.generatorTypesData.searchGeneratorTypeData(folderPath,
			                                                generatorTypeClassName);
		
		//==========================================================================================
		return generatorTypeInfoData;
	}

	/**
	 * @param pos
	 * 			is the position.
	 * @return
	 * 			the generator of {@link #generatorTypesData} at given position.
	 */
	public GeneratorTypeInfoData getGeneratorTypeData(int pos) {
		//==========================================================================================
		GeneratorTypeInfoData ret;
		
		if (this.generatorTypesData != null)
		{
			ret = (GeneratorTypeInfoData)this.generatorTypesData.get(pos);
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
	public int getGeneratorTypesCount() {
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
	public Iterator<GeneratorTypeInfoData> retrieveGeneratorTypesIterator() {
		//==========================================================================================
		Iterator<GeneratorTypeInfoData> ret;
		
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
	 * @param generatorTypeInfoData
	 * 			is the generator type.
	 */
	public void removeGeneratorType(GeneratorTypeInfoData generatorTypeInfoData) {
		this.generatorTypesData.removeGeneratorType(generatorTypeInfoData);
	}

	/**
	 * @param folderPath
	 * 			is the Folder-Path in Format <code>"/folder1/folder2/"</code>.
	 * @param folderName
	 * 			is the folder name.
	 */
	public void addFolder(String folderPath, String folderName) {
		//==========================================================================================
		String path = folderPath + folderName + "/";
		
		GeneratorTypeInfoData generatorTypeInfoData = new GeneratorTypeInfoData(path,
		                                                            null,
		                                                            null,
		                                                            null);
		
		this.generatorTypesData.addGeneratorTypeData(generatorTypeInfoData);
		
		//==========================================================================================
	}

	/**
	 * @param folderPath
	 * 			is the Folder-Path in format <code>"/folder1/folder2/"</code>.
	 * @param folderName
	 * 			is the Folder-Name.
	 */
	public void renameFolder(String folderPath, String folderName) {
		//==========================================================================================
		Iterator<GeneratorTypeInfoData> generatorTypesIterator = this.generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext())
		{
			GeneratorTypeInfoData generatorTypeInfoData = generatorTypesIterator.next();
			
			String gtFolderPath = generatorTypeInfoData.getFolderPath();
			
			if (gtFolderPath.startsWith(folderPath))
			{
				int beginIndex = gtFolderPath.lastIndexOf('/', folderPath.length() - 2);
				int endIndex = gtFolderPath.lastIndexOf('/', folderPath.length() - 1);
				
				String startPath = gtFolderPath.substring(0, beginIndex + 1);
				String endPath = gtFolderPath.substring(endIndex);
				
				generatorTypeInfoData.setFolderPath(startPath + folderName + endPath);
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
	public void moveFolder(String cutFolderPath,  String pasteFolderPath) {
		//==========================================================================================
		Iterator<GeneratorTypeInfoData> generatorTypesIterator = this.generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext())
		{
			GeneratorTypeInfoData generatorTypeInfoData = generatorTypesIterator.next();
			
			String gtFolderPath = generatorTypeInfoData.getFolderPath();
			
			if (gtFolderPath.startsWith(cutFolderPath)) {
				//----------------------------------------------------------------------------------
				int beginIndex = gtFolderPath.lastIndexOf('/', cutFolderPath.length() - 2);
				
				String endPath = gtFolderPath.substring(beginIndex + 1);
				
				generatorTypeInfoData.setFolderPath(pasteFolderPath + endPath);
				
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
	 * @param moduleGeneratorTypeData
	 * 			is the Module-Generator.
	 */
	public void moveModule(String cutFolderPath, 
	                       String pasteFolderPath,
	                       ModuleGeneratorTypeInfoData moduleGeneratorTypeData) {
		//==========================================================================================
		boolean moduleMoved = false;
		
		String moduleName = moduleGeneratorTypeData.getGeneratorTypeName();
		
		//------------------------------------------------------------------------------------------
		Iterator<GeneratorTypeInfoData> generatorTypesIterator = this.generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext()) {
			GeneratorTypeInfoData generatorTypeInfoData = generatorTypesIterator.next();
			
			String gtFolderPath = generatorTypeInfoData.getFolderPath();
			String gtName = generatorTypeInfoData.getGeneratorTypeName();
			
			if (cutFolderPath.equals(gtFolderPath) && moduleName.equals(gtName)) {
				//----------------------------------------------------------------------------------
				generatorTypeInfoData.setFolderPath(pasteFolderPath);

				moduleMoved = true;
				
				//----------------------------------------------------------------------------------
				break;
			}
		}
		
		if (moduleMoved == false) {
			throw new RuntimeException("Moved module \"" + cutFolderPath + moduleGeneratorTypeData.getGeneratorTypeName() + "\" not found.");
		}
		
		//==========================================================================================
	}

	/**
	 * Check Module is used by other modules.
	 * 
	 * @param moduleGeneratorTypeData
	 * 			is the Module-Generator.
	 * @return
	 * 			<code>true</code> if module is in use.
	 */
	public boolean checkModuleIsUsed(ModuleGeneratorTypeInfoData moduleGeneratorTypeData) {
		//==========================================================================================
		boolean moduleIsUsed;
		
		moduleIsUsed = false;
		
		//------------------------------------------------------------------------------------------
		Iterator<GeneratorTypeInfoData> generatorTypesIterator = this.generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext()) {
			GeneratorTypeInfoData generatorTypeInfoData = generatorTypesIterator.next();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			if (generatorTypeInfoData instanceof ModuleGeneratorTypeInfoData) {
				ModuleGeneratorTypeInfoData checkedGeneratorTypeData = (ModuleGeneratorTypeInfoData) generatorTypeInfoData;
				
				Iterator<Generator> tracksIterator = checkedGeneratorTypeData.getTracksIterator();
				
				while (tracksIterator.hasNext()) {
					Generator trackGenerator = (Generator)tracksIterator.next();
					
					GeneratorTypeInfoData trackGeneratorTypeInfoData = trackGenerator.getGeneratorTypeData();
					
					if (moduleGeneratorTypeData == trackGeneratorTypeInfoData) {
						moduleIsUsed = true;
					}
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		//==========================================================================================
		return moduleIsUsed;
	}

	/**
	 * @param folderPath
	 * 			is the Folder-Path in format <code>"/folder1/folder2/"</code>.
	 * @return
	 * 			<code>true</code> if a module in folder or sub folder is in use.
	 */
	public boolean checkModuleInFolderIsUsed(String folderPath) {
		//==========================================================================================
		boolean moduleIsUsed;
		
		moduleIsUsed = false;
		
		//------------------------------------------------------------------------------------------
		Iterator<GeneratorTypeInfoData> generatorTypesIterator = this.generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext()) {
			GeneratorTypeInfoData generatorTypeInfoData = generatorTypesIterator.next();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			if (generatorTypeInfoData instanceof ModuleGeneratorTypeInfoData) {
				ModuleGeneratorTypeInfoData checkedGeneratorTypeData = (ModuleGeneratorTypeInfoData) generatorTypeInfoData;
				
				Iterator<Generator> tracksIterator = checkedGeneratorTypeData.getTracksIterator();
				
				while (tracksIterator.hasNext()) {
					Generator trackGenerator = (Generator)tracksIterator.next();
					
					GeneratorTypeInfoData trackGeneratorTypeInfoData = trackGenerator.getGeneratorTypeData();
					
					String gtFolderPath = trackGeneratorTypeInfoData.getFolderPath();
					
					if (gtFolderPath.startsWith(folderPath)) {
						moduleIsUsed = true;
					}
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		//==========================================================================================
		return moduleIsUsed;
	}

	/**
	 * @param folderPath
	 * 			is the Folder-Path in format <code>"/folder1/folder2/"</code>.
	 * @return
	 * 			<code>true</code> if a module in folder or sub folder is the main module.
	 */
	public boolean checkModuleInFolderIsMainModule(String folderPath) {
		//==========================================================================================
		boolean moduleIsMainModule;
		
		moduleIsMainModule = false;
		
		//------------------------------------------------------------------------------------------
		Iterator<GeneratorTypeInfoData> generatorTypesIterator = this.generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext()) {
			GeneratorTypeInfoData generatorTypeInfoData = generatorTypesIterator.next();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			if (generatorTypeInfoData instanceof ModuleGeneratorTypeInfoData) {
				ModuleGeneratorTypeInfoData checkedGeneratorTypeData = (ModuleGeneratorTypeInfoData) generatorTypeInfoData;
				
				String gtFolderPath = checkedGeneratorTypeData.getFolderPath();
				
				if (gtFolderPath.startsWith(folderPath)) {
					if (checkedGeneratorTypeData.getIsMainModuleGeneratorType() == true) {
						moduleIsMainModule = true;
						break;
					}
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		//==========================================================================================
		return moduleIsMainModule;
	}

	/**
	 * @param folderPath
	 * 			is the Folder-Path in format <code>"/folder1/folder2/"</code>.
	 */
	public void removeFolder(String folderPath) {
		//==========================================================================================
		Iterator<GeneratorTypeInfoData> generatorTypesIterator = this.generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext()) {
			GeneratorTypeInfoData generatorTypeInfoData = generatorTypesIterator.next();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			if (generatorTypeInfoData instanceof ModuleGeneratorTypeInfoData) {
				ModuleGeneratorTypeInfoData checkedGeneratorTypeData = (ModuleGeneratorTypeInfoData) generatorTypeInfoData;

				String gtFolderPath = checkedGeneratorTypeData.getFolderPath();
				
				if (gtFolderPath.startsWith(folderPath)) {
					generatorTypesIterator.remove();
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		//==========================================================================================
	}

	/**
	 * @param generatorTypeInfoData
	 * 			is the Module-Generator.
	 * @return
	 * 			<code>true</code> if Module-Generator is existing.
	 */
	public boolean checkGeneratorTypeIsExisting(GeneratorTypeInfoData generatorTypeInfoData) {
		//==========================================================================================
		boolean isExisting;
		
		isExisting = false;
		
		String folderPath = generatorTypeInfoData.getFolderPath();
		String generatorTypeName = generatorTypeInfoData.getGeneratorTypeName();
		
		//------------------------------------------------------------------------------------------
		Iterator<GeneratorTypeInfoData> generatorTypesIterator = this.generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext()) {
			GeneratorTypeInfoData gtData = generatorTypesIterator.next();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//			if (gtData instanceof ModuleGeneratorTypeData)
			{
//				ModuleGeneratorTypeData checkedGeneratorTypeData = (ModuleGeneratorTypeData)gtData;

				String gtFolderPath = gtData.getFolderPath();
				String gtName = gtData.getGeneratorTypeName();
				
				// Folder path and Module Name is the same?
				if (CompareUtils.compareWithNull(folderPath, gtFolderPath) && 
					CompareUtils.compareWithNull(generatorTypeName, gtName)) {
					isExisting = true;
					break;
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		//==========================================================================================
		return isExisting;
	}

	/**
	 * @param moduleGeneratorTypeData
	 * 			is the ModuleGenerator-Type-Data.
	 * @param generatorTypeName
	 * 			is the generator name.
	 * @param modulesMain
	 * 			<code>true</code> if the module generator is main.
	 * @return
	 * 			the last Main ModuleGenerator.</br>
	 * 			<code>null</code> if the given ModuleGenerator was main or 
	 * 			the generator is set to not main.
	 */
	public ModuleGeneratorTypeInfoData
	updateModuleGeneratorTypeData(ModuleGeneratorTypeInfoData moduleGeneratorTypeData,
								  String generatorTypeName,
								  Boolean modulesMain) {
		//==========================================================================================
		ModuleGeneratorTypeInfoData lastMainModuleGeneratorTypeData;
		
		//------------------------------------------------------------------------------------------
		// Set main module?
		if (modulesMain == true) {
			if (moduleGeneratorTypeData.getIsMainModuleGeneratorType() == false) {
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				lastMainModuleGeneratorTypeData = null;
				
				// Search last Main ModuleGenerator:
				
				Iterator<GeneratorTypeInfoData> generatorTypesIterator = this.generatorTypesData.getGeneratorTypesIterator();
				
				while (generatorTypesIterator.hasNext()) {
					GeneratorTypeInfoData generatorTypeInfoData = (GeneratorTypeInfoData)generatorTypesIterator.next();
					
					if (generatorTypeInfoData instanceof ModuleGeneratorTypeInfoData) {
						ModuleGeneratorTypeInfoData checkedModuleGeneratorTypeData = (ModuleGeneratorTypeInfoData) generatorTypeInfoData;
						
						if (checkedModuleGeneratorTypeData.getIsMainModuleGeneratorType() == true) {
							checkedModuleGeneratorTypeData.setIsMainModuleGeneratorType(false);
							
							lastMainModuleGeneratorTypeData = checkedModuleGeneratorTypeData;
							break;
						}
					}
				}
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				moduleGeneratorTypeData.setIsMainModuleGeneratorType(modulesMain);

				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			} else {
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				lastMainModuleGeneratorTypeData = null;
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			}
		}
		else
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			moduleGeneratorTypeData.setIsMainModuleGeneratorType(modulesMain);

			lastMainModuleGeneratorTypeData = null;
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		//------------------------------------------------------------------------------------------
		moduleGeneratorTypeData.setGeneratorTypeName(generatorTypeName);
		
		//==========================================================================================
		return lastMainModuleGeneratorTypeData;
	}

	/**
	 * @param looped
	 * 			<code>true</code> if sound looped between start and end time.
	 */
	public synchronized void submitLoopSound(boolean looped) {
		//==========================================================================================
		this.looped = looped;
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #looped}.
	 */
	public boolean retrieveLooped() {
		return this.looped;
	}

	/**
	 * @param looped 
	 * 			to set {@link #looped}.
	 */
	public void submitLooped(boolean looped) {
		this.looped = looped;
	}

	/**
	 * @return 
	 * 			returns the {@link #startTime}.
	 */
	public double retrieveStartTime() {
		return this.startTime;
	}

	/**
	 * @param startTime 
	 * 			to set {@link #startTime}.
	 */
	public void submitStartTime(double startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return 
	 * 			returns the {@link #endTime}.
	 */
	public double retrieveEndTime() {
		return this.endTime;
	}

	/**
	 * @param endTime 
	 * 			to set {@link #endTime}.
	 */
	public void submitEndTime(double endTime) {
		this.endTime = endTime;
	}
}
