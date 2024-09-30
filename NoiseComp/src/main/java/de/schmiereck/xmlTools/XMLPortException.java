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
public class XMLPortException
	extends XMLException
{
	/**
	 * @param message
	 */
	public XMLPortException(String message)
	{
		super(message);
	}

	/**
	 * @param message
	 * @param sourceException
	 */
	public XMLPortException(String message, Exception sourceException)
	{
		super(message, sourceException);
	}

	/**
	 * @param reason
	 * @param message
	 * @param sourceException
	 */
	public XMLPortException(String reason, String message, Exception sourceException)
	{
		super(reason, message, sourceException);
	}
}
