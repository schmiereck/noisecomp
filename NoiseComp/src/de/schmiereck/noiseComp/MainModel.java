package de.schmiereck.noiseComp;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopController.DesktopGraphic;
import de.schmiereck.noiseComp.desktopController.EditData;
import de.schmiereck.noiseComp.desktopController.actions.AddGeneratorButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.ExitButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.GroupGeneratorButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.LoadButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.LoadCancelButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.LoadFileButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.NewButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.PauseButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.PlayButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.SaveButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.SaveCancelButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.SaveFileButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.StopButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.editModulPage.actions.CancelGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.editModulPage.actions.InputTypeEditSaveActionListener;
import de.schmiereck.noiseComp.desktopController.editModulPage.actions.SaveGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.AddInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.NewInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.RemoveGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.RemoveInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.SetGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.SetInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.ZoomInButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.ZoomOutButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectAddButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectEditButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectInsertButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectMainEditButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectRemoveButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopInput.DesktopInputListener;
import de.schmiereck.noiseComp.generator.CutGenerator;
import de.schmiereck.noiseComp.generator.FaderGenerator;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.MixerGenerator;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.RectangleGenerator;
import de.schmiereck.noiseComp.generator.SinusGenerator;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.screenTools.Runner;
import de.schmiereck.screenTools.RunnerSchedulers;
import de.schmiereck.screenTools.controller.ControllerSchedulerLogic;
import de.schmiereck.screenTools.scheduler.SchedulerWaiter;

/*
 * Created on 25.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	TODO docu type
 * </p>
 * 
 * @author smk
 * @version <p>25.03.2005:	created, smk</p>
 */
public class MainModel
{
	private SoundData soundData;
	
	private GeneratorTypesData generatorTypesData;
	
	private DesktopControllerData controllerData;
	
	private DesktopControllerLogic controllerLogic;
	
	private DesktopInputListener inputListener;
	
	private SchedulerWaiter waiter;

	public MainModel()
	{
		//======================================================================
		// Setup Sound:
		
		SourceDataLine line = MainModel.createLine();
		
		//----------------------------------------------------------------------
		SoundSourceLogic soundSourceLogic = new SoundSourceLogic();
		
		this.soundData = new SoundData(line, soundSourceLogic);
		
		//======================================================================
		this.generatorTypesData  = new GeneratorTypesData();

		this.createBaseGeneratorTypes();
		
		// new ModulGeneratorTypeData(null, null, null);
		ModulGeneratorTypeData mainModulGeneratorTypeData = ModulGenerator.createModulGeneratorTypeData();

		mainModulGeneratorTypeData.setIsMainModulGeneratorType(true);
		
		mainModulGeneratorTypeData.setGeneratorTypeName("Main-Modul");

		this.generatorTypesData.addGeneratorTypeData(mainModulGeneratorTypeData);
		
		//======================================================================
		// Setup Desktop:

		String playerName = null;
		
		this.waiter = new SchedulerWaiter();

		this.inputListener = new DesktopInputListener();
		
		//----------------------------------------------------------------------
		this.controllerData = new DesktopControllerData(this.soundData, this.generatorTypesData);
		
		EditData editData = this.controllerData.getEditData();
		
		editData.setMainModulTypeData(mainModulGeneratorTypeData);

		//editData.setEditModulGenerator(mainModulGeneratorTypeData);

		//----------------------------------------------------------------------
		this.controllerLogic = new DesktopControllerLogic(soundSourceLogic,
														  controllerData, 
														  this.inputListener, 
														  this.waiter, 
														  playerName);
		
		this.controllerLogic.createDemoGenerators(soundData.getFrameRate(), mainModulGeneratorTypeData);
		
		this.controllerLogic.selectModulGeneratorToEdit(mainModulGeneratorTypeData);
		
		//----------------------------------------------------------------------
		this.inputListener.setGameControllerLogic(controllerLogic);
		
		AddGeneratorButtonActionLogicListener addGeneratorButtonActionLogicListener	= new AddGeneratorButtonActionLogicListener(controllerLogic, controllerData);
		RemoveGeneratorButtonActionLogicListener removeGeneratorButtonActionLogicListener = new RemoveGeneratorButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		GroupGeneratorButtonActionLogicListener groupGeneratorButtonActionLogicListener = new GroupGeneratorButtonActionLogicListener(controllerLogic, controllerData, controllerData.getEditModulPageData());

		PlayButtonActionLogicListener playButtonActionLogicListener = new PlayButtonActionLogicListener(controllerLogic, controllerData);
		PauseButtonActionLogicListener pauseButtonActionLogicListener = new PauseButtonActionLogicListener(controllerLogic, controllerData);
		StopButtonActionLogicListener stopButtonActionLogicListener = new StopButtonActionLogicListener(controllerLogic, controllerData);
		
		//ExitButtonActionLogicListener exitButtonActionLogicListener = new ExitButtonActionLogicListener(controllerLogic);
		//NewButtonActionLogicListener newButtonActionLogicListener = new NewButtonActionLogicListener(controllerLogic, controllerData);

		ZoomInButtonActionLogicListener zoomInButtonActionLogicListener = new ZoomInButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		ZoomOutButtonActionLogicListener zoomOutButtonActionLogicListener = new ZoomOutButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		
		SetGeneratorButtonActionLogicListener setGeneratorButtonActionLogicListener = new SetGeneratorButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		SetInputButtonActionLogicListener setInputButtonActionLogicListener = new SetInputButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		RemoveInputButtonActionLogicListener removeInputButtonActionLogicListener = new RemoveInputButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		NewInputButtonActionLogicListener newInputButtonActionLogicListener = new NewInputButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		AddInputButtonActionLogicListener addInputButtonActionLogicListener = new AddInputButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		
		SelectCancelButtonActionLogicListener selectCancelButtonActionLogicListener = new SelectCancelButtonActionLogicListener(controllerLogic, controllerData, controllerData.getSelectGeneratorPageData());
		SelectAddButtonActionLogicListener selectAddButtonActionLogicListener = new SelectAddButtonActionLogicListener(controllerLogic, controllerData, controllerData.getSelectGeneratorPageData());
		SelectInsertButtonActionLogicListener selectInsertButtonActionLogicListener = new SelectInsertButtonActionLogicListener(controllerLogic, controllerData, controllerData.getSelectGeneratorPageData());
		SelectEditButtonActionLogicListener selectEditButtonActionLogicListener = new SelectEditButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData, controllerData.getSelectGeneratorPageData());
		SelectMainEditButtonActionLogicListener selectMainEditButtonActionLogicListener = new SelectMainEditButtonActionLogicListener(controllerLogic, controllerData, controllerData.getSelectGeneratorPageData());
		SelectRemoveButtonActionLogicListener selectRemoveButtonActionLogicListener = new SelectRemoveButtonActionLogicListener(controllerLogic, controllerData, controllerData.getSelectGeneratorPageData());
		
		//SaveButtonActionLogicListener saveButtonActionLogicListener = new SaveButtonActionLogicListener(controllerLogic, controllerData);
		//SaveCancelButtonActionLogicListener saveCancelButtonActionLogicListener = new SaveCancelButtonActionLogicListener(controllerLogic, controllerData);
		//SaveFileButtonActionLogicListener saveFileButtonActionLogicListener = new SaveFileButtonActionLogicListener(controllerLogic, controllerData);
		
		//LoadButtonActionLogicListener loadButtonActionLogicListener = new LoadButtonActionLogicListener(controllerLogic, controllerData);
		//LoadCancelButtonActionLogicListener loadCancelButtonActionLogicListener = new LoadCancelButtonActionLogicListener(controllerLogic, controllerData);
		//LoadFileButtonActionLogicListener loadFileButtonActionLogicListener = new LoadFileButtonActionLogicListener(controllerLogic, controllerData);
		
		CancelGroupButtonActionLogicListener cancelGroupButtonActionLogicListener = new CancelGroupButtonActionLogicListener(controllerLogic, controllerData);
		SaveGroupButtonActionLogicListener saveGroupButtonActionLogicListener = new SaveGroupButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData, controllerData.getEditModulPageData());
		
		InputTypeEditSaveActionListener inputTypeEditSaveActionListener = new InputTypeEditSaveActionListener(controllerLogic, controllerData, controllerData.getEditModulPageData());
		
		controllerData.setActionListeners(
				addGeneratorButtonActionLogicListener,
				removeGeneratorButtonActionLogicListener,
				groupGeneratorButtonActionLogicListener,
				
				playButtonActionLogicListener,
				pauseButtonActionLogicListener,
				stopButtonActionLogicListener,
				
				//exitButtonActionLogicListener,
				//newButtonActionLogicListener,
				
				zoomInButtonActionLogicListener,
				zoomOutButtonActionLogicListener,
				
				setGeneratorButtonActionLogicListener,
				setInputButtonActionLogicListener,
				removeInputButtonActionLogicListener,
				newInputButtonActionLogicListener,
				addInputButtonActionLogicListener,
				
				selectCancelButtonActionLogicListener,
				selectAddButtonActionLogicListener,
				selectInsertButtonActionLogicListener,
				selectEditButtonActionLogicListener,
				selectMainEditButtonActionLogicListener,
				selectRemoveButtonActionLogicListener,
										  
				//saveButtonActionLogicListener,
				//saveCancelButtonActionLogicListener,
				//saveFileButtonActionLogicListener,
										  
				//loadButtonActionLogicListener,
				//loadCancelButtonActionLogicListener,
				//loadFileButtonActionLogicListener,
				
				cancelGroupButtonActionLogicListener,
				saveGroupButtonActionLogicListener,
				
				inputTypeEditSaveActionListener);
		
		//----------------------------------------------------------------------
		// run:

		int controllerTargetFramesPerSecond = 16;
		
		ControllerSchedulerLogic gameSchedulerLogic = new ControllerSchedulerLogic(this.controllerData, 
																				   this.controllerLogic, 
																				   this.waiter, 
																				   controllerTargetFramesPerSecond);
		
		this.controllerLogic.initGameData(controllerData);

		gameSchedulerLogic.startThread();
		/*
		RunnerSchedulers runnerSchedulers;
		
		runnerSchedulers = Runner.start(this.getControllerData(), 
										this.mainView.getControllerLogic(), 
										this.mainView.getMultiBufferGraphic(), 
										this.mainView.getInputListener(), 
										this.mainView.getWaiter(), 24, 16,
										false, 
										false,
										-1, -1);
		
		//Runner.stop(runnerSchedulers);
		 * 
		 */
	}

	public void createBaseGeneratorTypes()
	{
		this.generatorTypesData.clear();
		
		this.generatorTypesData.addGeneratorTypeData(FaderGenerator.createGeneratorTypeData());
		this.generatorTypesData.addGeneratorTypeData(MixerGenerator.createGeneratorTypeData());
		this.generatorTypesData.addGeneratorTypeData(OutputGenerator.createGeneratorTypeData());
		this.generatorTypesData.addGeneratorTypeData(SinusGenerator.createGeneratorTypeData());
		this.generatorTypesData.addGeneratorTypeData(RectangleGenerator.createGeneratorTypeData());
		this.generatorTypesData.addGeneratorTypeData(CutGenerator.createGeneratorTypeData());
	}

	private static SourceDataLine createLine()
	{
		//SourceDataLine sourceDataLine = new SourceDataLine();
		
		// Gew�nschtes Audioformat definieren:
		
		float sampleRate 		= 44100;		// the number of samples per second
		int sampleSizeInBits 	= 16;			// the number of bits in each sample
		int channels 			= 2;			// the number of channels (1 for mono, 2 for stereo, and so on)
		int frameSize 			= 4;			// the number of bytes in each frame
		float frameRate 		= sampleRate;	// the number of frames per second
		boolean bigEndian 		= false; 		// indicates whether the data for a single sample is stored in big-endian byte order 
												// (false means little-endian)
		
		AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,	// encoding the audio encoding technique
												  sampleRate, 
												  sampleSizeInBits, 
												  channels, 
												  frameSize, 
												  frameRate, 
												  bigEndian);
		
		DataLine.Info	info = new DataLine.Info(SourceDataLine.class, audioFormat);

		// Ausgabekanal mit den gew�nschten Eigenschaften holen und �ffnen:
		
		SourceDataLine line;
		
		try
		{
			line = (SourceDataLine)AudioSystem.getLine(info);
		}
		catch (LineUnavailableException ex)
		{
			throw new RuntimeException("getLine info", ex);
		}
		
		try
		{
			line.open(audioFormat);
		}
		catch (LineUnavailableException ex)
		{
			throw new RuntimeException("open line", ex);
		}
		
		//line.start();
		return line;
	}
	
	/**
	 * @return returns the {@link #soundData}.
	 */
	public SoundData getSoundData()
	{
		return this.soundData;
	}
	/**
	 * @return returns the {@link #controllerData}.
	 */
	public DesktopControllerData getControllerData()
	{
		return this.controllerData;
	}
	/**
	 * @return returns the {@link #inputListener}.
	 */
	public DesktopInputListener getInputListener()
	{
		return this.inputListener;
	}
	/**
	 * @return returns the {@link #waiter}.
	 */
	public SchedulerWaiter getWaiter()
	{
		return this.waiter;
	}
	/**
	 * @return returns the {@link #controllerLogic}.
	 */
	public DesktopControllerLogic getControllerLogic()
	{
		return this.controllerLogic;
	}
}
