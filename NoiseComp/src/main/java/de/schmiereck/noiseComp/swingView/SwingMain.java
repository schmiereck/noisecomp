/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView;

import javax.sound.sampled.SourceDataLine;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeInfoData;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.service.StartupService;
import de.schmiereck.noiseComp.soundScheduler.SoundDataLogic;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.appController.AppController;

import java.awt.*;
import java.util.Enumeration;

/**
 * <p>
 * 	Swing Main.
 * </p>
 * 
 * @author smk
 * @version <p>17.06.2010:	created, smk</p>
 */
public class SwingMain
{
	//**********************************************************************************************
	// Constants:

	/**
	 * Node Name der Preferences.
	 */
	public static final String	PREFERENCES_ROOT_NODE_NAME	= "/de/schmiereck/noiseComp";
	
	//**********************************************************************************************
	// Fields:
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @param args
	 * 			are the command line arguments.
 	 */
	public static void main(final String[] args) {
		//==========================================================================================
		final SoundService soundService = new SoundService();

		StartupService.createBaseGeneratorTypes(soundService);
		
		//------------------------------------------------------------------------------------------
		// Setup Sound:
		
		final SourceDataLine line = StartupService.createLine();
		
		//------------------------------------------------------------------------------------------
		final SoundSourceLogic soundSourceLogic = new SoundSourceLogic();
		
		final SoundDataLogic soundDataLogic = new SoundDataLogic(line, soundSourceLogic);

		//==========================================================================================
		Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler());

		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable()  {
			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			public void run() {
				try {
					createAndShowGUI();
					
					final AppController appController = new AppController(soundSourceLogic, soundDataLogic, soundService);

					// see: LoadFileOperationLogic#loadNoiseCompFile

					final ModuleGeneratorTypeInfoData mainModuleGeneratorTypeData =
							StartupService.createDemoGenerators(appController.getSoundSourceData(),
									soundService, soundDataLogic.getFrameRate());

					appController.changeEditModule(mainModuleGeneratorTypeData);

					appController.initStartupModel();

					appController.startSoundSourceScheduler();

				} catch (final Exception ex) {
					System.out.println("EX 1: " + ex);
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
	private static void createAndShowGUI() throws Exception {
		//==========================================================================================
		//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		//UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		//UIManager.setLookAndFeel("com.l2fprod.gui.plaf.skin.SkinLookAndFeel");
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		// Set the default font size
		setUIFont(new FontUIResource(new Font("SansSerif", Font.PLAIN, 12)));

		//==========================================================================================
	}
	public static void setUIFont(FontUIResource f) {
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				// Setup font size based on screen size
				//UIManager.put("Label.font", new Font("Dialog", Font.BOLD, 8)); //Change the 8 to what ever you want
				//UIManager.put("Button.font", new Font("Dialog", Font.BOLD, 8));
				//UIManager.put("RadioButton.font", new Font("Dialog", Font.BOLD, 8));
				//...
				UIManager.put(key, f);
			}
		}
	}
}
