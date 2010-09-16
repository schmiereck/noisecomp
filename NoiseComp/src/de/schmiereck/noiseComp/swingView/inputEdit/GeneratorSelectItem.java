/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import de.schmiereck.noiseComp.generator.Generator;

/**
 * <p>
 * 	Generator Select-Item for Combo-Box.
 * </p>
 * 
 * @author smk
 * @version <p>16.09.2010:	created, smk</p>
 */
public class GeneratorSelectItem
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Generator.
	 */
	private final Generator generator;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param generator
	 * 			is the Generator.
	 */
	public GeneratorSelectItem(Generator generator)
	{
		this.generator = generator;
	}

	/**
	 * @return 
	 * 			returns the {@link #generator}.
	 */
	public Generator getGenerator()
	{
		return this.generator;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		boolean ret;
		
		if (obj != null)
		{
			ret = this.generator == ((GeneratorSelectItem)obj).generator;
		}
		else
		{
			ret = false;
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String ret;
		
		if (this.generator != null)
		{
			ret = this.generator.getName();
		}
		else
		{
			ret = "--- No Generator ---";
		}
		
		return ret;
	}

}
