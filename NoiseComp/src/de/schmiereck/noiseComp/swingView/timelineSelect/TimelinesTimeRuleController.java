/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

/**
 * <p>
 * 	Timelines-Time-Rule Controller.
 * </p>
 * 
 * @author smk
 * @version <p>24.09.2010:	created, smk</p>
 */
public class TimelinesTimeRuleController
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Timelines-Time-Rule Model.
	 */
	private final TimelinesTimeRuleModel timelinesTimeRuleModel;

	/**
	 * Timelines-Time-Rule View.
	 */
	private final TimelinesTimeRuleView timelinesTimeRuleView;

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public TimelinesTimeRuleController()
	{
		//==========================================================================================
		this.timelinesTimeRuleModel = new TimelinesTimeRuleModel();
		
		this.timelinesTimeRuleView = new TimelinesTimeRuleView(this.timelinesTimeRuleModel);
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #timelinesTimeRuleModel}.
	 */
	public TimelinesTimeRuleModel getTimelinesTimeRuleModel()
	{
		return this.timelinesTimeRuleModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #timelinesTimeRuleView}.
	 */
	public TimelinesTimeRuleView getTimelinesTimeRuleView()
	{
		return this.timelinesTimeRuleView;
	}

	/**
	 * Do Timeline Generator Models Changed.
	 */
	public void doTimelineGeneratorModelsChanged()
	{
		this.timelinesTimeRuleView.repaint();
	}

	/**
	 * 
	 */
	public void doChangeZoomX(float zoomX)
	{
		this.timelinesTimeRuleModel.setUnits((int)zoomX);
		
		this.timelinesTimeRuleView.repaint();
	}

}
