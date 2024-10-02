package de.schmiereck.screenTools.graphic;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.BufferCapabilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferStrategy;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.input.InputDeviceException;
import de.schmiereck.screenTools.input.InputMouseDevice;

/**
 * <p>
 * 	Stellt Funktionen zum zeichnen in einem Full-Screen zur Verfügung.
 * </p>
 * <p>
 * 	Einfache 2D-Zeichenfunktionen zum Zeichnen von scallierter 2D-Grafik.<br/>
 * 	Der Ursprung liegt im Mittelpunkt des Screens. 
 * </p>
 *
 * @author smk
 * @version 07.01.2004
 */
public abstract class MultiBufferFullScreenGraphic 
implements ScreenGraficInterface
{
	private static DisplayMode[] BEST_DISPLAY_MODES =
		new DisplayMode[] 
		{
			new DisplayMode(1024, 768, 32, 0),
			
			new DisplayMode(1280, 1024, 32, 0),
			new DisplayMode(1280, 768, 32, 0),
			new DisplayMode(1152, 864, 32, 0),

			new DisplayMode(640, 480, 32, 0),
			new DisplayMode(640, 480, 16, 0),

			new DisplayMode(1280, 1024, 8, 0),
			new DisplayMode(1024, 768, 8, 0),
			new DisplayMode(640, 480, 8, 0)
		};
	
	//private Window mainFrame = null;
	private Container mainFrame = null;

	private GraphicsDevice graphicsDevice = null;
	
	private Image transCursorImage = null;
	private Cursor transCursor = null;
	private String transCursorName = new String("transparentCursor");
	
	private BufferStrategy bufferStrategy = null;
	private Rectangle bounds = null;
	private int centerX = 0;
	private int centerY = 0;
	
	/**
	 * Scalierung der 2D-Grafik in X-Richtung.
	 */
	private double scaleX = 1;

	/**
	 * Scalierung der 2D-Grafik in Y-Richtung.
	 */
	private double scaleY = 1;

	/**
	 * Die aktuell angezeigte Buffer Nummer (0 oder 1).
	 */
	private int actualBufferNr = 0;
	
	/**
	 * Hintergrundfarbe.
	 */
	private Color fieldBackgroundColor = new Color(0xE2,0xFF,0xC4);
	
	private boolean useFullScreen;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public MultiBufferFullScreenGraphic(boolean useFullScreen)
	{
		super();

		this.useFullScreen = useFullScreen;
	}

	public static JFrame createFrameView(boolean useFullScreen)
	{
		JFrame frameView;
		
		if (useFullScreen == true)
		{
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice graphicsDevice = env.getDefaultScreenDevice();

			GraphicsConfiguration graphicsConfiguration = graphicsDevice.getDefaultConfiguration();

			frameView = new JFrame(graphicsConfiguration);

			frameView.setUndecorated(true);
		}
		else
		{
			frameView = new MainFrame(800, 600);

			//localMainFrame.buildMenuBar(null, null);
		}
		
		return frameView;
	}
	
	public void initGraphicBuffer()
	{
		Frame frameView = createFrameView(this.useFullScreen);
		
		this.initGraphicBuffer(frameView);
	}
	
	public void initGraphicBuffer(Container frameView)
	{
		if (this.useFullScreen == true)
		{
			// 2D:
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			this.graphicsDevice = env.getDefaultScreenDevice();

			this.mainFrame = frameView;

			this.mainFrame.setIgnoreRepaint(true);

			//this.mainApplet = this.mainFrame;
		}
		else
		{

			//GraphicView applet = new GraphicView(localMainFrame);
			{
				//Container cp = localMainFrame.getContentPane();
				//cp.add("Center", applet);
				//localMainFrame.add(applet);
			}
			
			this.mainFrame = frameView; //localMainFrame;
			//this.mainApplet = applet;
		}

		// Tabulator-Funktionen abschalten.
		this.mainFrame.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		this.mainFrame.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		this.mainFrame.setFocusTraversalKeys(KeyboardFocusManager.UP_CYCLE_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		this.mainFrame.setFocusTraversalKeys(KeyboardFocusManager.DOWN_CYCLE_TRAVERSAL_KEYS, Collections.EMPTY_SET);

		// 3D 1:
		//GraphicsConfigTemplate3D gct = new GraphicsConfigTemplate3D();
		//gct.setDoubleBuffer(GraphicsConfigTemplate3D.REQUIRED);

		//this.graphicsConfiguration = this.graphicsDevice.getBestConfiguration(gct);

		// 3D 2:
		//this.graphicsConfiguration     = SimpleUniverse.getPreferredConfiguration();

		/*
		GraphicsConfiguration graphicsConfiguration = this.mainFrame.getGraphicsConfiguration();
		
		BufferCapabilities bufCap = graphicsConfiguration.getBufferCapabilities();

		if (bufCap.isPageFlipping() == true) 
		{
			// Page flipping is supported
		} 
		else 
		{
			// Page flipping is not supported
		}
		*/
	}

	/**
	 * In den Fullscreen-Mode schalten.
	 * 
	 * @see #switchModeBack()
	 * 
	 * @param useFullScreen	
	 * 			true, wenn FullScreen Modus benutzt werden soll.
	 */
	public void switchModeToFullScreen(boolean useFullScreen)
	{
		this.mainFrame.requestFocus();

		if (useFullScreen == true)
		{
			this.graphicsDevice.setFullScreenWindow((Window)this.mainFrame);
		}
		else
		{
			/*
			this.mainFrame.setSize(640, 480);
			this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.mainFrame.pack();
			//this.mainFrame.setResizable(false);    // fixed size display
			*/
			this.mainFrame.setVisible(true);
		}
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		transCursorImage = toolkit.getImage("./1pxtrans_cursor.gif");
		MediaTracker mediaTracker = new MediaTracker(this.mainFrame);
		mediaTracker.addImage(transCursorImage, 0);
		try
		{
			mediaTracker.waitForID(0);
		}
		catch (InterruptedException ie)
		{
			System.err.println(ie);
			System.exit(1);
		}
		
		transCursor = toolkit.createCustomCursor(transCursorImage, new Point(0, 0), transCursorName);
		this.mainFrame.setCursor(transCursor);
		
		if (useFullScreen == true)
		{
			if (this.graphicsDevice.isDisplayChangeSupported())
			{
				this.chooseBestDisplayMode(graphicsDevice);
			}
		}
		
		if (this.mainFrame instanceof Window)
		{
			Window window = (Window)this.mainFrame;
			
			window.createBufferStrategy(2);

			this.bufferStrategy = window.getBufferStrategy();
		}
	}

	/**
	 * Call 
	 * AFTER {@link #initGraphicBuffer(boolean)} and
	 * AFTER {@link #switchModeToFullScreen(int, int, boolean, int, int)}.
	 *  
	 * @param fieldWidth	
	 * 			ist die Breite des Spielfeldes, dass gezeichnet werden soll.
	 * @param fieldHeight	
	 * 			ist die H�he des Spielfeldes, dass gezeichnet werden soll.
	 * @param centerDirX	
	 * 			-1:	X-Mittelpunkt sitzt links.<br/>	
	 * 			0:	X-Mittelpunkt sitzt in der Mitte.<br/>
	 * 			1:	X-Mittelpunkt sitzt rechts.
	 * @param centerDirY	
	 * 			-1:	Y-Mittelpunkt sitzt oben.<br/>
	 * 			0:	Y-Mittelpunkt sitzt in der Mitte.<br/>
	 * 			1:	Y-Mittelpunkt sitzt unten.
	 */
	public void initScreen(int fieldWidth, int fieldHeight,
						   int centerDirX, int centerDirY)
	{
		
		this.bounds = this.mainFrame.getBounds();
		Insets insets = this.mainFrame.getInsets();
		
		int height = this.bounds.height - (insets.top + insets.bottom);
		int width = this.bounds.width - (insets.left + insets.right);
		
		this.scaleX = ((double)fieldWidth) / width;
		this.scaleY = ((double)fieldHeight) / height;
		
		
		int halfX = (width / 2);
		int halfY = (height / 2);
		
		this.centerX = (int)((halfX + (centerDirX * halfX)) * this.scaleX) + insets.right;
		this.centerY = (int)((halfY + (centerDirY * halfY)) * this.scaleY) + insets.top;

		/*
		 Container c = this.mainFrame.getContentPane();
		 c.setLayout( new BorderLayout() );
		 
		 WrapCheckers3D w3d = new WrapCheckers3D();     // panel holding the 3D canvas
		 c.add(w3d, BorderLayout.CENTER);
		 */
		//this.build3D();
	}

	/**
	 * Vom Fullscreen wieder zur�ckschalten.
	 *
	 * @see #switchModeToFullScreen(GameControllerData, boolean)
	 */
	public void switchModeBack(boolean useFullScreen)
	{
		if (useFullScreen == true)
		{
			this.graphicsDevice.setFullScreenWindow(null);
		}
	}

	/**
	 * W�hle den f�r das Device am besten geeigneten Screen-Mode aus.
	 * 
	 * @see #getBestDisplayMode(GraphicsDevice)
	 * 
	 * @param device
	 */
	public void chooseBestDisplayMode(GraphicsDevice device)
	{
		
		DisplayMode best = this.getBestDisplayMode(device);
		if (best != null)
		{
			device.setDisplayMode(best);
		}
	}

	/**
	 * Ucht aus der Liste der f�r das Devive vorhandenen Modis den besten aus.
	 * 
	 * @param device
	 * @return
	 */
	private DisplayMode getBestDisplayMode(GraphicsDevice device)
	{
		for (int x = 0; x < BEST_DISPLAY_MODES.length; x++)
		{
			DisplayMode[] modes = device.getDisplayModes();
			for (int i = 0; i < modes.length; i++)
			{
				if (modes[i].getWidth() == BEST_DISPLAY_MODES[x].getWidth()
						&& modes[i].getHeight() == BEST_DISPLAY_MODES[x].getHeight()
						&& modes[i].getBitDepth()
						== BEST_DISPLAY_MODES[x].getBitDepth())
				{
					return BEST_DISPLAY_MODES[x];
				}
			}
		}
		return null;
	}

	/**
	 * NACH {@link #initGraphicBuffer()} aufrufen.
	 * 
	 */
	public void addInputListener(GraficInputListener inputListener, boolean useFullScreen) throws MultiBufferFullScreenGraphicException
	{
		InputMouseDevice inputDevice;
		
		try
		{
			if (useFullScreen == true)
			{
				inputDevice = new InputMouseDevice(this.graphicsDevice);
			}
			else
			{
				inputDevice = null;
			}
		}
		catch (InputDeviceException ex)
		{
			throw new MultiBufferFullScreenGraphicException("error while create input device", ex);
		}

		inputListener.setInputDevice(inputDevice);

		this.mainFrame.addMouseListener(inputListener);
		this.mainFrame.addMouseMotionListener(inputListener);
		this.mainFrame.addKeyListener(inputListener);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.ScreenGraficInterface#setFont(java.awt.Graphics, java.lang.String, int, int)
	 */
	public void setFont(Graphics g, String name, int style, int size)
	{
		Font font = new Font(name, style, (int)(size / this.scaleY));
		
		g.setFont(font);
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.ScreenGraficInterface#setColor(java.awt.Graphics, java.awt.Color)
	 */
	public void setColor(Graphics g, Color color)
	{
		g.setColor(color);
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.ScreenGraficInterface#drawCircle(java.awt.Graphics, int, int, int)
	 */
	public void drawCircle(Graphics g, int posX, int posY, int radius)
	{
		g.drawOval((int)((this.centerX + posX - radius) / this.scaleX), 
				(int)((this.centerY + posY - radius) / this.scaleY), 
				(int)((radius << 1) / this.scaleX), 
				(int)((radius << 1) / this.scaleY));
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.ScreenGraficInterface#fillCircle(java.awt.Graphics, int, int, int)
	 */
	public void fillCircle(Graphics g, int posX, int posY, int radius)
	{
		g.fillOval((int)((this.centerX + posX - radius) / scaleX), 
				(int)((this.centerY + posY - radius) / scaleY), 
				(int)((radius << 1) / scaleX), 
				(int)((radius << 1) / scaleY));
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.ScreenGraficInterface#drawLine(java.awt.Graphics, int, int, int, int)
	 */
	public void drawLine(Graphics g, int posX, int posY, int dirX, int dirY)
	{
		g.drawLine((int)((this.centerX + posX) / scaleX), 
				(int)((this.centerY + posY) / scaleY), 
				(int)((this.centerX + posX + dirX) / scaleX), 
				(int)((this.centerY + posY + dirY) / scaleY));
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.ScreenGraficInterface#drawAbsLine(java.awt.Graphics, int, int, int, int)
	 */
	public void drawAbsLine(Graphics g, int pos1X, int pos1Y, int pos2X, int pos2Y)
	{
		g.drawLine((int)((this.centerX + pos1X) / scaleX), 
				(int)((this.centerY + pos1Y) / scaleY), 
				(int)((this.centerX + pos2X) / scaleX), 
				(int)((this.centerY + pos2Y) / scaleY));
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.ScreenGraficInterface#drawArc(java.awt.Graphics, int, int, int, int)
	 */
	public void drawArc(Graphics g, int posX, int posY, int radius, int arc)
	{
		g.drawArc((int)((this.centerX + posX - radius) / scaleX), 
				(int)((this.centerY + posY - radius) / scaleY), 
				(int)((radius << 1) / scaleX), 
				(int)((radius << 1) / scaleY),
				0, 
				arc);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.ScreenGraficInterface#fillArc(java.awt.Graphics, int, int, int, int, int)
	 */
	public void fillArc(Graphics g, int posX, int posY, int radius, int startAngel, int arcAngel)
	{
		g.fillArc((int)((this.centerX + posX - radius) / scaleX), 
				(int)((this.centerY + posY - radius) / scaleY), 
				(int)((radius << 1) / scaleX), 
				(int)((radius << 1) / scaleY),
				startAngel, 
				arcAngel);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.ScreenGraficInterface#fillRect(java.awt.Graphics, int, int, int, int)
	 */
	public void fillRect(Graphics g, int posX, int posY, int width, int height)
	{
		if (width < 0)
		{
			width = -width;
			posX -= width;
		}
		g.fillRect((int)((this.centerX + posX) / scaleX), 
				(int)((this.centerY + posY) / scaleY), 
				(int)((width) / scaleX), 
				(int)((height) / scaleY));
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.ScreenGraficInterface#drawRect(java.awt.Graphics, int, int, int, int)
	 */
	public void drawRect(Graphics g, int posX, int posY, int width, int height)
	{
		if (width < 0)
		{
			width = -width;
			posX -= width;
		}
		g.drawRect((int)((this.centerX + posX) / scaleX), 
				(int)((this.centerY + posY) / scaleY), 
				(int)((width) / scaleX), 
				(int)((height) / scaleY));
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.ScreenGraficInterface#draw3DRect(java.awt.Graphics, int, int, int, int, boolean)
	 */
	public void draw3DRect(Graphics g, int posX, int posY, int width, int height, boolean raised)
	{
		if (width < 0)
		{
			width = -width;
			posX -= width;
		}
		g.draw3DRect((int)((this.centerX + posX) / scaleX), 
				(int)((this.centerY + posY) / scaleY), 
				(int)((width) / scaleX), 
				(int)((height) / scaleY), raised);
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.ScreenGraficInterface#drawString(java.awt.Graphics, int, int, java.lang.String)
	 */
	public void drawString(Graphics g, int posX, int posY, String text)
	{
		if (text != null)
		{	
			g.drawString(text, 
					(int)((this.centerX + posX) / this.scaleX), 
					(int)((this.centerY + posY) / this.scaleY));
		}
	}

	protected void drawStringAbsolutePos(Graphics g, int posX, int posY, String text)
	{
		if (text != null)
		{	
			g.drawString(text, 
					(int)(((posX)) / this.scaleX), 
					(int)((posY) / this.scaleY));
		}
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.ScreenGraficInterface#drawPoint(java.awt.Graphics, int, int, int)
	 */
	public void drawPoint(Graphics g, int posX, int posY, int size)
	{
		if (size <= 0)
		{	
			size = 1;
		}
		g.fillRect((int)((this.centerX + posX) / this.scaleX) - size / 2, 
				(int)((this.centerY + posY) / this.scaleY) - size / 2, size, size);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.ScreenGraficInterface#calcStringWidth(java.awt.Graphics, java.lang.String)
	 */
	public int calcStringWidth(Graphics g, String labelText)
	{
		FontMetrics fontMetrics = g.getFontMetrics();
		
		int stringWidth = (int)(fontMetrics.stringWidth(labelText) * this.scaleX);
		
		return stringWidth;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.ScreenGraficInterface#calcFontAscent(java.awt.Graphics)
	 */
	public int calcFontAscent(Graphics g)
	{
		FontMetrics fontMetrics = g.getFontMetrics();
		
		return (int)(fontMetrics.getAscent() * this.scaleX);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.graphic.ScreenGraficInterface#calcFontDescent(java.awt.Graphics)
	 */
	public int calcFontDescent(Graphics g)
	{
		FontMetrics fontMetrics = g.getFontMetrics();
		
		return (int)(fontMetrics.getDescent() * this.scaleX);
	}
	
	/**
	 * @param controllerData sind die Daten die gezeichnet werden sollen.
	 * @param actualWaitPerFramesMillis  sind die Millisekunden, die seit dem letzten Zeichnen vergangen sind.
	 */
	public void drawBuffer(ControllerData controllerData, long actualWaitPerFramesMillis)
	{
		if (this.bufferStrategy != null)
		{
			Graphics g = this.bufferStrategy.getDrawGraphics();

			if (this.bufferStrategy.contentsLost() == false)
			{
				//g.setColor(this.fieldBackgroundColor);
				//g.fillRect(0, 0, this.bounds.width, this.bounds.height);

				//------------------------------------------------------------------
				// Alle Zeichenoperationen der Anwendung werden hier vorgenommen:
				
				this.drawGraphic(g, 
								 controllerData, this.bounds, actualWaitPerFramesMillis);
				
				//------------------------------------------------------------------
				this.bufferStrategy.show();
			}

			g.dispose();

			if (this.actualBufferNr == 0)
			{
				this.actualBufferNr = 1;
			}
			else
			{
				this.actualBufferNr = 0;
			}
		}
		else
		{
			Graphics g = this.mainFrame.getGraphics();

			if (g != null)
			{
				this.drawGraphic(g, 
								 controllerData, this.bounds, actualWaitPerFramesMillis);

				g.dispose();
			}
		}
	}

	/**
	 * �berschreiben, um die aktuelle Grafik in den Puffer zu zeichnen.
	 * 
	 * @param g ist das Grafik-Device auf das Gezeichnet wird. 
	 * @param controllerData sind die Daten die gezeichnet werden sollen.
	 * @param bounds sind die Abmessungen der Zeichenfl�che auf die gezeichnet werden kann.
	 * @param actualWaitPerFramesMillis  sind die Millisekunden, die seit dem letzten Zeichnen vergangen sind.
	 */
	public abstract void drawGraphic(Graphics g, ControllerData controllerData, Rectangle bounds, long actualWaitPerFramesMillis);
	/**
	 * @return the attribute {@link #scaleX}.
	 */
	public double getScaleX()
	{
		return this.scaleX;
	}
	/**
	 * @return the attribute {@link #scaleY}.
	 */
	public double getScaleY()
	{
		return this.scaleY;
	}

	/**
	 * Anzeigen eines Popup-Fensters mit einer Fehlernachricht f�r den Benutzer.
	 * 
	 * @param message
	public void showErrorMessage(String message)
	{
		JOptionPane.showInternalMessageDialog(//this.mainContainer, 
											  this.mainFrame.getContentPane(),
											  "Message:\n" + message, 
											  "ERROR-Message",
											  JOptionPane.INFORMATION_MESSAGE);
	}
	 */

	/**
	 * @return
	 */
	public Container getApplet()
	{
		return this.mainFrame;//.getContentPane();
	}
	public int getCenterX()
	{
		return this.centerX;
	}
	public int getCenterY()
	{
		return this.centerY;
	}
	/**
	 * @return returns the {@link #mainFrame}.
	 */
	//public Window getMainFrame()
	//{
	//	return this.mainFrame;
	//}
	/**
	 * @return returns the {@link #bounds}.
	 */
	public Rectangle getBounds()
	{
		return this.bounds;
	}
}
