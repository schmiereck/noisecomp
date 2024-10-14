package de.schmiereck.noiseComp.generator.module;

import java.util.Iterator;
import java.util.Vector;

import de.schmiereck.noiseComp.PopupRuntimeException;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.signal.OutputGenerator;
import de.schmiereck.noiseComp.soundSource.SoundSourceData;


/**
 * Is the Generator-Type-Data of a generator manages a
 * list of {@link de.schmiereck.noiseComp.generator.Generator}-Objects
 * to generate the output.
 *
 * @author smk
 * @version <p>28.02.2004: created, smk</p>
 */
public class ModuleGeneratorTypeData
	extends GeneratorTypeData
//	implements GeneratorChangeListenerInterface
{
	//**********************************************************************************************
	// Fields:

	/**
	 * The Output-Object of the Sound-Generators.
	 */
	private OutputGenerator outputGenerator = null;

//	/**
//	 * List of {@link Generator}-Objects, used to generate the output.
//	 */
//	private Generators generators = new Generators();
	
	/**
	 * true, if the ModuleGeneratorType is the main module
	 */
	private boolean isMainModuleGeneratorType = false;

	//private GeneratorChangeObserver generatorChangeObserver = null;
	
	/**
	 * List of Tracks with generators placed on it.<br/>
	 * Used to display the generators in editing mode.
	 */
	private Vector<Generator> generators = new Vector<Generator>();
	
	/**
	 * View Zoom X.
	 */
	private Float viewZoomX = null;
	
	/**
	 * Tick Units.
	 */
	public enum TicksPer
	{
		Seconds,
		Milliseconds,
		BPM
	}
	
	/**
	 * {@link #viewTicksCount} per value.
	 */
	private TicksPer viewTicksPer = null;
	
	/**
	 * Count of ticks per {@link #viewTicksPer}.
	 */
	private Float viewTicksCount = null;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public ModuleGeneratorTypeData(String folderPath,
	                              Class<? extends Generator> generatorClass, 
								  String generatorTypeName, 
								  String generatorTypeDescription)
	{
		super(folderPath, generatorClass, generatorTypeName, generatorTypeDescription);
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
	 * @return returns the {@link #isMainModuleGeneratorType}.
	 */
	public boolean getIsMainModuleGeneratorType()
	{
		return this.isMainModuleGeneratorType;
	}
	/**
	 * @param isMainModuleGeneratorType to set {@link #isMainModuleGeneratorType}.
	 */
	public void setIsMainModuleGeneratorType(boolean isMainModuleGeneratorType)
	{
		this.isMainModuleGeneratorType = isMainModuleGeneratorType;
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
	 * Alle Generatoren durchlaufen und benachrichtigen 
	 * das einer der ihren gelöscht wurde (als Input entfernen usw.):
	 * 
	 * @param removedGenerator
	 * 			is the generator.
	 */
	private void notifyGeneratorsOfRemoving(final SoundSourceData soundSourceData, Generator removedGenerator)
	{
		//==========================================================================================
		if (removedGenerator != null)
		{
			Iterator<Generator> generatorsIterator = this.generators.iterator();

			while (generatorsIterator.hasNext())
			{
				Generator generator = generatorsIterator.next();
				
				generator.notifyModuleGeneratorRemoved(soundSourceData, removedGenerator);
			}
			
			// Output removed ?
			if (removedGenerator == this.outputGenerator)
			{
				this.setOutputGenerator(null);
			}
		}
		//==========================================================================================
	}

	public void clear(final SoundSourceData soundSourceData) {
		//==========================================================================================
		synchronized (this)
		{
			Iterator<Generator> generatorsIterator = this.generators.iterator();
			
			while (generatorsIterator.hasNext())
			{
				Generator generator = generatorsIterator.next();
				
				this.removeGenerator(soundSourceData, generator);
			}

			this.setOutputGenerator(null);
		}
		//==========================================================================================
	}

	/**
	 * @param generator 
	 * 			is the Generator to add.
	 * @param generatorPos
	 * 			is the position of the generator in the list of {@link #generators}.
	 */
	public void addGenerator(int generatorPos,
	                         Generator generator)
	{
		//==========================================================================================
		synchronized (this)
		{
			this.addGeneratorWithoutTrack(generatorPos,
			                              generator);
			
//			this.addTrackForExistingGenerator(generator);

			// Registriert sich bei dem Generator als Listener.
			//generator.getGeneratorChangeObserver().registerGeneratorChangeListener(this);
		}
		
		//==========================================================================================
	}

	/**
	 * Adds the given generator at the end of the list of {@link #generators}.
	 * 
	 * @param generator 
	 * 			is the Generator to add.
	 */
	public void addGenerator(Generator generator)
	{
		//==========================================================================================
		int generatorPos = this.generators.size();
		
		this.addGenerator(generatorPos,
		                  generator);
		
		//==========================================================================================
	}
	/**
	 * @param generatorPos
	 * 			is the position of the generator in the list of {@link #generators}.
	 * @param generator
	 * 			is the Generator to add.
	 */
	public void addGeneratorWithoutTrack(int generatorPos,
	                                     Generator generator)
	{
		//==========================================================================================
		this.generators.add(generatorPos, generator);
		
		if (generator instanceof OutputGenerator)
		{	
			this.setOutputGenerator((OutputGenerator)generator);
		}
		//==========================================================================================
	}
	
	public void addTrackForExistingGeneratorByName(String generatorName)
	{
		//==========================================================================================
		Generator generator = this.searchGenerator(generatorName);
		
		if (generator == null)
		{					
			throw new PopupRuntimeException("Can't find generator in track: " + generatorName);
		}

//		this.addTrackForExistingGenerator(generator);
//		this.generators.add(generator);
		//==========================================================================================
	}
	
//	public void addTrackForExistingGenerator(Generator generator)
//	{
//		//==========================================================================================
//		this.generators.add(generator);
//		
//		//==========================================================================================
//	}
	
	public void removeGenerator(final SoundSourceData soundSourceData, int trackPos) {
		//==========================================================================================
		synchronized (this) {
			Generator removedGenerator = (Generator)this.generators.get(trackPos);
			
			this.removeGenerator(soundSourceData, removedGenerator);

			// De-Registriert sich bei dem Generator als Listener.
			//removedGenerator.getGeneratorChangeObserver().removeGeneratorChangeListener(this);
		}
		//==========================================================================================
	}

	/**
	 * @param searchedGenerator 
	 * 			is the Generator to search for.
	 * @return 
	 * 			the Track of the generator or <code>null</code>.
	 */
	private Generator searchTrackData(Generator searchedGenerator)
	{
		//==========================================================================================
		Generator retGenerator;

		//------------------------------------------------------------------------------------------
		retGenerator = null;
		
		for (Generator generator : this.generators)
		{
			if (generator == searchedGenerator)
			{
				retGenerator = generator;
				break;
			}
		}
		//==========================================================================================
		return retGenerator;//(TrackData)this.tracksHash.get(generator);
	}

	public void removeGenerator(final SoundSourceData soundSourceData, final Generator removedeGenerator)
	{
		//==========================================================================================
		synchronized (this){
			this.generators.remove(removedeGenerator);

			final Generator generator = this.searchTrackData(removedeGenerator);
			
//			this.tracksData.removeSelectedTrack(trackData);
			this.generators.remove(generator);
		
			this.notifyGeneratorsOfRemoving(soundSourceData, removedeGenerator);

			removedeGenerator.notifyModuleGeneratorRemoveListeners(soundSourceData, removedeGenerator);
			
			// De-Registriert sich bei dem Generator als Listener.
			//generator.getGeneratorChangeObserver().removeGeneratorChangeListener(this);
		}
		//==========================================================================================
	}
	
	public Iterator<Generator> getGeneratorsIterator()
	{
		//==========================================================================================
		Iterator<Generator> generatorsIterator;
		
		synchronized (this)
		{
			generatorsIterator = this.generators.iterator();
		}
		
		//==========================================================================================
		return generatorsIterator;
	}

	public Generator searchGenerator(String generatorName)
	{
		//==========================================================================================
		Generator retGenerator;
		
		synchronized (this)
		{
			retGenerator = null;
			
			for (Generator generator : this.generators)
			{
				if (generator.getName().equals(generatorName))
				{
					retGenerator = generator;
					break;
				}
			}
		}
		
		//==========================================================================================
		return retGenerator;
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
		// Meldet die Änderung an einem seiner Generatoren,
		// an die beim Module angemeldeten Listener-Objekten.
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
//	/**
//	 * @return returns the {@link #generators}.
//	 */
//	public TracksData getTracksData()
//	{
//		return this.tracksData;
//	}

	/**
	 * @see TracksData#getTracksIterator()
	 */
	public Iterator<Generator> getTracksIterator()
	{
		return this.generators.iterator();
	}

	/**
	 * @param sourceTrackPos
	 * 			is the source Position in the {@link #tracks}.
	 * @param tagetTrackPos
	 * 			is the target Position in the {@link #tracks}.
	 */
	public void switchTracksByPos2(int sourceTrackPos, int tagetTrackPos)
	{
		//==========================================================================================
		if ((sourceTrackPos >= 0) &&
			(tagetTrackPos >= 0) &&
			(sourceTrackPos < this.generators.size()) &&
			(tagetTrackPos < this.generators.size()))
		{
			Generator sourceTrackData = this.generators.get(sourceTrackPos);
			Generator targetTrackData = this.generators.get(tagetTrackPos);
			
			this.generators.setElementAt(sourceTrackData, tagetTrackPos);
			this.generators.setElementAt(targetTrackData, sourceTrackPos);
			
//			targetTrackData.setTrackPos(sourceTrackPos);
//			sourceTrackData.setTrackPos(tagetTrackPos);
		}
		//==========================================================================================
	}

	/**
	 * @see TracksData#switchTracksByPos(int, int)
	 */
	public void switchTracksByPos(int sourceTrackPos, int tagetTrackPos)
	{
		//==========================================================================================
//		this.generators.switchTracksByPos(sourceTrackPos, tagetTrackPos);
		
		this.switchTracksByPos2(sourceTrackPos, tagetTrackPos);
		
		//==========================================================================================
	}
	/**
	 * @return 
	 * 			returns the {@link #viewZoomX}.
	 */
	public Float getViewZoomX()
	{
		return this.viewZoomX;
	}

	/**
	 * @param viewZoomX 
	 * 			to set {@link #viewZoomX}.
	 */
	public void setViewZoomX(Float viewZoomX)
	{
		this.viewZoomX = viewZoomX;
	}

	/**
	 * @return 
	 * 			returns the {@link #viewTicksPer}.
	 */
	public TicksPer getViewTicksPer()
	{
		return this.viewTicksPer;
	}

	/**
	 * @param viewTicksPer 
	 * 			to set {@link #viewTicksPer}.
	 */
	public void setViewTicksPer(TicksPer ticksPer)
	{
		this.viewTicksPer = ticksPer;
	}

	/**
	 * @return 
	 * 			returns the {@link #viewTicksCount}.
	 */
	public Float getViewTicksCount()
	{
		return this.viewTicksCount;
	}

	/**
	 * @param ticksCount 
	 * 			to set {@link #viewTicksCount}.
	 */
	public void setViewTicksCount(Float ticksCount)
	{
		this.viewTicksCount = ticksCount;
	}
}
