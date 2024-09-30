package de.schmiereck.screenTools.controller;
/*
 * Created on 26.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	TODO docu type
 * </p>
 * 
 * @author smk
 * @version <p>26.03.2005:	created, smk</p>
 */
public interface DataChangedListener
{
	public void notifyDataChanged(ControllerData controllerData,
								  int posX, int posY, int sizeX, int sizeY);

	/**
	 * 
	 */
	public void notifyDataChanged(ControllerData controllerData);
}
