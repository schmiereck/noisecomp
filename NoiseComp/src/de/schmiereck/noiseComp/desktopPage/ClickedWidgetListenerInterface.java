package de.schmiereck.noiseComp.desktopPage;

import de.schmiereck.noiseComp.desktopPage.widgets.WidgetData;

/**
 * Informs if a widget is clicked by the mouse pointer or the pointer is draged this widget.
 *
 * @author smk
 * @version 01.02.2004
 */
public interface ClickedWidgetListenerInterface
{

	/**
	 * @param widgetData
	 */
	void notifyClickedWidget(WidgetData widgetData, int pointerPosX, int pointerPosY);

	/**
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
