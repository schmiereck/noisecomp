/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineEdit;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.service.StartupService;

/**
 * <p>
 * 	Generator-Type Select-Item for Combo-Box.
 * </p>
 * 
 * @author smk
 * @version <p>20.09.2010:	created, smk</p>
 */
public class GeneratorTypeSelectItem
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Input-Type Data.
	 */
	private final GeneratorTypeData generatorTypeData;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param generatorTypeData
	 * 			is the Input-Type Data.
	 */
	public GeneratorTypeSelectItem(GeneratorTypeData generatorTypeData)
	{
		this.generatorTypeData = generatorTypeData;
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorTypeData}.
	 */
	public GeneratorTypeData getGeneratorTypeData()
	{
		return this.generatorTypeData;
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
			ret = this.generatorTypeData == ((GeneratorTypeSelectItem)obj).generatorTypeData;
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
		
		if (this.generatorTypeData != null)
		{
			String folderPath = this.generatorTypeData.getFolderPath();
			
			String path;
			
			if (folderPath.startsWith(StartupService.MODULE_FOLDER_PATH))
			{
				path = folderPath.substring(StartupService.MODULE_FOLDER_PATH.length() - 1);
			}
			else
			{
				path = "";
			}
			
			ret = path + this.generatorTypeData.getGeneratorTypeName();
		}
		else
		{
			ret = "--- No Generator-Type ---";
		}
		
		return ret;
	}
}