package de.schmiereck.noiseComp.desktopPage.widgets;

import de.schmiereck.noiseComp.desktopPage.FocusedWidgetListenerInterface;

/**
 * TODO docu
 *
 * @author smk
 * @version 07.02.2004
 */
public class InputlineData
extends ButtonData
implements FocusedWidgetListenerInterface
{
	private String inputText = null;
	private int inputPos = 0;
	
	/**
	 * Constructor.
	 * 
	 */
	public InputlineData(String name, int posX, int posY, int sizeX, int sizeY)
	{
		super(name, posX, posY, sizeX, sizeY);
	}
	/**
	 * @return the attribute {@link #inputText}.
	 */
	public String getInputText()
	{
		return this.inputText;
	}
	/**
	 * @see #inputText
	 */
	public void setInputText(String inputText)
	{
		this.inputText = inputText;

		int len;
		
		if (this.inputText != null)
		{
			len = this.inputText.length();
		}
		else
		{
			len = 0;
		}
		
		if (this.inputPos > len)
		{
			this.inputPos = len;
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.FocusedWidgetListenerInterface#notifyFocusedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyFocusedWidget(WidgetData widgetData)
	{
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.FocusedWidgetListenerInterface#notifyDefocusedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyDefocusedWidget(WidgetData widgetData)
	{
	}
	
	/**
	 * @return the attribute {@link #inputPos}.
	 */
	public int getInputPos()
	{
		return this.inputPos;
	}
	/**
	 * @param dir
	 */
	public void moveCursor(int dir)
	{
		if (dir == -1)
		{
			if (this.inputPos > 0)
			{
				this.inputPos--;
			}
		}
		else
		{
			int len;
			
			if (this.inputText != null)
			{
				len = this.inputText.length();
			}
			else
			{
				len = 0;
			}
			
			if (this.inputPos < len)
			{
				this.inputPos++;
			}
		}
	}
	/**
	 * @param dir
	 */
	public void deleteChar(int dir)
	{
		if (this.inputText != null)
		{
			int len = this.inputText.length();
			
			if (dir == 1)
			{
				dir = 0;
			}
			
			int pos = this.inputPos + dir;
			
			if ((pos >= 0) && (pos < len))
			{
				this.inputText = this.inputText.substring(0, pos) + this.inputText.substring(pos + 1);
				
				if (dir == -1)
				{
					this.moveCursor(-1);
				}
			}
		}
	}
	/**
	 * @param c
	 */
	public void inputChar(char c)
	{
		if (this.inputText == null)
		{
			this.inputText = "" + c;
		}
		else
		{
			this.inputText = this.inputText.substring(0, this.inputPos) + c + this.inputText.substring(this.inputPos);
		}

		this.moveCursor(1);
	}
}
