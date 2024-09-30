/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JDialog;

/**
 * <p>
 * 	Dialog Utils.
 * </p>
 * 
 * @author smk
 * @version <p>27.01.2011:	created, smk</p>
 */
public class DialogUtils
{
	
	/**
	 * Centers the dialog within the parent container.
	 * 
	 * @param parentContainer
	 * 			is the parent of dialog.
	 * @param dialog
	 * 			is the dialog.
	 */
	public static void setLocationCenter(final Container parentContainer,
	                                     final JDialog dialog) 
	{
		Point topLeft = parentContainer.getLocationOnScreen();
		Dimension parentSize = parentContainer.getSize();

		Dimension dialogSize = dialog.getSize();

		int x;
		int y;

		if (parentSize.width > dialogSize.width) 
		{
			x = ((parentSize.width - dialogSize.width) / 2) + topLeft.x;
		}
		else
		{
			x = topLeft.x;
		}
   
		if (parentSize.height > dialogSize.height)
		{
			y = ((parentSize.height - dialogSize.height) / 2) + topLeft.y;
		}
		else
		{
			y = topLeft.y;
		}
   
		dialog.setLocation(x, y);
	}  
}
