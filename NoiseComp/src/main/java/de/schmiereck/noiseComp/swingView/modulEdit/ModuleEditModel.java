/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulEdit;

import de.schmiereck.noiseComp.generator.ModuleGeneratorTypeData.TicksPer;
import de.schmiereck.noiseComp.swingView.CompareUtils;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;

/**
 * <p>
 * 	ModuleEdit Model.
 * </p>
 * 
 * @author smk
 * @version <p>09.09.2010:	created, smk</p>
 */
public class ModuleEditModel
{
	//**********************************************************************************************
	// Fields:

	/**
	 * {@link ModuleEditModel} changed listeners.
	 */
	private final ModelPropertyChangedNotifier moduleEditModelChangedNotifier = new ModelPropertyChangedNotifier();

	//----------------------------------------------------------------------------------------------
	/**
	 * Module Name.
	 */
	private String moduleName = null;

	/**
	 * {@link #moduleName} changed listeners.
	 */
	private final ModelPropertyChangedNotifier moduleNameChangedNotifier = new ModelPropertyChangedNotifier();

	//----------------------------------------------------------------------------------------------
	
	/**
	 * Module Is-Main.
	 */
	private Boolean moduleIsMain = null;

	/**
	 * {@link #moduleIsMain} changed listeners.
	 */
	private final ModelPropertyChangedNotifier moduleIsMainChangedNotifier = new ModelPropertyChangedNotifier();
	
	//----------------------------------------------------------------------------------------------
	/**
	 * Zoom Size X.<br/>
	 * <code>null</code> if no special zoom for module defined.
	 */
	private float zoomX = 1.0F;

	/**
	 * {@link #zoomX} changed listeners.
	 */
	private final ModelPropertyChangedNotifier zoomXChangedNotifier = new ModelPropertyChangedNotifier();
	
	//----------------------------------------------------------------------------------------------
	/**
	 * Count of ticks per {@link #ticksPer}.
	 */
	private Float ticksCount;
	
	/**
	 * {@link #ticksCount} changed listeners.
	 */
	private final ModelPropertyChangedNotifier ticksCountChangedNotifier = new ModelPropertyChangedNotifier();

	//----------------------------------------------------------------------------------------------
	/**
	 * {@link #ticksCount} per value.
	 */
	private TicksPer ticksPer;
	
	/**
	 * {@link #ticksPer} changed listeners.
	 */
	private final ModelPropertyChangedNotifier ticksPerChangedNotifier = new ModelPropertyChangedNotifier();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @return 
	 * 			returns the {@link #moduleName}.
	 */
	public String getModuleName()
	{
		return this.moduleName;
	}

	/**
	 * @param moduleName 
	 * 			to set {@link #moduleName}.
	 */
	public void setModuleName(String moduleame)
	{
		if (CompareUtils.compareWithNull(this.moduleName, moduleame) == false)
		{
			this.moduleName = moduleame;
			
			// Notify listeners.
			this.moduleNameChangedNotifier.notifyModelPropertyChangedListeners();
			
			this.moduleEditModelChangedNotifier.notifyModelPropertyChangedListeners();
		}
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleNameChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getModuleNameChangedNotifier()
	{
		return this.moduleNameChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleEditModelChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getModulEditModelChangedNotifier()
	{
		return this.moduleEditModelChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleIsMain}.
	 */
	public Boolean getModuleIsMain()
	{
		return this.moduleIsMain;
	}

	/**
	 * @param moduleIsMain 
	 * 			to set {@link #moduleIsMain}.
	 */
	public void setModuleIsMain(Boolean modulesMain)
	{
		this.moduleIsMain = modulesMain;
		
		// Notify listeners.
		this.moduleIsMainChangedNotifier.notifyModelPropertyChangedListeners();
		
		this.moduleEditModelChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleIsMainChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getModuleIsMainChangedNotifier()
	{
		return this.moduleIsMainChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #zoomX}.
	 */
	public float getZoomX()
	{
		return this.zoomX;
	}

	/**
	 * @param zoomX 
	 * 			to set {@link #zoomX}.
	 */
	public void setZoomX(float zoomX)
	{
		//==========================================================================================
		this.zoomX = zoomX;
		
		// Notify listeners.
		this.zoomXChangedNotifier.notifyModelPropertyChangedListeners();
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #ticksCount}.
	 */
	public Float getTicksCount()
	{
		return this.ticksCount;
	}

	/**
	 * @param ticksCount 
	 * 			to set {@link #ticksCount}.
	 */
	public void setTicksCount(Float ticksCount)
	{
		if (CompareUtils.compareWithNull(this.ticksCount, ticksCount) == false)
		{
			this.ticksCount = ticksCount;
			
			this.ticksCountChangedNotifier.notifyModelPropertyChangedListeners();
		}
	}

	/**
	 * @param ticksCount 
	 * 			to update {@link #ticksCount}.
	 */
	public void updateTicksCount(Float ticksCount)
	{
		this.ticksCount = ticksCount;
	}

	/**
	 * @return 
	 * 			returns the {@link #ticksPer}.
	 */
	public TicksPer getTicksPer()
	{
		return this.ticksPer;
	}

	/**
	 * @param ticksPer 
	 * 			to set {@link #ticksPer}.
	 */
	public void setTicksPer(TicksPer ticksPer)
	{
		if (CompareUtils.compareWithNull(this.ticksPer, ticksPer) == false)
		{
			this.ticksPer = ticksPer;
			
			this.ticksPerChangedNotifier.notifyModelPropertyChangedListeners();
		}
	}

	/**
	 * @param ticksPer 
	 * 			to set {@link #ticksPer}.
	 */
	public void updateTicksPer(TicksPer ticksPer)
	{
		this.ticksPer = ticksPer;
	}

	/**
	 * @return 
	 * 			returns the {@link #ticksCountChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getTicksCountChangedNotifier()
	{
		return this.ticksCountChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #ticksPerChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getTicksPerChangedNotifier()
	{
		return this.ticksPerChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #zoomXChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getZoomXChangedNotifier()
	{
		return this.zoomXChangedNotifier;
	}

}
