package de.schmiereck.noiseComp.file;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import de.schmiereck.noiseComp.PopupRuntimeException;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData.TicksPer;
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
	public static ModulGeneratorTypeData loadNoiseCompFile(GeneratorTypesData generatorTypesData,
														   //ModulGeneratorTypeData mainModulTypeData,
														   String fileName,
														   float frameRate) 
	throws XMLPortException
	{
		//==========================================================================================
		ModulGeneratorTypeData mainModulGeneratorTypeData;
		
		//------------------------------------------------------------------------------------------
		Document xmlDoc;
		
		xmlDoc = XMLPort.open(fileName);
		
		Node noiseNode = XMLData.selectSingleNode(xmlDoc, "/noise");

//		String version = XMLData.selectSingleNodeText(noiseNode, "version");
		
		//------------------------------------------------------------------------------------------
		// GeneratorTypesData:
		
		GeneratorTypeNodesData generatorTypeNodesData = new GeneratorTypeNodesData();
		
		mainModulGeneratorTypeData = LoadFileOperationLogic.createGeneratorTypes(generatorTypesData,
																				 generatorTypeNodesData,
																				 noiseNode, 
																				 frameRate);//, loadFileGeneratorNodeDatas);
		
		LoadFileOperationLogic.createModulGeneratorTypesGenerators(generatorTypeNodesData, 
																   generatorTypesData, 
																   frameRate);
		
		// Old file version without any main module?
		if (mainModulGeneratorTypeData == null)
		{
			//-----------------------------------------------------
			// Generate a new one:
			
			mainModulGeneratorTypeData = ModulGenerator.createModulGeneratorTypeData("/");
			
			mainModulGeneratorTypeData.setIsMainModulGeneratorType(true);
			
			mainModulGeneratorTypeData.setGeneratorTypeName("ConsoleMain-Modul");
			
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
		
		//==========================================================================================
		return mainModulGeneratorTypeData;
	}

	/**
	 * @param generatorTypeNodesData
	 * @param generatorTypesData
	 * @param frameRate
	 */
	private static void createModulGeneratorTypesGenerators(GeneratorTypeNodesData generatorTypeNodesData, GeneratorTypesData generatorTypesData, float frameRate)
	{
		//==========================================================================================
		Iterator<GeneratorTypeNodeData> generatorTypeNodesIterator = generatorTypeNodesData.getGeneratorTypeNodesIterator();
		
		while (generatorTypeNodesIterator.hasNext())
		{
			GeneratorTypeNodeData generatorTypeNodeData = generatorTypeNodesIterator.next();
			
			ModulGeneratorTypeData modulGeneratorTypeData = generatorTypeNodeData.getModulGeneratorTypeData();
			
			Node generatorTypeNode = generatorTypeNodeData.getGeneratorTypeNode();
			
			generatorTypeNodesData.addTempGeneratorTypeData(new GeneratorTypeNodeData(modulGeneratorTypeData,
																					  generatorTypeNode));
			
			//Generators modulGenerators = modulGeneratorTypeData.getGenerators();
			
			LoadFileOperationLogic.createGenerators(generatorTypesData,
													generatorTypeNode, frameRate, 
													//modulGenerators, 
													modulGeneratorTypeData);
			
			//modulGeneratorTypeData.setGenerators(modulGenerators);
			LoadFileOperationLogic.createTracks(generatorTypeNode, modulGeneratorTypeData);
		}
		//==========================================================================================
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
		//==========================================================================================
		// List with temporarely {@link LoadFileGeneratorNodeData}-Objects.
		Vector<LoadFileGeneratorNodeData> loadFileGeneratorNodeDatas = new Vector<LoadFileGeneratorNodeData>();
		
		LoadFileOperationLogic.createGenerators(generatorTypesData,
												rootNode, frameRate, //generators, 
												loadFileGeneratorNodeDatas, 
												modulGeneratorTypeData); //, parentModulGenerator);
		
		// Inserting the inputs:
		
		LoadFileOperationLogic.createGeneratorInputs(//generators, 
		                                             loadFileGeneratorNodeDatas, 
		                                             modulGeneratorTypeData);
		//==========================================================================================
	}

	private static ModulGeneratorTypeData createGeneratorTypes(GeneratorTypesData generatorTypesData,
															   GeneratorTypeNodesData generatorTypeNodesData,
															   Node noiseNode, 
															   float frameRate)//, Vector loadFileGeneratorNodeDatas)
	{
		//==========================================================================================
		ModulGeneratorTypeData mainModulGeneratorTypeData = null;
		
		Node generatorTypesNode = XMLData.selectSingleNode(noiseNode, "generatorTypes");

		if (generatorTypesNode != null)
		{
			generatorTypesData.clear();
			
			NodeIterator generatorTypesNodeIterator = XMLData.selectNodeIterator(generatorTypesNode, "generatorType");
	
			Node generatorTypeNode;
	
			while ((generatorTypeNode = generatorTypesNodeIterator.nextNode()) != null)
			{
				GeneratorTypeData generatorTypeData = createGeneratorType(generatorTypesData,
				                                                          generatorTypeNodesData,
				                                                          generatorTypeNode, 
				                                                          frameRate);
				
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
		
		//==========================================================================================
		return mainModulGeneratorTypeData;
	}

	@SuppressWarnings("unchecked")
	private static GeneratorTypeData createGeneratorType(GeneratorTypesData generatorTypesData,
														 GeneratorTypeNodesData generatorTypeNodesData,
														 Node generatorTypeNode, 
														 float frameRate)//, Vector loadFileGeneratorNodeDatas)
	{
		//==========================================================================================
		//ModulGeneratorTypeData modulGeneratorTypeData = null;
		
		String folderPath = XMLData.selectSingleNodeText(generatorTypeNode, "folderPath");
		String generatorTypeClassName = XMLData.selectSingleNodeText(generatorTypeNode, "generatorTypeClassName");
		String generatorClassName = XMLData.selectSingleNodeText(generatorTypeNode, "generatorClassName");
		String generatorTypeName = XMLData.selectSingleNodeText(generatorTypeNode, "name");
		String generatorTypeDescription = XMLData.selectSingleNodeText(generatorTypeNode, "description");
		
//		String generatorModulTypeName;	// Not realy needed, because 'generatorTypeName' should be the same.
		
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
	
				// Class name with appended name of a generic modul type ?
				if (namePartPos != -1)
				{
	//				generatorModulTypeName = generatorClassName.substring(namePartPos + 1);
					generatorClassName = generatorClassName.substring(0, namePartPos);
				}
				else
				{
	//				generatorModulTypeName = null;
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
			
			if (generatorTypeData instanceof ModulGeneratorTypeData)
			{
				ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)generatorTypeData;
				
				{
					String isMainName = XMLData.selectSingleNodeText(generatorTypeNode, "isMain");
		
					if ("true".equals(isMainName) == true)
					{
						modulGeneratorTypeData.setIsMainModulGeneratorType(true);
					}
				}
				{
					Node viewNode = XMLData.selectSingleNode(generatorTypeNode, "view");
					
					if (viewNode != null)
					{
						{
							Float viewZoomX = XMLData.selectSingleNodeFloat(viewNode, "zoomX");
							
							modulGeneratorTypeData.setViewZoomX(viewZoomX);
						}
						{
							Float viewTicksCount = XMLData.selectSingleNodeFloat(viewNode, "ticksCount");
							
							modulGeneratorTypeData.setViewTicksCount(viewTicksCount);
						}
						{
							String ticksPer = XMLData.selectSingleNodeText(viewNode, "ticksPer");
							
							if (ticksPer != null)
							{
								TicksPer viewTicksPer = TicksPer.valueOf(ticksPer);
								
								modulGeneratorTypeData.setViewTicksPer(viewTicksPer);
							}
						}
					}
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
				
				generatorTypeNodesData.addTempGeneratorTypeData(new GeneratorTypeNodeData(modulGeneratorTypeData,
																						  generatorTypeNode));

				/*
				//Generators modulGenerators = modulGeneratorTypeData.getGenerators();
				
				LoadFileOperationLogic.createGenerators(generatorTypesData,
														generatorTypeNode, frameRate, 
														//modulGenerators, 
														modulGeneratorTypeData);
				
				//modulGeneratorTypeData.setGenerators(modulGenerators);
				LoadFileOperationLogic.createTracks(generatorTypeNode, modulGeneratorTypeData);
				*/
			}
			//--------------------------------------------------------------------------------------
		}
		else
		{
			//--------------------------------------------------------------------------------------
			// Folder:
			
			generatorTypeData = new ModulGeneratorTypeData(folderPath,
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
			//if (generatorTypeClassName == null)	"de.schmiereck.noiseComp.generator.ModulGeneratorTypeData"
			//if (generatorClassName == null)		"de.schmiereck.noiseComp.generator.ModulGenerator#drum-set 1"
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
										 ModulGeneratorTypeData modulGeneratorTypeData) 
			//ModulGenerator parentModulGenerator)
	{
		//==========================================================================================
		Node generatorsNode = XMLData.selectSingleNode(generatorTypeNode, "generators");

		NodeIterator generatorsNodeIterator = XMLData.selectNodeIterator(generatorsNode, "generator");

		int generatorPos = 0;
		Node generatorNode;

		while ((generatorNode = generatorsNodeIterator.nextNode()) != null)
		{
			String folderPath = XMLData.selectSingleNodeText(generatorNode, "folderPath");
			String generatorType = XMLData.selectSingleNodeText(generatorNode, "type");
			//String generatorModulTypeName = XMLData.selectSingleNodeText(generatorNode, "modulTypeName");
			String generatorName = XMLData.selectSingleNodeText(generatorNode, "name");
			Float generatorStartTime = XMLData.selectSingleNodeFloat(generatorNode, "startTime");
			Float generatorEndTime = XMLData.selectSingleNodeFloat(generatorNode, "endTime");
			
			if (folderPath == null)
			{
//				folderPath = makeMissingFolderPath(generatorType);
				
				//"de.schmiereck.noiseComp.generator.ModulGenerator#drum-set 1"
				if (generatorType.startsWith("de.schmiereck.noiseComp.generator.ModulGenerator#"))
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
			
			generator = generatorTypeData.createGeneratorInstance(generatorName, frameRate); //, parentModulGenerator);

			if (generator == null)
			{
				throw new PopupRuntimeException("can't create generator by type \"" + folderPath + "\" + \"" + generatorType + "\".");
			}

			generator.setTimePos(generatorStartTime.floatValue(),
			                     generatorEndTime.floatValue());

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
			modulGeneratorTypeData.addGeneratorWithoutTrack(generatorPos,
			                                                generator);
			
			//if (generator instanceof OutputGenerator)
			//{	
			//	modulGeneratorTypeData.setOutputGenerator((OutputGenerator)generator);
			//}
			
			loadFileGeneratorNodeDatas.add(new LoadFileGeneratorNodeData(generator, generatorNode));
			
			generatorPos++;
		}
		//==========================================================================================
	}
	
	/**
	 * @param generatorTypeNode
	 * @param modulGeneratorTypeData
	 */
	private static void createTracks(Node generatorTypeNode, ModulGeneratorTypeData modulGeneratorTypeData)
	{
		//==========================================================================================
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
			Iterator<Generator> generatorsIterator = modulGeneratorTypeData.getGeneratorsIterator();
			
			while (generatorsIterator.hasNext())
			{
				Generator generator = generatorsIterator.next();
				
//				modulGeneratorTypeData.addTrackForExistingGenerator(generator);
				modulGeneratorTypeData.addGenerator(generator);
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
											  ModulGeneratorTypeData modulGeneratorTypeData)
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
//					InputData inputData = 
						generator.addInputGenerator(inputGenerator, inputTypeData, 
						                            inputValue, inputStringValue,
						                            inputModulInputTypeData);
				}
			}
		}
		//==========================================================================================
 	}
}
