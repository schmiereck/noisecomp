package de.schmiereck.noiseComp.desktopPage.widgets;

/**
 * TODO docu
 *
 * @author smk
 * @version 21.02.2004
 */
public class SelectEntryData
{
	private Object 	value;
	private String	labelText;
	private Object	entry;
	
	/**
	 * Constructor.
	 * 
	 * @param value
	 * @param labelText
	 * @param entry
	 */
	public SelectEntryData(Object value, String labelText, Object entry)
	{
		super();
		this.value = value;
		this.labelText = labelText;
		this.entry = entry;
	}

	/**
	 * @see #value
	 */
	public Object getValue()
	{
		return this.value;
	}

	/**
	 * @see #labelText
	 */
	public String getLabelText()
	{
		return this.labelText;
	}
}
