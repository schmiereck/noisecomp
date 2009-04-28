package de.schmiereck.noiseComp.view.desctopPage.widgets;

import java.awt.Graphics;
import java.util.Iterator;
import de.schmiereck.noiseComp.view.desctopPage.DesktopColors;
import de.schmiereck.screenTools.graphic.ScreenGraficInterface;

/**
 * TODO docu
 *
 * @author smk
 * @version 08.02.2004
 */
public abstract class ListWidgetGraphic
{

	/**
	 * Draw the List.
	 */
	public void drawList(Graphics g, ScreenGraficInterface screenGrafic, 
						 DesktopColors desktopColors,
						 ListWidgetData listWidgetData)
	{
		int posX = listWidgetData.getPosX();
		int posY = listWidgetData.getPosY();
		int sizeX = listWidgetData.getSizeX();
		int sizeY = listWidgetData.getSizeY();

		int entryHeight = listWidgetData.getListEntryHeight();
		
		g.setColor(desktopColors.getGeneratorsBackgroundColor());
		
		screenGrafic.fillRect(g, posX, posY, sizeX, sizeY);

		float horizontalScrollStart;
		float horizontalScrollEnd;
		
		ScrollbarData horizontalScrollbarData = listWidgetData.getHorizontalScrollbarData();
		
		if (horizontalScrollbarData != null)
		{	
			horizontalScrollStart = horizontalScrollbarData.getScrollerPos();
			horizontalScrollEnd = horizontalScrollStart + horizontalScrollbarData.getScrollerSize();
		}
		else
		{	
			horizontalScrollStart = 0.0F;
			horizontalScrollEnd = 0.0F;
		}

		this.drawBackground(g, screenGrafic, desktopColors, listWidgetData, 
		                    posX, posY, sizeX, sizeY, 
		                    horizontalScrollStart, horizontalScrollEnd);
		
		ScrollbarData verticalScrollbarData = listWidgetData.getVerticalScrollbarData();

		float verticalScrollerStart;
		float verticalScrollerEnd;
		
		if (verticalScrollbarData != null)
		{	
			//System.out.println("getVerticalScrollerPos: " + listWidgetData.getVerticalScrollerPos());
			verticalScrollerStart = (verticalScrollbarData.getScrollerPos() - 1F); //(listWidgetData.getListEntryHeight()));
			verticalScrollerEnd = (verticalScrollerStart + verticalScrollbarData.getScrollerSize());
		}
		else
		{	
			verticalScrollerStart = 0.0F;
			verticalScrollerEnd = 0.0F;
		}

		Iterator entrysIterator = listWidgetData.getListEntrysIterator();
		
		if (entrysIterator != null)
		{	
			synchronized (listWidgetData)
			{
				int entryPos = 0;
				int entryPosY = 0;
				
				int maxEntryPosY = (Math.round(verticalScrollerEnd) * entryHeight);
				
				while (entrysIterator.hasNext())
				{
					Object entry = entrysIterator.next();
					//InputData inputData = (InputData)entrysIterator.next();
					
					if (entryPos >= verticalScrollerStart)
					{	
						int screenPosY = entryPosY + posY;
	
						this.drawListEntry(g, screenGrafic, desktopColors,
								listWidgetData,
								entry, entryPos, screenPosY, entryHeight,
								horizontalScrollStart, horizontalScrollEnd,
								verticalScrollerStart, verticalScrollerEnd);
						
						entryPosY += entryHeight;
					}
					
					entryPos++;
					//if (entryPos >= verticalScrollerEnd)
					if (entryPosY >= maxEntryPosY)
					{
						break;
					}
				}
			}
		}
		
		this.drawSelectors(g, screenGrafic, desktopColors, 
		                   listWidgetData, 
		                   posX, posY, sizeX, sizeY, entryHeight, 
		                   horizontalScrollStart, horizontalScrollEnd,
		                   verticalScrollerStart, verticalScrollerEnd);
	}


	/**
	 * @param g
	 * @param screenGrafic
	 * @param desktopColors
	 * @param listWidgetData
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 * @param horizontalScrollStart
	 * @param horizontalScrollEnd
	 */
	public abstract void drawBackground(Graphics g, ScreenGraficInterface screenGrafic, DesktopColors desktopColors, ListWidgetData listWidgetData, int posX, int posY, int sizeX, int sizeY, float horizontalScrollStart, float horizontalScrollEnd);

	/**
	 * @param g
	 * @param screenGrafic
	 * @param desktopColors
	 * @param listWidgetData
	 * @param entry
	 * @param entryPos
	 * @param screenPosY
	 */
	public abstract void drawListEntry(Graphics g, ScreenGraficInterface screenGrafic, 
										DesktopColors desktopColors, 
										ListWidgetData listWidgetData, 
										Object entry, int entryPos, int screenPosY, int entryHeight,
										float horizontalScrollStart, float horizontalScrollEnd,
										float verticalScrollerStart, float verticalScrollerEnd);

	/**
	 * @param g
	 * @param screenGrafic
	 * @param desktopColors
	 * @param listWidgetData
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 * @param entryHeight
	 * @param horizontalScrollStart
	 * @param horizontalScrollEnd
	 * @param verticalScrollerStart
	 * @param verticalScrollerEnd
	 */
	public abstract void drawSelectors(Graphics g, ScreenGraficInterface screenGrafic, DesktopColors desktopColors, 
										ListWidgetData listWidgetData, 
										int posX, int posY, int sizeX, int sizeY, int entryHeight, 
										float horizontalScrollStart, float horizontalScrollEnd, 
										float verticalScrollerStart, float verticalScrollerEnd);
}
