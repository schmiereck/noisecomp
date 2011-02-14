/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.file;

import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
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
	public static ModulGeneratorTypeData importNoiseCompFile(GeneratorTypesData generatorTypesData,
	                                                         //ModulGeneratorTypeData mainModulTypeData,
	                                                         String fileName,
	                                                         float frameRate) 
	throws XMLPortException
	{
		//==========================================================================================
		ModulGeneratorTypeData mainModulGeneratorTypeData;
		
		//------------------------------------------------------------------------------------------
		mainModulGeneratorTypeData = 
			LoadFileOperationLogic.loadNoiseCompFile(generatorTypesData, 
			                                         fileName, 
			                                         frameRate);
		
		//==========================================================================================
		return mainModulGeneratorTypeData;
	}
}
