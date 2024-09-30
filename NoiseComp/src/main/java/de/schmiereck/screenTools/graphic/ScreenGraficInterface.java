package de.schmiereck.screenTools.graphic;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Interface für Funktionen zum zeichnen von 2D-Funktionen.
 *
 * @author smk
 * @version 01.02.2004
 */
public interface ScreenGraficInterface
{
	/**
	 * @param g
	 * @param name	Dialog, DialogInput, Monospaced, Serif, or SansSerif
	 * @param style	{@link Font#PLAIN}, {@link Font#BOLD} or {@link Font#ITALIC}
	 * @param size	in scaled Pixels.
	 */
	public abstract void setFont(Graphics g, String name, int style, int size);
	public abstract void drawCircle(Graphics g, int posX, int posY, int radius);
	public abstract void fillCircle(Graphics g, int posX, int posY, int radius);
	public abstract void drawLine(Graphics g, int posX, int posY, int dirX, int dirY);
	public abstract void drawAbsLine(Graphics g, int pos1X, int pos1Y, int pos2X, int pos2Y);
	public abstract void drawArc(Graphics g, int posX, int posY, int radius, int arc);
	public abstract void fillArc(Graphics g, int posX, int posY, int radius, int startAngel, int arcAngel);
	public abstract void fillRect(Graphics g, int posX, int posY, int width, int height);
	public abstract void drawRect(Graphics g, int posX, int posY, int width, int height);
	public abstract void draw3DRect(Graphics g, int posX, int posY, int width, int height, boolean raised);
	public abstract void drawString(Graphics g, int posX, int posY, String text);
	public abstract void drawPoint(Graphics g, int posX, int posY, int size);
	
	public void setColor(Graphics g, Color color);
	/**
	 * @param labelText
	 * @return
	 */
	public abstract int calcStringWidth(Graphics g, String labelText);
	/**
	 * @return
	 */
	public abstract int calcFontAscent(Graphics g);
	/**
	 * @param g
	 * @return
	 */
	public abstract int calcFontDescent(Graphics g);
}