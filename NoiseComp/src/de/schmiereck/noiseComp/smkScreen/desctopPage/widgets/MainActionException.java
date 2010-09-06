package de.schmiereck.noiseComp.smkScreen.desctopPage.widgets;
/*
 * Created on 20.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	Wird geworfen, wenn in einem {@link ButtonActionLogicListenerInterface}-Listener
 * 	ein Fehler auftritt.
 * </p>
 * 
 * @author smk
 * @version <p>20.03.2005:	created, smk</p>
 */
public class MainActionException
	extends Exception
{

	/**
	 * Constructor.
	 * 
	 */
	public MainActionException(String message)
	{
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 */
	public MainActionException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
