package de.schmiereck.noiseComp.generator;

import java.util.Iterator;
import java.util.Vector;

/**
 * Manages a List of {@link Generator}-Objects and 
 * the main output generator for this list. 
 *
 * @author smk
 * @version <p>28.02.2004: created, smk</p>
 */
public class Generators
{
	/**
	 * The Output-Object of the Sound-Generators.
	 */
	private OutputGenerator	outputGenerator = null;
	
	/**
	 * List of {@link Generator}-Objects with an
	 * Integer-Position-Number as Key.
	 */
	private Vector	generators = new Vector();

	/**
	 * Constructor.
	 * 
	 * 
	 */
	public Generators()
	{
		super();
	}

	/**
	 * @return the attribute {@link #outputGenerator}.
	 */
	public OutputGenerator getOutputGenerator()
	{
		return this.outputGenerator;
	}
	
	/**
	 * @param outputGenerator is the new value for attribute {@link #outputGenerator} to set.
	 */
	public void setOutputGenerator(OutputGenerator outputGenerator)
	{
		this.outputGenerator = outputGenerator;
	}

	/**
	 * @param generator is the Generator to add.
	 */
	public void addGenerator(Generator generator)
	{
		synchronized (this)
		{
			this.generators.add(generator);

			if (generator instanceof OutputGenerator)
			{	
				this.setOutputGenerator((OutputGenerator)generator);
			}
		}
	}

	/**
	 * @param trackPos
	 */
	public void removeGenerator(int trackPos)
	{
		synchronized (this)
		{
			Generator removedGenerator = (Generator)this.generators.get(trackPos);
			this.generators.remove(trackPos);
			
			// Alle Generatoren durchlaufen und benachrichtigen 
			// das einer der ihren gelöscht wurde (als Input entfernen usw.):
			
			Iterator generatorsIterator = this.generators.iterator();
			
			while (generatorsIterator.hasNext())
			{
				Generator generator = (Generator)generatorsIterator.next();
				
				generator.notifyRemoveGenerator(removedGenerator);
			}
			
			// Output removed ?
			if (removedGenerator == outputGenerator)
			{
				this.setOutputGenerator(null);
			}
		}
	}

	/**
	 * @return a Iterator over the {@link Generator}-Objects.
	 */
	public Iterator getGeneratorsIterator()
	{
		return this.generators.iterator();
	}

	/**
	 * 
	 */
	public void clear()
	{
		synchronized (this)
		{
			this.generators.clear();
			this.setOutputGenerator(null);
		}
	}

	/**
	 * @param inputGeneratorName
	 * @return
	 */
	public Generator searchGenerator(String generatorName)
	{
		Generator retGenerator = null;
		
		Iterator generatorsIterator = this.generators.iterator();
		
		while (generatorsIterator.hasNext())
		{
			Generator generator = (Generator)generatorsIterator.next();
			
			if (generator.getName().equals(generatorName) == true)
			{
				retGenerator = generator;
				break;
			}
		}
		
		return retGenerator;
	}

	public InputData addInput(Generator generator, String inputGeneratorName, Integer inputType, Float inputValue)
	{
		InputData inputData;
		
		/*
		 TrackData inputTrackData;
		 
		 if (inputGeneratorName != null)
		 {
		 if (inputGeneratorName.length() > 0)
		 {
		 //inputTrackData = this.controllerData.getTracksData().searchTrackData(inputGeneratorName);
		 inputTrackData = tracksData.searchTrackData(inputGeneratorName);
		 
		 // Found no Track with the name of the Input ?
		 if (inputTrackData == null)
		 {
		 throw new RuntimeException("input generator \"" + inputGeneratorName + "\" not found");
		 }
		 }
		 else
		 {
		 inputTrackData = null;
		 }
		 }
		 else
		 {
		 inputTrackData = null;
		 }
		 
		 Generator inputGenerator;
		 
		 // Found a Track with the name of the Input ?
		 if (inputTrackData != null)
		 {
		 inputGenerator = inputTrackData.getGenerator();
		 }
		 else
		 {
		 inputGenerator = null;
		 }
		 */
		Generator inputGenerator = this.searchGenerator(inputGeneratorName);
		
		inputData = generator.addInputGenerator(inputGenerator, inputType.intValue(), inputValue);
		
		return inputData;
	}

}