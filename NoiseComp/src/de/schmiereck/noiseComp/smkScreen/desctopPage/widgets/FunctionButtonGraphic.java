package de.schmiereck.noiseComp.smkScreen.desctopPage.widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import de.schmiereck.noiseComp.smkScreen.desctopPage.DesktopColors;
import de.schmiereck.screenTools.graphic.ScreenGraficInterface;

/**
 * Function-Button Graphic.
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
		
		int fontStyle;
		
		if (buttonData.getHaveFocus() == true)
		{
			fontStyle = Font.BOLD;
		}
		else
		{
			fontStyle = Font.PLAIN;
		}

		screenGrafic.setFont(g, "Dialog", fontStyle, sizeY - 4);
		
		int stringWidth = screenGrafic.calcStringWidth(g, labelText);
		
		screenGrafic.drawString(g, posX + offX + (sizeX / 2) - (stringWidth / 2), 
				posY + offY + (sizeY / 2) + 
				(screenGrafic.calcFontAscent(g) / 2), 
				labelText);
	}

}
