package de.schmiereck.noiseComp.dataUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import de.schmiereck.noiseComp.desktopPage.widgets.SelectEntryData;

/**
 * 	Manages a list of Objects in a Vector and in a Hash.
 * 	Provides functions to access this list as a sorted Vector and
 * 	as a hash with keys.
 *
 * @author smk
 * @version 21.02.2004
 */
public class VectorHash
{
	private Vector	vector	= new Vector();
	private HashMap	hash	= new HashMap();
	
	public void add(Object key, Object value)
	{
		this.vector.add(value);
		this.hash.put(key, value);
	}

	public Object get(Object key)
	{
		return this.hash.get(key);
	}

	/**
	 * @param pos
	 * @return
	 */
	public Object get(int pos)
	{
		return this.vector.get(pos);
	}

	/**
	 * @return
	 */
	public Iterator iterator()
	{
		return this.vector.iterator();
	}

	/**
	 * @return
	 */
	public int size()
	{
		return this.vector.size();
	}

	/**
	 * 
	 */
	public void clear()
	{
		this.vector.clear();
		this.hash.clear();
	}
}
