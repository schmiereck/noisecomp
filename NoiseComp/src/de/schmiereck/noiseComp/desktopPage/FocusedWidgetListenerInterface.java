package de.schmiereck.noiseComp.desktopPage;

import de.schmiereck.noiseComp.desktopPage.widgets.WidgetData;

/**
 * Informs if the state of the focus of a widget is changed. 
 *
 * @author smk
 * @version 07.02.2004
 */
public interface FocusedWidgetListenerInterface
{

	/**
	 * @param widgetData
	 */
	void notifyFocusedWidget(WidgetData widgetData);

	/**
	 * @param widgetData
	 */
	void notifyDefocusedWidget(WidgetData widgetData);

}
