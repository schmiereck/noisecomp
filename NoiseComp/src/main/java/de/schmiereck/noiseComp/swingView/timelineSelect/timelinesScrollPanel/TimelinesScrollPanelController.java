/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesScrollPanel;

import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelController;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelView;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesGeneratorsRule.TimelinesGeneratorsRuleController;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesGeneratorsRule.TimelinesGeneratorsRuleModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesGeneratorsRule.TimelinesGeneratorsRuleView;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimelinesTimeRuleController;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimelinesTimeRuleModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimelinesTimeRuleView;


/**
 * <p>
 * 	Timelines Scroll-Panel Controller.
 * </p>
 * 
 * @author smk
 * @version <p>07.09.2010:	created, smk</p>
 */
public class TimelinesScrollPanelController
{
	//**********************************************************************************************
	// Fields:
	
	private final TimelinesScrollPanelView timelinesScrollPanelView;
	
	private final TimelinesScrollPanelModel timelinesScrollPanelModel;
	
	private TimelinesTimeRuleController timelinesTimeRuleController = null; 
	
	private TimelinesGeneratorsRuleController timelinesGeneratorsRuleController = null; 
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 */
	public TimelinesScrollPanelController()
	{
		//==========================================================================================
		this.timelinesScrollPanelModel = new TimelinesScrollPanelModel();
		
	    this.timelinesScrollPanelView = new TimelinesScrollPanelView(this.timelinesScrollPanelModel);
	    
		//==========================================================================================
	}

	/**
	 * @param timelinesDrawPanelController 
	 * 			is the Timelines Draw-Panel Controller.
	 */
	public void setTimelinesDrawPanelController(final TimelinesDrawPanelController timelinesDrawPanelController)
	{
		//==========================================================================================
	    TimelinesDrawPanelView timelinesDrawPanelView = timelinesDrawPanelController.getTimelinesDrawPanelView();
	    
	    TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
	    
	    this.timelinesScrollPanelView.setTimelinesDrawPanelView(timelinesDrawPanelView);
	    
	    //------------------------------------------------------------------------------------------
//	    TimelinesTimeRuleModel timelinesTimeRuleModel = this.timelinesTimeRuleController.getTimelinesTimeRuleModel();
//	    
//		timelinesTimeRuleModel.setTimelineSelectEntryModels(timelinesDrawPanelModel.getTimelineSelectEntryModels());
		
	    //------------------------------------------------------------------------------------------
//	    TimelinesGeneratorsRuleModel timelinesGeneratorsRuleModel = this.timelinesGeneratorsRuleController.getTimelinesGeneratorsRuleModel();
//	    
//		timelinesGeneratorsRuleModel.setTimelineSelectEntryModels(timelinesDrawPanelModel.getTimelineSelectEntryModels());
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #timelinesScrollPanelView}.
	 */
	public TimelinesScrollPanelView getTimelinesScrollPanelView()
	{
		return this.timelinesScrollPanelView;
	}

	/**
	 * @return 
	 * 			returns the {@link #timelinesScrollPanelModel}.
	 */
	public TimelinesScrollPanelModel getTimelinesScrollPanelModel()
	{
		return this.timelinesScrollPanelModel;
	}

	/**
	 * @param timelinesTimeRuleController
	 * 			is the Timelines-Time-Rule Controller.
	 * @param timelinesGeneratorsRuleController
	 * 			is the Timeline-Generators-Rule Controller.
	 */
	public void setTimelinesRuleController(TimelinesTimeRuleController timelinesTimeRuleController,
	                                       TimelinesGeneratorsRuleController timelinesGeneratorsRuleController)
	{
		//==========================================================================================
		TimelinesTimeRuleModel timelinesTimeRuleModel = timelinesTimeRuleController.getTimelinesTimeRuleModel();

		timelinesTimeRuleModel.setTimelinesScrollPanelModel(this.timelinesScrollPanelModel);
		
		this.timelinesTimeRuleController = timelinesTimeRuleController;
		
		TimelinesTimeRuleView timelinesTimeRuleView = timelinesTimeRuleController.getTimelinesTimeRuleView();
		
		this.timelinesScrollPanelView.setTimelinesTimeRuleView(timelinesTimeRuleView);

		//------------------------------------------------------------------------------------------
		TimelinesGeneratorsRuleModel timelinesGeneratorsRuleModel = timelinesGeneratorsRuleController.getTimelinesGeneratorsRuleModel();

		timelinesGeneratorsRuleModel.setTimelinesScrollPanelModel(this.timelinesScrollPanelModel);
		
		this.timelinesGeneratorsRuleController = timelinesGeneratorsRuleController;
		
		TimelinesGeneratorsRuleView timelinesGeneratorsRuleView = timelinesGeneratorsRuleController.getTimelinesGeneratorsRuleView();
		
		this.timelinesScrollPanelView.setTimelinesGeneratorsRuleView(timelinesGeneratorsRuleView);
		
		//==========================================================================================
	}
}
