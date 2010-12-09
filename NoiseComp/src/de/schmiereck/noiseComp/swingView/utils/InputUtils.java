/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.utils;

import java.text.DecimalFormat;
import java.text.ParseException;

import de.schmiereck.noiseComp.swingView.MultiValue;

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
//	private static final NumberFormat format = NumberFormat.getInstance();
	private static final DecimalFormat format = new DecimalFormat();

	/**
	 * @param value
	 * 			is the string.
	 * @return
	 * 			the float value.
	 */
	public static Integer makeIntegerValue(String value)
	{
		return Integer.parseInt(value);
	}

	/**
	 * @param value
	 * 			is the string.
	 * @return
	 * 			the float value.<br/>
	 * 			<code>null</code> if value is <code>null</code> or <code>""</code>.
	 */
	public static Float makeFloatValue(String value)
	{
		//==========================================================================================
//		return Float.parseFloat(value);
		Float ret;
		
		if (value != null)
		{
			if (value.length() > 0)
			{
				Number number;
				
				try
				{
					number = format.parse(value);
				}
				catch (ParseException ex)
				{
					throw new RuntimeException("Parse \"" + value + "\".", ex);
				}
				
				ret = new Float(number.floatValue());
			}
			else
			{
				ret = null;
			}
		}
		else
		{
			ret = null;
		}
		//==========================================================================================
		return ret;
	}

	/**
	 * @param value
	 * 			is the string.
	 * @return
	 * 			the string value (<code>null</code> if string is empty).
	 */
	public static String makeStringValue(String value)
	{
		String stringValue;
		
		if (value != null)
		{
			if (value.length() > 0)
			{
				stringValue = value;
			}
			else
			{
				stringValue = null;
			}
		}
		else
		{
			stringValue = null;
		}
		
		return stringValue;
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
		
		multiValue.stringValue = makeStringValue(value);
		
		if (multiValue.stringValue != null)
		{
			try
			{
				multiValue.floatValue = makeFloatValue(value);
			}
			catch (NumberFormatException ex)
			{
				multiValue.floatValue = null;
			}
		}
		else
		{
			multiValue.floatValue = null;
		}
		
		return multiValue;
	}

	/**
	 * @param value
	 * 			is the boolean.
	 * @return
	 * 			the boolean value.
	 */
	public static Boolean makeBooleanValue(boolean value)
	{
		Boolean ret;
		
		if (value == true)
		{
			ret = Boolean.TRUE;
		}
		else
		{
			ret = Boolean.FALSE;
		}
		
		return ret;
	}
}
