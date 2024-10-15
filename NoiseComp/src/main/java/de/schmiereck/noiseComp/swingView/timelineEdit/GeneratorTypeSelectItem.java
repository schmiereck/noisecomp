/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineEdit;

import de.schmiereck.noiseComp.generator.GeneratorTypeInfoData;
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
	private final GeneratorTypeInfoData generatorTypeInfoData;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param generatorTypeInfoData
	 * 			is the Input-Type Data.
	 */
	public GeneratorTypeSelectItem(GeneratorTypeInfoData generatorTypeInfoData)
	{
		this.generatorTypeInfoData = generatorTypeInfoData;
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorTypeInfoData}.
	 */
	public GeneratorTypeInfoData getGeneratorTypeData()
	{
		return this.generatorTypeInfoData;
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
			ret = this.generatorTypeInfoData == ((GeneratorTypeSelectItem)obj).generatorTypeInfoData;
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
		
		if (this.generatorTypeInfoData != null)
		{
			String folderPath = this.generatorTypeInfoData.getFolderPath();
			
			String path;
			
			if (folderPath.startsWith(StartupService.MODULE_FOLDER_PATH))
			{
				path = folderPath.substring(StartupService.MODULE_FOLDER_PATH.length() - 1);
			}
			else
			{
				path = "";
			}
			
			ret = path + this.generatorTypeInfoData.getGeneratorTypeName();
		}
		else
		{
			ret = "--- No Generator-Type ---";
		}
		
		return ret;
	}
}
