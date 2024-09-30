package de.schmiereck.noiseComp;

/**
 * Runtime exception of this type are shown as arror popups to the user.
 *
 * @author smk
 * @version <p>11.04.2004: created, smk</p>
 */
public class PopupRuntimeException
	extends RuntimeException
{
	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public PopupRuntimeException(String message)
	{
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 * @param cause
	 */
	public PopupRuntimeException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public PopupRuntimeException(Throwable cause)
	{
		super(cause);
	}
}
