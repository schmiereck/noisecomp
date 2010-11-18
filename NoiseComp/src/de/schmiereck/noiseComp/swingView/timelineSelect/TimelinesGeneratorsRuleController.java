/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.awt.Dimension;

import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.RemoveTimelineGeneratorListenerInterface;
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
	
	private TimelinesGeneratorsRuleModel timelinesGeneratorsRuleModel;
	
	private TimelinesGeneratorsRuleView timelinesGeneratorsRuleView;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public TimelinesGeneratorsRuleController()
	{
		//==========================================================================================
	    this.timelinesGeneratorsRuleModel = 
	    	new TimelinesGeneratorsRuleModel();
	    
	    this.timelinesGeneratorsRuleView = 
	    	new TimelinesGeneratorsRuleView(this.timelinesGeneratorsRuleModel);
	    
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
	public void notifyRemoveTimelineGenerator(TimelinesDrawPanelModel timelinesDrawPanelModel,
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

}
