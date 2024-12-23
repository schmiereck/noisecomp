/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineEdit;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeInfoData;
import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeInfoData;
import de.schmiereck.noiseComp.soundScheduler.SoundDataLogic;
import de.schmiereck.noiseComp.soundSource.SoundSourceData;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.appView.AppView;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel;
import de.schmiereck.noiseComp.swingView.utils.InputUtils;
import de.schmiereck.noiseComp.timeline.Timeline;
import de.schmiereck.noiseComp.timeline.TimelineManagerLogic;

/**
 * <p>
 * 	Timeline-Edit Controller.
 * </p>
 * 
 * @author smk
 * @version <p>07.09.2010:	created, smk</p>
 */
public class TimelineEditController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * App Controller.
	 */
	private final AppController appController;

	private final SoundSourceLogic soundSourceLogic;

	private final SoundDataLogic soundDataLogic;

	/**
	 * Timeline-Edit View.
	 */
	private final TimelineEditView	timelineEditView;

	/**
	 * Timeline-Edit Model.
	 */
	private final TimelineEditModel	timelineEditModel;

	/**
	 * Timelines-Draw-Panel Model.
	 */
	private final TimelinesDrawPanelModel timelinesDrawPanelModel;
	
	private final AppModelChangedObserver appModelChangedObserver;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelinesDrawPanelModel 
	 * 			is the App Controller.
	 * @param timelinesDrawPanelModel 
	 * 			is the Timeline Draw-Panel Model.
	 * @param appModelChangedObserver 
	 */
	public TimelineEditController(final AppController appController,
								  final SoundSourceLogic soundSourceLogic,
								  final SoundDataLogic soundDataLogic,
	                              final TimelinesDrawPanelModel timelinesDrawPanelModel,
	                              final AppModelChangedObserver appModelChangedObserver)
	{
		//==========================================================================================
		this.appController = appController;
		this.soundSourceLogic = soundSourceLogic;
		this.soundDataLogic = soundDataLogic;

		this.timelineEditModel = new TimelineEditModel();
		this.timelineEditView = new TimelineEditView(this.timelineEditModel);

		//------------------------------------------------------------------------------------------
		this.timelinesDrawPanelModel = timelinesDrawPanelModel;
		
		this.appModelChangedObserver = appModelChangedObserver;
		
		//==========================================================================================
		final SelectedTimelineModel selectedTimelineModel = this.timelinesDrawPanelModel.getSelectedTimelineModel();
		
		//------------------------------------------------------------------------------------------
		// Selected Timeline changed: Update Timeline-Edit Model:
		
		//------------------------------------------------------------------------------------------
		selectedTimelineModel.getSelectedTimelineChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged() {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					Timeline timeline;
					List<GeneratorTypeSelectItem> generatorTypeSelectItems;
					GeneratorTypeInfoData generatorTypeInfoData;
					String generatorName;
					Float generatorStartTimePos;
					Float generatorEndTimePos;

					TimelineSelectEntryModel timelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();
					
					if (timelineSelectEntryModel != null) {
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						timeline = timelineSelectEntryModel.getTimeline();
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						{
							ModuleGeneratorTypeInfoData editedModuleGeneratorTypeInfoData = appController.getEditedModuleGeneratorTypeInfoData();
							
							generatorTypeSelectItems = new Vector<>();
							List<GeneratorTypeInfoData> generatorTypes = appController.retrieveGeneratorTypesForSelect();

							if (generatorTypes != null) {
								GeneratorTypeSelectItem noSelectItem = new GeneratorTypeSelectItem(null);
								generatorTypeSelectItems.add(noSelectItem);

								for (GeneratorTypeInfoData itemGeneratorTypeInfoData : generatorTypes) {
									Class< ? extends Generator> generatorClass = itemGeneratorTypeInfoData.getGeneratorClass();
									
									// Not a folder?
									//if (itemGeneratorTypeData instanceof ModuleGeneratorTypeData)
									//if (folderPath.startsWith(StartupService.MODULE_FOLDER_PATH))
									if (generatorClass != null) {
										// Is not the edited Module as Generator-Type?
										if (editedModuleGeneratorTypeInfoData != itemGeneratorTypeInfoData) {
											generatorTypeSelectItems.add(new GeneratorTypeSelectItem(itemGeneratorTypeInfoData));
										}
									}
								}
							}
						}
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						generatorName = timelineSelectEntryModel.getName();
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						{
							Generator generator = appController.retrieveGeneratorOfEditedModule(generatorName);
							
							if (generator != null) {
								generatorTypeInfoData = generator.getGeneratorTypeData();
							} else {
								generatorTypeInfoData = null;
							}
						}
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						generatorStartTimePos = timelineSelectEntryModel.getStartTimePos();
						generatorEndTimePos = timelineSelectEntryModel.getEndTimePos();
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					} else {
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						timeline = null;
						generatorTypeSelectItems = null;
						generatorTypeInfoData = null;
						generatorName = null;
						generatorStartTimePos = null;
						generatorEndTimePos = null;
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}

					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					timelineEditModel.setTimeline(timeline);
					timelineEditModel.setGeneratorTypeSelectItems(generatorTypeSelectItems);
					timelineEditModel.setGeneratorTypeInfoData(generatorTypeInfoData);
					timelineEditModel.setGeneratorName(generatorName);
					timelineEditModel.setGeneratorStartTimePos(generatorStartTimePos);
					timelineEditModel.setGeneratorEndTimePos(generatorEndTimePos);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.timelineEditModel.getGeneratorTypeDataChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
			{
				@Override
				public void notifyModelPropertyChanged()
				{
					final GeneratorTypeInfoData generatorTypeInfoData = timelineEditModel.getGeneratorTypeInfoData();

					final JComboBox generatorTypeComboBox = timelineEditView.getGeneratorTypeComboBox();
					
					if (Objects.nonNull(generatorTypeInfoData)) {
						generatorTypeComboBox.setEnabled(false);
					} else {
						generatorTypeComboBox.setEnabled(true);
					}
				}
			}
		);
		//------------------------------------------------------------------------------------------
//		// Timeline-Edit Update-Button: Update Generator, Update Timeline-Edit Model and Timeline-Generator Model:
//		
//		this.timelineEditView.getUpdateButton().addActionListener
//		(
//		 	new ActionListener()
//		 	{
//				@Override
//				public void actionPerformed(ActionEvent e)
//				{
//					doUpdateEditModel(appController, timelinesDrawPanelModel);
//				}
//		 	}
//		);
//		//------------------------------------------------------------------------------------------
//		// Timeline-Edit GeneratorStartTimePosChanged: Notify Timeline:
//		
//		this.timelineEditModel.getGeneratorStartTimePosChangedNotifier().addModelPropertyChangedListener
//		(
//		 	new ModelPropertyChangedListener()
//		 	{
//				@Override
//				public void notifyModelPropertyChanged()
//				{
//					TimelineSelectEntryModel timelineSelectEntryModel = timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
//					
//					if (timelineSelectEntryModel != null)
//					{
//						String name = timelineSelectEntryModel.getName();
//						
//						Generator generator = appController.retrieveGeneratorOfEditedModulename);
//						
//						generator
//					}
//				}
//		 	}
//		);
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineEditView}.
	 */
	public TimelineEditView getTimelineEditView()
	{
		return this.timelineEditView;
	}

	/**
	 * Timeline-Edit Update-Button: Update Generator, Update Timeline-Edit Model and Timeline-Generator Model:
	 * 
	 * @param editedModuleGeneratorTypeData
	 * 			is the editedModuleGeneratorTypeData.
	 * @param timelineSelectEntryModel
	 * 			is the TimelineSelectEntryModel.
	 * @param entryModelPos
	 * 			is the position of the timeline in the list of timelines.
	 */
	public void doUpdateEditModel(final SoundSourceData soundSourceData,
								  final ModuleGeneratorTypeInfoData editedModuleGeneratorTypeData,
								  //final Generator generator,
								  final TimelineSelectEntryModel timelineSelectEntryModel,
								  int entryModelPos)
	{
		//==========================================================================================
		//SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
		TimelineManagerLogic timelineManagerLogic = this.soundSourceLogic.getTimelineManagerLogic();
		
		//==========================================================================================
//		TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
//		TimelineSelectEntryModel timelineSelectEntryModel = timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
//		TimelineEditView timelineEditView = timelineEditController.getTimelineEditView();
		
		if (timelineSelectEntryModel != null) {
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			final GeneratorTypeInfoData generatorTypeInfoData;
			{
				final GeneratorTypeSelectItem generatorTypeSelectItem =
					(GeneratorTypeSelectItem)this.timelineEditView.getGeneratorTypeComboBox().getSelectedItem();
                if (Objects.nonNull(generatorTypeSelectItem)) {
					generatorTypeInfoData = generatorTypeSelectItem.getGeneratorTypeData();
				} else {
					generatorTypeInfoData = null;
				}
			}
			final String generatorName = this.timelineEditView.getGeneratorNameTextField().getText();
			final Float generatorStartTimePos = InputUtils.makeFloatValue(this.timelineEditView.getGeneratorStartTimePosTextField().getText());
			final Float generatorEndTimePos = InputUtils.makeFloatValue(this.timelineEditView.getGeneratorEndTimePosTextField().getText());
			
			if (generatorTypeInfoData == null) {
//				throw new RuntimeException("GeneratorTypeData not selected.");
				AppView appView = this.appController.getAppView();
			
				JOptionPane.showMessageDialog(appView, "Generator-Type is not selected.");
			} else {
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Update Timeline-Generator:

				final Timeline timeline;

				if (Objects.isNull(timelineSelectEntryModel.getTimeline())) {
					//Float soundFrameRate = SwingMain.getSoundData().getFrameRate();
					final float soundFrameRate = this.soundDataLogic.getFrameRate();

					timeline = timelineManagerLogic.createTimeline(soundSourceData,
							generatorTypeInfoData, soundFrameRate, generatorName, entryModelPos);
				} else {
					timeline = timelineSelectEntryModel.getTimeline();
					timelineManagerLogic.updateName(timeline, generatorName);
				}
	
				timelineManagerLogic.updateTimePos(soundSourceData, timeline,
				                                   generatorStartTimePos, 
				                                   generatorEndTimePos);
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Update Timeline-Edit Model:
				
				this.timelineEditModel.setTimeline(timeline);
				this.timelineEditModel.setGeneratorTypeInfoData(generatorTypeInfoData);
				this.timelineEditModel.setGeneratorName(generatorName);
				this.timelineEditModel.setGeneratorStartTimePos(generatorStartTimePos);
				this.timelineEditModel.setGeneratorEndTimePos(generatorEndTimePos);
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Update Timeline-Model:
				
				timelineSelectEntryModel.setTimeline(timeline);
	//			timelineSelectEntryModel.setGeneratorTypeData(generatorTypeData);
				timelineSelectEntryModel.setName(generatorName);
				timelineSelectEntryModel.setStartTimePos(generatorStartTimePos);
				timelineSelectEntryModel.setEndTimePos(generatorEndTimePos);
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				this.appModelChangedObserver.notifyAppModelChanged();
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			}
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineEditModel}.
	 */
	public TimelineEditModel getTimelineEditModel()
	{
		return this.timelineEditModel;
	}
}
