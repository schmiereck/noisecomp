/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView;

/**
 * <p>
 * 	Input-Utils
 * </p>
 * 
 * @author smk
 * @version <p>18.09.2010:	created, smk</p>
 */
public class InputUtils
{

	/**
	 * @param value
	 * 			is the string.
	 * @return
	 * 			the float value.
	 */
	public static Float makeFloatValue(String value)
	{
		return Float.parseFloat(value);
	}

	/**
	 * @param value
	 * 			is the string.
	 * @return
	 * 			the Multi-Value.
	 */
	public static MultiValue makeMultiValue(String value)
	{
		MultiValue multiValue = new MultiValue();
		
		multiValue.stringValue = value;

		try
		{
			multiValue.floatValue = InputUtils.makeFloatValue(value);
		}
		catch (NumberFormatException ex)
		{
			multiValue.floatValue = null;
		}
		return multiValue;
	}
}
