package de.schmiereck.noiseComp.desktopPage.widgets;

import de.schmiereck.screenTools.controller.DataChangedObserver;


/**
 * TODO docu
 *
 * @author smk
 * @version <p>06.03.2004: created, smk</p>
 */
public class TextWidgetData
extends WidgetData
{
	/**
	 * Text of the Label.
	 */
	private String	labelText;
	
	/**
	 * Constructor.
	 * 
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 */
	public TextWidgetData(DataChangedObserver dataChangedObserver,
						  String labelText, int posX, int posY, int sizeX, int sizeY)
	{
		super(dataChangedObserver,
			  posX, posY, sizeX, sizeY, false);
		this.labelText = labelText;
	}

	/**
	 * @return the attribute {@link #labelText}.
	 */
	public String getLabelText()
	{
		return this.labelText;
	}
	/**
	 * @param labelText is the new value for attribute {@link #labelText} to set.
	 */
	public void setLabelText(String labelText)
	{
		this.labelText = labelText;

		this.dataChangedVisible();
	}
}
