package de.schmiereck.noiseComp.desctopGraphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import de.schmiereck.noiseComp.desctopController.DesctopControllerData;
import de.schmiereck.noiseComp.desctopPage.DesctopPageData;
import de.schmiereck.noiseComp.desctopPage.DesctopPageGraphic;
import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.graphic.MultiBufferFullScreenGraphic;

/**
 * TODO docu
 *
 * @author smk
 * @version 08.01.2004
 */
public class DesctopGraphic
extends MultiBufferFullScreenGraphic
{
	private DesctopPageGraphic desctopPageGraphic;
	
	public DesctopGraphic()
	{
		this.desctopPageGraphic = new DesctopPageGraphic();
	}
	
	/**
	 * @param gameControllerData
	 */
	public void initGrafic(DesctopControllerData gameControllerData)
	{
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.MultiBufferFullScreenGraphic#drawGraphic(java.awt.Graphics, de.schmiereck.screenTools.controller.ControllerData, java.awt.Rectangle, long)
	 */
	public void drawGraphic(Graphics g, ControllerData controllerData, Rectangle bounds, long actualWaitPerFramesMillis)
	{
		controllerData.setGraphicSleepMillis(actualWaitPerFramesMillis);
		
		DesctopControllerData desctopControllerData = (DesctopControllerData)controllerData;
		
		DesctopPageData desctopPageData = desctopControllerData.getActiveDesctopPageData();
		
		this.desctopPageGraphic.drawWidgets(g, this, desctopPageData);
		
		this.drawFPSInfos(g, controllerData);

		this.desctopPageGraphic.drawPointer(g, this, desctopPageData);
	}

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
