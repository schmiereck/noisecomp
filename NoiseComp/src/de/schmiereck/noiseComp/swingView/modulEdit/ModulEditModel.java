/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulEdit;

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
	 * Generator Name.
	 */
	private String modulName = null;

	/**
	 * {@link #modulName} changed listeners.
	 */
	private final ModelPropertyChangedNotifier modulNameChangedNotifier = new ModelPropertyChangedNotifier();

	/**
	 * {@link ModulEditModel} changed listeners.
	 */
	private final ModelPropertyChangedNotifier modulEditModelChangedNotifier = new ModelPropertyChangedNotifier();

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
		this.modulName = modulName;
		
		// Notify listeners.
		this.modulNameChangedNotifier.notifyModelPropertyChangedListeners();
		
		this.modulEditModelChangedNotifier.notifyModelPropertyChangedListeners();
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

}
