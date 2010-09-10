package de.schmiereck.noiseComp.smkScreen.desctopPage.widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import de.schmiereck.noiseComp.smkScreen.desctopPage.DesktopColors;
import de.schmiereck.screenTools.graphic.ScreenGraficInterface;

/**
 * Text-Widget Graphic.
 *
 * @author smk
 * @version <p>06.03.2004: created, smk</p>
 */
public class TextWidgetGraphic
{
	public static void drawLabel(Graphics g, ScreenGraficInterface screenGrafic, 
			DesktopColors desktopColors,
			TextWidgetData textWidgetData)
	{
		String labelText = textWidgetData.getLabelText();
		int posX = textWidgetData.getPosX();
		int posY = textWidgetData.getPosY();
		int sizeX = textWidgetData.getSizeX();
		int sizeY = textWidgetData.getSizeY();
		
		g.setColor(Color.BLACK);
		
		screenGrafic.setFont(g, "Dialog", Font.PLAIN, sizeY - 4);
		
		int stringWidth;
		
		if (labelText != null)
		{
			//stringWidth = screenGrafic.calcStringWidth(g, labelText);

			screenGrafic.drawString(g, posX, 
					posY + (sizeY / 2) + (screenGrafic.calcFontAscent(g) / 2) + (screenGrafic.calcFontDescent(g) / 2), labelText);
		}
	}
}
