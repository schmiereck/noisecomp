/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

/**
 * <p>
 * 	Modul Arguments are the values of modul Input-Types.
 * </p>
 * 
 * @author smk
 * @version <p>29.10.2010:	created, smk</p>
 */
public class ModulArguments
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Calling Modul-Generator Buffer.
	 */
	private GeneratorBufferInterface callingModulGeneratorBuffer;
	
	/**
	 * Prev Modul Arguments.
	 */
	private ModulArguments prevModulArguments;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * @param callingModulGeneratorBuffer
	 * 			is the Calling Modul-Generator Buffer.
	 * @param prevModulArguments
	 * 			are the Prev Modul Arguments.
	 */
	public ModulArguments(GeneratorBufferInterface callingModulGeneratorBuffer,
	                      ModulArguments prevModulArguments)
	{
		this.callingModulGeneratorBuffer = callingModulGeneratorBuffer;
		this.prevModulArguments = prevModulArguments;
	}

	/**
	 * @return 
	 * 			returns the {@link #callingModulGeneratorBuffer}.
	 */
	public GeneratorBufferInterface getCallingModulGeneratorBuffer()
	{
		return this.callingModulGeneratorBuffer;
	}

	/**
	 * @return 
	 * 			returns the {@link #prevModulArguments}.
	 */
	public ModulArguments getPrevModulArguments()
	{
		return this.prevModulArguments;
	}
}
