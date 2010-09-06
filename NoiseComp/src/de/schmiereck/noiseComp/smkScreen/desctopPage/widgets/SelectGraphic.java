package de.schmiereck.noiseComp.smkScreen.desctopPage.widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import de.schmiereck.noiseComp.smkScreen.desctopPage.DesktopColors;
import de.schmiereck.screenTools.graphic.ScreenGraficInterface;

/**
 * TODO docu
 *
 * @author smk
 * @version 21.02.2004
 */
public class SelectGraphic
{
	/**
	 * @param g
	 * @param screenGrafic
	 * @param inputlineData
	 * @param active
	 * @param focused
	 */
	public static void drawWidget(Graphics g, ScreenGraficInterface screenGrafic, 
									 DesktopColors desktopColors,
					   SelectData selectData, boolean active, boolean focused)
	{
		int inputPos = selectData.getInputPos();
		int posX = selectData.getPosX();
		int posY = selectData.getPosY();
		int sizeX = selectData.getSizeX();
		int sizeY = selectData.getSizeY();
		
		if (focused == true)
		{	
			g.setColor(desktopColors.getFocusedInputlineColor());
		}
		else
		{
			if (active == true)
			{	
				g.setColor(desktopColors.getActiveInputlineColor());
			}
			else
			{
				g.setColor(desktopColors.getInactiveInputlineColor());
			}
		}
		
		screenGrafic.fillRect(g, posX, posY, sizeX, sizeY);
		
		screenGrafic.draw3DRect(g, posX, posY, sizeX, sizeY, false);
		
		g.setColor(Color.BLACK);
		
		screenGrafic.setFont(g, "Dialog", Font.PLAIN, sizeY - 4);
		
		int size = selectData.getSelectEntrysCount();
		
		String labelText;
		
		if (inputPos > -1)
		{	
			SelectEntryData selectEntryData = selectData.getSelectEntryData(inputPos);
			
			if (selectEntryData != null)
			{
				labelText = (inputPos + 1) + "/" + size + ":" + selectEntryData.getLabelText();
			}
			else
			{
				labelText = "";
			}
		}
		else
		{
			labelText = "0/" + size + ": --";
		}
		
		screenGrafic.drawString(g, posX, 
				posY + (sizeY / 2) + 
				(screenGrafic.calcFontAscent(g) / 2), 
				labelText);
		
		int s = sizeY;
		int hs = s / 2;
		
		screenGrafic.drawLine(g, (posX + sizeX) - s, 	posY + hs,	hs, -hs);
		screenGrafic.drawLine(g, (posX + sizeX) - hs,	posY + 0,	hs, hs);

		screenGrafic.drawLine(g, (posX + sizeX) - s, 	posY + hs,	s, 0);
		
		screenGrafic.drawLine(g, (posX + sizeX) - s, 	posY + hs,	hs, hs);
		screenGrafic.drawLine(g, (posX + sizeX) - hs,	posY + s,	hs, -hs);
	}
}
