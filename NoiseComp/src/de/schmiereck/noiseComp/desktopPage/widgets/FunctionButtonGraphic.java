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
public class FunctionButtonGraphic
{

	/**
	 * @param g
	 * @param buttonData
	 */
	public static void drawFunctionButton(Graphics g, ScreenGraficInterface screenGrafic, 
										  DesktopColors desktopColors,
										  FunctionButtonData buttonData, boolean active, boolean pressed)
	{
		String labelText = buttonData.getLabelText();
		int posX = buttonData.getPosX();
		int posY = buttonData.getPosY();
		int sizeX = buttonData.getSizeX();
		int sizeY = buttonData.getSizeY();
		
		int offX = 0;
		int offY = 0;
		
		if (active == true)
		{	
			g.setColor(desktopColors.getActiveButtonColor());
			
			screenGrafic.fillRect(g, posX, posY, sizeX, sizeY);

			if (pressed == true)
			{	
				screenGrafic.draw3DRect(g, posX, posY, sizeX, sizeY, false);

				offX = 1;
				offY = 1;
			}
			else
			{	
				screenGrafic.draw3DRect(g, posX, posY, sizeX, sizeY, true);
			}
		}
		else
		{
			g.setColor(desktopColors.getInactiveButtonColor());
			
			screenGrafic.fillRect(g, posX, posY, sizeX, sizeY);

			g.setColor(desktopColors.getInactiveButtonBorderColor());
			
			screenGrafic.drawRect(g, posX, posY, sizeX, sizeY);
		}

		g.setColor(Color.BLACK);
		
		screenGrafic.setFont(g, "Dialog", Font.PLAIN, sizeY - 4);
		
		int stringWidth = screenGrafic.calcStringWidth(g, labelText);
		
		screenGrafic.drawString(g, posX + offX + (sizeX / 2) - (stringWidth / 2), 
				posY + offY + (sizeY / 2) + 
				(screenGrafic.calcFontAscent(g) / 2), 
				labelText);
	}

}
