package de.schmiereck.noiseComp.generator;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import de.schmiereck.noiseComp.PopupRuntimeException;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;
import de.schmiereck.noiseComp.soundSource.SoundSourceData;


/**
 * <p>
 * 	Implements the Logic of a Generator.
 * <p>
 * </p>
 * 	The "ouput signal" is calculated based on the internal logic of the generator 
 * 	and the values of differnet inputs.<br/>
 * 	The the count and types of the acceped inputs are defined by the generator type
 * 	returned by the function {@link #createGeneratorData()}.
 * </p>
 * 
 * @author smk
 * @version 22.01.2004
 */
public abstract class Generator 
implements GeneratorInterface,
		   GeneratorChangeListenerInterface,
		   ModuleGeneratorRemoveListenerInterface {
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
	 * Is the Frame rate of the outgoing sound (Sample-Rate).
	 */
	private float soundFrameRate;
	
	/**
	 * Is the unique Name of the Generator. 
	 */
	private String name;
	
	/**
	 * List of {@link InputData}-Objekts (with {@link Generator}-Objekts).
	 */
	private Vector<InputData> inputs = null;
	
	/**
	 * Type of the Generator.
	 */
	private GeneratorTypeInfoData generatorTypeInfoData;

	//private GeneratorBuffer	generatorBuffer = null;
	
	private GeneratorChangeObserver generatorChangeObserver = null;
	
	/**
	 * Module-Generator Remove Listeners.
	 */
	private final List<ModuleGeneratorRemoveListenerInterface> moduleGeneratorRemoveListeners = new Vector<>();
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 * 			is the unique name of the generator.
	 * @param soundFrameRate		
	 * 			are the Frames per Second.
	 * @param generatorTypeInfoData
	 * 			is the Type of the Generator.
	 */
	public Generator(final String name, final Float soundFrameRate, final GeneratorTypeInfoData generatorTypeInfoData) {
		//==========================================================================================
		this.name = name;
		this.soundFrameRate = soundFrameRate.floatValue();
		this.generatorTypeInfoData = generatorTypeInfoData;
		//this.parentModuleGenerator = parentModuleGenerator;
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			the attribute {@link #soundFrameRate}.
	 */
	public float getSoundFrameRate() {
		return this.soundFrameRate;
	}

	/**
	 * @see #name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @see #name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param startTimePos
	 * 			is the startTimePos.
	 * @param endTimePos
	 * 			is the endTimePos.
	 */
	public void setTimePos(final SoundSourceData soundSourceData, final float startTimePos, final float endTimePos) {
		//==========================================================================================
		final float changedStartTimePos 	= Math.min(this.startTimePos, startTimePos);
		final float changedEndTimePos		= Math.max(this.endTimePos, endTimePos);
		
		this.startTimePos = startTimePos;
		this.endTimePos = endTimePos;
		
		this.generateChangedEvent(soundSourceData, this,
		                          changedStartTimePos,
								  changedEndTimePos);
		//==========================================================================================
	}
	
	/**
	 * @see #startTimePos
	 */
	public synchronized void setStartTimePos(final SoundSourceData soundSourceData, final float startTimePos)
	{
		//==========================================================================================
		final float changedStartTimePos 	= Math.min(this.startTimePos, startTimePos);
		final float changedEndTimePos		= this.endTimePos;
		
		this.startTimePos = startTimePos;
		
		this.generateChangedEvent(soundSourceData, this,
		                          changedStartTimePos,
								  changedEndTimePos);
		//==========================================================================================
	}

	/**
	 * @set #startTimePos
	 */
	public synchronized void setEndTimePos(final SoundSourceData soundSourceData, final float endTimePos) {
		//==========================================================================================
		final float changedStartTimePos 	= this.startTimePos;
		final float changedEndTimePos		= Math.max(this.endTimePos, endTimePos);
		
		this.endTimePos = endTimePos;
		
		this.generateChangedEvent(soundSourceData, this,
		                          changedStartTimePos,
								  changedEndTimePos);
		//==========================================================================================
	}

	/**
	 * @return the attribute {@link #endTimePos}.
	 */
	public float getEndTimePos() {
		return this.endTimePos;
	}
	/**
	 * @return the attribute {@link #startTimePos}.
	 */
	public float getStartTimePos() {
		return this.startTimePos;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorInterface#generateFrameSample(long, de.schmiereck.noiseComp.generator.module.ModuleGenerator)
	 */
	public SoundSample generateFrameSample(final long framePosition, final ModuleGenerator parentModuleGenerator,
										   final GeneratorBufferInterface generatorBuffer,
										   final ModuleArguments moduleArguments)
	{
		//==========================================================================================
		final SoundSample soundSample;
		
		// Die Frameposition in Zeit umrechnen.
		final float frameTime = (framePosition / this.getSoundFrameRate());
		
		if (this.checkIsInTime(frameTime)) {
			//--------------------------------------------------------------------------------------
			soundSample = this.createSoundSample();

			this.calculateSoundSample(framePosition, frameTime, soundSample, parentModuleGenerator, 
			                          generatorBuffer,
    	                              moduleArguments);

			//--------------------------------------------------------------------------------------
		} else {
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
	public boolean checkIsInTime(final float frameTime) {
		//==========================================================================================
		final boolean ret;
		
		if ((frameTime >= this.startTimePos) && (frameTime < this.endTimePos)) {
			ret = true;
		} else {
			ret = false;
		}
		//==========================================================================================
		return ret;
	}

	public SoundSample createSoundSample() {
		return new SoundSample();
	}
	
	/**
	 * Calculates the Sample-Values for given Frame-Position.<br/>
	 * Use inputs of generatorBuffer to calculate.
	 * 
	 * @param framePosition
	 * 			is the absolute position of the frame in the complete line to play.<br/>
	 * 			(Is NOT the position in the generator, calulate this frame pos with 
	 * 			soundFramePosition = framePosition - {@link #getStartTimePos()}!)
	 * @param frameTime
	 * 			is the absolute time of the frame in milli seconds.
	 * @param sample
	 * 			is the sound sample.
	 * @param moduleArguments
	 * 			are the Arguments of calling Module
	 * @param generatorBuffer
	 * 			is the generator buffer.<br/>
	 * 			<code>null</code> if there is no buffer available.
	 */
	public abstract void calculateSoundSample(final long framePosition,
											  final float frameTime,
											  final SoundSample sample,
											  final ModuleGenerator parentModuleGenerator,
											  final GeneratorBufferInterface generatorBuffer,
											  final ModuleArguments moduleArguments);
	
	/**
	 * @see #inputs
	 * 
	 * @param inputGenerator
	 * 			is the Input-Generator.
	 * @param inputTypeData
	 * 			is the Input-Type Data.
	 * @param inputValue
	 * 			is the Input-Value.
	 * @param inputStringValue
	 * 			is the Input-String-Value.
	 * @param inputModuleInputTypeData
	 * 			is the Input-Module-Input-Type Data.
	 * @return 
	 * 			the new created and added {@link InputData}-Object.
	 */
	public synchronized InputData addGeneratorInput(final SoundSourceData soundSourceData,
													final Generator inputGenerator,
													final InputTypeData inputTypeData,
													final Float inputValue,
													final String inputStringValue,
													final InputTypeData inputModuleInputTypeData) {
		//==========================================================================================
		final InputData inputData = new InputData(this,
											inputGenerator, 
											inputTypeData, 
											inputModuleInputTypeData);
		
		inputData.setInputValue(soundSourceData, inputValue, inputStringValue);
		
		if (this.inputs == null) {
			this.inputs = new Vector<>();
		}
		
		this.inputs.add(inputData);
		
		if (inputData.getInputGenerator() != null)
		{
			// Der Generator trägt sich als Listener bei dem Input ein, um Änderungen mitzubekommen.
			inputData.getInputGenerator().getGeneratorChangeObserver().registerGeneratorChangeListener(this);
		}
		
		this.generateChangedEvent(soundSourceData, this,
		                          this.getStartTimePos(),
								  this.getEndTimePos());
		
		//==========================================================================================
		return inputData;
	}
	
	public InputData addInputValue(final SoundSourceData soundSourceData, final float value, final int inputType) {
		//==========================================================================================
		final InputTypeData inputTypeData = this.getGeneratorTypeData().getInputTypeData(inputType);

		final InputData inputData = this.addGeneratorInput(soundSourceData, null, inputTypeData, Float.valueOf(value), null, null);
		
		//==========================================================================================
		return inputData;
	}

	/**
	 * @see #addGeneratorInput(SoundSourceData, Generator, InputTypeData, Float, String, InputTypeData)
	 */
	public InputData addInputValue(final SoundSourceData soundSourceData, final Float value, final InputTypeData inputTypeData) {
		//==========================================================================================
		final InputData inputData = this.addGeneratorInput(soundSourceData, null, inputTypeData, value, null, null);
		
		//inputData.getInputGenerator();

		//==========================================================================================
		return inputData;
	}

	public InputData getInputData(final int pos) {
		//==========================================================================================
		final InputData ret;
		
		if (this.inputs != null) {
			ret = (InputData)this.inputs.get(pos);
		} else {
			ret = null;
		}

		//==========================================================================================
		return ret;
	}

	public void removeInput(final SoundSourceData soundSourceData, final InputData inputData) {
		//==========================================================================================
		synchronized (this)
		{
			if (this.inputs != null)
			{
				this.inputs.remove(inputData);
				
				if (inputData.getInputGenerator() != null)
				{
					// The Generator de-register as Listener from Input.
					inputData.getInputGenerator().getGeneratorChangeObserver().removeGeneratorChangeListener(this);
				}
				
				this.generateChangedEvent(soundSourceData);
			}
		}
		//==========================================================================================
	}

	public void updateInput(final SoundSourceData soundSourceData, final InputData inputData) {
		//==========================================================================================
		synchronized (this) {
			if (this.inputs != null) {
//				this.inputs.remove(inputData);
				
				if (inputData.getInputGenerator() != null) {
					// The Generator de-register as Listener from Input.
//					inputData.getInputGenerator().getGeneratorChangeObserver().removeGeneratorChangeListener(this);
				}
				
				this.generateChangedEvent(soundSourceData);
			}
		}
		//==========================================================================================
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
	 * 			the iterator of {@link #inputs}.<br/>
	 * 			<code>null</code> if no inputs exists.
	 */
	public Iterator<InputData> getInputsIterator() {
		//==========================================================================================
		final Iterator<InputData> ret;
		
		if (this.inputs != null) {
			synchronized (this.inputs) {
				ret = this.inputs.iterator();
			}					
		} else {
			ret = null;
		}
		//==========================================================================================
		return ret;
	}
	
	/**
	 * @see #inputs
	 */
	public Object getInputsSyncObject() {
		return this.inputs;
	}

	/**
	 * Searches a input by type.<br/>
	 * Throws a {@link RuntimeException} if there is more than one input of this type.
	 */
	public InputData searchInputByType(final InputTypeData inputTypeData) {
		//==========================================================================================
		InputData retInputData = null;
		
		if (this.inputs != null) {
			synchronized (this.inputs) {
				final int inputType = inputTypeData.getInputType();

                for (final InputData inputData : this.inputs) {
                    if (inputData.getInputTypeData().getInputType() == inputType) {
                        if (retInputData != null) {
                            throw new PopupRuntimeException("Found more than one input by type \"" + inputTypeData + "\" in generator \"" + this.getName() + "\".");
                        }

                        retInputData = inputData;
                    }
                }
			}
		}
		//==========================================================================================
		return retInputData;
	}
	
	/**
	 * Searches a input by type.<br/>
	 * Throws a {@link RuntimeException} if there is more than one input of this type.
	 */
	public InputData searchInputByTypeName(final String inputTypeName) {
		//==========================================================================================
		InputData retInputData = null;
		
		if (this.inputs != null) {
			synchronized (this.inputs) {
				if (this.inputs != null) {

                    for (final InputData inputData : this.inputs) {
                        if (inputTypeName.equals(inputData.getInputTypeData().getInputTypeName())) {
                            if (retInputData != null) {
                                throw new PopupRuntimeException("found more than one input by name \"" + inputTypeName + "\" in generator " + this.toString());
                            }

                            retInputData = inputData;
                        }
                    }
				}
			}
		}
		//==========================================================================================
		return retInputData;
	}
	
	/**
	 * @return
	 * 			the size of the {@link #inputs}.
	 */
	public synchronized int getInputsCount() {
		//==========================================================================================
		final int ret;
		
		if (this.inputs != null) {
			ret = this.inputs.size();
		} else {
			ret = 0;
		}
		//==========================================================================================
		return ret;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.ModuleGeneratorRemoveListenerInterface#notifyModuleGeneratorRemoved(de.schmiereck.noiseComp.generator.Generator)
	 */
	public synchronized void notifyModuleGeneratorRemoved(final SoundSourceData soundSourceData, final Generator removedGenerator) {
		//==========================================================================================
		if (removedGenerator != null) {
			if (this.inputs != null) {
				final Iterator<InputData> inputGeneratorsIterator = this.inputs.iterator();
				
				while (inputGeneratorsIterator.hasNext()) {
					final InputData inputData = inputGeneratorsIterator.next();

					final Generator generator = inputData.getInputGenerator();
					
					if (generator == removedGenerator) {
						synchronized (this.inputs) {
							inputGeneratorsIterator.remove();
						}
						
						this.generateChangedEvent(soundSourceData, removedGenerator,
						                          generator.getStartTimePos(),
												  generator.getEndTimePos());
						break;
					}
				}
			}
		}
		//==========================================================================================
	}

	/**
	 * @param generatorBuffer 
	 * 			is the generatorBuffer.
	 * @param generatorBuffer
	 * 			is the generator buffer.<br/>
	 * 			<code>null</code> if there is no buffer available.
	 * @return 
	 * 			a scale factor for drawing the samples.
	 * 			Should be a factor that normalize all samples of the generator between -1.0 and +1.0.
	 */
	public float getGeneratorSampleDrawScale(final ModuleGenerator parentModuleGenerator,
											 final GeneratorBufferInterface generatorBuffer,
											 final ModuleArguments moduleArguments) {
		//==========================================================================================
		return 1.0F;
	}

	/**
	 * @return the attribute {@link #generatorTypeInfoData}.
	 */
	public GeneratorTypeInfoData getGeneratorTypeData() {
		//==========================================================================================
		return this.generatorTypeInfoData;
	}
	
	/**
	 * Calculates the value for a given input for this position.
	 *
	 * @param framePosition
	 * 			is the absolute position of the frame in the complete line to play.<br/>
	 * 			(Is NOT the position in the generator, calulate this frame pos with 
	 * 			soundFramePosition = framePosition - {@link #getStartTimePos()}!)
	 * @param frameTime
	 * 			is the absolute time of the frame in milli seconds.
	 * @param signal
	 * 			is the sound sample.
	 * @param generatorBuffer
	 * 			is the generator buffer.<br/>
	 * 			<code>null</code> if there is no buffer available.
	 * @param moduleArguments
	 * 			are the Arguments of calling Module
	 */
	protected void calcInputValue(final long framePosition,
								  final float frameTime,
								  final InputData inputData,
								  final SoundSample signal,
								  final ModuleGenerator parentModuleGenerator,
								  final GeneratorBufferInterface generatorBuffer,
								  final ModuleArguments moduleArguments) {
		//==========================================================================================
		final GeneratorBufferInterface inputGeneratorBuffer;
		
		if (generatorBuffer != null) {
			inputGeneratorBuffer = 
				generatorBuffer.getInputGeneratorBuffer(inputData);
		} else {
			inputGeneratorBuffer = null;
		}
		
		// Found Input-Generator-Buffer ?
		if (inputGeneratorBuffer != null) {
			// Use his input:
			this.calcInputFrameSample(framePosition, frameTime,
			                          signal, 
			                          parentModuleGenerator, 
			                          moduleArguments,
			                          inputGeneratorBuffer);
		} else {
			//--------------------------------------------------------------------------------------
			// Input-Generator:
			final GeneratorInterface inputGenerator = inputData.getInputGenerator();

			if (inputGenerator != null) {
				final SoundSample frameSample = inputGenerator.generateFrameSample(framePosition,
				                                                             parentModuleGenerator, 
				                                                             generatorBuffer,
				                           	                              	 moduleArguments);
				
				signal.setSignals(frameSample);
			} else {
				//------------------------------------------------------------------------------
				// Found no Input-Generator:
				final InputTypeData moduleInputTypeData = inputData.getInputModuleInputTypeData();

				// Found a module input type ?
				if (moduleInputTypeData != null) {
					this.calcModuleInputValue(framePosition, frameTime, signal,
							parentModuleGenerator,
							moduleArguments, moduleInputTypeData);
				} else {
					//----------------------------------------------------------------------------------
					// Found no module input:
					final Float inputValue = inputData.getInputValue();

					// Found constant input value ?
					if (inputValue != null) {
						signal.setMonoValue(inputValue.floatValue());
					} else {
						//--------------------------------------------------------------------------
						// Use Default Value of Input type:
						signal.setMonoValue(this.getInputDefaultValueByInputType(inputData));
					}
				}
			}
		}
		//==========================================================================================
	}

	/**
	 * @param framePosition
	 * 			is the absolute position of the frame in the complete line to play.<br/>
	 * 			(Is NOT the position in the generator, calulate this frame pos with 
	 * 			soundFramePosition = framePosition - {@link #getStartTimePos()}!)
	 * @param frameTime
	 * 			is the absolute time of the frame in milli seconds.
	 * @param signal
	 * 			is the sound sample.
	 * @param moduleArguments
	 * 			are the Arguments of calling Module
	 * @param inputGeneratorBuffer
	 * 			is the generator buffer of the input.<br/>
	 * 			<code>null</code> if there is no buffer available.
	 */
	private void calcInputFrameSample(final long framePosition, final float frameTime, final SoundSample signal,
									  final ModuleGenerator parentModuleGenerator, final ModuleArguments moduleArguments,
									  final GeneratorBufferInterface inputGeneratorBuffer) {
		//==========================================================================================
		final SoundSample inputSoundSample =
			inputGeneratorBuffer.calcFrameSample(framePosition, 
			                                     frameTime,
			                                     parentModuleGenerator,
			                                     moduleArguments);
		
		if (inputSoundSample != null) {
			signal.setSignals(inputSoundSample);
		} else {
			signal.setNaN();
		}
		//==========================================================================================
	}

	/**
	 * @param framePosition
	 * 			is the absolute position of the frame in the complete line to play.<br/>
	 * 			(Is NOT the position in the generator, calulate this frame pos with 
	 * 			soundFramePosition = framePosition - {@link #getStartTimePos()}!)
	 * @param frameTime
	 * 			is the absolute time of the frame in milli seconds.
	 * @param signal
	 * 			is the sound sample.
	 * @param moduleArguments
	 * 			are the Arguments of calling Module
	 */
	private void calcModuleInputValue(final long framePosition, final float frameTime, final SoundSample signal,
									  final ModuleGenerator parentModuleGenerator, final ModuleArguments moduleArguments,
									  final InputTypeData moduleInputTypeData) {
		//==========================================================================================
		boolean foundModuleInput;
		
		if (moduleArguments != null) {
			final GeneratorBufferInterface callingModuleGeneratorBuffer = moduleArguments.getCallingModuleGeneratorBuffer();

			final Generator callingGenerator = callingModuleGeneratorBuffer.getGenerator();

			final ModuleArguments prevModuleArguments = moduleArguments.getPrevModuleArguments();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Check if the calling Module have a input for this moduleInputType.
			
			foundModuleInput = false;

			final Iterator<InputData> callingInputsIterator = callingGenerator.getInputsIterator();
			
			if (callingInputsIterator != null) {
				while (callingInputsIterator.hasNext()) {
					final InputData moduleInputData = callingInputsIterator.next();
					
					if (moduleInputData.getInputTypeData().getInputType() == moduleInputTypeData.getInputType()) {
						final float callStartTimePos = callingGenerator.getStartTimePos();

						final float callFrameTime = callStartTimePos + frameTime;
						final long callFramePosition = (long)(callFrameTime * callingGenerator.soundFrameRate);
						
//						Generator inputGenerator = moduleInputData.getInputGenerator();
//						
//						float inputStartTimePos = inputGenerator.getStartTimePos();
//						
//						float inputFrameTime = inputStartTimePos + frameTime;
//						long inputFramePosition = (long)(inputFrameTime * inputGenerator.soundFrameRate);
						
						callingGenerator.calcInputValue(callFramePosition, callFrameTime, //inputFramePosition, inputFrameTime, //framePosition, frameTime, 
						                                moduleInputData, 
						                                signal, 
						                                parentModuleGenerator, 
						                                callingModuleGeneratorBuffer, 
						                                prevModuleArguments);
						
						foundModuleInput = true;
					}
				}
			}
		} else {
			foundModuleInput = false;
		}
		
		//------------------------------------------------------------------------------------------
		// If not?
		if (foundModuleInput == false)
		{
			// use the default value.
			signal.setMonoValue(moduleInputTypeData.getDefaultValue());
		}
		//==========================================================================================
	}
	
	/**
	 * @param framePosition
	 * 			is the absolute position of the frame in the complete line to play.<br/>
	 * 			(Is NOT the position in the generator, calulate this frame pos with 
	 * 			soundFramePosition = framePosition - {@link #getStartTimePos()}!)
	 * @param frameTime
	 * 			is the absolute time of the frame in milli seconds.
	 * @param generatorBuffer
	 * 			is the generator buffer.<br/>
	 * 			<code>null</code> if there is no buffer available.
	 * @param moduleArguments
	 * 			are the Arguments of calling Module
	 * @return
	 * 			the default value.<br/>
	 * 			{@link Float#NaN} if no default input defined.
	 */
	protected float calcInputMonoValue(final long framePosition,
									   final float frameTime,
									   final InputTypeData inputTypeData,
									   final ModuleGenerator parentModuleGenerator,
									   final GeneratorBufferInterface generatorBuffer,
									   final ModuleArguments moduleArguments) {
		//==========================================================================================
		final float value;

		final InputData inputData = this.searchInputByType(inputTypeData);
		
		if (inputData != null) {
			value = this.calcInputMonoValue(framePosition, 
			                                frameTime,
			                                inputData, 
			                                parentModuleGenerator,
			                                generatorBuffer,
			                                moduleArguments);
		} else {
			//throw new RuntimeException("input type not found: " + inputType);
			value = this.getInputDefaultValueByInputType(inputTypeData);
		}
		//==========================================================================================
		return value;
	}

	/**
	 * @param framePosition
	 * 			is the frame position.
	 * @param inputData
	 * 			is the input data.
	 * @param parentModuleGenerator
	 * 			is the parent module generator.
	 * @param generatorBuffer
	 * 			is the generator buffer.<br/>
	 * 			<code>null</code> if there is no buffer available.
	 * @return
	 * 			the value.<br/>
	 * 			{@link Float#NaN} if no input signal found.
	 */
	protected float calcInputMonoValue(final long framePosition,
									   final float frameTime,
									   final InputData inputData,
									   final ModuleGenerator parentModuleGenerator,
									   final GeneratorBufferInterface generatorBuffer,
									   final ModuleArguments moduleArguments) {
		//==========================================================================================
		final float value;

		//------------------------------------------------------------------------------------------
		final SoundSample signal = new SoundSample();
		
		this.calcInputValue(framePosition, 
		                    frameTime, 
		                    inputData, 
		                    signal, 
		                    parentModuleGenerator, 
		                    generatorBuffer, 
		                    moduleArguments);
		
		value = signal.getMonoValue();
		
		//==========================================================================================
		return value;
	}

	/**
	 * @param framePosition
	 * 			is the absolute position of the frame in the complete line to play.<br/>
	 * 			(Is NOT the position in the generator, calulate this frame pos with 
	 * 			soundFramePosition = framePosition - {@link #getStartTimePos()}!)
	 * @return
	 * 			the String-Value of the given input.
	 */
	protected String calcInputStringValue(final long framePosition, final InputTypeData inputTypeData,
										  final ModuleGenerator parentModuleGenerator) {
		//==========================================================================================
		final String value;

		final InputData inputData = this.searchInputByType(inputTypeData);

		if (inputData != null) {
			final Generator inputSoundGenerator = inputData.getInputGenerator();
	
			// Found Input-Generator ?
			if (inputSoundGenerator != null) {
				// Use his input:
				//value = inputSoundGenerator.calcInputStringValue(framePosition, parentModuleGenerator);
				value = null;
			} else {
				// Found no Input-Generator:
				value = inputData.getInputStringValue();
			}
		} else {
			value = null;
		}
		//==========================================================================================
		return value;
	}

	protected float getInputDefaultValueByInputType(final InputData inputData) {
		//==========================================================================================
		final float ret;

		final InputTypeData inputTypeData = inputData.getInputTypeData();
		
		// Found InputType ? (this situation may be occourse, if load an older type from file)
		if (inputTypeData != null) {
			ret = this.getInputDefaultValueByInputType(inputTypeData);
		} else {
			ret = 0.0F;
		}
		//==========================================================================================
		return ret;
	}

	/**
	 * @param inputTypeData
	 * 			is the input type.
	 * @return
	 * 			the default value.<br/>
	 * 			{@link Float#NaN} if no default input defined.
	 */
	private float getInputDefaultValueByInputType(final InputTypeData inputTypeData) {
		//==========================================================================================
		final float ret;
		final Float defaultValue = inputTypeData.getDefaultValue();
		
		if (defaultValue != null) {
			ret = defaultValue.floatValue();
		} else {
			ret = Float.NaN;
		}
		//==========================================================================================
		return ret;
	}
	
	/**
	 * @return returns the {@link #generatorChangeObserver}.
	 */
	public synchronized GeneratorChangeObserver getGeneratorChangeObserver() {
		if (this.generatorChangeObserver == null) {
			this.generatorChangeObserver = new GeneratorChangeObserver();
		}
		return this.generatorChangeObserver;
	}
	
	/**
	 * Send a change Event to {@link #generatorChangeObserver}.
	 */
	public synchronized void generateChangedEvent(final SoundSourceData soundSourceData, final Generator generator,
												  final float changedStartTimePos, final float changedEndTimePos) {
		System.out.println("Generator(\"" + this.getName() + "\").generateChangedEvent: " + changedStartTimePos + ", " + changedEndTimePos);
		if (this.generatorChangeObserver != null) {
			this.generatorChangeObserver.changedEvent(soundSourceData, generator,
													  changedStartTimePos, changedEndTimePos);
		}
	}
	
	/**
	 * {@link #generateChangedEvent(SoundSourceData, Generator, float, float)} for {@link #startTimePos} and {@link #endTimePos}.
	 */
	public void generateChangedEvent(final SoundSourceData soundSourceData) {
		this.generateChangedEvent(soundSourceData, this,
		                          this.getStartTimePos(),
								  this.getEndTimePos());
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorChangeListenerInterface#notifyGeneratorChanged(de.schmiereck.noiseComp.generator.Generator, float, float)
	 */
	public void notifyGeneratorChanged(SoundSourceData soundSourceData, final
	Generator generator, final float changedStartTimePos, final float changedEndTimePos) {
		//==========================================================================================
		// Einer der überwachten Inputs hat sich geändert:

		if (generator == this) {
			throw new RuntimeException("Generator \"" + generator + "\" notifies himself of changes.");
		}
		
		try {
			this.getGeneratorChangeObserver().changedEvent(soundSourceData, generator,
//		    	                                		   this.getStartTimePos() + startTimePos, 
//		        	                            		   this.getStartTimePos() + endTimePos);
			                                               changedStartTimePos, 
			                                               changedEndTimePos);
		} catch (final Throwable ex) {
			throw new RuntimeException("Notify Generator \"" + generator + "\" changed.", ex);
		}
		//==========================================================================================
	}

	/**
	 * @param moduleeneratorRemoveListener 
	 * 			to add to {@link #moduleGeneratorRemoveListeners}.
	 */
	public void addModuleGeneratorRemoveListener(final ModuleGeneratorRemoveListenerInterface moduleeneratorRemoveListener) {
		this.moduleGeneratorRemoveListeners.add(moduleeneratorRemoveListener);
	}

	/**
	 * @param removedGenerator 
	 * 			to notify the {@link #moduleGeneratorRemoveListeners}.
	 */
	public void notifyModuleGeneratorRemoveListeners(final SoundSourceData soundSourceData, final Generator removedGenerator) {
		for (final ModuleGeneratorRemoveListenerInterface moduleeneratorRemoveListener : this.moduleGeneratorRemoveListeners) {
			moduleeneratorRemoveListener.notifyModuleGeneratorRemoved(soundSourceData, removedGenerator);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{" + super.toString() + ": name: \"" + this.name + "\"}";
	}

	/**
	 * @return 
	 * 			returns the {@link #inputs}.
	 */
	public Vector<InputData> getInputs() {
		return this.inputs;
	}
	
	/**
	 * Creates generator data stored in eached timeline of this generator.
	 * Override to use a generator specific data object.
	 * 
	 * @return
	 * 			the generator data.
	 */
	public Object createGeneratorData() {
		return new Object();
	}
}
