package de.schmiereck.noiseComp.generator;

/**
 * TODO docu
 *
 * @author smk
 * @version 21.02.2004
 */
public class InputTypeData
{
	/**
	 * ID of the input type.
	 */
	private int inputType;
	
	/**
	 * Name of the input type.
	 */
	private String inputTypeName;
	
	/**
	 * Maximum count of inputs of this type.<br/>
	 * -1 is: no limit
	 */
	private int inputCountMax	= -1;

	/**
	 * Minimum count of inputs of this type.<br/>
	 * -1 is: no limit
	 */
	private int inputCountMin	= -1;
	
	/**
	 * Default Value of the input.
	 */
	private Float defaultValue = null;
	
	/**
	 * Description of the input type.
	 */
	private String inputTypeDescription = null;

	/**
	 * Constructor.
	 * 
	 * @param inputType
	 * @param inputTypeName
	 */
	public InputTypeData(int inputType, String inputTypeName, String inputTypeDescription)
	{
		super();
		this.inputType = inputType;
		this.inputTypeName = inputTypeName;
		this.inputTypeDescription = inputTypeDescription;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param inputType
	 * @param inputTypeName
	 * @param inputCountMax
	 * @param inputCountMin
	 */
	public InputTypeData(int inputType, String inputTypeName, int inputCountMax, int inputCountMin, String inputTypeDescription)
	{
		super();
		this.inputType = inputType;
		this.inputTypeName = inputTypeName;
		this.inputCountMax = inputCountMax;
		this.inputCountMin = inputCountMin;
		this.inputTypeDescription = inputTypeDescription;
	}

	/**
	 * Constructor.
	 * 
	 * @param inputType
	 * @param inputTypeName
	 * @param inputCountMax
	 * @param inputCountMin
	 */
	public InputTypeData(int inputType, String inputTypeName, 
			Integer inputCountMax, Integer inputCountMin, 
			Float defaultValue, String inputTypeDescription)
	{
		super();
		this.inputType = inputType;
		this.inputTypeName = inputTypeName;
		if (inputCountMax != null)
		{	
			this.inputCountMax = inputCountMax.intValue();
		}
		else
		{	
			this.inputCountMax = -1;
		}
		if (inputCountMin != null)
		{	
			this.inputCountMin = inputCountMin.intValue();
		}
		else
		{	
			this.inputCountMin = -1;
		}
		this.defaultValue = defaultValue;
		this.inputTypeDescription = inputTypeDescription;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param inputType
	 * @param inputTypeName
	 * @param inputCountMax
	 * @param inputCountMin
	 */
	public InputTypeData(int inputType, String inputTypeName, int inputCountMax, int inputCountMin, 
						 Float defaultValue, String inputTypeDescription)
	{
		super();
		this.inputType = inputType;
		this.inputTypeName = inputTypeName;
		this.inputCountMax = inputCountMax;
		this.inputCountMin = inputCountMin;
		this.defaultValue = defaultValue;
		this.inputTypeDescription = inputTypeDescription;
	}

	/**
	 * Constructor.
	 * 
	 */
	public InputTypeData(Integer inputTypeType, String inputTypeName, 
			Integer inputTypeCountMax, Integer inputTypeCountMin, 
			Float defaultValue, String inputTypeDescription)
	{
		super();
		this.inputType = inputTypeType.intValue();
		this.inputTypeName = inputTypeName;
		this.setInputCountMax(inputTypeCountMax);
		this.setInputCountMin(inputTypeCountMin);
		this.defaultValue = defaultValue;
		this.inputTypeDescription = inputTypeDescription;
	}

	/**
	 * @return the attribute {@link #defaultValue}.
	 */
	public Float getDefaultValue()
	{
		return this.defaultValue;
	}
	/**
	 * @return the attribute {@link #inputCountMax}.
	 */
	public int getInputCountMax()
	{
		return this.inputCountMax;
	}
	/**
	 * @return the attribute {@link #inputCountMin}.
	 */
	public int getInputCountMin()
	{
		return this.inputCountMin;
	}
	/**
	 * @return the attribute {@link #inputType}.
	 */
	public int getInputType()
	{
		return this.inputType;
	}
	/**
	 * @return the attribute {@link #inputTypeName}.
	 */
	public String getInputTypeName()
	{
		return this.inputTypeName;
	}
	/**
	 * @return the attribute {@link #inputTypeDescription}.
	 */
	public String getInputTypeDescription()
	{
		return this.inputTypeDescription;
	}
	/**
	 * @param defaultValue is the new value for attribute {@link #defaultValue} to set.
	 */
	public void setDefaultValue(Float defaultValue)
	{
		this.defaultValue = defaultValue;
	}
	/**
	 * @param inputCountMax is the new value for attribute {@link #inputCountMax} to set.
	 */
	public void setInputCountMax(int inputCountMax)
	{
		this.inputCountMax = inputCountMax;
	}
	/**
	 * @param inputCountMin is the new value for attribute {@link #inputCountMin} to set.
	 */
	public void setInputCountMin(int inputCountMin)
	{
		this.inputCountMin = inputCountMin;
	}
	/**
	 * @param inputCountMax is the new value for attribute {@link #inputCountMax} to set (null is translated to -1).
	 */
	public void setInputCountMax(Integer inputCountMax)
	{
		if (inputCountMax != null)
		{
			this.inputCountMax = inputCountMax.intValue();
		}
		else
		{
			this.inputCountMax = -1;
		}
	}
	/**
	 * @param inputCountMin is the new value for attribute {@link #inputCountMin} to set (null is translated to -1).
	 */
	public void setInputCountMin(Integer inputCountMin)
	{
		if (inputCountMin != null)
		{
			this.inputCountMin = inputCountMin.intValue();
		}
		else
		{
			this.inputCountMin = -1;
		}
	}
	/**
	 * @param inputType is the new value for attribute {@link #inputType} to set.
	 */
	public void setInputType(int inputType)
	{
		this.inputType = inputType;
	}
	/**
	 * @param inputTypeDescription is the new value for attribute {@link #inputTypeDescription} to set.
	 */
	public void setInputTypeDescription(String inputTypeDescription)
	{
		this.inputTypeDescription = inputTypeDescription;
	}
	/**
	 * @param inputTypeName is the new value for attribute {@link #inputTypeName} to set.
	 */
	public void setInputTypeName(String inputTypeName)
	{
		this.inputTypeName = inputTypeName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return super.toString() + 
		"{" +
		"inputType:" + this.inputType + ", " +
		"inputTypeName:" + this.inputTypeName +
		"}";
	}
}
