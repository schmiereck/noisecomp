package de.schmiereck.noiseComp.desktopController.actions;

import java.util.Iterator;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.generator.FaderGenerator;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.MixerGenerator;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.SinusGenerator;
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
			
				// List with temporarely {@link LoadFileGeneratorNodeData}-Objects.
				Vector loadFileGeneratorNodeDatas = new Vector();
				
				Node noiseNode = XMLData.selectSingleNode(xmlDoc, "/noise");

				// Inserting the Generators.
				
				this.createGenerators(noiseNode, soundData, loadFileGeneratorNodeDatas);
				
				// Inserting the inputs:
				
				this.createGeneratorInputs(loadFileGeneratorNodeDatas);
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
		}
	}

	/**
	 * 
	 * @param noiseNode is the root Node of the Generators-XML.
	 * @param soundData is the empty sound data object the generators are inserted.
	 * @param loadFileGeneratorNodeDatas is a list with temporarely {@link LoadFileGeneratorNodeData}-Objects.
	 */
	private void createGenerators(Node noiseNode, SoundData soundData, Vector loadFileGeneratorNodeDatas)
	{
		Node generatorsNode = XMLData.selectSingleNode(noiseNode, "generators");

		NodeIterator generatorNodesIterator = XMLData.selectNodeIterator(generatorsNode, "generator");

		Node generatorNode;
		
		while ((generatorNode = generatorNodesIterator.nextNode()) != null)
		{
			String generatorType = XMLData.selectSingleNodeText(generatorNode, "type");
			String generatorName = XMLData.selectSingleNodeText(generatorNode, "name");
			Float generatorStartTime = XMLData.selectSingleNodeFloat(generatorNode, "startTime");
			Float generatorEndTime = XMLData.selectSingleNodeFloat(generatorNode, "endTime");
			
			Generator generator;
			
			if (generatorType.equals(FaderGenerator.class.getName()))
			{	
				FaderGenerator faderGenerator = new FaderGenerator(generatorName, Float.valueOf(soundData.getFrameRate()));
				
				//Float generatorStartFadeValue = XMLData.selectSingleNodeFloat(generatorNode, "startFadeValue");
				//faderGenerator.setStartFadeValue(generatorStartFadeValue.floatValue());

				//Float generatorEndFadeValue = XMLData.selectSingleNodeFloat(generatorNode, "endFadeValue");
				//faderGenerator.setEndFadeValue(generatorEndFadeValue.floatValue());

				generator = faderGenerator;
			}
			else
			{
				if (generatorType.equals(MixerGenerator.class.getName()))
				{	
					generator = new MixerGenerator(generatorName, Float.valueOf(soundData.getFrameRate()));
				}
				else
				{
					if (generatorType.equals(OutputGenerator.class.getName()))
					{	
						generator = new OutputGenerator(generatorName, Float.valueOf(soundData.getFrameRate()));
					}
					else
					{
						if (generatorType.equals(SinusGenerator.class.getName()))
						{	
							SinusGenerator sinusGenerator = new SinusGenerator(generatorName, Float.valueOf(soundData.getFrameRate()));
							
							//Float generatorSignalFrequency = XMLData.selectSingleNodeFloat(generatorNode, "signalFrequency");
							//sinusGenerator.setSignalFrequency(generatorSignalFrequency.floatValue());
							
							generator = sinusGenerator;
						}
						else
						{
							generator = null;
						}
					}
				}
			}
			
			if (generator != null)
			{					
				generator.setStartTimePos(generatorStartTime.floatValue());
				generator.setEndTimePos(generatorEndTime.floatValue());
				
				this.controllerLogic.addGenerator(generator);
				
				loadFileGeneratorNodeDatas.add(new LoadFileGeneratorNodeData(generator, generatorNode));
			}
			else
			{
				// TODO show ERROR message
				break;
			}
		}
	}

	/**
	 * Create and insert the Inputs for the Generator-Objects in the list.
	 * 
	 * @param loadFileGeneratorNodeDatas is a list with temporarely {@link LoadFileGeneratorNodeData}-Objects.
	 */
	private void createGeneratorInputs(Vector loadFileGeneratorNodeDatas)
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
					
					this.controllerLogic.addInput(generator, inputGeneratorName, inputType, inputValue);
				}
			}
		}
	}

}
