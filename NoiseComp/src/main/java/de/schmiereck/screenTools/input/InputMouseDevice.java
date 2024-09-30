package de.schmiereck.screenTools.input;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.Robot;

/**
 * @author smk
 * @version 29.11.2003
 */
public class InputMouseDevice
implements InputDeviceInterface
{
	private GraphicsDevice graphicsDevice;
	
	private Robot robot = null;
	
	public InputMouseDevice(GraphicsDevice graphicsDevice) 
	throws InputDeviceException
	{
		this.graphicsDevice = graphicsDevice;

		try
		{
			this.robot = new Robot(graphicsDevice);
		}
		catch (AWTException ex)
		{
			throw new InputDeviceException("error while init mouse device", ex);
		}
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.ballblazer.InputDeviceInterface#centerMouseDevice()
	 */
	public void centerMouseDevice()
	{
		this.robot.mouseMove(320, 100);
	}

}
