package de.schmiereck.noiseComp.desktopPage;

import de.schmiereck.noiseComp.desktopPage.widgets.WidgetData;

/**
 * TODO docu
 *
 * @author smk
 * @version 08.02.2004
 */
public interface HitWidgetListenerInterface
{

	/**
	 * @param activeWidgetData
	 * @param pointerPosX is relative to the X-Position of the Widget.
	 * @param pointerPosY is relative to the Y-Position of the Widget.
	 */
	public void notifyHitWidget(WidgetData activeWidgetData, int pointerPosX, int pointerPosY);
	
}
