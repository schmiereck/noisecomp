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
	private String inputDescription = null;
	
	/**
	 * Constructor.
	 * 
	 * @param inputType
	 * @param inputTypeName
	 */
	public InputTypeData(int inputType, String inputTypeName, String inputDescription)
	{
		super();
		this.inputType = inputType;
		this.inputTypeName = inputTypeName;
		this.inputDescription = inputDescription;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param inputType
	 * @param inputTypeName
	 * @param inputCountMax
	 * @param inputCountMin
	 */
	public InputTypeData(int inputType, String inputTypeName, int inputCountMax, int inputCountMin, String inputDescription)
	{
		super();
		this.inputType = inputType;
		this.inputTypeName = inputTypeName;
		this.inputCountMax = inputCountMax;
		this.inputCountMin = inputCountMin;
		this.inputDescription = inputDescription;
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
						 Float defaultValue, String inputDescription)
	{
		super();
		this.inputType = inputType;
		this.inputTypeName = inputTypeName;
		this.inputCountMax = inputCountMax;
		this.inputCountMin = inputCountMin;
		this.defaultValue = defaultValue;
		this.inputDescription = inputDescription;
	}

	/**
	 * Constructor.
	 * 
	 */
	public InputTypeData(Integer inputTypeType, String inputTypeName, 
			Integer inputTypeCountMax, Integer inputTypeCountMin, 
			Float defaultValue, String inputDescription)
	{
		super();
		this.inputType = inputTypeType.intValue();
		this.inputTypeName = inputTypeName;

		int countMin;

		if (inputTypeCountMin != null)
		{
			countMin = inputTypeCountMin.intValue();
		}
		else
		{
			countMin = -1;
		}

		int countMax;

		if (inputTypeCountMax != null)
		{
			countMax = inputTypeCountMax.intValue();
		}
		else
		{
			countMax = -1;
		}
		
		this.inputCountMax = countMax;
		this.inputCountMin = countMin;
		
		this.defaultValue = defaultValue;
		this.inputDescription = inputDescription;
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
	 * @return the attribute {@link #inputDescription}.
	 */
	public String getInputDescription()
	{
		return this.inputDescription;
	}
}
