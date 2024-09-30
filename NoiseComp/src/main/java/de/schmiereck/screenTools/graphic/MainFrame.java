package de.schmiereck.screenTools.graphic;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

/**
 * <p> Da es Probleme mit dem mischen von JMenuBar und JApplet gibt
 * (LightWeight- und HeavyWeight-Komponente), 
 * den setDefaultLightWeightPopupEnabled(false) Trick anwenden.<br>
 * @link http://java.sun.com/products/jfc/tsc/articles/mixing/index.html<br>
 * Sonst erscheint das PopupMenu als LightWeight-Komponente, wenn es innerhalb
 * des Frames dargestellt werden kann.</p>
 * General good infos:<br>
 * @link http://java.sun.com/docs/books/tutorial/uiswing/components/menu.html
 *
 * @author  smk
 * @version V1.0a
 */
public class MainFrame 
extends JFrame 
{
    private JMenuBar			menuBar; 

    public MainFrame(int sizeX, int sizeY) 
    {
        super();
        
			//------------------------------------------------------------------
			// Menu Bar:
		this.menuBar = new JMenuBar();
		
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		
		this.setJMenuBar(this.menuBar);

			//------------------------------------------------------------------
		Container cp = this.getContentPane();
		
		//BoxLayout lm = new BoxLayout(cp, BoxLayout.Y_AXIS) ;
		BorderLayout lm = new BorderLayout();
		
		cp.setLayout(lm);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//cp.add(menuBar);
		//cp.add("Center", statusView);

			//------------------------------------------------------------------
		//this.show();
        
			//------------------------------------------------------------------
		this.setSize(getInsets().left + 
					 getInsets().right + sizeX, //this.applet.getSize().width, //this.applet.getSizeX(),
					 getInsets().top + 
					 getInsets().bottom + sizeY); //this.applet.getSize().height); //this.applet.getSizeY() + statusView.getSizeY());
        /*
        this.addWindowListener(new java.awt.event.WindowAdapter() 
                          {
                              public void windowClosing(java.awt.event.WindowEvent evt) 
                              {
                                  exitForm(evt);
                              }
                          }
                         );
                         */
        
        this.setFileTitle(null);
    }
/*
    private void exitForm(java.awt.event.WindowEvent evt) 
    {
        System.exit(0);
    }
*/
	public void buildMenuBar(ActionListener actionListener, ItemListener itemListener)
	{
		JMenu		menu;
		JMenuItem	menuItem;

		//======================================================================
		// File:
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		this.menuBar.add(menu);

		//----------------------------------------------------------------------
		// New:
		menuItem = new JMenuItem("New", KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(actionListener);
		menuItem.setActionCommand("new");
		menu.add(menuItem);

		//----------------------------------------------------------------------
		// Open:
		menuItem = new JMenuItem("Open...", KeyEvent.VK_O);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(actionListener);
		menuItem.setActionCommand("open");
		menu.add(menuItem);

		//----------------------------------------------------------------------
		menu.addSeparator();
		//----------------------------------------------------------------------
		// Save as:
		menuItem = new JMenuItem("Save as...");
		menuItem.addActionListener(actionListener);
		menuItem.setActionCommand("saveAs");
		menu.add(menuItem);

		//----------------------------------------------------------------------
		// Save:
		menuItem = new JMenuItem("Save", KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(actionListener);
		menuItem.setActionCommand("save");
		menu.add(menuItem);

		//----------------------------------------------------------------------
		menu.addSeparator();
		//----------------------------------------------------------------------
		// Exit:
		menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(actionListener);
		menuItem.setActionCommand("exit");
		menu.add(menuItem);
		
		//======================================================================
		// Insert:
		ClassLoader cl = this.getClass().getClassLoader(); 

		menu = new JMenu("Insert");
		menu.setMnemonic(KeyEvent.VK_I);
		this.menuBar.add(menu);

		//----------------------------------------------------------------------
		menuItem = new JMenuItem("1: Water", 
		//new ImageIcon(cl.getResource("images/e00_ball01.gif")));
		new ImageIcon("images/e00_ball01.gif"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(actionListener);
		menuItem.setMnemonic(KeyEvent.VK_1);
		menu.add(menuItem);

		//----------------------------------------------------------------------
		menuItem = new JMenuItem("2: Goldnugget", 
		//new ImageIcon(cl.getResource("images/e00_ball03.gif")));
		new ImageIcon("images/e00_ball03.gif"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(actionListener);
		menuItem.setMnemonic(KeyEvent.VK_2);
		menu.add(menuItem);

		//======================================================================
		// Help:
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_I);
		this.menuBar.add(menu);

		//----------------------------------------------------------------------
		menuItem = new JMenuItem("About...");
		menuItem.addActionListener(actionListener);
		menu.add(menuItem);

		/*
//		  a group of radio button menu items
		menu.addSeparator();
		ButtonGroup group = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
		rbMenuItem.setSelected(true);
		rbMenuItem.setMnemonic(KeyEvent.VK_R);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Another one");
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

//		  a group of check box menu items
		menu.addSeparator();
		cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
		cbMenuItem.setMnemonic(KeyEvent.VK_C);
		menu.add(cbMenuItem);

		cbMenuItem = new JCheckBoxMenuItem("Another one");
		cbMenuItem.setMnemonic(KeyEvent.VK_H);
		menu.add(cbMenuItem);

//		  a submenu
		menu.addSeparator();
		submenu = new JMenu("A submenu");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("An item in the submenu");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_2, ActionEvent.ALT_MASK));
		submenu.add(menuItem);

		menuItem = new JMenuItem("Another item");
		submenu.add(menuItem);
		menu.add(submenu);

//		  Build second menu in the menu bar.
		menu = new JMenu("Another Menu");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
				"This menu does nothing");
		menuBar.add(menu);

*/
	}
	/**
	 * Method setFileTitle.
	 * @param string
	 */
	public void setFileTitle(String fileName)
	{
		String title;
		if (fileName != null)
		{
			title = fileName + " - ";
		}
		else
		{
			title = "Unnamed - ";
		}

		this.setTitle(title + "NoiseComp V1.0");
	}
}
