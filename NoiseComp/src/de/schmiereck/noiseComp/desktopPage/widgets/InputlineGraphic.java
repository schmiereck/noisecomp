package de.schmiereck.noiseComp.desktopPage.widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import de.schmiereck.noiseComp.desktopPage.DesktopColors;
import de.schmiereck.screenTools.graphic.ScreenGraficInterface;

/**
 * TODO docu
 *
 * @author smk
 * @version 08.02.2004
 */
public class InputlineGraphic
{

	/**
	 * @param g
	 * @param screenGrafic
	 * @param inputlineData
	 * @param active
	 * @param focused
	 */
	public static void drawInputline(Graphics g, ScreenGraficInterface screenGrafic, 
									 DesktopColors desktopColors,
									 InputlineData inputlineData, boolean active, boolean focused)
	{
		String inputText = inputlineData.getInputText();
		int posX = inputlineData.getPosX();
		int posY = inputlineData.getPosY();
		int sizeX = inputlineData.getSizeX();
		int sizeY = inputlineData.getSizeY();
		
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
		
		int stringWidth;
		
		if (inputText != null)
		{
			stringWidth = screenGrafic.calcStringWidth(g, inputText);
			
			screenGrafic.drawString(g, posX, 
					posY + (sizeY / 2) + 
					(screenGrafic.calcFontAscent(g) / 2), 
					inputText);
		}

		int inputSizeX;
		
		if (inputText != null)
		{	
			inputSizeX = screenGrafic.calcStringWidth(g, inputText.substring(0, inputlineData.getInputPos()));
		}
		else
		{
			inputSizeX = 0;
		}
		
		screenGrafic.drawLine(g, posX + inputSizeX, posY, 0, sizeY);
		screenGrafic.drawLine(g, posX + inputSizeX, posY, 3, 0);
		screenGrafic.drawLine(g, posX + inputSizeX, posY + sizeY, 3, 0);
	}

}
