package de.schmiereck.noiseComp.desktopController.actions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import de.schmiereck.noiseComp.PopupRuntimeException;
import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.TrackData;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.xmlTools.XMLData;
import de.schmiereck.xmlTools.XMLException;
import de.schmiereck.xmlTools.XMLPort;

/**
 * Load a generators definition from a XML file into memory.
 *
 * @author smk
 * @version 21.02.2004
 */
public class LoadFileButtonActionLogicListener 
implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic controllerLogic;
	private DesktopControllerData controllerData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public LoadFileButtonActionLogicListener(DesktopControllerLogic controllerLogic, DesktopControllerData controllerData)
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
		String fileName = this.controllerData.getLoadFileNameInputlineData().getInputText();
		
		fileName = fileName.trim();
		
		if (fileName.length() > 0)
		{	
			if (fileName.endsWith(".xml") == false)
			{
				fileName = fileName.concat(".xml");
			}
			
			Document xmlDoc;
			
			try
			{
				xmlDoc = XMLPort.open(fileName);
			
				this.controllerData.clearTracks();
				
				SoundData soundData = this.controllerData.getSoundData();

				Node noiseNode = XMLData.selectSingleNode(xmlDoc, "/noise");

				String version = XMLData.selectSingleNodeText(noiseNode, "version");
				
				//-----------------------------------------------------
				// GeneratorTypesData:
				
				this.createGeneratorTypes(noiseNode, soundData);//, loadFileGeneratorNodeDatas);
				
				//-----------------------------------------------------
				// Inserting the Generators.
				
				Generators mainGenerators = this.controllerData.getMainGenerators();
				
				this.createGenerators(noiseNode, soundData, mainGenerators, null);

				//-----------------------------------------------------
				// Generators updating in actual View:
				
				Iterator generatorsIterator = mainGenerators.getGeneratorsIterator();
				
				while (generatorsIterator.hasNext())
				{
					Generator generator = (Generator)generatorsIterator.next();
					
					this.controllerLogic.addTrackData(new TrackData(generator));
				}
			}
			catch (XMLException ex)
			{
				// TODO show ERROR message
				throw ex;
			}
			
			this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());
		}
		else
		{
			// TODO show ERROR message
			throw new RuntimeException("file name is empty");
		}
	}

	/**
	 * Creates the list of generatores descripted below the 'rootNode'.
	 * 
	 * @param rootNode
	 * @param soundData
	 * @param generators
	 * @param parentModulGenerator
	 */
	private void createGenerators(Node rootNode, SoundData soundData, Generators generators, ModulGeneratorTypeData modulGeneratorTypeData) //, ModulGenerator parentModulGenerator)
	{
		// List with temporarely {@link LoadFileGeneratorNodeData}-Objects.
		Vector loadFileGeneratorNodeDatas = new Vector();
		
		this.createGenerators(rootNode, soundData, generators, loadFileGeneratorNodeDatas, modulGeneratorTypeData); //, parentModulGenerator);
		
		// Inserting the inputs:
		
		this.createGeneratorInputs(generators, loadFileGeneratorNodeDatas, modulGeneratorTypeData);
	}

	private void createGeneratorTypes(Node noiseNode, SoundData soundData)//, Vector loadFileGeneratorNodeDatas)
	{
		Node generatorTypesNode = XMLData.selectSingleNode(noiseNode, "generatorTypes");

		if (generatorTypesNode != null)
		{
			GeneratorTypesData generatorTypesData = this.controllerData.getGeneratorTypesData();
		
			generatorTypesData.clear();
			
			NodeIterator generatorTypesNodeIterator = XMLData.selectNodeIterator(generatorTypesNode, "generatorType");
	
			Node generatorTypeNode;
	
			while ((generatorTypeNode = generatorTypesNodeIterator.nextNode()) != null)
			{
				String generatorTypeClassName = XMLData.selectSingleNodeText(generatorTypeNode, "generatorTypeClassName");
				String generatorClassName = XMLData.selectSingleNodeText(generatorTypeNode, "generatorClassName");
				String generatorTypeName = XMLData.selectSingleNodeText(generatorTypeNode, "name");
				String generatorTypeDescription = XMLData.selectSingleNodeText(generatorTypeNode, "description");
				String generatorModulTypeName;	// Not realy needed, because 'generatorTypeName' should be the same.
				
				GeneratorTypeData generatorTypeData = generatorTypesData.searchGeneratorTypeData(generatorTypeClassName);
				
				if (generatorTypeData != null)
				{
					throw new RuntimeException("generator type \"" + generatorTypeClassName + "\" allready exist");
				}
				
				Class generatorClass;
				try
				{
					int namePartPos = generatorClassName.indexOf("#");

					// Class name with appended name of a generic modul type ?
					if (namePartPos != -1)
					{
						generatorModulTypeName = generatorClassName.substring(namePartPos + 1);
						generatorClassName = generatorClassName.substring(0, namePartPos);
					}
					else
					{
						generatorModulTypeName = null;
					}
					generatorClass = Class.forName(generatorClassName);
				}
				catch (ClassNotFoundException ex)
				{
					throw new RuntimeException("Class not found", ex);
				}
				
				generatorTypeData = this.createGeneratorTypeData(generatorTypeClassName, generatorClass, generatorTypeName, generatorTypeDescription);
				
				generatorTypesData.addGeneratorTypeData(generatorTypeData);
				
				//--------------------------------------------------------
				// inputTypes:
				
				Node inputTypesNode = XMLData.selectSingleNode(generatorTypeNode, "inputTypes");
	
				NodeIterator inputTypeNodesIterator = XMLData.selectNodeIterator(inputTypesNode, "inputType");
	
				Node inputTypeNode;
	
				while ((inputTypeNode = inputTypeNodesIterator.nextNode()) != null)
				{
					String inputTypeName = XMLData.selectSingleNodeText(inputTypeNode, "name");
					Integer inputTypeType = XMLData.selectSingleNodeInteger(inputTypeNode, "type");
					Integer inputTypeCountMin = XMLData.selectSingleNodeInteger(inputTypeNode, "countMin");
					Integer inputTypeCountMax = XMLData.selectSingleNodeInteger(inputTypeNode, "countMax");
					Float inputTypeDefaultValue = XMLData.selectSingleNodeFloat(inputTypeNode, "defaultValue");
					String inputDescription = XMLData.selectSingleNodeText(inputTypeNode, "description");
					
					InputTypeData inputTypeData = new InputTypeData(inputTypeType, inputTypeName, inputTypeCountMax, inputTypeCountMin, inputTypeDefaultValue, inputDescription);
	
					generatorTypeData.addInputTypeData(inputTypeData);
				}
				
				//--------------------------------------------------------
				// ModulGeneratorType:
				
				//if (generatorTypeClassName.equals(ModulGeneratorTypeData.class.getName()))
				if (generatorTypeData instanceof ModulGeneratorTypeData)	
				{
					ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)generatorTypeData;
					
					Generators modulGenerators = new Generators();
					
					this.createGenerators(generatorTypeNode, soundData, modulGenerators, modulGeneratorTypeData);
					
					modulGeneratorTypeData.setGenerators(modulGenerators);
				}
			}
		}
	}

	public GeneratorTypeData createGeneratorTypeData(String generatorTypeClassName, Class generatorClass, String generatorTypeName, String generatorTypeDescription)
	{
		GeneratorTypeData generatorTypeData = null;
		
		Class generatorTypeDataClass;
		
		try
		{
			generatorTypeDataClass = Class.forName(generatorTypeClassName);
		}
		catch (ClassNotFoundException ex)
		{
			throw new RuntimeException("class not found: " + generatorTypeClassName, ex);
		}
		
		Class[]	params		= new Class[3];
		
		params[0] = Class.class;	// generatorClass
		params[1] = String.class;	// generatorTypeName
		params[2] = String.class;	// generatorTypeDescription
		
		try
		{
			Constructor generatorConstructor = generatorTypeDataClass.getConstructor(params);
			Object[]	args	= new Object[3];

			args[0] = generatorClass;
			args[1] = generatorTypeName;
			args[2] = generatorTypeDescription;
			
			try
			{
				//pageView = (HTMLPageView)Class.forName(pageViewClassName).newInstance();
				generatorTypeData = (GeneratorTypeData)generatorConstructor.newInstance(args);
			}
			catch (java.lang.InstantiationException ex)
			{
				throw new RuntimeException("new instance: " + generatorTypeClassName, ex);
			}
			catch (java.lang.IllegalAccessException ex)
			{
				throw new RuntimeException("access exception for class: " + generatorTypeClassName, ex);
			}
			//catch (java.lang.ClassNotFoundException ex)
			//{
			//	throw new RuntimeException("class not found: " + pageViewClassName, ex);
			//} 
			catch (IllegalArgumentException ex)
			{
				throw new RuntimeException("illegal argument: " + generatorTypeClassName, ex);
			} 
			catch (InvocationTargetException ex)
			{
				throw new RuntimeException("invocation target: " + generatorTypeClassName, ex);
			}
		}
		catch (java.lang.NoSuchMethodException ex)
		{
			throw new RuntimeException("no such method exception for class: " + generatorTypeClassName, ex);
		}
		
		return generatorTypeData;
	}
	
	/**
	 * 
	 * @param noiseNode is the root Node of the Generators-XML.
	 * @param soundData is the empty sound data object the generators are inserted.
	 * @param loadFileGeneratorNodeDatas is a list with temporarely {@link LoadFileGeneratorNodeData}-Objects.
	 */
	private void createGenerators(Node noiseNode, SoundData soundData, 
			Generators generators, Vector loadFileGeneratorNodeDatas, ModulGeneratorTypeData modulGeneratorTypeData) 
			//ModulGenerator parentModulGenerator)
	{
		Node generatorsNode = XMLData.selectSingleNode(noiseNode, "generators");

		NodeIterator generatorsNodeIterator = XMLData.selectNodeIterator(generatorsNode, "generator");

		Node generatorNode;

		GeneratorTypesData generatorTypesData = this.controllerData.getGeneratorTypesData();
		
		while ((generatorNode = generatorsNodeIterator.nextNode()) != null)
		{
			String generatorType = XMLData.selectSingleNodeText(generatorNode, "type");
			//String generatorModulTypeName = XMLData.selectSingleNodeText(generatorNode, "modulTypeName");
			String generatorName = XMLData.selectSingleNodeText(generatorNode, "name");
			Float generatorStartTime = XMLData.selectSingleNodeFloat(generatorNode, "startTime");
			Float generatorEndTime = XMLData.selectSingleNodeFloat(generatorNode, "endTime");
			
			GeneratorTypeData generatorTypeData = generatorTypesData.searchGeneratorTypeData(generatorType);
			
			Generator generator;
			
			generator = generatorTypeData.createGeneratorInstance(generatorName, soundData.getFrameRate()); //, parentModulGenerator);

			if (generator != null)
			{					
				generator.setStartTimePos(generatorStartTime.floatValue());
				generator.setEndTimePos(generatorEndTime.floatValue());

				/*
				// Generator is a Module ?
				if (generator instanceof ModulGenerator)
				{
					ModulGenerator modulGenerator = (ModulGenerator)generator;
					
					String modulTypeName = XMLData.selectSingleNodeText(generatorNode, "modulTypeName");
					
					modulGenerator.getGeneratorTypeData();
				}
				*/
				//this.controllerLogic.addGenerator(generator);
				generators.addGenerator(generator);
				
				if (generator instanceof OutputGenerator)
				{	
					generators.setOutputGenerator((OutputGenerator)generator);
				}
				
				loadFileGeneratorNodeDatas.add(new LoadFileGeneratorNodeData(generator, generatorNode));
			}
			else
			{
				throw new PopupRuntimeException("can't create generator by type: " + generatorTypeData);
			}
		}
	}

	/**
	 * Create and insert the Inputs for the Generator-Objects in the list.
	 * 
	 * @param loadFileGeneratorNodeDatas is a list with temporarely {@link LoadFileGeneratorNodeData}-Objects.
	 */
	private void createGeneratorInputs(Generators generators, Vector loadFileGeneratorNodeDatas, ModulGeneratorTypeData modulGeneratorTypeData)
	{
		Iterator loadFileGeneratorNodeDatasIterator = loadFileGeneratorNodeDatas.iterator();
		
		while (loadFileGeneratorNodeDatasIterator.hasNext())
		{
			LoadFileGeneratorNodeData loadFileGeneratorNodeData = (LoadFileGeneratorNodeData)loadFileGeneratorNodeDatasIterator.next();
			
			Generator generator = loadFileGeneratorNodeData.getGenerator();
			Node generatorNode = loadFileGeneratorNodeData.getGeneratorNode();
			
			Node inputsNode = XMLData.selectSingleNode(generatorNode, "inputs");
			
			if (inputsNode != null)
			{	
				NodeIterator inputNodesIterator = XMLData.selectNodeIterator(inputsNode, "input");
	
				Node inputNode;
				
				while ((inputNode = inputNodesIterator.nextNode()) != null)
				{
					String inputGeneratorName = XMLData.selectSingleNodeText(inputNode, "generatorName");
					Integer inputType = XMLData.selectSingleNodeInteger(inputNode, "type");
					Float inputValue = XMLData.selectSingleNodeFloat(inputNode, "value");
					Integer inputModulInputType = XMLData.selectSingleNodeInteger(inputNode, "inputModulInputType");
					
					Generator inputGenerator = generators.searchGenerator(inputGeneratorName);
					
					InputTypeData inputTypeData = generator.getGeneratorTypeData().getInputTypeData(inputType.intValue());
					
					InputTypeData inputModulInputTypeData;
					
					if (inputModulInputType != null)
					{	
						inputModulInputTypeData = modulGeneratorTypeData.getInputTypeData(inputModulInputType.intValue());
					}
					else
					{	
						inputModulInputTypeData = null;
					}
					
					//generators.addInput(generator, inputGenerator, inputTypeData, inputValue, inputModulInputTypeData);
					InputData inputData = generator.addInputGenerator(inputGenerator, inputTypeData, inputValue, inputModulInputTypeData);
				}
			}
		}
	}
}
