package de.schmiereck.noiseComp.desktopPage;

import de.schmiereck.noiseComp.desktopPage.widgets.WidgetData;

/**
 * TODO docu
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
