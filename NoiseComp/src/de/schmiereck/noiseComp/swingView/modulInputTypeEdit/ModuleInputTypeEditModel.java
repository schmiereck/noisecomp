/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulInputTypeEdit;

import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;

/**
 * <p>
 * 	Modul-Input-Type Edit Model.
 * </p>
 * 
 * @author smk
 * @version <p>20.09.2010:	created, smk</p>
 */
public class ModuleInputTypeEditModel
{
	//**********************************************************************************************
	// Fields:

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Input Generator.
	 */
	private InputTypeData inputTypeData = null;

	/**
	 * {@link #inputTypeData} changed listeners.
	 */
	private final ModelPropertyChangedNotifier inputTypeDataChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Input-Type ID.
	 */
	private Integer inputTypeID = null;

	/**
	 * {@link #inputTypeID} changed listeners.
	 */
	private final ModelPropertyChangedNotifier inputTypeIDChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Input-Type Name.
	 */
	private String inputTypeName = null;

	/**
	 * {@link #inputTypeName} changed listeners.
	 */
	private final ModelPropertyChangedNotifier inputTypeNameChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Input-Type Default-Value.
	 */
	private Float inputTypeDefaultValue = null;

	/**
	 * {@link #inputTypeDefaultValue} changed listeners.
	 */
	private final ModelPropertyChangedNotifier inputTypeDefaultValueChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Input-Type Description.
	 */
	private String inputTypeDescription = null;

	/**
	 * {@link #inputTypeDescription} changed listeners.
	 */
	private final ModelPropertyChangedNotifier inputTypeDescriptionChangedNotifier = new ModelPropertyChangedNotifier();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @param inputTypeData 
	 * 			to set {@link #inputTypeData}.
	 */
	public void setInputTypeData(InputTypeData inputTypeData)
	{
		this.inputTypeData = inputTypeData;
		
		// Notify Listeners.
		this.inputTypeDataChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeDataChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getInputTypeDataChangedNotifier()
	{
		return this.inputTypeDataChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeID}.
	 */
	public Integer getInputTypeID()
	{
		return this.inputTypeID;
	}

	/**
	 * @param inputTypeID 
	 * 			to set {@link #inputTypeID}.
	 */
	public void setInputTypeID(Integer inputTypeID)
	{
		this.inputTypeID = inputTypeID;
		
		// Notify listeners.
		this.inputTypeIDChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeIDChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getInputTypeIDChangedNotifier()
	{
		return this.inputTypeIDChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeDefaultValue}.
	 */
	public Float getInputTypeDefaultValue()
	{
		return this.inputTypeDefaultValue;
	}

	/**
	 * @param inputTypeDefaultValue 
	 * 			to set {@link #inputTypeDefaultValue}.
	 */
	public void setInputTypeDefaultValue(Float inputTypeDefaultValue)
	{
		this.inputTypeDefaultValue = inputTypeDefaultValue;
		
		// Notify listeners.
		this.inputTypeDefaultValueChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeDefaultValueChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getInputTypeDefaultValueChangedNotifier()
	{
		return this.inputTypeDefaultValueChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeName}.
	 */
	public String getInputTypeName()
	{
		return this.inputTypeName;
	}

	/**
	 * @param inputTypeName 
	 * 			to set {@link #inputTypeName}.
	 */
	public void setInputTypeName(String inputTypeName)
	{
		this.inputTypeName = inputTypeName;
		
		// Notify listeners.
		this.inputTypeNameChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeNameChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getInputTypeNameChangedNotifier()
	{
		return this.inputTypeNameChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeDescription}.
	 */
	public String getInputTypeDescription()
	{
		return this.inputTypeDescription;
	}

	/**
	 * @param inputTypeDescription
	 * 			to set {@link #inputTypeDescription}.
	 */
	public void setInputTypeDescription(String inputTypeDescription)
	{
		this.inputTypeDescription = inputTypeDescription;
		
		// Notify listeners.
		this.inputTypeDescriptionChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeDescriptionChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getInputTypeDescriptionChangedNotifier()
	{
		return this.inputTypeDescriptionChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeData}.
	 */
	public InputTypeData getInputTypeData()
	{
		return this.inputTypeData;
	}
}
