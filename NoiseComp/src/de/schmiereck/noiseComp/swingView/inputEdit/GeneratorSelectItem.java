/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.swingView.CompareUtils;
import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * <p>
 * 	Generator Select-Item for Combo-Box.
 * </p>
 * 
 * @author smk
 * @version <p>16.09.2010:	created, smk</p>
 */
public class GeneratorSelectItem
implements Comparable<GeneratorSelectItem>
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Generator.
	 */
	private final Timeline timeline;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timeline
	 * 			is the Timeline.
	 */
	public GeneratorSelectItem(Timeline timeline)
	{
		this.timeline = timeline;
	}

	/**
	 * @return 
	 * 			returns the {@link #timeline}.
	 */
	public Timeline getTimeline()
	{
		return this.timeline;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		//==========================================================================================
		boolean ret;
		
		if (obj != null)
		{
			ret = this.timeline == ((GeneratorSelectItem)obj).timeline;
		}
		else
		{
			ret = false;
		}
		
		//==========================================================================================
		return ret;
	}
	
	public int compareTo(GeneratorSelectItem otherGeneratorSelectItem)
	{
		//==========================================================================================
		int ret;
		
		Timeline otherTimeline = otherGeneratorSelectItem.getTimeline();
		
		if (otherTimeline != null)
		{
			if (this.timeline != null)
			{
				Generator otherGenerator = otherTimeline.getGenerator();
				Generator generator = this.timeline.getGenerator();
				
				String otherName = otherGenerator.getName();
				String name = generator.getName();
				
				ret = CompareUtils.compareToWithNull(name, otherName);
			}
			else
			{
				ret = 1;
			}
		}
		else
		{
			ret = -1;
		}
		//==========================================================================================
		return ret;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String ret;
		
		if (this.timeline != null)
		{
			Generator generator = this.timeline.getGenerator();
			
			if (generator != null)
			{
				ret = generator.getName();
			}
			else
			{
				ret = "(UNKOWN)";
			}
		}
		else
		{
			ret = "--- No Generator ---";
		}
		
		return ret;
	}

}
