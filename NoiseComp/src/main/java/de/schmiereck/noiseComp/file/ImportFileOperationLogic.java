/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.file;

import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeInfoData;
import de.schmiereck.noiseComp.soundSource.SoundSourceData;
import de.schmiereck.xmlTools.XMLPortException;

/**
 * <p>
 * 	Import-File-Operation Logic.
 * </p>
 * 
 * @author smk
 * @version <p>14.02.2011:	created, smk</p>
 */
public class ImportFileOperationLogic
{

	/**
	 * Import a NoiseComp XML File into the generator types object and the
	 * main generator object.
	 * 
	 * @param fileName
	 * 			is the file name of the xml file to import.
	 * @throws XMLPortException
	 * @throws MainActionException
	 */
	public static ModuleGeneratorTypeInfoData importNoiseCompFile(final SoundSourceData soundSourceData,
                                                                  GeneratorTypesData generatorTypesData,
                                                                  //ModuleGeneratorTypeData mainModuleTypeData,
                                                                  String fileName,
                                                                  float frameRate)
	throws XMLPortException
	{
		//==========================================================================================
		ModuleGeneratorTypeInfoData mainModuleGeneratorTypeData;
		
		//------------------------------------------------------------------------------------------
		mainModuleGeneratorTypeData = 
			LoadFileOperationLogic.loadNoiseCompFile(soundSourceData, generatorTypesData,
			                                         fileName, 
			                                         frameRate);
		
		//==========================================================================================
		return mainModuleGeneratorTypeData;
	}
}
