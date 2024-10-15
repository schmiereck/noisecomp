package de.schmiereck.noiseComp.generator;

import de.schmiereck.noiseComp.soundSource.SoundSourceData;

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
	 * The generator generates the input value (see {@link #inputModuleInputTypeData} ???).<br/>
	 * <code>null</code> if {@link #inputValue} is used.
	 */
	private Generator 	inputGenerator;
	
	/**
	 * The type of the input of the module used to get the input value  
	 * (the module generator is {@link #inputGenerator}) ???
	 */
	private InputTypeData			inputModuleInputTypeData;
	
	/**
	 * Constant Value of the input.<br/>
	 * <code>null</code> if no input value is used, otherwise {@link #inputGenerator} is used. 
	 */
	private Float 		inputValue;
	
	/**
	 * String representation of the constant Value {@link #inputValue}.<br/>
	 * Sometimes <code>null</code>, only used for specals. 
	 */
	private String inputStringValue;
	
	/**
	 * The type of the input as defined in the {@link GeneratorTypeInfoData} of the
	 * Generator used this input.
	 */
	private InputTypeData 		inputTypeData;
	
	/**
	 * Data of the Input of a module input.<br/>
	 * Type of the input, the values of inputs of the module of this type added together.
	 */
	//private String				inputModuleInput;
	
	/**
	 * Constructor.
	 * 
	 */
	public InputData(Generator ownerGenerator,
					 Generator inputGenerator, 
					 InputTypeData inputTypeData, 
					 //String inputModuleInput)
					 InputTypeData inputModuleInputTypeData)
	{
		super();
		this.ownerGenerator = ownerGenerator;
		this.inputGenerator = inputGenerator;
		this.inputTypeData = inputTypeData;
		//this.inputModuleInput = inputModuleInput;
		this.inputModuleInputTypeData = inputModuleInputTypeData;
	}
	/**
	 * @return 
	 * 			the {@link #inputGenerator}.
	 */
	public Generator getInputGenerator()
	{
		return this.inputGenerator;
	}
	/**
	 * @param inputGenerator 
	 * 			is the new value for attribute {@link #inputGenerator} to set.
	 */
	public void setInputGenerator(final SoundSourceData soundSourceData, Generator inputGenerator)
	{
		if (this.inputGenerator != inputGenerator)
		{
			this.inputGenerator = inputGenerator;
			
			this.ownerGenerator.generateChangedEvent(soundSourceData);
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
	 * Only supported for new Inputs. Don't know why...
	 * 
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
	 * @see #inputStringValue
	 */
	public String getInputStringValue()
	{
		return this.inputStringValue;
	}

	/**
	 * @see #inputValue
	 * @see #inputStringValue
	 */
	public void setInputValue(final SoundSourceData soundSourceData, Float inputValue, String inputStringValue)
	{
		if ((InputData.compareEqualValues(this.inputValue, inputValue) == false) ||
			(InputData.compareEqualValues(this.inputStringValue, inputStringValue) == false))
		{
			this.inputValue = inputValue;
			this.inputStringValue = inputStringValue;
System.out.println("InputData.setInputValue: " + inputValue + ", " + inputStringValue);

			this.ownerGenerator.generateChangedEvent(soundSourceData);
		}
	}
	
	private static boolean compareEqualValues(Object value1, Object value2)
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
	 * @return the attribute {@link #inputModuleInput}.
	public String getInputModuleInput()
	{
		return this.inputModuleInput;
	}
	 */
	/**
	 * @param inputModuleInput is the new value for attribute {@link #inputModuleInput} to set.
	public void setInputModuleInput(String inputModuleInput)
	{
		this.inputModuleInput = inputModuleInput;
	}
	 */
	
	/**
	 * @return the attribute {@link #inputModuleInputTypeData}.
	 */
	public InputTypeData getInputModuleInputTypeData()
	{
		return this.inputModuleInputTypeData;
	}
	/**
	 * @param inputModuleInputTypeData is the new value for attribute {@link #inputModuleInputTypeData} to set.
	 */
	public void setInputModuleInputTypeData(final SoundSourceData soundSourceData, InputTypeData inputModuleInputTypeData)
	{
		if (this.inputModuleInputTypeData != inputModuleInputTypeData)
		{
			this.inputModuleInputTypeData = inputModuleInputTypeData;
		
			this.ownerGenerator.generateChangedEvent(soundSourceData);
		}
	}

	/*
	public void setInput(Generator inputGenerator, 
						 InputTypeData inputTypeData, 
						 Float inputGeneratorValue, 
						 InputTypeData inputModuleInputTypeData)
	{
		this.inputGenerator = inputGenerator;
		this.inputTypeData = inputTypeData;
		this.setInputValue(inputGeneratorValue);
		this.inputModuleInputTypeData = inputModuleInputTypeData;
		
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
