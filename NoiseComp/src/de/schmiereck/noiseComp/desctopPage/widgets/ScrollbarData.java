package de.schmiereck.noiseComp.desctopPage.widgets;

/**
 * TODO docu
 *
 * @author smk
 * @version 26.01.2004
 */
public class ScrollbarData
extends ButtonData
{
	/**
	 * Position des Scrollers innerhalb von {@link #scrollerLength}.
	 */
	private float scrollerPos = 0.0F;

	/**
	 * Länge des Scrollers (ganze Bar).
	 */
	private float scrollerLength = 1.0F;

	/**
	 * Größe des Scrollers (Größe des Scroll-Buttons) im Verhältnis zu {@link #scrollerLength}.
	 */
	private float scrollerSize = 1.0F;
	
	/**
	 * Schrittweite um die gescrollt wird, wenn auf die Up-/Down-Buttons geklickt wird.
	 */
	private float scrollStep = 1.0F;

	/**
	 * true, wenn Vertical gescrollt werden soll,
	 * sonst wird Horizontal gescrollt.
	 */
	private boolean doScrollVertical;
	
	/**
	 * Constructor.
	 * 
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 */
	public ScrollbarData(String name, int posX, int posY, int sizeX, int sizeY, boolean doScrollVertical)
	{
		super(name, posX, posY, sizeX, sizeY);
		this.doScrollVertical = doScrollVertical;
	}

	/**
	 * @return the attribute {@link #scrollerLength}.
	 */
	public float getScrollerLength()
	{
		return this.scrollerLength;
	}
	/**
	 * @param scrollerLength is the new value for attribute {@link #scrollerLength} to set.
	 */
	public void setScrollerLength(float scrollerLength)
	{
		this.scrollerLength = scrollerLength;
	}
	/**
	 * @return the attribute {@link #scrollerPos}.
	 */
	public float getScrollerPos()
	{
		return this.scrollerPos;
	}
	/**
	 * @return the attribute {@link #scrollerSize}.
	 */
	public float getScrollerSize()
	{
		return this.scrollerSize;
	}
	/**
	 * @param scrollerPos is the new value for attribute {@link #scrollerPos} to set.
	 */
	public void setScrollerPos(float scrollerPos)
	{
		this.scrollerPos = scrollerPos;
	}
	/**
	 * @param scrollerSize is the new value for attribute {@link #scrollerSize} to set.
	 */
	public void setScrollerSize(float scrollerSize)
	{
		this.scrollerSize = scrollerSize;
	}

	/**
	 * @return
	 */
	public int getScreenScrollerPos()
	{
		int width;
		int screenLength;

		if (this.getDoScrollVertical() == true)
		{
			width = this.getSizeX();
			screenLength = this.getSizeY() - (width * 2);
		}
		else
		{
			width = this.getSizeY();
			screenLength = this.getSizeX() - (width * 2);
		}

		//  screenLength     screenPos
		// -------------- = -----------
		// scrollerLength   scrollerPos
		
		int screenPos = (int)((this.scrollerPos * screenLength) / this.scrollerLength);
		
		return screenPos + width;
	}

	/**
	 * @return the attribute {@link #scrollerSize}.
	 */
	public int getScreenScrollerSize()
	{
		int width;
		int screenLength;

		if (this.getDoScrollVertical() == true)
		{
			width = this.getSizeX();
			screenLength = this.getSizeY() - (width * 2);
		}
		else
		{
			width = this.getSizeY();
			screenLength = this.getSizeX() - (width * 2);
		}

		//  screenLength     screenSize
		// -------------- = -----------
		// scrollerLength   scrollerSize
		
		int screenSize;
		
		if (this.scrollerSize >= this.scrollerLength)
		{
			screenSize = screenLength;
		}
		else
		{	
			screenSize = (int)((screenLength * this.scrollerSize) / this.scrollerLength);
		}

		return screenSize;
	}
	/**
	 * @return the attribute {@link #doScrollVertical}.
	 */
	public boolean getDoScrollVertical()
	{
		return this.doScrollVertical;
	}
	/**
	 * @return the attribute {@link #scrollStep}.
	 */
	public float getScrollStep()
	{
		return this.scrollStep;
	}
	/**
	 * @param scrollStep is the new value for attribute {@link #scrollStep} to set.
	 */
	public void setScrollStep(float scrollStep)
	{
		this.scrollStep = scrollStep;
	}
}
