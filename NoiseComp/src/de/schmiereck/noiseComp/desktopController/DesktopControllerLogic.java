package de.schmiereck.noiseComp.desktopController;

import java.util.Iterator;

import de.schmiereck.noiseComp.PopupRuntimeException;
import de.schmiereck.noiseComp.desktopController.editModulPage.EditModulPageLogic;
import de.schmiereck.noiseComp.desktopController.mainPage.MainPageLogic;
import de.schmiereck.noiseComp.desktopInput.DesktopInputListener;
import de.schmiereck.noiseComp.desktopPage.DesktopPageLogic;
import de.schmiereck.noiseComp.desktopPage.widgets.MainActionException;
import de.schmiereck.noiseComp.desktopPage.widgets.InputlineData;
import de.schmiereck.noiseComp.desktopPage.widgets.SelectData;
import de.schmiereck.noiseComp.desktopPage.widgets.WidgetData;
import de.schmiereck.noiseComp.generator.FaderGenerator;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.MixerGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.SinusGenerator;
import de.schmiereck.noiseComp.generator.TrackData;
import de.schmiereck.noiseComp.generator.TracksData;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.noiseComp.soundData.SoundSchedulerLogic;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.soundSource.SoundSourceSchedulerLogic;
import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.controller.ControllerLogic;
import de.schmiereck.screenTools.controller.DataChangedListener;
import de.schmiereck.screenTools.controller.DataChangedObserver;
import de.schmiereck.screenTools.graphic.GraphicComponent;
import de.schmiereck.screenTools.scheduler.SchedulerWaiter;

/**
 * <p>
 * 	Provides the logic of the desctop controller.<br/>
 * 	The data and related objects are managed in a {@link de.schmiereck.noiseComp.desktopController.DesktopControllerData}-Object.<br/>
 * </p>
 *
 * @author smk
 * @version 08.01.2004
 */
public class DesktopControllerLogic
extends ControllerLogic
//implements //GeneratorSelectedListenerInterface, 
//GeneratorInputSelectedListenerInterface,
//EditGeneratorChangedListener
{
	/**
	 * Manages the actual Output-Generator and filles a buffer with his
	 * pre calculated samples (by polling in a thread).
	 */
	private SoundSourceLogic soundSourceLogic;
	
	private DesktopControllerData desktopControllerData;
	
	private SoundSchedulerLogic soundSchedulerLogic = null;
	
	private MainPageLogic mainPageLogic = null;
	
	private SoundSourceSchedulerLogic soundSourceSchedulerLogic;
	
	/**
	 * Constructor.
	 * 
	 * @param waiter
	 */
	public DesktopControllerLogic(DataChangedObserver dataChangedObserver,
								  SoundSourceLogic soundSourceLogic,
								  DesktopControllerData controllerData, 
								  DesktopInputListener inputListener, 
								  SchedulerWaiter waiter, 
								  String playerName)
	{
		super(dataChangedObserver, waiter);

		try
		{
			//------------------------------------------------------------------
			this.soundSourceLogic = soundSourceLogic;
			this.desktopControllerData = controllerData;
			
			//------------------------------------------------------------------
			//this.desktopControllerData.registerEditGeneratorChangedListener(this);
			
			this.soundSourceSchedulerLogic = new SoundSourceSchedulerLogic(16);

			this.soundSourceSchedulerLogic.startThread();
			
			// Start scheduled polling with the new SoundSource.
			this.soundSourceSchedulerLogic.setSoundSourceLogic(this.soundSourceLogic);

			//------------------------------------------------------------------
			// main page logic: 
			
			this.mainPageLogic = new MainPageLogic(this, 
												   this.desktopControllerData.getMainDesktopPageData());
			
			//------------------------------------------------------------------
			// edit modul page logic: 
			
			EditModulPageLogic editModulPageLogic = new EditModulPageLogic(this,
																		   this.desktopControllerData.getEditModulPageData());
			
			this.desktopControllerData.getEditModulPageData().getInputTypesListWidgetData().registerInputTypeSelectedListener(editModulPageLogic);

			this.mainPageLogic.registerEditGeneratorChangedListener(editModulPageLogic);
			
			//------------------------------------------------------------------
			
			SoundData soundData = this.desktopControllerData.getSoundData();
			//TracksListWidgetData tracksData = this.desktopControllerData.getTracksListWidgetData();

			//Generators mainGenerators = this.desktopControllerData.getMainGenerators();
			//this.desktopControllerData.getEditData().setEditGenerators(mainGenerators);
			//this.createGenerators(soundData.getFrameRate(), mainGenerators);
			
			//this.desktopControllerData.getTracksListWidgetData().registerGeneratorSelectedListener(this);
			
			//this.desktopControllerData.getGeneratorInputsData().registerGeneratorInputSelectedListener(this);
			
			this.selectMainModul();
		}
		catch (PopupRuntimeException ex)
		{
			this.desktopControllerData.setPopupRuntimeException(ex);
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#calc(de.schmiereck.screenTools.controller.ControllerData, long, double)
	 */
	public void calc(ControllerData controllerData, long actualWaitPerFramesMillis, double runSeconds)
	{
		try
		{
			controllerData.setCalcSleepMillis(actualWaitPerFramesMillis);
			
			DesktopControllerData desktopControllerData = (DesktopControllerData)controllerData;
			
			DesktopPageLogic.calcWidgets(desktopControllerData.getActiveDesktopPageData());
		}
		catch (PopupRuntimeException ex)
		{
			this.desktopControllerData.setPopupRuntimeException(ex);
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#initGameData(de.schmiereck.screenTools.controller.ControllerData)
	 */
	public void initGameData(ControllerData controllerData)
	{
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#openGameServerConnection()
	 */
	public void openGameServerConnection()
	{
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#closeGameServerConnection()
	 */
	public void closeGameServerConnection()
	{
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#openGameClientConnection()
	 */
	public void openGameClientConnection()
	{
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#closeGameClientConnection()
	 */
	public void closeGameClientConnection()
	{
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#getIsGameClientConnected()
	 */
	public boolean getIsGameClientConnected()
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#getIsGameServerConnected()
	 */
	public boolean getIsGameServerConnected()
	{
		return false;
	}

	/**
	 * 
	 */
	public void doEndGame()
	{
		try
		{
			this.soundSourceSchedulerLogic.stopThread();
			this.stopWaitGame();
		}
		catch (PopupRuntimeException ex)
		{
			this.desktopControllerData.setPopupRuntimeException(ex);
		}
	}

	/**
	 * Wird vom Input-Listener aufgerufen, wenn sich die Mausposition geändert hat.
	 */
	public void movePointer(int posX, int posY)
	{
		try
		{
			if (this.desktopControllerData.getDesktopData().setPointerPos(posX, posY) == true)
			{
				//this.dataChanged();
				//System.out.println("chnaged");
			}
		}
		catch (PopupRuntimeException ex)
		{
			this.desktopControllerData.setPopupRuntimeException(ex);
		}
	}

	/**
	 * Wird vom Input-Listener aufgerufen, wenn die Maustaste gedrückt wurde.
	 */
	public void pointerPressed()
	{
		try
		{
			DesktopPageLogic.pointerPressed(this.desktopControllerData.getActiveDesktopPageData());
			//this.dataChanged();
		}
		catch (PopupRuntimeException ex)
		{
			this.desktopControllerData.setPopupRuntimeException(ex);
		}
	}

	public void pointerReleased()
	{
		try
		{
			DesktopPageLogic.pointerReleased(this.desktopControllerData.getActiveDesktopPageData());
			//this.dataChanged();
		}
		catch (PopupRuntimeException ex)
		{
			this.desktopControllerData.setPopupRuntimeException(ex);
		}
	}

	public void stopSound()
	{
		try
		{
			if (this.soundSchedulerLogic != null)
			{
				this.soundSchedulerLogic.stopPlayback();
				
				this.soundSchedulerLogic.stopThread();
				
				this.soundSchedulerLogic = null;
			}
		}
		catch (PopupRuntimeException ex)
		{
			this.desktopControllerData.setPopupRuntimeException(ex);
		}
	}

	public void pauseSound()
	{
		try
		{
			if (this.soundSchedulerLogic != null)
			{
				this.soundSchedulerLogic.pausePlayback();
			}
		}
		catch (PopupRuntimeException ex)
		{
			this.desktopControllerData.setPopupRuntimeException(ex);
		}
	}

	public void playSound()
	{
		try
		{
			if (this.soundSchedulerLogic == null)
			{
				SoundData soundData = this.desktopControllerData.getSoundData();
				
				this.soundSchedulerLogic = new SoundSchedulerLogic(25, soundData);
				
				this.soundSchedulerLogic.startThread();
	
				this.soundSchedulerLogic.startPlayback();
			}
			else
			{	
				this.soundSchedulerLogic.resumePlayback();
			}
		}
		catch (PopupRuntimeException ex)
		{
			this.desktopControllerData.setPopupRuntimeException(ex);
		}
	}

	/**
	 * <p>
	 * 	Add the generator to the MainGenerators (if selected for edit) or to the
	 * 	for edit selected ModulGenerator.
	 * </p>
	 * <p>
	 * 	Get the active track list and and the genarator to this list.
	 * </p>
	 * <p>
	 * 	Put the generator into a new {@link TracksListWidgetData}-Object and add the
	 * 	new track to the track list.
	 * </p>
	 * <p>
	 * 	If the generator is a {@link OutputGenerator}-Object, 
	 * 	this Object is set as output for the sound data.
	 * </p>
	 * 
	 * @see #addGenerator(TracksListWidgetData, Generator)
	 * @param generator
	 */
	public void addGenerator(Generator generator)
	{
		EditData editData = this.desktopControllerData.getEditData();
		
		ModulGeneratorTypeData editModulTypeData = editData.getEditModulTypeData();
		
		if (editModulTypeData != null)
		{
			TrackData trackData = editModulTypeData.addGenerator(generator);

			//----------------------------------------------------------------------
			// Manage a new Output-Generator specialy:
			
			if (generator instanceof OutputGenerator)
			{	
				OutputGenerator outputGenerator = (OutputGenerator)generator;
				
				//SoundData soundData = this.desktopControllerData.getSoundData();

				this.soundSourceLogic.setOutputGenerator(outputGenerator);

				// Start scheduled polling for the new SoundSource.
				//this.soundSourceSchedulerLogic.setSoundSourceLogic(this.soundSourceLogic);
				//soundData.setOutputGenerator(outputGenerator);
			}

			//Generators generators = editModulTypeData.getGenerators();
	
			//generators.addGenerator(generator);
			/*
			ModulGeneratorTypeData modulGeneratorTypeData = editData.getEditModulTypeData();
	
			if (modulGeneratorTypeData != null)
			{
				Generators generators = modulGeneratorTypeData.getGenerators();
				
				generators.addGenerator(generator);
			}
			else
			{		
				this.desktopControllerData.getMainGenerators().addGenerator(generator);
			}
			*/
			
			//TrackData trackData = new TrackData(generator);
			
			//this.addTrackData(trackData);
		}
		else
		{
			throw new PopupRuntimeException("No main modul present to insert");
		}
	}

	/**
	 * 	Add the Track to the visible list of generators in the main page.<br/>
	 * 	If the generator in the track is a {@link OutputGenerator},
	 * 	the generator is set as as Output in a new 
	 * 	{@link SoundSourceLogic}-Object.
	 * 
	 * @param trackData
	public void addTrackData(TrackData trackData)
	{
		//----------------------------------------------------------------------
		// Adding the Track:
		
		this.mainPageLogic.addTrackData(trackData);
		//TracksListWidgetData tracksListWidgetData = this.desktopControllerData.getTracksListWidgetData();
		//tracksListWidgetData.addTrack(trackData);

		//----------------------------------------------------------------------
		// Manage a new Output-Generator specialy:
		
		Generator generator = trackData.getGenerator();
		
		if (generator instanceof OutputGenerator)
		{	
			OutputGenerator outputGenerator = (OutputGenerator)generator;
			
			//SoundData soundData = this.desktopControllerData.getSoundData();

			this.soundSourceLogic.setOutputGenerator(outputGenerator);

			// Start scheduled polling for the new SoundSource.
			//this.soundSourceSchedulerLogic.setSoundSourceLogic(this.soundSourceLogic);
			//soundData.setOutputGenerator(outputGenerator);
		}
	}
	 */

	/**
	 * Creates a demo list of generators with different types.
	 * It's only for developing.
	 */
	public OutputGenerator createDemoGenerators(float frameRate, 
												 ModulGeneratorTypeData mainModulTypeData)
	{
		// Sound-Generatoren für das Sound-Format des Ausgabekanals erzeugen:
		
		//Generators generators = mainModulTypeData.getGenerators();

		//---------------------------------
		FaderGenerator faderInGenerator;
		{
			GeneratorTypeData generatorTypeData = this.desktopControllerData.searchGeneratorTypeData(FaderGenerator.class.getName());
			faderInGenerator = new FaderGenerator("faderIn", Float.valueOf(frameRate), generatorTypeData);
			
			faderInGenerator.setStartTimePos(0.0F);
			faderInGenerator.setEndTimePos(2.0F);
			
			faderInGenerator.addInputValue(0.0F, FaderGenerator.INPUT_TYPE_START_VALUE);
			faderInGenerator.addInputValue(0.5F, FaderGenerator.INPUT_TYPE_END_VALUE);
			//faderInGenerator.setStartFadeValue(0.0F);
			//faderInGenerator.setEndFadeValue(1.0F);
			
			mainModulTypeData.addGenerator(faderInGenerator);
		}
		//---------------------------------
		FaderGenerator faderOutGenerator;
		{
			GeneratorTypeData generatorTypeData = this.desktopControllerData.searchGeneratorTypeData(FaderGenerator.class.getName());
			faderOutGenerator = new FaderGenerator("faderOut", Float.valueOf(frameRate), generatorTypeData);
			
			faderOutGenerator.setStartTimePos(2.0F);
			faderOutGenerator.setEndTimePos(5.0F);
			
			faderOutGenerator.addInputValue(0.5F, FaderGenerator.INPUT_TYPE_START_VALUE);
			faderOutGenerator.addInputValue(0.0F, FaderGenerator.INPUT_TYPE_END_VALUE);
			//faderOutGenerator.setStartFadeValue(1.0F);
			//faderOutGenerator.setEndFadeValue(0.0F);
			
			mainModulTypeData.addGenerator(faderOutGenerator);
		}
		//---------------------------------
		SinusGenerator sinusGenerator;
		{
			GeneratorTypeData generatorTypeData = this.desktopControllerData.searchGeneratorTypeData(SinusGenerator.class.getName());
			sinusGenerator = new SinusGenerator("sinus", Float.valueOf(frameRate), generatorTypeData);
			sinusGenerator.addInputValue(262F, SinusGenerator.INPUT_TYPE_FREQ);
			//sinusGenerator.setSignalFrequency(262F);
			
			sinusGenerator.setStartTimePos(0.0F);
			sinusGenerator.setEndTimePos(5.0F);
			
			mainModulTypeData.addGenerator(sinusGenerator);
		}
		//---------------------------------
		SinusGenerator sinus2Generator;
		{
			GeneratorTypeData generatorTypeData = this.desktopControllerData.searchGeneratorTypeData(SinusGenerator.class.getName());
			sinus2Generator = new SinusGenerator("sinus2", Float.valueOf(frameRate), generatorTypeData);
			sinus2Generator.addInputValue(131F, SinusGenerator.INPUT_TYPE_FREQ);
			//sinus2Generator.setSignalFrequency(131F);
			
			sinus2Generator.setStartTimePos(0.0F);
			sinus2Generator.setEndTimePos(5.0F);
			
			mainModulTypeData.addGenerator(sinus2Generator);
		}
		//---------------------------------
		SinusGenerator sinus3Generator;
		{
			GeneratorTypeData generatorTypeData = this.desktopControllerData.searchGeneratorTypeData(SinusGenerator.class.getName());
			sinus3Generator = new SinusGenerator("sinus3", Float.valueOf(frameRate), generatorTypeData);
			sinus3Generator.addInputValue(70F, SinusGenerator.INPUT_TYPE_FREQ);
			//sinus3Generator.setSignalFrequency(70F);
			
			sinus3Generator.setStartTimePos(0.0F);
			sinus3Generator.setEndTimePos(5.0F);
			
			mainModulTypeData.addGenerator(sinus3Generator);
		}
		//---------------------------------
		MixerGenerator mixerGenerator;
		{
			GeneratorTypeData generatorTypeData = this.desktopControllerData.searchGeneratorTypeData(MixerGenerator.class.getName());
			mixerGenerator = new MixerGenerator("mixer", Float.valueOf(frameRate), generatorTypeData);
		
			mixerGenerator.setStartTimePos(0.0F);
			mixerGenerator.setEndTimePos(5.0F);
			
			mixerGenerator.addVolumeInput(faderInGenerator);
			mixerGenerator.addVolumeInput(faderOutGenerator);
			
			mixerGenerator.addSignalInput(sinusGenerator);
			mixerGenerator.addSignalInput(sinus2Generator);
			mixerGenerator.addSignalInput(sinus3Generator);
			
			mainModulTypeData.addGenerator(mixerGenerator);
		}
		//---------------------------------
		OutputGenerator outputGenerator;
		{
			GeneratorTypeData generatorTypeData = this.desktopControllerData.searchGeneratorTypeData(OutputGenerator.class.getName());
			outputGenerator = new OutputGenerator("output", Float.valueOf(frameRate), generatorTypeData);
	
			outputGenerator.setStartTimePos(0.0F);
			outputGenerator.setEndTimePos(5.0F);
			
			outputGenerator.setSignalInput(mixerGenerator);
			
			mainModulTypeData.addGenerator(outputGenerator);
		}		
		return outputGenerator;
	}


	/**
	 * @param dir 	1: 	to move right<br/>
	 * 				-1:	to move left<br/>
	 */
	public void doMoveCursor(int dir)
	{
		WidgetData focusedWidgetData = this.desktopControllerData.getActiveDesktopPageData().getFocusedWidgetData();
		
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
		WidgetData focusedWidgetData = this.desktopControllerData.getActiveDesktopPageData().getFocusedWidgetData();
		
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
		WidgetData focusedWidgetData = this.desktopControllerData.getActiveDesktopPageData().getFocusedWidgetData();
		
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
		WidgetData focusedWidgetData = this.desktopControllerData.getActiveDesktopPageData().getFocusedWidgetData();
		
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
		WidgetData focusedWidgetData = this.desktopControllerData.getActiveDesktopPageData().getFocusedWidgetData();
		
		if (focusedWidgetData != null)
		{
			if (focusedWidgetData instanceof InputlineData)
			{
				((InputlineData)focusedWidgetData).inputChar(c);
			}
		}
	}
	
	/**
	 * @param i
	 */
	public void doFocusWalk(int dir)
	{
		this.desktopControllerData.getActiveDesktopPageData().focusWalk(dir);
	}

	/**
	 * Defaultverhalten der Seite bei einem Submit (z.B. durch ENTER) auslösen.
	 */
	public void doSubmitPage()
	{
		try
		{
			// Aktive Seite holen und Submit auslösen.
			this.desktopControllerData.getActiveDesktopPageData().submitPage();
		}
		catch (MainActionException ex)
		{
			this.desktopControllerData.setPopupRuntimeException(ex);
		}
	}

	/**
	 * @param generator
	 */
	public void addDefaultInputs(Generator generator)
	{
		//GeneratorTypeData generatorTypeData = this.desktopControllerData.searchGeneratorTypeData(generator);
		GeneratorTypeData generatorTypeData = generator.getGeneratorTypeData();
		
		Iterator inputTypesDataIterator = generatorTypeData.getInputTypesIterator();
		
		while (inputTypesDataIterator.hasNext())
		{
			InputTypeData inputTypeData = (InputTypeData)inputTypesDataIterator.next();
			
			for (int pos = 0; pos < inputTypeData.getInputCountMin(); pos++)
			{
				generator.addInputValue(inputTypeData.getDefaultValue(), inputTypeData);
			}
		}
	}
	
	public void selectMainModul()
	{
		EditData editData = this.desktopControllerData.getEditData();

		ModulGeneratorTypeData mainModulGeneratorTypeData = editData.getMainModulTypeData();

		///this.desktopControllerData.getSoundData().setGenerators(generators);
		//this.desktopControllerData.getSoundData().setOutputGenerator(generators.getOutputGenerator());
		//???this.soundSourceLogic = new SoundSourceLogic(generators.getOutputGenerator());
		//???this.desktopControllerData.getSoundData().setSoundSourceLogic(this.soundSourceLogic);
		
		// endlich die mainGenerators auch in einen ModulGenerator verpacken und nicht mehr als spezialfall abhandeln, smk
		this.selectModulGeneratorToEdit(mainModulGeneratorTypeData);
	}
	
	/**
	 * 	Make the edited generators as the edited list of 
	 * 	tracks on the main page.<br/>
	 * Set this Tracks for editing.
	 * 	
	 * @see #addTrackData(TrackData) because of the handling of the OutputGenerator.
	 * 
	 */
	public void selectModulGeneratorToEdit(ModulGeneratorTypeData modulGeneratorTypeData)
	{
		EditData editData = this.desktopControllerData.getEditData();
		
		editData.setEditModulGenerator(modulGeneratorTypeData);

		//-----------------------------------------------------
		// Clear the list with the prviouse selected generators.
		//this.mainPageLogic.clearTracks();

		//-----------------------------------------------------
		// Generators updating in actual View:
		
		if (modulGeneratorTypeData != null)
		{
			this.getMainPageLogic().setEditModulGeneratorTypeData(modulGeneratorTypeData);
			//Generators generators = modulTypeData.getGenerators();
		
			//if (generators != null)
			/*
			{
				Iterator generatorsIterator = modulTypeData.getGeneratorsIterator();
				
				while (generatorsIterator.hasNext())
				{
					Generator generator = (Generator)generatorsIterator.next();
					
					this.addTrackData(new TrackData(generator));
				}
			}
			*/
			OutputGenerator outputGenerator = modulGeneratorTypeData.getOutputGenerator();
			
			//SoundData soundData = this.desktopControllerData.getSoundData();

			if (outputGenerator != null)
			{
				this.soundSourceLogic.setOutputGenerator(outputGenerator);
			}
		}

		//-----------------------------------------------------
		this.mainPageLogic.triggerEditGeneratorChanged(editData);
	}

	/**
	 * @return the attribute {@link #mainPageLogic}.
	 */
	public MainPageLogic getMainPageLogic()
	{
		return this.mainPageLogic;
	}

	/**
	 * Update the Tracks with a new list of generators and switch to the main page.
	 * 
	 * @param mainGenerators
	public void updateEditModul(Generators mainGenerators)
	{
		//-----------------------------------------------------
		// Generators updating in actual View:
		
		Iterator generatorsIterator = mainGenerators.getGeneratorsIterator();
		
		while (generatorsIterator.hasNext())
		{
			Generator generator = (Generator)generatorsIterator.next();
			
			this.addTrackData(new TrackData(generator));
		}

		//-----------------------------------------------------
		this.desktopControllerData.setActiveDesktopPageData(this.desktopControllerData.getMainDesktopPageData());
	}
	 */
}
