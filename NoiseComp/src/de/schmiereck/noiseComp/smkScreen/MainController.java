package de.schmiereck.noiseComp.smkScreen;

import javax.sound.sampled.SourceDataLine;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.service.StartupService;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.smkScreen.desktopController.EditData;
import de.schmiereck.noiseComp.smkScreen.desktopController.actions.old.AddGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.actions.old.ExitButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.actions.old.GroupGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.actions.old.LoadButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.actions.old.LoadCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.actions.old.LoadFileButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.actions.old.NewButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.actions.old.PauseButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.actions.old.PlayButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.actions.old.SaveButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.actions.old.SaveCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.actions.old.SaveFileButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.actions.old.StopButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.editModulPage.actions.CancelGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.editModulPage.actions.InputTypeEditSaveActionListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.editModulPage.actions.SaveGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.mainPage.actions.AddInputButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.mainPage.actions.NewInputButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.mainPage.actions.RemoveGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.mainPage.actions.RemoveInputButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.mainPage.actions.SetGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.mainPage.actions.SetInputButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.mainPage.actions.old.ZoomInButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.mainPage.actions.old.ZoomOutButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.selectGeneratorPage.actions.SelectAddButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.selectGeneratorPage.actions.SelectCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.selectGeneratorPage.actions.SelectEditButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.selectGeneratorPage.actions.SelectInsertButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.selectGeneratorPage.actions.SelectMainEditButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.selectGeneratorPage.actions.SelectRemoveButtonActionLogicListener;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.screenTools.controller.DataChangedListener;
import de.schmiereck.screenTools.controller.DataChangedObserver;
import de.schmiereck.screenTools.graphic.GraphicMediator;
import de.schmiereck.screenTools.scheduler.SchedulerWaiter;

/*
 * Created on 25.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	ConsoleMain-Controller.
 * </p>
 * 
 * @author smk
 * @version <p>25.03.2005:	created, smk</p>
 */
public class MainController
{
	private SoundData soundData;
	
	/**
	 * List of {@link DataChangedListener}-Objects.
	 */
	private DataChangedObserver dataChangedObserver;
	
	private DesktopControllerData controllerData;
	
	private DesktopControllerLogic controllerLogic;
	
	private SchedulerWaiter waiter;

	private ZoomInButtonActionLogicListener zoomInButtonActionLogicListener;
	private ZoomOutButtonActionLogicListener zoomOutButtonActionLogicListener;
	
	public MainController(//ModulGeneratorTypeData mainModulGeneratorTypeData,
	                      GraphicMediator graphicMediator)
	{
		//======================================================================
		// Setup Sound:
		
		SourceDataLine line = StartupService.createLine();
		
		//----------------------------------------------------------------------
		SoundSourceLogic soundSourceLogic = new SoundSourceLogic();
		
		this.soundData = new SoundData(line, soundSourceLogic);
		
		//----------------------------------------------------------------------
		ModulGeneratorTypeData mainModulGeneratorTypeData = 
			StartupService.createDemoGenerators(soundData.getFrameRate());

//		OutputGenerator outputGenerator = mainModulGeneratorTypeData.getOutputGenerator();
		
		//======================================================================
		// Setup Desktop:

		String playerName = null;
		
		this.waiter = new SchedulerWaiter();

		//----------------------------------------------------------------------
		this.dataChangedObserver = new DataChangedObserver();
		
		//----------------------------------------------------------------------
		this.controllerData = new DesktopControllerData(this.dataChangedObserver,
														this.soundData, 
														graphicMediator);
		
		EditData editData = this.controllerData.getEditData();
		
		editData.setMainModulTypeData(mainModulGeneratorTypeData);

		//editData.setEditModulGenerator(mainModulGeneratorTypeData);

		//----------------------------------------------------------------------
		this.controllerLogic = new DesktopControllerLogic(this.dataChangedObserver,
														  soundSourceLogic,
														  this.controllerData, 
														  ///this.inputListener, 
														  this.waiter, 
														  playerName);
		
		StartupService.createDemoGenerators(soundData.getFrameRate()); 
		                                    //mainModulGeneratorTypeData);
		
		this.controllerLogic.selectModulGeneratorToEdit(mainModulGeneratorTypeData);
		
		//----------------------------------------------------------------------
		AddGeneratorButtonActionLogicListener addGeneratorButtonActionLogicListener	= new AddGeneratorButtonActionLogicListener(controllerLogic, controllerData);
		RemoveGeneratorButtonActionLogicListener removeGeneratorButtonActionLogicListener = new RemoveGeneratorButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		GroupGeneratorButtonActionLogicListener groupGeneratorButtonActionLogicListener = new GroupGeneratorButtonActionLogicListener(controllerLogic, controllerData, controllerData.getEditModulPageData());

		PlayButtonActionLogicListener playButtonActionLogicListener = new PlayButtonActionLogicListener(controllerLogic, controllerData);
		PauseButtonActionLogicListener pauseButtonActionLogicListener = new PauseButtonActionLogicListener(controllerLogic, controllerData);
		StopButtonActionLogicListener stopButtonActionLogicListener = new StopButtonActionLogicListener(controllerLogic, controllerData);
		
		ExitButtonActionLogicListener exitButtonActionLogicListener = new ExitButtonActionLogicListener(controllerLogic);
		NewButtonActionLogicListener newButtonActionLogicListener = new NewButtonActionLogicListener(controllerLogic, controllerData);

		this.zoomInButtonActionLogicListener = new ZoomInButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		this.zoomOutButtonActionLogicListener = new ZoomOutButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		
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
		
		SaveButtonActionLogicListener saveButtonActionLogicListener = new SaveButtonActionLogicListener(controllerLogic, controllerData);
		SaveCancelButtonActionLogicListener saveCancelButtonActionLogicListener = new SaveCancelButtonActionLogicListener(controllerLogic, controllerData);
		SaveFileButtonActionLogicListener saveFileButtonActionLogicListener = new SaveFileButtonActionLogicListener(controllerLogic, controllerData);
		
		LoadButtonActionLogicListener loadButtonActionLogicListener = new LoadButtonActionLogicListener(controllerLogic, controllerData);
		LoadCancelButtonActionLogicListener loadCancelButtonActionLogicListener = new LoadCancelButtonActionLogicListener(controllerLogic, controllerData);
		LoadFileButtonActionLogicListener loadFileButtonActionLogicListener = new LoadFileButtonActionLogicListener(controllerLogic, controllerData);
		
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
				
				exitButtonActionLogicListener,
				newButtonActionLogicListener,
				
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
										  
				saveButtonActionLogicListener,
				saveCancelButtonActionLogicListener,
				saveFileButtonActionLogicListener,
										  
				loadButtonActionLogicListener,
				loadCancelButtonActionLogicListener,
				loadFileButtonActionLogicListener,
				
				cancelGroupButtonActionLogicListener,
				saveGroupButtonActionLogicListener,
				
				inputTypeEditSaveActionListener);
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
