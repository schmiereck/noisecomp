package de.schmiereck.noiseComp.generator;

import java.util.Iterator;
import java.util.Vector;


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
	 * Wenn der Wert eines Samples berechnet wurde,
	 * wird er in {@link #bufferedSoundSample} abgelegt und der
	 * Frame zu dem er gehört in dieser Varaiablen abgelegt.<br/>
	 * Die Berechnung wird nur angestossen, wenn die nachgefragte Frame-Position
	 * nicht der hier abgelegten entspricht.
	 */
	private long bufferedFramePosition	= -1;
	
	/**
	 * @see #bufferedFramePosition
	 */
	private SoundSample bufferedSoundSample		= null;
	
	/**
	 * Is the unique Name of the Generator Object. 
	 */
	private String name;
	
	/**
	 * Liste aus {@link InputData}-Objekten (mit {@link Generator}-Objekten).
	 */
	private Vector inputs = null;
	
	private GeneratorTypeData generatorTypeData;
	
	/**
	 * Constructor.
	 * 
	 * @param defaultValue	Wert der als Output zurückgegeben wird,
	 * 						wenn kein Wert berechnet werden kann.
	 * @param holdLastValue	true: liefer den letzten berechneten Wert zuück.<br/>
	 * 						false: liefer den Default-Wert zurück.
	 * @param frameRate		Anzahl der Frames pro Sekunde.
	 */
	public Generator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
	{
		super();
		
		this.name = name;
		this.frameRate = frameRate.floatValue();
		this.generatorTypeData = generatorTypeData;
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
	public void setStartTimePos(float startTimePos)
	{
		this.startTimePos = startTimePos;
	}

	/**
	 * @set #startTimePos
	 */
	public void setEndTimePos(float endTimePos)
	{
		this.endTimePos = endTimePos;
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
	 * @see de.schmiereck.soundGenerator.GeneratorInterface#generateFrameSample(long)
	 */
	public SoundSample generateFrameSample(long framePosition)
	{
		// Die Frameposition in Zeit umrechnen.
		float frameTime = (framePosition / this.getFrameRate());
		
		if ((frameTime >= this.startTimePos) && (frameTime < this.endTimePos))
		{	
			if (this.bufferedSoundSample == null)
			{
				this.bufferedSoundSample = new SoundSample();
			}
			if (this.bufferedFramePosition != framePosition)
			{
				this.calculateSoundSample(framePosition, frameTime, this.bufferedSoundSample);
				
				this.bufferedFramePosition = framePosition;
			}
		}
		else
		{
			this.bufferedSoundSample = null;
		}
		return this.bufferedSoundSample;
	}

	/**
	 * Berechnen des Sample-Wertes für die angegebene Frame-Position.
	 * 
	 * @param framePosition
	 * @param sample
	 */
	public abstract void calculateSoundSample(long framePosition, float frameTime, SoundSample sample);

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
	public InputData addInputGenerator(Generator inputGenerator, int inputType, Float inputValue)
	{
		InputData inputData = new InputData(inputGenerator, inputType);
		
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
	
	/**
	 * @see #addInputGenerator(Generator, int, Float)
	 */
	public InputData addInputGenerator(Generator inputGenerator, int inputType)
	{
		return this.addInputGenerator(inputGenerator, inputType, null);
	}

	/**
	 * @see #addInputGenerator(Generator, int, Float)
	 */
	public InputData addInputValue(Float value, int inputType)
	{
		return this.addInputGenerator(null, inputType, value);
	}

	/**
	 * @see #addInputGenerator(Generator, int, Float)
	 */
	public InputData addInputValue(float value, int inputType)
	{
		return this.addInputGenerator(null, inputType, Float.valueOf(value));
	}
	
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
	
	/**<
	 * Searches a input by type.<br/>
	 * Throws a {@link RuntimeException} if there is more than one input of this type.
	 * 
	 * @param inputType
	 * @return
	 */
	public InputData searchInputByType(int inputType)
	{
		InputData retInputData = null;
		
		synchronized (this.inputs)
		{
			if (this.inputs != null)
			{	
				Iterator inputGeneratorsIterator = this.inputs.iterator();
				
				while (inputGeneratorsIterator.hasNext())
				{
					InputData inputData = (InputData)inputGeneratorsIterator.next();
					
					if (inputData.getInputType() == inputType)
					{
						if (retInputData != null)
						{
							throw new RuntimeException("found more than one input by type " + inputType + " in generator " + this.getName());
						}
						
						retInputData = inputData;
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
	public float getGeneratorSampleDrawScale()
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
}
