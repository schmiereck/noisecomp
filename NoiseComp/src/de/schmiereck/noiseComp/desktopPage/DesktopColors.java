package de.schmiereck.noiseComp.desktopPage;

import java.awt.Color;

/**
 * TODO docu
 *
 * @author smk
 * @version 08.02.2004
 */
public class DesktopColors
{
	/**
	 * Color of Mouse Pointer.
	 */
	private Color pointerColor;
	
	private Color activeButtonColor;
	
	private Color starColor;

	private Color sampleColor; 
	private Color timestepColor;

	private Color activeGeneratorBackgroundColor;

	private Color inactiveGeneratorBackgroundColor;

	private Color selectedGeneratorSelectorColor;

	private Color inactiveButtonColor;

	private Color paneBackgroundColor;

	private Color activeInputlineColor;

	private Color inactiveInputlineColor;

	private Color inactiveButtonBorderColor;

	private Color generatorsBackgroundColor;

	private Color generatorBottomLineColor;

	private Color focusedInputlineColor;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public DesktopColors()
	{
		super();

		// Good old Gray-Dialog Color Schema:
		/*
		 this.pointerColor = new Color(0F, 0F, 1.0F, 0.3F);
		 this.paneBackgroundColor = Color.LIGHT_GRAY;
		 this.inactiveButtonColor = Color.LIGHT_GRAY;
		 this.activeButtonColor = new Color(0xE0, 0xE8, 0xE4);
		 this.inactiveButtonBorderColor = Color.GRAY;
		 this.starColor = new Color(0xE0, 0xD0, 0xD0);
		 //this.sampleColor = new Color(0x00, 0x00, 0x00, 255/5);
		 this.sampleColor = Color.BLACK; 
		 this.timestepColor = new Color(0xF0, 0xFF, 0xF0);
		 this.activeGeneratorBackgroundColor 	= new Color(0xFF, 0xFF, 0xFF);
		 this.inactiveGeneratorBackgroundColor	= new Color(0xDF, 0xFF, 0xDF);
		 this.selectedGeneratorSelectorColor	= new Color(0x00, 0x00, 0xFF);
		 this.activeInputlineColor	= new Color(0xEF, 0xEF, 0xEF);
		 this.focusedInputlineColor	= new Color(0xFF, 0xFF, 0xFF);
		 this.inactiveInputlineColor	= new Color(0xDF, 0xFF, 0xDF);
		 this.generatorsBackgroundColor = new Color(0xE0, 0xE0, 0xE0);
		 this.generatorBottomLineColor = Color.GRAY;
		 */
		// The more happy new Color Schema:
		this.pointerColor = new Color(0.4F, 0.4F, 1.0F, 0.75F);
		//this.paneBackgroundColor = new Color(0x1B, 0x12, 0xB4);
		this.paneBackgroundColor = new Color(0xFF, 0xFF, 0xCC);
		this.inactiveButtonColor = new Color(0xFB, 0xE2, 0x49);
		this.activeButtonColor = new Color(0xE5, 0xEF, 0x4C);
		this.inactiveButtonBorderColor = new Color(0xE4, 0xC4, 0x05);
		this.starColor = new Color(0xE0, 0xD0, 0xD0);
		//this.sampleColor = new Color(0x00, 0x00, 0x00, 255/5);
		this.sampleColor = Color.BLACK; 
		this.timestepColor = new Color(0xF0, 0xFF, 0xF0);
		this.activeGeneratorBackgroundColor 	= new Color(0xFF, 0xFF, 0xFF);
		this.inactiveGeneratorBackgroundColor	= new Color(0xDF, 0xFF, 0xDF);
		this.selectedGeneratorSelectorColor	= new Color(0x00, 0x00, 0xFF);
		this.activeInputlineColor	= new Color(0xEF, 0xEF, 0xEF);
		this.focusedInputlineColor	= new Color(0xFF, 0xFF, 0xFF);
		this.inactiveInputlineColor	= new Color(0xFF, 0xFF, 0x2E);
		this.generatorsBackgroundColor = new Color(0xAA, 0xE0, 0xFD);
		this.generatorBottomLineColor = new Color(0x1B, 0x12, 0xB4);
	}

	/**
	 * @return the attribute {@link #activeButtonColor}.
	 */
	public Color getActiveButtonColor()
	{
		return this.activeButtonColor;
	}
	/**
	 * @return the attribute {@link #activeGeneratorBackgroundColor}.
	 */
	public Color getActiveGeneratorBackgroundColor()
	{
		return this.activeGeneratorBackgroundColor;
	}
	/**
	 * @return the attribute {@link #activeInputlineColor}.
	 */
	public Color getActiveInputlineColor()
	{
		return this.activeInputlineColor;
	}
	/**
	 * @return the attribute {@link #generatorBottomLineColor}.
	 */
	public Color getGeneratorBottomLineColor()
	{
		return this.generatorBottomLineColor;
	}
	/**
	 * @return the attribute {@link #generatorsBackgroundColor}.
	 */
	public Color getGeneratorsBackgroundColor()
	{
		return this.generatorsBackgroundColor;
	}
	/**
	 * @return the attribute {@link #inactiveButtonBorderColor}.
	 */
	public Color getInactiveButtonBorderColor()
	{
		return this.inactiveButtonBorderColor;
	}
	/**
	 * @return the attribute {@link #inactiveButtonColor}.
	 */
	public Color getInactiveButtonColor()
	{
		return this.inactiveButtonColor;
	}
	/**
	 * @return the attribute {@link #inactiveGeneratorBackgroundColor}.
	 */
	public Color getInactiveGeneratorBackgroundColor()
	{
		return this.inactiveGeneratorBackgroundColor;
	}
	/**
	 * @return the attribute {@link #inactiveInputlineColor}.
	 */
	public Color getInactiveInputlineColor()
	{
		return this.inactiveInputlineColor;
	}
	/**
	 * @return the attribute {@link #paneBackgroundColor}.
	 */
	public Color getPaneBackgroundColor()
	{
		return this.paneBackgroundColor;
	}
	/**
	 * @return the attribute {@link #pointerColor}.
	 */
	public Color getPointerColor()
	{
		return this.pointerColor;
	}
	/**
	 * @return the attribute {@link #sampleColor}.
	 */
	public Color getSampleColor()
	{
		return this.sampleColor;
	}
	/**
	 * @return the attribute {@link #selectedGeneratorSelectorColor}.
	 */
	public Color getSelectedGeneratorSelectorColor()
	{
		return this.selectedGeneratorSelectorColor;
	}
	/**
	 * @return the attribute {@link #starColor}.
	 */
	public Color getStarColor()
	{
		return this.starColor;
	}
	/**
	 * @return the attribute {@link #timestepColor}.
	 */
	public Color getTimestepColor()
	{
		return this.timestepColor;
	}

	/**
	 * @see #focusedInputlineColor
	 */
	public Color getFocusedInputlineColor()
	{
		return this.focusedInputlineColor;
	}
}
