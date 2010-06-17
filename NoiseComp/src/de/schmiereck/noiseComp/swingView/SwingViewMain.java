/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView;

import javax.swing.UIManager;

/**
 * <p>
 * 	Swing-View ConsoleMain.
 * </p>
 * 
 * @author smk
 * @version <p>17.06.2010:	created, smk</p>
 */
public class SwingViewMain
{
	//**********************************************************************************************
	// Functions:

	/**
	 * @param args
	 * 			are the command line arguments.
 	 */
	public static void main(String[] args)
	{
		//==========================================================================================
		//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
        	/* (non-Javadoc)
        	 * @see java.lang.Runnable#run()
        	 */
        	public void run()
        	{
        		try
				{
					createAndShowGUI();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
            }
        });
		//==========================================================================================
	}
	
	/**
	 * @throws Exception
	 * 			if there is an Error in View. 
 	 */
	private static void createAndShowGUI() 
	throws Exception
	{
		//==========================================================================================
    	//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    	//UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        //UIManager.setLookAndFeel("com.l2fprod.gui.plaf.skin.SkinLookAndFeel");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	
    	AppView appView = new AppView();
        
        appView.setTitle("NoiseComp V2.0");
        
        appView.setSize(800, 600);
        
        appView.setLocationRelativeTo(null);
        
        appView.setVisible(true);
        
		//==========================================================================================
	}
}
