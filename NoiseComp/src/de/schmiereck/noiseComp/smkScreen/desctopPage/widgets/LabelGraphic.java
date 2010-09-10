package de.schmiereck.noiseComp.smkScreen.desctopPage.widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import de.schmiereck.noiseComp.smkScreen.desctopPage.DesktopColors;
import de.schmiereck.screenTools.graphic.ScreenGraficInterface;

/**
 * Label Graphic.
 *
 * @author smk
 * @version 08.02.2004
 */
public class LabelGraphic
{

	/**
	 * @param g
	 * @param screenGrafic
	 * @param labelData
	 */
	public static void drawLabel(Graphics g, ScreenGraficInterface screenGrafic, 
								 DesktopColors desktopColors,
								 LabelData labelData)
	{
		String labelText = labelData.getLabelText();
		int posX = labelData.getPosX();
		int posY = labelData.getPosY();
		int sizeX = labelData.getSizeX();
		int sizeY = labelData.getSizeY();
		
		g.setColor(Color.BLACK);
		
		screenGrafic.setFont(g, "Dialog", Font.PLAIN, sizeY - 4);
		
		int stringWidth;
		
		if (labelText != null)
		{
			stringWidth = screenGrafic.calcStringWidth(g, labelText);
			//stringWidth = fontMetrics.stringWidth(labelText);

			screenGrafic.drawString(g, posX + (sizeX - stringWidth), 
					posY + (sizeY / 2) + (screenGrafic.calcFontAscent(g) / 2) + (screenGrafic.calcFontDescent(g) / 2), labelText);
		}
	}

}
