package de.schmiereck.noiseComp.desktopPage.widgets;

import de.schmiereck.noiseComp.desktopPage.FocusedWidgetListenerInterface;
import de.schmiereck.noiseComp.desktopPage.SubmitWidgetListenerInterface;

/**
 * TODO docu
 *
 * @author smk
 * @version 29.01.2004
 */
public class FunctionButtonData 
extends InputWidgetData
implements FocusedWidgetListenerInterface, SubmitWidgetListenerInterface
{
	private String labelText;
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 * @param labelText
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 */
	public FunctionButtonData(String name, String labelText, int posX, int posY, int sizeX, int sizeY)
	{
		super(name, posX, posY, sizeX, sizeY);

		this.labelText = labelText;
	}

	/**
	 * @return the attribute {@link #labelText}.
	 */
	public String getLabelText()
	{
		return this.labelText;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.FocusedWidgetListenerInterface#notifyDefocusedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyDefocusedWidget(WidgetData widgetData)
	{
		this.setHaveFocus(false);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.FocusedWidgetListenerInterface#notifyFocusedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyFocusedWidget(WidgetData widgetData)
	{
		this.setHaveFocus(true);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.SubmitWidgetListenerInterface#notifySubmit()
	 */
	public void notifySubmit()
	{
		if (this.getButtonActionLogicListener() != null)
		{
			this.getButtonActionLogicListener().notifyButtonReleased(this);
		}
	}
}
