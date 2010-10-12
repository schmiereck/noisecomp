package de.schmiereck.noiseComp.generator;

import java.util.Iterator;
import java.util.Vector;
import de.schmiereck.noiseComp.PopupRuntimeException;


/**
 * <p>
 * 	Implementiert die Logik eines Generators der einen Sample f�r eine
 * 	Frameposition in einem Buffer ablegt, um ihn nicht mehrfach zu berechnen.
 * <p>
 * </p>
 * 	The "ouput signal" is calculated based on the internal logic of the generator 
 * 	and the values of differnet inputs.<br/>
 * 	The the count and types of the acceped inputs are defined by the generator type
 * 	returned by the function {@link #createGeneratorTypeData()}.
 * </p>
 * 
 * @author smk
 * @version 22.01.2004
 */
public abstract class Generator 
implements GeneratorInterface,
		   GeneratorChangeListenerInterface
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Start time position in milli seconds.
	 */
	private float startTimePos = 0.0F;

	/**
	 * End time position in milli seconds.
	 */
	private float endTimePos = 1.0F;
	
	/**
	 * Is the Frame rate of the outgoing sound.
	 */
	private float soundFrameRate;
	
	/**
	 * Is the unique Name of the Generator. 
	 */
	private String name;
	
	/**
	 * Liste aus {@link InputData}-Objekten (mit {@link Generator}-Objekten).
	 */
	private Vector<InputData> inputs = null;
	
	/**
	 * Type of the Generator.
	 */
	private GeneratorTypeData generatorTypeData;

	//private GeneratorBuffer	generatorBuffer = null;
	
	private GeneratorChangeObserver generatorChangeObserver = null;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 * 			is the unique name of the generator.
	 * @param soundFrameRate		
	 * 			are the Frames per Second.
	 * @param generatorTypeData
	 * 			is the Type of the Generator.
	 */
	public Generator(String name, Float soundFrameRate, GeneratorTypeData generatorTypeData)
	{
		super();
		
		this.name = name;
		this.soundFrameRate = soundFrameRate.floatValue();
		this.generatorTypeData = generatorTypeData;
		//this.parentModulGenerator = parentModulGenerator;
	}

	/**
	 * @return 
	 * 			the attribute {@link #soundFrameRate}.
	 */
	public float getSoundFrameRate()
	{
		return this.soundFrameRate;
	}

	/**
	 * @set #startTimePos
	 */
	public synchronized void setStartTimePos(float startTimePos)
	{
		float changedStartTimePos 	= Math.min(this.startTimePos, startTimePos);
		float changedEndTimePos		= this.endTimePos;
		
		this.startTimePos = startTimePos;
		
		this.generateChangedEvent(changedStartTimePos,
								  changedEndTimePos);
	}

	/**
	 * @set #startTimePos
	 */
	public synchronized void setEndTimePos(float endTimePos)
	{
		float changedStartTimePos 	= this.startTimePos;
		float changedEndTimePos		= Math.max(this.endTimePos, endTimePos);
		
		this.endTimePos = endTimePos;
		
		this.generateChangedEvent(changedStartTimePos,
								  changedEndTimePos);
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
	public SoundSample generateFrameSample(long framePosition, ModulGenerator parentModulGenerator, GeneratorBufferInterface generatorBuffer)
	{
		//==========================================================================================
		SoundSample soundSample;
		
		// Die Frameposition in Zeit umrechnen.
		float frameTime = (framePosition / this.getSoundFrameRate());
		
		if (this.checkIsInTime(frameTime) == true)
		{	
			//--------------------------------------------------------------------------------------
			soundSample = new SoundSample();

			this.calculateSoundSample(framePosition, frameTime, soundSample, parentModulGenerator, generatorBuffer);

			//--------------------------------------------------------------------------------------
		}
		else
		{
			soundSample = null;
		}
		//==========================================================================================
		return soundSample;
	}

	/**
	 * @param frameTime
	 * 			is the frame time in milli seconds.
	 * @return 
	 * 			<code>true</code> if the frameTime is in the generator time 
	 * 			(between {@link #startTimePos} and {@link #endTimePos}).
	 */
	public boolean checkIsInTime(float frameTime)
	{
		//==========================================================================================
		boolean ret;
		
		if ((frameTime >= this.startTimePos) && (frameTime < this.endTimePos))
		{
			ret = true;
		}
		else
		{
			ret = false;
		}
		//==========================================================================================
		return ret;
	}
	
	/**
	 * Berechnen des Sample-Wertes für die angegebene Frame-Position.
	 * TODO Use inputs of generatorBuffer to calculate.
	 * 
	 * @param framePosition
	 * 			is the absolute position of the frame in the complete line to play.<br/>
	 * 			(Is NOT the position in the generator, calulate this frame pos with 
	 * 			soundFramePosition = framePosition - {@link #getStartTimePos()}!)
	 * @param frameTime
	 * 			is the absolute time of the frame in milli seconds.
	 * @param sample
	 * 			is the sound sample.
	 * @param generatorBuffer
	 * 			is the generatorBuffer.
	 */
	public abstract void calculateSoundSample(long framePosition, 
	                                          float frameTime, 
	                                          SoundSample sample, 
	                                          ModulGenerator parentModulGenerator, 
	                                          GeneratorBufferInterface generatorBuffer);

	/**
	 * @see #name
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * @see #inputs
	 * @return 
	 * 			the new created and added {@link InputData}-Object.
	 */
	public synchronized InputData addInputGenerator(Generator inputGenerator, 
	                                                InputTypeData inputTypeData, 
	                                                Float inputValue, 
	                                                String inputStringValue, 
	                                                InputTypeData inputModulInputTypeData)
	{
		InputData inputData = new InputData(this,
											inputGenerator, 
											inputTypeData, 
											inputModulInputTypeData);
		
		inputData.setInputValue(inputValue, inputStringValue);
		
		if (this.inputs == null)
		{	
			this.inputs = new Vector<InputData>();
		}
		
		this.inputs.add(inputData);
		
		if (inputData.getInputGenerator() != null)
		{
			// Der Generator trägt sich als Listener bei dem Input ein, um Änderungen mitzubekommen.
			inputData.getInputGenerator().getGeneratorChangeObserver().registerGeneratorChangeListener(this);
		}
		
		this.generateChangedEvent(this.getStartTimePos(),
								  this.getEndTimePos());
		
		return inputData;
	}
	
	public InputData addInputValue(float value, int inputType)
	{
		InputTypeData inputTypeData = this.getGeneratorTypeData().getInputTypeData(inputType);
		
		InputData inputData = this.addInputGenerator(null, inputTypeData, Float.valueOf(value), null, null);
		
		return inputData;
	}

	public InputData getInputData(int pos)
	{
		InputData ret;
		
		if (this.inputs != null)
		{
			ret = (InputData)this.inputs.get(pos);
		}
		else
		{
			ret = null;
		}

		return ret;
	}

	public void removeInput(InputData inputData)
	{
		synchronized (this)
		{
			if (this.inputs != null)
			{
				this.inputs.remove(inputData);
				
				if (inputData.getInputGenerator() != null)
				{
					//Der Generator tr�gt sich wieder als Listener bei dem Input aus.
					inputData.getInputGenerator().getGeneratorChangeObserver().removeGeneratorChangeListener(this);
				}
				
				this.generateChangedEvent();
			}
		}
	}

	/**
	 * @see #addInputGenerator(Generator, InputTypeData, Float)
	 */
	public InputData addInputValue(Float value, InputTypeData inputTypeData)
	{
		InputData inputData = this.addInputGenerator(null, inputTypeData, value, null, null);
		
		//inputData.getInputGenerator();

		return inputData;
	}

	/**
	 * @see #addInputGenerator(Generator, InputTypeData, Float)
	public InputData addInputValue(float value, InputTypeData inputTypeData)
	{
		return this.addInputGenerator(null, inputTypeData, Float.valueOf(value));
	}
	 */
	
	/**
	 * @return
	 * 			the iterator of {@link #inputs}.
	 */
	public Iterator<InputData> getInputsIterator()
	{
		Iterator<InputData> ret;
		
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
	*/
	
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
				int inputType = inputTypeData.getInputType();
				
				Iterator<InputData> inputGeneratorsIterator = this.inputs.iterator();
				
				while (inputGeneratorsIterator.hasNext())
				{
					InputData inputData = inputGeneratorsIterator.next();
					
					if (inputData.getInputTypeData().getInputType() == inputType)
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
					Iterator<InputData> inputGeneratorsIterator = this.inputs.iterator();
					
					while (inputGeneratorsIterator.hasNext())
					{
						InputData inputData = inputGeneratorsIterator.next();
						
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
	 * 			the size of the {@link #inputs}.
	 */
	public synchronized int getInputsCount()
	{
		int ret;
		
		if (this.inputs != null)
		{	
			ret = this.inputs.size();
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
	public synchronized void notifyRemoveGenerator(Generator removedGenerator)
	{
		if (removedGenerator != null)
		{
			if (this.inputs != null)
			{	
				Iterator<InputData> inputGeneratorsIterator = this.inputs.iterator();
				
				while (inputGeneratorsIterator.hasNext())
				{
					InputData inputData = inputGeneratorsIterator.next();
	
					Generator generator = (Generator)inputData.getInputGenerator();
					
					if (generator == removedGenerator)
					{
						synchronized (this.inputs)
						{
							inputGeneratorsIterator.remove();
						}
						
						this.generateChangedEvent(generator.getStartTimePos(),
												  generator.getEndTimePos());
						break;
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
	 * @param generatorBuffer 
	 * 			is the generatorBuffer.
	 * @return a scale factor for drawing the samples.
	 * 			Should be a factor that normalize all samples of the generator between -1.0 and +1.0.
	 */
	public float getGeneratorSampleDrawScale(ModulGenerator parentModulGenerator, 
	                                         GeneratorBufferInterface generatorBuffer)
	{
		return 1.0F;
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
	protected void calcInputValue(long framePosition, 
	                              float frameTime,
	                              InputData inputData, 
	                              SoundSample signal, 
	                              ModulGenerator parentModulGenerator,
	                              GeneratorBufferInterface generatorBuffer)
	{
		//==========================================================================================
//		GeneratorInterface inputGenerator = inputData.getInputGenerator();
//
//		// Found Input-Generator ?
//		if (inputGenerator != null)
//		{	
//			//--------------------------------------------------------------------------------------
//			// Use Input-Generator input:
//			
//			SoundSample inputSoundSample = 
//				inputGenerator.generateFrameSample(framePosition, 
//				                                        parentModulGenerator, 
//				                                        generatorBuffer);
//			
//			value.setValues(inputSoundSample);
//		}
//		else
//		{
		GeneratorBufferInterface inputGeneratorBuffer = 
			generatorBuffer.getInputGeneratorBuffer(inputData);
		
		GeneratorInterface inputGenerator = inputData.getInputGenerator();

		// Found Input-Generator-Buffer ?
		if (inputGeneratorBuffer != null)
		{	
			// Use his input:
			
			SoundSample inputSoundSample = 
				inputGeneratorBuffer.calcFrameSample(framePosition, 
				                                     frameTime,
				                                     parentModulGenerator);
			
			signal.setSignals(inputSoundSample);
		}
		else
		{
			//--------------------------------------------------------------------------------------
			// Found no Input-Generator:
			
			Float inputValue = inputData.getInputValue();
			
			// Found constant input value ?
			if (inputValue != null)
			{
				signal.setMonoValue(inputValue.floatValue());
			}
			else
			{
				//----------------------------------------------------------------------------------
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
						Iterator<InputData> modulInputsIterator = parentModulGenerator.getInputsIterator();
						
						if (modulInputsIterator != null)
						{
							while (modulInputsIterator.hasNext())
							{
								InputData modulInputData = modulInputsIterator.next();
								
								if (modulInputData.getInputTypeData().getInputType() == modulInputTypeData.getInputType())
								{
									parentModulGenerator.calcInputValue(framePosition, 
									                                    frameTime,
									                                    modulInputData, 
									                                    signal, 
									                                    parentModulGenerator,
									                                    generatorBuffer);
									break;
								}
							}
						}
					}
					else
					{
						signal.setMonoValue(modulInputTypeData.getDefaultValue());
					}
				}
				else
				{
					//------------------------------------------------------------------------------
					// Use Default Value of Input type:
				
					signal.setMonoValue(this.getInputDefaultValueByInputType(inputData));
				}
			}
		}
		//==========================================================================================
	}

	protected void calcInputSignal(long framePosition, 
	                               float frameTime,
	                               InputData inputData, 
	                               SoundSample signal, 
	                               ModulGenerator parentModulGenerator,
	                               GeneratorBufferInterface generatorBuffer)
	{
		//==========================================================================================
		if (inputData != null)
		{
//			//--------------------------------------------------------------------------------------
//			SoundSample bufInputSoundSample = generatorBuffer.calcInputFrameSample(framePosition, 
//			                                                                       inputData, 
//			                                                                       parentModulGenerator);
//			
//			if (bufInputSoundSample != null)
//			{
//				signal.setSignals(bufInputSoundSample);
//			}
//			else
//			{
			//--------------------------------------------------------------------------------------
			
			GeneratorBufferInterface inputGeneratorBuffer = 
				generatorBuffer.getInputGeneratorBuffer(inputData);
			
			GeneratorInterface inputGenerator = inputData.getInputGenerator();
	
			// Found Input-Generator-Buffer ?
			if (inputGeneratorBuffer != null)
			{	
				// Use his input:
				
				SoundSample inputSoundSample = 
					inputGeneratorBuffer.calcFrameSample(framePosition, 
					                                     frameTime,
					                                     parentModulGenerator);
				
				signal.setSignals(inputSoundSample);
			}
			else
			{
				//----------------------------------------------------------------------------------
				// Found no Input-Generator:
				
				Float inputValue = inputData.getInputValue();
				
				// Found constant input value ?
				if (inputValue != null)
				{
					signal.setMonoSignal(inputValue.floatValue());
				}
				else
				{
					//------------------------------------------------------------------------------
					// Found no input value:
	
					InputTypeData modulInputTypeData = inputData.getInputModulInputTypeData();

					// Found a modul input type ?
					if ((modulInputTypeData != null) && (parentModulGenerator != null))
					{
						// Use Value from this input:

						// ALTERNATIVE: irgenendwie an den ModulGenerator drannkommen, in dem dieser Generator benutzt wird und dann in dem nach dem input suchen.
						//InputData modulInputData = this.getParentModulGenerator().searchInputByTypeName(inputModulInput);
						Iterator<InputData> modulInputsIterator = parentModulGenerator.getInputsIterator();
						
						if (modulInputsIterator != null)
						{
							while (modulInputsIterator.hasNext())
							{
								InputData modulInputData = modulInputsIterator.next();
								
								if (modulInputData.getInputTypeData().getInputType() == modulInputTypeData.getInputType())
								{
//									GeneratorBufferInterface modulInputGeneratorBuffer = 
//										generatorBuffer.getInputGeneratorBuffer(modulInputData);
//									
//									SoundSample inputSoundSample = 
//										modulInputGeneratorBuffer.calcFrameSample(framePosition, 
//										                                          frameTime,
//										                                          parentModulGenerator);
//									
//									signal.setSignals(inputSoundSample);
									
									parentModulGenerator.calcInputSignal(framePosition, 
									                                     frameTime,
									                                     modulInputData, 
									                                     signal, 
									                                     parentModulGenerator,
									                                     generatorBuffer);
									break;
								}
							}
						}
					}
					else
					{
						//--------------------------------------------------------------------------
						// Found no input value:
						// Use Default Value of Input type:
						
						signal.setMonoSignal(this.getInputDefaultValueByInputType(inputData));
					}
				}
			}
//		}
		}
		else
		{
			//--------------------------------------------------------------------------------------
			signal.setMonoSignal(0.0F);
		}
		//==========================================================================================
	}
	
	protected float calcInputMonoValue(long framePosition, 
	                                   float frameTime,
	                                   InputTypeData inputTypeData, 
	                                   ModulGenerator parentModulGenerator,
	                                   GeneratorBufferInterface generatorBuffer)
//	throws NoInputSignalException
	{
		float value;
		
		InputData inputData = this.searchInputByType(inputTypeData);
		
		if (inputData != null)
		{
			value = this.calcInputMonoValue(framePosition, 
			                                frameTime,
			                                inputData, 
			                                parentModulGenerator,
			                                generatorBuffer);
		}
		else
		{
			//throw new RuntimeException("input type not found: " + inputType);
			value = this.getInputDefaultValueByInputType(inputTypeData);
		}
		
		return value;
	}

	/**
	 * @param framePosition
	 * 			is the frame position.
	 * @param inputData
	 * 			is the input data.
	 * @param parentModulGenerator
	 * 			is the parent modul generator.
	 * @return
	 * 			the value.<br/>
	 * 			{@link Float#NaN} if no input signal found.

	 */
	protected float calcInputMonoValue(long framePosition, 
	                                   float frameTime,
	                                   InputData inputData, 
									   ModulGenerator parentModulGenerator,
									   GeneratorBufferInterface generatorBuffer)
//	throws NoInputSignalException
	{
		//==========================================================================================
		float value;

		//------------------------------------------------------------------------------------------
//		GeneratorInterface inputSoundGenerator = inputData.getInputGenerator();
//
//		// Found Input-Generator ?
//		if (inputSoundGenerator != null)
//		{	
//			// Use his input:
//			
//			SoundSample inputSoundSample = 
//				inputSoundGenerator.generateFrameSample(framePosition, 
//				                                        parentModulGenerator, 
//				                                        generatorBuffer);
//			
//			if (inputSoundSample != null)
//			{	
//				value = inputSoundSample.getMonoValue();
//			}
//			else
//			{
//				// Found no input signal:
//				
////				throw new NoInputSignalException();
//				value = Float.NaN; // this.getInputDefaultValueByInputType(inputType);
//			}
//		}
		
		GeneratorBufferInterface inputGeneratorBuffer = 
			generatorBuffer.getInputGeneratorBuffer(inputData);
		
		GeneratorInterface inputGenerator = inputData.getInputGenerator();

		// Found Input-Generator-Buffer ?
		if (inputGeneratorBuffer != null)
		{	
			// Use his input:
			
			SoundSample inputSoundSample = 
				inputGeneratorBuffer.calcFrameSample(framePosition, 
				                                     frameTime,
				                                     parentModulGenerator);
			
			if (inputSoundSample != null)
			{	
				value = inputSoundSample.getMonoValue();
			}
			else
			{
				// Found no input signal:
				
//				throw new NoInputSignalException();
				value = Float.NaN; // this.getInputDefaultValueByInputType(inputType);
			}
		}
		else
		{
			//--------------------------------------------------------------------------------------
			// Found no Input-Generator:
			
			Float inputValue = inputData.getInputValue();
		
			// Found constant input value ?
			if (inputValue != null)
			{
				value = inputValue.floatValue();
			}
			else
			{
				//----------------------------------------------------------------------------------
				InputTypeData modulInputTypeData = inputData.getInputModulInputTypeData();

				// Found a modul input type ?
				if ((modulInputTypeData != null) && (parentModulGenerator != null))
				{
					// Use Value from this input:

					Iterator<InputData> modulInputsIterator = parentModulGenerator.getInputsIterator();
					
					value = 0.0F;
					
					if (modulInputsIterator != null)
					{
						while (modulInputsIterator.hasNext())
						{
							InputData modulInputData = modulInputsIterator.next();
							
							if (modulInputData.getInputTypeData().getInputType() == modulInputTypeData.getInputType())
							{
								GeneratorBufferInterface modulGeneratorBuffer = 
									generatorBuffer.getInputGeneratorBuffer(modulInputData);
								
								value = parentModulGenerator.calcInputMonoValue(framePosition, 
								                                                frameTime,
								                                                modulInputData, 
								                                                parentModulGenerator,
								                                                generatorBuffer);
								break;
							}
						}
					}
				}
				else
				{
					//------------------------------------------------------------------------------
					// Found no input value:
					// Use Default Value of Input type:
					
					value = this.getInputDefaultValueByInputType(inputData);
				}
			}
		}
		//==========================================================================================
		return value;
	}

	protected String calcInputStringValue(long framePosition, InputTypeData inputTypeData,
										  ModulGenerator parentModulGenerator)
	{
		String value;

		InputData inputData = this.searchInputByType(inputTypeData);

		if (inputData != null)
		{
			Generator inputSoundGenerator = inputData.getInputGenerator();
	
			// Found Input-Generator ?
			if (inputSoundGenerator != null)
			{	
				// Use his input:
				
				//value = inputSoundGenerator.calcInputStringValue(framePosition, parentModulGenerator);
				value = null;
			}
			else
			{
				// Found no Input-Generator:
				
				value = inputData.getInputStringValue();
			}
		}
		else
		{
			value = null;
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

	/**
	 * @param inputTypeData
	 * @return
	 * 			the default value.<br/>
	 * 			{@link Float#NaN} if no default input defined.
	 */
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
			ret = Float.NaN;
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
	
	/**
	 * @return returns the {@link #generatorChangeObserver}.
	 */
	public synchronized GeneratorChangeObserver getGeneratorChangeObserver()
	{
		if (this.generatorChangeObserver == null)
		{
			this.generatorChangeObserver = new GeneratorChangeObserver();
		}
		
		return this.generatorChangeObserver;
	}
	
	/**
	 * Send a change Event to {@link #generatorChangeObserver}.
	 */
	public synchronized void generateChangedEvent(float changedStartTimePos, 
	                                              float changedEndTimePos)
	{
System.out.println("Generator(\"" + this.getName() + "\").generateChangedEvent: " + changedStartTimePos + ", " + changedEndTimePos);
		if (this.generatorChangeObserver != null)
		{
			this.generatorChangeObserver.changedEvent(this, 
													  changedStartTimePos, changedEndTimePos);
		}
	}
	
	/**
	 * {@link #generateChangedEvent(float, float)} for {@link #startTimePos} and {@link #endTimePos}.
	 */
	public void generateChangedEvent()
	{
		this.generateChangedEvent(this.getStartTimePos(),
								  this.getEndTimePos());
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorChangeListenerInterface#notifyGeneratorChanged(de.schmiereck.noiseComp.generator.Generator, float, float)
	 */
	public void notifyGeneratorChanged(Generator generator, float startTimePos, float endTimePos)
	{
		// Einer der überwachten Inputs hat sich geändert:

		if (generator == this)
		{
			throw new RuntimeException("Generator \"" + generator + "\" notify himself of changes.");
		}
		
		try
		{
			this.getGeneratorChangeObserver().changedEvent(this, 
//		    	                                		   this.getStartTimePos() + startTimePos, 
//		        	                            		   this.getStartTimePos() + endTimePos);
			                                               startTimePos, 
			                                               endTimePos);
		}
		catch (Throwable ex)
		{
			throw new RuntimeException("Notify Generator \"" + generator + "\" changed.", ex);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "{" + super.toString() + ": name: \"" + this.name + "\"}";
	}
}
