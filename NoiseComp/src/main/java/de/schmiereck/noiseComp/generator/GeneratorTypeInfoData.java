package de.schmiereck.noiseComp.generator;

import de.schmiereck.noiseComp.generator.module.ModuleGenerator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

/**
 * <p>
 * 	Generator-Type Data.
 * </p>
 *
 * @author smk
 * @version 21.02.2004
 */
public class GeneratorTypeInfoData
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Folder-Path in Format <code>"/folder1/folder2/"</code>.
	 */
	private String folderPath;
	
	/**
	 * Class-name of Generator.
	 */
	private Class<? extends Generator> generatorClass;
	
	/**
	 * {@link GeneratorTypeInfoData#generatorTypeName} of the generator type.
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
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * @param folderPath
	 * 			is the Folder-Path in format <code>"/folder1/folder2/"</code>.
	 * @param generatorTypeName
	 * 			is the {@link GeneratorTypeInfoData#generatorTypeName} of the generator type.
	 */
	public GeneratorTypeInfoData(String folderPath,
								 Class<? extends Generator> generatorClass,
								 String generatorTypeName,
								 String generatorTypeDescription)
	{
		//==========================================================================================
		this.folderPath = folderPath;
		this.generatorClass = generatorClass;
		this.generatorTypeName = generatorTypeName;
		this.generatorTypeDescription = generatorTypeDescription;
		//==========================================================================================
	}

	/**
	 * @see #inputTypesData
	 */
	public void addInputTypeData(InputTypeData inputTypeData)
	{
		this.inputTypesData.addInputTypeData(inputTypeData);
	}
	
	/**
	 * @see #inputTypesData
	 */
	public void removeInputTypeData(InputTypeData inputTypeData)
	{
		this.inputTypesData.removeInputTypeData(inputTypeData);
	}

	/**
	 * @return 
	 * 			returns the {@link #folderPath}.
	 */
	public String getFolderPath()
	{
		return this.folderPath;
	}

	/**
	 * @param folderPath 
	 * 			to set {@link #folderPath}.
	 */
	public void setFolderPath(String path)
	{
		this.folderPath = path;
	}
	
	/**
	 * @return 
	 * 			the attribute {@link #generatorClass}.
	 */
	public Class<? extends Generator> getGeneratorClass()
	{
		return this.generatorClass;
	}

	/**
	 * @see #generatorClass
	 * @see #generatorTypeName
	 */
	public String getGeneratorTypeClassName()
	{
		//==========================================================================================
		String generatorTypeClassName;
		
		if (this.generatorClass != null)
		{
			if (this.generatorClass.equals(ModuleGenerator.class) == true)
			{
				generatorTypeClassName = this.getGeneratorClass().getName() + "#" + this.getGeneratorTypeName();
			}
			else
			{
				generatorTypeClassName = this.getGeneratorClass().getName();
			}
		}
		else
		{
			generatorTypeClassName = "";
		}
		
		//==========================================================================================
		return generatorTypeClassName;
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
	public Iterator<InputTypeData> getInputTypesIterator()
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
	 * 			is the genrator name.
	 * @param frameRate
	 * 			is the frame rate.
	 * @return
	 * 			the generator.
	 */
	public Generator createGeneratorInstance(String generatorName, 
	                                         float frameRate)//, ModuleGenerator parentModuleGenerator)
	{
		//==========================================================================================
		Generator generator = null;
		
		//try
		{
//			Class pageViewClass = ;
			Class<?>[]	params		= new Class[3];
			
			params[0] = String.class;				// generatorName
			params[1] = Float.class;				// frameRate
			params[2] = GeneratorTypeInfoData.class;	// generatorTypeData
			//params[3] = ModuleGenerator.class;		// parentModuleGenerator
			
			try
			{
				Constructor<? extends Generator> generatorConstructor = this.generatorClass.getConstructor(params);
				Object[]	args	= new Object[3];

				args[0] = generatorName;
				args[1] = Float.valueOf(frameRate);
				args[2] = this;
				//args[3] = parentModuleGenerator;
				
				try
				{
					//pageView = (HTMLPageView)Class.forName(pageViewClassName).newInstance();
					generator = (Generator)generatorConstructor.newInstance(args);
				}
				catch (java.lang.InstantiationException ex)
				{
					throw new RuntimeException("New instance \"" + this.generatorClass.getName() + "\".", ex);
				}
				catch (java.lang.IllegalAccessException ex)
				{
					throw new RuntimeException("Access exception for class \"" + this.generatorClass.getName() + "\".", ex);
				}
				//catch (java.lang.ClassNotFoundException ex)
				//{
				//	throw new RuntimeException("class not found: " + pageViewClassName, ex);
				//} 
				catch (IllegalArgumentException ex)
				{
					throw new RuntimeException("Illegal argument \"" + this.generatorClass.getName() + "\".", ex);
				} 
				catch (InvocationTargetException ex)
				{
					throw new RuntimeException("Invocation target \"" + this.generatorClass.getName() + "\".", ex);
				}
			}
			catch (java.lang.NoSuchMethodException ex)
			{
				throw new RuntimeException("No such method exception for class \"" + this.generatorClass.getName() + "\".", ex);
			}
		}
		//catch (ClassNotFoundException ex)
		//{
		//	throw new RuntimeException("class not found: " + this.generatorClass.getName(), ex);
		//}
		
		//==========================================================================================
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return this.generatorTypeName + " (" + this.generatorTypeDescription + ")";
	}
	
}
