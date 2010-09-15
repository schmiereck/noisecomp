/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;

/**
 * <p>
 * 	Input-Edit Model.
 * </p>
 * 
 * @author smk
 * @version <p>15.09.2010:	created, smk</p>
 */
public class InputEditModel
{
	//**********************************************************************************************
	// Fields:

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Generator Name.
	 */
	private String value = null;

	/**
	 * {@link #value} changed listeners.
	 */
	private final ModelPropertyChangedNotifier valueChangedNotifier = new ModelPropertyChangedNotifier();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @return 
	 * 			returns the {@link #value}.
	 */
	public String getValue()
	{
		return this.value;
	}

	/**
	 * @param value 
	 * 			to set {@link #value}.
	 */
	public void setValue(String value)
	{
		this.value = value;
		
		// Notify listeners.
		this.valueChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #valueChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getValueChangedNotifier()
	{
		return this.valueChangedNotifier;
	}
}
