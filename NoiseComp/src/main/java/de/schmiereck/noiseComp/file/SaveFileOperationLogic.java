package de.schmiereck.noiseComp.file;

import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import de.schmiereck.noiseComp.Version;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;
import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeData;
import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeData.TicksPer;
import de.schmiereck.xmlTools.XMLData;
import de.schmiereck.xmlTools.XMLPort;
import de.schmiereck.xmlTools.XMLPortException;
import de.schmiereck.xmlTools.XMLRuntimeException;

/*
 * Created on 25.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	Save-File-Operation Logic.
 * </p>
 * 
 * @author smk
 * @version <p>25.03.2005:	created, smk</p>
 */
public class SaveFileOperationLogic
{

	public static void saveFile(GeneratorTypesData generatorTypesData,
								ModuleGeneratorTypeData mainModuleypeData,
								String fileName) 
	throws XMLPortException
	{
		//==========================================================================================
		Document xmlDoc = XMLData.createDocument();
		
		Node noiseNode = XMLData.appendNode(xmlDoc, xmlDoc, "noise");

		XMLData.appendTextNode(xmlDoc, noiseNode, "version", Version.version);
		
		//-----------------------------------------------------
		// GeneratorTypesData:
		
		SaveFileOperationLogic.appendGeneratorTypes(xmlDoc, noiseNode, generatorTypesData);
		
		//-----------------------------------------------------
		// Generators:
		/*
		Generators mainGenerators = mainModuleypeData.getGenerators();
		
		SaveFileOperationLogic.appendGenerators(xmlDoc, noiseNode, mainGenerators);
		*/
		
		//-----------------------------------------------------
		XMLPort.save(fileName, xmlDoc);
		//==========================================================================================
	}

	private static void appendGeneratorTypes(Document xmlDoc, Node noiseNode, GeneratorTypesData generatorTypesData)
	{
		//==========================================================================================
		Node generatorTypesNode = XMLData.appendNode(xmlDoc, noiseNode, "generatorTypes");
		
		Iterator<GeneratorTypeData> generatorTypesIterator = generatorTypesData.getGeneratorTypesIterator();

		while (generatorTypesIterator.hasNext())
		{
			GeneratorTypeData generatorTypeData = generatorTypesIterator.next();
			
			SaveFileOperationLogic.appendGeneratorType(xmlDoc, generatorTypesNode, generatorTypeData);
		}
		//==========================================================================================
	}
	
	/**
	 * @param xmlDoc
	 * @param generatorTypesNode
	 * @param generatorTypeData
	 * @throws XMLRuntimeException
	 */
	private static void appendGeneratorType(Document xmlDoc, 
											Node generatorTypesNode, 
											GeneratorTypeData generatorTypeData)
		throws XMLRuntimeException
	{
		//==========================================================================================
		Node generatorTypeNode = XMLData.appendNode(xmlDoc, generatorTypesNode, "generatorType");
		
//		Node generatorTypeClassNameNode = 
			XMLData.appendTextNode(xmlDoc, generatorTypeNode, "generatorTypeClassName", generatorTypeData.getClass().getName());

		String generatorClassName = generatorTypeData.getGeneratorTypeClassName();
		
//		Node generatorClassNameNode = 
			XMLData.appendTextNode(xmlDoc, generatorTypeNode, "generatorClassName", generatorClassName);
//		Node folderPathNode = 
			XMLData.appendTextNode(xmlDoc, generatorTypeNode, "folderPath", generatorTypeData.getFolderPath());
//		Node generatorNameNode = 
			XMLData.appendTextNode(xmlDoc, generatorTypeNode, "name", generatorTypeData.getGeneratorTypeName());
//		Node generatorDescriptionNode = 
			XMLData.appendTextNode(xmlDoc, generatorTypeNode, "description", generatorTypeData.getGeneratorTypeDescription());
		
		if (generatorTypeData instanceof ModuleGeneratorTypeData)
		{	
			ModuleGeneratorTypeData moduleGeneratorTypeData = (ModuleGeneratorTypeData)generatorTypeData;
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			if (moduleGeneratorTypeData.getIsMainModuleGeneratorType() == true)
			{
//				Node isMainNode = 
					XMLData.appendTextNode(xmlDoc, generatorTypeNode, "isMain", "true");
			}
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			Node viewNode = XMLData.appendNode(xmlDoc, generatorTypeNode, "view");
			
			{
				Float viewZoomX = moduleGeneratorTypeData.getViewZoomX();
				
				XMLData.appendFloatNode(xmlDoc, viewNode, "zoomX", viewZoomX);
			}
			{
				Float viewTicksCount = moduleGeneratorTypeData.getViewTicksCount();
				
				XMLData.appendFloatNode(xmlDoc, viewNode, "ticksCount", viewTicksCount);
			}
			{
				TicksPer viewTicksPer = moduleGeneratorTypeData.getViewTicksPer();
				String ticksPer;
				
				if (viewTicksPer != null)
				{
					ticksPer = viewTicksPer.name();
				}
				else
				{
					ticksPer = null;
				}
				
				XMLData.appendTextNode(xmlDoc, viewNode, "ticksPer", ticksPer);
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		
		Iterator<InputTypeData> inputTypesIterator = generatorTypeData.getInputTypesIterator();
		
		if (inputTypesIterator != null)
		{
			Node inputTypesNode = XMLData.appendNode(xmlDoc, generatorTypeNode, "inputTypes");
			
			while (inputTypesIterator.hasNext())
			{
				InputTypeData InputTypeData = inputTypesIterator.next();
				
				Node inputTypeNode = XMLData.appendNode(xmlDoc, inputTypesNode, "inputType");
				
				XMLData.appendTextNode(xmlDoc, inputTypeNode, "name", InputTypeData.getInputTypeName());
				XMLData.appendIntegerNode(xmlDoc, inputTypeNode, "type", InputTypeData.getInputType());
				XMLData.appendIntegerNode(xmlDoc, inputTypeNode, "countMin", InputTypeData.getInputCountMin());
				XMLData.appendIntegerNode(xmlDoc, inputTypeNode, "countMax", InputTypeData.getInputCountMax());
				XMLData.appendFloatNode(xmlDoc, inputTypeNode, "defaultValue", InputTypeData.getDefaultValue());
				XMLData.appendTextNode(xmlDoc, inputTypeNode, "description", InputTypeData.getInputTypeDescription());
			}
		}
		
		if (generatorTypeData instanceof ModuleGeneratorTypeData)
		{	
			ModuleGeneratorTypeData moduleGeneratorTypeData = (ModuleGeneratorTypeData)generatorTypeData;
			
			//Generators moduleenerators = moduleeneratorTypeData.getGenerators();

			SaveFileOperationLogic.appendGenerators(xmlDoc, generatorTypeNode, moduleGeneratorTypeData); //, moduleenerators);

			SaveFileOperationLogic.appendTracks(xmlDoc, generatorTypeNode, moduleGeneratorTypeData);
		}
		//==========================================================================================
	}

	private static void appendGenerators(Document xmlDoc, 
										 Node generatorTypeNode,
										 ModuleGeneratorTypeData moduleGeneratorTypeData)
	{
		//==========================================================================================
		Node generatorsNode = XMLData.appendNode(xmlDoc, generatorTypeNode, "generators");
		
		Iterator<Generator> generatorsIterator = moduleGeneratorTypeData.getGeneratorsIterator();
		
		while (generatorsIterator.hasNext())
		{
			Generator generator = generatorsIterator.next();
			
			Node generatorNode = XMLData.appendNode(xmlDoc, generatorsNode, "generator");
			
			String folderPath;
			String type;
			
			// Generator is a Module ?
			if (generator instanceof ModuleGenerator)
			{
				ModuleGenerator moduleGenerator = (ModuleGenerator)generator;
				
				GeneratorTypeData generatorTypeData = moduleGenerator.getGeneratorTypeData();
				
				folderPath = generatorTypeData.getFolderPath();
				
				String moduleypeName = generatorTypeData.getGeneratorTypeName();

				type = generator.getClass().getName() + "#" + moduleypeName;
			}
			else
			{
				folderPath = null;
				type = generator.getClass().getName();
			}
			
//			Node folderPathNode = 
				XMLData.appendTextNode(xmlDoc, generatorNode, "folderPath", folderPath);
				
//			Node generatorTypeTypeNode = 
				XMLData.appendTextNode(xmlDoc, generatorNode, "type", type);

//			Node generatorNameNode = 
				XMLData.appendTextNode(xmlDoc, generatorNode, "name", generator.getName());
//			Node generatorStartTimeNode = 
				XMLData.appendFloatNode(xmlDoc, generatorNode, "startTime", generator.getStartTimePos());
//			Node generatorEndTimeNode = 
				XMLData.appendFloatNode(xmlDoc, generatorNode, "endTime", generator.getEndTimePos());
			
			Iterator<InputData> inputsIterator = generator.getInputsIterator();
			
			if (inputsIterator != null)
			{	
				Node inputsNode = XMLData.appendNode(xmlDoc, generatorNode, "inputs");
				
				while (inputsIterator.hasNext())
				{
					InputData InputData = inputsIterator.next();
				
					Node inputNode = XMLData.appendNode(xmlDoc, inputsNode, "input");
					
					Generator inputGenerator = InputData.getInputGenerator();
					if (inputGenerator != null)
					{	
//						Node inputGeneratorNameNode = 
							XMLData.appendTextNode(xmlDoc, inputNode, "generatorName", inputGenerator.getName());
					}
//					Node inputTypeNode = 
						XMLData.appendIntegerNode(xmlDoc, inputNode, "type", InputData.getInputTypeData().getInputType());
//					Node inputValueNode = 
						XMLData.appendFloatNode(xmlDoc, inputNode, "value", InputData.getInputValue());
//					Node inputStringValueNode = 
						XMLData.appendTextNode(xmlDoc, inputNode, "stringValue", InputData.getInputStringValue());
					
					InputTypeData inputModuleInputTypeData = InputData.getInputModuleInputTypeData();
					
					if (inputModuleInputTypeData != null)
					{
//						Node inputModuleInputNode = 
							XMLData.appendIntegerNode(xmlDoc, inputNode, "inputModuleInputType", inputModuleInputTypeData.getInputType());
					}
				}
			}
		}
		//==========================================================================================
	}

	/**
	 * @param xmlDoc
	 * @param generatorTypeNode
	 * @param moduleGeneratorTypeData
	 */
	private static void appendTracks(Document xmlDoc, Node generatorTypeNode, 
									 ModuleGeneratorTypeData moduleGeneratorTypeData)
	{
		//==========================================================================================
		Node tracksNode = XMLData.appendNode(xmlDoc, generatorTypeNode, "tracks");
		
		Iterator<Generator> tracksIterator = moduleGeneratorTypeData.getTracksIterator();
		
		while (tracksIterator.hasNext())
		{
			Generator trackData = tracksIterator.next();
			
			Node trackNode = XMLData.appendNode(xmlDoc, tracksNode, "track");
			
//			Node generatorNameNode = 
				XMLData.appendTextNode(xmlDoc, trackNode, "generatorName", trackData.getName());
			
		}
		//==========================================================================================
	}
}
