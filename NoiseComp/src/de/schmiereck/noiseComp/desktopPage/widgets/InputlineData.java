package de.schmiereck.noiseComp.desktopPage.widgets;

import de.schmiereck.noiseComp.desktopPage.FocusedWidgetListenerInterface;
import de.schmiereck.noiseComp.desktopPage.SubmitWidgetListenerInterface;

/**
 * TODO docu
 *
 * @author smk
 * @version 07.02.2004
 */
public class InputlineData
extends InputWidgetData
implements FocusedWidgetListenerInterface, SubmitWidgetListenerInterface
{
	private String inputText = null;
	private int inputPos = 0;
	
	/**
	 * Default Button, if the widget is submited.<br/>
	 * Normaly a {@link FunctionButtonData}-Object who perform the submit.
	 */
	private SubmitWidgetListenerInterface defaultSubmitWidgetInterface = null;
	
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
	public synchronized String getInputText()
	{
		String ret;
		
		if (this.inputText != null)
		{
			if (this.inputText.length() > 0)
			{
				ret = this.inputText;
			}
			else
			{
				ret = null;
			}
		}
		else
		{
			ret = null;
		}

		return ret;
	}
	/**
	 * @see #inputText
	 */
	public synchronized void setInputText(String inputText)
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

	public synchronized Integer getInputInteger()
	{
		String text = this.getInputText();
		
		Integer ret;
		
		if (text != null)
		{
			ret = Integer.valueOf(text);
		}
		else
		{	
			ret = null;
		}
		return ret;
	}

	public synchronized void setInputInteger(Integer value)
	{
		String text;
		
		if (value != null)
		{
			text = value.toString();
		}
		else
		{	
			text = null;
		}
		this.setInputText(text);
	}

	public synchronized Float getInputFloat()
	{
		String text = this.getInputText();
		
		Float ret;
		
		if (text != null)
		{
			ret = Float.valueOf(text);
		}
		else
		{	
			ret = null;
		}
		return ret;
	}

	public synchronized void setInputFloat(Float value)
	{
		String text;
		
		if (value != null)
		{
			text = value.toString();
		}
		else
		{	
			text = null;
		}
		this.setInputText(text);
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
	public synchronized int getInputPos()
	{
		return this.inputPos;
	}
	/**
	 * @param dir
	 */
	public synchronized void moveCursor(int dir)
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
	 * @param dir	0:	begin<br/>
	 * 				1:	end
	 */
	public synchronized void changeCursorPos(int dir)
	{
		if (dir == 0)
		{	
			this.inputPos = 0;
		}
		else
		{
			if (this.inputText != null)
			{
				this.inputPos = this.inputText.length();
			}
			else
			{
				this.inputPos = 0;
			}
		}
	}

	/**
	 * @param dir
	 */
	public synchronized void deleteChar(int dir)
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
	public synchronized void inputChar(char c)
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

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.SubmitWidgetListenerInterface#notifySubmit()
	 */
	public void notifySubmit()
	{
		if (this.defaultSubmitWidgetInterface != null)
		{	
			this.defaultSubmitWidgetInterface.notifySubmit();
		}
	}
	
	/**
	 * @param defaultSubmitWidgetInterface is the new value for attribute {@link #defaultSubmitWidgetInterface} to set.
	 */
	public void setDefaultSubmitWidgetInterface(SubmitWidgetListenerInterface defaultSubmitWidgetInterface)
	{
		this.defaultSubmitWidgetInterface = defaultSubmitWidgetInterface;
	}
}
