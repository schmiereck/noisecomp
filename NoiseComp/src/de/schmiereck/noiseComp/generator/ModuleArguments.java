/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

/**
 * <p>
 * 	Module Arguments are the values of module Input-Types.
 * </p>
 * 
 * @author smk
 * @version <p>29.10.2010:	created, smk</p>
 */
public class ModuleArguments
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Calling Module-Generator Buffer.
	 */
	private GeneratorBufferInterface callingModuleGeneratorBuffer;
	
	/**
	 * Prev Module Arguments.
	 */
	private ModuleArguments prevModuleArguments;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * @param callingModuleGeneratorBuffer
	 * 			is the Calling Module-Generator Buffer.
	 * @param prevModuleArguments
	 * 			are the Prev Module Arguments.
	 */
	public ModuleArguments(GeneratorBufferInterface callingModuleGeneratorBuffer,
	                      ModuleArguments prevModuleArguments)
	{
		this.callingModuleGeneratorBuffer = callingModuleGeneratorBuffer;
		this.prevModuleArguments = prevModuleArguments;
	}

	/**
	 * @return 
	 * 			returns the {@link #callingModuleGeneratorBuffer}.
	 */
	public GeneratorBufferInterface getCallingModuleGeneratorBuffer()
	{
		return this.callingModuleGeneratorBuffer;
	}

	/**
	 * @return 
	 * 			returns the {@link #prevModuleArguments}.
	 */
	public ModuleArguments getPrevModuleArguments()
	{
		return this.prevModuleArguments;
	}
}
