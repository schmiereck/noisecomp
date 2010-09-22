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
	 * @param def
	 * 			is the Default-Value of the Atrribute returned if it is a newly created attribute.
	 * @return
	 * 			the value associated with the attribute bevor.
	 */
	public static String getValueString(Preferences userPrefs, String name, String def)
	{
		boolean foundKey = false;
		String value = null;
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
				value = userPrefs.get(name, def);
				foundKey = true;
				break;
			}
		}
		
		if (foundKey == false)
		{
			String defStr;
			
			if (def == null)
			{
				defStr = "";
			}
			else
			{
				defStr = def;
			}
			
			userPrefs.put(name, defStr);
		}
		
		return value;
	}
	
	/**
	 * @param userPrefs
	 * 			are the Preferenzes of Application.
	 * @param name
	 * 			is the Name of the Attribute.
	 * @param def
	 * 			is the Default-Value of the Atrribute returned if it is a newly created attribute.
	 * @return
	 * 			the value associated with the attribute bevor.
	 */
	public static long getValueLong(Preferences userPrefs, String name, long def)
	{
		boolean foundKey = false;
		long value = def;
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
				value = userPrefs.getLong(name, def);
				foundKey = true;
				break;
			}
		}
		
		if (foundKey == false)
		{
			userPrefs.putLong(name, def);
		}
		
		return value;
	}

	/**
	 * @param userPrefs
	 * 			are the Preferenzes of Application.
	 * @param name
	 * 			is the Name of the Attribute.
	 */
	public static void setValueString(Preferences userPrefs, String name, String value)
	{
		userPrefs.put(name, value);
	}
}
