package de.schmiereck.noiseComp.xmlUtils;

/**
 * TODO docu
 *
 * @author smk
 * @version 16.01.2004
 */
public class XMLException 
extends RuntimeException
{
	
	/**
	 * Constructs an instance of <code>MainRuntimeException</code> with the specified detail message.
	 * @param msg the detail message.
	 */
	public XMLException(String message)
	{
		super(message);
	}
	
	/**
	 * Constructs an instance of <code>MainRuntimeException</code> with the specified detail message.
	 * @param msg the detail message.
	 */
	public XMLException(String message, java.lang.Exception sourceException)
	{
		super(sourceException == null ? message : message + "\nsourceException: " + sourceException.getMessage(), sourceException);
	}
	
	/**
	 * Constructs an instance of <code>MainRuntimeException</code> with the specified detail message.
	 * @param msg the detail message.
	 */
	public XMLException(String reason, String message, java.lang.Exception sourceException)
	{
		super(message, sourceException);
	}
}
