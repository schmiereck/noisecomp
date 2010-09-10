package de.schmiereck.noiseComp.smkScreen.desctopPage.widgets;

import java.awt.Graphics;

import de.schmiereck.noiseComp.smkScreen.desctopPage.DesktopColors;
import de.schmiereck.screenTools.graphic.ScreenGraficInterface;

/**
 * Pane Graphic.
 *
 * @author smk
 * @version 08.02.2004
 */
public class PaneGraphic
{

	/**
	 * @param g
	 * @param paneData
	 */
	public static void drawPane(Graphics g, ScreenGraficInterface screenGrafic, 
								DesktopColors desktopColors,
								PaneData paneData)
	{
		int posX = paneData.getPosX();
		int posY = paneData.getPosY();
		int sizeX = paneData.getSizeX();
		int sizeY = paneData.getSizeY();
		
		g.setColor(desktopColors.getPaneBackgroundColor());
		
		screenGrafic.fillRect(g, posX, posY, sizeX, sizeY);
		screenGrafic.draw3DRect(g, posX, posY, sizeX, sizeY, true);

		g.setColor(desktopColors.getStarColor());
		
		int sx = sizeX / 2;
		int sy = sizeY / 2;
		
		for (int pos = 0; pos < PaneData.STARS_COUNT; pos++)
		{
			int x = paneData.getStarX(pos) / PaneData.SIZE + posX + sx;
			int y = paneData.getStarY(pos) / PaneData.SIZE + posY + sy;
			
			int size = paneData.getStarX(pos);
			
			if (size < 0)
			{
				size = -size;
			}
			
			//this.drawPoint(g, x, y, size / (sx / (PaneData.SIZE / 2)) + 2);
			screenGrafic.drawPoint(g, x, y, 3);
		}
		
		paneData.anim();
	}
}
