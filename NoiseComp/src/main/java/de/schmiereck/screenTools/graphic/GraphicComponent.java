package de.schmiereck.screenTools.graphic;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.IllegalComponentStateException;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.util.Locale;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRole;
import javax.accessibility.AccessibleStateSet;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.Scrollable;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.plaf.TextUI;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.controller.DataChangedListener;

/*
 * Created on 25.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	TODO docu type
 * </p>
 * 
 * @author smk
 * @version <p>25.03.2005:	created, smk</p>
 */
public class GraphicComponent
extends JPanel
implements DataChangedListener, Scrollable, Accessible
{
	private ControllerData controllerData = null;
	private MultiBufferFullScreenGraphic multiBufferGraphic;
	
	//private transient MutableCaretEvent focusListener;
	
	/*
	class MyAccessibleContext
	extends AccessibleJPanel//AccessibleContext
	{
		private AccessibleStateSet accessibleStateSet = new AccessibleStateSet();
		
		private Locale locale;
		
		/ **
		 * Constructor.
		 * 
		 * /
		public MyAccessibleContext(Locale locale)
		{
			this.locale = locale;
		}

		public AccessibleRole getAccessibleRole()
		{
			return AccessibleRole.SWING_COMPONENT;
		}

		public AccessibleStateSet getAccessibleStateSet()
		{
			return this.accessibleStateSet;
		}

		public int getAccessibleIndexInParent()
		{
			return 0;
		}

		public int getAccessibleChildrenCount()
		{
			return 0;
		}

		public Accessible getAccessibleChild(int i)
		{
			return null;
		}

		public Locale getLocale()
			throws IllegalComponentStateException
		{
			return this.getLocale();
		}
	}
	*/

	/**
	 * Constructor.
	 * @param data
	 * 
	 */
	public GraphicComponent(MultiBufferFullScreenGraphic multiBufferGraphic, 
							///ControllerData controllerData)
							int fieldWidth, int fieldHeight)
	{
		super();
		
		this.multiBufferGraphic = multiBufferGraphic;
		///this.controllerData = controllerData;

		//setLayout(null); // layout is managed by View hierarchy
		
		//this.focusListener = new MutableCaretEvent();
		
		//this.addFocusListener(this.focusListener);
		/*
		this.addKeyListener(new java.awt.event.KeyAdapter()
							    {
							      public void keyPressed(KeyEvent e)
							      {
							        System.out.println("GraphicComponent KEY PRESSES");
							      }
							    });		
        */
        /*
        LookAndFeel.installProperty(this,
                                    "focusTraversalKeysForward", 
                                    JComponent.
                                    getManagingFocusForwardTraversalKeys());
        LookAndFeel.installProperty(this,
                                    "focusTraversalKeysBackward", 
                                    JComponent.
                                    getManagingFocusBackwardTraversalKeys());
        */
		//this.setPreferredSize(new Dimension(this.controllerData.getFieldWidth(), 
		//									this.controllerData.getFieldHeight()));
		this.setPreferredSize(new Dimension(fieldWidth, 
											fieldHeight));

		//setUI((TextUI)UIManager.getUI(this));
		
		//this.accessibleContext = new MyAccessibleContext(); //this.getLocale());
		
		this.enableEvents(AWTEvent.KEY_EVENT_MASK | 
						  AWTEvent.FOCUS_EVENT_MASK | 
						  AWTEvent.INPUT_METHOD_EVENT_MASK |
						  //AWTEvent.INPUT_METHODS_ENABLED_MASK |
						  AWTEvent.MOUSE_EVENT_MASK |
						  AWTEvent.MOUSE_MOTION_EVENT_MASK | 
						  AWTEvent.COMPONENT_EVENT_MASK |
						  AWTEvent.TEXT_EVENT_MASK);

		this.setVisible(true);
		this.setEnabled(true);
		this.enableInputMethods(true);
		this.setFocusable(true);
		this.setFocusCycleRoot(true);
		this.setFocusTraversalKeysEnabled(false);
		this.setRequestFocusEnabled(true);
		this.setVerifyInputWhenFocusTarget(true);
		
		//JTextField textField = new JTextField("Hallo");
		
		//this.add(textField);
	}

	public AccessibleContext getAccessibleContext()
	{
		return super.getAccessibleContext();
	}

    //protected void processComponentKeyEvent(KeyEvent e)
    //{
    //	super.processComponentKeyEvent(e);
    //}
    /*
    static class MutableCaretEvent implements FocusListener//, ChangeListener, MouseListener 
	{

		/ * (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
		 * /
		public void focusGained(FocusEvent fe)
		{
			System.out.println("focusGained: " + fe.getSource());
		}

		/ * (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
		 * /
		public void focusLost(FocusEvent fe)
		{
			System.out.println("focusLost: " + fe.getSource());
		}
	}
    */
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g)
	//public void update(Graphics g)
	{
		super.paintComponent(g);

		//System.out.println(this.getBounds());
		
		this.multiBufferGraphic.drawGraphic(g, 
											this.controllerData,
											this.getBounds(),
											30L);
		
	    //g.dispose();
		//g.drawRect(10, 10, 30, 50);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.DataChangedListener#notifyDataChanged(int, int, int, int)
	 */
	public void notifyDataChanged(ControllerData controllerData,
								  int posX, int posY, int sizeX, int sizeY)
	{
		this.controllerData = controllerData;
        //this.updateUI();
		//this.validate();
		//this.invalidate();

		//this.revalidate();
	    this.repaint(posX, posY, sizeX, sizeY);
		//this.repaint();

		//System.out.println("CH");
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.DataChangedListener#notifyDataChanged()
	 */
	public void notifyDataChanged(ControllerData controllerData)
	{
		this.controllerData = controllerData;
		//this.revalidate();
	    this.repaint();
	}

	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getPreferredScrollableViewportSize()
	 */
	public Dimension getPreferredScrollableViewportSize()
	{
		return new Dimension(this.controllerData.getFieldWidth(), 
							 this.controllerData.getFieldHeight());
	}

	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getScrollableUnitIncrement(java.awt.Rectangle, int, int)
	 */
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
	{
		return 1;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getScrollableBlockIncrement(java.awt.Rectangle, int, int)
	 */
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)
	{
		return 16;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getScrollableTracksViewportWidth()
	 */
	public boolean getScrollableTracksViewportWidth()
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getScrollableTracksViewportHeight()
	 */
	public boolean getScrollableTracksViewportHeight()
	{
		return false;
	}
}
