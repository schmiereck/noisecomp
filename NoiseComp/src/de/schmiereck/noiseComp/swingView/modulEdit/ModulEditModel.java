/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulEdit;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData.TicksPer;
import de.schmiereck.noiseComp.swingView.CompareUtils;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;

/**
 * <p>
 * 	Modul-Edit Model.
 * </p>
 * 
 * @author smk
 * @version <p>09.09.2010:	created, smk</p>
 */
public class ModulEditModel
{
	//**********************************************************************************************
	// Fields:

	/**
	 * {@link ModulEditModel} changed listeners.
	 */
	private final ModelPropertyChangedNotifier modulEditModelChangedNotifier = new ModelPropertyChangedNotifier();

	//----------------------------------------------------------------------------------------------
	/**
	 * Modul Name.
	 */
	private String modulName = null;

	/**
	 * {@link #modulName} changed listeners.
	 */
	private final ModelPropertyChangedNotifier modulNameChangedNotifier = new ModelPropertyChangedNotifier();

	//----------------------------------------------------------------------------------------------
	
	/**
	 * Modul Is-Main.
	 */
	private Boolean modulIsMain = null;

	/**
	 * {@link #modulIsMain} changed listeners.
	 */
	private final ModelPropertyChangedNotifier modulIsMainChangedNotifier = new ModelPropertyChangedNotifier();
	
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
	 * 			returns the {@link #modulName}.
	 */
	public String getModulName()
	{
		return this.modulName;
	}

	/**
	 * @param modulName 
	 * 			to set {@link #modulName}.
	 */
	public void setModulName(String modulName)
	{
		if (CompareUtils.compareWithNull(this.modulName, modulName) == false)
		{
			this.modulName = modulName;
			
			// Notify listeners.
			this.modulNameChangedNotifier.notifyModelPropertyChangedListeners();
			
			this.modulEditModelChangedNotifier.notifyModelPropertyChangedListeners();
		}
	}

	/**
	 * @return 
	 * 			returns the {@link #modulNameChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getModulNameChangedNotifier()
	{
		return this.modulNameChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #modulEditModelChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getModulEditModelChangedNotifier()
	{
		return this.modulEditModelChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #modulIsMain}.
	 */
	public Boolean getModulIsMain()
	{
		return this.modulIsMain;
	}

	/**
	 * @param modulIsMain 
	 * 			to set {@link #modulIsMain}.
	 */
	public void setModulIsMain(Boolean modulIsMain)
	{
		this.modulIsMain = modulIsMain;
		
		// Notify listeners.
		this.modulIsMainChangedNotifier.notifyModelPropertyChangedListeners();
		
		this.modulEditModelChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #modulIsMainChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getModulIsMainChangedNotifier()
	{
		return this.modulIsMainChangedNotifier;
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
