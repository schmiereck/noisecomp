package de.schmiereck.noiseComp.smkScreen.desctopPage.widgets;

import java.awt.Color;
import java.awt.Graphics;

import de.schmiereck.noiseComp.smkScreen.desctopPage.DesktopColors;
import de.schmiereck.screenTools.graphic.ScreenGraficInterface;

/**
 * Scrollbar Graphic.
 *
 * @author smk
 * @version 08.02.2004
 */
public class ScrollbarGraphic
{

	/**
	 * @param g
	 * @param scrollbarData
	 */
	public static void drawScrollbar(Graphics g, ScreenGraficInterface screenGrafic, 
							   DesktopColors desktopColors,
							   ScrollbarData scrollbarData, boolean active, int activeScrollbarPart)
	{
		int posX = scrollbarData.getPosX();
		int posY = scrollbarData.getPosY();
		int sizeX = scrollbarData.getSizeX();
		int sizeY = scrollbarData.getSizeY();

		int width;
		
		if (scrollbarData.getDoScrollVertical() == true)
		{
			width = sizeX;
		}
		else
		{
			width = sizeY;
		}

		// Background:
		g.setColor(Color.WHITE);
		screenGrafic.fillRect(g, posX, posY, sizeX, sizeY);

		g.setColor(Color.GRAY);
		screenGrafic.draw3DRect(g, posX, posY, sizeX, sizeY, false);

		// Scroll-Up-Arrow:
		if ((active == true) && (activeScrollbarPart == 1))
		{
			g.setColor(desktopColors.getActiveButtonColor());
		}
		else
		{
			g.setColor(Color.LIGHT_GRAY);
		}
		screenGrafic.fillRect(g, posX, posY, width, width);
		g.setColor(Color.GRAY);
		screenGrafic.draw3DRect(g, posX, posY, width, width, true);
		g.setColor(Color.BLACK);
		if (scrollbarData.getDoScrollVertical() == true)
		{
			screenGrafic.drawLine(g, posX, posY + width, width / 2, -width);
			screenGrafic.drawLine(g, posX + width / 2, posY, width / 2, width);
		}
		else
		{
			screenGrafic.drawLine(g, posX + width, posY, -width, width / 2);
			screenGrafic.drawLine(g, posX, posY + width / 2, width, width / 2);
		}
		
		// Scroll-Down-Arrow:
		if ((active == true) && (activeScrollbarPart == 3))
		{
			g.setColor(desktopColors.getActiveButtonColor());
		}
		else
		{
			g.setColor(Color.LIGHT_GRAY);
		}
		if (scrollbarData.getDoScrollVertical() == true)
		{
			screenGrafic.fillRect(g, posX, posY + (sizeY - width), width, width);
		}
		else
		{
			screenGrafic.fillRect(g, posX + (sizeX - width), posY, width, width);
		}
		g.setColor(Color.GRAY);
		if (scrollbarData.getDoScrollVertical() == true)
		{
			screenGrafic.draw3DRect(g, posX, posY + (sizeY - width), width, width, true);
		}
		else
		{
			screenGrafic.draw3DRect(g, posX + (sizeX - width), posY, width, width, true);
		}
		g.setColor(Color.BLACK);
		if (scrollbarData.getDoScrollVertical() == true)
		{
			screenGrafic.drawLine(g, posX, posY + (sizeY - width), width / 2, width);
			screenGrafic.drawLine(g, posX + width / 2, posY + (sizeY), width / 2, -width);
		}
		else
		{
			screenGrafic.drawLine(g, posX + (sizeX - width), posY, width, width / 2);
			screenGrafic.drawLine(g, posX + (sizeX), posY + width / 2, -width, width / 2);
		}

		// Scroller:
		int scrollerSize = scrollbarData.getScreenScrollerSize();
		int scrollerPos = scrollbarData.getScreenScrollerPos();
		
		if ((active == true) && (activeScrollbarPart == 2))
		{
			g.setColor(desktopColors.getActiveButtonColor());
		}
		else
		{
			g.setColor(Color.LIGHT_GRAY);
		}
		if (scrollbarData.getDoScrollVertical() == true)
		{
			screenGrafic.fillRect(g, posX, posY + scrollerPos, width, scrollerSize);
			g.setColor(Color.GRAY);
			screenGrafic.draw3DRect(g, posX, posY + scrollerPos, width, scrollerSize, true);
		}
		else
		{
			screenGrafic.fillRect(g, posX + scrollerPos, posY, scrollerSize, width);
			g.setColor(Color.GRAY);
			//screenGrafic.draw3DRect(g, posX + scrollerPos, posY, scrollerSize, width, true);
		}
	}

}
