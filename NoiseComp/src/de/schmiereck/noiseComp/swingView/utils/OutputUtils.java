/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.utils;

/**
 * <p>
 * 	Output Utils.
 * </p>
 * 
 * @author smk
 * @version <p>09.09.2010:	created, smk</p>
 */
public class OutputUtils
{

	public static String makeStringText(String value)
	{
		String ret;
		
		if (value != null)
		{
			ret = value;
		}
		else
		{
			ret = "";
		}
		
		return ret;
	}
	
	public static String makeIntegerText(Integer value)
	{
		String ret;
		
		if (value != null)
		{
			ret = Integer.toString(value);
		}
		else
		{
			ret = "";
		}
		
		return ret;
	}

	public static String makeFloatText(Float value)
	{
		String ret;
		
		if (value != null)
		{
			ret = Float.toString(value);
		}
		else
		{
			ret = "";
		}
		
		return ret;
	}

	/**
	 * @param value
	 * 			is the value.
	 * @return
	 * 			<code>true</code> if value is not <code>null</code> and {@link Boolean#TRUE}.
	 * 			<code>false</code> if value is <code>null</code> or {@link Boolean#FALSE}.
	 */
	public static boolean makeBoolean(Boolean value)
	{
		boolean ret;
		
		if (value != null)
		{
			ret = value.booleanValue();
		}
		else
		{
			ret = false;
		}
		
		return ret;
	}
}
