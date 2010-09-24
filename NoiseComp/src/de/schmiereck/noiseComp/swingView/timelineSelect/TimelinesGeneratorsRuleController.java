/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

/**
 * <p>
 * 	Timeline-Generators-Rule Controller.
 * </p>
 * 
 * @author smk
 * @version <p>23.09.2010:	created, smk</p>
 */
public class TimelinesGeneratorsRuleController
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
	public void doTimelineGeneratorModelsChanged()
	{
//		this.timelinesGeneratorsRuleModel.setTimelinesScrollPanelModel(timelinesScrollPanelModel)
		this.timelinesGeneratorsRuleView.repaint();
	}

}
