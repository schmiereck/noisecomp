package de.schmiereck.screenTools.input;

/**
 * @author smk
 * @version 29.11.2003
 */
public class InputDeviceException 
extends Exception
{
	public InputDeviceException(String message) 
	{
		super(message);
	}

	public InputDeviceException(String message, Throwable cause) 
	{
		super(message, cause);
	}
}
