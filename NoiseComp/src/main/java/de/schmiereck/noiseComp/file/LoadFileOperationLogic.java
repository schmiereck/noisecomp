package de.schmiereck.noiseComp.file;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

import de.schmiereck.noiseComp.PopupRuntimeException;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModuleGenerator;
import de.schmiereck.noiseComp.generator.ModuleGeneratorTypeData;
import de.schmiereck.noiseComp.generator.ModuleGeneratorTypeData.TicksPer;
import de.schmiereck.noiseComp.service.StartupService;
import de.schmiereck.xmlTools.XMLData;
import de.schmiereck.xmlTools.XMLPort;
import de.schmiereck.xmlTools.XMLPortException;

/*
 * Created on 25.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	Provides functions to load and save NoiseComp XML files.
 * </p>
 * 
 * @author smk
 * @version <p>25.03.2005:	created, smk</p>
 */
public class LoadFileOperationLogic
{

	/**
	 * Load a NoiseComp XML File into the generator types object and the
	 * main generator object.
	 * 
	 * @param fileName
	 * 			is the file name of the xml file to load.
	 * @throws XMLPortException
	 * @throws MainActionException
	 */
	public static ModuleGeneratorTypeData loadNoiseCompFile(GeneratorTypesData generatorTypesData,
														   //ModuleGeneratorTypeData mainModuleTypeData,
														   String fileName,
														   float frameRate) 
	throws XMLPortException
	{
		//==========================================================================================
		ModuleGeneratorTypeData mainModuleGeneratorTypeData;
		
		//------------------------------------------------------------------------------------------
		Document xmlDoc;
		
		xmlDoc = XMLPort.open(fileName);
		
		Node noiseNode = XMLData.selectSingleNode(xmlDoc, "/noise");

//		String version = XMLData.selectSingleNodeText(noiseNode, "version");
		
		//------------------------------------------------------------------------------------------
		// GeneratorTypesData:
		
		GeneratorTypeNodesData generatorTypeNodesData = new GeneratorTypeNodesData();
		
		mainModuleGeneratorTypeData = LoadFileOperationLogic.createGeneratorTypes(generatorTypesData,
																				 generatorTypeNodesData,
																				 noiseNode, 
																				 frameRate);//, loadFileGeneratorNodeDatas);
		
		LoadFileOperationLogic.createModuleGeneratorTypesGenerators(generatorTypeNodesData, 
		                                                            generatorTypesData, 
		                                                            frameRate);
		
		// Old file version without any main module?
		if (mainModuleGeneratorTypeData == null)
		{
			//-----------------------------------------------------
			// Generate a new one:
			
			mainModuleGeneratorTypeData = ModuleGenerator.createModuleGeneratorTypeData("/");
			
			mainModuleGeneratorTypeData.setIsMainModuleGeneratorType(true);
			
			mainModuleGeneratorTypeData.setGeneratorTypeName("ConsoleMain-Module");
			
			//-----------------------------------------------------
			// Inserting the Generators.
			
			//Generators mainGenerators = mainModuleGeneratorTypeData.getGenerators();
			
			LoadFileOperationLogic.createGenerators(generatorTypesData,
													noiseNode, 
													frameRate, 
													//mainGenerators, 
													mainModuleGeneratorTypeData);
			
			//-----------------------------------------------------
			// Add as new Type:
			
			generatorTypesData.addGeneratorTypeData(mainModuleGeneratorTypeData);
		}
		
		//==========================================================================================
		return mainModuleGeneratorTypeData;
	}

	/**
	 * @param generatorTypeNodesData
	 * @param generatorTypesData
	 * @param frameRate
	 */
	private static void createModuleGeneratorTypesGenerators(GeneratorTypeNodesData generatorTypeNodesData, GeneratorTypesData generatorTypesData, float frameRate)
	{
		//==========================================================================================
		Iterator<GeneratorTypeNodeData> generatorTypeNodesIterator = generatorTypeNodesData.getGeneratorTypeNodesIterator();
		
		while (generatorTypeNodesIterator.hasNext())
		{
			GeneratorTypeNodeData generatorTypeNodeData = generatorTypeNodesIterator.next();
			
			ModuleGeneratorTypeData moduleGeneratorTypeData = generatorTypeNodeData.getModuleGeneratorTypeData();
			
			Node generatorTypeNode = generatorTypeNodeData.getGeneratorTypeNode();
			
			generatorTypeNodesData.addTempGeneratorTypeData(new GeneratorTypeNodeData(moduleGeneratorTypeData,
																					  generatorTypeNode));
			
			//Generators moduleenerators = moduleeneratorTypeData.getGenerators();
			
			LoadFileOperationLogic.createGenerators(generatorTypesData,
													generatorTypeNode, frameRate, 
													//moduleenerators, 
													moduleGeneratorTypeData);
			
			//moduleeneratorTypeData.setGenerators(moduleenerators);
			LoadFileOperationLogic.createTracks(generatorTypeNode, moduleGeneratorTypeData);
		}
		//==========================================================================================
	}

	/**
	 * Creates the list of generatores descripted below the 'rootNode'.
	 */
	private static void createGenerators(GeneratorTypesData generatorTypesData,
										 Node rootNode, float frameRate, 
										 //Generators generators, 
										 ModuleGeneratorTypeData moduleGeneratorTypeData) //, ModuleGenerator parentModuleGenerator)
	{
		//==========================================================================================
		// List with temporarely {@link LoadFileGeneratorNodeData}-Objects.
		Vector<LoadFileGeneratorNodeData> loadFileGeneratorNodeDatas = new Vector<LoadFileGeneratorNodeData>();
		
		LoadFileOperationLogic.createGenerators(generatorTypesData,
												rootNode, frameRate, //generators, 
												loadFileGeneratorNodeDatas, 
												moduleGeneratorTypeData); //, parentModuleGenerator);
		
		// Inserting the inputs:
		
		LoadFileOperationLogic.createGeneratorInputs(//generators, 
		                                             loadFileGeneratorNodeDatas, 
		                                             moduleGeneratorTypeData);
		//==========================================================================================
	}

	private static ModuleGeneratorTypeData createGeneratorTypes(GeneratorTypesData generatorTypesData,
															   GeneratorTypeNodesData generatorTypeNodesData,
															   Node noiseNode, 
															   float frameRate)//, Vector loadFileGeneratorNodeDatas)
	{
		//==========================================================================================
		ModuleGeneratorTypeData mainModuleGeneratorTypeData = null;
		
		Node generatorTypesNode = XMLData.selectSingleNode(noiseNode, "generatorTypes");

		if (generatorTypesNode != null)
		{
			generatorTypesData.clear();
			
			//NodeIterator generatorTypesNodeIterator = XMLData.selectNodeIterator(generatorTypesNode, "generatorType");
			NodeList generatorTypesNodeList = XMLData.selectNodeList(generatorTypesNode, "generatorType");

			//Node generatorTypeNode;while ((generatorTypeNode = generatorTypesNodeIterator.nextNode()) != null)
			for (int pos = 0; pos < generatorTypesNodeList.getLength(); pos++) {
				Node generatorTypeNode = generatorTypesNodeList.item(pos);

				GeneratorTypeData generatorTypeData = createGeneratorType(generatorTypesData,
				                                                          generatorTypeNodesData,
				                                                          generatorTypeNode, 
				                                                          frameRate);
				
				if (generatorTypeData instanceof ModuleGeneratorTypeData)	
				{
					ModuleGeneratorTypeData moduleGeneratorTypeData = (ModuleGeneratorTypeData)generatorTypeData;
				
					if (moduleGeneratorTypeData.getIsMainModuleGeneratorType() == true)
					{
						mainModuleGeneratorTypeData = moduleGeneratorTypeData;
					}
				}
			}
		}
		
		//==========================================================================================
		return mainModuleGeneratorTypeData;
	}

	@SuppressWarnings("unchecked")
	private static GeneratorTypeData createGeneratorType(GeneratorTypesData generatorTypesData,
														 GeneratorTypeNodesData generatorTypeNodesData,
														 Node generatorTypeNode, 
														 float frameRate)//, Vector loadFileGeneratorNodeDatas)
	{
		//==========================================================================================
		//ModuleGeneratorTypeData moduleeneratorTypeData = null;
		
		String folderPath = XMLData.selectSingleNodeText(generatorTypeNode, "folderPath");
		String generatorTypeClassName = XMLData.selectSingleNodeText(generatorTypeNode, "generatorTypeClassName");
		String generatorClassName = XMLData.selectSingleNodeText(generatorTypeNode, "generatorClassName");
		String generatorTypeName = XMLData.selectSingleNodeText(generatorTypeNode, "name");
		String generatorTypeDescription = XMLData.selectSingleNodeText(generatorTypeNode, "description");
		
//		String generatorModuleypeName;	// Not realy needed, because 'generatorTypeName' should be the same.
		
		if (folderPath == null)
		{
			folderPath = makeMissingFolderPath(generatorTypeClassName);
		}
		
		GeneratorTypeData generatorTypeData = generatorTypesData.searchGeneratorTypeData(folderPath,
		                                                                                 generatorTypeClassName);
		
		if (generatorTypeData != null)
		{
			throw new RuntimeException("Generator type \"" + folderPath + "\" + \"" + generatorTypeClassName + "\" allready exist.");
		}
		
		if (generatorClassName != null)
		{
			//--------------------------------------------------------------------------------------
			Class<? extends Generator> generatorClass;
			try
			{
				int namePartPos = generatorClassName.indexOf("#");
	
				// Class name with appended name of a generic module type ?
				if (namePartPos != -1)
				{
	//				generatorModuleypeName = generatorClassName.substring(namePartPos + 1);
					generatorClassName = generatorClassName.substring(0, namePartPos);
				}
				else
				{
	//				generatorModuleypeName = null;
				}
				generatorClass = (Class< ? extends Generator>)Class.forName(generatorClassName);
			}
			catch (ClassNotFoundException ex)
			{
				throw new RuntimeException("Class \"" + folderPath + "\" + \"" + generatorTypeClassName + "\" not found.", ex);
			}
			
			generatorTypeData = LoadFileOperationLogic.createGeneratorTypeData(folderPath,
			                                                                   generatorTypeClassName, 
																			   generatorClass, 
																			   generatorTypeName, 
																			   generatorTypeDescription);
			
			if (generatorTypeData instanceof ModuleGeneratorTypeData)
			{
				ModuleGeneratorTypeData moduleGeneratorTypeData = (ModuleGeneratorTypeData)generatorTypeData;
				
				{
					String isMainName = XMLData.selectSingleNodeText(generatorTypeNode, "isMain");
		
					if ("true".equals(isMainName) == true)
					{
						moduleGeneratorTypeData.setIsMainModuleGeneratorType(true);
					}
				}
				{
					Node viewNode = XMLData.selectSingleNode(generatorTypeNode, "view");
					
					if (viewNode != null)
					{
						{
							Float viewZoomX = XMLData.selectSingleNodeFloat(viewNode, "zoomX");
							
							moduleGeneratorTypeData.setViewZoomX(viewZoomX);
						}
						{
							Float viewTicksCount = XMLData.selectSingleNodeFloat(viewNode, "ticksCount");
							
							moduleGeneratorTypeData.setViewTicksCount(viewTicksCount);
						}
						{
							String ticksPer = XMLData.selectSingleNodeText(viewNode, "ticksPer");
							
							if (ticksPer != null)
							{
								TicksPer viewTicksPer = TicksPer.valueOf(ticksPer);
								
								moduleGeneratorTypeData.setViewTicksPer(viewTicksPer);
							}
						}
					}
				}
			}

			generatorTypesData.addGeneratorTypeData(generatorTypeData);
				
			//--------------------------------------------------------
			// inputTypes:
			
			Node inputTypesNode = XMLData.selectSingleNode(generatorTypeNode, "inputTypes");

			//NodeIterator inputTypeNodesIterator = XMLData.selectNodeIterator(inputTypesNode, "inputType");
			NodeList inputTypeNodeList = XMLData.selectNodeList(inputTypesNode, "inputType");

			//Node inputTypeNode; while ((inputTypeNode = inputTypeNodesIterator.nextNode()) != null)
			for (int pos = 0; pos < inputTypeNodeList.getLength(); pos++) {
				Node inputTypeNode = inputTypeNodeList.item(pos);
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
			// ModuleGeneratorType:
			
			//if (generatorTypeClassName.equals(ModuleGeneratorTypeData.class.getName()))
			if (generatorTypeData instanceof ModuleGeneratorTypeData)	
			{
				ModuleGeneratorTypeData moduleGeneratorTypeData = (ModuleGeneratorTypeData)generatorTypeData;
				
				generatorTypeNodesData.addTempGeneratorTypeData(new GeneratorTypeNodeData(moduleGeneratorTypeData,
																						  generatorTypeNode));

				/*
				//Generators moduleenerators = moduleeneratorTypeData.getGenerators();
				
				LoadFileOperationLogic.createGenerators(generatorTypesData,
														generatorTypeNode, frameRate, 
														//moduleenerators, 
														moduleeneratorTypeData);
				
				//moduleeneratorTypeData.setGenerators(moduleenerators);
				LoadFileOperationLogic.createTracks(generatorTypeNode, moduleeneratorTypeData);
				*/
			}
			//--------------------------------------------------------------------------------------
		}
		else
		{
			//--------------------------------------------------------------------------------------
			// Folder:
			
			generatorTypeData = new ModuleGeneratorTypeData(folderPath,
			                                               null, 
			                                               null, 
														   null);
			
			generatorTypesData.addGeneratorTypeData(generatorTypeData);
			
			//--------------------------------------------------------------------------------------
		}
		
		//==========================================================================================
		return generatorTypeData;
	}

	/**
	 * @param generatorTypeClassName
	 * @return
	 */
	private static String makeMissingFolderPath(String generatorTypeClassName)
	{
		String folderPath;
		//if (generatorTypeClassName == null)	"de.schmiereck.noiseComp.generator.GeneratorTypeData"
		//if (generatorClassName == null)		"de.schmiereck.noiseComp.generator.MixerGenerator"
		//if (generatorTypeName == null)		"Mixer"
		if ("de.schmiereck.noiseComp.generator.GeneratorTypeData".equals(generatorTypeClassName))
		{
			folderPath = StartupService.GENERATOR_FOLDER_PATH;
		}
		else
		{
			//if (generatorTypeClassName == null)	"de.schmiereck.noiseComp.generator.ModuleGeneratorTypeData"
			//if (generatorClassName == null)		"de.schmiereck.noiseComp.generator.ModuleGenerator#drum-set 1"
			//if (generatorTypeName == null)		"drum-set 1"
			folderPath = StartupService.MODULE_FOLDER_PATH;
		}
		return folderPath;
	}

	@SuppressWarnings("unchecked")
	public static GeneratorTypeData createGeneratorTypeData(String folderPath,
	                                                        String generatorTypeClassName, 
															Class<? extends Generator> generatorClass, 
															String generatorTypeName, 
															String generatorTypeDescription)
	{
		//==========================================================================================
		GeneratorTypeData generatorTypeData = null;
		
		Class<? extends GeneratorTypeData> generatorTypeDataClass;
		
		try
		{
			generatorTypeDataClass = (Class< ? extends GeneratorTypeData>)Class.forName(generatorTypeClassName);
		}
		catch (ClassNotFoundException ex)
		{
			throw new RuntimeException("Class not found: \"" + folderPath + "\" + \"" + generatorTypeClassName + "\".", ex) ;
		}
		
		Class<?>[]	params		= new Class[4];
		
		params[0] = String.class;	// folderPath
		params[1] = Class.class;	// generatorClass
		params[2] = String.class;	// generatorTypeName
		params[3] = String.class;	// generatorTypeDescription
		
		try
		{
			Constructor<?> generatorConstructor = generatorTypeDataClass.getConstructor(params);
			Object[]	args	= new Object[4];

			args[0] = folderPath;
			args[1] = generatorClass;
			args[2] = generatorTypeName;
			args[3] = generatorTypeDescription;
			
			try
			{
				//pageView = (HTMLPageView)Class.forName(pageViewClassName).newInstance();
				generatorTypeData = (GeneratorTypeData)generatorConstructor.newInstance(args);
			}
			catch (java.lang.InstantiationException ex)
			{
				throw new RuntimeException("New instance: \"" + folderPath + "\" + \"" + generatorTypeClassName + "\".", ex);
			}
			catch (java.lang.IllegalAccessException ex)
			{
				throw new RuntimeException("Access exception for class: \"" + folderPath + "\" + " + generatorTypeClassName+ "\".", ex);
			}
			//catch (java.lang.ClassNotFoundException ex)
			//{
			//	throw new RuntimeException("class not found: " + pageViewClassName, ex);
			//} 
			catch (IllegalArgumentException ex)
			{
				throw new RuntimeException("Illegal argument: \"" + folderPath + "\" + \"" + generatorTypeClassName + "\".", ex);
			} 
			catch (InvocationTargetException ex)
			{
				throw new RuntimeException("Invocation target: \"" + folderPath + "\" + \"" + generatorTypeClassName + "\".", ex);
			}
		}
		catch (java.lang.NoSuchMethodException ex)
		{
			throw new RuntimeException("No such method exception for class: \"" + folderPath + "\" + \"" + generatorTypeClassName + "\".", ex);
		}
		
		//==========================================================================================
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
										 Vector<LoadFileGeneratorNodeData> loadFileGeneratorNodeDatas, 
										 ModuleGeneratorTypeData moduleGeneratorTypeData) 
			//ModuleGenerator parentModuleGenerator)
	{
		//==========================================================================================
		Node generatorsNode = XMLData.selectSingleNode(generatorTypeNode, "generators");

		//NodeIterator generatorsNodeIterator = XMLData.selectNodeIterator(generatorsNode, "generator");
		NodeList generatorsNodeList = XMLData.selectNodeList(generatorsNode, "generator");

		int generatorPos = 0;
		//Node generatorNode; while ((generatorNode = generatorsNodeIterator.nextNode()) != null)
		for (int pos = 0; pos < generatorsNodeList.getLength(); pos++) {
			Node generatorNode = generatorsNodeList.item(pos);
			String folderPath = XMLData.selectSingleNodeText(generatorNode, "folderPath");
			String generatorType = XMLData.selectSingleNodeText(generatorNode, "type");
			//String generatorModuleypeName = XMLData.selectSingleNodeText(generatorNode, "moduleypeName");
			String generatorName = XMLData.selectSingleNodeText(generatorNode, "name");
			Float generatorStartTime = XMLData.selectSingleNodeFloat(generatorNode, "startTime");
			Float generatorEndTime = XMLData.selectSingleNodeFloat(generatorNode, "endTime");
			
			if (folderPath == null)
			{
//				folderPath = makeMissingFolderPath(generatorType);
				
				//"de.schmiereck.noiseComp.generator.ModuleGenerator#drum-set 1"
				if (generatorType.startsWith("de.schmiereck.noiseComp.generator.ModuleGenerator#"))
				{
					folderPath = StartupService.MODULE_FOLDER_PATH;
				}
				else
				{
					folderPath = StartupService.GENERATOR_FOLDER_PATH;
				}
			}
			
			GeneratorTypeData generatorTypeData = generatorTypesData.searchGeneratorTypeData(folderPath,
			                                                                                 generatorType);
			
			if (generatorTypeData == null)
			{
				throw new RuntimeException("Unknown Generator-Type \"" + folderPath + "\" + \"" + generatorType + "\".");
			}
			
			Generator generator;
			
			generator = generatorTypeData.createGeneratorInstance(generatorName, frameRate); //, parentModuleGenerator);

			if (generator == null)
			{
				throw new PopupRuntimeException("can't create generator by type \"" + folderPath + "\" + \"" + generatorType + "\".");
			}

			generator.setTimePos(generatorStartTime.floatValue(),
			                     generatorEndTime.floatValue());

			/*
			// Generator is a Module ?
			if (generator instanceof ModuleGenerator)
			{
				ModuleGenerator moduleenerator = (ModuleGenerator)generator;
				
				String moduleypeName = XMLData.selectSingleNodeText(generatorNode, "moduleypeName");
				
				moduleenerator.getGeneratorTypeData();
			}
			*/
			//this.controllerLogic.addGenerator(generator);
			moduleGeneratorTypeData.addGeneratorWithoutTrack(generatorPos,
			                                                generator);
			
			//if (generator instanceof OutputGenerator)
			//{	
			//	moduleeneratorTypeData.setOutputGenerator((OutputGenerator)generator);
			//}
			
			loadFileGeneratorNodeDatas.add(new LoadFileGeneratorNodeData(generator, generatorNode));
			
			generatorPos++;
		}
		//==========================================================================================
	}
	
	/**
	 * @param generatorTypeNode
	 * @param moduleGeneratorTypeData
	 */
	private static void createTracks(Node generatorTypeNode, ModuleGeneratorTypeData moduleGeneratorTypeData)
	{
		//==========================================================================================
		Node tracksNode = XMLData.selectSingleNode(generatorTypeNode, "tracks");

		if (tracksNode != null)
		{
			//NodeIterator tracksNodeIterator = XMLData.selectNodeIterator(tracksNode, "track");
			NodeList tracksNodeList = XMLData.selectNodeList(tracksNode, "track");

			//Node trackNode; while ((trackNode = tracksNodeIterator.nextNode()) != null)
			for (int pos = 0; pos < tracksNodeList.getLength(); pos++) {
				Node trackNode = tracksNodeList.item(pos);
				String generatorName = XMLData.selectSingleNodeText(trackNode, "generatorName");
				
				moduleGeneratorTypeData.addTrackForExistingGeneratorByName(generatorName);
			}
		}
		else
		{
			Iterator<Generator> generatorsIterator = moduleGeneratorTypeData.getGeneratorsIterator();
			
			while (generatorsIterator.hasNext())
			{
				Generator generator = generatorsIterator.next();
				
//				moduleeneratorTypeData.addTrackForExistingGenerator(generator);
				moduleGeneratorTypeData.addGenerator(generator);
			}
		}
		//==========================================================================================
	}

	/**
	 * Create and insert the Inputs for the Generator-Objects in the list.
	 * 
	 * @param loadFileGeneratorNodeDatas is a list with temporarely {@link LoadFileGeneratorNodeData}-Objects.
	 */
	private static void createGeneratorInputs(//Generators generators, 
											  Vector<LoadFileGeneratorNodeData> loadFileGeneratorNodeDatas, 
											  ModuleGeneratorTypeData moduleGeneratorTypeData)
	{
		//==========================================================================================
		Iterator<LoadFileGeneratorNodeData> loadFileGeneratorNodeDatasIterator = loadFileGeneratorNodeDatas.iterator();
		
		while (loadFileGeneratorNodeDatasIterator.hasNext())
		{
			LoadFileGeneratorNodeData loadFileGeneratorNodeData = loadFileGeneratorNodeDatasIterator.next();
			
			Generator generator = loadFileGeneratorNodeData.getGenerator();
			Node generatorNode = loadFileGeneratorNodeData.getGeneratorNode();
			
			Node inputsNode = XMLData.selectSingleNode(generatorNode, "inputs");
			
			if (inputsNode != null)
			{	
				//NodeIterator inputNodesIterator = XMLData.selectNodeIterator(inputsNode, "input");
				NodeList inputNodeList = XMLData.selectNodeList(inputsNode, "input");

				//Node inputNode; while ((inputNode = inputNodesIterator.nextNode()) != null)
				for (int pos = 0; pos < inputNodeList.getLength(); pos++) {
					Node inputNode = inputNodeList.item(pos);

					String inputGeneratorName = XMLData.selectSingleNodeText(inputNode, "generatorName");
					Integer inputType = XMLData.selectSingleNodeInteger(inputNode, "type");
					Float inputValue = XMLData.selectSingleNodeFloat(inputNode, "value");
					String inputStringValue = XMLData.selectSingleNodeText(inputNode, "stringValue");
					Integer inputModuleInputType = XMLData.selectSingleNodeInteger(inputNode, "inputModuleInputType");
					
					Generator inputGenerator = moduleGeneratorTypeData.searchGenerator(inputGeneratorName);
					
					InputTypeData inputTypeData = generator.getGeneratorTypeData().getInputTypeData(inputType.intValue());
					
					InputTypeData inputModuleInputTypeData;
					
					if (inputModuleInputType != null)
					{	
						inputModuleInputTypeData = moduleGeneratorTypeData.getInputTypeData(inputModuleInputType.intValue());
					}
					else
					{	
						inputModuleInputTypeData = null;
					}
					
					//generators.addInput(generator, inputGenerator, inputTypeData, inputValue, inputModuleInputTypeData);
//					InputData inputData = 
						generator.addGeneratorInput(inputGenerator, inputTypeData, 
						                            inputValue, inputStringValue,
						                            inputModuleInputTypeData);
				}
			}
		}
		//==========================================================================================
 	}
}
