package de.schmiereck.noiseComp.generator;

/**
 * Verwaltet einen {@link Generator} der als Input benutzt wird und den
 * Typ des Eingangs.
 *
 * @author smk
 * @version 01.02.2004
 */
public class InputData
{
	/**
	 * The generator generates the input value.<br/>
	 * null if {@link #inputValue} is used.
	 */
	private Generator 	inputGenerator;
	
	/**
	 * Constant Value of the input.<br/>
	 * null, if no input value is used, otherwise {@link #inputGenerator} is used. 
	 */
	private Float 		inputValue;
	
	/**
	 * The type of the input as defined in the {@link GeneratorTypeData} of the
	 * Generator used this input.
	 */
	private int 		inputType;
	
	/**
	 * Constructor.
	 * 
	 * @param inputGenerator
	 * @param inputType
	 */
	public InputData(Generator inputGenerator, int inputType)
	{
		super();
		this.inputGenerator = inputGenerator;
		this.inputType = inputType;
	}
	/**
	 * @return the attribute {@link #inputGenerator}.
	 */
	public Generator getInputGenerator()
	{
		return this.inputGenerator;
	}
	/**
	 * @param inputGenerator is the new value for attribute {@link #inputGenerator} to set.
	 */
	public void setInputGenerator(Generator inputGenerator)
	{
		this.inputGenerator = inputGenerator;
	}
	/**
	 * @return the attribute {@link #inputType}.
	 */
	public int getInputType()
	{
		return this.inputType;
	}
	/**
	 * @param inputType is the new value for attribute {@link #inputType} to set.
	 */
	public void setInputType(int inputType)
	{
		this.inputType = inputType;
	}
	/**
	 * @see #inputType
	 */
	public void setInputType(Integer inputType)
	{
		this.inputType = inputType.intValue();
	}
	/**
	 * @see #inputValue
	 */
	public Float getInputValue()
	{
		return this.inputValue;
	}
	/**
	 * @see #inputValue
	 */
	public void setInputValue(Float inputValue)
	{
		this.inputValue = inputValue;
	}
}
