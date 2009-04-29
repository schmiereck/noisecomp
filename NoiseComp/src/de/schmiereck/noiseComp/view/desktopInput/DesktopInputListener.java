package de.schmiereck.noiseComp.view.desktopInput;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import de.schmiereck.noiseComp.view.desktopController.DesktopControllerLogic;
import de.schmiereck.screenTools.graphic.GraficInputListener;
import de.schmiereck.screenTools.input.InputDeviceInterface;

/**
 * Listener for Keyboard and Mouse Inputs.<br/>
 * Connect this events with the {@link DesktopControllerLogic} logic.
 *
 * @author smk
 * @version 08.01.2004
 */
public class DesktopInputListener
implements GraficInputListener
{
	private DesktopControllerLogic gameControllerLogic = null;
	//private DesktopGraphic graphic = null;
	
	private double graphicScaleX;
	private double graphicScaleY;
	private int graphicCenterX;
	private int graphicCenterY;
	
	public void setGameControllerLogic(DesktopControllerLogic gameControllerLogic)
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
		int posX = (int)(e.getX() * this.graphicScaleX);// - this.graphic.getCenterX();
		int posY = (int)(e.getY() * this.graphicScaleY);// - this.graphic.getCenterY();
		
		this.gameControllerLogic.movePointer(posX, posY);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent e)
	{
		int posX = (int)(e.getX() * this.graphicScaleX) - this.graphicCenterX;
		int posY = (int)(e.getY() * this.graphicScaleY) - this.graphicCenterY;//XXX
		
		this.gameControllerLogic.movePointer(posX, posY);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e)
	{
		char c = e.getKeyChar();
		
		int type = Character.getType(c);
		
		if ((type == Character.DECIMAL_DIGIT_NUMBER) ||			// 0123456789
			(type == Character.LOWERCASE_LETTER) ||				// a-z, öäü
				(type == Character.UPPERCASE_LETTER) ||			// A-Z, ÖÄÜ
				(type == Character.CONNECTOR_PUNCTUATION) ||	// 
				(type == Character.DASH_PUNCTUATION) ||			// -_
				(type == Character.OTHER_PUNCTUATION))			// .,
		{		
			this.gameControllerLogic.doInputChar(c);
			
			e.consume();
		}
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
			case KeyEvent.VK_LEFT:	// LEFT
				{
					this.gameControllerLogic.doMoveCursor(-1);
					break;
				} 
			case KeyEvent.VK_RIGHT:	// RIGHT
				{
					this.gameControllerLogic.doMoveCursor(1);
					break;
				} 
			case KeyEvent.VK_UP:	// UP
				{
					this.gameControllerLogic.doScrollCursor(-1);
					break;
				} 
			case KeyEvent.VK_DOWN:	// DOWN
				{
					this.gameControllerLogic.doScrollCursor(1);
					break;
				} 
			case KeyEvent.VK_HOME:	// HOME, POS1
				{
					this.gameControllerLogic.doChangeCursorPos(0);
					break;
				} 
			case KeyEvent.VK_END:	// END
				{
					this.gameControllerLogic.doChangeCursorPos(1);
					break;
				} 
			case KeyEvent.VK_DELETE:	// DELETE
				{
					this.gameControllerLogic.doDelete(1);
					break;
				} 
			case KeyEvent.VK_BACK_SPACE:	// BACK_SPACE
				{
					this.gameControllerLogic.doDelete(-1);
					break;
				} 
			case KeyEvent.VK_TAB:	// TAB
				{
					if ((keyEvent.getModifiersEx() & KeyEvent.SHIFT_DOWN_MASK) == KeyEvent.SHIFT_DOWN_MASK)
					{
						this.gameControllerLogic.doFocusWalk(-1);
					}
					else
					{
						this.gameControllerLogic.doFocusWalk(1);
					}
					break;
				} 
			case KeyEvent.VK_ENTER:	// ENTER
				{
					// Defaultverhalten der Seite beim ENTER ausl�sen.
					this.gameControllerLogic.doSubmitPage();
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

	public void setGraphic(double graphicScaleX, double graphicScaleY,
						   int graphicCenterX, int graphicCenterY)
	{
		this.graphicScaleX = graphicScaleX;
		this.graphicScaleY = graphicScaleY;
		this.graphicCenterX = graphicCenterX;
		this.graphicCenterY = graphicCenterY;
	}

}
