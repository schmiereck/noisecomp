/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesGeneratorsRule;

import java.awt.Dimension;

import de.schmiereck.noiseComp.soundSource.SoundSourceData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntriesModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.RemoveTimelineGeneratorListenerInterface;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel;
import de.schmiereck.noiseComp.timeline.TimelineContentChangedListenerInterface;

/**
 * <p>
 * 	Timeline-Generators-Rule Controller.
 * </p>
 * 
 * @author smk
 * @version <p>23.09.2010:	created, smk</p>
 */
public class TimelinesGeneratorsRuleController 
implements RemoveTimelineGeneratorListenerInterface, 
		   TimelineContentChangedListenerInterface
{
	//**********************************************************************************************
	// Fields:
	
	private final TimelinesGeneratorsRuleModel timelinesGeneratorsRuleModel;
	
	private final TimelinesGeneratorsRuleView timelinesGeneratorsRuleView;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelineSelectEntriesModel
	 * 			are the Timeline-Select-Entries Model.
	 * @param selectedTimelineModel
	 * 			is the Selected-Timeline Model.
	 */
	public TimelinesGeneratorsRuleController(final TimelineSelectEntriesModel timelineSelectEntriesModel,
	                                         final SelectedTimelineModel selectedTimelineModel)
	{
		//==========================================================================================
	    this.timelinesGeneratorsRuleModel = 
	    	new TimelinesGeneratorsRuleModel(timelineSelectEntriesModel,
	    	                                 selectedTimelineModel);
	    
	    this.timelinesGeneratorsRuleView = 
	    	new TimelinesGeneratorsRuleView(this.timelinesGeneratorsRuleModel);
	    
	    //------------------------------------------------------------------------------------------
	    selectedTimelineModel.getSelectedTimelineChangedNotifier().addModelPropertyChangedListener
	    (
	     	new ModelPropertyChangedListener()
	     	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					timelinesGeneratorsRuleView.repaint();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
	     	}
	    );
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #timelinesGeneratorsRuleModel}.
	 */
	public TimelinesGeneratorsRuleModel getTimelinesGeneratorsRuleModel()
	{
		return this.timelinesGeneratorsRuleModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #timelinesGeneratorsRuleView}.
	 */
	public TimelinesGeneratorsRuleView getTimelinesGeneratorsRuleView()
	{
		return this.timelinesGeneratorsRuleView;
	}

	/**
	 * Do Timeline Generator Models Changed.
	 */
	public void doTimelineGeneratorModelsChanged(double timelinesDrawPanelHeight)
	{
		//==========================================================================================
		this.timelinesGeneratorsRuleView.setHeight((int)timelinesDrawPanelHeight);
		
		this.timelinesGeneratorsRuleView.repaint();
		
		//==========================================================================================
	}

	/**
	 * Do Change Timelines Position.
	 */
	public void doChangeTimelinesPosition()
	{
		//==========================================================================================
		this.getTimelinesGeneratorsRuleView().repaint();
		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.swingView.timelineSelect.RemoveTimelineGeneratorListenerInterface#notifyRemoveTimelineGenerator(de.schmiereck.noiseComp.swingView.timelineSelect.TimelinesDrawPanelModel, de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel)
	 */
	@Override
	public void notifyRemoveTimelineGenerator(SoundSourceData soundSourceData, TimelinesDrawPanelModel timelinesDrawPanelModel,
                                              TimelineSelectEntryModel timelineSelectEntryModel)
	{
		//==========================================================================================
		Dimension timelinesDrawPanelDimension = timelinesDrawPanelModel.getDimension();
		
		// TimelinesGeneratorsRule update.
		this.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getHeight());
		
		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.timeline.TimelineContentChangedListenerInterface#notifyTimelineContentChanged(long, long)
	 */
	@Override
	public void notifyTimelineContentChanged(long bufferStart, long bufferEnd)
	{
		//==========================================================================================
		this.timelinesGeneratorsRuleView.repaint();

		//==========================================================================================
	}

	/**
	 * Called if selected timeline is changed.
	 * 
	 * @param selectedTimelineSelectEntryModel
	 * 			is the selected timeline entry.
	 */
	public void notifySelectedTimelineChanged(TimelineSelectEntryModel selectedTimelineSelectEntryModel)
	{
		//==========================================================================================
		SelectedTimelineModel selectedTimelineModel = this.timelinesGeneratorsRuleModel.getSelectedTimelineModel();
		
		selectedTimelineModel.setSelectedTimelineSelectEntryModel(selectedTimelineSelectEntryModel);
		
		//==========================================================================================
	}

}
