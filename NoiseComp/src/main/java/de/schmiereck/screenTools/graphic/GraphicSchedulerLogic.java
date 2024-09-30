package de.schmiereck.screenTools.graphic;

import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.scheduler.SchedulerLogic;
import de.schmiereck.screenTools.scheduler.SchedulerWaiter;

/**
 * Stellt die Framerate wie vorgegeben ein,
 * reguliert sie gegebenenfalls nach unten, wenn die Zeichenroutine nicht nachkommt.
 * 
 * @author smk
 * @version 29.11.2003
 */
public class GraphicSchedulerLogic
extends SchedulerLogic
{
	private ControllerData controllerData = null;
	
	private MultiBufferFullScreenGraphic multiBufferGraphic = null;
	
	public GraphicSchedulerLogic(ControllerData controllerData, MultiBufferFullScreenGraphic multiBufferGraphic, SchedulerWaiter waiter, int targetFramesPerSecond)
	{
		super(targetFramesPerSecond);
		
		this.controllerData = controllerData;
		this.multiBufferGraphic = multiBufferGraphic;
	}

	public void notifyRunSchedul(long actualWaitPerFramesMillis)
	{
		synchronized (this.controllerData)
		{
			this.multiBufferGraphic.drawBuffer(this.controllerData, actualWaitPerFramesMillis);
		}
	}
}
