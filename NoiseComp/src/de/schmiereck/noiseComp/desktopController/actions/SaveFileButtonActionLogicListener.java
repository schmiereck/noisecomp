package de.schmiereck.noiseComp.desktopController.actions;

import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.xmlTools.XMLData;
import de.schmiereck.xmlTools.XMLPort;

/**
 * Load the actual generators definitions in the memory in a XML file.
 *
 * @author smk
 * @version 21.02.2004
 */
public class SaveFileButtonActionLogicListener 
implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic controllerLogic;
	private DesktopControllerData controllerData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public SaveFileButtonActionLogicListener(DesktopControllerLogic controllerLogic, DesktopControllerData controllerData)
	{
		super();
		
		this.controllerLogic = controllerLogic;
		this.controllerData = controllerData;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface#notifyButtonReleased(de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData)
	 */
	public void notifyButtonReleased(InputWidgetData buttonData)
	{
		String fileName = this.controllerData.getSaveFileNameInputlineData().getInputText();
		
		fileName = fileName.trim();
		
		if (fileName.length() > 0)
		{	
			if (fileName.endsWith(".xml") == false)
			{
				fileName = fileName.concat(".xml");
			}
			
			this.saveFile(fileName);
		
			this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());
		}
		else
		{
			// TODO show ERROR message
		}
	}

	private void saveFile(String fileName)
	{
		Document xmlDoc = XMLData.createDocument();
		
		Node noiseNode = XMLData.appendNode(xmlDoc, xmlDoc, "noise");

		XMLData.appendTextNode(xmlDoc, noiseNode, "version", "1.0.0");
		
		SoundData soundData = this.controllerData.getSoundData();
		
		//-----------------------------------------------------
		// GeneratorTypesData:
		
		GeneratorTypesData generatorTypesData = this.controllerData.getGeneratorTypesData();
		
		this.appendGeneratorTypes(xmlDoc, noiseNode, generatorTypesData);
		
		//-----------------------------------------------------
		// Generators:
		
		Generators generators = soundData.getGenerators();
		
		this.appendGenerators(xmlDoc, noiseNode, generators);
		
		XMLPort.save(fileName, xmlDoc);
	}

	private void appendGeneratorTypes(Document xmlDoc, Node noiseNode, GeneratorTypesData generatorTypesData)
	{
		Node generatorTypesNode = XMLData.appendNode(xmlDoc, noiseNode, "generatorTypes");
		
		Iterator generatorTypesIterator = generatorTypesData.getGeneratorTypesIterator();

		while (generatorTypesIterator.hasNext())
		{
			GeneratorTypeData generatorTypeData = (GeneratorTypeData)generatorTypesIterator.next();
			
			Node generatorTypeNode = XMLData.appendNode(xmlDoc, generatorTypesNode, "generatorType");
			
			Node generatorTypeClassNameNode = XMLData.appendTextNode(xmlDoc, generatorTypeNode, "generatorTypeClassName", generatorTypeData.getClass().getName());

			String generatorClassName = generatorTypeData.getGeneratorTypeClassName();
			
			Node generatorClassNameNode = XMLData.appendTextNode(xmlDoc, generatorTypeNode, "generatorClassName", generatorClassName);
			Node generatorNameNode = XMLData.appendTextNode(xmlDoc, generatorTypeNode, "name", generatorTypeData.getGeneratorTypeName());
			Node generatorDescriptionNode = XMLData.appendTextNode(xmlDoc, generatorTypeNode, "description", generatorTypeData.getGeneratorTypeDescription());
			
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
				
				Generators modulGenerators = modulGeneratorTypeData.getGenerators();

				this.appendGenerators(xmlDoc, generatorTypeNode, modulGenerators);
			}
		}
	}
	
	private void appendGenerators(Document xmlDoc, Node noiseNode, Generators generators)
	{
		Node generatorsNode = XMLData.appendNode(xmlDoc, noiseNode, "generators");
		
		Iterator generatorsIterator = generators.getGeneratorsIterator();
		
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
			
			Node generatorTypeNode = XMLData.appendTextNode(xmlDoc, generatorNode, "type", type);

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
				}
			}
		}
	}

}
