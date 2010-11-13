/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appModel;

import java.io.File;

import de.schmiereck.noiseComp.swingView.CompareUtils;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;


/**
 * <p>
 * 	App Model.
 * </p>
 * <p>
 * 	see: http://de.wikipedia.org/wiki/Beobachter_%28Entwurfsmuster%29
 * 
 * 	AppModelChangedObserver
 * 		ModulsModelChangedObserver
 * 			- editedModul
 * 			TimelinesModelChangedObserver
 * 				- editedTimeline
 * 			ModulInputTypesModelChangedObserver
 * 				- editedModulInputType
 * 			InputsModelChangedObserver
 * 				- editedInput
 * </p>
 * 
 * @author smk
 * @version <p>06.09.2010:	created, smk</p>
 */
public class AppModel
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Die Datei der letzten Datei-Operation.
	 */
	private File fileActionFile = null;

	//----------------------------------------------------------------------------------------------
	/**
	 * <code>true</code> if model is changed and should be saved.
	 */
	private boolean isModelChanged = false;

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
	public enum TicksPer
	{
		Seconds,
		Milliseconds,
		BPM
	}
	
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
	 * 			returns the {@link #fileActionFile}.
	 */
	public File getFileActionFile()
	{
		return this.fileActionFile;
	}

	/**
	 * @param fileActionFile 
	 * 			to set {@link #fileActionFile}.
	 */
	public void setFileActionFile(File fileActionFile)
	{
		this.fileActionFile = fileActionFile;
	}

	/**
	 * @return 
	 * 			returns the {@link #isModelChanged}.
	 */
	public boolean getIsModelChanged()
	{
		return this.isModelChanged;
	}

	/**
	 * @param isModelChanged 
	 * 			to set {@link #isModelChanged}.
	 */
	public void setIsModelChanged(boolean isModelChanged)
	{
		this.isModelChanged = isModelChanged;
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

}
