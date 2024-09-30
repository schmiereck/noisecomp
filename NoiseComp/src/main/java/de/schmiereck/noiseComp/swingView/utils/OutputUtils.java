/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.utils;

import de.schmiereck.noiseComp.swingView.MultiValue;

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
//	private static final NumberFormat format = NumberFormat.getInstance();
//	private static final DecimalFormat format = new DecimalFormat();

//	private static final String[] formatPatterns =
//	{
//		"#0.",
//		"#0.0",
//		"#0.00",
//		"#0.000",
//		"#0.0000",
//		"#0.00000",
//	};
//	
//	private static DecimalFormat makeFormat(int digits)
//	{
//		return new DecimalFormat(formatPatterns[digits]);
//	}
	
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

	public static String makeFloatText(Float value, int digits)
	{
		String ret;
		
		if (value != null)
		{
			ret = String.format("%." + digits + "f", value.floatValue());
//			DecimalFormat format = makeFormat(digits);
//			ret = format.format(value.floatValue());
		}
		else
		{
			ret = "";
		}
		
		return ret;
	}
	
	public static String makeFloatEditText(Float value)
	{
		String ret;
		
		ret = makeFloatText(value, 5);
		
		int len = ret.length();
		
		while (len > 1)
		{
			char c = ret.charAt(len - 1);
			
			if (c == '0')
			{
				char cc = ret.charAt(len - 2);
				
				if (Character.isDigit(cc) == true)
				{
					len--;
				}
				else
				{
					break;
				}
			}
			else
			{
				break;
			}
		}
		
		return ret.substring(0, len);
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

	/**
	 * @param multiValue
	 * 			is the Multi-Value.
	 * @return
	 * 			the text string.
	 */
	public static String makeMultiValueEditText(MultiValue multiValue)
	{
		String ret;
		
		if (multiValue.floatValue != null)
		{
			ret = makeFloatEditText(multiValue.floatValue);
		}
		else
		{
			ret = makeStringText(multiValue.stringValue);
		}
		
		return ret;
	}
}
