package de.schmiereck.screenTools.controller;

import de.schmiereck.screenTools.scheduler.SchedulerLogic;
import de.schmiereck.screenTools.scheduler.SchedulerWaiter;

/**
 * @author smk
 * @version 29.11.2003
 */
public class ControllerSchedulerLogic
extends SchedulerLogic
{
	private ControllerData controllerData = null;
	
	private ControllerLogic controllerLogic = null;
	
	public ControllerSchedulerLogic(ControllerData controllerData, ControllerLogic controllerLogic, SchedulerWaiter waiter, int targetFramesPerSecond)
	{
		super(targetFramesPerSecond);
		
		this.controllerData = controllerData;
		this.controllerLogic = controllerLogic;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.scheduler.SchedulerLogic#notifyRunSchedul(long)
	 */
	public void notifyRunSchedul(long actualWaitPerFramesMillis)
	{
		synchronized (this.controllerData)
		{
			this.controllerLogic.calc(controllerData, actualWaitPerFramesMillis, actualWaitPerFramesMillis / 1000.0);
		}
	}
}
