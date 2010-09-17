/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView;

import javax.sound.sampled.SourceDataLine;
import javax.swing.UIManager;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.service.StartupService;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.soundSource.SoundSourceSchedulerLogic;
import de.schmiereck.noiseComp.swingView.appController.AppController;

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
	// Fields:

	private static SoundData soundData;
	
	private static SoundSourceSchedulerLogic soundSourceSchedulerLogic;

	//**********************************************************************************************
	// Functions:

	/**
	 * @param args
	 * 			are the command line arguments.
 	 */
	public static void main(String[] args)
	{
		//==========================================================================================
		StartupService.createBaseGeneratorTypes();
		
		//------------------------------------------------------------------------------------------
		// Setup Sound:
		
		SourceDataLine line = StartupService.createLine();
		
		//------------------------------------------------------------------------------------------
		SoundSourceLogic soundSourceLogic = new SoundSourceLogic();
		
		soundData = new SoundData(line, soundSourceLogic);
		
		//------------------------------------------------------------------------------------------
		final ModulGeneratorTypeData mainModulGeneratorTypeData = 
			StartupService.createDemoGenerators(soundData.getFrameRate());

		OutputGenerator outputGenerator = mainModulGeneratorTypeData.getOutputGenerator();
		
		soundSourceLogic.setOutputGenerator(outputGenerator);
		
		//------------------------------------------------------------------------------------------
	
		soundSourceSchedulerLogic = new SoundSourceSchedulerLogic(32);
		
		// Start scheduled polling with the new SoundSource.
		soundSourceSchedulerLogic.setSoundSourceLogic(soundSourceLogic);

		soundSourceSchedulerLogic.startThread();
	
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
					
					AppController appController = new AppController();
					
					appController.selectEditModule(mainModulGeneratorTypeData);
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
		
		//==========================================================================================
	}

	public static SoundData getSoundData()
	{
		return soundData;
	}
}
