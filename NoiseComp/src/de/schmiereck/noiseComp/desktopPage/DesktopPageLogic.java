package de.schmiereck.noiseComp.desktopPage;

import java.util.Iterator;

import de.schmiereck.noiseComp.desktopPage.widgets.FunctionButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.ScrollbarData;
import de.schmiereck.noiseComp.desktopPage.widgets.WidgetData;

/**
 * Provides Functions to manage the functions of a normal Page with Widgets.
 * All this Functions are static, the state of a page are manged in 
 * {@link de.schmiereck.noiseComp.desktopPage.DesktopPageData}-Objects.
 *
 * @author smk
 * @version 31.01.2004
 */
public class DesktopPageLogic
{

	/**
	 * <p>
	 * 	Calculates the actual State of the Widgets by the actual DesktopPageData State.
	 * </p>
	 * <p>
	 * 	Mainly the active state (Mouse Rollover) is calculated an the 
	 * 	correlating functions of the Listener-Interfaces are called.
	 * </p>
	 * 
	 * @param desktopPageData
	 */
	public static void calcWidgets(DesktopPageData desktopPageData)
	{
		WidgetData hitWidgetData = null;
		InputWidgetData hitButtonData = null;
		//int hitScrollbarPart = 0;
		ScrollbarData hitScrollbarData = null;
		
		int pointerPosX = desktopPageData.getPointerPosX();
		int pointerPosY = desktopPageData.getPointerPosY();
		
		Iterator widgetIterator = desktopPageData.getWidgetsIterator();
		
		while (widgetIterator.hasNext())
		{
			WidgetData widgetData = (WidgetData)widgetIterator.next();

			boolean hit;
			
			int posX = widgetData.getPosX();
			int posY = widgetData.getPosY();
			int sizeX = widgetData.getSizeX();
			int sizeY = widgetData.getSizeY();
			
			if ((pointerPosX >= posX) && (pointerPosX <= posX + sizeX) && 
				(pointerPosY >= posY) && (pointerPosY <= posY + sizeY))
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

						//hitScrollbarPart = DesktopPageLogic.calcHitScrollbarPart(pointerPosX, pointerPosY, posX, posY, sizeX, sizeY, scrollbarData);
						break;
					}
				}
			}
		}
		
		WidgetData selectedWidgetData = desktopPageData.getSelectedWidgetData();
		
		if (selectedWidgetData != null)
		{
			if (selectedWidgetData instanceof ClickedWidgetListenerInterface)
			{
				((ClickedWidgetListenerInterface)selectedWidgetData).notifyDragWidget(selectedWidgetData, pointerPosX, pointerPosY);
			}
		}
		
		desktopPageData.setActiveWidgetData(hitWidgetData, pointerPosX, pointerPosY);
		desktopPageData.setActiveButtonData(hitButtonData);
		desktopPageData.setActiveScrollbarData(hitScrollbarData);
	}
	
	/**
	 * <p>
	 * 	This Function is called, if the Mouse Pointer Button is pressed down.
	 * 	The Function checks, if there is a active (with Mouse Rollover) Widget.
	 * 	If this Widget implements the {@link ClickedWidgetListenerInterface} or
	 * 	the {@link FocusedWidgetListenerInterface}, the correlating functions are called.
	 * </p>
	 *  
	 * @param desktopPageData
	 */
	public static void pointerPressed(DesktopPageData desktopPageData)
	{
		desktopPageData.setPointerPressed(true);

		WidgetData widgetData = desktopPageData.getActiveWidgetData();
		
		if (widgetData != null)
		{
			desktopPageData.setSelectedWidgetData(widgetData);
			
			if (widgetData instanceof ClickedWidgetListenerInterface)
			{
				int pointerPosX = desktopPageData.getPointerPosX();
				int pointerPosY = desktopPageData.getPointerPosY();
				
				((ClickedWidgetListenerInterface)widgetData).notifyClickedWidget(widgetData, pointerPosX, pointerPosY);
			}
			
			WidgetData focusedWidgetData = desktopPageData.getFocusedWidgetData();
			
			if (focusedWidgetData != widgetData)
			{	
				if (focusedWidgetData != null)
				{	
					if (focusedWidgetData instanceof FocusedWidgetListenerInterface)
					{
						((FocusedWidgetListenerInterface)focusedWidgetData).notifyDefocusedWidget(focusedWidgetData);
					}
				}
				
				desktopPageData.setFocusedWidgetData(widgetData);
				
				if (widgetData instanceof FocusedWidgetListenerInterface)
				{
					((FocusedWidgetListenerInterface)widgetData).notifyFocusedWidget(widgetData);
				}
			}
		}
		else
		{
			WidgetData focusedWidgetData = desktopPageData.getFocusedWidgetData();
			
			if (focusedWidgetData != null)
			{	
				if (focusedWidgetData instanceof FocusedWidgetListenerInterface)
				{	
					((FocusedWidgetListenerInterface)focusedWidgetData).notifyDefocusedWidget(focusedWidgetData);
				}
			}
			desktopPageData.setFocusedWidgetData(null);

			desktopPageData.setSelectedWidgetData(null);
		}
	}

	/**
	 * <p>
	 * 	This Function is called, if the Mouse Pointer Button is released.
	 * 	The Function checks, if there is a pressed (selected) and active Widget.
	 * 	If this Widget implements the {@link ClickedWidgetListenerInterface} or
	 * 	the {@link FocusedWidgetListenerInterface}, the correlating functions are called.
	 * </p>
	 * 
	 * @param desktopPageData
	 */
	public static void pointerReleased(DesktopPageData desktopPageData)
	{
		InputWidgetData pressedButtonData = desktopPageData.getPressedButtonData();
		
		desktopPageData.setPointerPressed(false);
		
		if (pressedButtonData != null)
		{
			// Mouse is still over the Button ?
			if (pressedButtonData == desktopPageData.getActiveWidgetData())
			{	
				if (pressedButtonData.getButtonActionLogicListener() != null)
				{
					pressedButtonData.getButtonActionLogicListener().notifyButtonReleased(pressedButtonData);
				}
			}
		}
		
		WidgetData selectedWidgetData = desktopPageData.getSelectedWidgetData();
		
		if (selectedWidgetData != null)
		{
			if (selectedWidgetData instanceof ClickedWidgetListenerInterface)
			{
				((ClickedWidgetListenerInterface)selectedWidgetData).notifyReleasedWidget(selectedWidgetData);
			}
			
			desktopPageData.setSelectedWidgetData(null);
		}
	}
}
