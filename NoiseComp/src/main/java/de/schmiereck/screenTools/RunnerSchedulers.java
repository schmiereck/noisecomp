package de.schmiereck.screenTools;

import de.schmiereck.screenTools.controller.ControllerSchedulerLogic;
import de.schmiereck.screenTools.graphic.GraphicSchedulerLogic;

/*
 * Created on 25.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	TODO docu type
 * </p>
 * 
 * @author smk
 * @version <p>25.03.2005:	created, smk</p>
 */
public class RunnerSchedulers
{
	private GraphicSchedulerLogic graphicSchedulerLogic;
	
	private ControllerSchedulerLogic gameSchedulerLogic;
	
	/**
	 * Constructor.
	 * 
	 */
	public RunnerSchedulers(GraphicSchedulerLogic graphicSchedulerLogic, ControllerSchedulerLogic gameSchedulerLogic)
	{
		this.graphicSchedulerLogic = graphicSchedulerLogic;
		this.gameSchedulerLogic = gameSchedulerLogic;
	}
	/**
	 * @return returns the {@link #gameSchedulerLogic}.
	 */
	public ControllerSchedulerLogic getGameSchedulerLogic()
	{
		return this.gameSchedulerLogic;
	}
	/**
	 * @return returns the {@link #graphicSchedulerLogic}.
	 */
	public GraphicSchedulerLogic getGraphicSchedulerLogic()
	{
		return this.graphicSchedulerLogic;
	}
}
