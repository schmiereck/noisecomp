package de.schmiereck.noiseComp.file;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Vector;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import de.schmiereck.noiseComp.PopupRuntimeException;
//import de.schmiereck.noiseComp.desktopController.actions.LoadFileButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.old.LoadFileGeneratorNodeData;
import de.schmiereck.noiseComp.desktopPage.widgets.MainActionException;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.TrackData;
import de.schmiereck.xmlTools.XMLData;
import de.schmiereck.xmlTools.XMLException;
import de.schmiereck.xmlTools.XMLPort;
import de.schmiereck.xmlTools.XMLPortException;

/*
 * Created on 25.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	Provedes functions to load and save NoiseComp XML files.
 * </p>
 * 
 * @author smk
 * @version <p>25.03.2005:	created, smk</p>
 */
public class LoadFileOperationLogic
{

	/**
	 * Load a NoiseComp XML File into the generator types object and the
	 * main genrator object.
	 * 
	 * @param fileName
	 * 			is the file name of the xml file to load.
	 * @throws XMLPortException
	 * @throws MainActionException
	 */
	public static ModulGeneratorTypeData loadNoiseCompFile(GeneratorTypesData generatorTypesData,
														   //ModulGeneratorTypeData mainModulTypeData,
														   String fileName,
														   float frameRate) 
	throws XMLPortException
	{
		Document xmlDoc;
		
		xmlDoc = XMLPort.open(fileName);
		
		Node noiseNode = XMLData.selectSingleNode(xmlDoc, "/noise");

		String version = XMLData.selectSingleNodeText(noiseNode, "version");
		
		//-----------------------------------------------------
		// GeneratorTypesData:
		
		ModulGeneratorTypeData mainModulGeneratorTypeData;
		
		mainModulGeneratorTypeData = LoadFileOperationLogic.createGeneratorTypes(generatorTypesData,
																				 noiseNode, 
																				 frameRate);//, loadFileGeneratorNodeDatas);
		
		// Old file version without any main module?
		if (mainModulGeneratorTypeData == null)
		{
			//-----------------------------------------------------
			// Generate a new one:
			
			mainModulGeneratorTypeData = ModulGenerator.createModulGeneratorTypeData();
			
			mainModulGeneratorTypeData.setIsMainModulGeneratorType(true);
			
			mainModulGeneratorTypeData.setGeneratorTypeName("Main-Modul");
			
			//-----------------------------------------------------
			// Inserting the Generators.
			
			//Generators mainGenerators = mainModulGeneratorTypeData.getGenerators();
			
			LoadFileOperationLogic.createGenerators(generatorTypesData,
													noiseNode, 
													frameRate, 
													//mainGenerators, 
													mainModulGeneratorTypeData);
			
			//-----------------------------------------------------
			// Add as new Type:
			
			generatorTypesData.addGeneratorTypeData(mainModulGeneratorTypeData);
		}
		
		return mainModulGeneratorTypeData;
	}

	/**
	 * Creates the list of generatores descripted below the 'rootNode'.
	 * 
	 * @param rootNode
	 * @param generators
	 * @param parentModulGenerator
	 */
	private static void createGenerators(GeneratorTypesData generatorTypesData,
										 Node rootNode, float frameRate, 
										 //Generators generators, 
										 ModulGeneratorTypeData modulGeneratorTypeData) //, ModulGenerator parentModulGenerator)
	{
		// List with temporarely {@link LoadFileGeneratorNodeData}-Objects.
		Vector loadFileGeneratorNodeDatas = new Vector();
		
		LoadFileOperationLogic.createGenerators(generatorTypesData,
												rootNode, frameRate, //generators, 
												loadFileGeneratorNodeDatas, 
												modulGeneratorTypeData); //, parentModulGenerator);
		
		// Inserting the inputs:
		
		LoadFileOperationLogic.createGeneratorInputs(//generators, 
												  loadFileGeneratorNodeDatas, 
												  modulGeneratorTypeData);
	}

	private static ModulGeneratorTypeData createGeneratorTypes(GeneratorTypesData generatorTypesData,
															   Node noiseNode, 
															   float frameRate)//, Vector loadFileGeneratorNodeDatas)
	{
		ModulGeneratorTypeData mainModulGeneratorTypeData = null;
		
		Node generatorTypesNode = XMLData.selectSingleNode(noiseNode, "generatorTypes");

		if (generatorTypesNode != null)
		{
			generatorTypesData.clear();
			
			NodeIterator generatorTypesNodeIterator = XMLData.selectNodeIterator(generatorTypesNode, "generatorType");
	
			Node generatorTypeNode;
	
			while ((generatorTypeNode = generatorTypesNodeIterator.nextNode()) != null)
			{
				GeneratorTypeData generatorTypeData = createGeneratorType(generatorTypesData, generatorTypeNode, frameRate);
				
				if (generatorTypeData instanceof ModulGeneratorTypeData)	
				{
					ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)generatorTypeData;
				
					if (modulGeneratorTypeData.getIsMainModulGeneratorType() == true)
					{
						mainModulGeneratorTypeData = modulGeneratorTypeData;
					}
				}
			}
		}
		
		return mainModulGeneratorTypeData;
	}

	private static GeneratorTypeData createGeneratorType(GeneratorTypesData generatorTypesData,
														 Node generatorTypeNode, 
														 float frameRate)//, Vector loadFileGeneratorNodeDatas)
	{
		//ModulGeneratorTypeData modulGeneratorTypeData = null;
		
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
		
		generatorTypeData = LoadFileOperationLogic.createGeneratorTypeData(generatorTypeClassName, 
																		   generatorClass, 
																		   generatorTypeName, 
																		   generatorTypeDescription);
		
		if (generatorTypeData instanceof ModulGeneratorTypeData)
		{
			ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)generatorTypeData;
			
			String isMainName = XMLData.selectSingleNodeText(generatorTypeNode, "isMain");

			if ("true".equals(isMainName) == true)
			{
				modulGeneratorTypeData.setIsMainModulGeneratorType(true);
			}
		}

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
			
			//Generators modulGenerators = modulGeneratorTypeData.getGenerators();
			
			LoadFileOperationLogic.createGenerators(generatorTypesData,
													generatorTypeNode, frameRate, 
													//modulGenerators, 
													modulGeneratorTypeData);
			
			//modulGeneratorTypeData.setGenerators(modulGenerators);
			LoadFileOperationLogic.createTracks(generatorTypeNode, modulGeneratorTypeData);
		}
		
		return generatorTypeData;
	}

	public static GeneratorTypeData createGeneratorTypeData(String generatorTypeClassName, 
															Class generatorClass, 
															String generatorTypeName, 
															String generatorTypeDescription)
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
	 * @param generatorTypeNode 
	 * 			is the root Node of the Generators-XML.
	 * @param loadFileGeneratorNodeDatas 
	 * 			is a list with temporarely {@link LoadFileGeneratorNodeData}-Objects.
	 */
	private static void createGenerators(GeneratorTypesData generatorTypesData,
										 Node generatorTypeNode, float frameRate, 
										 //Generators generators, 
										 Vector loadFileGeneratorNodeDatas, 
										 ModulGeneratorTypeData modulGeneratorTypeData) 
			//ModulGenerator parentModulGenerator)
	{
		Node generatorsNode = XMLData.selectSingleNode(generatorTypeNode, "generators");

		NodeIterator generatorsNodeIterator = XMLData.selectNodeIterator(generatorsNode, "generator");

		Node generatorNode;

		while ((generatorNode = generatorsNodeIterator.nextNode()) != null)
		{
			String generatorType = XMLData.selectSingleNodeText(generatorNode, "type");
			//String generatorModulTypeName = XMLData.selectSingleNodeText(generatorNode, "modulTypeName");
			String generatorName = XMLData.selectSingleNodeText(generatorNode, "name");
			Float generatorStartTime = XMLData.selectSingleNodeFloat(generatorNode, "startTime");
			Float generatorEndTime = XMLData.selectSingleNodeFloat(generatorNode, "endTime");
			
			GeneratorTypeData generatorTypeData = generatorTypesData.searchGeneratorTypeData(generatorType);
			
			Generator generator;
			
			generator = generatorTypeData.createGeneratorInstance(generatorName, frameRate); //, parentModulGenerator);

			if (generator == null)
			{
				throw new PopupRuntimeException("can't create generator by type: " + generatorTypeData);
			}

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
			modulGeneratorTypeData.addGeneratorWithoutTrack(generator);
			
			//if (generator instanceof OutputGenerator)
			//{	
			//	modulGeneratorTypeData.setOutputGenerator((OutputGenerator)generator);
			//}
			
			loadFileGeneratorNodeDatas.add(new LoadFileGeneratorNodeData(generator, generatorNode));
		}
	}
	
	/**
	 * @param generatorTypeNode
	 * @param modulGeneratorTypeData
	 */
	private static void createTracks(Node generatorTypeNode, ModulGeneratorTypeData modulGeneratorTypeData)
	{
		Node tracksNode = XMLData.selectSingleNode(generatorTypeNode, "tracks");

		if (tracksNode != null)
		{
			NodeIterator tracksNodeIterator = XMLData.selectNodeIterator(tracksNode, "track");
	
			Node trackNode;
	
			while ((trackNode = tracksNodeIterator.nextNode()) != null)
			{
				String generatorName = XMLData.selectSingleNodeText(trackNode, "generatorName");
				
				modulGeneratorTypeData.addTrackForExistingGeneratorByName(generatorName);
			}
		}
		else
		{
			Iterator generatorsIterator = modulGeneratorTypeData.getGeneratorsIterator();
			
			while (generatorsIterator.hasNext())
			{
				Generator generator = (Generator)generatorsIterator.next();
				
				modulGeneratorTypeData.addTrackForExistingGenerator(generator);
			}
		}
	}

	/**
	 * Create and insert the Inputs for the Generator-Objects in the list.
	 * 
	 * @param loadFileGeneratorNodeDatas is a list with temporarely {@link LoadFileGeneratorNodeData}-Objects.
	 */
	private static void createGeneratorInputs(//Generators generators, 
											  Vector loadFileGeneratorNodeDatas, 
											  ModulGeneratorTypeData modulGeneratorTypeData)
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
					String inputStringValue = XMLData.selectSingleNodeText(inputNode, "stringValue");
					Integer inputModulInputType = XMLData.selectSingleNodeInteger(inputNode, "inputModulInputType");
					
					Generator inputGenerator = modulGeneratorTypeData.searchGenerator(inputGeneratorName);
					
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
					InputData inputData = generator.addInputGenerator(inputGenerator, inputTypeData, 
																	  inputValue, inputStringValue,
																	  inputModulInputTypeData);
				}
			}
		}
	}
}