package de.schmiereck.noiseComp.generator;

import java.util.Iterator;


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
	 * Constructor.
	 * 
	 */
	public ModulGeneratorTypeData(Class generatorClass, 
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
	public void setOutputGenerator(OutputGenerator outputGenerator)
	{
		this.outputGenerator = outputGenerator;
	}

	/**
	 * Alle Generatoren durchlaufen und benachrichtigen 
	 * das einer der ihren gelöscht wurde (als Input entfernen usw.):
	 * @param removedGenerator
	 */
	private void notifyGeneratorsOfRemoving(Generator removedGenerator)
	{
		Iterator generatorsIterator = this.generators.getGeneratorsIterator();
		
		while (generatorsIterator.hasNext())
		{
			Generator generator = (Generator)generatorsIterator.next();
			
			generator.notifyRemoveGenerator(removedGenerator);
		}
		
		// Output removed ?
		if (removedGenerator == this.outputGenerator)
		{
			this.setOutputGenerator(null);
		}
	}

	/**
	 * 
	 */
	public void clear()
	{
		synchronized (this)
		{
			Iterator generatorsIterator = this.generators.getGeneratorsIterator();
			
			while (generatorsIterator.hasNext())
			{
				Generator generator = (Generator)generatorsIterator.next();
				
				this.removeGenerator(generator);
			}

			this.setOutputGenerator(null);
		}
	}

	/**
	 * @param generator is the Generator to add.
	 */
	public void addGenerator(Generator generator)
	{
		synchronized (this)
		{
			this.generators.addGenerator(generator);
			
			if (generator instanceof OutputGenerator)
			{	
				this.setOutputGenerator((OutputGenerator)generator);
			}

			// Registriert sich bei dem Generator als Listener.
			//generator.getGeneratorChangeObserver().registerGeneratorChangeListener(this);
		}
	}

	/**
	 * @param trackPos
	 */
	public void removeGenerator(int trackPos)
	{
		synchronized (this)
		{
			Generator removedGenerator = (Generator)this.generators.getGenerator(trackPos);
			
			this.generators.removeGenerator(removedGenerator);
			
			this.notifyGeneratorsOfRemoving(removedGenerator);

			// De-Registriert sich bei dem Generator als Listener.
			//removedGenerator.getGeneratorChangeObserver().removeGeneratorChangeListener(this);
		}
	}

	public void removeGenerator(Generator generator)
	{
		synchronized (this)
		{
			this.generators.removeGenerator(generator);
			
			this.notifyGeneratorsOfRemoving(generator);

			// De-Registriert sich bei dem Generator als Listener.
			//generator.getGeneratorChangeObserver().removeGeneratorChangeListener(this);
		}
	}
	
	public Iterator getGeneratorsIterator()
	{
		Iterator generatorsIterator;
		
		synchronized (this)
		{
			generatorsIterator = this.generators.getGeneratorsIterator();
		}
		
		return generatorsIterator;
	}

	public Generator searchGenerator(String inputGeneratorName)
	{
		Generator generator;
		
		synchronized (this)
		{
			generator = this.generators.searchGenerator(inputGeneratorName);
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
		// Meldet die Änderung an einem seiner Generatoren,
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
}
