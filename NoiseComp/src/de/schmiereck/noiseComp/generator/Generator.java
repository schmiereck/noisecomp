package de.schmiereck.noiseComp.generator;

import java.util.Iterator;
import java.util.Vector;


/**
 * Implementiert die Logik eines Generators der einen Sample für eine
 * Frameposition in einem Buffer ablegt, um ihn nicht mehrfach zu berechnen.
 *
 * @author smk
 * @version 22.01.2004
 */
public abstract class Generator 
implements GeneratorInterface
{
	private float startTimePos = 0.0F;
	private float endTimePos = 0.0F;
	
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
	
	/**
	 * Constructor.
	 * 
	 * @param defaultValue	Wert der als Output zurückgegeben wird,
	 * 						wenn kein Wert berechnet werden kann.
	 * @param holdLastValue	true: liefer den letzten berechneten Wert zuück.<br/>
	 * 						false: liefer den Default-Wert zurück.
	 * @param frameRate		Anzahl der Frames pro Sekunde.
	 */
	public Generator(String name, float frameRate)
	{
		super();
		
		this.name = name;
		this.frameRate = frameRate;
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
	 */
	public void addInputGenerator(Generator inputGenerator, int inputType)
	{
		if (this.inputs == null)
		{	
			this.inputs = new Vector();
		}
		this.inputs.add(new InputData(inputGenerator, inputType));
	}
	
	/**
	 * @see #inputs
	 */
	public Iterator getInputsIterator()
	{
		Iterator ret;
		if (this.inputs != null)
		{	
			ret = this.inputs.iterator();
		}
		else
		{
			ret = null;
		}
		return ret;
	}
	
	public Generator getInputByType(int inputType)
	{
		Generator generator = null;
		
		Iterator inputGeneratorsIterator = this.inputs.iterator();
		
		while (inputGeneratorsIterator.hasNext())
		{
			InputData inputData = (InputData)inputGeneratorsIterator.next();
			
			if (generator != null)
			{
				throw new RuntimeException("found more than one input by type " + inputType);
			}
			
			generator = inputData.getInputGenerator();
		}
		
		return generator;
	}
	
	/**
	 * @return
	 */
	public int getInputsCount()
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
	public void notifyRemoveGenerator(Generator removedGenerator)
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
					inputGeneratorsIterator.remove();
					break;
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
}
