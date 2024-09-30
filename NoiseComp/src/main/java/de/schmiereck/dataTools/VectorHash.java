package de.schmiereck.dataTools;

import de.schmiereck.noiseComp.generator.InputData;

import java.util.*;

/**
 * 	Manages a list of Objects in a Vector and in a Hash.
 * 	Provides functions to access this list as a sorted Vector and
 * 	as a hash with keys.
 *
 * @author smk
 * @version 21.02.2004
 */
public class VectorHash<K, V>
{
	private Vector<V>	vector	= new Vector<>();
	private HashMap<K, V>	hash	= new HashMap<>();
	
	/**
	 * @see Vector#add(java.lang.Object)
	 * @see HashMap#put(java.lang.Object, java.lang.Object)
	 */
	public V add(K key, V value)
	{
		this.vector.add(value);
		return this.hash.put(key, value);
	}

	/**
	 * @see HashMap#get(java.lang.Object)
	 */
	public V get(Object key)
	{
		return this.hash.get(key);
	}

	/**
	 * @see Vector#get(int)
	 */
	public V get(int pos)
	{
		return this.vector.get(pos);
	}

	/**
	 * @see Vector#iterator()
	 */
	public Iterator iterator()
	{
		return this.vector.iterator();
	}

	/**
	 * @see Vector#size()
	 */
	public int size()
	{
		return this.vector.size();
	}

	/**
	 * @see Vector#clear()
	 * @see HashMap#clear()
	 */
	public void clear()
	{
		this.vector.clear();
		this.hash.clear();
	}

	/**
	 * @see Vector#remove(java.lang.Object)
	 * @see HashMap#remove(java.lang.Object)
	 */
	public void remove(Object key, Object value)
	{
		this.vector.remove(value);
		this.hash.remove(key);
		/*
		int pos = 0;
		Iterator iterator = this.vector.iterator();
		
		while (iterator.hasNext())
		{
			Object element = (Object) iterator.next();
			
			if (element.equals(key) == true)
			{
				this.vector.remove(pos);
				break;
			}
			pos++;
		}
		*/
	}

	/**
	 * @see Vector#remove(java.lang.Object)
	 * @see HashMap#remove(java.lang.Object)
	 */
	public V remove(Object key)
	{
		V removedValue = this.hash.remove(key);
		this.vector.remove(removedValue);
		/*
		int pos = 0;
		Iterator iterator = this.vector.iterator();

		while (iterator.hasNext())
		{
			Object element = (Object) iterator.next();

			if (element.equals(key) == true)
			{
				this.vector.remove(pos);
				break;
			}
			pos++;
		}
		*/
		return removedValue;
	}

	public Set<K> keySet() {
		return this.hash.keySet();
	}

	public Set<Map.Entry<K,V>> entrySet() {
		return this.hash.entrySet();
	}

	public Collection<V> values() {
		return this.vector;
	}

	public void changePositions(K selectedKey, K targetKey) {
		V selectedValue = this.hash.get(selectedKey);
		V targetValue = this.hash.get(targetKey);

		int selectedPos = -1;
		int targetPos = -1;

		for (int pos = 0; pos < this.vector.size(); pos++) {
			V value = this.vector.get(pos);
			if (selectedPos == -1) {
				if (value.equals(selectedValue)) {
					selectedPos = pos;
					if (targetPos != -1) {
						break;
					}
				}
			}
			if (targetPos == -1) {
				if (value.equals(targetValue)) {
					targetPos = pos;
					if (selectedPos != -1) {
						break;
					}
				}
			}
		}
		this.vector.set(selectedPos, selectedValue);
		this.vector.set(targetPos, targetValue);
	}
}
