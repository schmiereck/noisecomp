/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;

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
	
	private TimelinesScrollPanelView timelinesScrollPanelView;
	
	private TimelinesScrollPanelModel timelinesScrollPanelModel;
	
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
	public void setTimelinesScrollPanelController(TimelinesDrawPanelController timelinesDrawPanelController)
	{
		//==========================================================================================
	    TimelinesDrawPanelView timelinesDrawPanelView = timelinesDrawPanelController.getTimelinesDrawPanelView();
	    
	    this.timelinesScrollPanelView.setTimelinesDrawPanelView(timelinesDrawPanelView);
	    
	    //------------------------------------------------------------------------------------------
	    TimelinesGeneratorsRuleModel timelinesGeneratorsRuleModel = this.timelinesGeneratorsRuleController.getTimelinesGeneratorsRuleModel();
	    
	    TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
	    
		timelinesGeneratorsRuleModel.setTimelineGeneratorModels(timelinesDrawPanelModel.getTimelineGeneratorModels());
		
//		timelinesDrawPanelModel.getTimelineGeneratorModelsChangedNotifier().addModelPropertyChangedListener
//		(
//		 	new ModelPropertyChangedListener()
//		 	{
//				@Override
//				public void notifyModelPropertyChanged()
//				{
//					timelinesGeneratorsRuleController.doTimelineGeneratorModelsChanged();
//				}
//		 	}
//		);
		
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
	 * @param timelinesGeneratorsRuleController
	 */
	public void setTimelinesGeneratorsRuleController(TimelinesGeneratorsRuleController timelinesGeneratorsRuleController)
	{
		this.timelinesGeneratorsRuleController = timelinesGeneratorsRuleController;
		
		TimelinesGeneratorsRuleModel timelinesGeneratorsRuleModel = timelinesGeneratorsRuleController.getTimelinesGeneratorsRuleModel();

		timelinesGeneratorsRuleModel.setTimelinesScrollPanelModel(this.timelinesScrollPanelModel);
//		timelinesGeneratorsRuleModel.setGeneratorSizeY(this.timelinesScrollPanelModel.getGeneratorSizeY());
		
		TimelinesGeneratorsRuleView timelinesGeneratorsRuleView = timelinesGeneratorsRuleController.getTimelinesGeneratorsRuleView();
		
		this.timelinesScrollPanelView.setTimelinesGeneratorsRuleView(timelinesGeneratorsRuleView);
	}
}
