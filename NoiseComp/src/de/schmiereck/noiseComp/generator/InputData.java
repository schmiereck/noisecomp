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
	private InputTypeData 		inputTypeData;
	
	/**
	 * Constructor.
	 * 
	 * @param inputGenerator
	 * @param inputType
	 */
	public InputData(Generator inputGenerator, InputTypeData inputTypeData)
	{
		super();
		this.inputGenerator = inputGenerator;
		this.inputTypeData = inputTypeData;
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
	 * @return the attribute {@link #inputTypeData}.
	 */
	public InputTypeData getInputTypeData()
	{
		return this.inputTypeData;
	}
	/**
	 * @param inputTypeData is the new value for attribute {@link #inputTypeData} to set.
	 */
	public void setInputType(InputTypeData inputTypeData)
	{
		this.inputTypeData = inputTypeData;
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
