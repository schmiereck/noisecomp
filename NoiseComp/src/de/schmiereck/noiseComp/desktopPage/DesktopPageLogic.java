package de.schmiereck.noiseComp.desktopPage;

import java.util.Iterator;

import de.schmiereck.noiseComp.desktopPage.widgets.ButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.FunctionButtonData;
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
	 * 	Calculates the actual State of the Widgets by the actual DesctopPageData State.
	 * </p>
	 * <p>
	 * 	Mainly the active state (Mouse Rollover) is calculated an the 
	 * 	correlating functions of the Listener-Interfaces are called.
	 * </p>
	 * 
	 * @param desctopPageData
	 */
	public static void calcWidgets(DesktopPageData desctopPageData)
	{
		WidgetData hitWidgetData = null;
		ButtonData hitButtonData = null;
		//int hitScrollbarPart = 0;
		ScrollbarData hitScrollbarData = null;
		
		int pointerPosX = desctopPageData.getPointerPosX();
		int pointerPosY = desctopPageData.getPointerPosY();
		
		Iterator widgetIterator = desctopPageData.getWidgetsIterator();
		
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
		
		desctopPageData.setActiveWidgetData(hitWidgetData, pointerPosX, pointerPosY);
		desctopPageData.setActiveButtonData(hitButtonData);
		desctopPageData.setActiveScrollbarData(hitScrollbarData);
	}
	
	/**
	 * <p>
	 * 	This Function is called, if the Mouse Pointer Button is pressed down.
	 * 	The Function checks, if there is a active (with Mouse Rollover) Widget.
	 * 	If this Widget implements the {@link ClickedWidgetListenerInterface} or
	 * 	the {@link FocusedWidgetListenerInterface}, the correlating functions are called.
	 * </p>
	 *  
	 * @param desctopPageData
	 */
	public static void pointerPressed(DesktopPageData desctopPageData)
	{
		desctopPageData.setPointerPressed(true);

		WidgetData widgetData = desctopPageData.getActiveWidgetData();
		
		if (widgetData != null)
		{
			desctopPageData.setSelectedWidgetData(widgetData);
			
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
			
			if (focusedWidgetData != null)
			{	
				((FocusedWidgetListenerInterface)focusedWidgetData).notifyDefocusedWidget(focusedWidgetData);
			}
			desctopPageData.setFocusedWidgetData(null);

			desctopPageData.setSelectedWidgetData(null);
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
	 * @param desctopPageData
	 * @param buttonPressedCallback
	 */
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
		
		WidgetData selectedWidgetData = desctopPageData.getSelectedWidgetData();
		
		if (selectedWidgetData != null)
		{
			if (selectedWidgetData instanceof ClickedWidgetListenerInterface)
			{
				((ClickedWidgetListenerInterface)selectedWidgetData).notifyReleasedWidget(selectedWidgetData);
			}
			
			desctopPageData.setSelectedWidgetData(null);
		}
	}
}
