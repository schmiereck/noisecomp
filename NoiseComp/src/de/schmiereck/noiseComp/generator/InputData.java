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
	 * Is the Generator owning this input.
	 */
	private Generator ownerGenerator;
	
	/**
	 * The generator generates the input value (see {@link #inputModulInputTypeData} ???).<br/>
	 * null if {@link #inputValue} is used.
	 */
	private Generator 	inputGenerator;
	
	/**
	 * The type of the input of the modul used to get the input value  
	 * (the modul generator is {@link #inputGenerator}) ???
	 */
	private InputTypeData			inputModulInputTypeData;
	
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
	 * Data of the Input of a modul input.<br/>
	 * Type of the input, the values of inputs of the modul of this type added together.
	 */
	//private String				inputModulInput;
	
	/**
	 * Constructor.
	 * 
	 */
	public InputData(Generator ownerGenerator,
					 Generator inputGenerator, 
					 InputTypeData inputTypeData, 
					 //String inputModulInput)
					 InputTypeData inputModulInputTypeData)
	{
		super();
		this.ownerGenerator = ownerGenerator;
		this.inputGenerator = inputGenerator;
		this.inputTypeData = inputTypeData;
		//this.inputModulInput = inputModulInput;
		this.inputModulInputTypeData = inputModulInputTypeData;
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
		if (this.inputGenerator != inputGenerator)
		{
			this.inputGenerator = inputGenerator;
			
			this.ownerGenerator.generateChangedEvent();
		}
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
	public void setInputTypeData(InputTypeData inputTypeData)
	{
		if (this.inputTypeData != inputTypeData)
		{
			this.inputTypeData = inputTypeData;
			
			this.ownerGenerator.generateChangedEvent();
		}
	}
	 */

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
		if (InputData.compareEqualValues(this.inputValue, inputValue) == false)
		{
			this.inputValue = inputValue;
System.out.println("InputData.setInputValue: " + inputValue);

			this.ownerGenerator.generateChangedEvent();
		}
	}
	
	private static boolean compareEqualValues(Float value1, Float value2)
	{
		boolean ret;
		
		if (value1 == null)
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
		else
		{
			if (value2 == null)
			{
				ret = false;
			}
			else
			{
				ret = value1.equals(value2);
			}
		}
		
		return ret;
	}
	
	/**
	 * @return the attribute {@link #inputModulInput}.
	public String getInputModulInput()
	{
		return this.inputModulInput;
	}
	 */
	/**
	 * @param inputModulInput is the new value for attribute {@link #inputModulInput} to set.
	public void setInputModulInput(String inputModulInput)
	{
		this.inputModulInput = inputModulInput;
	}
	 */
	
	/**
	 * @return the attribute {@link #inputModulInputTypeData}.
	 */
	public InputTypeData getInputModulInputTypeData()
	{
		return this.inputModulInputTypeData;
	}
	/**
	 * @param inputModulInputTypeData is the new value for attribute {@link #inputModulInputTypeData} to set.
	 */
	public void setInputModulInputTypeData(InputTypeData inputModulInputTypeData)
	{
		if (this.inputModulInputTypeData != inputModulInputTypeData)
		{
			this.inputModulInputTypeData = inputModulInputTypeData;
		
			this.ownerGenerator.generateChangedEvent();
		}
	}

	/*
	public void setInput(Generator inputGenerator, 
						 InputTypeData inputTypeData, 
						 Float inputGeneratorValue, 
						 InputTypeData inputModulInputTypeData)
	{
		this.inputGenerator = inputGenerator;
		this.inputTypeData = inputTypeData;
		this.setInputValue(inputGeneratorValue);
		this.inputModulInputTypeData = inputModulInputTypeData;
		
		this.ownerGenerator.generateChangedEvent();
	}
	*/
	/**
	 * @return returns the {@link #ownerGenerator}.
	 */
	public Generator getOwnerGenerator()
	{
		return this.ownerGenerator;
	}
}
