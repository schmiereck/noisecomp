package de.schmiereck.noiseComp.desktopPage.widgets;

import java.awt.Color;
import java.awt.Graphics;

import de.schmiereck.noiseComp.desktopPage.DesktopColors;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.screenTools.graphic.ScreenGraficInterface;

/**
 * TODO docu
 *
 * @author smk
 * @version 08.02.2004
 */
public class InputsWidgetGraphic
extends ListWidgetGraphic
{

	private int nameColumnSizeX = 150;
	private int textOffsetSizeX = 4;
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetGraphic#drawBackground(java.awt.Graphics, de.schmiereck.screenTools.graphic.ScreenGraficInterface, de.schmiereck.noiseComp.desktopPage.DesktopColors, de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetData, int, int, int, int, float, float)
	 */
	public void drawBackground(Graphics g, ScreenGraficInterface screenGrafic, DesktopColors desktopColors, 
							   ListWidgetData listWidgetData, int posX, int posY, int sizeX, int sizeY, float horizontalScrollStart, float horizontalScrollEnd)
	{
		screenGrafic.setColor(g, desktopColors.getGeneratorsBackgroundColor());
		screenGrafic.fillRect(g, posX, posY, sizeX, sizeY);
		
		screenGrafic.setColor(g, desktopColors.getGeneratorBottomLineColor());
		screenGrafic.drawLine(g, posX + this.nameColumnSizeX, posY, 0, sizeY);
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetGraphic#drawListEntry(java.awt.Graphics, de.schmiereck.screenTools.graphic.ScreenGraficInterface, de.schmiereck.noiseComp.desktopPage.DesktopColors, de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetData, java.lang.Object, int, int, int, float, float, float, float)
	 */
	public void drawListEntry(Graphics g, ScreenGraficInterface screenGrafic, 
									  DesktopColors desktopColors, 
									  ListWidgetData listWidgetData, 
									  Object entry, int entryPos, int screenPosY, int entryHeight,
									  float horizontalScrollStart, float horizontalScrollEnd,
									  float verticalScrollerStart, float verticalScrollerEnd)
	{
		int posX = listWidgetData.getPosX();
		int posY = listWidgetData.getPosY();
		int sizeX = listWidgetData.getSizeX();
		int sizeY = listWidgetData.getSizeY();

		InputsWidgetData inputsData = (InputsWidgetData)listWidgetData;
		InputData inputData = (InputData)entry;

		if (inputsData.getSelectedInputData() == inputData)
		{
			screenGrafic.setColor(g, desktopColors.getGeneratorBottomLineColor());
			screenGrafic.fillRect(g, posX, screenPosY, sizeX, entryHeight);
		}
		if (inputsData.getActiveInputData() == inputData)
		{
			screenGrafic.setColor(g, desktopColors.getActiveGeneratorBackgroundColor());
			screenGrafic.drawRect(g, posX, screenPosY, sizeX, entryHeight);
		}
		
		// Bottom-Line.
		screenGrafic.setColor(g, desktopColors.getGeneratorBottomLineColor());
		screenGrafic.drawLine(g, posX, screenPosY + entryHeight, sizeX, 0);
		
		if (inputsData.getSelectedInputData() == inputData)
		{
			screenGrafic.setColor(g, Color.WHITE);
		}
		else
		{
			screenGrafic.setColor(g, Color.BLACK);
		}

		Generator inputGenerator = inputData.getInputGenerator();
		
		String label;
		
		if (inputData.getInputValue() != null)
		{
			label = String.valueOf(inputData.getInputValue());
		}
		else
		{
			if (inputGenerator != null)
			{	
				label = inputGenerator.getName();

				GeneratorTypeData inputGeneratorTypeData = inputGenerator.getGeneratorTypeData();
				
				if (inputGeneratorTypeData != null)
				{
					label += " [" + inputGeneratorTypeData.getGeneratorTypeName() + "]";
				}
			}
			else
			{
				label = "--";
			}
		}

		//Generator selectedGenerator = inputsData.getSelectedGenerator();
		
		//GeneratorTypeData selectedGeneratorTypeData = selectedGenerator.getGeneratorTypeData();
		
		InputTypeData inputTypeData = inputData.getInputTypeData();
		
		String inputTypeName;
		
		if (inputTypeData != null)
		{	
			inputTypeName = inputTypeData.getInputTypeName();
		}
		else
		{
			inputTypeName = "ERROR";
		}
		
		screenGrafic.drawString(g, posX + this.textOffsetSizeX, screenPosY + entryHeight, label);
		screenGrafic.drawString(g, posX + this.nameColumnSizeX + this.textOffsetSizeX, screenPosY + entryHeight, inputTypeName);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetGraphic#drawSelectors(java.awt.Graphics, de.schmiereck.screenTools.graphic.ScreenGraficInterface, de.schmiereck.noiseComp.desktopPage.DesktopColors, de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetData, int, int, int, int, int, float, float, float, float)
	 */
	public void drawSelectors(Graphics g, ScreenGraficInterface screenGrafic, DesktopColors desktopColors, ListWidgetData listWidgetData, int posX, int posY, int sizeX, int sizeY, int entryHeight, float horizontalScrollStart, float horizontalScrollEnd, float verticalScrollerStart, float verticalScrollerEnd)
	{
		// TODO Auto-generated method stub
		
	}
}