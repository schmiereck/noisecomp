package de.schmiereck.noiseComp.view.desctopPage.widgets;

import java.awt.Color;
import java.awt.Graphics;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.view.desctopPage.DesktopColors;
import de.schmiereck.screenTools.graphic.ScreenGraficInterface;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>25.02.2004: created, smk</p>
 */
public class GeneratorTypesWidgetGraphic
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

		GeneratorTypesWidgetData generatorTypesWidgetData = (GeneratorTypesWidgetData)listWidgetData;
		GeneratorTypeData generatorTypeData = (GeneratorTypeData)entry;

		if (generatorTypesWidgetData.getSelectedGeneratorTypeData() == generatorTypeData)
		{
			screenGrafic.setColor(g, desktopColors.getGeneratorBottomLineColor());
			screenGrafic.fillRect(g, posX, screenPosY, sizeX, entryHeight);
		}
		if (generatorTypesWidgetData.getActiveGeneratorTypeData() == generatorTypeData)
		{
			screenGrafic.setColor(g, desktopColors.getActiveGeneratorBackgroundColor());
			screenGrafic.drawRect(g, posX, screenPosY, sizeX, entryHeight);
		}
		
		// Bottom-Line.
		screenGrafic.setColor(g, desktopColors.getGeneratorBottomLineColor());
		screenGrafic.drawLine(g, posX, screenPosY + entryHeight, sizeX, 0);
		
		if (generatorTypesWidgetData.getSelectedGeneratorTypeData() == generatorTypeData)
		{
			screenGrafic.setColor(g, Color.WHITE);
		}
		else
		{
			screenGrafic.setColor(g, Color.BLACK);
		}

		/*
		GeneratorTypeData inputGeneratorTypeData = generatorTypesWidgetData.getGeneratorTypeData(generatorTypeData.getInputGenerator());
		
		Generator inputGenerator = generatorTypeData.getInputGenerator();
		
		if (generatorTypeData.getInputValue() != null)
		{
			label = String.valueOf(generatorTypeData.getInputValue());
		}
		else
		{
			if (inputGenerator != null)
			{	
				label = inputGenerator.getName();
			}
			else
			{
				label = "--";
			}
			
			if (inputGeneratorTypeData != null)
			{
				label += " [" + inputGeneratorTypeData.getGeneratorTypeName() + "]";
			}
		}

		GeneratorTypeData generatorTypeData = generatorTypesWidgetData.getGeneratorTypeData(generatorTypesWidgetData.getSelectedGenerator());
		
		InputTypeData inputTypeData = generatorTypeData.getInputTypeData(generatorTypeData.getInputType());
		
		String inputTypeName;
		
		if (inputTypeData != null)
		{	
			inputTypeName = inputTypeData.getInputTypeName();
		}
		else
		{
			inputTypeName = "ERROR";
		}
		*/

		String label;

		label = generatorTypeData.getGeneratorTypeName();

		String description;

		description = generatorTypeData.getGeneratorTypeDescription();
		
		screenGrafic.drawString(g, posX + this.textOffsetSizeX, screenPosY + entryHeight, label);
		screenGrafic.drawString(g, posX + this.nameColumnSizeX + this.textOffsetSizeX, screenPosY + entryHeight, description);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetGraphic#drawSelectors(java.awt.Graphics, de.schmiereck.screenTools.graphic.ScreenGraficInterface, de.schmiereck.noiseComp.desktopPage.DesktopColors, de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetData, int, int, int, int, int, float, float, float, float)
	 */
	public void drawSelectors(Graphics g, ScreenGraficInterface screenGrafic, DesktopColors desktopColors, ListWidgetData listWidgetData, int posX, int posY, int sizeX, int sizeY, int entryHeight, float horizontalScrollStart, float horizontalScrollEnd, float verticalScrollerStart, float verticalScrollerEnd)
	{
		// TODO Auto-generated method stub
		
	}
}
