package de.schmiereck.noiseComp.desctopInput;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import de.schmiereck.noiseComp.desctopController.DesctopControllerLogic;
import de.schmiereck.noiseComp.desctopGraphic.DesctopGraphic;
import de.schmiereck.screenTools.graphic.GraficInputListener;
import de.schmiereck.screenTools.input.InputDeviceInterface;

/**
 * TODO docu
 *
 * @author smk
 * @version 08.01.2004
 */
public class DesctopInputListener
implements GraficInputListener
{
	private DesctopControllerLogic gameControllerLogic = null;
	private DesctopGraphic graphic = null;
	
	public void setGameControllerLogic(DesctopControllerLogic gameControllerLogic)
	{
		this.gameControllerLogic = gameControllerLogic;
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.GraficInputListener#setInputDevice(de.schmiereck.screenTools.input.InputDeviceInterface)
	 */
	public void setInputDevice(InputDeviceInterface inputDevice)
	{
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e)
	{
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e)
	{
		this.gameControllerLogic.pointerPressed();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e)
	{
		this.gameControllerLogic.pointerReleased();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e)
	{
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e)
	{
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent e)
	{
		int posX = (int)(e.getX() * this.graphic.getScaleX());
		int posY = (int)(e.getY() * this.graphic.getScaleY());
		
		this.gameControllerLogic.movePointer(posX, posY);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent e)
	{
		int posX = (int)(e.getX() * this.graphic.getScaleX());
		int posY = (int)(e.getY() * this.graphic.getScaleY());
		
		this.gameControllerLogic.movePointer(posX, posY);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e)
	{
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent keyEvent)
	{
		switch (keyEvent.getKeyCode())
		{
			case KeyEvent.VK_ESCAPE:	// ESC
				{
					this.gameControllerLogic.doEndGame();
					break;
				} 
		}
		}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e)
	{
		
	}

	/**
	 * @param multiBufferGraphic
	 */
	public void setGraphic(DesctopGraphic graphic)
	{
		this.graphic = graphic;
	}

}
