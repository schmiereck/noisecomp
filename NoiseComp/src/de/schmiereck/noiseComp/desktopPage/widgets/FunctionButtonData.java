package de.schmiereck.noiseComp.desktopPage.widgets;

import de.schmiereck.noiseComp.desktopPage.FocusedWidgetListenerInterface;
import de.schmiereck.noiseComp.desktopPage.SubmitWidgetListenerInterface;
import de.schmiereck.screenTools.controller.DataChangedObserver;

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
	 */
	public FunctionButtonData(DataChangedObserver dataChangedObserver,
							  String name, String labelText, int posX, int posY, int sizeX, int sizeY)
	{
		super(dataChangedObserver,
			  name, posX, posY, sizeX, sizeY);

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

		this.dataChangedVisible();
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.FocusedWidgetListenerInterface#notifyFocusedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyFocusedWidget(WidgetData widgetData)
	{
		this.setHaveFocus(true);

		this.dataChangedVisible();
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.SubmitWidgetListenerInterface#notifySubmit()
	 */
	public void notifySubmit()
	throws MainActionException
	{
		if (this.getButtonActionLogicListener() != null)
		{
			this.getButtonActionLogicListener().notifyButtonReleased(this);
		}
	}
}
