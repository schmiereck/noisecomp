package de.schmiereck.noiseComp.desktopController.actions;

import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.generator.FaderGenerator;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.MixerGenerator;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.SinusGenerator;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.xmlTools.XMLData;
import de.schmiereck.xmlTools.XMLPort;

/**
 * TODO docu
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
			
			Document xmlDoc = XMLData.createDocument();
			
			Node noiseNode = XMLData.appendNode(xmlDoc, xmlDoc, "noise");
			Node generatorsNode = XMLData.appendNode(xmlDoc, noiseNode, "generators");
			
			SoundData soundData = this.controllerData.getSoundData();
			
			Iterator generatorsIterator = soundData.getGeneratorsIterator();
			
			while (generatorsIterator.hasNext())
			{
				Generator generator = (Generator)generatorsIterator.next();
				
				Node generatorNode = XMLData.appendNode(xmlDoc, generatorsNode, "generator");
				
				Node generatorTypeNode = XMLData.appendTextNode(xmlDoc, generatorNode, "type", generator.getClass().getName());
				Node generatorNameNode = XMLData.appendTextNode(xmlDoc, generatorNode, "name", generator.getName());
				Node generatorStartTimeNode = XMLData.appendFloatNode(xmlDoc, generatorNode, "startTime", generator.getStartTimePos());
				Node generatorEndTimeNode = XMLData.appendFloatNode(xmlDoc, generatorNode, "endTime", generator.getEndTimePos());
			
				if (generator.getClass().equals(FaderGenerator.class))
				{
					FaderGenerator faderGenerator = (FaderGenerator)generator;

					//Node faderGeneratorStartFadeValueNode = XMLData.appendFloatNode(xmlDoc, generatorNode, "startFadeValue", faderGenerator.getStartFadeValue());
					//Node faderGeneratorEndFadeValueNode = XMLData.appendFloatNode(xmlDoc, generatorNode, "endFadeValue", faderGenerator.getEndFadeValue());
				}
				else
				{
					if (generator.getClass().equals(MixerGenerator.class))
					{
						MixerGenerator mixerGenerator = (MixerGenerator)generator;
					}
					else
					{
						if (generator.getClass().equals(OutputGenerator.class))
						{
							OutputGenerator outputGenerator = (OutputGenerator)generator;
						}
						else
						{
							if (generator.getClass().equals(SinusGenerator.class))
							{
								SinusGenerator sinusGenerator = (SinusGenerator)generator;

								//Node sinusGeneratorSignalFrequencyNode = XMLData.appendFloatNode(xmlDoc, generatorNode, "signalFrequency", sinusGenerator.getSignalFrequency());
							}
							else
							{
								// TODO show ERROR message
								throw new RuntimeException("unknown generator type: " + generator.getClass().getName());
							}
						}
					}
				}
				
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
						Node inputTypeNode = XMLData.appendIntegerNode(xmlDoc, inputNode, "type", înputData.getInputType());
						Node inputValueNode = XMLData.appendFloatNode(xmlDoc, inputNode, "value", înputData.getInputValue());
					}
				}
			}
			
			XMLPort.save(fileName, xmlDoc);
		
			this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());
		}
		else
		{
			// TODO show ERROR message
		}
	}

}
