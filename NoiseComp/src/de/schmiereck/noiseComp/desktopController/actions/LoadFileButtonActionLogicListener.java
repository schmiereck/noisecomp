package de.schmiereck.noiseComp.desktopController.actions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.TracksData;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.xmlTools.XMLData;
import de.schmiereck.xmlTools.XMLException;
import de.schmiereck.xmlTools.XMLPort;

/**
 * TODO docu
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
			
				this.controllerData.getTracksData().clearTracks();
				
				SoundData soundData = this.controllerData.getSoundData();
			
				Node noiseNode = XMLData.selectSingleNode(xmlDoc, "/noise");

				//-----------------------------------------------------
				// GeneratorTypesData:
				
				this.createGeneratorTypes(noiseNode, soundData);//, loadFileGeneratorNodeDatas);
				
				//-----------------------------------------------------
				// Inserting the Generators.
				
				Generators generators = this.createGenerators(soundData, noiseNode);

				//-----------------------------------------------------
				// Generators updating in actual View:
				
				Iterator generatorsIterator = generators.getGeneratorsIterator();
				
				while (generatorsIterator.hasNext())
				{
					Generator generator = (Generator)generatorsIterator.next();
					
					this.controllerLogic.addGenerator(generator);
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

	private Generators createGenerators(SoundData soundData, Node rootNode)
	{
		Generators generators = new Generators();
		
		// List with temporarely {@link LoadFileGeneratorNodeData}-Objects.
		Vector loadFileGeneratorNodeDatas = new Vector();
		
		this.createGenerators(rootNode, soundData, generators, loadFileGeneratorNodeDatas);
		
		// Inserting the inputs:
		
		this.createGeneratorInputs(generators, loadFileGeneratorNodeDatas);
		
		return generators;
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
				
				GeneratorTypeData generatorTypeData = generatorTypesData.searchGeneratorTypeData(generatorTypeClassName);
				
				if (generatorTypeData != null)
				{
					throw new RuntimeException("generator type \"" + generatorTypeClassName + "\" allready exist");
				}
				
				Class generatorClass;
				try
				{
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
					
					InputTypeData inputTypeData = new InputTypeData(inputTypeType, inputTypeName, inputTypeCountMax, inputTypeCountMin, inputTypeDefaultValue);
	
					generatorTypeData.addInputTypeData(inputTypeData);
				}
				
				//--------------------------------------------------------
				// ModulGeneratorType:
				
				//if (generatorTypeClassName.equals(ModulGeneratorTypeData.class.getName()))
				if (generatorTypeData instanceof ModulGeneratorTypeData)	
				{
					ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)generatorTypeData;
					
					Generators modulGenerators = this.createGenerators(soundData, generatorTypeNode);
					
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
	private void createGenerators(Node noiseNode, SoundData soundData, Generators generators, Vector loadFileGeneratorNodeDatas)
	{
		Node generatorsNode = XMLData.selectSingleNode(noiseNode, "generators");

		NodeIterator generatorsNodeIterator = XMLData.selectNodeIterator(generatorsNode, "generator");

		Node generatorNode;

		GeneratorTypesData generatorTypesData = this.controllerData.getGeneratorTypesData();
		
		while ((generatorNode = generatorsNodeIterator.nextNode()) != null)
		{
			String generatorType = XMLData.selectSingleNodeText(generatorNode, "type");
			String generatorName = XMLData.selectSingleNodeText(generatorNode, "name");
			Float generatorStartTime = XMLData.selectSingleNodeFloat(generatorNode, "startTime");
			Float generatorEndTime = XMLData.selectSingleNodeFloat(generatorNode, "endTime");
			
			GeneratorTypeData generatorTypeData = generatorTypesData.searchGeneratorTypeData(generatorType);
			
			Generator generator;
			
			generator = generatorTypeData.createGeneratorInstance(generatorName, soundData.getFrameRate());

			if (generator != null)
			{					
				generator.setStartTimePos(generatorStartTime.floatValue());
				generator.setEndTimePos(generatorEndTime.floatValue());
				
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
				// TODO show ERROR message
				throw new RuntimeException("can't create generator by type: " + generatorTypeData);
			}
		}
	}

	/**
	 * Create and insert the Inputs for the Generator-Objects in the list.
	 * 
	 * @param loadFileGeneratorNodeDatas is a list with temporarely {@link LoadFileGeneratorNodeData}-Objects.
	 */
	private void createGeneratorInputs(Generators generators, Vector loadFileGeneratorNodeDatas)
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
					
					generators.addInput(generator, inputGeneratorName, inputType, inputValue);
				}
			}
		}
	}

}
