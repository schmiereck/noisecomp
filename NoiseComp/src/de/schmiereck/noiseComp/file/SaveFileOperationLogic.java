package de.schmiereck.noiseComp.file;

import java.util.Iterator;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.TrackData;
import de.schmiereck.xmlTools.XMLData;
import de.schmiereck.xmlTools.XMLPort;
import de.schmiereck.xmlTools.XMLPortException;
import de.schmiereck.xmlTools.XMLRuntimeException;

/*
 * Created on 25.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	TODO docu type
 * </p>
 * 
 * @author smk
 * @version <p>25.03.2005:	created, smk</p>
 */
public class SaveFileOperationLogic
{

	public static void saveFile(GeneratorTypesData generatorTypesData,
								ModulGeneratorTypeData mainModulTypeData,
								String fileName) 
	throws XMLPortException
	{
		Document xmlDoc = XMLData.createDocument();
		
		Node noiseNode = XMLData.appendNode(xmlDoc, xmlDoc, "noise");

		XMLData.appendTextNode(xmlDoc, noiseNode, "version", "1.0.1");
		
		//-----------------------------------------------------
		// GeneratorTypesData:
		
		SaveFileOperationLogic.appendGeneratorTypes(xmlDoc, noiseNode, generatorTypesData);
		
		//-----------------------------------------------------
		// Generators:
		/*
		Generators mainGenerators = mainModulTypeData.getGenerators();
		
		SaveFileOperationLogic.appendGenerators(xmlDoc, noiseNode, mainGenerators);
		*/
		
		//-----------------------------------------------------
		XMLPort.save(fileName, xmlDoc);
	}

	private static void appendGeneratorTypes(Document xmlDoc, Node noiseNode, GeneratorTypesData generatorTypesData)
	{
		Node generatorTypesNode = XMLData.appendNode(xmlDoc, noiseNode, "generatorTypes");
		
		Iterator generatorTypesIterator = generatorTypesData.getGeneratorTypesIterator();

		while (generatorTypesIterator.hasNext())
		{
			GeneratorTypeData generatorTypeData = (GeneratorTypeData)generatorTypesIterator.next();
			
			SaveFileOperationLogic.appendGeneratorType(xmlDoc, generatorTypesNode, generatorTypeData);
		}
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
		Node generatorTypeNode = XMLData.appendNode(xmlDoc, generatorTypesNode, "generatorType");
		
		Node generatorTypeClassNameNode = XMLData.appendTextNode(xmlDoc, generatorTypeNode, "generatorTypeClassName", generatorTypeData.getClass().getName());

		String generatorClassName = generatorTypeData.getGeneratorTypeClassName();
		
		Node generatorClassNameNode = XMLData.appendTextNode(xmlDoc, generatorTypeNode, "generatorClassName", generatorClassName);
		Node generatorNameNode = XMLData.appendTextNode(xmlDoc, generatorTypeNode, "name", generatorTypeData.getGeneratorTypeName());
		Node generatorDescriptionNode = XMLData.appendTextNode(xmlDoc, generatorTypeNode, "description", generatorTypeData.getGeneratorTypeDescription());
		
		if (generatorTypeData instanceof ModulGeneratorTypeData)
		{	
			ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)generatorTypeData;
			
			if (modulGeneratorTypeData.getIsMainModulGeneratorType() == true)
			{
				Node isMainNode = XMLData.appendTextNode(xmlDoc, generatorTypeNode, "isMain", "true");
			}
		}
		
		Iterator inputTypesIterator = generatorTypeData.getInputTypesIterator();
		
		if (inputTypesIterator != null)
		{
			Node inputTypesNode = XMLData.appendNode(xmlDoc, generatorTypeNode, "inputTypes");
			
			while (inputTypesIterator.hasNext())
			{
				InputTypeData înputTypeData = (InputTypeData)inputTypesIterator.next();
				
				Node inputTypeNode = XMLData.appendNode(xmlDoc, inputTypesNode, "inputType");
				
				XMLData.appendTextNode(xmlDoc, inputTypeNode, "name", înputTypeData.getInputTypeName());
				XMLData.appendIntegerNode(xmlDoc, inputTypeNode, "type", înputTypeData.getInputType());
				XMLData.appendIntegerNode(xmlDoc, inputTypeNode, "countMin", înputTypeData.getInputCountMin());
				XMLData.appendIntegerNode(xmlDoc, inputTypeNode, "countMax", înputTypeData.getInputCountMax());
				XMLData.appendFloatNode(xmlDoc, inputTypeNode, "defaultValue", înputTypeData.getDefaultValue());
				XMLData.appendTextNode(xmlDoc, inputTypeNode, "description", înputTypeData.getInputTypeDescription());
			}
		}
		
		if (generatorTypeData instanceof ModulGeneratorTypeData)
		{	
			ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)generatorTypeData;
			
			//Generators modulGenerators = modulGeneratorTypeData.getGenerators();

			SaveFileOperationLogic.appendGenerators(xmlDoc, generatorTypeNode, modulGeneratorTypeData); //, modulGenerators);

			SaveFileOperationLogic.appendTracks(xmlDoc, generatorTypeNode, modulGeneratorTypeData);
		}
	}

	private static void appendGenerators(Document xmlDoc, 
										 Node generatorTypeNode,
										 ModulGeneratorTypeData modulGeneratorTypeData)
	{
		Node generatorsNode = XMLData.appendNode(xmlDoc, generatorTypeNode, "generators");
		
		Iterator generatorsIterator = modulGeneratorTypeData.getGeneratorsIterator();
		
		while (generatorsIterator.hasNext())
		{
			Generator generator = (Generator)generatorsIterator.next();
			
			Node generatorNode = XMLData.appendNode(xmlDoc, generatorsNode, "generator");
			
			String type;
			
			// Generator is a Module ?
			if (generator instanceof ModulGenerator)
			{
				ModulGenerator modulGenerator = (ModulGenerator)generator;
				
				String modulTypeName = modulGenerator.getGeneratorTypeData().getGeneratorTypeName();

				type = generator.getClass().getName() + "#" + modulTypeName;
			}
			else
			{
				type = generator.getClass().getName();
			}
			
			Node generatorTypeTypeNode = XMLData.appendTextNode(xmlDoc, generatorNode, "type", type);

			Node generatorNameNode = XMLData.appendTextNode(xmlDoc, generatorNode, "name", generator.getName());
			Node generatorStartTimeNode = XMLData.appendFloatNode(xmlDoc, generatorNode, "startTime", generator.getStartTimePos());
			Node generatorEndTimeNode = XMLData.appendFloatNode(xmlDoc, generatorNode, "endTime", generator.getEndTimePos());
			
			Iterator inputsIterator = generator.getInputsIterator();
			
			if (inputsIterator != null)
			{	
				Node inputsNode = XMLData.appendNode(xmlDoc, generatorNode, "inputs");
				
				while (inputsIterator.hasNext())
				{
					InputData înputData = (InputData)inputsIterator.next();
				
					Node inputNode = XMLData.appendNode(xmlDoc, inputsNode, "input");
					
					Generator inputGenerator = înputData.getInputGenerator();
					if (inputGenerator != null)
					{	
						Node inputGeneratorNameNode = XMLData.appendTextNode(xmlDoc, inputNode, "generatorName", inputGenerator.getName());
					}
					Node inputTypeNode = XMLData.appendIntegerNode(xmlDoc, inputNode, "type", înputData.getInputTypeData().getInputType());
					Node inputValueNode = XMLData.appendFloatNode(xmlDoc, inputNode, "value", înputData.getInputValue());
					Node inputStringValueNode = XMLData.appendTextNode(xmlDoc, inputNode, "stringValue", înputData.getInputStringValue());
					
					InputTypeData inputModulInputTypeData = înputData.getInputModulInputTypeData();
					
					if (inputModulInputTypeData != null)
					{
						Node inputModulInputNode = XMLData.appendIntegerNode(xmlDoc, inputNode, "inputModulInputType", inputModulInputTypeData.getInputType());
					}
				}
			}
		}
	}

	/**
	 * @param xmlDoc
	 * @param generatorTypeNode
	 * @param modulGeneratorTypeData
	 */
	private static void appendTracks(Document xmlDoc, Node generatorTypeNode, 
									 ModulGeneratorTypeData modulGeneratorTypeData)
	{
		Node tracksNode = XMLData.appendNode(xmlDoc, generatorTypeNode, "tracks");
		
		Iterator tracksIterator = modulGeneratorTypeData.getTracksIterator();
		
		while (tracksIterator.hasNext())
		{
			TrackData trackData = (TrackData)tracksIterator.next();
			
			Node trackNode = XMLData.appendNode(xmlDoc, tracksNode, "track");
			
			Node generatorNameNode = XMLData.appendTextNode(xmlDoc, trackNode, "generatorName", trackData.getGenerator().getName());
			
		}
	}
}
