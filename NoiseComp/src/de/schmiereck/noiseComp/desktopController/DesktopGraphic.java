package de.schmiereck.noiseComp.desktopController;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import de.schmiereck.noiseComp.desktopPage.DesktopPageData;
import de.schmiereck.noiseComp.desktopPage.DesktopPageGraphic;
import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.graphic.MultiBufferFullScreenGraphic;

/**
 * Manages the full screen grafic of the desktop and calls the 
 * nesesarry functions to draw grafic reflecting the actual application state. 
 *
 * @author smk
 * @version 08.01.2004
 */
public class DesktopGraphic
extends MultiBufferFullScreenGraphic
{
	private DesktopPageGraphic desktopPageGraphic;
	
	public DesktopGraphic()
	{
		this.desktopPageGraphic = new DesktopPageGraphic();
	}
	
	/**
	 * @param gameControllerData
	 */
	public void initGrafic(DesktopControllerData gameControllerData)
	{
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.MultiBufferFullScreenGraphic#drawGraphic(java.awt.Graphics, de.schmiereck.screenTools.controller.ControllerData, java.awt.Rectangle, long)
	 */
	public void drawGraphic(Graphics g, ControllerData controllerData, Rectangle bounds, long actualWaitPerFramesMillis)
	{
		controllerData.setGraphicSleepMillis(actualWaitPerFramesMillis);
		
		DesktopControllerData desktopControllerData = (DesktopControllerData)controllerData;
		
		DesktopPageData desktopPageData = desktopControllerData.getActiveDesktopPageData();
		
		this.desktopPageGraphic.drawWidgets(g, this, desktopPageData);
		
		this.drawFPSInfos(g, controllerData);

		if (desktopControllerData.getPopupRuntimeExceptionText() != null)
		{
			g.drawString("ERROR: " + desktopControllerData.getPopupRuntimeExceptionText(), 
					9, 9);
		}
		
		this.desktopPageGraphic.drawPointer(g, this, desktopPageData);
	}

	/**
	 * Draws the frames per second informations of the graphic and calculating threads.
	 * 
	 * @param g
	 * @param controllerData
	 */
	private void drawFPSInfos(Graphics g, ControllerData controllerData)
	{
		g.setColor(Color.DARK_GRAY);

		this.setFont(g, "Dialog", Font.PLAIN, 16);
		
		if (controllerData.getGraphicSleepMillis() != 0)
		{	
			g.drawString("draw: " + (1000 / controllerData.getGraphicSleepMillis()) + " fps", 4, 18);
		}
		if (controllerData.getCalcSleepMillis() != 0)
		{	
			g.drawString("calc: " + (1000 / controllerData.getCalcSleepMillis()) + " fps", 4, 34);
		}
	}
}
