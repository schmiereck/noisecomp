/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelines;

/**
 * <p>
 * 	Timelines Draw-Panel Controller.
 * </p>
 * 
 * @author smk
 * @version <p>07.09.2010:	created, smk</p>
 */
public class TimelinesDrawPanelController
{
	//**********************************************************************************************
	// Fields:
	
	private TimelinesDrawPanelModel timelinesDrawPanelModel;
	
	private TimelinesDrawPanelView timelinesDrawPanelView;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 */
	public TimelinesDrawPanelController()
	{
		//==========================================================================================
		this.timelinesDrawPanelModel = new TimelinesDrawPanelModel();
		
	    this.timelinesDrawPanelView = new TimelinesDrawPanelView(this.timelinesDrawPanelModel);
	    
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
		this.timelinesDrawPanelModel.clearTimelineGenerators();
	}

	/**
	 * @param timelineGeneratorModel
	 * 			is a Timeline-Generator Model.
	 */
	public void addTimelineGeneratorModel(TimelineGeneratorModel timelineGeneratorModel)
	{
		this.timelinesDrawPanelModel.addTimelineGeneratorModel(timelineGeneratorModel);
	}
}
