package de.schmiereck.noiseComp.desktopController;

import java.util.Iterator;
import java.util.Vector;

import de.schmiereck.noiseComp.desktopInput.DesktopInputListener;
import de.schmiereck.noiseComp.desktopPage.DesktopPageLogic;
import de.schmiereck.noiseComp.desktopPage.widgets.GeneratorInputSelectedListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.GeneratorSelectedListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.InputlineData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputsWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.ScrollbarData;
import de.schmiereck.noiseComp.desktopPage.widgets.SelectData;
import de.schmiereck.noiseComp.desktopPage.widgets.SelectEntryData;
import de.schmiereck.noiseComp.desktopPage.widgets.TrackData;
import de.schmiereck.noiseComp.desktopPage.widgets.TracksData;
import de.schmiereck.noiseComp.desktopPage.widgets.WidgetData;
import de.schmiereck.noiseComp.generator.FaderGenerator;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.MixerGenerator;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.SinusGenerator;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.noiseComp.soundData.SoundSchedulerLogic;
import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.controller.ControllerLogic;
import de.schmiereck.screenTools.scheduler.SchedulerWaiter;

/**
 * TODO docu
 *
 * @author smk
 * @version 08.01.2004
 */
public class DesktopControllerLogic
extends ControllerLogic
implements GeneratorSelectedListenerInterface, 
GeneratorInputSelectedListenerInterface
{
	private DesktopControllerData controllerData;
	
	private SoundSchedulerLogic soundSchedulerLogic = null;
	
	/**
	 * Constructor.
	 * 
	 * @param waiter
	 */
	public DesktopControllerLogic(DesktopControllerData controllerData, DesktopInputListener inputListener, SchedulerWaiter waiter, String playerName)
	{
		super(waiter);
		
		this.controllerData = controllerData;
		
		SoundData soundData = this.controllerData.getSoundData();
		TracksData tracksData = this.controllerData.getTracksData();
		
		this.controllerData.setMainGenerators(soundData.getGenerators());
		
		this.createGenerators(soundData.getFrameRate(), soundData.getGenerators());
		
		this.controllerData.getTracksData().setGeneratorSelectedListener(this);
		
		this.controllerData.getGeneratorInputsData().setGeneratorInputSelectedListener(this);
		
		this.selectMainModul();
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#calc(de.schmiereck.screenTools.controller.ControllerData, long, double)
	 */
	public void calc(ControllerData controllerData, long actualWaitPerFramesMillis, double runSeconds)
	{
		controllerData.setCalcSleepMillis(actualWaitPerFramesMillis);
		
		DesktopControllerData desktopControllerData = (DesktopControllerData)controllerData;
		
		DesktopPageLogic.calcWidgets(desktopControllerData.getActiveDesktopPageData());
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#initGameData(de.schmiereck.screenTools.controller.ControllerData)
	 */
	public void initGameData(ControllerData controllerData)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#openGameServerConnection()
	 */
	public void openGameServerConnection()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#closeGameServerConnection()
	 */
	public void closeGameServerConnection()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#openGameClientConnection()
	 */
	public void openGameClientConnection()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#closeGameClientConnection()
	 */
	public void closeGameClientConnection()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#getIsGameClientConnected()
	 */
	public boolean getIsGameClientConnected()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#getIsGameServerConnected()
	 */
	public boolean getIsGameServerConnected()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 
	 */
	public void doEndGame()
	{
		this.stopWaitGame();
	}

	/**
	 * Wird vom Input-Listener aufgerufen, wenn sich die Mausposition geändert hat.
	 */
	public void movePointer(int posX, int posY)
	{
		this.controllerData.getDesktopData().setPointerPos(posX, posY);
	}

	/**
	 * Wird vom Input-Listener aufgerufen, wenn die Maustaste gedrückt wurde.
	 */
	public void pointerPressed()
	{
		DesktopPageLogic.pointerPressed(this.controllerData.getActiveDesktopPageData());
	}

	public void pointerReleased()
	{
		DesktopPageLogic.pointerReleased(this.controllerData.getActiveDesktopPageData());
	}

	public void stopSound()
	{
		if (this.soundSchedulerLogic != null)
		{
			this.soundSchedulerLogic.stopPlayback();
			
			this.soundSchedulerLogic.stopThread();
			
			this.soundSchedulerLogic = null;
		}
	}

	public void pauseSound()
	{
		if (this.soundSchedulerLogic != null)
		{
			this.soundSchedulerLogic.pausePlayback();
		}
	}

	public void playSound()
	{
		if (this.soundSchedulerLogic == null)
		{
			SoundData soundData = this.controllerData.getSoundData();
			
			this.soundSchedulerLogic = new SoundSchedulerLogic(20, soundData);
			
			this.soundSchedulerLogic.startThread();

			this.soundSchedulerLogic.startPlayback();
		}
		else
		{	
			this.soundSchedulerLogic.resumePlayback();
		}
	}

	/**
	 * <p>
	 * 	Get the active track list and and the genarator to this list.
	 * </p>
	 * <p>
	 * 	Put the generator into a new {@link TracksData}-Object and add the
	 * 	new track to the track list.
	 * </p>
	 * <p>
	 * 	If the generator is a {@link OutputGenerator}-Object, 
	 * 	this Object is set as output for the sound data.
	 * </p>
	 * 
	 * @see #addGenerator(TracksData, Generator)
	 * @param generator
	 */
	public void addGenerator(Generator generator)
	{
		this.controllerData.getMainGenerators().addGenerator(generator);
		
		TrackData trackData = new TrackData(generator);
		
		this.addTrackData(trackData);
	}

	public void addTrackData(TrackData trackData)
	{
		TracksData tracksData = this.controllerData.getTracksData();
		
		tracksData.addTrack(trackData);

		Generator generator = trackData.getGenerator();
		
		if (generator instanceof OutputGenerator)
		{	
			OutputGenerator outputGenerator = (OutputGenerator)generator;
			
			SoundData soundData = this.controllerData.getSoundData();
			
			soundData.setOutputGenerator(outputGenerator);
		}
	}

	/**
	 * Creates a demo list of generators with different types.
	 * It's only for developing.
	 */
	private OutputGenerator createGenerators(float frameRate, Generators generators)
	{
		// Sound-Generatoren für das Sound-Format des Ausgabekanals erzeugen:

		//---------------------------------
		FaderGenerator faderInGenerator;
		{
			GeneratorTypeData generatorTypeData = this.controllerData.searchGeneratorTypeData(FaderGenerator.class.getName());
			faderInGenerator = new FaderGenerator("faderIn", Float.valueOf(frameRate), generatorTypeData);
			
			faderInGenerator.setStartTimePos(0.0F);
			faderInGenerator.setEndTimePos(2.0F);
			
			faderInGenerator.addInputValue(0.0F, FaderGenerator.INPUT_TYPE_START_VALUE);
			faderInGenerator.addInputValue(0.5F, FaderGenerator.INPUT_TYPE_END_VALUE);
			//faderInGenerator.setStartFadeValue(0.0F);
			//faderInGenerator.setEndFadeValue(1.0F);
			
			generators.addGenerator(faderInGenerator);
		}
		//---------------------------------
		FaderGenerator faderOutGenerator;
		{
			GeneratorTypeData generatorTypeData = this.controllerData.searchGeneratorTypeData(FaderGenerator.class.getName());
			faderOutGenerator = new FaderGenerator("faderOut", Float.valueOf(frameRate), generatorTypeData);
			
			faderOutGenerator.setStartTimePos(2.0F);
			faderOutGenerator.setEndTimePos(5.0F);
			
			faderOutGenerator.addInputValue(0.5F, FaderGenerator.INPUT_TYPE_START_VALUE);
			faderOutGenerator.addInputValue(0.0F, FaderGenerator.INPUT_TYPE_END_VALUE);
			//faderOutGenerator.setStartFadeValue(1.0F);
			//faderOutGenerator.setEndFadeValue(0.0F);
			
			generators.addGenerator(faderOutGenerator);
		}
		//---------------------------------
		SinusGenerator sinusGenerator;
		{
			GeneratorTypeData generatorTypeData = this.controllerData.searchGeneratorTypeData(SinusGenerator.class.getName());
			sinusGenerator = new SinusGenerator("sinus", Float.valueOf(frameRate), generatorTypeData);
			sinusGenerator.addInputValue(262F, SinusGenerator.INPUT_TYPE_FREQ);
			//sinusGenerator.setSignalFrequency(262F);
			
			sinusGenerator.setStartTimePos(0.0F);
			sinusGenerator.setEndTimePos(5.0F);
			
			generators.addGenerator(sinusGenerator);
		}
		//---------------------------------
		SinusGenerator sinus2Generator;
		{
			GeneratorTypeData generatorTypeData = this.controllerData.searchGeneratorTypeData(SinusGenerator.class.getName());
			sinus2Generator = new SinusGenerator("sinus2", Float.valueOf(frameRate), generatorTypeData);
			sinus2Generator.addInputValue(131F, SinusGenerator.INPUT_TYPE_FREQ);
			//sinus2Generator.setSignalFrequency(131F);
			
			sinus2Generator.setStartTimePos(0.0F);
			sinus2Generator.setEndTimePos(5.0F);
			
			generators.addGenerator(sinus2Generator);
		}
		//---------------------------------
		SinusGenerator sinus3Generator;
		{
			GeneratorTypeData generatorTypeData = this.controllerData.searchGeneratorTypeData(SinusGenerator.class.getName());
			sinus3Generator = new SinusGenerator("sinus3", Float.valueOf(frameRate), generatorTypeData);
			sinus3Generator.addInputValue(70F, SinusGenerator.INPUT_TYPE_FREQ);
			//sinus3Generator.setSignalFrequency(70F);
			
			sinus3Generator.setStartTimePos(0.0F);
			sinus3Generator.setEndTimePos(5.0F);
			
			generators.addGenerator(sinus3Generator);
		}
		//---------------------------------
		MixerGenerator mixerGenerator;
		{
			GeneratorTypeData generatorTypeData = this.controllerData.searchGeneratorTypeData(MixerGenerator.class.getName());
			mixerGenerator = new MixerGenerator("mixer", Float.valueOf(frameRate), generatorTypeData);
		
			mixerGenerator.setStartTimePos(0.0F);
			mixerGenerator.setEndTimePos(5.0F);
			
			mixerGenerator.addVolumeInput(faderInGenerator);
			mixerGenerator.addVolumeInput(faderOutGenerator);
			
			mixerGenerator.addSignalInput(sinusGenerator);
			mixerGenerator.addSignalInput(sinus2Generator);
			mixerGenerator.addSignalInput(sinus3Generator);
			
			generators.addGenerator(mixerGenerator);
		}
		//---------------------------------
		OutputGenerator outputGenerator;
		{
			GeneratorTypeData generatorTypeData = this.controllerData.searchGeneratorTypeData(OutputGenerator.class.getName());
			outputGenerator = new OutputGenerator("output", Float.valueOf(frameRate), generatorTypeData);
	
			outputGenerator.setStartTimePos(0.0F);
			outputGenerator.setEndTimePos(5.0F);
			
			outputGenerator.setSignalInput(mixerGenerator);
			
			generators.addGenerator(outputGenerator);
		}		
		return outputGenerator;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.GeneratorSelectedListenerInterface#notifyGeneratorSelected(de.schmiereck.noiseComp.desktopPage.widgets.TrackData)
	 */
	public void notifyGeneratorSelected(TrackData trackGraficData)
	{
		Generator generator;
		GeneratorTypeData generatorTypeData;
		String name;
		String startTime;
		String endTime;
		Vector inputs;
		
		if (trackGraficData != null)
		{
			generator = trackGraficData.getGenerator();
			
			if (generator != null)
			{	
				name = generator.getName();
				//generatorTypeData = this.controllerData.searchGeneratorTypeData(generator);
				generatorTypeData = generator.getGeneratorTypeData();
				startTime = Float.toString(generator.getStartTimePos());
				endTime = Float.toString(generator.getEndTimePos());
				inputs = generator.getInputs();
			}
			else
			{
				name = "";
				generatorTypeData = null;
				startTime = "";
				endTime = "";
				inputs = null;
			}
		}
		else
		{
			generator = null;
			generatorTypeData = null;
			name = "";
			startTime = "";
			endTime = "";
			inputs = null;
		}
		
		this.controllerData.getGeneratorNameInputlineData().setInputText(name);
		this.controllerData.getGeneratorStartTimeInputlineData().setInputText(startTime);
		this.controllerData.getGeneratorEndTimeInputlineData().setInputText(endTime);
		this.controllerData.getGeneratorInputsData().setGeneratorInputs(generator, inputs);

		//--------------------------------------------------
		// Build the Generator-Names select List:
		{
			SelectData inputNameSelectData = this.controllerData.getGeneratorInputNameSelectData();
			
			inputNameSelectData.clearSelectEntrys();
			
			inputNameSelectData.setUseEmptyEntry(true);
			
			if (generatorTypeData != null)
			{	
				Iterator tracksDataIterator = this.controllerData.getTracksData().getListEntrysIterator();
				
				while (tracksDataIterator.hasNext())
				{
					TrackData trackData = (TrackData)tracksDataIterator.next();
					
					SelectEntryData selectEntryData = new SelectEntryData(trackData.getName(), 
							trackData.getName(),
							trackData);
					
					inputNameSelectData.addSelectEntryData(selectEntryData);
				}
			}
		}

		//--------------------------------------------------
		// Build the Generator-Input-Types select List:
		{
			SelectData inputTypeSelectData = this.controllerData.getGeneratorInputTypeSelectData();
			
			inputTypeSelectData.clearSelectEntrys();
			
			if (generatorTypeData != null)
			{	
				Iterator inputTypesDataIterator = generatorTypeData.getInputTypesDataIterator();
				
				while (inputTypesDataIterator.hasNext())
				{
					InputTypeData inputTypeData = (InputTypeData)inputTypesDataIterator.next();
					
					SelectEntryData selectEntryData = new SelectEntryData(Integer.valueOf(inputTypeData.getInputType()), 
							inputTypeData.getInputTypeName(),
							inputTypeData);
					
					inputTypeSelectData.addSelectEntryData(selectEntryData);
				}
			}	
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.GeneratorSelectedListenerInterface#notifyGeneratorDeselected(de.schmiereck.noiseComp.desktopPage.widgets.TrackData)
	 */
	public void notifyGeneratorDeselected(TrackData trackData)
	{
		this.controllerData.getGeneratorNameInputlineData().setInputText("");
		this.controllerData.getGeneratorInputNameSelectData().clearSelectEntrys();
		this.controllerData.getGeneratorInputTypeSelectData().clearSelectEntrys();
		this.controllerData.getGeneratorStartTimeInputlineData().setInputText("");
		this.controllerData.getGeneratorEndTimeInputlineData().setInputText("");
		this.controllerData.getGeneratorInputsData().setGeneratorInputs(null, null);
	}

	/**
	 * @param dir 	1: 	to move right<br/>
	 * 				-1:	to move left<br/>
	 */
	public void doMoveCursor(int dir)
	{
		WidgetData focusedWidgetData = this.controllerData.getActiveDesktopPageData().getFocusedWidgetData();
		
		if (focusedWidgetData != null)
		{
			if (focusedWidgetData instanceof InputlineData)
			{
				((InputlineData)focusedWidgetData).moveCursor(dir);
			}
		}
	}

	/**
	 * @param dir	0:	begin<br/>
	 * 				1:	end
	 */
	public void doChangeCursorPos(int dir)
	{
		WidgetData focusedWidgetData = this.controllerData.getActiveDesktopPageData().getFocusedWidgetData();
		
		if (focusedWidgetData != null)
		{
			if (focusedWidgetData instanceof InputlineData)
			{
				((InputlineData)focusedWidgetData).changeCursorPos(dir);
			}
		}
	}
	
	/**
	 * @param dir	1:	scroll Down<br/>
	 * 				-1:	scroll Up
	 */
	public void doScrollCursor(int dir)
	{
		WidgetData focusedWidgetData = this.controllerData.getActiveDesktopPageData().getFocusedWidgetData();
		
		if (focusedWidgetData != null)
		{
			if (focusedWidgetData instanceof SelectData)
			{
				((SelectData)focusedWidgetData).scrollInputPos(dir);
			}
		}
	}
	
	/**
	 * @param dir 	1: 	delete right from cursor<br/>
	 * 				-1:	delete left from cursor<br/>
	 */
	public void doDelete(int dir)
	{
		WidgetData focusedWidgetData = this.controllerData.getActiveDesktopPageData().getFocusedWidgetData();
		
		if (focusedWidgetData != null)
		{
			if (focusedWidgetData instanceof InputlineData)
			{
				((InputlineData)focusedWidgetData).deleteChar(dir);
			}
		}
	}

	/**
	 * @param c
	 */
	public void doInputChar(char c)
	{
		WidgetData focusedWidgetData = this.controllerData.getActiveDesktopPageData().getFocusedWidgetData();
		
		if (focusedWidgetData != null)
		{
			if (focusedWidgetData instanceof InputlineData)
			{
				((InputlineData)focusedWidgetData).inputChar(c);
			}
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.GeneratorTypeSelectedListenerInterface#notifyGeneratorInputSelected(InputsWidgetData, de.schmiereck.noiseComp.generator.InputData)
	 */
	public void notifyGeneratorInputSelected(InputsWidgetData inputsData, InputData selectedInputData)
	{
		String inputGeneratorName;
		String inputGeneratorTypeDescription;
		String inputValue;
		int inputType;
		
		if (selectedInputData != null)
		{
			InputTypeData inputTypeData = selectedInputData.getInputTypeData();
			inputType = inputTypeData.getInputType();
			
			Generator generator = selectedInputData.getInputGenerator();
			
			if (generator != null)
			{	
				inputGeneratorName = generator.getName();

				GeneratorTypeData generatorTypeData = generator.getGeneratorTypeData();
				
				inputGeneratorTypeDescription = inputTypeData.getInputDescription();
			}
			else
			{
				inputGeneratorName = null;
				inputGeneratorTypeDescription = null;
			}
			
			if (selectedInputData.getInputValue() != null)
			{	
				inputValue = String.valueOf(selectedInputData.getInputValue());
			}
			else
			{	
				inputValue = null;
			}
			
			//GeneratorTypeData generatorTypeData = this.controllerData.searchGeneratorTypeData(generator.getClass().getName());
		}
		else
		{
			inputGeneratorName = null;
			inputGeneratorTypeDescription = null;
			inputType = 0;
			inputValue = null;
		}
		
		//this.controllerData.getGeneratorInputNameInputlineData().setInputText(inputGeneratorName);
		this.controllerData.getGeneratorInputNameSelectData().setInputPosByValue(inputGeneratorName);
		
		SelectData selectData = this.controllerData.getGeneratorInputTypeSelectData();
		/*
		selectData.clearSelectEntrys();
		
		if (generatorTypeData != null)
		{	
			Iterator inputTypesDataIterator = generatorTypeData.getInputTypesDataIterator();
			
			while (inputTypesDataIterator.hasNext())
			{
				InputTypeData inputTypeData = (InputTypeData)inputTypesDataIterator.next();
				
				SelectEntryData selectEntryData = new SelectEntryData(Integer.valueOf(inputTypeData.getInputType()), 
						inputTypeData.getInputTypeName(),
						inputTypeData);
				
				selectData.addSelectEntryData(selectEntryData);
			}
		}
		*/	
		selectData.setInputPosByValue(Integer.valueOf(inputType));

		this.controllerData.getGeneratorInputValueInputlineData().setInputText(inputValue);
		
		this.controllerData.getGeneratorInputTypeDescriptionTextWidgetData().setLabelText(inputGeneratorTypeDescription);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.GeneratorTypeSelectedListenerInterface#notifyGeneratorInputDeselected(InputsWidgetData, de.schmiereck.noiseComp.generator.InputData)
	 */
	public void notifyGeneratorInputDeselected(InputsWidgetData inputsData, InputData deselectedInputData)
	{
		//this.controllerData.getGeneratorInputNameInputlineData().setInputText("");
		this.controllerData.getGeneratorInputNameSelectData().setInputPos(0);
		this.controllerData.getGeneratorInputTypeSelectData().setInputPos(0);
		this.controllerData.getGeneratorInputValueInputlineData().setInputText("");
	}

	/**
	 * Prepare Input-Setting to add a new Input.
	 * 
	 * @param generatorInputsData
	 */
	public void newInput(InputsWidgetData generatorInputsData)
	{
		if (generatorInputsData != null)
		{	
			generatorInputsData.deselectInput();
		}
	}
	
	/**
	 * Add a new Input witn the Input-Settings.
	 * 
	 * TODO replace the RuntimeExceptions with Message-Boxes, smk 
	 * @param generatorInputsData
	 */
	private void addInput_old(InputsWidgetData generatorInputsData)
	{
		if (generatorInputsData != null)
		{	
			//String inputGeneratorName = this.controllerData.getGeneratorInputNameInputlineData().getInputText();
			SelectEntryData inputGeneratorSelectEntryData = this.controllerData.getGeneratorInputNameSelectData().getSelectedEntryData();
			String inputGeneratorName = (String)inputGeneratorSelectEntryData.getValue();
				
			SelectEntryData selectedEntryData = this.controllerData.getGeneratorInputTypeSelectData().getSelectedEntryData();
			InputTypeData inputTypeData = (InputTypeData)selectedEntryData.getEntry();
			Integer inputType = (Integer)selectedEntryData.getValue();
			
			String inputGeneratorValueStr = this.controllerData.getGeneratorInputValueInputlineData().getInputText();
			
			Float inputGeneratorValue;
			
			if (inputGeneratorValueStr != null)
			{
				inputGeneratorValue = Float.valueOf(inputGeneratorValueStr);
			}
			else
			{
				inputGeneratorValue = null;
			}

			TrackData selectedTrackData = this.controllerData.getTracksData().getSelectedTrackData();
			
			if (selectedTrackData != null)
			{
				Generator selectedGenerator = selectedTrackData.getGenerator();
				
				InputData selectedInputData = this.controllerData.getTracksData().getGenerators().addInput(selectedGenerator, inputGeneratorName, inputTypeData, inputGeneratorValue);
				
				this.controllerData.getGeneratorInputsData().setSelectedInputData(selectedInputData);
			}
			else
			{
				throw new RuntimeException("no generator selected");
			}
		}
	}
	
	/**
	 * Set the new Input-Settings for the selected Input.
	 * If there is no selected input, {@link #addInput(InputsWidgetData)} is called.
	 *
	 * TODO replace the RuntimeExceptions with Message-Boxes, smk 
	 * @param generatorInputsData
	 * @param insertNew	true, if the input should by inserted as a new input.<br/>
	 * 					false, if the selected input should by updated.
	 */
	public void setInput(InputsWidgetData generatorInputsData, boolean insertNew)
	{
		if (generatorInputsData != null)
		{	
			TrackData selectedTrackData = this.controllerData.getTracksData().getSelectedTrackData();
			
			if (selectedTrackData != null)
			{
				//---------------------------------------------------
				// Input-Generator-Name:

				SelectEntryData inputGeneratorSelectEntryData = this.controllerData.getGeneratorInputNameSelectData().getSelectedEntryData();

				String inputGeneratorName;

				if (inputGeneratorSelectEntryData != null)
				{
					inputGeneratorName = (String)inputGeneratorSelectEntryData.getValue();
				}
				else
				{
					inputGeneratorName = null;
				}
				
				//---------------------------------------------------
				//Input-Generator:

				Generator inputGenerator;
			
				// Select the name of a generator ?
				if (inputGeneratorName != null)
				{	
					if (inputGeneratorName.length() > 0)
					{	
						TrackData inputTrackData;
						inputTrackData = this.controllerData.getTracksData().searchTrackData(inputGeneratorName);
						
						// Found no Track with the name of the Input ?
						if (inputTrackData == null)
						{
							throw new RuntimeException("input generator \"" + inputGeneratorName + "\" not found");
						}
						inputGenerator = inputTrackData.getGenerator();
					}
					else
					{
						inputGenerator = null;
					}
				}
				else
				{
					inputGenerator = null;
				}
				
				//---------------------------------------------------
				// Input-Type:

				SelectEntryData selectedEntryData = this.controllerData.getGeneratorInputTypeSelectData().getSelectedEntryData();
				InputTypeData inputTypeData = (InputTypeData)selectedEntryData.getEntry();
				Integer inputType = (Integer)selectedEntryData.getValue();
	
				//---------------------------------------------------
				// Input-Value:

				String inputGeneratorValueStr = this.controllerData.getGeneratorInputValueInputlineData().getInputText();
				
				Float inputGeneratorValue;
				
				if (inputGeneratorValueStr != null)
				{
					if (inputGeneratorValueStr.length() > 0)
					{	
						inputGeneratorValue = Float.valueOf(inputGeneratorValueStr);
					}
					else
					{
						inputGeneratorValue = null;
					}
				}
				else
				{
					inputGeneratorValue = null;
				}
				
				//---------------------------------------------------

				if ((inputGenerator == null) && (inputGeneratorValue == null))
				{
					throw new RuntimeException("no input generator and no value for the input \"" + inputGeneratorName + "\"");
				}

				InputData selectedInputData = this.controllerData.getGeneratorInputsData().getSelectedInputData();

				//No Input selected or a insert is requested.
				if ((selectedInputData == null) || (insertNew == true))
				{	
					// Insert new input:

					Generator selectedGenerator = selectedTrackData.getGenerator();

					selectedInputData = this.controllerData.getTracksData().getGenerators().addInput(selectedGenerator, inputGeneratorName, inputTypeData, inputGeneratorValue);
					
					this.controllerData.getGeneratorInputsData().setSelectedInputData(selectedInputData);
				}
				else
				{
					// Update selected input:

					selectedInputData.setInputGenerator(inputGenerator);
					selectedInputData.setInputType(inputTypeData);
					selectedInputData.setInputValue(inputGeneratorValue);
				}
			}
			else
			{
				throw new RuntimeException("no generator selected");
			}
		}
	}

	/**
	 * @param i
	 */
	public void doFocusWalk(int dir)
	{
		this.controllerData.getActiveDesktopPageData().focusWalk(dir);
	}

	/**
	 * 
	 */
	public void doSubmitPage()
	{
		this.controllerData.getActiveDesktopPageData().submitPage();
	}

	/**
	 * @param generator
	 */
	public void addDefaultInputs(Generator generator)
	{
		//GeneratorTypeData generatorTypeData = this.controllerData.searchGeneratorTypeData(generator);
		GeneratorTypeData generatorTypeData = generator.getGeneratorTypeData();
		
		Iterator inputTypesDataIterator = generatorTypeData.getInputTypesDataIterator();
		
		while (inputTypesDataIterator.hasNext())
		{
			InputTypeData inputTypeData = (InputTypeData)inputTypesDataIterator.next();
			
			for (int pos = 0; pos < inputTypeData.getInputCountMin(); pos++)
			{
				generator.addInputValue(inputTypeData.getDefaultValue(), inputTypeData);
			}
		}
	}

	/**
	 * Changes the actual zoom factor throu the given factor.
	 * 
	 * @param changeZoomFactor is the factor for the zoom factor ;-)
	 */
	public void doChangeZoom(float changeZoomFactor)
	{
		float generatorScaleX = this.controllerData.getTracksData().getGeneratorScaleX();

		generatorScaleX = generatorScaleX * changeZoomFactor;
		
		this.controllerData.getTracksData().setGeneratorScaleX(generatorScaleX);
		
		ScrollbarData scrollbarData = this.controllerData.getTimelineScrollbarData();
		
		// Anzahl Sekunden die erscrollt werden können.
		float time = scrollbarData.getScrollerLength();
		int visiblePoints = scrollbarData.getScreenScrollerLength();
		
		// Anzahl Angezeigte Sekunden berechnen.
		float visibleTime = visiblePoints / generatorScaleX;
		
		scrollbarData.setScrollerSize(visibleTime);
	}
	
	public void selectMainModul()
	{
		this.controllerData.getTracksData().clearTracks();

		Generators generators = this.controllerData.getMainGenerators();

		this.controllerData.getSoundData().setGenerators(generators);
		
		this.controllerData.setEditGenerators(null, generators);

		//-----------------------------------------------------
		// Generators updating in actual View:
		
		Iterator generatorsIterator = generators.getGeneratorsIterator();
		
		while (generatorsIterator.hasNext())
		{
			Generator generator = (Generator)generatorsIterator.next();
			
			this.addTrackData(new TrackData(generator));
		}
	}
	
}
