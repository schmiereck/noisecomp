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
import java.util.Set;
import java.util.Map.Entry;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.tree.TreePath;

import de.schmiereck.noiseComp.Version;
import de.schmiereck.noiseComp.file.LoadFileOperationLogic;
import de.schmiereck.noiseComp.file.SaveFileOperationLogic;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData.TicksPer;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.soundData.PlaybackPosChangedListenerInterface;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.noiseComp.soundData.SoundSchedulerLogic;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.SwingMain;
import de.schmiereck.noiseComp.swingView.about.AboutController;
import de.schmiereck.noiseComp.swingView.about.AboutDialogView;
import de.schmiereck.noiseComp.swingView.appModel.AppModel;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.appModel.EditModuleChangedListener;
import de.schmiereck.noiseComp.swingView.appView.AppView;
import de.schmiereck.noiseComp.swingView.inputEdit.InputEditController;
import de.schmiereck.noiseComp.swingView.inputEdit.InputEditModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectController;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectEntryModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectModel;
import de.schmiereck.noiseComp.swingView.inputSelect.RemoveInputSelectEntryListenerInterface;
import de.schmiereck.noiseComp.swingView.modulEdit.ModulEditController;
import de.schmiereck.noiseComp.swingView.modulEdit.ModulEditModel;
import de.schmiereck.noiseComp.swingView.modulInputTypeEdit.ModulInputTypeEditController;
import de.schmiereck.noiseComp.swingView.modulInputTypeEdit.ModulInputTypeEditModel;
import de.schmiereck.noiseComp.swingView.modulInputTypeSelect.ModulInputTypeSelectController;
import de.schmiereck.noiseComp.swingView.modulInputTypeSelect.ModulInputTypeSelectEntryModel;
import de.schmiereck.noiseComp.swingView.modulInputTypeSelect.ModulInputTypeSelectModel;
import de.schmiereck.noiseComp.swingView.modulInputs.ModulInputTypesController;
import de.schmiereck.noiseComp.swingView.modulsTree.DoEditModuleListener;
import de.schmiereck.noiseComp.swingView.modulsTree.ModulesTreeController;
import de.schmiereck.noiseComp.swingView.modulsTree.ModulesTreeModel;
import de.schmiereck.noiseComp.swingView.renameFolder.RenameFolderController;
import de.schmiereck.noiseComp.swingView.timelineEdit.TimelineEditController;
import de.schmiereck.noiseComp.swingView.timelineEdit.TimelineEditModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelinesDrawPanelController;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelinesDrawPanelModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelinesGeneratorsRuleController;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelinesScrollPanelController;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelinesScrollPanelView;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelinesTimeRuleController;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelinesTimeRuleModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.RemoveTimelineGeneratorListenerInterface;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.TimelineEndTimePosChangedListenerInterface;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.TimelineStartTimePosChangedListenerInterface;
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
		   RemoveInputSelectEntryListenerInterface
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * App View
	 */
	private final AppView appView;

	/**
	 * Modules Tree Controller.
	 */
	private final ModulesTreeController modulesTreeController;
	
	/**
	 * Modul-Edit Controller.
	 */
	private final ModulEditController modulEditController;
	
	/**
	 * Modul-Input-Types Controller.
	 */
	private final ModulInputTypesController modulInputTypesController;
	
	/**
	 * Rename-Module Controller.
	 */
	private final RenameFolderController renameFolderController;
	
	/**
	 * Modul-Input-Type Edit Controller.
	 */
	private final ModulInputTypeEditController modulInputTypeEditController;
	
	/**
	 * Modul-Input-Type Select Controller.
	 */
	private final ModulInputTypeSelectController modulInputTypeSelectController;
	
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
	 * Input-Select Controller.
	 */
	private final InputSelectController inputSelectController;

	/**
	 * Input-Edit Controller.
	 */
	private final InputEditController inputEditController;
	
	/**
	 * App Model
	 */
	private final AppModel appModel;
	
	private final AppModelChangedObserver appModelChangedObserver;
	
	private SoundSchedulerLogic soundSchedulerLogic = null;

	private final PlaybackPosChangedListenerInterface	playbackPosChangedListener;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public AppController()
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
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
		
		File fileActionFile = this.appModel.getFileActionFile();
		
		if (fileActionFile == null)
		{
			String fileActionFileStr = 
				PreferencesUtils.getValueString(userPrefs, 
				                                "fileActionFile", 
				                                null);
			
			if (fileActionFileStr != null)
			{
				File file = new File(fileActionFileStr);
				
				this.appModel.setFileActionFile(file);
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
		this.modulEditController = new ModulEditController(this,
		                                                   this.modulesTreeController.getModulesTreeModel(),
		                                                   this.appModelChangedObserver);
		
		this.appView.setModulEditView(this.modulEditController.getModulEditView());
		
		//------------------------------------------------------------------------------------------
		this.modulInputTypesController = new ModulInputTypesController(this,
		                                                               this.appModelChangedObserver);
		
		this.modulInputTypeEditController = modulInputTypesController.getModulInputTypeEditController();
		this.modulInputTypeSelectController = modulInputTypesController.getModulInputTypeSelectController();
		
		//------------------------------------------------------------------------------------------
		this.renameFolderController = new RenameFolderController(this,
		                                                         this.appModelChangedObserver);
		
		//------------------------------------------------------------------------------------------
		this.timelinesTimeRuleController = new TimelinesTimeRuleController();
		
		//------------------------------------------------------------------------------------------
		this.timelinesGeneratorsRuleController = new TimelinesGeneratorsRuleController();
		
		//------------------------------------------------------------------------------------------
		this.timelinesScrollPanelController = new TimelinesScrollPanelController();
		
		TimelinesScrollPanelView timelinesScrollPanelView = this.timelinesScrollPanelController.getTimelinesScrollPanelView();
		
//		this.appView.setTimelineComponent(timelinesScrollPanelView.getScrollPane());
		this.appView.setTimelineComponent(timelinesScrollPanelView);
		
		this.timelinesScrollPanelController.setTimelinesRuleController(this.timelinesTimeRuleController,
		                                                               this.timelinesGeneratorsRuleController);
		
		//------------------------------------------------------------------------------------------
		this.timelinesDrawPanelController = 
			new TimelinesDrawPanelController(//modulesTreeController.getModulesTreeModel(),
			                                 appModelChangedObserver);

		// TODO Change this dynamicaly with listener/notifier.
		this.timelinesDrawPanelController.getTimelinesDrawPanelModel().setMaxUnitIncrementY(this.timelinesScrollPanelController.getTimelinesScrollPanelModel().getGeneratorSizeY());
		
		this.timelinesScrollPanelController.setTimelinesDrawPanelController(this.timelinesDrawPanelController);
		
		this.timelinesDrawPanelController.getTimelinesDrawPanelModel().getTimelineGeneratorModelsChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
					
					Dimension timelinesDrawPanelDimension = timelinesDrawPanelModel.getDimension();
					
					timelinesTimeRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getWidth());
					timelinesGeneratorsRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getHeight());
				}
		 	}
		);

		this.timelinesDrawPanelController.getTimelinesDrawPanelModel().getChangeTimelinesPositionChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					timelinesGeneratorsRuleController.doChangeTimelinesPosition();
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
					TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
					
					float zoomX = timelinesDrawPanelModel.getZoomX();
					
					timelinesTimeRuleController.doChangeZoomX(zoomX);
				}
		 	}
		);
		
		//------------------------------------------------------------------------------------------
		this.inputSelectController = 
			new InputSelectController(this,
			                          this.timelinesDrawPanelController.getTimelinesDrawPanelModel(),
			                          this.appModelChangedObserver);
		
		this.appView.setInputSelectView(this.inputSelectController.getInputSelectView());
		
		//------------------------------------------------------------------------------------------
		this.inputEditController = 
			new InputEditController(this.inputSelectController.getInputSelectModel(),
			                        this.appModelChangedObserver);
		
		this.appView.setInputEditView(this.inputEditController.getInputEditView());
		
		//------------------------------------------------------------------------------------------
		this.timelineEditController = 
			new TimelineEditController(this,
			                           this.timelinesDrawPanelController.getTimelinesDrawPanelModel(),
			                           this.appModelChangedObserver);
		
		this.appView.setTimelineEditView(this.timelineEditController.getTimelineEditView());
		
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
				public void notifyEditModul(ModulGeneratorTypeData modulGeneratorTypeData)
				{
					selectEditModule(modulGeneratorTypeData);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Modules-Tree:
		//------------------------------------------------------------------------------------------
		// Modules-Tree Edited Model changed -> updated Modul-Edit Model:
		
		this.modulesTreeController.getModulesTreeModel().addEditModuleChangedListener
		(
		 	new EditModuleChangedListener()
		 	{
				@Override
				public void notifyEditModulChanged(ModulesTreeModel modulesTreeModel,
				                                   TreePath selectionTreePath)
				{
					ModulGeneratorTypeData modulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
					
					modulEditController.doEditModuleChanged(modulGeneratorTypeData);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Modules-Tree Edited Model changed: Update Modul-Input-Type Select Model:
		
		this.modulesTreeController.getModulesTreeModel().addEditModuleChangedListener
		(
		 	new EditModuleChangedListener()
		 	{
				@Override
				public void notifyEditModulChanged(ModulesTreeModel modulesTreeModel,
				                                   TreePath selectionTreePath)
				{
					ModulInputTypeSelectController modulInputTypeSelectController = modulInputTypesController.getModulInputTypeSelectController();
					
					ModulGeneratorTypeData modulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
					
					modulInputTypeSelectController.doEditModuleChanged(modulGeneratorTypeData);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Modul-Edit:
		//------------------------------------------------------------------------------------------
		// Modul-Edit: Edit-Input-Types Button: Update Modules-Tree-View:
		
		this.modulEditController.getModulEditView().getEditInputTypesButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					modulInputTypesController.getModulInputTypesView().setVisible(true);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Modul-Edit Model changed: Update Modules-Tree-View:
		
		this.modulEditController.getModulEditModel().getModulEditModelChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					ModulGeneratorTypeData editedModulGeneratorTypeData = 
						modulesTreeController.getModulesTreeModel().getEditedModulGeneratorTypeData();
					
					modulesTreeController.updateEditedModulTreeEntry(editedModulGeneratorTypeData);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Modul-Input-Type:
		//------------------------------------------------------------------------------------------
		// Modul-Input-Type Selected Input changed: Update Modul-Input-Type-Edit Model:
		
		this.modulInputTypeSelectController.getInputTypeSelectModel().getSelectedRowNoChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					ModulInputTypeSelectModel modulInputTypeSelectModel = modulInputTypeSelectController.getInputTypeSelectModel();
					
					ModulInputTypeSelectEntryModel selectEntryModel = modulInputTypeSelectModel.getSelectedRow();
					
					InputTypeData inputTypeData;
					
					if (selectEntryModel != null)
					{
						inputTypeData = selectEntryModel.getInputTypeData();
					}
					else
					{
						inputTypeData = null;
					}
					
					modulInputTypeEditController.updateEditedInputType(inputTypeData);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Modul-Input-Type Edit: Create-New Button: Update Modul-Input-Type Select Model:
		
		this.modulInputTypeEditController.getModulInputTypeEditView().getCreateNewButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					modulInputTypeSelectController.doCreateNew();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			 	}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Modul-Input-Type Edit Update-Button: Update Modul-Input-Type Data and Modul-Input-Type Edit-Model:
		// TODO Move to controller.
		this.modulInputTypeEditController.getModulInputTypeEditView().getUpdateButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					modulInputTypeEditController.doUpdateModulInputType(modulesTreeController,
					                                                    modulInputTypeSelectController,
					                                                    appModelChangedObserver);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Modul-Input-Type Edit Remove-Button: Update Modul-Input-Type Data and Modul-Input-Type Select-Model:
		// TODO Move to controller.
		this.modulInputTypeEditController.getModulInputTypeEditView().getRemoveButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					ModulesTreeModel modulesTreeModel = modulesTreeController.getModulesTreeModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					ModulGeneratorTypeData editedModulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
//					ModulInputTypeSelectModel selectModel = modulInputTypeSelectController.getInputTypeSelectModel();
					
//					InputTypeData inputTypeData = modulInputTypeSelectController.getSelectedModulInputType();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					modulInputTypeSelectController.doRemoveSelectedEntry(editedModulGeneratorTypeData);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Modul-Input-Type Edit Model Value changed: Update Input-Select:
		
		this.modulInputTypeEditController.getModulInputTypeEditModel().getInputTypeIDChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					ModulInputTypeSelectModel selectModel = modulInputTypeSelectController.getInputTypeSelectModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Input-Select:
						
					modulInputTypeSelectController.doInputTypeUpdated(selectModel);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Timeline-Edit:
		//------------------------------------------------------------------------------------------
		// Timeline-Edit Remove-Timeline-Button: Update Modul-Data and Timeline-Select-Model:
		
		this.timelineEditController.getTimelineEditView().getRemoveButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					TimelineSelectEntryModel selectedTimelineSelectEntryModel = timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Timeline-Select-Model:

					timelinesDrawPanelModel.removeTimelineSelectEntryModel(selectedTimelineSelectEntryModel);
					
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
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
//					TimelineSelectEntryModel timelineGeneratorModel = timelinesDrawPanelModel.getSelectedTimelineGeneratorModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					ModulGeneratorTypeData editedModulGeneratorTypeData = getEditedModulGeneratorTypeData();
					
					TimelineSelectEntryModel timelineSelectEntryModel = timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();

					int selectEntryModelPos = timelinesDrawPanelController.calcTimelineSelectEntryModelPos(timelineSelectEntryModel);
					
//					Generator generator = 
//						retrieveGeneratorOfEditedModul(timelineGeneratorModel.getName());
					
					timelineEditController.doUpdateEditModel(editedModulGeneratorTypeData,
					                                         //generator,
					                                         timelineSelectEntryModel,
					                                         selectEntryModelPos);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					Dimension timelinesDrawPanelDimension = timelinesDrawPanelModel.getDimension();
					
					// TimelinesTimeRule update.
					timelinesTimeRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getWidth());
					
					// TimelinesGeneratorsRule update.
					timelinesGeneratorsRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getHeight());
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					appModelChangedObserver.notifyAppModelChanged();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
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
					TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					timelinesDrawPanelController.doCreateNew();

					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					Dimension timelinesDrawPanelDimension = timelinesDrawPanelModel.getDimension();
					
					// TimelinesTimeRule update.
					timelinesTimeRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getWidth());
					
					// TimelinesGeneratorsRule update.
					timelinesGeneratorsRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getHeight());
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					appModelChangedObserver.notifyAppModelChanged();
					
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
					
					ModulGeneratorTypeData editedModulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
					
					Timeline selectedTimeline = timelinesDrawPanelController.getSelectedTimeline();
					
					inputEditController.updateEditedInput(editedModulGeneratorTypeData,
					                                      selectedTimeline,
					                                      inputData,
					                                      editInput);
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
					InputSelectModel inputSelectModel = inputSelectController.getInputSelectModel();
					
					Timeline selectedTimeline = timelinesDrawPanelController.getSelectedTimeline();
					
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
					Timeline selectedTimeline = timelinesDrawPanelController.getSelectedTimeline();
					
					inputSelectController.doRemoveSelectedEntry(selectedTimeline);
					
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
					inputSelectController.doCreateNewInput();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			 	}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Models Changed:
		//------------------------------------------------------------------------------------------
		final ModulEditModel modulEditModel = this.modulEditController.getModulEditModel();
		
		//------------------------------------------------------------------------------------------
		modulEditModel.getZoomXChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
					
					float zoomX = modulEditModel.getZoomX();
					
					timelinesDrawPanelModel.setZoomX(zoomX);
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
					JTextField ticksTextField = appView.getTicksTextField();
					
					Float ticksCount = modulEditModel.getTicksCount();
					
					ticksTextField.setText(OutputUtils.makeFloatEditText(ticksCount));
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
				}
		 	}
		);
		{
		 	ModelPropertyChangedListener ticksChangedListener = new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					TimelinesTimeRuleModel timelinesTimeRuleModel = timelinesTimeRuleController.getTimelinesTimeRuleModel();
					
					TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
					
					// Update TimelinesTimeRule
					TicksPer ticksPer = modulEditModel.getTicksPer();
					Float ticksCount = modulEditModel.getTicksCount();
					
					timelinesTimeRuleModel.notifyTicksChangedNotifier(ticksPer, ticksCount);
					
					timelinesDrawPanelModel.notifyTicksChangedNotifier(ticksPer, ticksCount);
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
//		ModulEditModel modulEditModel = this.modulEditController.getModulEditModel();
		
	 	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//		modulEditModel.getModulNameChangedNotifier().addModelPropertyChangedListener
//		(
//		 	appModelChangedObserver
//		);
//		modulEditModel.getModulIsMainChangedNotifier().addModelPropertyChangedListener
//		(
//		 	appModelChangedObserver
//		);
		//------------------------------------------------------------------------------------------
		ModulInputTypeEditModel modulInputTypeEditModel = modulInputTypeEditController.getModulInputTypeEditModel();
		
	 	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//		modulInputTypeEditModel.getInputTypeDataChangedNotifier().addModelPropertyChangedListener
//		(
//		 	appModelChangedObserver
//		);
//		modulInputTypeEditModel.getInputTypeDefaultValueChangedNotifier().addModelPropertyChangedListener
//		(
//		 	appModelChangedObserver
//		);
//		modulInputTypeEditModel.getInputTypeDescriptionChangedNotifier().addModelPropertyChangedListener
//		(
//		 	appModelChangedObserver
//		);
//		modulInputTypeEditModel.getInputTypeIDChangedNotifier().addModelPropertyChangedListener
//		(
//		 	appModelChangedObserver
//		);
//		modulInputTypeEditModel.getInputTypeNameChangedNotifier().addModelPropertyChangedListener
//		(
//		 	appModelChangedObserver
//		);
		//------------------------------------------------------------------------------------------
		TimelineEditModel timelineEditModel = timelineEditController.getTimelineEditModel();
		
	 	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		final TimelinesDrawPanelModel timelinesDrawPanelModel = this.timelinesDrawPanelController.getTimelinesDrawPanelModel();
		
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
		
//		//------------------------------------------------------------------------------------------
//		// Input-Edit-Model InputTypeData changed: Update TimelinesDrawPanel:
//		
//		inputEditModel.getInputTypeDataChangedNotifier().addModelPropertyChangedListener(modelPropertyChangedListener);

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
					// Update Input-Select:
						
					inputSelectController.doInputUpdated();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		
	 	inputEditModel.getValueChangedNotifier().addModelPropertyChangedListener(modelPropertyChangedListener);
		
		//------------------------------------------------------------------------------------------
		// Input-Edit-Model InputGenerator changed: Update TimelinesDrawPanel:
		
	 	inputEditModel.getInputTimelineChangedNotifier().addModelPropertyChangedListener(modelPropertyChangedListener);

		//------------------------------------------------------------------------------------------
		// Input-Edit-Model ModulInputTypeData changed: Update TimelinesDrawPanel:
		
	 	inputEditModel.getModulInputTypeDataChangedNotifier().addModelPropertyChangedListener(modelPropertyChangedListener);

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
	 	timelinesDrawPanelModel.getRemoveTimelineGeneratorNotifier().addRemoveTimelineGeneratorListeners
	 	(
	 	 	this
	 	);
	    //------------------------------------------------------------------------------------------
	 	timelinesDrawPanelModel.getRemoveTimelineGeneratorNotifier().addRemoveTimelineGeneratorListeners
	 	(
	 	 	timelinesTimeRuleController
	 	);
	    //------------------------------------------------------------------------------------------
		// TimelinesGeneratorsRule update.
	 	timelinesDrawPanelModel.getRemoveTimelineGeneratorNotifier().addRemoveTimelineGeneratorListeners
	 	(
	 	 	timelinesGeneratorsRuleController
	 	);
	 	timelinesDrawPanelModel.getSelectedTimelineChangedNotifier().addModelPropertyChangedListener
	 	(
	 	 	new ModelPropertyChangedListener()
	 	 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					TimelineSelectEntryModel selectedTimelineSelectEntryModel = timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
					
					timelinesGeneratorsRuleController.notifySelectedTimelineChanged(selectedTimelineSelectEntryModel);
				}
	 	 	}
	 	);
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
	 	InputSelectModel inputSelectModel = this.inputSelectController.getInputSelectModel();
	 	
	 	inputSelectModel.getRemoveInputSelectEntryNotifier().addRemoveInputSelectEntryListeners
	 	(
	 	 	this
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
					doPlaySound();
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
					doPauseSound();
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
					doStopSound();
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
					modulEditController.doTimelinesZoomIn();
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
					modulEditController.doTimelinesZoomOut();
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
		
	    //------------------------------------------------------------------------------------------
		this.playbackPosChangedListener = new PlaybackPosChangedListenerInterface()
		{
			@Override
			public void notifyPlaybackPosChanged(float actualTime)
			{
				timelinesDrawPanelController.doPlaybackTimeChanged(actualTime);
				
				SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
				
				Timeline outputTimeline = soundSourceLogic.getOutputTimeline();
				
				float generatorEndTimePos = outputTimeline.getGeneratorEndTimePos();
				
				if (actualTime > generatorEndTimePos)
				{
					doStopSound();
				}
			}
		};
		//==========================================================================================
	}

	/**
	 * @param mainModulGeneratorTypeData
	 * 			is the edited modul.
	 */
	public void selectEditModule(ModulGeneratorTypeData mainModulGeneratorTypeData)
	{
		//==========================================================================================
		SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
		//==========================================================================================
		TimelinesDrawPanelModel timelinesDrawPanelModel = this.timelinesDrawPanelController.getTimelinesDrawPanelModel();
		
		//==========================================================================================
		timelinesDrawPanelModel.setSelectedTimelineSelectEntryModel(null);

		this.timelinesDrawPanelController.clearTimelineGenerators();
		
		//------------------------------------------------------------------------------------------
		this.modulesTreeController.getModulesTreeModel().setEditedModulGeneratorTypeData(mainModulGeneratorTypeData);
		
		if (mainModulGeneratorTypeData != null)
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
			List<Timeline> timelines = soundSourceLogic.setMainModulGeneratorTypeData(mainModulGeneratorTypeData);
			
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
		}
		
		//==========================================================================================
	}

	/**
	 * @param generatorName
	 * 			is the Name of Generator.
	 * @return
	 * 			the genaror with given name.
	 */
	public Generator retrieveGeneratorOfEditedModul(String generatorName)
	{
		//==========================================================================================
		ModulGeneratorTypeData modulGeneratorTypeData = 
			modulesTreeController.getModulesTreeModel().getEditedModulGeneratorTypeData();
		
		Generator generator = modulGeneratorTypeData.searchGenerator(generatorName);
		
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
	 * 			the edited Modul-Generator-Type Data.
	 */
	public ModulGeneratorTypeData getEditedModulGeneratorTypeData()
	{
		//==========================================================================================
		ModulesTreeModel modulesTreeModel = this.modulesTreeController.getModulesTreeModel();
		
		ModulGeneratorTypeData editedModulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
		
		//==========================================================================================
		return editedModulGeneratorTypeData;
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
			int option = JOptionPane.showConfirmDialog(this.appView, "Model changed.\nReally Exit?");
	
			if (option == JOptionPane.YES_OPTION)
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

		File fileActionFile = this.getFileActionFile();
		
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
				
				this.setFileActionFile(file);
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
		//==========================================================================================
	}

	/**
	 * Load the Application data.
	 * 
	 * @param file
	 * 			is the file to load. 
	 * @throws LoadFileException 
	 * 			wenn einFehler beim Laden Auftritt.
	 */
	public void doLoad(File file) 
	throws LoadFileException
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		float frameRate = SwingMain.getSoundData().getFrameRate();
		String absolutePath = file.getAbsolutePath();

		ModulGeneratorTypeData mainModulGeneratorTypeData;
		GeneratorTypesData generatorTypesData = new GeneratorTypesData();

		try
		{
			mainModulGeneratorTypeData =
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
		
		this.selectEditModule(mainModulGeneratorTypeData);
		
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
	 * 			wenn ein Fehler beim Speichern Auftritt. 
	 */
	public void doSave(File file) throws SaveFileException
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		GeneratorTypesData generatorTypesData = new GeneratorTypesData();
		ModulGeneratorTypeData mainModulGeneratorTypeData = null;
		
		Iterator<GeneratorTypeData> generatorTypesIterator = soundService.retrieveGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext())
		{
			GeneratorTypeData generatorTypeData = generatorTypesIterator.next();
			
			if (generatorTypeData instanceof ModulGeneratorTypeData)
			{
				ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)generatorTypeData;
				
				if (modulGeneratorTypeData.getIsMainModulGeneratorType() == true)
				{
					mainModulGeneratorTypeData = modulGeneratorTypeData;
				}
			}
			
			generatorTypesData.addGeneratorTypeData(generatorTypeData);
		}
		
		//------------------------------------------------------------------------------------------
		String absolutePath = file.getAbsolutePath();
		
		try
		{
			SaveFileOperationLogic.saveFile(generatorTypesData, 
			                                mainModulGeneratorTypeData, 
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
	 * File-Save.
	 */
	public void doFileSave()
	{
		//==========================================================================================
		this.setEnableSaveFile(false);

		File fileActionFile = this.getFileActionFile();
		
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
				
				this.setFileActionFile(file);
			}
			catch (SaveFileException ex)
			{
				ex.printStackTrace(System.err);

				JOptionPane.showMessageDialog(this.appView,
				                              "Error while saving \"" + file + "\".\n" +
				                              "Message: " + ex,
				                              "Save File Error",
				                              JOptionPane.ERROR_MESSAGE);
			}
		}

		//==========================================================================================
		this.setEnableSaveFile(true);
	}

	/**
	 * @return
	 * 			the File of the last file operation.
	 */
	private File getFileActionFile()
	{
		return this.appModel.getFileActionFile();
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

	/**
	 * @param file
	 * 			is the File of the last file operation.
	 */
	private void setFileActionFile(File file)
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
		this.appModel.setFileActionFile(file);
		
		String fileActionFileStr = file.getAbsolutePath();
		
		PreferencesUtils.setValueString(userPrefs, 
		                                "fileActionFile", 
		                                fileActionFileStr);
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
		// Update Modul-Data:
		
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
		SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
		TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();
		
		//==========================================================================================
		ModulGeneratorTypeData editedModulGeneratorTypeData = this.getEditedModulGeneratorTypeData();
		TimelinesDrawPanelModel timelinesDrawPanelModel = this.timelinesDrawPanelController.getTimelinesDrawPanelModel();
		TimelineEditModel timelineEditModel = this.timelineEditController.getTimelineEditModel();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		TimelineSelectEntryModel selectedTimelineSelectEntryModel = timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		if (selectedTimelineSelectEntryModel != null)
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Copy timeline:
			
			this.timelinesDrawPanelController.doCreateNew();
	
			TimelineSelectEntryModel newTimelineSelectEntryModel = timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
			
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
			
			this.timelineEditController.doUpdateEditModel(editedModulGeneratorTypeData, 
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
	                    InputTypeData modulInputTypeData = inputData.getInputModulInputTypeData();
	                    
						timelineManagerLogic.addInputGenerator(newTimeline,
						                                       inputTimeline,
						                                       inputTypeData, 
						                                       floatValue, stringValue,
						                                       modulInputTypeData);
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
                    InputTypeData modulInputTypeData = outputInputData.getInputModulInputTypeData();
                    
					timelineManagerLogic.addInputGenerator(outputTimeline, 
					                                       newTimeline, 
					                                       inputTypeData, 
					                                       floatValue, 
					                                       stringValue, 
					                                       modulInputTypeData);
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			timelinesDrawPanelModel.setSelectedTimelineSelectEntryModel(newTimelineSelectEntryModel);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			Dimension timelinesDrawPanelDimension = timelinesDrawPanelModel.getDimension();
			
			// TimelinesTimeRule update.
			this.timelinesTimeRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getWidth());
			
			// TimelinesGeneratorsRule update.
			this.timelinesGeneratorsRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getHeight());
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			this.appModelChangedObserver.notifyAppModelChanged();
		}
		//==========================================================================================
	}

	public synchronized void doPlaySound()
	{
		//==========================================================================================
		if (this.soundSchedulerLogic == null)
		{
			SoundData soundData = SwingMain.getSoundData();
			
			this.soundSchedulerLogic = new SoundSchedulerLogic(25, soundData);
			
			this.soundSchedulerLogic.addPlaybackPosChangedListener(this.playbackPosChangedListener);
			
			this.soundSchedulerLogic.startThread();

			this.soundSchedulerLogic.startPlayback();
		}
		else
		{	
			this.soundSchedulerLogic.resumePlayback();
		}
		//==========================================================================================
	}

	public synchronized void doStopSound()
	{
		//==========================================================================================
		if (this.soundSchedulerLogic != null)
		{
			this.soundSchedulerLogic.stopPlayback();
			
			this.soundSchedulerLogic.stopThread();
			
			this.soundSchedulerLogic = null;

			//--------------------------------------------------------------------------------------
			this.timelinesDrawPanelController.doPlaybackTimeChanged(0.0F);
		}
		//==========================================================================================
	}

	public synchronized void doPauseSound()
	{
		//==========================================================================================
		if (this.soundSchedulerLogic != null)
		{
			this.soundSchedulerLogic.pausePlayback();
		}
		//==========================================================================================
	}

	/**
	 * Do Rename Module.
	 */
	public void doRenameModule()
	{
		//==========================================================================================
		this.renameFolderController.doRenameModule();
		
		//==========================================================================================
	}
}
