package de.schmiereck.noiseComp.generator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

/**
 * TODO docu
 *
 * @author smk
 * @version 21.02.2004
 */
public class GeneratorTypeData
{
	private Class generatorClass;
	/**
	 * Name of the generator type.
	 */
	private String generatorTypeName;
	
	/**
	 * Is the description of the generator type.
	 */
	private String generatorTypeDescription;
	
	/**
	 * List of the allowed {@link InputTypeData}-Objects of this Generator-Type.
	 */
	private InputTypesData inputTypesData = new InputTypesData();
	
	/**
	 * Constructor.
	 * 
	 * @param generatorTypeName
	 */
	public GeneratorTypeData(Class generatorClass, String generatorTypeName, String generatorTypeDescription)
	{
		super();
		this.generatorClass = generatorClass;
		this.generatorTypeName = generatorTypeName;
		this.generatorTypeDescription = generatorTypeDescription;
	}

	/**
	 * @see #inputTypesData
	 */
	public void addInputTypeData(InputTypeData inputTypeData)
	{
		this.inputTypesData.addInputTypeData(inputTypeData);
	}
	/**
	 * @return the attribute {@link #generatorClass}.
	 */
	public Class getGeneratorClass()
	{
		return this.generatorClass;
	}

	/**
	 * @see #generatorClass
	 * @see #generatorTypeName
	 */
	public String getGeneratorTypeClassName()
	{
		String generatorTypeClassName;
		
		if (this.generatorClass.equals(ModulGenerator.class) == true)
		{
			generatorTypeClassName = this.getGeneratorClass().getName() + "#" + this.getGeneratorTypeName();
		}
		else
		{
			generatorTypeClassName = this.getGeneratorClass().getName();
		}
		
		return generatorTypeClassName;
	}
	
	public InputTypeData getInputTypeData(int inputType)
	{
		return this.inputTypesData.getInputTypeData(inputType);
	}
	/**
	 * @return the attribute {@link #generatorTypeName}.
	 */
	public String getGeneratorTypeName()
	{
		return this.generatorTypeName;
	}
	
	/**
	 * @see #generatorTypeDescription
	 */
	public String getGeneratorTypeDescription()
	{
		return this.generatorTypeDescription;
	}
	
	/**
	 * @see #inputTypesData
	 */
	public Iterator getInputTypesIterator()
	{
		return this.inputTypesData.getInputTypesIterator();
	}
	
	/**
	 * @see #inputTypesData
	 */
	public InputTypesData getInputTypesData()
	{
		return this.inputTypesData;
	}
	
	/**
	 * @param generatorName
	 * @param frameRate
	 * @return
	 */
	public Generator createGeneratorInstance(String generatorName, float frameRate)//, ModulGenerator parentModulGenerator)
	{
		Generator generator = null;
		
		//try
		{
//			Class pageViewClass = ;
			Class[]	params		= new Class[3];
			
			params[0] = String.class;				// generatorName
			params[1] = Float.class;				// frameRate
			params[2] = GeneratorTypeData.class;	// generatorTypeData
			//params[3] = ModulGenerator.class;		// parentModulGenerator
			
			try
			{
				Constructor generatorConstructor = this.generatorClass.getConstructor(params);
				Object[]	args	= new Object[3];

				args[0] = generatorName;
				args[1] = Float.valueOf(frameRate);
				args[2] = this;
				//args[3] = parentModulGenerator;
				
				try
				{
					//pageView = (HTMLPageView)Class.forName(pageViewClassName).newInstance();
					generator = (Generator)generatorConstructor.newInstance(args);
				}
				catch (java.lang.InstantiationException ex)
				{
					throw new RuntimeException("new instance: " + this.generatorClass.getName(), ex);
				}
				catch (java.lang.IllegalAccessException ex)
				{
					throw new RuntimeException("access exception for class: " + this.generatorClass.getName(), ex);
				}
				//catch (java.lang.ClassNotFoundException ex)
				//{
				//	throw new RuntimeException("class not found: " + pageViewClassName, ex);
				//} 
				catch (IllegalArgumentException ex)
				{
					throw new RuntimeException("illegal argument: " + this.generatorClass.getName(), ex);
				} 
				catch (InvocationTargetException ex)
				{
					throw new RuntimeException("invocation target: " + this.generatorClass.getName(), ex);
				}
			}
			catch (java.lang.NoSuchMethodException ex)
			{
				throw new RuntimeException("no such method exception for class: " + this.generatorClass.getName(), ex);
			}
		}
		//catch (ClassNotFoundException ex)
		//{
		//	throw new RuntimeException("class not found: " + this.generatorClass.getName(), ex);
		//}
		
		return generator;
	}
	/**
	 * @param generatorTypeDescription is the new value for attribute {@link #generatorTypeDescription} to set.
	 */
	public void setGeneratorTypeDescription(String generatorTypeDescription)
	{
		this.generatorTypeDescription = generatorTypeDescription;
	}
	/**
	 * @param generatorTypeName is the new value for attribute {@link #generatorTypeName} to set.
	 */
	public void setGeneratorTypeName(String generatorTypeName)
	{
		this.generatorTypeName = generatorTypeName;
	}
}
