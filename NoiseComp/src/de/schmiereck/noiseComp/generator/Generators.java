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
	 * List of {@link Generator}-Objects with an
	 * Integer-Position-Number as Key.
	 */
	private Vector	generators = new Vector();

	/**
	 * Constructor.
	 * 
	 */
	public Generators()
	{
		super();
	}

	/**
	 * @see #generators
	 * @param generator is the Generator to add.
	 */
	public void addGenerator(Generator generator)
	{
		synchronized (this)
		{
			this.generators.add(generator);
		}
	}

	/**
	 * @see #generators
	 */
	public void removeGenerator(Generator generator)
	{
		synchronized (this)
		{
			this.generators.remove(generator);
		}
	}
	
	/**
	 * @see #generators
	 * @return a Iterator over the {@link Generator}-Objects.
	 */
	public Iterator getGeneratorsIterator()
	{
		return this.generators.iterator();
	}

	/**
	 * @see #generators
	 */
	public Generator getGenerator(int trackPos)
	{
		return (Generator)this.generators.get(trackPos);
	}

	/**
	 * @see #generators
	 */
	public void clear()
	{
		synchronized (this)
		{
			this.generators.clear();
		}
	}

	/**
	 * @see #generators
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

	/**
	 * Add a new Input to the generator 'generator'.
	public InputData addInput(Generator generator, Generator inputGenerator, 
			InputTypeData inputTypeData, Float inputValue, InputTypeData inputModulInputTypeData)
	{
		InputData inputData;
		
		//Generator inputGenerator = this.searchGenerator(inputGeneratorName);
		
		inputData = generator.addInputGenerator(inputGenerator, inputTypeData, 
				inputValue, inputModulInputTypeData);
		
		return inputData;
	}
	*/
}