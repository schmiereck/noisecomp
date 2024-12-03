/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appController;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.*;
import java.util.Map.Entry;
import java.util.prefs.Preferences;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import de.schmiereck.noiseComp.Version;
import de.schmiereck.noiseComp.file.ImportFileOperationLogic;
import de.schmiereck.noiseComp.file.LoadFileOperationLogic;
import de.schmiereck.noiseComp.file.SaveFileOperationLogic;
import de.schmiereck.noiseComp.generator.*;
import de.schmiereck.noiseComp.generator.GeneratorTypeInfoData;
import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeInfoData;
import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeInfoData.TicksPer;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.soundScheduler.SoundDataLogic;
import de.schmiereck.noiseComp.soundSource.SoundSourceData;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.soundSource.SoundSourceSchedulerLogic;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.about.AboutController;
import de.schmiereck.noiseComp.swingView.about.AboutDialogView;
import de.schmiereck.noiseComp.swingView.appModel.AppModel;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.appModel.EditModuleChangedListener;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesAddListenerInterface;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesChangePositionsListenerInterface;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesModel;
import de.schmiereck.noiseComp.swingView.appModel.InputEntryModel;
import de.schmiereck.noiseComp.swingView.appView.AppView;
import de.schmiereck.noiseComp.swingView.createFolder.CreateFolderController;
import de.schmiereck.noiseComp.swingView.inputEdit.InputEditController;
import de.schmiereck.noiseComp.swingView.inputEdit.InputEditModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectController;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectEntryModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectModel;
import de.schmiereck.noiseComp.swingView.inputSelect.RemoveInputSelectEntryListenerInterface;
import de.schmiereck.noiseComp.swingView.inputSelect.UpdateInputSelectEntryListenerInterface;
import de.schmiereck.noiseComp.swingView.modulEdit.ModuleEditController;
import de.schmiereck.noiseComp.swingView.modulEdit.ModuleEditModel;
import de.schmiereck.noiseComp.swingView.moduleInputTypeEdit.ModuleInputTypeEditController;
import de.schmiereck.noiseComp.swingView.moduleInputTypeSelect.ModuleInputTypeSelectController;
import de.schmiereck.noiseComp.swingView.moduleInputTypeSelect.ModuleInputTypeSelectEntryModel;
import de.schmiereck.noiseComp.swingView.moduleInputTypeSelect.ModuleInputTypeSelectModel;
import de.schmiereck.noiseComp.swingView.moduleInputs.ModuleInputTypesController;
import de.schmiereck.noiseComp.swingView.modulesTree.DoEditModuleListener;
import de.schmiereck.noiseComp.swingView.modulesTree.ModulesTreeController;
import de.schmiereck.noiseComp.swingView.modulesTree.ModulesTreeModel;
import de.schmiereck.noiseComp.swingView.modulesTree.ModulesTreeView;
import de.schmiereck.noiseComp.swingView.renameFolder.RenameFolderController;
import de.schmiereck.noiseComp.swingView.renameFolder.RenameFolderModel;
import de.schmiereck.noiseComp.swingView.timelineEdit.TimelineEditController;
import de.schmiereck.noiseComp.swingView.timelineEdit.TimelineEditModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineController;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntriesModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.RemoveTimelineGeneratorListenerInterface;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.TimelineEndTimePosChangedListenerInterface;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.TimelineStartTimePosChangedListenerInterface;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.InputPosEntriesModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelController;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesGeneratorsRule.TimelinesGeneratorsRuleController;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesScrollPanel.TimelinesScrollPanelController;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesScrollPanel.TimelinesScrollPanelModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesScrollPanel.TimelinesScrollPanelView;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimelinesTimeRuleController;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimelinesTimeRuleModel;
import de.schmiereck.noiseComp.swingView.utils.InputUtils;
import de.schmiereck.noiseComp.swingView.utils.OutputUtils;
import de.schmiereck.noiseComp.swingView.utils.PreferencesUtils;
import de.schmiereck.noiseComp.timeline.Timeline;
import de.schmiereck.noiseComp.timeline.TimelineManagerLogic;


/**
 * <p>
 * 	App Controller.
 * </p>
 * 
 * @author smk
 * @version <p>04.09.2010:	created, smk</p>
 */
public class AppController 
implements RemoveTimelineGeneratorListenerInterface, 
		   RemoveInputSelectEntryListenerInterface,
		   UpdateInputSelectEntryListenerInterface {
	//**********************************************************************************************
	// Constants:
	
	/**
	 * Preferences-Name: File of the last file load or save operation.
	 */
	private static final String	PREF_LOAD_FILE	= "fileActionFile";
	
	/**
	 * Preferences-Name: File of the last file import operation.
	 */
	private static final String	PREF_IMPORT_FILE	= "importFile";
	
	//**********************************************************************************************
	// Fields:

	private final SoundSourceData soundSourceData;
	private final SoundSourceLogic soundSourceLogic;

	private final SoundDataLogic soundDataLogic;

	private final SoundService soundService;

	/**
	 * App View
	 */
	private final AppView appView;
	
	/**
	 * App-Module Controller.
	 */
	private final AppModuleController appModuleController;
	
	/**
	 * App Model
	 */
	private final AppModel appModel;
	
	/**
	 * App-Model Changed Observer. 
	 */
	private final AppModelChangedObserver appModelChangedObserver;

	/**
	 * Modules Tree Controller.
	 */
	private final ModulesTreeController modulesTreeController;
	
	/**
	 * ModuleEdit Controller.
	 */
	private final ModuleEditController moduleEditController;
	
	/**
	 * ModuleInput-Types Controller.
	 */
	private final ModuleInputTypesController moduleInputTypesController;
	
	/**
	 * Rename-Folder Controller.
	 */
	private final RenameFolderController renameFolderController;
	
	/**
	 * Create-Folder Controller.
	 */
	private final CreateFolderController createFolderController;
	
	/**
	 * ModuleInput-Type Edit Controller.
	 */
	private final ModuleInputTypeEditController moduleInputTypeEditController;
	
	/**
	 * ModuleInput-Type Select Controller.
	 */
	private final ModuleInputTypeSelectController moduleInputTypeSelectController;
	
	/**
	 * Timeline-Select-Entries Model.
	 */
	private TimelineSelectEntriesModel timelineSelectEntriesModel;
	
	/**
	 * Selected-Timeline Model.
	 */
	private final SelectedTimelineModel selectedTimelineModel;
	
	/**
	 * Timelines-Time-Rule Controller
	 */
	private final TimelinesTimeRuleController timelinesTimeRuleController;
	
	/**
	 * Timelines-Generators-Rule Controller.
	 */
	private final TimelinesGeneratorsRuleController timelinesGeneratorsRuleController;
	
	/**
	 * Timelines Scroll-Panel Controller.
	 */
	private final TimelinesScrollPanelController timelinesScrollPanelController;
	
	/**
	 * Timelines Draw-Panel Controller.
	 */
	private final TimelinesDrawPanelController timelinesDrawPanelController;
	
	/**
	 * Selected-Timeline Controller.
	 */
	private final SelectedTimelineController selectedTimelineController;

	/**
	 * Timeline-Edit Controller.
	 */
	private final TimelineEditController timelineEditController;

	/**
	 * Play Controller.
	 */
	private final PlayController playController;
	
	/**
	 * Input-Select Controller.
	 */
	private final InputSelectController inputSelectController;

	/**
	 * Input-Edit Controller.
	 */
	private final InputEditController inputEditController;

	private SoundSourceSchedulerLogic soundSourceSchedulerLogic;

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public AppController(final SoundSourceLogic soundSourceLogic, final SoundDataLogic soundDataLogic,
						 final SoundService soundService) {
		//==========================================================================================
		this.soundSourceData = new SoundSourceData();

		this.soundSourceLogic = soundSourceLogic;
		this.soundDataLogic = soundDataLogic;
		this.soundService = soundService;

		//==========================================================================================
		this.appModel = new AppModel();
		
		this.appView = new AppView(this.appModel);
		
		this.appView.setTitle("NoiseComp V" + Version.version);
		this.restoreAppViewSize();
		this.appView.setVisible(true);
		
		//------------------------------------------------------------------------------------------
		this.appModelChangedObserver = new AppModelChangedObserver(this.appModel);
		
		//------------------------------------------------------------------------------------------
		final Preferences userPrefs = PreferencesUtils.getUserPreferences();
		{
			final File loadFile = this.appModel.getLoadFile();
			
			if (loadFile == null) {
				final String loadFileStr =
					PreferencesUtils.getValueString(userPrefs, 
					                                PREF_LOAD_FILE, 
					                                null);
				
				if (loadFileStr != null) {
					final File file = new File(loadFileStr);
					
					this.appModel.setLoadFile(file);
				}
			}
		}
		{
			final File importFile = this.appModel.getImportFile();
			
			if (importFile == null) {
				final String importFileStr =
					PreferencesUtils.getValueString(userPrefs, 
					                                PREF_IMPORT_FILE, 
					                                null);
				
				if (importFileStr != null) {
					final File file = new File(importFileStr);
					
					this.appModel.setImportFile(file);
				}
			}
		}
		//------------------------------------------------------------------------------------------
		// File:
		{
			final FileOpenAction action = new FileOpenAction(this);
			
			this.appView.getFileOpenMenuItem().setAction(action);
			this.appView.getFileOpenButtonView().setAction(action);
		}
		{
			final FileSaveAction action = new FileSaveAction(this);
			
			this.appView.getFileSaveMenuItem().setAction(action);
			this.appView.getFileSaveButtonView().setAction(action);
		}
		{
			final FileImportAction action = new FileImportAction(this);
			
			this.appView.getFileImportMenuItem().setAction(action);
		}
		//------------------------------------------------------------------------------------------
		// Edit:
		{
			final DoubleTimelineAction action = new DoubleTimelineAction(this);
			
			this.appView.getDoubleTimelineMenuItem().setAction(action);
		}
		//------------------------------------------------------------------------------------------
		// Help:
		{
			final HelpAboutAction action = new HelpAboutAction(this);
			
			this.appView.getHelpAboutMenuItem().setAction(action);
		}
		//------------------------------------------------------------------------------------------
		this.modulesTreeController = new ModulesTreeController(this, this.soundService);
		
		this.appView.setModulesTreeView(this.modulesTreeController.getModulesTreeView());
		
		//------------------------------------------------------------------------------------------
		this.moduleInputTypesController = new ModuleInputTypesController(this,
		                                                               this.appModelChangedObserver);
		
		this.moduleInputTypeEditController = moduleInputTypesController.getModuleInputTypeEditController();
		this.moduleInputTypeSelectController = moduleInputTypesController.getModuleInputTypeSelectController();
		
		//------------------------------------------------------------------------------------------
		this.renameFolderController = new RenameFolderController(this,
		                                                         this.appModelChangedObserver);
		
		//------------------------------------------------------------------------------------------
		this.createFolderController = new CreateFolderController(this,
		                                                         this.appModelChangedObserver);
		
		
		//------------------------------------------------------------------------------------------
		this.timelineSelectEntriesModel = new TimelineSelectEntriesModel(this.appModelChangedObserver);
		
		//------------------------------------------------------------------------------------------
		this.selectedTimelineModel = new SelectedTimelineModel();
		
		//------------------------------------------------------------------------------------------
		this.timelinesTimeRuleController = new TimelinesTimeRuleController(this,
		                                                                   this.timelineSelectEntriesModel);
		
		final TimelinesTimeRuleModel timelinesTimeRuleModel = this.timelinesTimeRuleController.getTimelinesTimeRuleModel();
		
		//------------------------------------------------------------------------------------------
		this.timelinesGeneratorsRuleController = new TimelinesGeneratorsRuleController(this.timelineSelectEntriesModel,
		                                                                               this.selectedTimelineModel);
		
		//------------------------------------------------------------------------------------------
		this.timelinesScrollPanelController = new TimelinesScrollPanelController();

		final TimelinesScrollPanelView timelinesScrollPanelView = this.timelinesScrollPanelController.getTimelinesScrollPanelView();
		
//		this.appView.setTimelineComponent(timelinesScrollPanelView.getScrollPane());
		this.appView.setTimelineComponent(timelinesScrollPanelView);
		
		this.timelinesScrollPanelController.setTimelinesRuleController(this.timelinesTimeRuleController,
		                                                               this.timelinesGeneratorsRuleController);
		
		//------------------------------------------------------------------------------------------
		this.timelinesDrawPanelController = 
			new TimelinesDrawPanelController(this,
											 this.soundSourceLogic,
			                                 //modulesTreeController.getModulesTreeModel(),
			                                 this.appModelChangedObserver,
			                                 this.timelineSelectEntriesModel,
			                                 this.selectedTimelineModel,
			                                 timelinesTimeRuleModel);

		final TimelinesDrawPanelModel timelinesDrawPanelModel = 
			this.timelinesDrawPanelController.getTimelinesDrawPanelModel();
		
		final TimelinesScrollPanelModel timelinesScrollPanelModel = 
			this.timelinesScrollPanelController.getTimelinesScrollPanelModel();
		
		// TODO Change this dynamicaly with listener/notifier.
		//timelinesDrawPanelModel.setYMinUnitIncrement(timelinesScrollPanelModel.getYSizeGenerator());
		
		this.timelinesScrollPanelController.setTimelinesDrawPanelController(this.timelinesDrawPanelController);
		
//XXX		this.timelineSelectEntriesModel.setTimelineSelectEntryModels(timelineSelectEntryModels);

		//------------------------------------------------------------------------------------------
		final TimelineManagerLogic timelineManagerLogic = this.soundSourceLogic.getTimelineManagerLogic();
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Add new Listeners:
		timelineManagerLogic.addTimelineContentChangedListener(this.timelinesDrawPanelController);

		timelineManagerLogic.addTimelineContentChangedListener(this.timelinesTimeRuleController);

		timelineManagerLogic.addTimelineContentChangedListener(this.timelinesGeneratorsRuleController);

		//------------------------------------------------------------------------------------------
		this.timelineSelectEntriesModel.getTimelineGeneratorModelsChangedNotifier().addModelPropertyChangedListener(
		 	new ModelPropertyChangedListener() {
				@Override
				public void notifyModelPropertyChanged() {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();

					final Dimension timelinesDrawPanelDimension = timelinesDrawPanelModel.getDimension();
					
					timelinesTimeRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getWidth());
					timelinesGeneratorsRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getHeight());
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);

		this.timelineSelectEntriesModel.getChangeTimelinesPositionChangedNotifier().addModelPropertyChangedListener(
		 	new ModelPropertyChangedListener() {
				@Override
				public void notifyModelPropertyChanged() {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					timelinesGeneratorsRuleController.doChangeTimelinesPosition();
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);

		this.timelinesDrawPanelController.getTimelinesDrawPanelModel().getZoomXChangedNotifier().addModelPropertyChangedListener(
		 	new ModelPropertyChangedListener() {
				@Override
				public void notifyModelPropertyChanged() {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();

					final float zoomX = timelinesDrawPanelModel.getZoomX();
					
					timelinesTimeRuleController.doChangeZoomX(zoomX);
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);

		this.timelinesDrawPanelController.getTimelinesDrawPanelModel().addYPosTimelineListChangedListener(evt -> {
			//if ("yPosTimelineList".equals(evt.getPropertyName())) {
			this.timelinesDrawPanelController.getTimelinesDrawPanelView().repaint();
			this.timelinesGeneratorsRuleController.getTimelinesGeneratorsRuleView().repaint();

			final Dimension timelinesDrawPanelDimension = timelinesDrawPanelModel.getDimension();

			// TimelinesTimeRule update.
			this.timelinesTimeRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getWidth());

			// TimelinesGeneratorsRule update.
			this.timelinesGeneratorsRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getHeight());
			//}
		});

		//------------------------------------------------------------------------------------------
		this.moduleEditController = new ModuleEditController(this,
		                                                     this.modulesTreeController.getModulesTreeModel(),
		                                                     this.appModelChangedObserver);
		
		this.appView.setModuleEditView(this.moduleEditController.getModuleEditView());
		
		//------------------------------------------------------------------------------------------
		this.inputSelectController = 
			new InputSelectController(this,
									  this.soundSourceLogic,
			                          this.timelinesDrawPanelController.getTimelinesDrawPanelModel(),
			                          this.appModelChangedObserver);
		
		this.appView.setInputSelectView(this.inputSelectController.getInputSelectView());
		
		//------------------------------------------------------------------------------------------
		this.inputEditController = 
			new InputEditController(this.timelinesDrawPanelController,
									this.soundSourceData,
									this.soundSourceLogic,
			                        this.inputSelectController.getInputSelectModel(),
			                        this.appModelChangedObserver,
			                        this.selectedTimelineModel);
		
		this.appView.setInputEditView(this.inputEditController.getInputEditView());
		
		//------------------------------------------------------------------------------------------
		this.selectedTimelineController =
				new SelectedTimelineController(this.soundSourceLogic, this.selectedTimelineModel);

		//------------------------------------------------------------------------------------------
		this.timelineEditController =
			new TimelineEditController(this,
									   this.soundSourceLogic,
									   this.soundDataLogic,
			                           this.timelinesDrawPanelController.getTimelinesDrawPanelModel(),
			                           this.appModelChangedObserver);
		
		this.appView.setTimelineEditView(this.timelineEditController.getTimelineEditView());
		
		//------------------------------------------------------------------------------------------
		this.playController = new PlayController(this.timelinesTimeRuleController,
				this.soundSourceData, this.soundSourceLogic, this.soundDataLogic, this.soundService);

		//==========================================================================================
		// Exit:
		{
			final ExitAction action = new ExitAction(this);
			
			this.appView.getExitMenuItem().setAction(action);
			this.appView.getExitButtonView().setAction(action);
			this.appView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		//------------------------------------------------------------------------------------------
		this.modulesTreeController.getModulesTreeView().addDoEditModuleListener(
		 	new DoEditModuleListener() {
				@Override
				public void notifyEditModule(final ModuleGeneratorTypeInfoData moduleGeneratorTypeData) {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					selectEditModule(moduleGeneratorTypeData);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.appModuleController = new AppModuleController(this.appModelChangedObserver);
		
		//------------------------------------------------------------------------------------------
		// Modules-Tree:
		//------------------------------------------------------------------------------------------
		// Modules-Tree Edited Model changed -> updated ModuleEdit Model:
		
		this.modulesTreeController.getModulesTreeModel().addEditModuleChangedListener(
		 	new EditModuleChangedListener() {
				@Override
				public void notifyEditModuleChanged(final ModulesTreeModel modulesTreeModel,
													final TreePath selectionTreePath) {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final ModuleGeneratorTypeInfoData moduleGeneratorTypeData = modulesTreeModel.getEditedModuleGeneratorTypeInfoData();
					
					moduleEditController.doEditModuleChanged(moduleGeneratorTypeData);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Modules-Tree Edited Model changed: Update ModuleInput-Type Select Model:
		
		this.modulesTreeController.getModulesTreeModel().addEditModuleChangedListener(
		 	new EditModuleChangedListener() {
				@Override
				public void notifyEditModuleChanged(final ModulesTreeModel modulesTreeModel,
													final TreePath selectionTreePath) {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					//if (Objects.nonNull(soundSourceSchedulerLogic)) {
					//	soundSourceSchedulerLogic.stopThread();
					//}

					final ModuleInputTypeSelectController moduleInputTypeSelectController = moduleInputTypesController.getModuleInputTypeSelectController();

					final ModuleGeneratorTypeInfoData moduleGeneratorTypeData = modulesTreeModel.getEditedModuleGeneratorTypeInfoData();
					
					moduleInputTypeSelectController.doEditModuleChanged(moduleGeneratorTypeData);
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// ModuleEdit:
		//------------------------------------------------------------------------------------------
		// ModuleEdit: Edit-Input-Types Button: Update Modules-Tree-View:
		
		this.moduleEditController.getModuleEditView().getEditInputTypesButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					appModuleController.doEditInputTypes(moduleInputTypesController);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// ModuleEdit Model changed: Update Modules-Tree-View:
		
		this.moduleEditController.getModuleEditModel().getModulEditModelChangedNotifier().addModelPropertyChangedListener(
		 	new ModelPropertyChangedListener() {
				@Override
				public void notifyModelPropertyChanged() {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final ModuleGeneratorTypeInfoData editedModuleGeneratorTypeData =
						modulesTreeController.getModulesTreeModel().getEditedModuleGeneratorTypeInfoData();
					
					modulesTreeController.updateEditedModuleTreeEntry(editedModuleGeneratorTypeData);
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// ModuleInput-Type:
		//------------------------------------------------------------------------------------------
		// ModuleInput-Type Selected Input changed: Update ModuleInput-Type-Edit Model:
		
		this.moduleInputTypeSelectController.getInputTypeSelectModel().getSelectedRowNoChangedNotifier().addModelPropertyChangedListener(
		 	new ModelPropertyChangedListener() {
				@Override
				public void notifyModelPropertyChanged() {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final ModuleInputTypeSelectModel moduleInputTypeSelectModel = moduleInputTypeSelectController.getInputTypeSelectModel();

					final ModuleInputTypeSelectEntryModel selectEntryModel = moduleInputTypeSelectModel.getSelectedRow();

					final InputTypeData inputTypeData;
					
					if (selectEntryModel != null) {
						inputTypeData = selectEntryModel.getInputTypeData();
					} else {
						inputTypeData = null;
					}
					
					moduleInputTypeEditController.updateEditedInputType(inputTypeData);
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// ModuleInput-Type Edit: Create-New Button: Update ModuleInput-Type Select Model:
		
		this.moduleInputTypeEditController.getModuleInputTypeEditView().getCreateNewButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					moduleInputTypeSelectController.doCreateNew();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			 	}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// ModuleInput-Type Edit Update-Button: Update ModuleInput-Type Data and ModuleInput-Type Edit-Model:
		// TODO Move to controller.
		this.moduleInputTypeEditController.getModuleInputTypeEditView().getUpdateButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					moduleInputTypeEditController.doUpdateModuleInputType(modulesTreeController,
					                                                      moduleInputTypeSelectController,
					                                                      appModelChangedObserver);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// ModuleInput-Type Edit Remove-Button: Update ModuleInput-Type Data and ModuleInput-Type Select-Model:
		// TODO Move to controller.
		this.moduleInputTypeEditController.getModuleInputTypeEditView().getRemoveButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final ModulesTreeModel modulesTreeModel = modulesTreeController.getModulesTreeModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final ModuleGeneratorTypeInfoData editedModuleGeneratorTypeData = modulesTreeModel.getEditedModuleGeneratorTypeInfoData();
//					ModuleInputTypeSelectModel selectModel = moduleInputTypeSelectController.getInputTypeSelectModel();
					
//					InputTypeData inputTypeData = moduleInputTypeSelectController.getSelectedModuleInputType();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					moduleInputTypeSelectController.doRemoveSelectedEntry(editedModuleGeneratorTypeData);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// ModuleInput-Type Edit Model Value changed: Update Input-Select:
		
		this.moduleInputTypeEditController.getModuleInputTypeEditModel().getInputTypeIDChangedNotifier().addModelPropertyChangedListener(
		 	new ModelPropertyChangedListener() {
				@Override
				public void notifyModelPropertyChanged() {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final ModuleInputTypeSelectModel selectModel = moduleInputTypeSelectController.getInputTypeSelectModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Input-Select:
					moduleInputTypeSelectController.doInputTypeUpdated(selectModel);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Timeline-Select:
		//------------------------------------------------------------------------------------------
		{
			InputEntriesModel inputEntriesModel = this.selectedTimelineModel.getInputEntriesModel();
			
			inputEntriesModel.getInputEntriesAddNotifier().addInputEntriesAddListeners(
			 	new InputEntriesAddListenerInterface() {
					@Override
					public void notifyAddInputEntry(final int entryPos,
													final InputEntryModel inputEntryModel) {
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						final InputPosEntriesModel inputPosEntriesModel = selectedTimelineModel.getInputPosEntriesModel();
						
						inputPosEntriesModel.addInputPosEntryInGroup(inputEntryModel);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
			);
		}
		//------------------------------------------------------------------------------------------
		// ChangePositions Input-Select:
		{
			InputEntriesModel inputEntriesModel = selectedTimelineModel.getInputEntriesModel();
			
			inputEntriesModel.getInputEntriesChangePositionsNotifier().addInputEntriesChangePositionsListeners(
			 	new InputEntriesChangePositionsListenerInterface() {
					@Override
					public void notifyChangePositions(final InputEntryModel selectedInputEntryModel, 
					                                  final InputEntryModel targetInputEntryModel) {
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						//SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();

						final TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						final TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
						
						final SelectedTimelineModel selectedTimelineModel = timelinesDrawPanelModel.getSelectedTimelineModel();
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						final TimelineSelectEntryModel selectedTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();
						
						final Timeline selectedTimeline = selectedTimelineSelectEntryModel.getTimeline();

						final InputData selectedInputData = selectedInputEntryModel.getInputData();
						final InputData targetInputData = targetInputEntryModel.getInputData();
						
						timelineManagerLogic.changeInputPositions(selectedTimeline,
						                                          selectedInputData,
						                                          targetInputData);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
			);
		}
		//------------------------------------------------------------------------------------------
		// Timeline-Edit:
		//------------------------------------------------------------------------------------------
		// Timeline-Edit Remove-Timeline-Button: Update ModuleData and Timeline-Select-Model:
		
		this.timelineEditController.getTimelineEditView().getRemoveButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final TimelinesDrawPanelModel timelinesDrawPanelModel =
						timelinesDrawPanelController.getTimelinesDrawPanelModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final TimelineSelectEntryModel selectedTimelineSelectEntryModel =
						selectedTimelineModel.getSelectedTimelineSelectEntryModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Timeline-Select-Model:

					final int timelinePos =
							timelineSelectEntriesModel.removeTimelineSelectEntryModel(soundSourceData,
									timelinesDrawPanelModel, selectedTimelineSelectEntryModel);

					timelinesDrawPanelController.recalcYPosTimelineList(timelinePos);

					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);	
		//------------------------------------------------------------------------------------------
		// Timeline-Edit Update-Button: Timeline-Select-Model:
		
		this.timelineEditController.getTimelineEditView().getUpdateButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					doUpdateTimeline();
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Timeline-Edit Create-New-Timeline-Button: Timeline-Select-Model:
		
		this.timelineEditController.getTimelineEditView().getCreateNewButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					doCreateNewTimeline();

					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);	
		//------------------------------------------------------------------------------------------
		// Input-Select:
		//------------------------------------------------------------------------------------------
		// Input-Select (Table in InputSelectModel / InputSelectView): Selected Input changed: Update Input-Edit:
		
		this.inputSelectController.getInputSelectModel().getSelectedRowNoChangedNotifier().addModelPropertyChangedListener(
		 	new ModelPropertyChangedListener() {
				@Override
				public void notifyModelPropertyChanged() {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final InputSelectModel inputSelectModel = inputSelectController.getInputSelectModel();

					final InputSelectEntryModel inputSelectEntryModel = inputSelectModel.getSelectedRow();

					final boolean editInput;
					final InputData inputData;
					
					if (inputSelectEntryModel != null) {
						inputData = inputSelectEntryModel.getInputData();
						editInput = true;
					} else {
						inputData = null;
						editInput = false;
					}

					final ModulesTreeModel modulesTreeModel = modulesTreeController.getModulesTreeModel();

					final ModuleGeneratorTypeInfoData editedModuleGeneratorTypeData = modulesTreeModel.getEditedModuleGeneratorTypeInfoData();

					final Timeline selectedTimeline = timelinesDrawPanelController.getSelectedTimeline();


					final Integer selectedRowNo = inputSelectModel.getSelectedRowNo();

					// TODO YYY selectedTimelineController.setSelectedInputData(inputData);
					// timelinesDrawPanelController.setSelectedInputData(inputData);
					//selectedTimelineModel.setSelectedInputEntry(selectedInputEntry);
					selectedTimelineController.setSelectedInputEntryByRowNo(selectedRowNo, true);

					inputEditController.updateEditedInput(editedModuleGeneratorTypeData,
					                                      selectedTimeline,
					                                      inputData,
					                                      editInput);
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Input-Select (SelectedTimelineModel in Timeline-View): Selected Input changed: Update Input-Edit:

		this.selectedTimelineModel.getSelectedInputEntryChangedViewNotifier().addModelPropertyChangedListener(
				new ModelPropertyChangedListener() {
					@Override
					public void notifyModelPropertyChanged() {
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						final InputEntryModel selectedInputEntry = selectedTimelineModel.getSelectedInputEntry();

						final InputData inputData;

						if (Objects.nonNull(selectedInputEntry)) {
							inputData = selectedInputEntry.getInputData();
						} else {
							inputData = null;
						}

						// Highlight selected Input in Input-Select-Table:
						inputSelectController.changeSelectedInputData(inputData, false);
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
		);
		this.selectedTimelineModel.getSelectedInputEntryChangedNotifier().addModelPropertyChangedListener(
		 	new ModelPropertyChangedListener() {
				@Override
				public void notifyModelPropertyChanged() {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final InputEntryModel selectedInputEntry = selectedTimelineModel.getSelectedInputEntry();

					final boolean editInput;
					final InputData inputData;

					if (Objects.nonNull(selectedInputEntry)) {
						inputData = selectedInputEntry.getInputData();
						editInput = true;
					} else {
						inputData = null;
						editInput = false;
					}

					final ModulesTreeModel modulesTreeModel = modulesTreeController.getModulesTreeModel();

					final ModuleGeneratorTypeInfoData editedModuleGeneratorTypeData = modulesTreeModel.getEditedModuleGeneratorTypeInfoData();

					final Timeline selectedTimeline = timelinesDrawPanelController.getSelectedTimeline();

					// Highlight selected Input in Input-Select-Table:
					//inputSelectController.changeSelectedInputData(inputData);

					// Update selected Input Input-Edit-Form:
					inputEditController.updateEditedInput(editedModuleGeneratorTypeData,
					                                      selectedTimeline,
					                                      inputData,
					                                      editInput);
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Input-Edit:
		//------------------------------------------------------------------------------------------
		// Input-Edit Remove-Input-Button: Update Input-Select-Model and Generator-Input-Data:		
		this.inputEditController.getInputEditView().getRemoveButton().addActionListener (
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					inputSelectController.doRemoveSelectedInputEntry();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			 	}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Input-Edit Create-New-Input-Button: Update Input-Select-Model // and Generator-Input-Data:
		
		this.inputEditController.getInputEditView().getCreateNewButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					inputEditController.doCreateNewInput(soundSourceData, inputSelectController);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			 	}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// ModuleEdit:
		//------------------------------------------------------------------------------------------
		// Update-Button: Update Module
		
		this.moduleEditController.getModuleEditView().getUpdateButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					appModuleController.doUpdateEditModule(moduleEditController,
					                                       modulesTreeController,
														   soundService);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Models Changed:
		//------------------------------------------------------------------------------------------
		final ModuleEditModel modulEditModel = this.moduleEditController.getModuleEditModel();
		
		//------------------------------------------------------------------------------------------
		modulEditModel.getZoomXChangedNotifier().addModelPropertyChangedListener(
		 	new ModelPropertyChangedListener() {
				@Override
				public void notifyModelPropertyChanged() {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();

					final float zoomX = modulEditModel.getZoomX();
					
					timelinesDrawPanelModel.setZoomX(zoomX);
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		modulEditModel.getTicksCountChangedNotifier().addModelPropertyChangedListener(
		 	new ModelPropertyChangedListener() {
				@Override
				public void notifyModelPropertyChanged() {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final JTextField ticksTextField = appView.getTicksTextField();

					final Float ticksCount = modulEditModel.getTicksCount();
					
					ticksTextField.setText(OutputUtils.makeFloatEditText(ticksCount));
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		modulEditModel.getTicksPerChangedNotifier().addModelPropertyChangedListener(
		 	new ModelPropertyChangedListener() {
				@Override
				public void notifyModelPropertyChanged() {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final JRadioButton ticksSecondsButton = appView.getTicksSecondsButton();
					final JRadioButton ticksMilliecondsButton = appView.getTicksMilliecondsButton();
					final JRadioButton ticksBpmButton = appView.getTicksBpmButton();

					final TicksPer ticksPer = modulEditModel.getTicksPer();
					
					switch (ticksPer) {
						case Seconds: {
							ticksSecondsButton.setSelected(true);
							ticksMilliecondsButton.setSelected(false);
							ticksBpmButton.setSelected(false);
							break;
						}
						case Milliseconds: {
							ticksSecondsButton.setSelected(false);
							ticksMilliecondsButton.setSelected(true);
							ticksBpmButton.setSelected(false);
							break;
						}
						case BPM: {
							ticksSecondsButton.setSelected(false);
							ticksMilliecondsButton.setSelected(false);
							ticksBpmButton.setSelected(true);
							break;
						}
						default: {
							throw new RuntimeException("Unexpected TicksPer \"" + ticksPer + "\".");
						}
					}
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		{
		 	ModelPropertyChangedListener ticksChangedListener = new ModelPropertyChangedListener() {
				@Override
				public void notifyModelPropertyChanged() {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final TimelinesTimeRuleModel timelinesTimeRuleModel = timelinesTimeRuleController.getTimelinesTimeRuleModel();

					final TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
					
					// Update TimelinesTimeRule
					final TicksPer ticksPer = modulEditModel.getTicksPer();
					final Float ticksCount = modulEditModel.getTicksCount();
					
					timelinesTimeRuleModel.notifyTicksChangedNotifier(ticksPer, ticksCount);
					
					timelinesDrawPanelModel.notifyTicksChangedNotifier(ticksPer, ticksCount);
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	};
		 	modulEditModel.getTicksPerChangedNotifier().addModelPropertyChangedListener(ticksChangedListener);
		 	modulEditModel.getTicksCountChangedNotifier().addModelPropertyChangedListener(ticksChangedListener);
		}
		//------------------------------------------------------------------------------------------
//		ModuleEditModel modulEditModel = this.modulEditController.getModulEditModel();
		
	 	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//		modulEditModel.getModuleameChangedNotifier().addModelPropertyChangedListener
//		(
//		 	appModelChangedObserver
//		);
//		modulEditModel.getModulesMainChangedNotifier().addModelPropertyChangedListener
//		(
//		 	appModelChangedObserver
//		);
		//------------------------------------------------------------------------------------------
//		final ModuleInputTypeEditModel moduleInputTypeEditModel = moduleInputTypeEditController.getModuleInputTypeEditModel();
		
	 	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//		moduleInputTypeEditModel.getInputTypeDataChangedNotifier().addModelPropertyChangedListener
//		(
//		 	appModelChangedObserver
//		);
//		moduleInputTypeEditModel.getInputTypeDefaultValueChangedNotifier().addModelPropertyChangedListener
//		(
//		 	appModelChangedObserver
//		);
//		moduleInputTypeEditModel.getInputTypeDescriptionChangedNotifier().addModelPropertyChangedListener
//		(
//		 	appModelChangedObserver
//		);
//		moduleInputTypeEditModel.getInputTypeIDChangedNotifier().addModelPropertyChangedListener
//		(
//		 	appModelChangedObserver
//		);
//		moduleInputTypeEditModel.getInputTypeNameChangedNotifier().addModelPropertyChangedListener
//		(
//		 	appModelChangedObserver
//		);
		//------------------------------------------------------------------------------------------
//		TimelineEditModel timelineEditModel = timelineEditController.getTimelineEditModel();
		
	 	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//		final TimelinesDrawPanelModel timelinesDrawPanelModel = this.timelinesDrawPanelController.getTimelinesDrawPanelModel();
		
//		TimelineEditModelChangedObserver timelineEditModelChangedObserver = new TimelineEditModelChangedObserver(timelineEditModel,
//		                                                                                                         timelinesDrawPanelModel);
//	 	
//		timelineEditModel.getGeneratorTypeDataChangedNotifier().addModelPropertyChangedListener
//	 	(
//	 	 	timelineEditModelChangedObserver
//	 	);
//		timelineEditModel.getGeneratorNameChangedNotifier().addModelPropertyChangedListener
//	 	(
//	 	 	timelineEditModelChangedObserver
//	 	);
//		timelineEditModel.getGeneratorStartTimePosChangedNotifier().addModelPropertyChangedListener
//	 	(
//	 	 	timelineEditModelChangedObserver
//	 	);
//	 	timelineEditModel.getGeneratorEndTimePosChangedNotifier().addModelPropertyChangedListener
//	 	(
//	 	 	timelineEditModelChangedObserver
//	 	);
	 	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	 	timelineEditModel.getGeneratorStartTimePosChangedNotifier().addModelPropertyChangedListener
//	 	(
//	 	 	timelinesDrawPanelController
//	 	);
//	 	timelineEditModel.getGeneratorEndTimePosChangedNotifier().addModelPropertyChangedListener
//	 	(
//	 	 	timelinesDrawPanelController
//	 	);

	 	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	 	timelineEditModel.getGeneratorTypeDataChangedNotifier().addModelPropertyChangedListener
//	 	(
//	 	 	appModelChangedObserver
//	 	);
//	 	timelineEditModel.getGeneratorNameChangedNotifier().addModelPropertyChangedListener
//	 	(
//	 	 	appModelChangedObserver
//	 	);
//	 	timelineEditModel.getGeneratorStartTimePosChangedNotifier().addModelPropertyChangedListener
//	 	(
//	 	 	appModelChangedObserver
//	 	);
//	 	timelineEditModel.getGeneratorEndTimePosChangedNotifier().addModelPropertyChangedListener
//	 	(
//	 	 	appModelChangedObserver
//	 	);
	 	
		//------------------------------------------------------------------------------------------
		// General TimelinesDrawPanelModel Listener: Called if something of Model changed:
	 	ModelPropertyChangedListener modelPropertyChangedListener = new ModelPropertyChangedListener() {
			@Override
			public void notifyModelPropertyChanged() {
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Update TimelinesDrawPanel:
				final TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
				
				timelinesDrawPanelModel.getTimelineGeneratorModelChangedListener().notifyModelPropertyChanged();

				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			}
	 	};
		//------------------------------------------------------------------------------------------
		final InputEditModel inputEditModel = this.inputEditController.getInputEditModel();
	 	
		//------------------------------------------------------------------------------------------
		// Input-Edit-Model InputTypeData changed: Update TimelinesDrawPanel:
		
		inputEditModel.getInputTypeDataChangedNotifier().addModelPropertyChangedListener(modelPropertyChangedListener);
		
		//------------------------------------------------------------------------------------------
		// Input-Edit-Model InputTypeData changed: Update TimelinesDrawPanel:
		
		inputEditModel.getInputTypeDataChangedNotifier().addModelPropertyChangedListener(modelPropertyChangedListener);

		//------------------------------------------------------------------------------------------
		// Input-Edit-Model Value changed: Update Input-Select and TimelinesDrawPanel:
		
	 	inputEditModel.getValueChangedNotifier().addModelPropertyChangedListener(
		 	new ModelPropertyChangedListener() {
				@Override
				public void notifyModelPropertyChanged() {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final InputSelectModel inputSelectModel = inputSelectController.getInputSelectModel();

					final InputEntriesModel inputEntriesModel = selectedTimelineModel.getInputEntriesModel();

					final InputSelectEntryModel inputSelectEntryModel = inputSelectModel.getSelectedRow();

					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Input-Select:

					final Integer selectedRowNo = inputSelectModel.getSelectedRowNo();

					final InputEntryModel inputEntryModel;
					
					if (selectedRowNo != null) {
						inputEntryModel = inputEntriesModel.searchInputEntry(selectedRowNo);
					} else {
						inputEntryModel = null;
					}

					final InputData inputData;
					
					if (inputSelectEntryModel != null) {
						inputData = inputSelectEntryModel.getInputData();
					} else {
						inputData = null;
					}
					
					inputSelectController.doInputUpdated(inputEntryModel,
					                                     inputData);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		
	 	inputEditModel.getValueChangedNotifier().addModelPropertyChangedListener(modelPropertyChangedListener);
		
		//------------------------------------------------------------------------------------------
		// Input-Edit-Model InputGenerator changed: Update TimelinesDrawPanel:
		
	 	inputEditModel.getInputTimelineChangedNotifier().addModelPropertyChangedListener(modelPropertyChangedListener);

		//------------------------------------------------------------------------------------------
		// Input-Edit-Model ModuleInputTypeData changed: Update TimelinesDrawPanel:
		
	 	//inputEditModel.getModuleInputTypeDataChangedNotifier().addModelPropertyChangedListener(modelPropertyChangedListener);

//	    //------------------------------------------------------------------------------------------
//		// Input-Edit-Model Value changed: Update Timeline-Select:
//		
//		inputEditController.getInputEditModel().getValueChangedNotifier().addModelPropertyChangedListener
//	    (
//	    	timelinesDrawPanelController.getTimelineGeneratorModelChangedListener()
//	    );
	    //------------------------------------------------------------------------------------------
//	 	TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
	 	
	    //------------------------------------------------------------------------------------------
	 	timelineSelectEntriesModel.getRemoveTimelineGeneratorNotifier().addRemoveTimelineGeneratorListeners(this);
	    //------------------------------------------------------------------------------------------
	 	timelineSelectEntriesModel.getRemoveTimelineGeneratorNotifier().addRemoveTimelineGeneratorListeners(timelinesTimeRuleController);
	    //------------------------------------------------------------------------------------------
		// TimelinesGeneratorsRule update.
	 	timelineSelectEntriesModel.getRemoveTimelineGeneratorNotifier().addRemoveTimelineGeneratorListeners(timelinesGeneratorsRuleController);
//	 	selectedTimelineModel.getSelectedTimelineChangedNotifier().addModelPropertyChangedListener
//	 	(
//	 	 	new ModelPropertyChangedListener()
//	 	 	{
//				@Override
//				public void notifyModelPropertyChanged()
//				{
//					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//					TimelineSelectEntryModel selectedTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();
//					
//					timelinesGeneratorsRuleController.notifySelectedTimelineChanged(selectedTimelineSelectEntryModel);
//					
//					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//				}
//	 	 	}
//	 	);
	    //------------------------------------------------------------------------------------------
	 	timelinesDrawPanelModel.getTimelineStartTimePosChangedListeners().add(
	 	 	new TimelineStartTimePosChangedListenerInterface() {
				@Override
				public void notifyTimelineStartTimePosChangedListener(final Timeline timeline, final Float startTimePos) {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();

					final TimelineEditModel timelineEditModel = timelineEditController.getTimelineEditModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					timelineManagerLogic.updateStartTimePos(soundSourceData, timeline, startTimePos);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					timelineEditModel.setGeneratorStartTimePos(startTimePos);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					appModelChangedObserver.notifyAppModelChanged();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
	 	 	}
	 	);
	    //------------------------------------------------------------------------------------------
	 	timelinesDrawPanelModel.getTimelineEndTimePosChangedListeners().add(
	 	 	new TimelineEndTimePosChangedListenerInterface() {
				@Override
				public void notifyTimelineEndTimePosChangedListener(Timeline timeline, Float endTimePos) {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();

					final TimelineEditModel timelineEditModel = timelineEditController.getTimelineEditModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					if (timeline != null) {
						timelineManagerLogic.updateEndTimePos(soundSourceData, timeline, endTimePos);
					}
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					timelineEditModel.setGeneratorEndTimePos(endTimePos);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					appModelChangedObserver.notifyAppModelChanged();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
	 	 	}
	 	);
	    //------------------------------------------------------------------------------------------
//	 	TimelinesTimeRuleModel timelinesTimeRuleModel = this.timelinesTimeRuleController.getTimelinesTimeRuleModel();
//	 	
//	 	TimeMarkerSelectEntryModel playTimeMarkerSelectEntryModel = timelinesTimeRuleModel.getPlayTimeMarkerSelectEntryModel();
//	 	
//	 	playTimeMarkerSelectEntryModel.getTimeMarkerChangedNotifier()addModelPropertyChangedListener
//	 	(
//	 	 	new ModelPropertyChangedListener()
//			{
//				@Override
//				public void notifyModelPropertyChanged()
//				{
//					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//					timelinesDTimeRuleController.getTimelinesTimeRuleModel();
//					
//					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//					TimeMarkerSelectEntryModel playTimeMarkerSelectEntryModel = timelinesTimeRuleModel.getPlayTimeMarkerSelectEntryModel();
//					
//					float playbackTime = timelineSelectEntriesModel.getPlaybackTime();
//					
//					playTimeMarkerSelectEntryModel.setTimeMarker(playbackTime);
//					
//					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//				}
//			}
//	 	);
	    //------------------------------------------------------------------------------------------
		final InputSelectModel inputSelectModel = this.inputSelectController.getInputSelectModel();
	 	
	 	inputSelectModel.getRemoveInputSelectEntryNotifier().addRemoveInputSelectEntryListeners(this);
	 	inputSelectModel.getUpdateInputSelectEntryNotifier().addUpdateInputSelectEntryListeners(this);
	    //------------------------------------------------------------------------------------------
	 	final RenameFolderModel renameFolderModel = this.renameFolderController.getRenameFolderModel();
	 	
	 	renameFolderModel.getFolderNameChangedNotifier().addModelPropertyChangedListener(
	 	 	new ModelPropertyChangedListener() {
				@Override
				public void notifyModelPropertyChanged() {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final ModulesTreeModel modulesTreeModel = modulesTreeController.getModulesTreeModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final String folderName = renameFolderModel.getFolderName();

					final DefaultMutableTreeNode editedFolderTreeNode = appModel.getEditedModuleTreeNode();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Service:
					
//					RenameFolderModel renameFolderModel = renameFolderController.getRenameFolderModel();

					final String folderPath = modulesTreeModel.makeFolderPath(editedFolderTreeNode);

					soundService.renameFolder(folderPath, folderName);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update View-Model:
					
					// XXX rename Folder
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Views:
					
					modulesTreeController.updateEditedFolderTreeNode(editedFolderTreeNode, folderName);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
	 	 	}
	 	);
	    //------------------------------------------------------------------------------------------
	 	// Action-Listeners:
	    //------------------------------------------------------------------------------------------
		// http://download.oracle.com/javase/tutorial/uiswing/misc/action.html
		this.appView.getPlayButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					playController.doPlaySound(soundSourceData);
				}
		 	}
		);
	    //------------------------------------------------------------------------------------------
		this.appView.getPauseButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					playController.doPauseSound();
				}
		 	}
		);
	    //------------------------------------------------------------------------------------------
		this.appView.getStopButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					playController.doStopSound();
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.appView.getLoopButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					final JCheckBox loopButton = appView.getLoopButton();

					final boolean looped = loopButton.getModel().isSelected();
					
					playController.doLoopSound(looped);
				}
		 	}
		);
	    //------------------------------------------------------------------------------------------
		this.appView.getZoomInButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					moduleEditController.doTimelinesZoomIn();
				}
		 	}
		);
	    //------------------------------------------------------------------------------------------
		this.appView.getZoomOutButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					moduleEditController.doTimelinesZoomOut();
				}
		 	}
		);
	    //------------------------------------------------------------------------------------------
		// Exit:
		this.appView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.appView.addWindowListener(new WindowAdapter()  {
            /* (non-Javadoc)
             * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
             */
            public void windowClosing(final WindowEvent ex) {
            	doExit();
            }
        });
	    //------------------------------------------------------------------------------------------
		this.appView.getTicksTextField().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					final JTextField ticksTextField = appView.getTicksTextField();

					final String text = ticksTextField.getText();

					final Float ticksCount = InputUtils.makeFloatValue(text);
					
					modulEditModel.setTicksCount(ticksCount);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.appView.getTicksSecondsButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					modulEditModel.setTicksPer(TicksPer.Seconds);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.appView.getTicksMilliecondsButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					modulEditModel.setTicksPer(TicksPer.Milliseconds);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.appView.getTicksBpmButton().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					modulEditModel.setTicksPer(TicksPer.BPM);
				}
		 	}
		);
		
	    //------------------------------------------------------------------------------------------
		// Init Generator-Types:
	    //------------------------------------------------------------------------------------------
		final List<GeneratorTypeInfoData> generatorTypes = this.soundService.retrieveGeneratorTypes();
		
		this.modulesTreeController.addGeneratorTypes(generatorTypes);
		
	    //------------------------------------------------------------------------------------------
//		this.timelinesTimeRuleController.doChangeZoomX(this.timelinesDrawPanelController.getTimelinesDrawPanelModel().getZoomX());
		modulEditModel.setZoomX(this.timelinesDrawPanelController.getTimelinesDrawPanelModel().getZoomX());
		
	    //------------------------------------------------------------------------------------------
		modulEditModel.setTicksCount(1.0F);
		
	    //------------------------------------------------------------------------------------------
		modulEditModel.setTicksPer(TicksPer.Seconds);
		
		//==========================================================================================
	}

	/**
	 * Do Create New Timeline.
	 */
	public void doCreateNewTimeline() {
		//final TimelineEditModel timelineEditModel = this.timelineEditController.getTimelineEditModel();
		//timelineEditModel.setGeneratorTypeInfoData(null);

		this.timelinesDrawPanelController.doCreateNewTimeline(this.timelinesTimeRuleController,
		                                              this.timelinesGeneratorsRuleController,
		                                              this.appModelChangedObserver);
	}

	/**
	 * @param mainModuleGeneratorTypeData
	 * 			is the edited module
	 */
	public void selectEditModule(final ModuleGeneratorTypeInfoData mainModuleGeneratorTypeData) {
		//==========================================================================================
		final TimelinesDrawPanelModel timelinesDrawPanelModel = this.timelinesDrawPanelController.getTimelinesDrawPanelModel();
		
		final SelectedTimelineModel selectedTimelineModel = timelinesDrawPanelModel.getSelectedTimelineModel();
		
		//==========================================================================================
		selectedTimelineModel.setSelectedTimelineSelectEntryModel(null);

		this.timelinesDrawPanelController.clearTimelineGenerators();
		
		//------------------------------------------------------------------------------------------
		this.modulesTreeController.getModulesTreeModel().setEditedModuleGeneratorTypeInfoData(mainModuleGeneratorTypeData);
		
		if (Objects.nonNull(mainModuleGeneratorTypeData)) {
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			final TimelineManagerLogic timelineManagerLogic = this.soundSourceLogic.getTimelineManagerLogic();

			if (Objects.nonNull(timelineManagerLogic)) {
				timelineManagerLogic.clearTimelines();
			}

			this.soundSourceLogic.setMainModuleGeneratorTypeData(this.soundSourceData, mainModuleGeneratorTypeData);

			final List<Timeline> timelineList = this.soundSourceData.getTimelineList();

			//final TimelineManagerLogic timelineManagerLogic = this.soundSourceLogic.getTimelineManagerLogic();

			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Add new Listeners:
			//timelineManagerLogic.addTimelineContentChangedListener(this.timelinesDrawPanelController);

			//timelineManagerLogic.addTimelineContentChangedListener(this.timelinesTimeRuleController);
			
			//timelineManagerLogic.addTimelineContentChangedListener(this.timelinesGeneratorsRuleController);

			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			int timelinePos = 0;
			int yPosGenerator = 0;

			for (final Timeline timeline : timelineList) {
				final Generator generator = timeline.getGenerator();

				final TimelineSelectEntryModel timelineSelectEntryModel =
					new TimelineSelectEntryModel(timeline,
//					                             timelinePos,
					                             generator.getName(),
					                             generator.getStartTimePos(), generator.getEndTimePos(),
							                     yPosGenerator, TimelinesDrawPanelModel.Y_SIZE_TIMELINE);
				
				this.timelinesDrawPanelController.addTimelineSelectEntryModel(timelinePos,
				                                                              timelineSelectEntryModel);
				
				timelinePos++;
				yPosGenerator += timelineSelectEntryModel.getYSizeGenerator();
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			this.timelinesTimeRuleController.resetTime();
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		//==========================================================================================
	}

	/**
	 * @param generatorName
	 * 			is the Name of Generator.
	 * @return
	 * 			the genaror with given name.
	 */
	public Generator retrieveGeneratorOfEditedModule(final String generatorName)
	{
		//==========================================================================================
		final ModuleGeneratorTypeInfoData moduleGeneratorTypeData =
			modulesTreeController.getModulesTreeModel().getEditedModuleGeneratorTypeInfoData();

		//==========================================================================================
		return moduleGeneratorTypeData.searchGenerator(generatorName);
	}

	/**
	 * @return 
	 * 			returns the {@link #appView}.
	 */
	public AppView getAppView() {
		return this.appView;
	}

	/**
	 * @return
	 * 			the generator types.
	 */
	public List<GeneratorTypeInfoData> retrieveGeneratorTypesForSelect() {
		//==========================================================================================
		return this.soundService.retrieveGeneratorTypes();
	}
	
	/**
	 * @return
	 * 			the edited ModuleGenerator-Type Data.
	 */
	public ModuleGeneratorTypeInfoData getEditedModuleGeneratorTypeInfoData() {
		//==========================================================================================
		final ModulesTreeModel modulesTreeModel = this.modulesTreeController.getModulesTreeModel();
		
		//==========================================================================================
		return modulesTreeModel.getEditedModuleGeneratorTypeInfoData();
	}

	/**
	 * Exit Applikation.
	 */
	public void doExit() {
		//==========================================================================================
		final boolean exitApp;

		final boolean isModelChanged = this.appModel.getIsModelChanged();
		
		if (isModelChanged) {
			final int option = JOptionPane.showConfirmDialog(this.appView, "File has been modified.\nSave Changes?");
	
			if (option == JOptionPane.YES_OPTION) {
				final boolean fileSaved = this.doFileSave();
				
				if (fileSaved) {
					exitApp = true;
				} else {
					exitApp = false;
				}
			} else {
				if (option == JOptionPane.CANCEL_OPTION) {
					exitApp = false;
				} else {
					exitApp = true;
				}
			}
		} else {
			exitApp = true;
		}
		
		//------------------------------------------------------------------------------------------
		if (exitApp) {
			this.exitApplication();
		}
		//==========================================================================================
	}

	private void exitApplication() {
		this.playController.doStopSound();
		if (Objects.nonNull(this.soundSourceSchedulerLogic)) {
			//soundSchedulerLogic.stopPlayback();
			//soundSchedulerLogic.stopThread();
			this.soundSourceSchedulerLogic.stopThread();
		}
		this.storeAppViewSize();
		this.appView.dispose();
		//System.exit(0);
	}

	/**
	 * Store the app view size to registry.
	 */
	private void storeAppViewSize() {
		//==========================================================================================
		final Preferences userPrefs = PreferencesUtils.getUserPreferences();

		//==========================================================================================
		final Dimension size = this.appView.getSize();
		
		PreferencesUtils.setValueDouble(userPrefs, "appWith", size.getWidth());
		PreferencesUtils.setValueDouble(userPrefs, "appHeight", size.getHeight());

		//------------------------------------------------------------------------------------------
		final Point location = this.appView.getLocation();
		
		PreferencesUtils.setValueDouble(userPrefs,  "appLocationX", location.getX());
		PreferencesUtils.setValueDouble(userPrefs, "appLocationY", location.getY());

		//==========================================================================================
	}
	
	/**
	 * Store the app view size from registry.
	 */
	private void restoreAppViewSize() {
		//==========================================================================================
		final Preferences userPrefs = PreferencesUtils.getUserPreferences();

		//==========================================================================================
		final double appWith = PreferencesUtils.getValueDouble(userPrefs, "appWith", 800);
		final double appHeight = PreferencesUtils.getValueDouble(userPrefs,  "appHeight", 600);
		
		this.appView.setSize((int)appWith, (int)appHeight);
		
		//------------------------------------------------------------------------------------------
		final boolean appLocationXFoundKey = PreferencesUtils.checkKeyExists(userPrefs, "appLocationX");
		final boolean appLocationYFoundKey = PreferencesUtils.checkKeyExists(userPrefs, "appLocationY");
		
		if (appLocationXFoundKey && appLocationYFoundKey) {
			final double appLocationX = PreferencesUtils.getValueDouble(userPrefs, "appLocationX", 0);
			final double appLocationY = PreferencesUtils.getValueDouble(userPrefs,  "appLocationY", 0);
			
			this.appView.setLocation((int)appLocationX, 
			                         (int)appLocationY);
		} else {
			this.appView.setLocationRelativeTo(null);
		}
		//==========================================================================================
	}

	/**
	 * Show About-Dialog.
	 */
	public void doHelpAbout() {
		//==========================================================================================
		final AboutDialogView aboutDialogView = new AboutDialogView(this.appView, true);

		final AboutController aboutController = new AboutController(aboutDialogView);
		
		aboutController.doShow();
		
		//==========================================================================================
	}

	/**
	 * File-Open.
	 */
	public void doFileOpen() {
		//==========================================================================================
		this.setEnableOpenFile(false);

		//------------------------------------------------------------------------------------------
		final File fileActionFile = this.getLoadFile();

		final JFileChooser chooser = new JFileChooser();

		chooser.setFileHidingEnabled(true);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setSelectedFile(fileActionFile);
		
//		ExtensionFileFilter filter = new ExtensionFileFilter();
//		filter.addExtension("lwx");
//		filter.setDescription("Geneden XML-File");
//		chooser.setFileFilter(filter);

		final int state = chooser.showDialog(this.appView, "Open");

		if (state == JFileChooser.APPROVE_OPTION) {
			//File dir = chooser.getCurrentDirectory();
			final File file = chooser.getSelectedFile();

			try {
				this.doLoad(file);
				
				this.setLoadFile(file);
			} catch (LoadFileException ex) {
				ex.printStackTrace(System.err);

				JOptionPane.showMessageDialog(this.appView,
				                              "Error while loading \"" + file + "\".\n" +
				                              "Message: " + ex,
				                              "Load File Error",
				                              JOptionPane.ERROR_MESSAGE);
			}
		}

		//------------------------------------------------------------------------------------------
		this.setEnableOpenFile(true);
		
		//==========================================================================================
	}

	/**
	 * File-Save.
	 * 
	 * @return
	 * 			<code>true</code> if file is saved.<br/>
	 *	 		<code>false</code> if operation canceled or errors occourse.
	 */
	public boolean doFileSave() {
		//==========================================================================================
		boolean ret;
		
		this.setEnableSaveFile(false);

		//------------------------------------------------------------------------------------------
		final File fileActionFile = this.getLoadFile();

		final JFileChooser chooser = new JFileChooser();

		chooser.setFileHidingEnabled(true);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setSelectedFile(fileActionFile);
		
//		ExtensionFileFilter filter = new ExtensionFileFilter();
//		filter.addExtension("lwx");
//		filter.setDescription("Geneden XML-File");
//		chooser.setFileFilter(filter);

		final int state = chooser.showDialog(this.appView, "Save");

		if (state == JFileChooser.APPROVE_OPTION) {
			//File dir = chooser.getCurrentDirectory();
			final File file = chooser.getSelectedFile();

			try {
				this.doSave(file);
				
				this.setLoadFile(file);

				ret = true;
			} catch (final SaveFileException ex) {
				ex.printStackTrace(System.err);

				JOptionPane.showMessageDialog(this.appView,
				                              "Error while saving \"" + file + "\".\n" +
				                              "Message: " + ex,
				                              "Save File Error",
				                              JOptionPane.ERROR_MESSAGE);

				ret = false;
			}
		} else {
			ret = false;
		}
		//------------------------------------------------------------------------------------------
		this.setEnableSaveFile(true);
		
		//==========================================================================================
		return ret;
	}

	/**
	 * File-Import.
	 */
	public void doFileImport() {
		//==========================================================================================
		this.setEnableOpenFile(false);

		//------------------------------------------------------------------------------------------
		final File importFile = this.getImportFile();

		final JFileChooser chooser = new JFileChooser();

		chooser.setFileHidingEnabled(true);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setSelectedFile(importFile);
		
//		ExtensionFileFilter filter = new ExtensionFileFilter();
//		filter.addExtension("lwx");
//		filter.setDescription("Geneden XML-File");
//		chooser.setFileFilter(filter);

		final int state = chooser.showDialog(this.appView, "Import");

		if (state == JFileChooser.APPROVE_OPTION) {
			//File dir = chooser.getCurrentDirectory();
			final File file = chooser.getSelectedFile();

			try {
				this.doImport(file);
				
				this.setImportFile(file);
			} catch (final LoadFileException ex) {
				ex.printStackTrace(System.err);

				JOptionPane.showMessageDialog(this.appView,
				                              "Error while importing \"" + file + "\".\n" +
				                              "Message: " + ex,
				                              "Import File Error",
				                              JOptionPane.ERROR_MESSAGE);
			}
		}

		//------------------------------------------------------------------------------------------
		final ModulesTreeModel modulesTreeModel = this.modulesTreeController.getModulesTreeModel();
		final ModulesTreeView modulesTreeView = this.modulesTreeController.getModulesTreeView();
		
		modulesTreeView.setSelectionPath(modulesTreeModel.getSelectionPath());
		
		//------------------------------------------------------------------------------------------
		this.setEnableOpenFile(true);
		
		//==========================================================================================
	}
	
	/**
	 * @param enable
	 * 			<code>true</code> wenn die Open-Auswahlfunktionen für Files zugelassen werden sollen.
	 */
	public void setEnableOpenFile(final boolean enable) {
		//==========================================================================================
		this.appView.getFileOpenMenuItem().setEnabled(enable);
		this.appView.getFileOpenButtonView().setEnabled(enable);
		this.appView.getFileImportMenuItem().setEnabled(enable);
		//==========================================================================================
	}

	/**
	 * Load the Application data.
	 * 
	 * @param file
	 * 			is the file to load. 
	 * @throws LoadFileException 
	 * 			if a Error occourse while loading.
	 */
	public void doLoad(final File file) throws LoadFileException {
		//==========================================================================================
		//float frameRate = SwingMain.getSoundData().getFrameRate();
		final float frameRate = this.soundDataLogic.getFrameRate();
		final String absolutePath = file.getAbsolutePath();

		final ModuleGeneratorTypeInfoData mainModuleGeneratorTypeData;
		final GeneratorTypesData generatorTypesData = new GeneratorTypesData();

		try {
			mainModuleGeneratorTypeData =
				LoadFileOperationLogic.loadNoiseCompFile(this.soundSourceData, generatorTypesData, absolutePath, frameRate);
		} catch (final Exception ex) {
			throw new LoadFileException("Load \"" + absolutePath + "\".", ex);
		}
		
		//------------------------------------------------------------------------------------------
		this.soundService.removeAllGeneratorTypes();

		final Iterator<GeneratorTypeInfoData> generatorTypesIterator = generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext()) {
			final GeneratorTypeInfoData generatorTypeInfoData = generatorTypesIterator.next();

			this.soundService.addGeneratorType(generatorTypeInfoData);
		}
		
		//------------------------------------------------------------------------------------------
		this.changeEditModule(mainModuleGeneratorTypeData);

		//------------------------------------------------------------------------------------------
		this.appModel.setIsModelChanged(false);
		
		//==========================================================================================
	}

	public void changeEditModule(final ModuleGeneratorTypeInfoData mainModuleGeneratorTypeData) {
		this.selectEditModule(null);

		final List<GeneratorTypeInfoData> generatorTypes = this.soundService.retrieveGeneratorTypes();

		this.modulesTreeController.addGeneratorTypes(generatorTypes);

		this.selectEditModule(mainModuleGeneratorTypeData);
	}

	/**
	 * Save the Application data.
	 * 
	 * @param file
	 * 			is the file to save. 
	 * @throws SaveFileException
	 * 			if a Error occourse while saving.
	 */
	public void doSave(final File file) throws SaveFileException {
		//==========================================================================================
		final GeneratorTypesData generatorTypesData = new GeneratorTypesData();
		ModuleGeneratorTypeInfoData mainModuleGeneratorTypeData = null;

		final Iterator<GeneratorTypeInfoData> generatorTypesIterator = this.soundService.retrieveGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext()) {
			final GeneratorTypeInfoData generatorTypeInfoData = generatorTypesIterator.next();
			
			if (generatorTypeInfoData instanceof ModuleGeneratorTypeInfoData) {
				ModuleGeneratorTypeInfoData moduleGeneratorTypeData = (ModuleGeneratorTypeInfoData) generatorTypeInfoData;
				
				if (moduleGeneratorTypeData.getIsMainModuleGeneratorType() == true) {
					mainModuleGeneratorTypeData = moduleGeneratorTypeData;
				}
			}
			
			generatorTypesData.addGeneratorTypeData(generatorTypeInfoData);
		}
		
		//------------------------------------------------------------------------------------------
		final String absolutePath = file.getAbsolutePath();
		
		try {
			SaveFileOperationLogic.saveFile(generatorTypesData, 
			                                mainModuleGeneratorTypeData, 
			                                absolutePath);
		} catch (final Exception ex) {
			throw new SaveFileException("Save file \"" + absolutePath + "\".", ex);
		}
		
		//------------------------------------------------------------------------------------------
		this.appModel.setIsModelChanged(false);
		
		//==========================================================================================
	}

	/**
	 * Import the Application data.
	 * 
	 * @param file
	 * 			is the file to import. 
	 * @throws LoadFileException 
	 * 			if a Error occourse while importing.
	 */
	public void doImport(final File file)  throws LoadFileException {
		//==========================================================================================
		//float frameRate = SwingMain.getSoundData().getFrameRate();
		final float frameRate = this.soundDataLogic.getFrameRate();
		final String absolutePath = file.getAbsolutePath();

		final ModuleGeneratorTypeInfoData mainModuleGeneratorTypeData;
		final GeneratorTypesData generatorTypesData = new GeneratorTypesData();

		try {
			mainModuleGeneratorTypeData =
				ImportFileOperationLogic.importNoiseCompFile(this.soundSourceData, generatorTypesData, absolutePath, frameRate);
		} catch (final Exception ex) {
			throw new LoadFileException("Import \"" + absolutePath + "\".", ex);
		}
		
		//------------------------------------------------------------------------------------------
		mainModuleGeneratorTypeData.setIsMainModuleGeneratorType(false);
		
		//------------------------------------------------------------------------------------------
		// Check imported generator type is not existing:

		final GeneratorTypesData addedGeneratorTypesData = new GeneratorTypesData();

		final Iterator<GeneratorTypeInfoData> generatorTypesIterator = generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext()) {
			final GeneratorTypeInfoData generatorTypeInfoData = generatorTypesIterator.next();

			// Not an existing generator type?
			if (this.soundService.checkGeneratorTypeIsExisting(generatorTypeInfoData) == false)
			{
				this.soundService.addGeneratorType(generatorTypeInfoData);
				 
				addedGeneratorTypesData.addGeneratorTypeData(generatorTypeInfoData);
			}
		}
		
		//------------------------------------------------------------------------------------------
		this.modulesTreeController.importGeneratorTypes(addedGeneratorTypesData.getGeneratorTypesIterator());
		
//		this.selectEditModule(null);
//		
//		List<GeneratorTypeData> generatorTypes = this.soundService.retrieveGeneratorTypes();
//		
//		this.modulesTreeController.addGeneratorTypes(generatorTypes);
//		
//		this.selectEditModule(mainModuleGeneratorTypeData);
		
		//------------------------------------------------------------------------------------------
		this.appModel.setIsModelChanged(true);
		
		//==========================================================================================
	}

	/**
	 * @return
	 * 			the File of the last file save or load operation.
	 */
	private File getLoadFile() {
		return this.appModel.getLoadFile();
	}

	/**
	 * @param file
	 * 			is the File of the last file load or save operation.
	 */
	private void setLoadFile(final File file) {
		//==========================================================================================
		final Preferences userPrefs = PreferencesUtils.getUserPreferences();

		//==========================================================================================
		if (file == null) {
			this.appView.setTitle("NoiseComp");
		} else {
			final String fileName = file.getName();
			
			this.appView.setTitle(fileName + " - NoiseComp");
		}
		this.appModel.setLoadFile(file);
		
		//------------------------------------------------------------------------------------------
		final String loadFileStr = file.getAbsolutePath();
		
		PreferencesUtils.setValueString(userPrefs, 
		                                PREF_LOAD_FILE, 
		                                loadFileStr);
		
		//==========================================================================================
	}

	/**
	 * @return
	 * 			the File of the last file import operation.
	 */
	private File getImportFile() {
		return this.appModel.getImportFile();
	}

	/**
	 * @param file
	 * 			is the File of the last file import operation.
	 */
	private void setImportFile(final File file) {
		//==========================================================================================
		final Preferences userPrefs = PreferencesUtils.getUserPreferences();

		//==========================================================================================
		this.appModel.setImportFile(file);

		final String importFileStr = file.getAbsolutePath();
		
		PreferencesUtils.setValueString(userPrefs, 
		                                PREF_IMPORT_FILE, 
		                                importFileStr);
		
		//==========================================================================================
	}
	
	/**
	 * @param enable
	 * 			<code>true</code> wenn die Save-Auswahlfunktionen für Files zugelassen werden sollen.
	 */
	public void setEnableSaveFile(final boolean enable) {
		//==========================================================================================
		this.appView.getFileSaveMenuItem().setEnabled(enable);
		this.appView.getFileSaveButtonView().setEnabled(enable);
		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.swingView.timelineSelect.RemoveTimelineGeneratorListenerInterface#notifyRemoveTimelineGenerator(de.schmiereck.noiseComp.swingView.timelineSelect.TimelinesDrawPanelModel, de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel)
	 */
	@Override
	public void notifyRemoveTimelineGenerator(final SoundSourceData soundSourceData,
											  final TimelinesDrawPanelModel timelinesDrawPanelModel,
											  final TimelineSelectEntryModel timelineSelectEntryModel) {
		//==========================================================================================
		final TimelineManagerLogic timelineManagerLogic = this.soundSourceLogic.getTimelineManagerLogic();
		
		//------------------------------------------------------------------------------------------
		// Update ModuleData:

		final Timeline selectedTimeline = timelineSelectEntryModel.getTimeline();
		
		if (selectedTimeline != null) {
			timelineManagerLogic.removeTimeline(soundSourceData, selectedTimeline);
		}
		
		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.swingView.inputSelect.RemoveInputSelectEntryListenerInterface#notifyRemoveInputSelectEntry(de.schmiereck.noiseComp.timeline.Timeline, de.schmiereck.noiseComp.swingView.inputSelect.InputSelectModel, de.schmiereck.noiseComp.swingView.inputSelect.InputSelectEntryModel)
	 */
	@Override
	public void notifyRemoveInputSelectEntry(Timeline selectedTimeline,
	                                         InputSelectModel inputSelectModel, 
	                                         InputSelectEntryModel inputSelectEntryModel) {
		//==========================================================================================
		final TimelineManagerLogic timelineManagerLogic = this.soundSourceLogic.getTimelineManagerLogic();
		
		//------------------------------------------------------------------------------------------
		// Update Generator-Input-Data:

		final InputData inputData = inputSelectEntryModel.getInputData();
		
		if (inputData != null) {
			timelineManagerLogic.removeInput(soundSourceData, selectedTimeline,
			                                 inputData);
		}
		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.swingView.inputSelect.UpdateInputSelectEntryListenerInterface#notifyUpdateInputSelectEntry(de.schmiereck.noiseComp.timeline.Timeline, de.schmiereck.noiseComp.swingView.inputSelect.InputSelectModel, de.schmiereck.noiseComp.swingView.inputSelect.InputSelectEntryModel)
	 */
	@Override
	public void notifyUpdateInputSelectEntry(final Timeline selectedTimeline,
											 final InputSelectModel inputSelectModel,
											 final InputSelectEntryModel inputSelectEntryModel) {
		//==========================================================================================
		final TimelineManagerLogic timelineManagerLogic = this.soundSourceLogic.getTimelineManagerLogic();
		
		//------------------------------------------------------------------------------------------
		// Update Generator-Input-Data:

		final InputData inputData = inputSelectEntryModel.getInputData();
		
		if (Objects.nonNull(inputData)) {
			timelineManagerLogic.updateInput(this.soundSourceData, selectedTimeline,
			                                 inputData);
		}
		//==========================================================================================
	}

	/**
	 * Init the startup Model.
	 */
	public void initStartupModel() {
		this.appModel.setIsModelChanged(false);
	}

	/**
	 * Double the selected Timeline
	 */
	public void doDoubleTimeline() {
		//==========================================================================================
		final TimelineManagerLogic timelineManagerLogic = this.soundSourceLogic.getTimelineManagerLogic();
		
		//==========================================================================================
		final ModuleGeneratorTypeInfoData editedModuleGeneratorTypeData = this.getEditedModuleGeneratorTypeInfoData();
		final TimelinesDrawPanelModel timelinesDrawPanelModel = this.timelinesDrawPanelController.getTimelinesDrawPanelModel();
		final TimelineEditModel timelineEditModel = this.timelineEditController.getTimelineEditModel();
		
		final SelectedTimelineModel selectedTimelineModel = timelinesDrawPanelModel.getSelectedTimelineModel();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		final TimelineSelectEntryModel selectedTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		if (selectedTimelineSelectEntryModel != null) {
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Copy timeline:
			
			this.doCreateNewTimeline();

			final TimelineSelectEntryModel newTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();
			
			{
				final Timeline timeline = selectedTimelineSelectEntryModel.getTimeline();
				timelineEditModel.setTimeline(timeline);
				{
					final GeneratorTypeInfoData generatorTypeInfoData = timeline.getGenerator().getGeneratorTypeData();
					timelineEditModel.setGeneratorTypeInfoData(generatorTypeInfoData);
				}
			}
			{
				final String name = selectedTimelineSelectEntryModel.getName();
				timelineEditModel.setGeneratorName(name + " (new)");
			}
			{
				final Float startTimePos = selectedTimelineSelectEntryModel.getStartTimePos();
				timelineEditModel.setGeneratorStartTimePos(startTimePos);
			}
			{
				final Float endTimePos = selectedTimelineSelectEntryModel.getEndTimePos();
				timelineEditModel.setGeneratorEndTimePos(endTimePos);
			}
			
			int newEntryModelPos = 
				this.timelinesDrawPanelController.calcTimelineSelectEntryModelPos(newTimelineSelectEntryModel);
			
			this.timelineEditController.doUpdateEditModel(this.soundSourceData, editedModuleGeneratorTypeData,
			                                              newTimelineSelectEntryModel,
			                                              newEntryModelPos);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Copy:
			final Timeline newTimeline = newTimelineSelectEntryModel.getTimeline();

			final Timeline selectedTimeline = selectedTimelineSelectEntryModel.getTimeline();
			final Generator selectedGenerator = selectedTimeline.getGenerator();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Copy inputs:
			{
				final Iterator<InputData> inputsIterator = selectedGenerator.getInputsIterator();
				
				if (inputsIterator != null) {
					while (inputsIterator.hasNext()) {
						final InputData inputData = inputsIterator.next();

						final Generator inputGenerator = inputData.getInputGenerator();

						final Timeline inputTimeline = timelineManagerLogic.searchGeneratorTimeline(inputGenerator);

						final InputTypeData inputTypeData = inputData.getInputTypeData();
						final Float floatValue = inputData.getInputValue();
						final String stringValue = inputData.getInputStringValue();
						final InputTypeData moduleInputTypeData = inputData.getInputModuleInputTypeData();
	                    
						timelineManagerLogic.addGeneratorInput(soundSourceData, newTimeline,
						                                       inputTimeline,
						                                       inputTypeData, 
						                                       floatValue, stringValue,
						                                       moduleInputTypeData);
					}
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Copy outputs:
			{
				final Map<InputData, Timeline> outputTimelines = selectedTimeline.getOutputTimelines();
				final Set<Entry<InputData, Timeline>> outputTimelinesEntrySet = outputTimelines.entrySet();
				
				for (final Entry<InputData, Timeline> outputTimelineEntry : outputTimelinesEntrySet) {
					final Timeline outputTimeline = outputTimelineEntry.getValue();
					final InputData outputInputData = outputTimelineEntry.getKey();

					final InputTypeData inputTypeData = outputInputData.getInputTypeData();
					final Float floatValue = outputInputData.getInputValue();
					final String stringValue = outputInputData.getInputStringValue();
					final InputTypeData moduleInputTypeData = outputInputData.getInputModuleInputTypeData();
                    
					timelineManagerLogic.addGeneratorInput(soundSourceData, outputTimeline,
					                                       newTimeline, 
					                                       inputTypeData, 
					                                       floatValue, 
					                                       stringValue, 
					                                       moduleInputTypeData);
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			selectedTimelineModel.setSelectedTimelineSelectEntryModel(newTimelineSelectEntryModel);
			
		}
		//==========================================================================================
	}
	
	/**
	 * Do Update Timeline.
	 */
	private void doUpdateTimeline() {
		//==========================================================================================
		final TimelinesDrawPanelModel timelinesDrawPanelModel = this.timelinesDrawPanelController.getTimelinesDrawPanelModel();
//		TimelineSelectEntryModel timelineGeneratorModel = this.timelinesDrawPanelModel.getSelectedTimelineGeneratorModel();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		final ModuleGeneratorTypeInfoData editedModuleGeneratorTypeData = this.getEditedModuleGeneratorTypeInfoData();
		final TimelineSelectEntryModel timelineSelectEntryModel = this.selectedTimelineModel.getSelectedTimelineSelectEntryModel();

		int selectEntryModelPos = 
			this.timelinesDrawPanelController.calcTimelineSelectEntryModelPos(timelineSelectEntryModel);
		
//		Generator generator = 
//			this.retrieveGeneratorOfEditedModuletimelineGeneratorModel.getName());
		
		this.timelineEditController.doUpdateEditModel(this.soundSourceData, editedModuleGeneratorTypeData,
		                                              //generator,
		                                              timelineSelectEntryModel,
		                                              selectEntryModelPos);
		
		this.selectedTimelineModel.setSelectedTimelineSelectEntryModel(timelineSelectEntryModel);

		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		final Dimension timelinesDrawPanelDimension = timelinesDrawPanelModel.getDimension();
		
		// TimelinesTimeRule update.
		this.timelinesTimeRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getWidth());
		
		// TimelinesGeneratorsRule update.
		this.timelinesGeneratorsRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getHeight());
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		this.appModelChangedObserver.notifyAppModelChanged();
		
		//==========================================================================================
	}
	
	/**
	 * Do Rename Module.
	 * 
	 * @param folderTreeNode
	 * 			is the folder Tree-Node.
	 */
	public void doRenameModule(final DefaultMutableTreeNode folderTreeNode) {
		//==========================================================================================
		final String folderName = (String)folderTreeNode.getUserObject();
		
		this.appModel.setEditedModuleTreeNode(folderTreeNode);
		
		this.renameFolderController.doRenameFolder(folderName);
		
		//==========================================================================================
	}

	/**
	 * @param folderTreeNode
	 * 			is the folder Tree-Node.
	 */
	public void doCreateFolder(final DefaultMutableTreeNode folderTreeNode) {
		//==========================================================================================
		final String folderName = (String)folderTreeNode.getUserObject();
		
		this.appModel.setEditedModuleTreeNode(folderTreeNode);
		
		this.createFolderController.doCreateFolder("newSub-" + folderName);
		
		//==========================================================================================
	}

	/**
	 * Do Create Folder.
	 * 
	 * @param folderName
	 * 			is the folder name.
	 */
	public void doCreateFolder(final String folderName) {
		//==========================================================================================
		final DefaultMutableTreeNode editedFolderTreeNode = this.appModel.getEditedModuleTreeNode();

		this.modulesTreeController.doCreateFolder(editedFolderTreeNode, 
		                                          folderName);
		
		//==========================================================================================
	}

	/**
	 * @param editedModuleTreeNode
	 * 			is the folder Tree-Node.
	 */
	public void doCutModule(final DefaultMutableTreeNode editedModuleTreeNode) {
		//==========================================================================================
		this.appModel.setEditedModuleTreeNode(editedModuleTreeNode);
		
		//==========================================================================================
	}

	/**
	 * @param pasteFolderTreeNode
	 * 			is the paste folder or module Tree-Node.
	 */
	public void doPasteModule(final DefaultMutableTreeNode pasteFolderTreeNode) {
		//==========================================================================================
		final DefaultMutableTreeNode cutTreeNode = this.appModel.getEditedModuleTreeNode();
		
		//------------------------------------------------------------------------------------------
		this.modulesTreeController.pasteModule(cutTreeNode,
		                                       pasteFolderTreeNode);
		
		//==========================================================================================
	}

	/**
	 * @param playTimePos
	 * 			is the playbackPos in seconds.
	 */
	public void doSubmitPlaybackPos(final float playTimePos) {
		//==========================================================================================
		this.playController.doSubmitPlaybackPos(playTimePos);
		
		//==========================================================================================
	}

	public void doUpdateTimelineInput(final TimelineSelectEntryModel selectedTimelineSelectEntryModel,
	                                  final InputEntryModel selectedInputEntry, 
	                                  final InputTypeData inputTypeData,
	                                  final TimelineSelectEntryModel targetTimelineSelectEntryModel) {
		//==========================================================================================
		final TimelinesDrawPanelModel timelinesDrawPanelModel = this.timelinesDrawPanelController.getTimelinesDrawPanelModel();
		final SelectedTimelineModel selectedTimelineModel = timelinesDrawPanelModel.getSelectedTimelineModel();
		
		//==========================================================================================
		final InputEditModel inputEditModel = this.inputEditController.getInputEditModel();
		
		//==========================================================================================
		// Replay the user action:
		
		// New Input?
		if (Objects.isNull(selectedInputEntry.getInputData())) {
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// New Input:
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			//	Select Input-Type.
			inputEditModel.setInputTypeData(inputTypeData);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			//	Create new Input.
			this.inputEditController.doCreateNewInput(soundSourceData, this.inputSelectController);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}

		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Update Input:
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		//	Select Input-Generator.
		final Timeline targetTimeline = targetTimelineSelectEntryModel.getTimeline();
		
		inputEditModel.setInputTimeline(targetTimeline);
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		//	Submit.
		final InputSelectModel inputSelectModel = this.inputSelectController.getInputSelectModel();
		final Timeline selectedTimeline = selectedTimelineSelectEntryModel.getTimeline();
		
		this.inputEditController.doSubmitInput(this.soundSourceData, selectedTimeline, inputSelectModel);
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		//	Select Input.
		selectedTimelineModel.setSelectedInputEntry(selectedInputEntry, true);
		
		//==========================================================================================
	}

	public void startSoundSourceScheduler() {
		//==========================================================================================
		this.soundSourceSchedulerLogic = new SoundSourceSchedulerLogic(this.soundSourceData, this.soundSourceLogic, 32);

		// Start scheduled polling with the new SoundSource.
		this.soundSourceSchedulerLogic.startThread();

		//==========================================================================================
	}

	public SoundService getSoundService() {
		return this.soundService;
	}

	public SoundSourceData getSoundSourceData() {
		return this.soundSourceData;
	}

	public ModulesTreeController getModulesTreeController() {
		return this.modulesTreeController;
	}
}
