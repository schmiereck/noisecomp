package de.schmiereck.noiseComp.generator;

import java.util.Iterator;
import java.util.Vector;

import de.schmiereck.noiseComp.PopupRuntimeException;


/**
 * Implementiert die Logik eines Generators der einen Sample für eine
 * Frameposition in einem Buffer ablegt, um ihn nicht mehrfach zu berechnen.
 * <p>
 * 	The "ouput signal" is calculated based on the internal logic of the generator 
 * 	and the values of differnet inputs.<br/>
 * 	The the count and types of the acceped inputs are defined by the generator type
 * 	returned by the function {@link #createGeneratorTypeData()}.
 * </p>
 * @author smk
 * @version 22.01.2004
 */
public abstract class Generator 
implements GeneratorInterface
{
	private float startTimePos = 0.0F;
	private float endTimePos = 1.0F;
	
	private float frameRate;
	
	/**
	 * Is the unique Name of the Generator Object. 
	 */
	private String name;
	
	/**
	 * Liste aus {@link InputData}-Objekten (mit {@link Generator}-Objekten).
	 */
	private Vector inputs = null;
	
	private GeneratorTypeData generatorTypeData;

	//private GeneratorBuffer	generatorBuffer = null;
	
	/**
	 * Constructor.
	 * 
	 * @param frameRate		Frames per Second.
	 */
	public Generator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
	{
		super();
		
		this.name = name;
		this.frameRate = frameRate.floatValue();
		this.generatorTypeData = generatorTypeData;
		//this.parentModulGenerator = parentModulGenerator;
	}

	/**
	 * @return the attribute {@link #frameRate}.
	 */
	public float getFrameRate()
	{
		return this.frameRate;
	}

	/**
	 * @set #startTimePos
	 */
	public synchronized void setStartTimePos(float startTimePos)
	{
		this.startTimePos = startTimePos;
		//GeneratorBuffer generatorBuffer = this.getGeneratorBuffer();
		//generatorBuffer.setStartPosition((long)(this.startTimePos * this.getFrameRate()));
	}

	/**
	 * @set #startTimePos
	 */
	public synchronized void setEndTimePos(float endTimePos)
	{
		this.endTimePos = endTimePos;
		//GeneratorBuffer generatorBuffer = this.getGeneratorBuffer();
		//generatorBuffer.setEndPosition((long)(this.endTimePos * this.getFrameRate()));
	}

	/**
	 * @return the attribute {@link #endTimePos}.
	 */
	public float getEndTimePos()
	{
		return this.endTimePos;
	}
	/**
	 * @return the attribute {@link #startTimePos}.
	 */
	public float getStartTimePos()
	{
		return this.startTimePos;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorInterface#generateFrameSample(long, de.schmiereck.noiseComp.generator.ModulGenerator)
	 */
	public SoundSample generateFrameSample(long framePosition, ModulGenerator parentModulGenerator)
	{
		SoundSample soundSample;
		
		//GeneratorBuffer generatorBuffer = this.getGeneratorBuffer();

		// Die Frameposition in Zeit umrechnen.
		float frameTime = (framePosition / this.getFrameRate());
		
		//if (generatorBuffer.checkIsInTime(framePosition) == true)
		//if ((frameTime >= this.startTimePos) && (frameTime < this.endTimePos))
		if (this.checkIsInTime(frameTime) == true)
		{	
			//soundSample = generatorBuffer.readBuffer(framePosition);
				
			//if (soundSample == null)
			//{
				soundSample = new SoundSample();

				this.calculateSoundSample(framePosition, frameTime, soundSample, parentModulGenerator);
				
			//	generatorBuffer.writeBuffer(framePosition, soundSample);
			//}
		}
		else
		{
			soundSample = null;
		}
		return soundSample;
	}

	/**
	 * @param frameTime
	 * @return true, if the frameTime is in the generator time.
	 */
	public boolean checkIsInTime(float frameTime)
	{
		boolean ret;
		
		if ((frameTime >= this.startTimePos) && (frameTime < this.endTimePos))
		{
			ret = true;
		}
		else
		{
			ret = false;
		}
		return ret;
	}
	
	/**
	 * Berechnen des Sample-Wertes für die angegebene Frame-Position.
	 * 
	 * @param framePosition
	 * @param sample
	 */
	public abstract void calculateSoundSample(long framePosition, float frameTime, SoundSample sample, ModulGenerator parentModulGenerator);

	/**
	 * @see #name
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * @see #inputs
	 * @return the new created and added {@link InputData}-Object.
	 */
	public InputData addInputGenerator(Generator inputGenerator, 
			InputTypeData inputTypeData, Float inputValue, InputTypeData inputModulInputTypeData)
	{
		InputData inputData = new InputData(inputGenerator, inputTypeData, inputModulInputTypeData);
		
		inputData.setInputValue(inputValue);
		
		if (this.inputs == null)
		{	
			this.inputs = new Vector();
		}
			
		synchronized (this.inputs)
		{
			this.inputs.add(inputData);
		}
		
		return inputData;
	}
	
	public InputData addInputValue(float value, int inputType)
	{
		InputTypeData inputTypeData = this.getGeneratorTypeData().getInputTypeData(inputType);
		
		return this.addInputGenerator(null, inputTypeData, Float.valueOf(value), null);
	}

	/**
	 * @see #addInputGenerator(Generator, InputTypeData, Float)
	 */
	public InputData addInputValue(Float value, InputTypeData inputTypeData)
	{
		return this.addInputGenerator(null, inputTypeData, value, null);
	}

	/**
	 * @see #addInputGenerator(Generator, InputTypeData, Float)
	public InputData addInputValue(float value, InputTypeData inputTypeData)
	{
		return this.addInputGenerator(null, inputTypeData, Float.valueOf(value));
	}
	 */
	
	/**
	 * @see #inputs
	 */
	public Iterator getInputsIterator()
	{
		Iterator ret;
		
		if (this.inputs != null)
		{	
			synchronized (this.inputs)
			{
				ret = this.inputs.iterator();
			}					
		}
		else
		{
			ret = null;
		}
		return ret;
	}
	
	/**
	 * @see #inputs
	 */
	public Object getInputsSyncObject()
	{
		return this.inputs;
	}

	/**
	 * @see #inputs
	 */
	public Vector getInputs()
	{
		Vector ret;
		
		if (this.inputs != null)
		{	
			synchronized (this.inputs)
			{
				ret = this.inputs;
			}
		}
		else
		{
			ret = null;
		}
		return ret;
	}
	
	/**
	 * Searches a input by type.<br/>
	 * Throws a {@link RuntimeException} if there is more than one input of this type.
	 * 
	 * @param inputType
	 * @return
	 */
	public InputData searchInputByType(InputTypeData inputTypeData)
	{
		InputData retInputData = null;
		
		if (this.inputs != null)
		{	
			synchronized (this.inputs)
			{
				Iterator inputGeneratorsIterator = this.inputs.iterator();
				
				while (inputGeneratorsIterator.hasNext())
				{
					InputData inputData = (InputData)inputGeneratorsIterator.next();
					
					if (inputData.getInputTypeData().getInputType() == inputTypeData.getInputType())
					{
						if (retInputData != null)
						{
							throw new PopupRuntimeException("found more than one input by type " + inputTypeData + " in generator " + this.getName());
						}
						
						retInputData = inputData;
					}
				}
			}
		}
		return retInputData;
	}
	
	/**
	 * Searches a input by type.<br/>
	 * Throws a {@link RuntimeException} if there is more than one input of this type.
	 * 
	 * @param inputType
	 * @return
	 */
	public InputData searchInputByTypeName(String inputTypeName)
	{
		InputData retInputData = null;
		
		if (this.inputs != null)
		{	
			synchronized (this.inputs)
			{
				if (this.inputs != null)
				{	
					Iterator inputGeneratorsIterator = this.inputs.iterator();
					
					while (inputGeneratorsIterator.hasNext())
					{
						InputData inputData = (InputData)inputGeneratorsIterator.next();
						
						if (inputTypeName.equals(inputData.getInputTypeData().getInputTypeName()) == true)
						{
							if (retInputData != null)
							{
								throw new PopupRuntimeException("found more than one input by name \"" + inputTypeName + "\" in generator " + this.toString());
							}
							
							retInputData = inputData;
						}
					}
				}
			}
		}
		
		return retInputData;
	}
	
	/**
	 * @return
	 */
	public int getInputsCount()
	{
		int ret;
		
		if (this.inputs != null)
		{
			synchronized (this.inputs)
			{
				if (this.inputs != null)
				{	
					ret = this.inputs.size();
				}
				else
				{
					ret = 0;
				}
			}
		}
		else
		{
			ret = 0;
		}
		return ret;
	}

	/**
	 * Generator benachrichtigen 
	 * das einer der ihren gelöscht wurde (als Input entfernen usw.):
	 * 
	 * @param removedGenerator
	 */
	public void notifyRemoveGenerator(Generator removedGenerator)
	{
		if (this.inputs != null)
		{	
			synchronized (this.inputs)
			{
				if (this.inputs != null)
				{	
					Iterator inputGeneratorsIterator = this.inputs.iterator();
					
					while (inputGeneratorsIterator.hasNext())
					{
						InputData inputData = (InputData)inputGeneratorsIterator.next();
		
						Generator generator = (Generator)inputData.getInputGenerator();
						
						if (generator == removedGenerator)
						{
							synchronized (this.inputs)
							{
								inputGeneratorsIterator.remove();
							}
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * @see #name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return a scale factor for drawing the samples.
	 * 			Should be a factor that normalize all samples of the generator between -1.0 and +1.0.
	 */
	public float getGeneratorSampleDrawScale(ModulGenerator parentModulGenerator)
	{
		return 1.0F;
	}

	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(SinusGenerator.class, "Base", "Is the base of all other generators.");
		
		return generatorTypeData;
	}
	/**
	 * @return the attribute {@link #generatorTypeData}.
	 */
	public GeneratorTypeData getGeneratorTypeData()
	{
		return this.generatorTypeData;
	}
	
	/**
	 * Calculates the value for a given input for this position.
	 *
	 */
	protected void calcInputValue(long framePosition, InputData inputData, SoundSample value, ModulGenerator parentModulGenerator)
	{
		GeneratorInterface inputSoundGenerator = inputData.getInputGenerator();

		// Found Input-Generator ?
		if (inputSoundGenerator != null)
		{	
			// Use his input:
			
			SoundSample inputSoundSample = inputSoundGenerator.generateFrameSample(framePosition, parentModulGenerator);
			
			value.setValues(inputSoundSample);
		}
		else
		{
			// Found no Input-Generator:
			
			Float inputValue = inputData.getInputValue();
			
			// Found constant input value ?
			if (inputValue != null)
			{
				value.setMonoValue(inputValue.floatValue());
			}
			else
			{
				// Found no input value:

				InputTypeData modulInputTypeData = inputData.getInputModulInputTypeData();

				// Found a modul input type ?
				if (modulInputTypeData != null)
				{
					if (parentModulGenerator != null)
					{	
						// Use Value from this input:
	
						// ALTERNATIVE: irgenendwie an den ModulGenerator drannkommen, in dem dieser Generator benutzt wird und dann in dem nach dem input suchen.
						//InputData modulInputData = this.getParentModulGenerator().searchInputByTypeName(inputModulInput);
						Iterator modulInputsIterator = parentModulGenerator.getInputsIterator();
						
						if (modulInputsIterator != null)
						{
							while (modulInputsIterator.hasNext())
							{
								InputData modulInputData = (InputData)modulInputsIterator.next();
								
								if (modulInputData.getInputTypeData().getInputType() == modulInputTypeData.getInputType())
								{
									parentModulGenerator.calcInputValue(framePosition, modulInputData, value, parentModulGenerator);
									break;
								}
							}
						}
					}
					else
					{
						value.setMonoValue(modulInputTypeData.getDefaultValue());
					}
				}
				else
				{
					// Use Default Value of Input type:
				
					value.setMonoValue(this.getInputDefaultValueByInputType(inputData));
				}
			}
		}
	}

	protected void calcInputSignals(long framePosition, InputData inputData, SoundSample signal, ModulGenerator parentModulGenerator)
	{
		if (inputData != null)
		{	
			GeneratorInterface inputSoundGenerator = inputData.getInputGenerator();
	
			// Found Input-Generator ?
			if (inputSoundGenerator != null)
			{	
				// Use his input:
				
				SoundSample inputSoundSample = inputSoundGenerator.generateFrameSample(framePosition, parentModulGenerator);
				
				signal.setSignals(inputSoundSample);
			}
			else
			{
				// Found no Input-Generator:
				
				Float inputValue = inputData.getInputValue();
				
				// Found constant input value ?
				if (inputValue != null)
				{
					signal.setMonoSignal(inputValue.floatValue());
				}
				else
				{
					// Found no input value:
	
					InputTypeData modulInputTypeData = inputData.getInputModulInputTypeData();

					// Found a modul input type ?
					if ((modulInputTypeData != null) && (parentModulGenerator != null))
					{
						// Use Value from this input:

						// ALTERNATIVE: irgenendwie an den ModulGenerator drannkommen, in dem dieser Generator benutzt wird und dann in dem nach dem input suchen.
						//InputData modulInputData = this.getParentModulGenerator().searchInputByTypeName(inputModulInput);
						Iterator modulInputsIterator = parentModulGenerator.getInputsIterator();
						
						if (modulInputsIterator != null)
						{
							while (modulInputsIterator.hasNext())
							{
								InputData modulInputData = (InputData)modulInputsIterator.next();
								
								if (modulInputData.getInputTypeData().getInputType() == modulInputTypeData.getInputType())
								{
									parentModulGenerator.calcInputSignals(framePosition, modulInputData, signal, parentModulGenerator);
									break;
								}
							}
						}
					}
					else
					{
						// Found no input value:
						// Use Default Value of Input type:
						
						signal.setMonoSignal(this.getInputDefaultValueByInputType(inputData));
					}
				}
			}
		}
		else
		{
			signal.setMonoSignal(0.0F);
		}
	}
	
	protected float calcInputMonoValue(long framePosition, InputTypeData inputTypeData, ModulGenerator parentModulGenerator) //, float defaultValue)
	{
		float value;
		
		InputData inputData = this.searchInputByType(inputTypeData);
		
		if (inputData != null)
		{
			value = this.calcInputMonoValue(framePosition, inputData, parentModulGenerator);
		}
		else
		{
			//throw new RuntimeException("input type not found: " + inputType);
			value = this.getInputDefaultValueByInputType(inputTypeData);
		}
		
		return value;
	}

	protected float calcInputMonoValue(long framePosition, InputData inputData, ModulGenerator parentModulGenerator)
	{
		float value;

		GeneratorInterface inputSoundGenerator = inputData.getInputGenerator();

		// Found Input-Generator ?
		if (inputSoundGenerator != null)
		{	
			// Use his input:
			
			SoundSample inputSoundSample = inputSoundGenerator.generateFrameSample(framePosition, parentModulGenerator);
			
			if (inputSoundSample != null)
			{	
				value = inputSoundSample.getMonoValue();
			}
			else
			{
				// Found no input signal:
				
				value = 0.0F; // this.getInputDefaultValueByInputType(inputType);
			}
		}
		else
		{
			// Found no Input-Generator:
			
			Float inputValue = inputData.getInputValue();
		
			// Found constant input value ?
			if (inputValue != null)
			{
				value = inputValue.floatValue();
			}
			else
			{
				InputTypeData modulInputTypeData = inputData.getInputModulInputTypeData();

				// Found a modul input type ?
				if ((modulInputTypeData != null) && (parentModulGenerator != null))
				{
					// Use Value from this input:

					Iterator modulInputsIterator = parentModulGenerator.getInputsIterator();
					
					value = 0.0F;
					
					if (modulInputsIterator != null)
					{
						while (modulInputsIterator.hasNext())
						{
							InputData modulInputData = (InputData)modulInputsIterator.next();
							
							if (modulInputData.getInputTypeData().getInputType() == modulInputTypeData.getInputType())
							{
								value = parentModulGenerator.calcInputMonoValue(framePosition, modulInputData, parentModulGenerator);
								break;
							}
						}
					}
				}
				else
				{
					// Found no input value:
					// Use Default Value of Input type:
					
					value = this.getInputDefaultValueByInputType(inputData);
				}
			}
		}

		return value;
	}

	protected float getInputDefaultValueByInputType(InputData inputData)
	{
		float ret;
		
		InputTypeData inputTypeData = inputData.getInputTypeData();
		
		// Found InputType ? (this situation may be occourse, if load an older type from file)
		if (inputTypeData != null)
		{	
			ret = this.getInputDefaultValueByInputType(inputTypeData);
		}
		else
		{	
			ret = 0.0F;
		}
		return ret;
	}

	private float getInputDefaultValueByInputType(InputTypeData inputTypeData)
	{
		float ret;
		Float defaultValue = inputTypeData.getDefaultValue();
		
		if (defaultValue != null)
		{	
			ret = defaultValue.floatValue();
		}
		else
		{	
			ret = 0.0F;
		}
		return ret;
	}
	
	/**
	 * @return the attribute {@link #parentModulGenerator}.
	public ModulGenerator getParentModulGenerator()
	{
		return this.parentModulGenerator;
	}
	 */
	/**
	 * @param parentModulGenerator is the new value for attribute {@link #parentModulGenerator} to set.
	public void setParentModulGenerator(ModulGenerator parentModulGenerator)
	{
		this.parentModulGenerator = parentModulGenerator;
	}
	 */
	
	//private synchronized GeneratorBuffer getGeneratorBuffer()
	//{
	//	if (this.generatorBuffer == null)
	//	{	
	//		this.generatorBuffer = this.createGeneratorBuffer();
	//	}
	//	return this.generatorBuffer;
	//}
	
	/**
	 * This function is called, if the buffer for the generator is created.<br/>
	 * Overwrite this function if a generator should use a special buffer type.
	 * 
	 * @return the default buffer for all generators, a object of the type {@link GeneratorSingleBuffer}.
	 */
	//public synchronized GeneratorBuffer createGeneratorBuffer()
	//{
	//	return new GeneratorSingleBuffer();
	//}
	
}
