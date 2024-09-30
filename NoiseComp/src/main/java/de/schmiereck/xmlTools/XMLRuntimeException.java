package de.schmiereck.xmlTools;
/*
 * Created on 25.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	TODO docu type
 * </p>
 * 
 * @author smk
 * @version <p>25.03.2005:	created, smk</p>
 */
public class XMLRuntimeException
	extends RuntimeException
{
	/**
	 * @param message
	 */
	public XMLRuntimeException(String message)
	{
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public XMLRuntimeException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public XMLRuntimeException(Throwable cause)
	{
		super(cause);
	}
}
