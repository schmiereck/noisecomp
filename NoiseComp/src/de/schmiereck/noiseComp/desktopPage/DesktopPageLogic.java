package de.schmiereck.noiseComp.desktopPage;

import java.util.Iterator;

import de.schmiereck.noiseComp.desktopPage.widgets.ButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.FunctionButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.GeneratorsGraphicData;
import de.schmiereck.noiseComp.desktopPage.widgets.ScrollbarData;
import de.schmiereck.noiseComp.desktopPage.widgets.TrackGraficData;
import de.schmiereck.noiseComp.desktopPage.widgets.WidgetData;
import de.schmiereck.noiseComp.generator.Generator;

/**
 * TODO docu
 *
 * @author smk
 * @version 31.01.2004
 */
public class DesktopPageLogic
{

	/**
	 * Berechnet des aktuellen zustand der Widgets anhand der angegebenen Daten.
	 * 
	 * @param desctopPageData
	 */
	public static void calcWidgets(DesktopPageData desctopPageData)
	{
		WidgetData hitWidgetData = null;
		ButtonData hitButtonData = null;
		int hitScrollbarPart = 0;
		ScrollbarData hitScrollbarData = null;
		
		int pX = desctopPageData.getPointerPosX();
		int pY = desctopPageData.getPointerPosY();
		
		Iterator widgetIterator = desctopPageData.getWidgetsIterator();
		
		while (widgetIterator.hasNext())
		{
			WidgetData widgetData = (WidgetData)widgetIterator.next();

			boolean hit;
			
			int posX = widgetData.getPosX();
			int posY = widgetData.getPosY();
			int sizeX = widgetData.getSizeX();
			int sizeY = widgetData.getSizeY();
			
			if ((pX >= posX) && (pX <= posX + sizeX) && 
				(pY >= posY) && (pY <= posY + sizeY))
			{
				hit = true;
				hitWidgetData = widgetData;
			}
			else
			{
				hit = false;
			}
			
			if (widgetData instanceof FunctionButtonData)
			{	
				if (hit == true)
				{
					FunctionButtonData buttonData = (FunctionButtonData)widgetData;

					hitButtonData = buttonData;
					break;
				}
			}
			else
			{
				if (widgetData instanceof ScrollbarData)
				{	
					if (hit == true)
					{
						ScrollbarData scrollbarData = (ScrollbarData)widgetData;
						
						hitScrollbarData = scrollbarData;

						hitScrollbarPart = DesktopPageLogic.calcHitScrollbarPart(pX, pY, posX, posY, sizeX, sizeY, scrollbarData);
						break;
					}
				}
				else
				{
					if (widgetData instanceof GeneratorsGraphicData)
					{	
						if (hit == true)
						{
							GeneratorsGraphicData generatorsGraphicData = (GeneratorsGraphicData)widgetData;
							
							//hitScrollbarData = scrollbarData;

							//hitScrollbarPart = DesktopPageLogic.calcHitScrollbarPart(pX, pY, posX, posY, sizeX, sizeY, scrollbarData);

							TrackGraficData trackGraficData = DesktopPageLogic.calcHitTrack(pX, pY, posX, posY, sizeX, sizeY, generatorsGraphicData);
							
							int hitGeneratorPart;
							
							if (trackGraficData != null)
							{
								hitGeneratorPart = DesktopPageLogic.calcHitGeneratorPart(pX, pY, posX, posY, sizeX, sizeY, generatorsGraphicData, trackGraficData);
							}
							else
							{
								hitGeneratorPart = 0;
							}
							
							generatorsGraphicData.setActiveTrackGraficData(trackGraficData, hitGeneratorPart);
							break;
						}
					}
				}
			}
		}
		
		desctopPageData.setActiveWidgetData(hitWidgetData);
		desctopPageData.setActiveButtonData(hitButtonData);
		desctopPageData.setActiveScrollbarData(hitScrollbarData, hitScrollbarPart);
	}
	

	/**
	 * @param px
	 * @param py
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 * @param generatorsGraphicData
	 * @param trackGraficData
	 * @return 	Der mit der Maus überfahrene Part des Generators:<br/>
	 * 			{@link GeneratorsGraphicData#HIT_PART_NONE}:		kein Hit.<br/>
	 * 			{@link GeneratorsGraphicData#HIT_PART_GENERATOR}:	Generator überfahren.
	 */
	private static int calcHitGeneratorPart(int px, int py, int posX, int posY, int sizeX, int sizeY, GeneratorsGraphicData generatorsGraphicData, TrackGraficData trackGraficData)
	{
		int hitGeneratorPart = GeneratorsGraphicData.HIT_PART_NONE;
		int x = (px - posX);

		// Inerhalb des Trackbereichs ?
		if ((x >= generatorsGraphicData.getGeneratorsLabelSizeX()) && (x <= sizeX))
		{
			float timePos = ((x - generatorsGraphicData.getGeneratorsLabelSizeX()) / generatorsGraphicData.getGeneratorScaleX()) - generatorsGraphicData.getTrackScrollPos();
			
			Generator generator = trackGraficData.getGenerator();
			
			if ((timePos >= generator.getStartTimePos()) && (timePos <= generator.getEndTimePos()))
			{
				hitGeneratorPart = GeneratorsGraphicData.HIT_PART_GENERATOR;
			}
		}
		return hitGeneratorPart;
	}

	/**
	 * @param px
	 * @param py
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 * @param generatorsGraphicData
	 * @return
	 */
	private static TrackGraficData calcHitTrack(int px, int py, int posX, int posY, int sizeX, int sizeY, GeneratorsGraphicData generatorsGraphicData)
	{
		TrackGraficData trackGraficData = null;
		
		// Die Positionsnummer des Generators in der Liste.
		int generatorPos = ((py - posY) / generatorsGraphicData.getTrackHeight()) + generatorsGraphicData.getTrackScrollPos();
		
		// Innerhalb der Anzahl der vorhandneen generatoren ?
		if (generatorPos < generatorsGraphicData.getTracksCount())
		{
			trackGraficData = generatorsGraphicData.getTrack(generatorPos);
		}
		
		return trackGraficData;
	}
	
	private static int calcHitScrollbarPart(int pX, int pY, int posX, int posY, int sizeX, int sizeY, ScrollbarData scrollbarData)
	{
		int hitScrollbarPart = 0;
		if (scrollbarData.getDoScrollVertical() == true)
		{	
			int width = sizeX;
			int y = pY - posY;
			
			if (y <= width)
			{
				// 1:	Up-Scroller
				hitScrollbarPart = 1;
			}
			else
			{	
				if (y >= (sizeY - width))
				{
					// 3:	Down-Scroller
					hitScrollbarPart = 3;
				}
				else
				{	
					int scrollerPos = scrollbarData.getScreenScrollerPos();
					int scrollerSize = scrollbarData.getScreenScrollerSize();
					
					if ((y >= (scrollerPos)) &&
							(y <= (scrollerPos + scrollerSize)))
					{
						// 2:	Scroller
						hitScrollbarPart = 2;
					}
				}
			}
		}
		else
		{
			int width = sizeY;
			int x = pX - posX;
			
			if (x <= width)
			{
				// 1:	Up-Scroller
				hitScrollbarPart = 1;
			}
			else
			{	
				if (x >= (sizeX - width))
				{
					// 3:	Down-Scroller
					hitScrollbarPart = 3;
				}
				else
				{	
					int scrollerPos = scrollbarData.getScreenScrollerPos();
					int scrollerSize = scrollbarData.getScreenScrollerSize();
					
					if ((x >= (scrollerPos)) &&
							(x <= (scrollerPos + scrollerSize)))
					{
						// 2:	Scroller
						hitScrollbarPart = 2;
					}
				}
			}
		}
		return hitScrollbarPart;
	}

	public static void pointerPressed(DesktopPageData desctopPageData)
	{
		desctopPageData.setPointerPressed(true);

		WidgetData widgetData = desctopPageData.getActiveWidgetData();
		
		if (widgetData != null)
		{
			if (widgetData instanceof ClickedWidgetListenerInterface)
			{
				((ClickedWidgetListenerInterface)widgetData).notifyClickedWidget(widgetData);
			}
			
			if (widgetData instanceof FocusedWidgetListenerInterface)
			{
				WidgetData focusedWidgetData = desctopPageData.getFocusedWidgetData();
				
				if (focusedWidgetData != widgetData)
				{	
					if (focusedWidgetData != null)
					{	
						((FocusedWidgetListenerInterface)focusedWidgetData).notifyDefocusedWidget(focusedWidgetData);
					}
					desctopPageData.setFocusedWidgetData(widgetData);
					((FocusedWidgetListenerInterface)widgetData).notifyFocusedWidget(widgetData);
				}
			}
		}
		else
		{
			WidgetData focusedWidgetData = desctopPageData.getFocusedWidgetData();
			
			((FocusedWidgetListenerInterface)focusedWidgetData).notifyDefocusedWidget(focusedWidgetData);
			desctopPageData.setFocusedWidgetData(null);
		}
		
		ScrollbarData activeScrollbarData = desctopPageData.getActiveScrollbarData();

		if (activeScrollbarData != null)
		{
			//if ("generatorsVScroll".equals(activeScrollbarData.getName()) == true)
			{
				int activeScrollbarPart = desctopPageData.getActiveScrollbarPart();
				
				if (activeScrollbarPart == 1)
				{				
					float pos = activeScrollbarData.getScrollerPos();
					
					if (pos > 0)
					{
						pos -= activeScrollbarData.getScrollStep();
						activeScrollbarData.setScrollerPos(pos);
					}
				}
				else
				{
					if (activeScrollbarPart == 3)
					{				
						float pos = activeScrollbarData.getScrollerPos();
						
						if (pos < (activeScrollbarData.getScrollerLength() - activeScrollbarData.getScrollerSize()))
						{
							pos += activeScrollbarData.getScrollStep();
							activeScrollbarData.setScrollerPos(pos);
						}
					}
					else
					{
						if (activeScrollbarPart == 2)
						{				
						}
					}
				}
			}
		}		
	}

	public static void pointerReleased(DesktopPageData desctopPageData, ButtonPressedCallbackInterface buttonPressedCallback)
	{
		ButtonData pressedButtonData = desctopPageData.getPressedButtonData();
		
		desctopPageData.setPointerPressed(false);
		
		if (pressedButtonData != null)
		{
			// Mouse is still over the Button ?
			if (pressedButtonData == desctopPageData.getActiveWidgetData())
			{	
				buttonPressedCallback.buttonPressed(pressedButtonData);
			}
		}
		
	}
}
