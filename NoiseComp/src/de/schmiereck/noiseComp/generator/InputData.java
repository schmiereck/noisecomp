package de.schmiereck.noiseComp.generator;

/**
 * Verwaltet einen {@link Generator} der als Input benutzt wird und den
 * Typ des Eingangs.
 *
 * @author smk
 * @version 01.02.2004
 */
public class InputData
{
	private Generator 	inputGenerator;
	private int 		inputType;
	
	/**
	 * Constructor.
	 * 
	 * @param inputGenerator
	 * @param inputType
	 */
	public InputData(Generator inputGenerator, int inputType)
	{
		super();
		this.inputGenerator = inputGenerator;
		this.inputType = inputType;
	}
	/**
	 * @return the attribute {@link #inputGenerator}.
	 */
	public Generator getInputGenerator()
	{
		return this.inputGenerator;
	}
	/**
	 * @return the attribute {@link #inputType}.
	 */
	public int getInputType()
	{
		return this.inputType;
	}
}
