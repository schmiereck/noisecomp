package de.schmiereck.noiseComp.desctopPage.widgets;

/**
 * TODO docu
 *
 * @author smk
 * @version 29.01.2004
 */
public class FunctionButtonData extends ButtonData
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
}
