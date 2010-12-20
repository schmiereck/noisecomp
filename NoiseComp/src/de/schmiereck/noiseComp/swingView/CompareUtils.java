/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView;

/**
 * <p>
 * 	Compare Utils.
 * </p>
 * 
 * @author smk
 * @version <p>18.09.2010:	created, smk</p>
 */
public class CompareUtils
{

	/**
	 * @param value1
	 * 			is the first value.
	 * @param value2
	 * 			is the second value.
	 * @return
	 * 			<code>true</code> if the values are equal or both <code>null</code>.
	 */
	public static boolean compareWithNull(Object value1, Object value2)
	{
		boolean ret;
		
		if (value1 != null)
		{
			ret = value1.equals(value2);
		}
		else
		{
			if (value2 == null)
			{
				ret = true;
			}
			else
			{
				ret = false;
			}
		}
		
		return ret;
	}
	/**
	 * @param value1
	 * 			is the first value.
	 * @param value2
	 * 			is the second value.
	 * @return
	 * 			{@link Comparable#compareTo(Object)} or 
	 * 			<code>-1</code> if only value2 is null or
	 * 			<code>0</code> if the values are both <code>null</code>.
	 */
//	public static int compareToWithNull(Comparable<Object> value1, Comparable<Object> value2)
	public static int compareToWithNull(String value1, String value2)
	{
		int ret;
		
		if (value1 != null)
		{
			ret = value1.compareTo(value2);
		}
		else
		{
			if (value2 == null)
			{
				ret = -1;
			}
			else
			{
				ret = 0;
			}
		}
		
		return ret;
	}
}
