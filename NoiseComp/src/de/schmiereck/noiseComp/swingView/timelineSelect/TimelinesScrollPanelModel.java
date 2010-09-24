/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

/**
 * <p>
 * 	Timelines Scroll-Panel Model.
 * </p>
 * 
 * @author smk
 * @version <p>07.09.2010:	created, smk</p>
 */
public class TimelinesScrollPanelModel
{
	//**********************************************************************************************
	// Fields:
	
	private int generatorSizeY = 32;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 */
	public TimelinesScrollPanelModel()
	{
		//==========================================================================================
	    
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorSizeY}.
	 */
	public int getGeneratorSizeY()
	{
		return this.generatorSizeY;
	}

	/**
	 * @param generatorSizeY 
	 * 			to set {@link #generatorSizeY}.
	 */
	public void setGeneratorSizeY(int generatorSizeY)
	{
		this.generatorSizeY = generatorSizeY;
	}
}
