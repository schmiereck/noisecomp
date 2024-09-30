/*
 * www.schmiereck.de (c) 2010
 */
package demo2;

import javax.swing.SwingUtilities;

/**
 * <p>
 * 	http://192.9.162.102/thread.jspa?messageID=10896446
 * </p>
 * 
 * @author smk
 * @version <p>03.11.2010:	created, smk</p>
 */
public class ViewportExampleMain
{
	public static void main(String... args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				ViewportExampleView viewportExampleView = new ViewportExampleView();
				
				viewportExampleView.setVisible(true);
			}
		});
	}
}
