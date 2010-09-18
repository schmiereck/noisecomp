/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import de.schmiereck.noiseComp.generator.InputTypeData;

/**
 * <p>
 * 	Modul-Input-Type Select-Item for Combo-Box.
 * </p>
 * 
 * @author smk
 * @version <p>16.09.2010:	created, smk</p>
 */
public class ModulInputTypeSelectItem
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Input-Type Data.
	 */
	private final InputTypeData inputTypeData;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param inputTypeData
	 * 			is the Input-Type Data.
	 */
	public ModulInputTypeSelectItem(InputTypeData inputTypeData)
	{
		this.inputTypeData = inputTypeData;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeData}.
	 */
	public InputTypeData getInputTypeData()
	{
		return this.inputTypeData;
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
			ret = this.inputTypeData == ((ModulInputTypeSelectItem)obj).inputTypeData;
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
		
		if (this.inputTypeData != null)
		{
			ret = this.inputTypeData.getInputTypeName();
		}
		else
		{
			ret = "--- No Modul-Input-Type ---";
		}
		
		return ret;
	}
}