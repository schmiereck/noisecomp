package de.schmiereck.noiseComp.generator;

import java.util.Iterator;
import de.schmiereck.dataTools.VectorHash;

/**
 * Input-Types Data.
 *
 * @author smk
 * @version <p>21.03.2004: created, smk</p>
 */
public class InputTypesData
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * List of the allowed {@link InputTypeData}-Objects of this Generator-Type.
	 */
	private VectorHash<Integer, InputTypeData> inputTypes = new VectorHash<Integer, InputTypeData>();

	//**********************************************************************************************
	// Functions:
	
	/**
	 * @param inputTypeData
	 * 			is the input type to add to {@link #inputTypes}.
	 */
	public void addInputTypeData(InputTypeData inputTypeData)
	{
		this.inputTypes.add(Integer.valueOf(inputTypeData.getInputType()), inputTypeData);
	}

	/**
	 * @param inputType
	 * 			is the {@link InputTypeData#getInputType()}.
	 * @return
	 * 			the input type of given type.<br/>
	 * 			<code>null</code> if input type not found.
	 */
	public InputTypeData getInputTypeData(int inputType)
	{
		return (InputTypeData)this.inputTypes.get(Integer.valueOf(inputType));
	}

	/**
	 * @param inputTypeData
	 * 			the input type to remove from {@link #inputTypes}.
	 */
	public void removeInputTypeData(InputTypeData inputTypeData)
	{
		this.inputTypes.remove(Integer.valueOf(inputTypeData.getInputType()), inputTypeData);
	}
	
	/**
	 * @return
	 * 			the iterator of {@link #inputTypes}.
	 */
	public Iterator<InputTypeData> getInputTypesIterator()
	{
		return this.inputTypes.iterator();
	}

	/**
	 * @param pos
	 * 			is the position.
	 * @return
	 * 			the input type of {@link #inputTypes} at given position.
	 */
	public InputTypeData getInputTypeDataByPos(int pos)
	{
		return this.inputTypes.get(pos);
	}

	/**
	 * @return
	 * 			the size of {@link #inputTypes}.
	 */
	public int getInputTypesSize()
	{
		return this.inputTypes.size();
	}

}
