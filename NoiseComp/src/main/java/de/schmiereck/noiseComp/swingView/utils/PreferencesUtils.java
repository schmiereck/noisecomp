/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.utils;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import de.schmiereck.noiseComp.swingView.SwingMain;

/**
 * <p>
 * 	Tool functions to access Preferences.
 * </p>
 * 
 * @author smk
 * @version <p>21.09.2010:	created, smk</p>
 */
public class PreferencesUtils
{
	/**
	 * @return
	 * 			the Preferenzes of Application.
	 */
	public static Preferences getUserPreferences()
	{
		// TODO userRoot() wäre richtig, aber wegen BUG http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4479451 und 
		// http://onesearch.sun.com/search/onesearch/index.jsp?charset=utf-8&col=developer-debugging&qt=Error+while+using+references+java.util.prefs.Preferences.userRoot&rt=true&cs=false&y=15&x=13
		// geht das nicht.
		// Die App muss einmal unter einem Admin-Account ausgeführt werden, um zu laufen...
		//Preferences preferences = Preferences.systemRoot();
		Preferences preferences = Preferences.userRoot();
		
		Preferences userPrefs;

		userPrefs = preferences.node(SwingMain.PREFERENCES_ROOT_NODE_NAME);
		
		return userPrefs;
	}
	
	/**
	 * @param userPrefs
	 * 			are the Preferenzes of Application.
	 * @param name
	 * 			is the Name of the Attribute.
	 * @return
	 * 			<code>true</code> if key exists.
	 */
	public static boolean checkKeyExists(Preferences userPrefs, String name)
	{
		//==========================================================================================
		boolean foundKey = false;
		
		//------------------------------------------------------------------------------------------
		String[] keys;

		try
		{
			keys = userPrefs.keys();
		}
		catch (BackingStoreException ex)
		{
			throw new RuntimeException(ex);
		}

		for (int pos = 0; pos < keys.length; pos++)
		{
			String key = keys[pos];
			
			if (name.equals(key) == true)
			{
				foundKey = true;
				break;
			}
		}
		
		//==========================================================================================
		return foundKey;
	}
	
	/**
	 * @param userPrefs
	 * 			are the Preferenzes of Application.
	 * @param name
	 * 			is the Name of the Attribute.
	 * @param defValue
	 * 			is the Default-Value of the Atrribute returned if it is a newly created attribute.
	 * @return
	 * 			the value associated with the attribute bevor.
	 */
	public static String getValueString(Preferences userPrefs, String name, String defValue)
	{
		//==========================================================================================
		String retValue;
		
		//------------------------------------------------------------------------------------------
		boolean foundKey = checkKeyExists(userPrefs, name);

		if (foundKey == true)
		{
			retValue = userPrefs.get(name, defValue);
		}
		else
		{
			if (defValue == null)
			{
				retValue = "";
			}
			else
			{
				retValue = defValue;
			}
			
			//userPrefs.put(name, defStr);
		}
		
		//==========================================================================================
		return retValue;
	}
	
	/**
	 * @param userPrefs
	 * 			are the Preferenzes of Application.
	 * @param name
	 * 			is the Name of the Attribute.
	 * @param defValue
	 * 			is the Default-Value of the Atrribute returned if it is a newly created attribute.
	 * @return
	 * 			the value associated with the attribute bevor.
	 */
	public static long getValueLong(Preferences userPrefs, String name, long defValue)
	{
		//==========================================================================================
		long retValue;
		
		//------------------------------------------------------------------------------------------
		boolean foundKey = checkKeyExists(userPrefs, name);
		
		if (foundKey == true)
		{
			retValue = userPrefs.getLong(name, defValue);
		}
		else
		{
//			userPrefs.putLong(name, def);
			retValue = defValue;
		}
		
		//==========================================================================================
		return retValue;
	}
	
	/**
	 * @param userPrefs
	 * 			are the Preferenzes of Application.
	 * @param name
	 * 			is the Name of the Attribute.
	 * @param defValue
	 * 			is the Default-Value of the Atrribute returned if it is a newly created attribute.
	 * @return
	 * 			the value associated with the attribute bevor.
	 */
	public static int getValueInteger(Preferences userPrefs, String name, int defValue)
	{
		//==========================================================================================
		int retValue;
		
		//------------------------------------------------------------------------------------------
		boolean foundKey = checkKeyExists(userPrefs, name);
		
		if (foundKey == true)
		{
			retValue = userPrefs.getInt(name, defValue);
		}
		else
		{
//			userPrefs.putLong(name, def);
			retValue = defValue;
		}
		
		//==========================================================================================
		return retValue;
	}
	
	/**
	 * @param userPrefs
	 * 			are the Preferenzes of Application.
	 * @param name
	 * 			is the Name of the Attribute.
	 * @param defValue
	 * 			is the Default-Value of the Atrribute returned if it is a newly created attribute.
	 * @return
	 * 			the value associated with the attribute bevor.
	 */
	public static double getValueDouble(Preferences userPrefs, String name, double defValue)
	{
		//==========================================================================================
		double retValue;
		
		//------------------------------------------------------------------------------------------
		boolean foundKey = checkKeyExists(userPrefs, name);
		
		if (foundKey == true)
		{
			retValue = userPrefs.getDouble(name, defValue);
		}
		else
		{
//			userPrefs.putLong(name, def);
			retValue = defValue;
		}
		
		//==========================================================================================
		return retValue;
	}

	/**
	 * @param userPrefs
	 * 			are the Preferenzes of Application.
	 * @param name
	 * 			is the Name of the Attribute.
	 * @param value
	 * 			is the value of the Attribute.
	 */
	public static void setValueString(Preferences userPrefs, String name, String value)
	{
		userPrefs.put(name, value);
	}

	/**
	 * @param userPrefs
	 * 			are the Preferenzes of Application.
	 * @param name
	 * 			is the Name of the Attribute.
	 * @param value
	 * 			is the value of the Attribute.
	 */
	public static void setValueInteger(Preferences userPrefs, String name, int value)
	{
		userPrefs.putInt(name, value);
	}

	/**
	 * @param userPrefs
	 * 			are the Preferenzes of Application.
	 * @param name
	 * 			is the Name of the Attribute.
	 * @param value
	 * 			is the value of the Attribute.
	 */
	public static void setValueDouble(Preferences userPrefs, String name, double value)
	{
		userPrefs.putDouble(name, value);
	}
}
