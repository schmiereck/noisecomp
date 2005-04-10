package de.schmiereck.noiseComp.desktopPage.widgets;

import java.util.Random;
import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.controller.DataChangedObserver;

/**
 * TODO docu
 *
 * @author smk
 * @version 26.01.2004
 */
public class PaneData
extends WidgetData
{

	public static final int STARS_COUNT = 60; 
	private int starsX[]	= new int[STARS_COUNT];
	private int starsY[]	= new int[STARS_COUNT];
	
	private Random rnd = new Random();
	
	public static final int SIZE = 500;
	
	private static final int SPEED = 11;
	private static final int SPEED2 = SPEED / 2;
	
	/**
	 * Constructor.
	 * 
	 */
	public PaneData(ControllerData controllerData,
					  DataChangedObserver dataChangedObserver,
					int posX, int posY, int sizeX, int sizeY)
	{
		super(controllerData, dataChangedObserver,
			  posX, posY, sizeX, sizeY, false);

		for (int pos = 0; pos < PaneData.STARS_COUNT; pos++)
		{
			this.setStar(pos, 
						rnd.nextInt(sizeX * PaneData.SIZE) - (sizeX * PaneData.SIZE) / 2, 
						rnd.nextInt(sizeY * PaneData.SIZE) - (sizeY * PaneData.SIZE) / 2);
		}
	}

	public void setStar(int pos, int x, int y)
	{
		this.starsX[pos] = x;
		this.starsY[pos] = y;
	}

	public int getStarX(int pos)
	{
		return this.starsX[pos];
	}
	
	public int getStarY(int pos)
	{
		return this.starsY[pos];
	}
	
	public void anim()
	{
		int sizeX = this.getSizeX() * PaneData.SIZE / 2;
		int sizeY = this.getSizeY() * PaneData.SIZE / 2;
		
		for (int pos = 0; pos < PaneData.STARS_COUNT; pos++)
		{
			int x = this.getStarX(pos);
			int y = this.getStarY(pos);

			if ((x >= -SPEED2) && (x <= SPEED2))
			{
				x = this.rnd.nextInt(SPEED) - SPEED2;
			}
			if ((y >= -SPEED2) && (y <= SPEED2))
			{
				y = this.rnd.nextInt(SPEED) - SPEED2;
			}
			x += (x / SPEED2);
			y += (y / SPEED2);
			
			if ((x < -sizeX) || (x > sizeX))
			{
				x = this.rnd.nextInt(SPEED) - SPEED2;
			}
			if ((y < -sizeY) || (y > sizeY))
			{
				y = this.rnd.nextInt(SPEED) - SPEED2;
			}
			
			this.setStar(pos, x, y);
		}

		//this.dataChangedVisible();
	}
}
