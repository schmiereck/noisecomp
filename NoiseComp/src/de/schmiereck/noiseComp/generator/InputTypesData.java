package de.schmiereck.noiseComp.generator;

import java.util.Iterator;

import de.schmiereck.dataTools.VectorHash;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>21.03.2004: created, smk</p>
 */
public class InputTypesData
{
	/**
	 * List of the allowed {@link InputTypeData}-Objects of this Generator-Type.
	 */
	private VectorHash inputTypes = new VectorHash();

	/**
	 * @see #inputTypes
	 */
	public void addInputTypeData(InputTypeData inputTypeData)
	{
		this.inputTypes.add(Integer.valueOf(inputTypeData.getInputType()), inputTypeData);
	}

	public InputTypeData getInputTypeData(int inputType)
	{
		return (InputTypeData)this.inputTypes.get(Integer.valueOf(inputType));
	}

	/**
	 * @param inputTypeData
	 */
	public void removeInputTypeData(InputTypeData inputTypeData)
	{
		this.inputTypes.remove(Integer.valueOf(inputTypeData.getInputType()), inputTypeData);
	}
	
	/**
	 * @return
	 */
	public Iterator getInputTypesIterator()
	{
		return this.inputTypes.iterator();
	}

	/**
	 * @param pos
	 * @return
	 */
	public InputTypeData getInputTypeDataByPos(int pos)
	{
		return (InputTypeData)this.inputTypes.get(pos);
	}

	/**
	 * @return
	 */
	public int getInputTypesSize()
	{
		return this.inputTypes.size();
	}

}
