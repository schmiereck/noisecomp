package de.schmiereck.noiseComp.desktopPage.widgets;

import de.schmiereck.screenTools.controller.DataChangedObserver;

/**
 * Manages the Data of an Label-Text Object.
 * The Text is shown with an right alignment.
 * 
 * @author smk
 * @version 07.02.2004
 */
public class LabelData 
extends WidgetData
{
	/**
	 * Text of the Label.
	 */
	private String	labelText;
	
	/**
	 * Constructor.
	 * 
	 */
	public LabelData(DataChangedObserver dataChangedObserver,
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
