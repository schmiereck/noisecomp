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
}
