package de.schmiereck.noiseComp.desktopPage;

import de.schmiereck.noiseComp.desktopPage.widgets.WidgetData;

/**
 * Informs if a widget is clicked by the moise pointer.
 *
 * @author smk
 * @version 01.02.2004
 */
public interface ClickedWidgetListenerInterface
{

	/**
	 * @param widgetData
	 */
	void notifyClickedWidget(WidgetData widgetData);

	/**
	 * @param selectedWidgetData
	 */
	void notifyReleasedWidget(WidgetData selectedWidgetData);

}
