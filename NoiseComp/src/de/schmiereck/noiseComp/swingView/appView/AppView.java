/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appView;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import de.schmiereck.noiseComp.swingView.appModel.AppModel;
import de.schmiereck.noiseComp.swingView.inputEdit.InputEditView;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectView;
import de.schmiereck.noiseComp.swingView.modulEdit.ModulEditView;
import de.schmiereck.noiseComp.swingView.modulsTree.ModulesTreeView;
import de.schmiereck.noiseComp.swingView.timelineEdit.TimelineEditView;

/**
 * <p>
 * 	App View.
 * </p>
 * 
 * see: http://java.sun.com/docs/books/tutorial/uiswing/components/scrollpane.html
 * see: http://java.sun.com/docs/books/tutorial/uiswing/examples/components/index.html#ScrollDemo
 * see: http://download.oracle.com/javase/tutorial/uiswing/components/tree.html
 * 
 * @author smk
 * @version <p>17.06.2010:	created, smk</p>
 */
public class AppView
extends JFrame 
{
	//**********************************************************************************************
	// Fields:

	/**
	 * App Model
	 */
	@SuppressWarnings("unused")
	private final AppModel appModel;
	
	/**
	 * Menu-Bar: Main.
	 */
	private JMenuBar menuBarView = null;

	/**
	 * SubMenu Bar: File: File-Open.
	 */
	private JMenuItem fileOpenMenuItem = null;
	
	/**
	 * SubMenu Bar: File: File-Save.
	 */
	private JMenuItem fileSaveMenuItem = null;
	
	/**
	 * SubMenu Bar: File: Exit.
	 */
	private JMenuItem exitMenuItem = null;

	/**
	 * SubMenu Bar: Help: About.
	 */
	private JMenuItem helpAboutMenuItem = null;
	
	/**
	 * Button-Bar: File.
	 */
	private JToolBar fileToolBarView = null;
	
	/**
	 * Button: File: File-Open.
	 */
	private JButton fileOpenButtonView = null;
	
	/**
	 * Button: File: File-Save.
	 */
	private JButton fileSaveButtonView = null;
	
	/**
	 * Button: File: Exit.
	 */
	private JButton exitButtonView = null;
	
	/**
	 * Modules-Tree Scroll-Pane.
	 */
	private final JScrollPane modulesTreeScrollPane;
	
	/**
	 * Timeline-Edit Split-Pane.
	 */
	private final JSplitPane timelineEditSplitPane;
	
	/**
	 * Input Split-Pane.
	 */
	private final JSplitPane inputSplitPane;
	
	/**
	 * Modul-Timeline Split-Pane.
	 */
	private final JSplitPane modulSplitPane;
	
	/**
	 * Modul-Edit Split-Pane.
	 */
	private final JSplitPane modulEditSplitPane;
	
	/**
	 * Timeline-Edit Split-Pane.
	 */
	private final JSplitPane timelineSplitPane;

	private JButton playButton;

	private JButton	pauseButton;

	private JButton	stopButton;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * <pre>
	 * .-AppView-----------------------------------------------------------------------------.
	 * | .-modulSplitPane------------------------------------------------------------------. |
	 * | | .-modulesTreeScrollPane-. .-modulEditSplitPane--------------------------------. | |
	 * | | |.-modulesTreeView---------.| | .-modulEditPane---------------------------------. | | | 
	 * | | ||                     || | |                                               | | | |
	 * | | ||                     || | `-----------------------------------------------´ | | |
	 * | | ||                     || | ,-timelineSplitPane-----------------------------. | | |
	 * | | ||                     || | | .-TimelinesScrollPanel-. .-timelineEditView-. | | | |
	 * | | ||                     || | | |.-TimelinesDrawPanel-.| |                  | | | | |
	 * | | ||                     || | | ||                    || | inputSelect      | | | | |
	 * | | ||                     || | | ||                    || | inputEdit        | | | | |
	 * | | ||                     || | | |`--------------------´| |                  | | | | |
	 * | | ||                     || | | `----------------------´ `------------------´ | | | |
	 * | | |`---------------------´| | `-----------------------------------------------´ | | | 
	 * | | '-----------------------´ `---------------------------------------------------´ | |
	 * | `---------------------------------------------------------------------------------´ |
	 * `-------------------------------------------------------------------------------------´
	 * </pre>
	 * 
	 * @param appModel
	 * 			is the App Model.
	 * @throws HeadlessException
	 * 			if an Error occurse.
	 */
	public AppView(AppModel appModel)
		throws HeadlessException
	{
		//==========================================================================================
		this.appModel = appModel;
//		this.timelinesDrawPanelModel = timelinesDrawPanelModel;
		
		//==========================================================================================
		// Menu-Bar:
		{
			this.menuBarView = new JMenuBar();
		
			//JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		
			this.setJMenuBar(this.menuBarView);
			
			this.buildMenuBar();
		}
		//------------------------------------------------------------------------------------------
		// Tool-Bar Panel:
		
		JPanel toolBarPanel = new JPanel();
		
		toolBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.add(toolBarPanel, BorderLayout.PAGE_START);
		
		//------------------------------------------------------------------------------------------
		// File Tool-Bar:

		this.fileToolBarView = new JToolBar("File");
			
		this.buildFileToolBar(this.fileToolBarView);
		
//		this.add(this.toolBarView);
//		this.getContentPane().add(this.fileToolBarView, BorderLayout.NORTH);
		toolBarPanel.add(this.fileToolBarView, BorderLayout.PAGE_START);

		//------------------------------------------------------------------------------------------
		// http://download.oracle.com/javase/tutorial/uiswing/components/toolbar.html
		
		JToolBar playToolBar = new JToolBar("Play");
        
		this.buildPlayToolBar(playToolBar);
	    
		toolBarPanel.add(playToolBar, BorderLayout.PAGE_START);

		//------------------------------------------------------------------------------------------
		// Modul Select Panel:
		
		this.modulesTreeScrollPane = new JScrollPane();
		
		//------------------------------------------------------------------------------------------
		// Input Split-Pane:
		
		this.inputSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		this.inputSplitPane.setOneTouchExpandable(true);
		// All Space for Input-Select.
		this.inputSplitPane.setResizeWeight(1.0D);
		
		//------------------------------------------------------------------------------------------
		// Timeline-Edit Split-Pane:
		
		this.timelineEditSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		this.timelineEditSplitPane.setOneTouchExpandable(true);
		
		this.timelineEditSplitPane.setBottomComponent(this.inputSplitPane);
		
		//------------------------------------------------------------------------------------------
		// Timeline Split-Pane:
		
		this.timelineSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		this.timelineSplitPane.setOneTouchExpandable(true);
		
		this.timelineSplitPane.setRightComponent(this.timelineEditSplitPane);
		
		//------------------------------------------------------------------------------------------
		// Modul-Edit Split-Pane:
		
		this.modulEditSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		this.modulEditSplitPane.setOneTouchExpandable(true);
		
//		this.modulEditSplitPane.setTopComponent(new JLabel("modulEditPane"));
		this.modulEditSplitPane.setBottomComponent(this.timelineSplitPane);
		
		//------------------------------------------------------------------------------------------
		// Modul Split-Pane:
		
		this.modulSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		this.modulSplitPane.setOneTouchExpandable(true);
		this.modulSplitPane.setDividerLocation(150);
		
		this.add(this.modulSplitPane);

		this.modulSplitPane.setLeftComponent(this.modulesTreeScrollPane);
		this.modulSplitPane.setRightComponent(this.modulEditSplitPane);

		//------------------------------------------------------------------------------------------
//		this.add(this.timelinePanel);
		
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//==========================================================================================
	}

	public void setTimelineComponent(Component timelineComponent)
	{
		this.timelineSplitPane.setLeftComponent(timelineComponent);

//		this.timelineSplitPane.setDividerLocation(timelineComponent.getPreferredSize().width);
		this.timelineSplitPane.setDividerLocation(350);
	}

	/**
	 * @param timelineEditView 
	 * 			to set {@link #timelineEditView}.
	 */
	public void setTimelineEditView(TimelineEditView timelineEditView)
	{
		this.timelineEditSplitPane.setTopComponent(timelineEditView);
	}

	/**
	 * @param modulEditView 
	 * 			to set {@link #modulEditView}.
	 */
	public void setModulEditView(ModulEditView modulEditView)
	{
		this.modulEditSplitPane.setTopComponent(modulEditView);
	}

	/**
	 * @param inputSelectView 
	 * 			to set {@link #inputSelectView}.
	 */
	public void setInputSelectView(InputSelectView inputSelectView)
	{
		JScrollPane scrollpane = new JScrollPane(inputSelectView);

		int vScrollBarWidth = scrollpane.getVerticalScrollBar().getPreferredSize().width;
		Dimension dimTable = inputSelectView.getPreferredSize();
		dimTable.setSize(dimTable.getWidth() + vScrollBarWidth, 
		                 dimTable.getHeight());

//		scrollpane.setPreferredSize(new Dimension(dimTable.width, 100));
//		scrollpane.setMaximumSize(new Dimension(dimTable.width, 100));
//		scrollpane.setMinimumSize(new Dimension(dimTable.width, 100));
		
		scrollpane.setBorder(BorderFactory.createTitledBorder("Inputs:"));
		
//		JToolBar toolBar = new JToolBar();
//		
//		JButton button = this.makeNavigationButton("playIcon", 
//		                          //"PLAY_CMD",
//		                          "Play edited modul.",
//		                          "Play");
//	
//		toolBar.add(button);
//		
//		inputSelectView.add(toolBar, BorderLayout.PAGE_START);
		
		//------------------------------------------------------------------------------------------
//		JPanel panel = new JPanel();
//		
//		panel.add(scrollpane, BorderLayout.WEST);
				
		this.inputSplitPane.setTopComponent(scrollpane);
	}

	/**
	 * @param inputEditView 
	 * 			to set {@link #inputEditView}.
	 */
	public void setInputEditView(InputEditView inputEditView)
	{
		this.inputSplitPane.setBottomComponent(inputEditView);
	}
	
	/**
	 * @param modulesTreeView
	 */
	public void setModulesTreeView(ModulesTreeView modulesTreeView)
	{
//		this.modulesTreeScrollPane = new JScrollPane(this.modulesTreeView);
		this.modulesTreeScrollPane.setViewportView(modulesTreeView);
	}

	/**
	 * @param toolBar
	 * 			is the play Tool-bar.
	 */
	private final void buildPlayToolBar(JToolBar toolBar)
	{
		//==========================================================================================
		// Play-Button:
		{
			this.playButton = 
				this.makeNavigationButton("playIcon", 
				                          //"PLAY_CMD",
				                          "Play edited modul.",
				                          "Play");
			
		    toolBar.add(this.playButton);
		}
		//------------------------------------------------------------------------------------------
		// Pause-Button:
		{
			this.pauseButton = 
				this.makeNavigationButton("pauseIcon", 
				                          //"PAUSE_CMD",
				                          "Pause edited modul.",
				                          "Pause");
			
		    toolBar.add(this.pauseButton);
		}
		//------------------------------------------------------------------------------------------
		// Stop-Button:
		{
			this.stopButton = 
				this.makeNavigationButton("stopIcon", 
				                          //"STOP_CMD",
				                          "Stop edited modul.",
				                          "Stop");
			
		    toolBar.add(this.stopButton);
		}
		//==========================================================================================
	}
	
	private JButton makeNavigationButton(String imageName,
	                                     //String actionCommand,
	                                     String toolTipText,
	                                     String altText) 
	{
		//==========================================================================================
	    // Look for the image.
	    String imgLocation = "images/" + imageName + ".gif";
	    
	    URL imageURL = AppView.class.getResource(imgLocation);

	    // Create and initialize the button.
	    JButton button = new JButton();
	    //button.setActionCommand(actionCommand);
	    button.setToolTipText(toolTipText);
	    //button.addActionListener(this);

	    // Image found?
	    if (imageURL != null) 
	    {
	    	button.setIcon(new ImageIcon(imageURL, altText));
	    } 
	    else 
	    {
	    	// No Image found:
	        button.setText(altText);
	        System.err.println("Resource not found: " + imgLocation);
	    }

		//==========================================================================================
	    return button;
	}

	public JButton getPlayButton()
	{
		return this.playButton;
	}

	public JButton getPauseButton()
	{
		return this.pauseButton;
	}

	public JButton getStopButton()
	{
		return this.stopButton;
	}
	
	/**
	 * Create Main-Menu.
	 */
	public void buildMenuBar(/*ActionListener actionListener, ItemListener itemListener*/)
	{
		{
			//======================================================================
			// File:

			JMenu menu = new JMenu("File");
			menu.setMnemonic(KeyEvent.VK_F);
			this.menuBarView.add(menu);
	
			//----------------------------------------------------------------------
			// File-Open:
			this.fileOpenMenuItem = new JMenuItem();
			menu.add(this.fileOpenMenuItem);
	
			//----------------------------------------------------------------------
			// File-Save:
			this.fileSaveMenuItem = new JMenuItem();
			menu.add(this.fileSaveMenuItem);
			
			//----------------------------------------------------------------------
			menu.addSeparator();
			//----------------------------------------------------------------------
			// Exit:
			this.exitMenuItem = new JMenuItem(); //"Exit", KeyEvent.VK_X);
			//this.exitMenuItem.setActionCommand(MainMenuActionListener.CMD_EXIT);
			menu.add(this.exitMenuItem);
		}
		/*
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
		*/
		
		//======================================================================
		// Insert:
		//ClassLoader cl = this.getClass().getClassLoader(); 

		/*
		menu = new JMenu("Insert");
		menu.setMnemonic(KeyEvent.VK_I);
		this.menuBarView.add(menu);

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
		*/
		{
			//======================================================================
			// Help:
			
			JMenu menu = new JMenu("Help");
			menu.setMnemonic(KeyEvent.VK_I);
			this.menuBarView.add(menu);
	
			//----------------------------------------------------------------------
			this.helpAboutMenuItem = new JMenuItem();//"About...");
			//this.helpAboutMenuItem.addActionListener(actionListener);
			menu.add(this.helpAboutMenuItem);
		}
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
		menuBarView.add(menu);

*/
	}

	/**
	 * Aufbau der File Buttons für die angebenen Tool-Bar.
	 * 
	 * @param toolBarView
	 * 			ist die Tool-Bar.
	 */
	public void buildFileToolBar(JToolBar toolBarView)
	{
		//------------------------------------------------------------------------------------------
		{
			this.fileOpenButtonView = new JButton();//, new ImageIcon("images/select_scan_button_icon_e00.gif"));
			//this.selectSourceButtonView.setActionCommand(MainMenuActionListener.CMD_SELECT_SOURCE);
		    toolBarView.add(this.fileOpenButtonView);
		}
		//------------------------------------------------------------------------------------------
		{
			this.fileSaveButtonView = new JButton();//, new ImageIcon("images/select_scan_button_icon_e00.gif"));
			//this.selectSourceButtonView.setActionCommand(MainMenuActionListener.CMD_SELECT_SOURCE);
		    toolBarView.add(this.fileSaveButtonView);
		}
		//------------------------------------------------------------------------------------------
		{
			this.exitButtonView = new JButton();//, new ImageIcon("images/exit_button_icon_e00.gif"));
			//this.exitButtonView.setActionCommand(MainMenuActionListener.CMD_EXIT);
		    toolBarView.add(this.exitButtonView);
		}
	}

	/**
	 * @return 
	 * 			returns the {@link #menuBarView}.
	 */
	public JMenuBar getMenuBarView()
	{
		return this.menuBarView;
	}

	/**
	 * @return 
	 * 			returns the {@link #fileOpenMenuItem}.
	 */
	public JMenuItem getFileOpenMenuItem()
	{
		return this.fileOpenMenuItem;
	}

	/**
	 * @return 
	 * 			returns the {@link #fileSaveMenuItem}.
	 */
	public JMenuItem getFileSaveMenuItem()
	{
		return this.fileSaveMenuItem;
	}

	/**
	 * @return 
	 * 			returns the {@link #exitMenuItem}.
	 */
	public JMenuItem getExitMenuItem()
	{
		return this.exitMenuItem;
	}

	/**
	 * @return 
	 * 			returns the {@link #helpAboutMenuItem}.
	 */
	public JMenuItem getHelpAboutMenuItem()
	{
		return this.helpAboutMenuItem;
	}

	/**
	 * @return 
	 * 			returns the {@link #fileToolBarView}.
	 */
	public JToolBar getFileToolBarView()
	{
		return this.fileToolBarView;
	}

	/**
	 * @return 
	 * 			returns the {@link #fileOpenButtonView}.
	 */
	public JButton getFileOpenButtonView()
	{
		return this.fileOpenButtonView;
	}

	/**
	 * @return 
	 * 			returns the {@link #fileSaveButtonView}.
	 */
	public JButton getFileSaveButtonView()
	{
		return this.fileSaveButtonView;
	}

	/**
	 * @return 
	 * 			returns the {@link #exitButtonView}.
	 */
	public JButton getExitButtonView()
	{
		return this.exitButtonView;
	}

}
