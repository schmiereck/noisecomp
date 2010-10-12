package de.schmiereck.noiseComp.generator;

import java.util.Iterator;

import de.schmiereck.noiseComp.PopupRuntimeException;


/**
 * Is the Generator-Type-Data of a generator manages a
 * list of {@link de.schmiereck.noiseComp.generator.Generator}-Objects
 * to generate the output.
 *
 * @author smk
 * @version <p>28.02.2004: created, smk</p>
 */
public class ModulGeneratorTypeData
	extends GeneratorTypeData
//	implements GeneratorChangeListenerInterface
{
	//**********************************************************************************************
	// Fields:

	/**
	 * The Output-Object of the Sound-Generators.
	 */
	private OutputGenerator	outputGenerator = null;

	/**
	 * List of {@link Generator}-Objects, used to generate the output.
	 */
	private Generators generators = new Generators();
	
	/**
	 * true, if the ModulGeneratorType is the main modul.
	 */
	private boolean isMainModulGeneratorType = false;

	//private GeneratorChangeObserver generatorChangeObserver = null;
	
	/**
	 * List of Tracks with generators placed on it.<br/>
	 * Used to display the generators in editing mode.
	 */
	private TracksData tracksData = new TracksData();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public ModulGeneratorTypeData(Class<? extends Generator> generatorClass, 
								  String generatorTypeName, 
								  String generatorTypeDescription)
	{
		super(generatorClass, generatorTypeName, generatorTypeDescription);
	}
	
	/**
	 * @see #generators
	public void setGenerators(Generators generators)
	{
		this.generators = generators;
	}
	 */
	/**
	 * @see #generators
	public Generators getGenerators()
	{
		return this.generators;
	}
	 */

	/**
	 * @return returns the {@link #isMainModulGeneratorType}.
	 */
	public boolean getIsMainModulGeneratorType()
	{
		return this.isMainModulGeneratorType;
	}
	/**
	 * @param isMainModulGeneratorType to set {@link #isMainModulGeneratorType}.
	 */
	public void setIsMainModulGeneratorType(boolean isMainModulGeneratorType)
	{
		this.isMainModulGeneratorType = isMainModulGeneratorType;
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
	private void setOutputGenerator(OutputGenerator outputGenerator)
	{
		this.outputGenerator = outputGenerator;
	}

	/**
	 * Alle Generatoren durchlaufen und benachrichtigen 
	 * das einer der ihren gelöscht wurde (als Input entfernen usw.):
	 * 
	 * @param removedGenerator
	 * 			is the generator.
	 */
	private void notifyGeneratorsOfRemoving(Generator removedGenerator)
	{
		if (removedGenerator != null)
		{
			Iterator<Generator> generatorsIterator = this.generators.getGeneratorsIterator();

			while (generatorsIterator.hasNext())
			{
				Generator generator = generatorsIterator.next();
				
				generator.notifyModulGeneratorRemoved(removedGenerator);
			}
			
			// Output removed ?
			if (removedGenerator == this.outputGenerator)
			{
				this.setOutputGenerator(null);
			}
		}
	}

	/**
	 * 
	 */
	public void clear()
	{
		synchronized (this)
		{
			Iterator<Generator> generatorsIterator = this.generators.getGeneratorsIterator();
			
			while (generatorsIterator.hasNext())
			{
				Generator generator = generatorsIterator.next();
				
				this.removeGenerator(generator);
			}

			this.setOutputGenerator(null);
		}
	}

	/**
	 * @param generator is the Generator to add.
	 */
	public TrackData addGenerator(Generator generator)
	{
		TrackData trackData;
		
		synchronized (this)
		{
			this.addGeneratorWithoutTrack(generator);
			
			trackData = this.addTrackForExistingGenerator(generator);

			// Registriert sich bei dem Generator als Listener.
			//generator.getGeneratorChangeObserver().registerGeneratorChangeListener(this);
		}
		
		return trackData;
	}

	public void addGeneratorWithoutTrack(Generator generator)
	{
		this.generators.addGenerator(generator);
		
		if (generator instanceof OutputGenerator)
		{	
			this.setOutputGenerator((OutputGenerator)generator);
		}
	}
	
	public void addTrackForExistingGeneratorByName(String generatorName)
	{
		Generator generator = this.searchGenerator(generatorName);
		
		if (generator == null)
		{					
			throw new PopupRuntimeException("Can't find generator in track: " + generatorName);
		}

		this.addTrackForExistingGenerator(generator);
	}
	
	public TrackData addTrackForExistingGenerator(Generator generator)
	{
		TrackData trackData;

		trackData = new TrackData(generator);
		
		this.tracksData.addTrack(trackData);
		
		return trackData;
	}
	
	public void removeGenerator(int trackPos)
	{
		synchronized (this)
		{
			Generator removedGenerator = (Generator)this.generators.getGenerator(trackPos);
			
			this.removeGenerator(removedGenerator);

			// De-Registriert sich bei dem Generator als Listener.
			//removedGenerator.getGeneratorChangeObserver().removeGeneratorChangeListener(this);
		}
	}

	public void removeGenerator(Generator generator)
	{
		synchronized (this)
		{
			this.generators.removeGenerator(generator);
			
			TrackData trackData = this.tracksData.searchTrackData(generator);
			
			this.tracksData.removeSelectedTrack(trackData.getTrackPos());
		
			this.notifyGeneratorsOfRemoving(generator);

			generator.notifyModulGeneratorRemoveListeners(generator);
			
			// De-Registriert sich bei dem Generator als Listener.
			//generator.getGeneratorChangeObserver().removeGeneratorChangeListener(this);
		}
	}
	
	public Iterator<Generator> getGeneratorsIterator()
	{
		Iterator<Generator> generatorsIterator;
		
		synchronized (this)
		{
			generatorsIterator = this.generators.getGeneratorsIterator();
		}
		
		return generatorsIterator;
	}

	public Generator searchGenerator(String generatorName)
	{
		Generator generator;
		
		synchronized (this)
		{
			generator = this.generators.searchGenerator(generatorName);
		}
		
		return generator;
	}

	/**
	 * @return returns the {@link #generatorChangeObserver}.
	public GeneratorChangeObserver getGeneratorChangeObserver()
	{
		synchronized (this)
		{
			if (this.generatorChangeObserver == null)
			{
				this.generatorChangeObserver = new GeneratorChangeObserver();
			}
		}
		
		return this.generatorChangeObserver;
	}
	 */
	
	/**
	 * @see #generatorChangeObserver
	public void generateChangedEvent(float startTimePos, float endTimePos)
	{
		synchronized (this)
		{
			if (this.generatorChangeObserver != null)
			{
				this.generatorChangeObserver.changedEvent(null, startTimePos, endTimePos);
			}
		}
	}
	*/

	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorChangeListenerInterface#notifyGeneratorChanged(de.schmiereck.noiseComp.generator.Generator, float, float)
	public void notifyGeneratorChanged(Generator generator, float startTimePos, float endTimePos)
	{
		// Meldet die �nderung an einem seiner Generatoren,
		// an die beim Modul angemeldeten Listener-Objekten.
		this.generateChangedEvent(startTimePos, endTimePos);
	}
	 */

	/**
	 * @return returns the {@link #generatorChangeObserver}.
	public GeneratorChangeObserver getGeneratorChangeObserver()
	{
		return this.generatorChangeObserver;
	}
	 */
	/**
	 * @return returns the {@link #tracksData}.
	 */
	public TracksData getTracksData()
	{
		return this.tracksData;
	}

	/**
	 * @see TracksData#getTracksIterator()
	 */
	public Iterator<TrackData> getTracksIterator()
	{
		return this.tracksData.getTracksIterator();
	}

	/**
	 * @see TracksData#switchTracksByPos(int, int)
	 */
	public void switchTracksByPos(int sourceTrackPos, int tagetTrackPos)
	{
		this.generators.switchTracksByPos(sourceTrackPos, tagetTrackPos);
		
		this.tracksData.switchTracksByPos(sourceTrackPos, tagetTrackPos);
	}
}
