package de.schmiereck.noiseComp.desktopPage.widgets;

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
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 */
	public LabelData(String labelText, int posX, int posY, int sizeX, int sizeY)
	{
		super(posX, posY, sizeX, sizeY, false);
		this.labelText = labelText;
	}

	/**
	 * @return the attribute {@link #labelText}.
	 */
	public String getLabelText()
	{
		return this.labelText;
	}
}
