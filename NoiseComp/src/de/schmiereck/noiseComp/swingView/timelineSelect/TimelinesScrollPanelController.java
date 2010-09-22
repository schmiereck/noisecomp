/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

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
}
