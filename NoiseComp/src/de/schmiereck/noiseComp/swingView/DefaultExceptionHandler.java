/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView;

import java.awt.Frame;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.schmiereck.noiseComp.swingView.error.ErrorController;

/**
 * <p>
 * 	Default Exception-Handler.
 * </p>
 * 
 * http://www.roseindia.net/javatutorials/catching_uncaught_exceptions_in_jdk_5.shtml
 * http://androidblogger.blogspot.com/2009/12/how-to-improve-your-application-crash.html
 * 
 * @author smk
 * @version <p>15.03.2011:	created, smk</p>
 */
public class DefaultExceptionHandler
implements UncaughtExceptionHandler 
{
	//**********************************************************************************************
	// Fields:
	
	private UncaughtExceptionHandler previousHandler;
	
	private final ErrorController errorController;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public DefaultExceptionHandler()
	{
		//==========================================================================================
		this.previousHandler = Thread.getDefaultUncaughtExceptionHandler();
		
		Thread.setDefaultUncaughtExceptionHandler(this);
		
		//------------------------------------------------------------------------------------------
		this.errorController = new ErrorController();
		
		//==========================================================================================
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang.Thread, java.lang.Throwable)
	 */
	public void uncaughtException(Thread t, Throwable e) 
	{
		//==========================================================================================
		//String timestamp = TimestampFormatter.getInstance().getTimestamp();
		
		//------------------------------------------------------------------------------------------
		final Writer resultWriter = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(resultWriter);
		
		e.printStackTrace(printWriter);
		
		String stacktrace = resultWriter.toString();
		
		// If the exception was thrown in a background thread inside
		// AsyncTask, then the actual exception can be found with getCause
		Throwable cause = e.getCause();
		
		if (cause != null)
		{
			stacktrace += "Cause:\n";

			while (cause != null)
			{
				cause.printStackTrace(printWriter);
				
				stacktrace += resultWriter.toString();
				
				cause = cause.getCause();
			}
		}
		printWriter.close();
		
		//------------------------------------------------------------------------------------------
		Frame activeFrame = this.findActiveFrame();
		
		JOptionPane.showMessageDialog(activeFrame,
									  stacktrace, 
									  "Exception Occurred", 
									  JOptionPane.OK_OPTION);

		this.errorController.showError(activeFrame,
		                               stacktrace);
		
		//------------------------------------------------------------------------------------------
		//e.printStackTrace();
		if (this.previousHandler != null)
		{
			this.previousHandler.uncaughtException(t, e);
		}
	
		//==========================================================================================
	}
	
	private Frame findActiveFrame() 
	{
		//==========================================================================================
		Frame[] frames = JFrame.getFrames();
		
		for (int i = 0; i < frames.length; i++) 
		{
			if (frames[i].isVisible()) 
			{
				return frames[i];
			}
		}
		
		//==========================================================================================
		return null;
	}
}
