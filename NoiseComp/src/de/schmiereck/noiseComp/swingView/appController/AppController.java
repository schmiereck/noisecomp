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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModuleGeneratorTypeData;
import de.schmiereck.noiseComp.generator.ModuleGeneratorTypeData.TicksPer;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.SwingMain;
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
		   UpdateInputSelectEntryListenerInterface
{
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

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public AppController()
	{
		//==========================================================================================
		final SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		this.appModel = new AppModel();
		
		this.appView = new AppView(this.appModel);
		
		this.appView.setTitle("NoiseComp V" + Version.version);
		this.restoreAppViewSize();
		this.appView.setVisible(true);
		
		//------------------------------------------------------------------------------------------
		this.appModelChangedObserver = new AppModelChangedObserver(this.appModel);
		
		//------------------------------------------------------------------------------------------
		Preferences userPrefs = PreferencesUtils.getUserPreferences();
		{
			File loadFile = this.appModel.getLoadFile();
			
			if (loadFile == null)
			{
				String loadFileStr = 
					PreferencesUtils.getValueString(userPrefs, 
					                                PREF_LOAD_FILE, 
					                                null);
				
				if (loadFileStr != null)
				{
					File file = new File(loadFileStr);
					
					this.appModel.setLoadFile(file);
				}
			}
		}
		{
			File importFile = this.appModel.getImportFile();
			
			if (importFile == null)
			{
				String importFileStr = 
					PreferencesUtils.getValueString(userPrefs, 
					                                PREF_IMPORT_FILE, 
					                                null);
				
				if (importFileStr != null)
				{
					File file = new File(importFileStr);
					
					this.appModel.setImportFile(file);
				}
			}
		}
		//------------------------------------------------------------------------------------------
		// File:
		{
			FileOpenAction action = new FileOpenAction(this);
			
			this.appView.getFileOpenMenuItem().setAction(action);
			this.appView.getFileOpenButtonView().setAction(action);
		}
		{
			FileSaveAction action = new FileSaveAction(this);
			
			this.appView.getFileSaveMenuItem().setAction(action);
			this.appView.getFileSaveButtonView().setAction(action);
		}
		{
			FileImportAction action = new FileImportAction(this);
			
			this.appView.getFileImportMenuItem().setAction(action);
		}
		//------------------------------------------------------------------------------------------
		// Edit:
		{
			DoubleTimelineAction action = new DoubleTimelineAction(this);
			
			this.appView.getDoubleTimelineMenuItem().setAction(action);
		}
		//------------------------------------------------------------------------------------------
		// Help:
		{
			HelpAboutAction action = new HelpAboutAction(this);
			
			this.appView.getHelpAboutMenuItem().setAction(action);
		}
		//------------------------------------------------------------------------------------------
		this.modulesTreeController = new ModulesTreeController(this);
		
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
		
		TimelinesScrollPanelView timelinesScrollPanelView = this.timelinesScrollPanelController.getTimelinesScrollPanelView();
		
//		this.appView.setTimelineComponent(timelinesScrollPanelView.getScrollPane());
		this.appView.setTimelineComponent(timelinesScrollPanelView);
		
		this.timelinesScrollPanelController.setTimelinesRuleController(this.timelinesTimeRuleController,
		                                                               this.timelinesGeneratorsRuleController);
		
		//------------------------------------------------------------------------------------------
		this.timelinesDrawPanelController = 
			new TimelinesDrawPanelController(this,
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
		timelinesDrawPanelModel.setMaxUnitIncrementY(timelinesScrollPanelModel.getGeneratorSizeY());
		
		this.timelinesScrollPanelController.setTimelinesDrawPanelController(this.timelinesDrawPanelController);
		
//XXX		this.timelineSelectEntriesModel.setTimelineSelectEntryModels(timelineSelectEntryModels);
		
		this.timelineSelectEntriesModel.getTimelineGeneratorModelsChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
					
					Dimension timelinesDrawPanelDimension = timelinesDrawPanelModel.getDimension();
					
					timelinesTimeRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getWidth());
					timelinesGeneratorsRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getHeight());
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);

		this.timelineSelectEntriesModel.getChangeTimelinesPositionChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					timelinesGeneratorsRuleController.doChangeTimelinesPosition();
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);

		this.timelinesDrawPanelController.getTimelinesDrawPanelModel().getZoomXChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
					
					float zoomX = timelinesDrawPanelModel.getZoomX();
					
					timelinesTimeRuleController.doChangeZoomX(zoomX);
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		
		//------------------------------------------------------------------------------------------
		this.moduleEditController = new ModuleEditController(this,
		                                                     this.modulesTreeController.getModulesTreeModel(),
		                                                     this.appModelChangedObserver);
		
		this.appView.setModuleEditView(this.moduleEditController.getModuleEditView());
		
		//------------------------------------------------------------------------------------------
		this.inputSelectController = 
			new InputSelectController(this,
			                          this.timelinesDrawPanelController.getTimelinesDrawPanelModel(),
			                          this.appModelChangedObserver);
		
		this.appView.setInputSelectView(this.inputSelectController.getInputSelectView());
		
		//------------------------------------------------------------------------------------------
		this.inputEditController = 
			new InputEditController(this.inputSelectController.getInputSelectModel(),
			                        this.appModelChangedObserver,
			                        this.selectedTimelineModel);
		
		this.appView.setInputEditView(this.inputEditController.getInputEditView());
		
		//------------------------------------------------------------------------------------------
		this.timelineEditController = 
			new TimelineEditController(this,
			                           this.timelinesDrawPanelController.getTimelinesDrawPanelModel(),
			                           this.appModelChangedObserver);
		
		this.appView.setTimelineEditView(this.timelineEditController.getTimelineEditView());
		
		//------------------------------------------------------------------------------------------
		this.playController = new PlayController(this.timelinesTimeRuleController);
		
		//==========================================================================================
		// Exit:
		{
			ExitAction action = new ExitAction(this);
			
			this.appView.getExitMenuItem().setAction(action);
			this.appView.getExitButtonView().setAction(action);
			this.appView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		//------------------------------------------------------------------------------------------
		this.modulesTreeController.getModulesTreeView().addDoEditModuleListener
		(
		 	new DoEditModuleListener()
		 	{
				@Override
				public void notifyEditModule(ModuleGeneratorTypeData moduleGeneratorTypeData)
				{
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
		
		this.modulesTreeController.getModulesTreeModel().addEditModuleChangedListener
		(
		 	new EditModuleChangedListener()
		 	{
				@Override
				public void notifyEditModuleChanged(ModulesTreeModel modulesTreeModel,
				                                    TreePath selectionTreePath)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					ModuleGeneratorTypeData moduleGeneratorTypeData = modulesTreeModel.getEditedModuleGeneratorTypeData();
					
					moduleEditController.doEditModuleChanged(moduleGeneratorTypeData);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Modules-Tree Edited Model changed: Update ModuleInput-Type Select Model:
		
		this.modulesTreeController.getModulesTreeModel().addEditModuleChangedListener
		(
		 	new EditModuleChangedListener()
		 	{
				@Override
				public void notifyEditModuleChanged(ModulesTreeModel modulesTreeModel,
				                                    TreePath selectionTreePath)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					ModuleInputTypeSelectController moduleInputTypeSelectController = moduleInputTypesController.getModuleInputTypeSelectController();
					
					ModuleGeneratorTypeData moduleGeneratorTypeData = modulesTreeModel.getEditedModuleGeneratorTypeData();
					
					moduleInputTypeSelectController.doEditModuleChanged(moduleGeneratorTypeData);
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// ModuleEdit:
		//------------------------------------------------------------------------------------------
		// ModuleEdit: Edit-Input-Types Button: Update Modules-Tree-View:
		
		this.moduleEditController.getModuleEditView().getEditInputTypesButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					appModuleController.doEditInputTypes(moduleInputTypesController);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// ModuleEdit Model changed: Update Modules-Tree-View:
		
		this.moduleEditController.getModuleEditModel().getModulEditModelChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					ModuleGeneratorTypeData editedModuleGeneratorTypeData = 
						modulesTreeController.getModulesTreeModel().getEditedModuleGeneratorTypeData();
					
					modulesTreeController.updateEditedModulereeEntry(editedModuleGeneratorTypeData);
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// ModuleInput-Type:
		//------------------------------------------------------------------------------------------
		// ModuleInput-Type Selected Input changed: Update ModuleInput-Type-Edit Model:
		
		this.moduleInputTypeSelectController.getInputTypeSelectModel().getSelectedRowNoChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					ModuleInputTypeSelectModel moduleInputTypeSelectModel = moduleInputTypeSelectController.getInputTypeSelectModel();
					
					ModuleInputTypeSelectEntryModel selectEntryModel = moduleInputTypeSelectModel.getSelectedRow();
					
					InputTypeData inputTypeData;
					
					if (selectEntryModel != null)
					{
						inputTypeData = selectEntryModel.getInputTypeData();
					}
					else
					{
						inputTypeData = null;
					}
					
					moduleInputTypeEditController.updateEditedInputType(inputTypeData);
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// ModuleInput-Type Edit: Create-New Button: Update ModuleInput-Type Select Model:
		
		this.moduleInputTypeEditController.getModuleInputTypeEditView().getCreateNewButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					moduleInputTypeSelectController.doCreateNew();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			 	}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// ModuleInput-Type Edit Update-Button: Update ModuleInput-Type Data and ModuleInput-Type Edit-Model:
		// TODO Move to controller.
		this.moduleInputTypeEditController.getModuleInputTypeEditView().getUpdateButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
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
		this.moduleInputTypeEditController.getModuleInputTypeEditView().getRemoveButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					ModulesTreeModel modulesTreeModel = modulesTreeController.getModulesTreeModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					ModuleGeneratorTypeData editedModuleGeneratorTypeData = modulesTreeModel.getEditedModuleGeneratorTypeData();
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
		
		this.moduleInputTypeEditController.getModuleInputTypeEditModel().getInputTypeIDChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					ModuleInputTypeSelectModel selectModel = moduleInputTypeSelectController.getInputTypeSelectModel();
					
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
			
			inputEntriesModel.getInputEntriesAddNotifier().addInputEntriesAddListeners
			(
			 	new InputEntriesAddListenerInterface()
				{
					@Override
					public void notifyAddInputEntry(int entryPos,
					                                InputEntryModel inputEntryModel)
					{
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						InputPosEntriesModel inputPosEntriesModel = selectedTimelineModel.getInputPosEntriesModel();
						
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
			
			inputEntriesModel.getInputEntriesChangePositionsNotifier().addInputEntriesChangePositionsListeners
			(
			 	new InputEntriesChangePositionsListenerInterface()
				{
					@Override
					public void notifyChangePositions(final InputEntryModel selectedInputEntryModel, 
					                                  final InputEntryModel targetInputEntryModel)
					{
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
						
						TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						final TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
						
						final SelectedTimelineModel selectedTimelineModel = timelinesDrawPanelModel.getSelectedTimelineModel();
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						final TimelineSelectEntryModel selectedTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();
						
						final Timeline selectedTimeline = selectedTimelineSelectEntryModel.getTimeline();
						
						InputData selectedInputData = selectedInputEntryModel.getInputData();
						InputData targetInputData = targetInputEntryModel.getInputData();
						
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
		
		this.timelineEditController.getTimelineEditView().getRemoveButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					TimelinesDrawPanelModel timelinesDrawPanelModel = 
						timelinesDrawPanelController.getTimelinesDrawPanelModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					TimelineSelectEntryModel selectedTimelineSelectEntryModel = 
						selectedTimelineModel.getSelectedTimelineSelectEntryModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Timeline-Select-Model:

					timelineSelectEntriesModel.removeTimelineSelectEntryModel(timelinesDrawPanelModel,
					                                                          selectedTimelineSelectEntryModel);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);	
		//------------------------------------------------------------------------------------------
		// Timeline-Edit Update-Button: Timeline-Select-Model:
		
		this.timelineEditController.getTimelineEditView().getUpdateButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					doUpdateTimeline();
				}

		 	}
		);
		//------------------------------------------------------------------------------------------
		// Timeline-Edit Create-New-Timeline-Button: Timeline-Select-Model:
		
		this.timelineEditController.getTimelineEditView().getCreateNewButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//					TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					doCreateNewTimeline();

					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);	
		//------------------------------------------------------------------------------------------
		// Input-Select:
		//------------------------------------------------------------------------------------------
		// Input-Select: Selected Input changed: Update Input-Edit:
		
		this.inputSelectController.getInputSelectModel().getSelectedRowNoChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					InputSelectModel inputSelectModel = inputSelectController.getInputSelectModel();
					
					InputSelectEntryModel inputSelectEntryModel = inputSelectModel.getSelectedRow();
					
					boolean editInput;
					InputData inputData;
					
					if (inputSelectEntryModel != null)
					{
						inputData = inputSelectEntryModel.getInputData();
						editInput = true;
					}
					else
					{
						inputData = null;
						editInput = false;
					}
					
					ModulesTreeModel modulesTreeModel = modulesTreeController.getModulesTreeModel();
					
					ModuleGeneratorTypeData editedModuleGeneratorTypeData = modulesTreeModel.getEditedModuleGeneratorTypeData();
					
					Timeline selectedTimeline = timelinesDrawPanelController.getSelectedTimeline();
					
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
		// Input-Edit Update-Button: Update Input-Data and Input-Edit-Model:
		
		this.inputEditController.getInputEditView().getUpdateButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final InputSelectModel inputSelectModel = inputSelectController.getInputSelectModel();
					
					final Timeline selectedTimeline = timelinesDrawPanelController.getSelectedTimeline();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					inputEditController.doSubmit(inputSelectModel, selectedTimeline);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Input-Edit Remove-Input-Button: Update Input-Select-Model and Generator-Input-Data:
		
		this.inputEditController.getInputEditView().getRemoveButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//					Timeline selectedTimeline = timelinesDrawPanelController.getSelectedTimeline();
					
					inputSelectController.doRemoveSelectedInputEntry();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			 	}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Input-Edit Create-New-Input-Button: Update Input-Select-Model // and Generator-Input-Data:
		
		this.inputEditController.getInputEditView().getCreateNewButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					inputEditController.doCreateNewInput(inputSelectController);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			 	}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// ModuleEdit:
		//------------------------------------------------------------------------------------------
		// Update-Button: Update Module
		
		this.moduleEditController.getModuleEditView().getUpdateButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					appModuleController.doUpdateEditModule(moduleEditController,
					                                       modulesTreeController);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Models Changed:
		//------------------------------------------------------------------------------------------
		final ModuleEditModel modulEditModel = this.moduleEditController.getModuleEditModel();
		
		//------------------------------------------------------------------------------------------
		modulEditModel.getZoomXChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
					
					float zoomX = modulEditModel.getZoomX();
					
					timelinesDrawPanelModel.setZoomX(zoomX);
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		modulEditModel.getTicksCountChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					JTextField ticksTextField = appView.getTicksTextField();
					
					Float ticksCount = modulEditModel.getTicksCount();
					
					ticksTextField.setText(OutputUtils.makeFloatEditText(ticksCount));
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		modulEditModel.getTicksPerChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					JRadioButton ticksSecondsButton = appView.getTicksSecondsButton();
					JRadioButton ticksMilliecondsButton = appView.getTicksMilliecondsButton();
					JRadioButton ticksBpmButton = appView.getTicksBpmButton();
					
					TicksPer ticksPer = modulEditModel.getTicksPer();
					
					switch (ticksPer)
					{
						case Seconds:
						{
							ticksSecondsButton.setSelected(true);
							ticksMilliecondsButton.setSelected(false);
							ticksBpmButton.setSelected(false);
							break;
						}
						case Milliseconds:
						{
							ticksSecondsButton.setSelected(false);
							ticksMilliecondsButton.setSelected(true);
							ticksBpmButton.setSelected(false);
							break;
						}
						case BPM:
						{
							ticksSecondsButton.setSelected(false);
							ticksMilliecondsButton.setSelected(false);
							ticksBpmButton.setSelected(true);
							break;
						}
						default:
						{
							throw new RuntimeException("Unexpected TicksPer \"" + ticksPer + "\".");
						}
					}
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		{
		 	ModelPropertyChangedListener ticksChangedListener = new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					TimelinesTimeRuleModel timelinesTimeRuleModel = timelinesTimeRuleController.getTimelinesTimeRuleModel();
					
					TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
					
					// Update TimelinesTimeRule
					TicksPer ticksPer = modulEditModel.getTicksPer();
					Float ticksCount = modulEditModel.getTicksCount();
					
					timelinesTimeRuleModel.notifyTicksChangedNotifier(ticksPer, ticksCount);
					
					timelinesDrawPanelModel.notifyTicksChangedNotifier(ticksPer, ticksCount);
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	};
		 	modulEditModel.getTicksPerChangedNotifier().addModelPropertyChangedListener
			(
			 	ticksChangedListener
			);
		 	modulEditModel.getTicksCountChangedNotifier().addModelPropertyChangedListener
			(
			 	ticksChangedListener
			);
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
	 	ModelPropertyChangedListener modelPropertyChangedListener = new ModelPropertyChangedListener()
	 	{
			@Override
			public void notifyModelPropertyChanged()
			{
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Update TimelinesDrawPanel:
				
				TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
				
				timelinesDrawPanelModel.getTimelineGeneratorModelChangedListener().notifyModelPropertyChanged();

				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			}
	 	};
		//------------------------------------------------------------------------------------------
	 	InputEditModel inputEditModel = this.inputEditController.getInputEditModel();
	 	
		//------------------------------------------------------------------------------------------
		// Input-Edit-Model InputTypeData changed: Update TimelinesDrawPanel:
		
		inputEditModel.getInputTypeDataChangedNotifier().addModelPropertyChangedListener(modelPropertyChangedListener);
		
		//------------------------------------------------------------------------------------------
		// Input-Edit-Model InputTypeData changed: Update TimelinesDrawPanel:
		
		inputEditModel.getInputTypeDataChangedNotifier().addModelPropertyChangedListener(modelPropertyChangedListener);

		//------------------------------------------------------------------------------------------
		// Input-Edit-Model Value changed: Update Input-Select and TimelinesDrawPanel:
		
	 	inputEditModel.getValueChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					InputSelectModel inputSelectModel = inputSelectController.getInputSelectModel();
					
					InputEntriesModel inputEntriesModel = selectedTimelineModel.getInputEntriesModel();
					
					InputSelectEntryModel inputSelectEntryModel = inputSelectModel.getSelectedRow();

					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Input-Select:
					
					Integer selectedRowNo = inputSelectModel.getSelectedRowNo();
					
					InputEntryModel inputEntryModel;
					
					if (selectedRowNo != null)
					{
						inputEntryModel = inputEntriesModel.searchInputEntry(selectedRowNo);
					}
					else
					{
						inputEntryModel = null;
					}

					InputData inputData;
					
					if (inputSelectEntryModel != null)
					{
						inputData = inputSelectEntryModel.getInputData();
					}
					else
					{
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
	 	timelineSelectEntriesModel.getRemoveTimelineGeneratorNotifier().addRemoveTimelineGeneratorListeners
	 	(
	 	 	this
	 	);
	    //------------------------------------------------------------------------------------------
	 	timelineSelectEntriesModel.getRemoveTimelineGeneratorNotifier().addRemoveTimelineGeneratorListeners
	 	(
	 	 	timelinesTimeRuleController
	 	);
	    //------------------------------------------------------------------------------------------
		// TimelinesGeneratorsRule update.
	 	timelineSelectEntriesModel.getRemoveTimelineGeneratorNotifier().addRemoveTimelineGeneratorListeners
	 	(
	 	 	timelinesGeneratorsRuleController
	 	);
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
	 	timelinesDrawPanelModel.getTimelineStartTimePosChangedListeners().add
	 	(
	 	 	new TimelineStartTimePosChangedListenerInterface()
	 	 	{
				@Override
				public void notifyTimelineStartTimePosChangedListener(Timeline timeline, Float startTimePos)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
					
					TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();
					
					TimelineEditModel timelineEditModel = timelineEditController.getTimelineEditModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					timelineManagerLogic.updateStartTimePos(timeline, startTimePos);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					timelineEditModel.setGeneratorStartTimePos(startTimePos);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					appModelChangedObserver.notifyAppModelChanged();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
	 	 	}
	 	);
	    //------------------------------------------------------------------------------------------
	 	timelinesDrawPanelModel.getTimelineEndTimePosChangedListeners().add
	 	(
	 	 	new TimelineEndTimePosChangedListenerInterface()
	 	 	{
				@Override
				public void notifyTimelineEndTimePosChangedListener(Timeline timeline, Float endTimePos)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
					
					TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();
					
					TimelineEditModel timelineEditModel = timelineEditController.getTimelineEditModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					if (timeline != null)
					{
						timelineManagerLogic.updateEndTimePos(timeline, endTimePos);
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
	 	InputSelectModel inputSelectModel = this.inputSelectController.getInputSelectModel();
	 	
	 	inputSelectModel.getRemoveInputSelectEntryNotifier().addRemoveInputSelectEntryListeners
	 	(
	 	 	this
	 	);
	 	
	 	inputSelectModel.getUpdateInputSelectEntryNotifier().addUpdateInputSelectEntryListeners
	 	(
	 	 	this
	 	);
	    //------------------------------------------------------------------------------------------
	 	final RenameFolderModel renameFolderModel = this.renameFolderController.getRenameFolderModel();
	 	
	 	renameFolderModel.getFolderNameChangedNotifier().addModelPropertyChangedListener
	 	(
	 	 	new ModelPropertyChangedListener()
	 	 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//					SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
					
					ModulesTreeModel modulesTreeModel = modulesTreeController.getModulesTreeModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					String folderName = renameFolderModel.getFolderName();

					DefaultMutableTreeNode editedFolderTreeNode = appModel.getEditedModuleTreeNode();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Service:
					
//					RenameFolderModel renameFolderModel = renameFolderController.getRenameFolderModel();
					
					String folderPath = modulesTreeModel.makeFolderPath(editedFolderTreeNode);
					
					soundService.renameFolder(folderPath,
					                          folderName);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update View-Model:
					
					// XXX rename Folder
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Views:
					
					modulesTreeController.updateEditedFolderTreeNode(editedFolderTreeNode,
					                                                 folderName);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
	 	 	}
	 	);
	    //------------------------------------------------------------------------------------------
	 	// Action-Listeners:
	    //------------------------------------------------------------------------------------------
		// http://download.oracle.com/javase/tutorial/uiswing/misc/action.html
		this.appView.getPlayButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					playController.doPlaySound();
				}
		 	}
		);
	    //------------------------------------------------------------------------------------------
		this.appView.getPauseButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					playController.doPauseSound();
				}
		 	}
		);
	    //------------------------------------------------------------------------------------------
		this.appView.getStopButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					playController.doStopSound();
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.appView.getLoopButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					JCheckBox loopButton = appView.getLoopButton();
					
					boolean looped = loopButton.getModel().isSelected();
					
					playController.doLoopSound(looped);
				}
		 	}
		);
	    //------------------------------------------------------------------------------------------
		this.appView.getZoomInButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					moduleEditController.doTimelinesZoomIn();
				}
		 	}
		);
	    //------------------------------------------------------------------------------------------
		this.appView.getZoomOutButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					moduleEditController.doTimelinesZoomOut();
				}
		 	}
		);
	    //------------------------------------------------------------------------------------------
		// Exit:
		this.appView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.appView.addWindowListener(new WindowAdapter() 
		{
            /* (non-Javadoc)
             * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
             */
            public void windowClosing(WindowEvent ex) 
            {
            	doExit();
            }
        });
	    //------------------------------------------------------------------------------------------
		this.appView.getTicksTextField().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					JTextField ticksTextField = appView.getTicksTextField();
					
					String text = ticksTextField.getText();
					
					Float ticksCount = InputUtils.makeFloatValue(text);
					
					modulEditModel.setTicksCount(ticksCount);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.appView.getTicksSecondsButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					modulEditModel.setTicksPer(TicksPer.Seconds);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.appView.getTicksMilliecondsButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					modulEditModel.setTicksPer(TicksPer.Milliseconds);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.appView.getTicksBpmButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					modulEditModel.setTicksPer(TicksPer.BPM);
				}
		 	}
		);
		
	    //------------------------------------------------------------------------------------------
		// Init Generator-Types:
	    //------------------------------------------------------------------------------------------
		List<GeneratorTypeData> generatorTypes = soundService.retrieveGeneratorTypes();
		
		this.modulesTreeController.addGeneratorTypes(generatorTypes);
		
	    //------------------------------------------------------------------------------------------
//		this.timelinesTimeRuleController.doChangeZoomX(this.timelinesDrawPanelController.getTimelinesDrawPanelModel().getZoomX());
		modulEditModel.setZoomX(this.timelinesDrawPanelController.getTimelinesDrawPanelModel().getZoomX());
		
	    //------------------------------------------------------------------------------------------
		modulEditModel.setTicksCount(new Float(1.0F));
		
	    //------------------------------------------------------------------------------------------
		modulEditModel.setTicksPer(TicksPer.Seconds);
		
		//==========================================================================================
	}

	/**
	 * Do Create New Timeline.
	 */
	public void doCreateNewTimeline()
	{
		this.timelinesDrawPanelController.doCreateNew(this.timelinesTimeRuleController,
		                                              this.timelinesGeneratorsRuleController,
		                                              this.appModelChangedObserver);
	}

	/**
	 * @param mainModuleGeneratorTypeData
	 * 			is the edited module
	 */
	public void selectEditModule(ModuleGeneratorTypeData mainModuleGeneratorTypeData)
	{
		//==========================================================================================
		final SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
		//==========================================================================================
		final TimelinesDrawPanelModel timelinesDrawPanelModel = this.timelinesDrawPanelController.getTimelinesDrawPanelModel();
		
		final SelectedTimelineModel selectedTimelineModel = timelinesDrawPanelModel.getSelectedTimelineModel();
		
		//==========================================================================================
		selectedTimelineModel.setSelectedTimelineSelectEntryModel(null);

		this.timelinesDrawPanelController.clearTimelineGenerators();
		
		//------------------------------------------------------------------------------------------
		this.modulesTreeController.getModulesTreeModel().setEditedModuleGeneratorTypeData(mainModuleGeneratorTypeData);
		
		if (mainModuleGeneratorTypeData != null)
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Generates a new TimelineManagerLogic.
			{
				// XXX Because of a memory leake clear timelines explicitely.
				
				TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();
				
				if (timelineManagerLogic != null)
				{
					timelineManagerLogic.clearTimelines();
				}
			}
			List<Timeline> timelines = soundSourceLogic.setMainModuleGeneratorTypeData(mainModuleGeneratorTypeData);
			
			TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Add new Listeners:
			timelineManagerLogic.addTimelineContentChangedListener(this.timelinesDrawPanelController);

			timelineManagerLogic.addTimelineContentChangedListener(this.timelinesTimeRuleController);
			
			timelineManagerLogic.addTimelineContentChangedListener(this.timelinesGeneratorsRuleController);

			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			int timelinePos = 0;
			
			for (Timeline timeline : timelines)
			{
				Generator generator = timeline.getGenerator();
				
				TimelineSelectEntryModel timelineSelectEntryModel = 
					new TimelineSelectEntryModel(timeline,
//					                             timelinePos,
					                             generator.getName(),
					                             generator.getStartTimePos(),
					                             generator.getEndTimePos());
				
				this.timelinesDrawPanelController.addTimelineSelectEntryModel(timelinePos,
				                                                              timelineSelectEntryModel);
				
				timelinePos++;
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
	public Generator retrieveGeneratorOfEditedModule(String generatorName)
	{
		//==========================================================================================
		ModuleGeneratorTypeData moduleGeneratorTypeData = 
			modulesTreeController.getModulesTreeModel().getEditedModuleGeneratorTypeData();
		
		Generator generator = moduleGeneratorTypeData.searchGenerator(generatorName);
		
		//==========================================================================================
		return generator;
	}

	/**
	 * @return 
	 * 			returns the {@link #appView}.
	 */
	public AppView getAppView()
	{
		return this.appView;
	}

	/**
	 * @return
	 * 			the generator types.
	 */
	public List<GeneratorTypeData> retrieveGeneratorTypesForSelect()
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		List<GeneratorTypeData> generatorTypes = soundService.retrieveGeneratorTypes();

		//==========================================================================================
		return generatorTypes;
	}
	
	/**
	 * @return
	 * 			the edited ModuleGenerator-Type Data.
	 */
	public ModuleGeneratorTypeData getEditedModuleGeneratorTypeData()
	{
		//==========================================================================================
		ModulesTreeModel modulesTreeModel = this.modulesTreeController.getModulesTreeModel();
		
		ModuleGeneratorTypeData editedModuleGeneratorTypeData = modulesTreeModel.getEditedModuleGeneratorTypeData();
		
		//==========================================================================================
		return editedModuleGeneratorTypeData;
	}

	/**
	 * Exit Applikation.
	 */
	public void doExit()
	{
		//==========================================================================================
		boolean exitApp;
		
		boolean isModelChanged = this.appModel.getIsModelChanged();
		
		if (isModelChanged == true)
		{
			int option = JOptionPane.showConfirmDialog(this.appView, "File has been modified.\nSave Changes?");
	
			if (option == JOptionPane.YES_OPTION)
			{
				boolean fileSaved = this.doFileSave();
				
				if (fileSaved == true)
				{
					exitApp = true;
				}
				else
				{
					exitApp = false;
				}
			}
			else
			{
				exitApp = true;
			}
		}
		else
		{
			exitApp = true;
		}
		
		//------------------------------------------------------------------------------------------
		if (exitApp == true)
		{
			this.storeAppViewSize();
			
			System.exit(0);
		}
		
		//==========================================================================================
	}

	/**
	 * Store the app view size to registry.
	 */
	private void storeAppViewSize()
	{
		//==========================================================================================
		Preferences userPrefs = PreferencesUtils.getUserPreferences();

		//==========================================================================================
		Dimension size = this.appView.getSize();
		
		PreferencesUtils.setValueDouble(userPrefs, 
		                                "appWith", 
		                                size.getWidth());

		PreferencesUtils.setValueDouble(userPrefs, 
		                                "appHeight", 
		                                size.getHeight());

		//------------------------------------------------------------------------------------------
		Point location = this.appView.getLocation();
		
		PreferencesUtils.setValueDouble(userPrefs, 
		                                "appLocationX", 
		                                location.getX());

		PreferencesUtils.setValueDouble(userPrefs, 
		                                "appLocationY", 
		                                location.getY());

		//==========================================================================================
	}
	
	/**
	 * Store the app view size from registry.
	 */
	private void restoreAppViewSize()
	{
		//==========================================================================================
		Preferences userPrefs = PreferencesUtils.getUserPreferences();

		//==========================================================================================
		double appWith = 
			PreferencesUtils.getValueDouble(userPrefs, 
			                                "appWith", 
			                                800);
		
		double appHeight = 
			PreferencesUtils.getValueDouble(userPrefs, 
			                                "appHeight", 
			                                600);
		
		this.appView.setSize((int)appWith, (int)appHeight);
		
		//------------------------------------------------------------------------------------------
		boolean appLocationXFoundKey = PreferencesUtils.checkKeyExists(userPrefs, "appLocationX");
		boolean appLocationYFoundKey = PreferencesUtils.checkKeyExists(userPrefs, "appLocationY");
		
		if (appLocationXFoundKey && appLocationYFoundKey)
		{
			double appLocationX = PreferencesUtils.getValueDouble(userPrefs, 
			                                                      "appLocationX", 
			                                                      0);

			double appLocationY = PreferencesUtils.getValueDouble(userPrefs, 
			                                                      "appLocationY", 
			                                                      0);
			
			this.appView.setLocation((int)appLocationX, 
			                         (int)appLocationY);
		}
		else
		{
			this.appView.setLocationRelativeTo(null);
		}
		//==========================================================================================
	}

	/**
	 * Show About-Dialog.
	 */
	public void doHelpAbout()
	{
		//==========================================================================================
		AboutDialogView aboutDialogView = new AboutDialogView(this.appView, true);
		
		AboutController aboutController = new AboutController(aboutDialogView);
		
		aboutController.doShow();
		
		//==========================================================================================
	}

	/**
	 * File-Open.
	 */
	public void doFileOpen()
	{
		//==========================================================================================
		this.setEnableOpenFile(false);

		//------------------------------------------------------------------------------------------
		File fileActionFile = this.getLoadFile();
		
		JFileChooser chooser = new JFileChooser();

		chooser.setFileHidingEnabled(true);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setSelectedFile(fileActionFile);
		
//		ExtensionFileFilter filter = new ExtensionFileFilter();
//		filter.addExtension("lwx");
//		filter.setDescription("Geneden XML-File");
//		chooser.setFileFilter(filter);

		int state = chooser.showDialog(this.appView, "Open");

		if (state == JFileChooser.APPROVE_OPTION)
		{ 
			//File dir = chooser.getCurrentDirectory();
			File file = chooser.getSelectedFile();

			try
			{
				this.doLoad(file);
				
				this.setLoadFile(file);
			}
			catch (LoadFileException ex)
			{
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
	public boolean doFileSave()
	{
		//==========================================================================================
		boolean ret;
		
		this.setEnableSaveFile(false);

		//------------------------------------------------------------------------------------------
		File fileActionFile = this.getLoadFile();
		
		JFileChooser chooser = new JFileChooser();

		chooser.setFileHidingEnabled(true);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setSelectedFile(fileActionFile);
		
//		ExtensionFileFilter filter = new ExtensionFileFilter();
//		filter.addExtension("lwx");
//		filter.setDescription("Geneden XML-File");
//		chooser.setFileFilter(filter);

		int state = chooser.showDialog(this.appView, "Save");

		if (state == JFileChooser.APPROVE_OPTION)
		{ 
			//File dir = chooser.getCurrentDirectory();
			File file = chooser.getSelectedFile();

			try
			{
				this.doSave(file);
				
				this.setLoadFile(file);

				ret = true;
			}
			catch (SaveFileException ex)
			{
				ex.printStackTrace(System.err);

				JOptionPane.showMessageDialog(this.appView,
				                              "Error while saving \"" + file + "\".\n" +
				                              "Message: " + ex,
				                              "Save File Error",
				                              JOptionPane.ERROR_MESSAGE);

				ret = false;
			}
		}
		else
		{
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
	public void doFileImport()
	{
		//==========================================================================================
		this.setEnableOpenFile(false);

		//------------------------------------------------------------------------------------------
		File importFile = this.getImportFile();
		
		JFileChooser chooser = new JFileChooser();

		chooser.setFileHidingEnabled(true);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setSelectedFile(importFile);
		
//		ExtensionFileFilter filter = new ExtensionFileFilter();
//		filter.addExtension("lwx");
//		filter.setDescription("Geneden XML-File");
//		chooser.setFileFilter(filter);

		int state = chooser.showDialog(this.appView, "Import");

		if (state == JFileChooser.APPROVE_OPTION)
		{ 
			//File dir = chooser.getCurrentDirectory();
			File file = chooser.getSelectedFile();

			try
			{
				this.doImport(file);
				
				this.setImportFile(file);
			}
			catch (LoadFileException ex)
			{
				ex.printStackTrace(System.err);

				JOptionPane.showMessageDialog(this.appView,
				                              "Error while importing \"" + file + "\".\n" +
				                              "Message: " + ex,
				                              "Import File Error",
				                              JOptionPane.ERROR_MESSAGE);
			}
		}

		//------------------------------------------------------------------------------------------
		ModulesTreeModel modulesTreeModel = this.modulesTreeController.getModulesTreeModel();
		ModulesTreeView modulesTreeView = this.modulesTreeController.getModulesTreeView();
		
		modulesTreeView.setSelectionPath(modulesTreeModel.getSelectionPath());
		
		//------------------------------------------------------------------------------------------
		this.setEnableOpenFile(true);
		
		//==========================================================================================
	}
	
	/**
	 * @param enable
	 * 			<code>true</code> wenn die Open-Auswahlfunktionen für Files zugelassen werden sollen.
	 */
	public void setEnableOpenFile(boolean enable)
	{
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
	public void doLoad(File file) 
	throws LoadFileException
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		float frameRate = SwingMain.getSoundData().getFrameRate();
		String absolutePath = file.getAbsolutePath();

		ModuleGeneratorTypeData mainModuleGeneratorTypeData;
		GeneratorTypesData generatorTypesData = new GeneratorTypesData();

		try
		{
			mainModuleGeneratorTypeData =
				LoadFileOperationLogic.loadNoiseCompFile(generatorTypesData, absolutePath, frameRate);
		}
		catch (Exception ex)
		{
			throw new LoadFileException("Load \"" + absolutePath + "\".", ex);
		}
		
		//------------------------------------------------------------------------------------------
		soundService.removeAllGeneratorTypes();
		
		Iterator<GeneratorTypeData> generatorTypesIterator = generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext())
		{
			GeneratorTypeData generatorTypeData = generatorTypesIterator.next();
			
			soundService.addGeneratorType(generatorTypeData);
		}
		
		//------------------------------------------------------------------------------------------
		this.selectEditModule(null);
		
		List<GeneratorTypeData> generatorTypes = soundService.retrieveGeneratorTypes();
		
		this.modulesTreeController.addGeneratorTypes(generatorTypes);
		
		this.selectEditModule(mainModuleGeneratorTypeData);
		
		//------------------------------------------------------------------------------------------
		this.appModel.setIsModelChanged(false);
		
		//==========================================================================================
	}

	/**
	 * Save the Application data.
	 * 
	 * @param file
	 * 			is the file to save. 
	 * @throws SaveFileException
	 * 			if a Error occourse while saving.
	 */
	public void doSave(File file) throws SaveFileException
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		GeneratorTypesData generatorTypesData = new GeneratorTypesData();
		ModuleGeneratorTypeData mainModuleGeneratorTypeData = null;
		
		Iterator<GeneratorTypeData> generatorTypesIterator = soundService.retrieveGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext())
		{
			GeneratorTypeData generatorTypeData = generatorTypesIterator.next();
			
			if (generatorTypeData instanceof ModuleGeneratorTypeData)
			{
				ModuleGeneratorTypeData moduleGeneratorTypeData = (ModuleGeneratorTypeData)generatorTypeData;
				
				if (moduleGeneratorTypeData.getIsMainModuleGeneratorType() == true)
				{
					mainModuleGeneratorTypeData = moduleGeneratorTypeData;
				}
			}
			
			generatorTypesData.addGeneratorTypeData(generatorTypeData);
		}
		
		//------------------------------------------------------------------------------------------
		String absolutePath = file.getAbsolutePath();
		
		try
		{
			SaveFileOperationLogic.saveFile(generatorTypesData, 
			                                mainModuleGeneratorTypeData, 
			                                absolutePath);
		}
		catch (Exception ex)
		{
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
	public void doImport(File file) 
	throws LoadFileException
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		float frameRate = SwingMain.getSoundData().getFrameRate();
		String absolutePath = file.getAbsolutePath();

		ModuleGeneratorTypeData mainModuleGeneratorTypeData;
		GeneratorTypesData generatorTypesData = new GeneratorTypesData();

		try
		{
			mainModuleGeneratorTypeData =
				ImportFileOperationLogic.importNoiseCompFile(generatorTypesData, absolutePath, frameRate);
		}
		catch (Exception ex)
		{
			throw new LoadFileException("Import \"" + absolutePath + "\".", ex);
		}
		
		//------------------------------------------------------------------------------------------
		mainModuleGeneratorTypeData.setIsMainModuleGeneratorType(false);
		
		//------------------------------------------------------------------------------------------
		// Check imported generator type is not existing:
		
		GeneratorTypesData addedGeneratorTypesData = new GeneratorTypesData();
		
		Iterator<GeneratorTypeData> generatorTypesIterator = generatorTypesData.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext())
		{
			GeneratorTypeData generatorTypeData = generatorTypesIterator.next();

			// Not a existing generator type?
			if (soundService.checkGeneratorTypeIsExisting(generatorTypeData) == false)
			{
				soundService.addGeneratorType(generatorTypeData);
				 
				addedGeneratorTypesData.addGeneratorTypeData(generatorTypeData);
			}
		}
		
		//------------------------------------------------------------------------------------------
		this.modulesTreeController.importGeneratorTypes(addedGeneratorTypesData.getGeneratorTypesIterator());
		
//		this.selectEditModule(null);
//		
//		List<GeneratorTypeData> generatorTypes = soundService.retrieveGeneratorTypes();
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
	private File getLoadFile()
	{
		return this.appModel.getLoadFile();
	}

	/**
	 * @param file
	 * 			is the File of the last file load or save operation.
	 */
	private void setLoadFile(File file)
	{
		//==========================================================================================
		Preferences userPrefs = PreferencesUtils.getUserPreferences();

		//==========================================================================================
		if (file == null)
		{
			this.appView.setTitle("NoiseComp");
		}
		else
		{
			String fileName = file.getName();
			
			this.appView.setTitle(fileName + " - NoiseComp");
		}
		this.appModel.setLoadFile(file);
		
		//------------------------------------------------------------------------------------------
		String loadFileStr = file.getAbsolutePath();
		
		PreferencesUtils.setValueString(userPrefs, 
		                                PREF_LOAD_FILE, 
		                                loadFileStr);
		
		//==========================================================================================
	}

	/**
	 * @return
	 * 			the File of the last file import operation.
	 */
	private File getImportFile()
	{
		return this.appModel.getImportFile();
	}

	/**
	 * @param file
	 * 			is the File of the last file import operation.
	 */
	private void setImportFile(File file)
	{
		//==========================================================================================
		Preferences userPrefs = PreferencesUtils.getUserPreferences();

		//==========================================================================================
		this.appModel.setImportFile(file);
		
		String importFileStr = file.getAbsolutePath();
		
		PreferencesUtils.setValueString(userPrefs, 
		                                PREF_IMPORT_FILE, 
		                                importFileStr);
		
		//==========================================================================================
	}
	
	/**
	 * @param enable
	 * 			<code>true</code> wenn die Save-Auswahlfunktionen für Files zugelassen werden sollen.
	 */
	public void setEnableSaveFile(boolean enable)
	{
		//==========================================================================================
		this.appView.getFileSaveMenuItem().setEnabled(enable);
		this.appView.getFileSaveButtonView().setEnabled(enable);
		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.swingView.timelineSelect.RemoveTimelineGeneratorListenerInterface#notifyRemoveTimelineGenerator(de.schmiereck.noiseComp.swingView.timelineSelect.TimelinesDrawPanelModel, de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel)
	 */
	@Override
	public void notifyRemoveTimelineGenerator(TimelinesDrawPanelModel timelinesDrawPanelModel,
	                                          TimelineSelectEntryModel timelineSelectEntryModel)
	{
		//==========================================================================================
		SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
		TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();
		
		//------------------------------------------------------------------------------------------
		// Update ModuleData:
		
		Timeline selectedTimeline = timelineSelectEntryModel.getTimeline();
		
		if (selectedTimeline != null)
		{
			timelineManagerLogic.removeTimeline(selectedTimeline);
		}
		
		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.swingView.inputSelect.RemoveInputSelectEntryListenerInterface#notifyRemoveInputSelectEntry(de.schmiereck.noiseComp.timeline.Timeline, de.schmiereck.noiseComp.swingView.inputSelect.InputSelectModel, de.schmiereck.noiseComp.swingView.inputSelect.InputSelectEntryModel)
	 */
	@Override
	public void notifyRemoveInputSelectEntry(Timeline selectedTimeline,
	                                         InputSelectModel inputSelectModel, 
	                                         InputSelectEntryModel inputSelectEntryModel)
	{
		//==========================================================================================
		SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
		TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();
		
		//------------------------------------------------------------------------------------------
		// Update Generator-Input-Data:
		
		InputData inputData = inputSelectEntryModel.getInputData();
		
		if (inputData != null)
		{
			timelineManagerLogic.removeInput(selectedTimeline,
			                                 inputData);
		}
		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.swingView.inputSelect.UpdateInputSelectEntryListenerInterface#notifyUpdateInputSelectEntry(de.schmiereck.noiseComp.timeline.Timeline, de.schmiereck.noiseComp.swingView.inputSelect.InputSelectModel, de.schmiereck.noiseComp.swingView.inputSelect.InputSelectEntryModel)
	 */
	@Override
	public void notifyUpdateInputSelectEntry(Timeline selectedTimeline,
	                                         InputSelectModel inputSelectModel, 
	                                         InputSelectEntryModel inputSelectEntryModel)
	{
		//==========================================================================================
		SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
		TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();
		
		//------------------------------------------------------------------------------------------
		// Update Generator-Input-Data:
		
		InputData inputData = inputSelectEntryModel.getInputData();
		
		if (inputData != null)
		{
			timelineManagerLogic.updateInput(selectedTimeline,
			                                 inputData);
		}
		//==========================================================================================
	}

	/**
	 * Init the startup Model.
	 */
	public void initStartupModel()
	{
		this.appModel.setIsModelChanged(false);
	}

	/**
	 * Double the selected Timeline
	 */
	public void doDoubleTimeline()
	{
		//==========================================================================================
		final SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
		final TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();
		
		//==========================================================================================
		final ModuleGeneratorTypeData editedModuleGeneratorTypeData = this.getEditedModuleGeneratorTypeData();
		final TimelinesDrawPanelModel timelinesDrawPanelModel = this.timelinesDrawPanelController.getTimelinesDrawPanelModel();
		final TimelineEditModel timelineEditModel = this.timelineEditController.getTimelineEditModel();
		
		final SelectedTimelineModel selectedTimelineModel = timelinesDrawPanelModel.getSelectedTimelineModel();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		TimelineSelectEntryModel selectedTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		if (selectedTimelineSelectEntryModel != null)
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Copy timeline:
			
			this.doCreateNewTimeline();
	
			TimelineSelectEntryModel newTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();
			
			{
				Timeline timeline = selectedTimelineSelectEntryModel.getTimeline();
				timelineEditModel.setTimeline(timeline);
				{
					GeneratorTypeData generatorTypeData = timeline.getGenerator().getGeneratorTypeData();
					timelineEditModel.setGeneratorTypeData(generatorTypeData);
				}
			}
			{
				String name = selectedTimelineSelectEntryModel.getName();
				timelineEditModel.setGeneratorName(name + " (new)");
			}
			{
				Float startTimePos = selectedTimelineSelectEntryModel.getStartTimePos();
				timelineEditModel.setGeneratorStartTimePos(startTimePos);
			}
			{
				Float endTimePos = selectedTimelineSelectEntryModel.getEndTimePos();
				timelineEditModel.setGeneratorEndTimePos(endTimePos);
			}
			
			int newEntryModelPos = 
				this.timelinesDrawPanelController.calcTimelineSelectEntryModelPos(newTimelineSelectEntryModel);
			
			this.timelineEditController.doUpdateEditModel(editedModuleGeneratorTypeData, 
			                                              newTimelineSelectEntryModel,
			                                              newEntryModelPos);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Copy:
			Timeline newTimeline = newTimelineSelectEntryModel.getTimeline();
			
			Timeline selectedTimeline = selectedTimelineSelectEntryModel.getTimeline();
			Generator selectedGenerator = selectedTimeline.getGenerator();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Copy inputs:
			{
				Iterator<InputData> inputsIterator = selectedGenerator.getInputsIterator();
				
				if (inputsIterator != null)
				{
					while (inputsIterator.hasNext())
					{
						InputData inputData = (InputData)inputsIterator.next();
						
						Generator inputGenerator = inputData.getInputGenerator();
						
						Timeline inputTimeline = timelineManagerLogic.searchGeneratorTimeline(inputGenerator);
						
	                    InputTypeData inputTypeData = inputData.getInputTypeData(); 
	                    Float floatValue = inputData.getInputValue();
	                    String stringValue = inputData.getInputStringValue();
	                    InputTypeData moduleInputTypeData = inputData.getInputModuleInputTypeData();
	                    
						timelineManagerLogic.addGeneratorInput(newTimeline,
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
				Map<InputData, Timeline> outputTimelines = selectedTimeline.getOutputTimelines();
				Set<Entry<InputData, Timeline>> outputTimelinesEntrySet = outputTimelines.entrySet();
				
				for (Entry<InputData, Timeline> outputTimelineEntry : outputTimelinesEntrySet)
				{
					Timeline outputTimeline = outputTimelineEntry.getValue();
					InputData outputInputData = outputTimelineEntry.getKey();
					
                    InputTypeData inputTypeData = outputInputData.getInputTypeData(); 
                    Float floatValue = outputInputData.getInputValue();
                    String stringValue = outputInputData.getInputStringValue();
                    InputTypeData moduleInputTypeData = outputInputData.getInputModuleInputTypeData();
                    
					timelineManagerLogic.addGeneratorInput(outputTimeline, 
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
	private void doUpdateTimeline()
	{
		//==========================================================================================
		TimelinesDrawPanelModel timelinesDrawPanelModel = this.timelinesDrawPanelController.getTimelinesDrawPanelModel();
//		TimelineSelectEntryModel timelineGeneratorModel = this.timelinesDrawPanelModel.getSelectedTimelineGeneratorModel();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		ModuleGeneratorTypeData editedModuleGeneratorTypeData = this.getEditedModuleGeneratorTypeData();
		
		TimelineSelectEntryModel timelineSelectEntryModel = 
			this.selectedTimelineModel.getSelectedTimelineSelectEntryModel();

		int selectEntryModelPos = 
			this.timelinesDrawPanelController.calcTimelineSelectEntryModelPos(timelineSelectEntryModel);
		
//		Generator generator = 
//			this.retrieveGeneratorOfEditedModuletimelineGeneratorModel.getName());
		
		this.timelineEditController.doUpdateEditModel(editedModuleGeneratorTypeData,
		                                              //generator,
		                                              timelineSelectEntryModel,
		                                              selectEntryModelPos);
		
		this.selectedTimelineModel.setSelectedTimelineSelectEntryModel(timelineSelectEntryModel);

		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		Dimension timelinesDrawPanelDimension = timelinesDrawPanelModel.getDimension();
		
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
	public void doRenameModule(DefaultMutableTreeNode folderTreeNode)
	{
		//==========================================================================================
		String folderName = (String)folderTreeNode.getUserObject();
		
		this.appModel.setEditedModuleTreeNode(folderTreeNode);
		
		this.renameFolderController.doRenameFolder(folderName);
		
		//==========================================================================================
	}

	/**
	 * @param folderTreeNode
	 * 			is the folder Tree-Node.
	 */
	public void doCreateFolder(DefaultMutableTreeNode folderTreeNode)
	{
		//==========================================================================================
		String folderName = (String)folderTreeNode.getUserObject();
		
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
	public void doCreateFolder(String folderName)
	{
		//==========================================================================================
		DefaultMutableTreeNode editedFolderTreeNode = this.appModel.getEditedModuleTreeNode();

		this.modulesTreeController.doCreateFolder(editedFolderTreeNode, 
		                                          folderName);
		
		//==========================================================================================
	}

	/**
	 * @param editedModuleTreeNode
	 * 			is the folder Tree-Node.
	 */
	public void doCutModule(DefaultMutableTreeNode editedModuleTreeNode)
	{
		//==========================================================================================
		this.appModel.setEditedModuleTreeNode(editedModuleTreeNode);
		
		//==========================================================================================
	}

	/**
	 * @param pasteFolderTreeNode
	 * 			is the paste folder or module Tree-Node.
	 */
	public void doPasteModule(DefaultMutableTreeNode pasteFolderTreeNode)
	{
		//==========================================================================================
		DefaultMutableTreeNode cutTreeNode = this.appModel.getEditedModuleTreeNode();
		
		//------------------------------------------------------------------------------------------
		this.modulesTreeController.pasteModule(cutTreeNode,
		                                       pasteFolderTreeNode);
		
		//==========================================================================================
	}

	/**
	 * @param playbackPos
	 * 			is the playbackPos in seconds.
	 */
	public void doSubmitPlaybackPos(float playTimePos)
	{
		//==========================================================================================
		this.playController.doSubmitPlaybackPos(playTimePos);
		
		//==========================================================================================
	}

	/**
	 * @param selectedTimelineSelectEntryModel
	 * @param selectedInputEntry
	 * @param inputTypeData
	 * @param targetTimelineSelectEntryModel
	 */
	public void doUpdateTimelineInput(final TimelineSelectEntryModel selectedTimelineSelectEntryModel,
	                                  final InputEntryModel selectedInputEntry, 
	                                  final InputTypeData inputTypeData,
	                                  final TimelineSelectEntryModel targetTimelineSelectEntryModel)
	{
		//==========================================================================================
		final TimelinesDrawPanelModel timelinesDrawPanelModel = this.timelinesDrawPanelController.getTimelinesDrawPanelModel();
		final SelectedTimelineModel selectedTimelineModel = timelinesDrawPanelModel.getSelectedTimelineModel();
		
		//==========================================================================================
		final InputEditModel inputEditModel = this.inputEditController.getInputEditModel();
		
		//==========================================================================================
		// Replay the user action:
		
		// New Input?
		if (selectedInputEntry.getInputData() == null)
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// New Input:
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			//	Select Input-Type.
			inputEditModel.setInputTypeData(inputTypeData);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			//	Create new Input.
			this.inputEditController.doCreateNewInput(this.inputSelectController);
			
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
		
		this.inputEditController.doSubmit(inputSelectModel, selectedTimeline);
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		//	Select Input.
		selectedTimelineModel.setSelectedInputEntry(selectedInputEntry);
		
		//==========================================================================================
	}
}
