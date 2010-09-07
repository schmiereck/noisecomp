/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelines;

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
	 * @param timelinesDrawPanelController 
	 * 
	 */
	public TimelinesScrollPanelController(TimelinesDrawPanelController timelinesDrawPanelController)
	{
		//==========================================================================================
		this.timelinesScrollPanelModel = new TimelinesScrollPanelModel();
		
	    this.timelinesScrollPanelView = new TimelinesScrollPanelView(this.timelinesScrollPanelModel);
	    
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
