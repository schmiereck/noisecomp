/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView;

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
}
