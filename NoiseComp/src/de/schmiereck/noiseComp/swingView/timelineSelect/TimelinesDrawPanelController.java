/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.util.List;

import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.SwingMain;
import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.DoChangeTimelinesPositionListenerInterface;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.RemoveTimelineGeneratorListenerInterface;
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
	
	private final TimelinesDrawPanelModel timelinesDrawPanelModel;
	
	private final TimelinesDrawPanelView timelinesDrawPanelView;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * @param modulesTreeModel 
	 * 			is the Modules-Tree Model.
	 * @param inputEditModel 
	 * 			is the Input-Edit Model.
	 */
	public TimelinesDrawPanelController(final AppController appController,
	                                    //final ModulesTreeModel modulesTreeModel,
	                                    //InputEditModel inputEditModel)
	                                    final AppModelChangedObserver appModelChangedObserver)
	{
		//==========================================================================================
//		this.modulesTreeModel = modulesTreeModel;
		
		this.timelinesDrawPanelModel = new TimelinesDrawPanelModel(appModelChangedObserver);
		
	    this.timelinesDrawPanelView = new TimelinesDrawPanelView(this.timelinesDrawPanelModel);
	    
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
	    this.timelinesDrawPanelView.addChangeTimelinesPositionListeners
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
	    this.timelinesDrawPanelModel.getRemoveTimelineGeneratorNotifier().addRemoveTimelineGeneratorListeners
	    (
	     	new RemoveTimelineGeneratorListenerInterface()
	     	{
				@Override
				public void notifyRemoveTimelineGenerator(TimelinesDrawPanelModel timelinesDrawPanelModel,
				                                          TimelineSelectEntryModel timelineSelectEntryModel)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					if (timelineSelectEntryModel == timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel())
					{
						timelinesDrawPanelModel.setSelectedTimelineSelectEntryModel(null);
					}
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
	     	}
	    );
	    //------------------------------------------------------------------------------------------
	    this.timelinesDrawPanelModel.getPlaybackTimeChangedNotifier().addModelPropertyChangedListener
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
		// Remove listeners if timeline is removed:
		
		ModelPropertyChangedListener modelChangedListener = this.timelinesDrawPanelModel.getTimelineGeneratorModelChangedListener();
		
		List<TimelineSelectEntryModel> timelineSelectEntryModels = this.timelinesDrawPanelModel.getTimelineSelectEntryModels();
		
		for (TimelineSelectEntryModel timelineSelectEntryModel : timelineSelectEntryModels)
		{
			timelineSelectEntryModel.getNameChangedNotifier().removeModelPropertyChangedListener(modelChangedListener);
			timelineSelectEntryModel.getStartTimePosChangedNotifier().removeModelPropertyChangedListener(modelChangedListener);
			timelineSelectEntryModel.getEndTimePosChangedNotifier().removeModelPropertyChangedListener(modelChangedListener);
		}
		
		//------------------------------------------------------------------------------------------
		this.timelinesDrawPanelModel.clearTimelineGenerators();

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
		this.timelinesDrawPanelModel.addTimelineSelectEntryModel(timelinePos,
		                                                         timelineSelectEntryModel);
		
		ModelPropertyChangedListener modelChangedListener = this.timelinesDrawPanelModel.getTimelineGeneratorModelChangedListener();
		
		timelineSelectEntryModel.getNameChangedNotifier().addModelPropertyChangedListener(modelChangedListener);
		timelineSelectEntryModel.getStartTimePosChangedNotifier().addModelPropertyChangedListener(modelChangedListener);
		timelineSelectEntryModel.getEndTimePosChangedNotifier().addModelPropertyChangedListener(modelChangedListener);
		
		// TODO remove listeners if timeline is removed.
	}

	/**
	 * @return
	 * 			the Selected Timeline.
	 */
	public Timeline getSelectedTimeline()
	{
		Timeline retSelectedTimeline;
		
		TimelineSelectEntryModel selectedTimelineSelectEntryModel = this.timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();

		if (selectedTimelineSelectEntryModel != null)
		{
			retSelectedTimeline = selectedTimelineSelectEntryModel.getTimeline();
			
//			Timeline selectedTimeline = selectedTimelineSelectEntryModel.getTimeline();
//			
//			String selectedTimelineGeneratorName = selectedTimelineSelectEntryModel.getName();
//			
//			ModulGeneratorTypeData editedModulGeneratorTypeData = this.modulesTreeModel.getEditedModulGeneratorTypeData();
//			
//			retSelectedTimeline = null;
//			
//			Iterator<Generator> generatorsIterator = editedModulGeneratorTypeData.getGeneratorsIterator();
//			
//			while (generatorsIterator.hasNext())
//			{
//				Generator generator = generatorsIterator.next();
//				
//				if (generator.getName().equals(selectedTimelineGeneratorName))
//				{
//					retSelectedTimeline = generator;
//					break;
//				}
//			}
		}
		else
		{
			retSelectedTimeline = null;
		}
		
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
		SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
		TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();
		
		//==========================================================================================
//		int firstTimelinePos = firstTimelineSelectEntryModel.getTimelinePos();
//		int secondTimelinePos = secondTimelineSelectEntryModel.getTimelinePos();
		int firstTimelinePos = this.timelinesDrawPanelModel.getTimelineSelectEntryPos(firstTimelineSelectEntryModel);
		int secondTimelinePos = this.timelinesDrawPanelModel.getTimelineSelectEntryPos(secondTimelineSelectEntryModel);
		//------------------------------------------------------------------------------------------
		// Update edited Modul Data:
		
//		TimelineSelectEntryModel selectedTimelineSelectEntryModel = this.timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
		
//		ModulGeneratorTypeData editedModulGeneratorTypeData = this.modulesTreeModel.getEditedModulGeneratorTypeData();
		
//		Generator firstGenerator = selectedTimelineGeneratorModel.getGenerator();
//		Generator secondGenerator = newTimelineGeneratorModel.getGenerator();
		
		timelineManagerLogic.switchTracksByPos(firstTimelinePos,
                                               secondTimelinePos);
		
//		editedModulGeneratorTypeData.switchTracksByPos(firstTimelinePos,
//		                                               secondTimelinePos);
		
		//------------------------------------------------------------------------------------------
		// Update Timeline-Draw Model:
		
//		List<TimelineSelectEntryModel> timelineGeneratorModels = this.timelinesDrawPanelModel.getTimelineGeneratorModels();
//		
//		timelineGeneratorModels.set(firstTimelinePos, secondTimelineGeneratorModel);
//		timelineGeneratorModels.set(secondTimelinePos, firstTimelineGeneratorModel);
		this.timelinesDrawPanelModel.changeTimelinesPosition(firstTimelinePos,
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

	/**
	 * 
	 */
	public void doCreateNew(final TimelinesTimeRuleController timelinesTimeRuleController,
	                        final TimelinesGeneratorsRuleController timelinesGeneratorsRuleController,
	                        final AppModelChangedObserver appModelChangedObserver)
	{
		//==========================================================================================
		TimelinesDrawPanelModel timelinesDrawPanelModel = this.getTimelinesDrawPanelModel();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		int timelinePos;
		Float startTimePos;
		Float endTimePos;
		
		TimelineSelectEntryModel selectedTimelineSelectEntryModel = timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
		
		if (selectedTimelineSelectEntryModel != null)
		{
			timelinePos = this.calcTimelineSelectEntryModelPos(selectedTimelineSelectEntryModel) + 1;
			startTimePos = selectedTimelineSelectEntryModel.getStartTimePos();
			endTimePos = selectedTimelineSelectEntryModel.getEndTimePos();
		}
		else
		{
			timelinePos = timelinesDrawPanelModel.getTimelineSelectEntryModels().size();
			startTimePos = 0.0F;
			endTimePos = 1.0F;
		}
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Update Timeline-Select-Model:
		
		TimelineSelectEntryModel timelineSelectEntryModel = 
			new TimelineSelectEntryModel(null,
//			                             timelinePos,
			                             "(new)",
			                             startTimePos,
			                             endTimePos);
		
		this.addTimelineSelectEntryModel(timelinePos,
		                                 timelineSelectEntryModel);
		
		timelinesDrawPanelModel.setSelectedTimelineSelectEntryModel(timelineSelectEntryModel);
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		Dimension timelinesDrawPanelDimension = timelinesDrawPanelModel.getDimension();
		
		// TimelinesTimeRule update.
		timelinesTimeRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getWidth());
		
		// TimelinesGeneratorsRule update.
		timelinesGeneratorsRuleController.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getHeight());
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		appModelChangedObserver.notifyAppModelChanged();
		
		
//		
//		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//		Dimension timelinesDrawPanelDimension = timelinesDrawPanelModel.getDimension();
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
		TimelineSelectEntryModel retTimelineSelectEntryModel;
		
		List<TimelineSelectEntryModel> timelineSelectEntryModels = this.timelinesDrawPanelModel.getTimelineSelectEntryModels();
		
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
		int ret;
		
		List<TimelineSelectEntryModel> timelineSelectEntryModels = this.timelinesDrawPanelModel.getTimelineSelectEntryModels();
		
		ret = 0;
		
		for (TimelineSelectEntryModel timelineSelectEntryModel : timelineSelectEntryModels)
		{
			if (timelineSelectEntryModel == searchedTimelineSelectEntryModel)
			{
				break;
			}
			ret++;
		}
		
		//==========================================================================================
		return ret;
	}

	/**
	 * @param actualTime
	 * 			the actual play time in seconds.
	 */
	public void doPlaybackTimeChanged(float actualTime)
	{
		//==========================================================================================
		this.timelinesDrawPanelModel.setPlaybackTime(actualTime);
		
		//==========================================================================================
	}
}
