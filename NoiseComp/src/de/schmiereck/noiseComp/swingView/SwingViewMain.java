/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView;

import javax.sound.sampled.SourceDataLine;
import javax.swing.UIManager;

import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.service.StartupService;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.soundSource.SoundSourceSchedulerLogic;

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
	// Fields:

	private static SoundSourceSchedulerLogic soundSourceSchedulerLogic = null;

	//**********************************************************************************************
	// Functions:

	/**
	 * @param args
	 * 			are the command line arguments.
 	 */
	public static void main(String[] args)
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		StartupService.createBaseGeneratorTypes();
		
		// new ModulGeneratorTypeData(null, null, null);
		ModulGeneratorTypeData mainModulGeneratorTypeData = ModulGenerator.createModulGeneratorTypeData();

		mainModulGeneratorTypeData.setIsMainModulGeneratorType(true);
		
		mainModulGeneratorTypeData.setGeneratorTypeName("Swing-Main-Modul");

		soundService.addGeneratorType(mainModulGeneratorTypeData);
		
		//------------------------------------------------------------------------------------------
		// Setup Sound:
		
		SourceDataLine line = StartupService.createLine();
		
		//------------------------------------------------------------------------------------------
		SoundSourceLogic soundSourceLogic = new SoundSourceLogic();
		
		SoundData soundData = new SoundData(line, soundSourceLogic);
		
		//------------------------------------------------------------------------------------------
		OutputGenerator outputGenerator = 
			StartupService.createDemoGenerators(soundData.getFrameRate(), 
			                                    mainModulGeneratorTypeData);

		soundSourceLogic.setOutputGenerator(outputGenerator);
		
		//------------------------------------------------------------------------------------------
	
		soundSourceSchedulerLogic = new SoundSourceSchedulerLogic(16);
		
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
