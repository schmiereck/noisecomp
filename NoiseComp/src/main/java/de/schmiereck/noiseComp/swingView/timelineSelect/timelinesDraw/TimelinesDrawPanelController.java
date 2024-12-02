/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw;

import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.util.List;

import de.schmiereck.noiseComp.soundSource.SoundSourceData;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesAddListenerInterface;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesChangePositionsListenerInterface;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesModel;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesRemoveListenerInterface;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesUpdateListenerInterface;
import de.schmiereck.noiseComp.swingView.appModel.InputEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntriesModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.DoChangeTimelinesPositionListenerInterface;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.RemoveTimelineGeneratorListenerInterface;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesGeneratorsRule.TimelinesGeneratorsRuleController;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimeMarkerSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimelinesTimeRuleController;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimelinesTimeRuleModel;
import de.schmiereck.noiseComp.timeline.Timeline;
import de.schmiereck.noiseComp.timeline.TimelineContentChangedListenerInterface;
import de.schmiereck.noiseComp.timeline.TimelineManagerLogic;

/**
 * <p>
 * 	Timelines Draw-Panel Controller.
 * </p>
 * 
 * @author smk
 * @version <p>07.09.2010:	created, smk</p>
 */
public class TimelinesDrawPanelController 
implements TimelineContentChangedListenerInterface, 
		   ModelPropertyChangedListener
{
	//**********************************************************************************************
	// Fields:
	
//	private final ModulesTreeModel modulesTreeModel;

	private final SoundSourceLogic soundSourceLogic;

	private final TimelinesDrawPanelModel timelinesDrawPanelModel;
	
	private final TimelinesDrawPanelView timelinesDrawPanelView;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * @param selectedTimelineModel
	 * 			is the Selected-Timeline Model.
	 * @param timelinesTimeRuleModel
	 * 			is the Timelines-Time-Rule Model.
	 */
	public TimelinesDrawPanelController(final AppController appController,
										final SoundSourceLogic soundSourceLogic,
	                                    //final ModulesTreeModel modulesTreeModel,
	                                    //InputEditModel inputEditModel)
	                                    final AppModelChangedObserver appModelChangedObserver,
	                                    final TimelineSelectEntriesModel timelineSelectEntriesModel,
	                                    final SelectedTimelineModel selectedTimelineModel,
	                                    final TimelinesTimeRuleModel timelinesTimeRuleModel)
	{
		//==========================================================================================
		this.soundSourceLogic = soundSourceLogic;
		//this.modulesTreeModel = modulesTreeModel;

		this.timelinesDrawPanelModel = new TimelinesDrawPanelModel(appModelChangedObserver,
		                                                           timelineSelectEntriesModel,
		                                                           selectedTimelineModel);
		
	    this.timelinesDrawPanelView = new TimelinesDrawPanelView(this.timelinesDrawPanelModel,
	                                                             timelinesTimeRuleModel);
	    
	    //------------------------------------------------------------------------------------------
//	    this.timelinesDrawPanelView.addDoTimelineSelectedListeners
//	    (
//	     	new DoTimelineSelectedListenerInterface()
//	     	{
//				@Override
//				public void timelineSelected(TimelineSelectEntryModel timelineGeneratorModel)
//				{
//					timelinesDrawPanelModel.setSelectedTimelineGeneratorModel(timelineGeneratorModel);
//				}
//	     	}
//	    );
//	    //------------------------------------------------------------------------------------------
//	    inputEditModel.getValueChangedNotifier().addModelPropertyChangedListener
//	    (
//	     	this.timelineGeneratorModelChangedListener
//	    );
	    //------------------------------------------------------------------------------------------
	    selectedTimelineModel.addChangeTimelinesPositionListeners
	    (
	     	new DoChangeTimelinesPositionListenerInterface()
	     	{
				@Override
				public void changeTimelinesPosition(TimelineSelectEntryModel selectedTimelineSelectEntryModel,
													TimelineSelectEntryModel newTimelineSelectEntryModel)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					if ((selectedTimelineSelectEntryModel != null) &&
						(newTimelineSelectEntryModel != null))
					{
						doChangeTimelinesPosition(selectedTimelineSelectEntryModel,
						                          newTimelineSelectEntryModel);
					}
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
	     	}
	    );
	    //------------------------------------------------------------------------------------------
	    timelineSelectEntriesModel.getRemoveTimelineGeneratorNotifier().addRemoveTimelineGeneratorListeners
	    (
	     	new RemoveTimelineGeneratorListenerInterface()
	     	{
				@Override
				public void notifyRemoveTimelineGenerator(SoundSourceData soundSourceData, TimelinesDrawPanelModel timelinesDrawPanelModel,
                                                          TimelineSelectEntryModel timelineSelectEntryModel)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					SelectedTimelineModel selectedTimelineModel = timelinesDrawPanelModel.getSelectedTimelineModel();
					
					if (timelineSelectEntryModel == selectedTimelineModel.getSelectedTimelineSelectEntryModel())
					{
						selectedTimelineModel.setSelectedTimelineSelectEntryModel(null);
					}
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
	     	}
	    );
	    //------------------------------------------------------------------------------------------
	    TimeMarkerSelectEntryModel playTimeMarkerSelectEntryModel = timelinesTimeRuleModel.getPlayTimeMarkerSelectEntryModel();
	    
	    playTimeMarkerSelectEntryModel.getTimeMarkerChangedNotifier().addModelPropertyChangedListener
	    (
	     	new ModelPropertyChangedListener()
	     	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					timelinesDrawPanelView.repaint();
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
	     	}
	    );
		//------------------------------------------------------------------------------------------
		{
			selectedTimelineModel.getSelectedInputEntryChangedViewNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
				{
					@Override
					public void notifyModelPropertyChanged()
					{
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						timelinesDrawPanelView.repaint();
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			selectedTimelineModel.getHighlightedInputEntryChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
				{
					@Override
					public void notifyModelPropertyChanged()
					{
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						timelinesDrawPanelView.repaint();
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			selectedTimelineModel.getInputEntryTargetModelChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
				{
					@Override
					public void notifyModelPropertyChanged()
					{
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						timelinesDrawPanelView.repaint();
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			InputEntriesModel inputEntriesModel = selectedTimelineModel.getInputEntriesModel();
			
			inputEntriesModel.getInputEntriesAddNotifier().addInputEntriesAddListeners
			(
			 	new InputEntriesAddListenerInterface()
				{
					@Override
					public void notifyAddInputEntry(int entryPos,
					                                InputEntryModel inputEntryModel)
					{
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						timelinesDrawPanelView.repaint();
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			InputEntriesModel inputEntriesModel = selectedTimelineModel.getInputEntriesModel();
			
			inputEntriesModel.getInputEntriesRemoveNotifier().addInputEntriesRemoveListeners
			(
			 	new InputEntriesRemoveListenerInterface()
				{
					@Override
					public void notifyRemoveInputEntry(int inputNo,
					                                   InputEntryModel inputEntryModel)
					{
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						timelinesDrawPanelView.repaint();
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			InputEntriesModel inputEntriesModel = selectedTimelineModel.getInputEntriesModel();
			
			inputEntriesModel.getInputEntriesUpdateNotifier().addInputEntriesUpdateListeners
			(
			 	new InputEntriesUpdateListenerInterface()
				{
					@Override
					public void notifyUpdateInputEntry(InputEntryModel inputEntryModel)
					{
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						timelinesDrawPanelView.repaint();
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
						final InputPosEntriesModel inputPosEntriesModel = selectedTimelineModel.getInputPosEntriesModel(); 
						
						InputPosEntriesModel selectedInputPosEntry = inputPosEntriesModel.searchInputPosEntry(selectedInputEntryModel);
						InputPosEntriesModel targetInputPosEntry = inputPosEntriesModel.searchInputPosEntry(targetInputEntryModel);
						
						InputPosEntriesModel groupInputPosEntriesModel = selectedInputPosEntry.getGroupInputPosEntriesModel();
						
						List<InputPosEntriesModel> groupInputPosEntries = groupInputPosEntriesModel.getInputPosEntries();
						
						int selectedInputPos = groupInputPosEntries.indexOf(selectedInputPosEntry);
						int targetInputPos = groupInputPosEntries.indexOf(targetInputPosEntry);
						
						groupInputPosEntries.set(selectedInputPos, targetInputPosEntry);
						groupInputPosEntries.set(targetInputPos, selectedInputPosEntry);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						timelinesDrawPanelView.repaint();
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			// Add listener to components that can bring up popup menus.
			MouseListener popupListener = new TimelinesDrawMouseAdapter(appController,
			                                                             this,
			                                                             this.timelinesDrawPanelModel,
			                                                             this.timelinesDrawPanelView);
			//output.addMouseListener(popupListener);
			//menuBar.addMouseListener(popupListener);
			this.timelinesDrawPanelView.addMouseListener(popupListener);
		}
	    //==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #timelinesDrawPanelModel}.
	 */
	public TimelinesDrawPanelModel getTimelinesDrawPanelModel()
	{
		return this.timelinesDrawPanelModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #timelinesDrawPanelView}.
	 */
	public TimelinesDrawPanelView getTimelinesDrawPanelView()
	{
		return this.timelinesDrawPanelView;
	}

	/**
	 * Clear Timeline-Generators.
	 */
	public void clearTimelineGenerators()
	{
		//==========================================================================================
		TimelineSelectEntriesModel timelineSelectEntriesModel = 
			this.timelinesDrawPanelModel.getTimelineSelectEntriesModel();
		
		//==========================================================================================
		// Remove listeners if timeline is removed:
		
		ModelPropertyChangedListener modelChangedListener = this.timelinesDrawPanelModel.getTimelineGeneratorModelChangedListener();
		
		List<TimelineSelectEntryModel> timelineSelectEntryModels = timelineSelectEntriesModel.getTimelineSelectEntryModels();
		
		for (TimelineSelectEntryModel timelineSelectEntryModel : timelineSelectEntryModels)
		{
			timelineSelectEntryModel.getNameChangedNotifier().removeModelPropertyChangedListener(modelChangedListener);
			timelineSelectEntryModel.getStartTimePosChangedNotifier().removeModelPropertyChangedListener(modelChangedListener);
			timelineSelectEntryModel.getEndTimePosChangedNotifier().removeModelPropertyChangedListener(modelChangedListener);
		}
		
		//------------------------------------------------------------------------------------------
		timelineSelectEntriesModel.clearTimelineGenerators();

		//==========================================================================================
	}

	/**
	 * @param timelineSelectEntryModel
	 * 			is a Timeline-Generator Model.
	 * @param timelinePos
	 * 			is the timeline Pos to insert the Timeline.
	 */
	public void addTimelineSelectEntryModel(int timelinePos,
	                                        TimelineSelectEntryModel timelineSelectEntryModel)
	{
		//==========================================================================================
		TimelineSelectEntriesModel timelineSelectEntriesModel = 
			this.timelinesDrawPanelModel.getTimelineSelectEntriesModel();
		
		//==========================================================================================
		timelineSelectEntriesModel.addTimelineSelectEntryModel(timelinePos,
		                                                       timelineSelectEntryModel);
		
		ModelPropertyChangedListener modelChangedListener = this.timelinesDrawPanelModel.getTimelineGeneratorModelChangedListener();
		
		timelineSelectEntryModel.getNameChangedNotifier().addModelPropertyChangedListener(modelChangedListener);
		timelineSelectEntryModel.getStartTimePosChangedNotifier().addModelPropertyChangedListener(modelChangedListener);
		timelineSelectEntryModel.getEndTimePosChangedNotifier().addModelPropertyChangedListener(modelChangedListener);
		
		// TODO remove listeners if timeline is removed.
		//==========================================================================================
	}

	/**
	 * @return
	 * 			the Selected Timeline.
	 */
	public Timeline getSelectedTimeline()
	{
		//==========================================================================================
		Timeline retSelectedTimeline;
		
		SelectedTimelineModel selectedTimelineModel = this.timelinesDrawPanelModel.getSelectedTimelineModel();
		
		TimelineSelectEntryModel selectedTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();

		if (selectedTimelineSelectEntryModel != null)
		{
			retSelectedTimeline = selectedTimelineSelectEntryModel.getTimeline();
		}
		else
		{
			retSelectedTimeline = null;
		}
		
		//==========================================================================================
		return retSelectedTimeline;
	}
	
	/**
	 * 
	 * @param firstTimelineSelectEntryModel
	 * 			is the first Timeline-Generator Model.
	 * @param secondTimelineSelectEntryModel
	 * 			is the second Timeline-Generator Model.
	 */
	public void doChangeTimelinesPosition(TimelineSelectEntryModel firstTimelineSelectEntryModel,
	                                      TimelineSelectEntryModel secondTimelineSelectEntryModel)
	{
		//==========================================================================================
		//SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
		TimelineManagerLogic timelineManagerLogic = this.soundSourceLogic.getTimelineManagerLogic();
		
		//==========================================================================================
		TimelineSelectEntriesModel timelineSelectEntriesModel = 
			this.timelinesDrawPanelModel.getTimelineSelectEntriesModel();
		
		//==========================================================================================
//		int firstTimelinePos = firstTimelineSelectEntryModel.getTimelinePos();
//		int secondTimelinePos = secondTimelineSelectEntryModel.getTimelinePos();
		int firstTimelinePos = timelineSelectEntriesModel.getTimelineSelectEntryPos(firstTimelineSelectEntryModel);
		int secondTimelinePos = timelineSelectEntriesModel.getTimelineSelectEntryPos(secondTimelineSelectEntryModel);
		//------------------------------------------------------------------------------------------
		// Update edited Module Data:
		
//		TimelineSelectEntryModel selectedTimelineSelectEntryModel = this.timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
		
//		ModuleGeneratorTypeData editedModuleGeneratorTypeData = this.modulesTreeModel.getEditedModuleGeneratorTypeData();
		
//		Generator firstGenerator = selectedTimelineGeneratorModel.getGenerator();
//		Generator secondGenerator = newTimelineGeneratorModel.getGenerator();
		
		timelineManagerLogic.switchTracksByPos(firstTimelinePos,
                                               secondTimelinePos);
		
//		editedModuleGeneratorTypeData.switchTracksByPos(firstTimelinePos,
//		                                               secondTimelinePos);
		
		//------------------------------------------------------------------------------------------
		// Update Timeline-Draw Model:
		
//		List<TimelineSelectEntryModel> timelineGeneratorModels = this.timelinesDrawPanelModel.getTimelineGeneratorModels();
//		
//		timelineGeneratorModels.set(firstTimelinePos, secondTimelineGeneratorModel);
//		timelineGeneratorModels.set(secondTimelinePos, firstTimelineGeneratorModel);
		timelineSelectEntriesModel.changeTimelinesPosition(firstTimelinePos,
		                                                   secondTimelinePos);
		
//		//------------------------------------------------------------------------------------------
//		// Update Timeline-Generator Models:
//		
//		firstTimelineGeneratorModel.setTimelinePos(secondTimelinePos);
//		secondTimelineGeneratorModel.setTimelinePos(firstTimelinePos);
//		
		//------------------------------------------------------------------------------------------
		// Should be triggered by timelinesDrawPanel Model update.
//		this.timelinesDrawPanelView.repaint();
		
		// Update TimelinesGeneratorsRule.
		
		//==========================================================================================
	}

	public void doCreateNewTimeline(final TimelinesTimeRuleController timelinesTimeRuleController,
									final TimelinesGeneratorsRuleController timelinesGeneratorsRuleController,
									final AppModelChangedObserver appModelChangedObserver) {
		//==========================================================================================
		final TimelinesDrawPanelModel timelinesDrawPanelModel = this.getTimelinesDrawPanelModel();

		final TimelineSelectEntriesModel timelineSelectEntriesModel =  this.timelinesDrawPanelModel.getTimelineSelectEntriesModel();
		
		//==========================================================================================
		final SelectedTimelineModel selectedTimelineModel = timelinesDrawPanelModel.getSelectedTimelineModel();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		final int timelinePos;
		final Float startTimePos;
		final Float endTimePos;

		final TimelineSelectEntryModel selectedTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();
		
		if (selectedTimelineSelectEntryModel != null) {
			timelinePos = this.calcTimelineSelectEntryModelPos(selectedTimelineSelectEntryModel) + 1;
			startTimePos = selectedTimelineSelectEntryModel.getStartTimePos();
			endTimePos = selectedTimelineSelectEntryModel.getEndTimePos();
		} else {
			timelinePos = timelineSelectEntriesModel.getTimelineSelectEntryModels().size();
			startTimePos = 0.0F;
			endTimePos = 1.0F;
		}
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Update Timeline-Select-Model:

		final TimelineSelectEntryModel timelineSelectEntryModel =
			new TimelineSelectEntryModel(null,
//			                             timelinePos,
			                             "new-%d".formatted(timelinePos),
			                             startTimePos,
			                             endTimePos);
		
		this.addTimelineSelectEntryModel(timelinePos,
		                                 timelineSelectEntryModel);
		
		selectedTimelineModel.setSelectedTimelineSelectEntryModel(timelineSelectEntryModel);
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		final Dimension timelinesDrawPanelDimension = timelinesDrawPanelModel.getDimension();
		
		// TimelinesTimeRule update.
		timelinesTimeRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getWidth());
		
		// TimelinesGeneratorsRule update.
		timelinesGeneratorsRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getHeight());
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		appModelChangedObserver.notifyAppModelChanged();

//		
//		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//		final Dimension timelinesDrawPanelDimension = timelinesDrawPanelModel.getDimension();
//		
//		// TimelinesTimeRule update.
//		timelinesTimeRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getWidth());
//		
//		// TimelinesGeneratorsRule update.
//		timelinesGeneratorsRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getHeight());
//		
//		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//		appModelChangedObserver.notifyAppModelChanged();
		
		//==========================================================================================
	}
//
//	public void doTimelinesZoomIn()
//	{
//		//==========================================================================================
//		float zoomX = this.timelinesDrawPanelModel.getZoomX();
//		
//		zoomX *= 1.5F;
//		
//		this.timelinesDrawPanelModel.setZoomX(zoomX);
//
//		//==========================================================================================
//	}
//
//	public void doTimelinesZoomOut()
//	{
//		//==========================================================================================
//		float zoomX = this.timelinesDrawPanelModel.getZoomX();
//		
//		zoomX /= 1.5F;
//		
//		this.timelinesDrawPanelModel.setZoomX(zoomX);
//
//		//==========================================================================================
//	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.timeline.TimelineContentChangedListenerInterface#notifyTimelineContentChanged(long, long)
	 */
	@Override
	public void notifyTimelineContentChanged(long bufferStart, long bufferEnd)
	{
		//==========================================================================================
		this.timelinesDrawPanelView.repaint();
		//rule.repaint();
		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener#notifyModelPropertyChanged()
	 */
	@Override
	public void notifyModelPropertyChanged()
	{
		//==========================================================================================
		this.timelinesDrawPanelView.repaint();
		
		//==========================================================================================
	}

	/**
	 * @param searchedTimelineSelectEntryModel
	 * 			is the searched TimelineSelectEntryModel;
	 * @return
	 * 			<code>null</code> if no next TimelineSelectEntryModel found.
	 */
	public TimelineSelectEntryModel calcNextTimelineSelectEntryModel(TimelineSelectEntryModel searchedTimelineSelectEntryModel)
	{
		//==========================================================================================
		TimelineSelectEntriesModel timelineSelectEntriesModel = 
			this.timelinesDrawPanelModel.getTimelineSelectEntriesModel();
		
		//==========================================================================================
		TimelineSelectEntryModel retTimelineSelectEntryModel;
		
		List<TimelineSelectEntryModel> timelineSelectEntryModels = 
			timelineSelectEntriesModel.getTimelineSelectEntryModels();
		
		boolean found = false;
		retTimelineSelectEntryModel = null;
		
		for (TimelineSelectEntryModel timelineSelectEntryModel : timelineSelectEntryModels)
		{
			if (found == true)
			{
				retTimelineSelectEntryModel = timelineSelectEntryModel;
				break;
			}
			if (timelineSelectEntryModel == searchedTimelineSelectEntryModel)
			{
				found = true;
			}
		}
		
		//==========================================================================================
		return retTimelineSelectEntryModel;
	}

	/**
	 * @param searchedTimelineSelectEntryModel
	 * 			is the searched TimelineSelectEntryModel;
	 * @return
	 * 			the pos of searched TimelineSelectEntryModel.
	 */
	public int calcTimelineSelectEntryModelPos(TimelineSelectEntryModel searchedTimelineSelectEntryModel)
	{
		//==========================================================================================
		TimelineSelectEntriesModel timelineSelectEntriesModel = 
			this.timelinesDrawPanelModel.getTimelineSelectEntriesModel();
		
		//==========================================================================================
		int retSelectEntryModelPos;
		
		List<TimelineSelectEntryModel> timelineSelectEntryModels = 
			timelineSelectEntriesModel.getTimelineSelectEntryModels();
		
		retSelectEntryModelPos = 0;
		
		for (TimelineSelectEntryModel timelineSelectEntryModel : timelineSelectEntryModels)
		{
			if (timelineSelectEntryModel == searchedTimelineSelectEntryModel)
			{
				break;
			}
			retSelectEntryModelPos++;
		}
		
		//==========================================================================================
		return retSelectEntryModelPos;
	}

//	/**
//	 * Called if selected timeline is changed.
//	 * 
//	 * @param selectedTimelineSelectEntryModel
//	 * 			is the selected timeline entry.
//	 */
//	public void notifySelectedTimelineChanged(TimelineSelectEntryModel selectedTimelineSelectEntryModel)
//	{
//		//==========================================================================================
//		SelectedTimelineModel selectedTimelineModel = this.timelinesDrawPanelModel.getSelectedTimelineModel();
//		
//		selectedTimelineModel.setSelectedTimelineSelectEntryModel(selectedTimelineSelectEntryModel);
//		
//		//==========================================================================================
//	}
}
