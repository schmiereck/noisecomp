package de.schmiereck.screenTools;

import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.controller.ControllerLogic;
import de.schmiereck.screenTools.controller.ControllerSchedulerLogic;
import de.schmiereck.screenTools.graphic.GraficInputListener;
import de.schmiereck.screenTools.graphic.GraphicSchedulerLogic;
import de.schmiereck.screenTools.graphic.MultiBufferFullScreenGraphic;
import de.schmiereck.screenTools.graphic.MultiBufferFullScreenGraphicException;
import de.schmiereck.screenTools.scheduler.SchedulerWaiter;

/**
 * Initialisiert die Objekte in der richtigen Reiehnfolge,<br/>
 * startet alle benötigten Threads (für Berechnungen und Grafik) und<br/>
 * führt die Logic aus.
 *
 * @author smk
 * @version 08.01.2004
 */
public class Runner
{
	public static void run(ControllerData controllerData, ControllerLogic gameControllerLogic, 
						   MultiBufferFullScreenGraphic multiBufferGraphic, 
						   GraficInputListener inputListener, 
						   SchedulerWaiter waiter, 
						   int graphicTargetFramesPerSecond,
						   int controllerTargetFramesPerSecond,
						   boolean startAsServer, 
						   boolean useFullScreen,
						   int centerDirX, int centerDirY)
	{
		try
		{
			RunnerSchedulers runnerSchedulers;
			
			runnerSchedulers = Runner.start(controllerData, gameControllerLogic, multiBufferGraphic, inputListener, waiter, graphicTargetFramesPerSecond, controllerTargetFramesPerSecond, startAsServer, useFullScreen, centerDirX, centerDirY);

			gameControllerLogic.startWaitGame();

			Runner.stop(multiBufferGraphic, useFullScreen, runnerSchedulers);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static RunnerSchedulers start(ControllerData controllerData, ControllerLogic gameControllerLogic, MultiBufferFullScreenGraphic multiBufferGraphic, GraficInputListener inputListener, SchedulerWaiter waiter, int graphicTargetFramesPerSecond, int controllerTargetFramesPerSecond, boolean startAsServer, boolean useFullScreen, int centerDirX, int centerDirY)
	{
		GraphicSchedulerLogic graphicSchedulerLogic;
		
		if (startAsServer != true)
		{
			graphicSchedulerLogic = new GraphicSchedulerLogic(controllerData, 
															  multiBufferGraphic, 
															  waiter, 
															  graphicTargetFramesPerSecond);
		}
		else
		{
			graphicSchedulerLogic = null;
		}
		
		ControllerSchedulerLogic gameSchedulerLogic = new ControllerSchedulerLogic(controllerData, 
																				   gameControllerLogic, 
																				   waiter, 
																				   controllerTargetFramesPerSecond);
		
		gameControllerLogic.initGameData(controllerData);
		
		try
		{
			if (multiBufferGraphic != null)
			{	
				//multiBufferGraphic.initGraphicBuffer();
				
				//if (useFullScreen == true)
				{	
					multiBufferGraphic.addInputListener(inputListener, useFullScreen);
				}

				multiBufferGraphic.switchModeToFullScreen(useFullScreen);
				
				multiBufferGraphic.initScreen(controllerData.getFieldWidth(), 
											  controllerData.getFieldHeight(), 
											  centerDirX, centerDirY);
			}

			if (graphicSchedulerLogic != null)
			{	
				graphicSchedulerLogic.startThread();
			}
			
			gameSchedulerLogic.startThread();
			
			if (startAsServer == true)
			{	
				gameControllerLogic.openGameServerConnection();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
			
		RunnerSchedulers runnerSchedulers = new RunnerSchedulers(graphicSchedulerLogic,
																 gameSchedulerLogic);
		return runnerSchedulers;
	}

	/**
	 * @param multiBufferGraphic
	 * @param useFullScreen
	 * @param graphicSchedulerLogic
	 * @param gameSchedulerLogic
	 */
	public static void stop(MultiBufferFullScreenGraphic multiBufferGraphic, 
							 boolean useFullScreen, 
							 RunnerSchedulers runnerSchedulers)
	{
		GraphicSchedulerLogic graphicSchedulerLogic = runnerSchedulers.getGraphicSchedulerLogic();
		
		ControllerSchedulerLogic gameSchedulerLogic = runnerSchedulers.getGameSchedulerLogic();
		 
		gameSchedulerLogic.stopThread();

		if (graphicSchedulerLogic != null)
		{	
			graphicSchedulerLogic.stopThread();
		}

		if (multiBufferGraphic != null)
		{	
			multiBufferGraphic.switchModeBack(useFullScreen);
		}
	}
}
