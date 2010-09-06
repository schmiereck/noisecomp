package de.schmiereck.noiseComp.smkScreen.desctopPage;

import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.WidgetData;

/**
 * Informs if a widget is clicked by the mouse pointer or the pointer is draged this widget.
 *
 * @author smk
 * @version 01.02.2004
 */
public interface ClickedWidgetListenerInterface
{

	/**
	 * Calling if widget is clicked with mouse.
	 * 
	 * @param widgetData
	 */
	void notifyClickedWidget(WidgetData widgetData, int pointerPosX, int pointerPosY);

	/**
	 * Calling if widget mouse click is realeased
	 * because of: ???
	 * 
	 * @param selectedWidgetData
	 */
	void notifyReleasedWidget(WidgetData selectedWidgetData);

	/**
	 * @param selectedWidgetData
	 * @param pointerPosX
	 * @param pointerPosY
	 */
	void notifyDragWidget(WidgetData selectedWidgetData, int pointerPosX, int pointerPosY);

}
