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
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import de.schmiereck.noiseComp.swingView.appModel.AppModel;
import de.schmiereck.noiseComp.swingView.inputEdit.InputEditView;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectView;
import de.schmiereck.noiseComp.swingView.modulEdit.ModuleEditView;
import de.schmiereck.noiseComp.swingView.modulesTree.ModulesTreeView;
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
	 * SubMenu Bar: File: File-Open.
	 */
	private JMenuItem fileImportMenuItem = null;
	
	/**
	 * SubMenu Bar: Edit: Double-Timeline.
	 */
	private JMenuItem doubleTimelineMenuItem = null;
	
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
	 * ModuleTimeline Split-Pane.
	 */
	private final JSplitPane moduleplitPane;
	
	/**
	 * ModuleEdit Split-Pane.
	 */
	private final JSplitPane modulEditSplitPane;
	
	/**
	 * Timeline-Edit Split-Pane.
	 */
	private final JSplitPane timelineSplitPane;

	private JButton playButton;

	private JButton	pauseButton;

	private JButton	stopButton;
	
	private JCheckBox loopButton;
	
	private JButton	zoomInButton;

	private JButton	zoomOutButton;
	
	private JTextField ticksTextField;
	
	private JRadioButton ticksSecondsButton;
	
	private JRadioButton ticksMilliecondsButton;
	
	private JRadioButton ticksBpmButton;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * <pre>
	 * .-AppView-----------------------------------------------------------------------------.
	 * | .-moduleplitPane------------------------------------------------------------------. |
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
	public AppView(final AppModel appModel)
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
		// http://download.oracle.com/javase/tutorial/uiswing/components/toolbar.html
		
		JPanel toolBarPanel = new JPanel();
		
		toolBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.add(toolBarPanel, BorderLayout.PAGE_START);
		
		//------------------------------------------------------------------------------------------
		// File Tool-Bar:
		{
			this.fileToolBarView = new JToolBar("File");
				
			this.buildFileToolBar(this.fileToolBarView);
			
//			this.add(this.toolBarView);
//			this.getContentPane().add(this.fileToolBarView, BorderLayout.NORTH);
			toolBarPanel.add(this.fileToolBarView, BorderLayout.PAGE_START);
		}
		//------------------------------------------------------------------------------------------
		{
			JToolBar playToolBar = new JToolBar("Play");
	        
			this.buildPlayToolBar(playToolBar);
		    
			toolBarPanel.add(playToolBar, BorderLayout.PAGE_START);
		}
		//------------------------------------------------------------------------------------------
		{
			JToolBar zoomToolBar = new JToolBar("Zoom");
	        
			this.buildZoomToolBar(zoomToolBar);
		    
			toolBarPanel.add(zoomToolBar, BorderLayout.PAGE_START);
		}
		//------------------------------------------------------------------------------------------
		{
			JToolBar scaleToolBar = new JToolBar("Scale");
	        
			this.buildScaleToolBar(scaleToolBar);
		    
			toolBarPanel.add(scaleToolBar, BorderLayout.PAGE_START);
		}
		//------------------------------------------------------------------------------------------
		// Module Select Panel:
		
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

		// All Space for Timeline-Select.
		this.timelineSplitPane.setResizeWeight(1.0D);
		
		//------------------------------------------------------------------------------------------
		// ModuleEdit Split-Pane:
		
		this.modulEditSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		this.modulEditSplitPane.setOneTouchExpandable(true);
		
//		this.modulEditSplitPane.setTopComponent(new JLabel("modulEditPane"));
		this.modulEditSplitPane.setBottomComponent(this.timelineSplitPane);
		
		//------------------------------------------------------------------------------------------
		// Module Split-Pane:
		
		this.moduleplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		this.moduleplitPane.setOneTouchExpandable(true);
		this.moduleplitPane.setDividerLocation(150);
		
		this.add(this.moduleplitPane);

		this.moduleplitPane.setLeftComponent(this.modulesTreeScrollPane);
		this.moduleplitPane.setRightComponent(this.modulEditSplitPane);

		//==========================================================================================
	}

	public void setTimelineComponent(Component timelineComponent)
	{
		this.timelineSplitPane.setLeftComponent(timelineComponent);

//		this.timelineSplitPane.setDividerLocation(timelineComponent.getPreferredSize().width);
		this.timelineSplitPane.setDividerLocation(this.timelineSplitPane.getWidth() - 260);
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
	 * @param moduleEditView 
	 * 			to set {@link #modulEditView}.
	 */
	public void setModuleEditView(ModuleEditView moduleEditView)
	{
		this.modulEditSplitPane.setTopComponent(moduleEditView);
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
//		                          "Play edited module",
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
				                          "Play edited module",
				                          "Play");
			
		    toolBar.add(this.playButton);
		}
		//------------------------------------------------------------------------------------------
		// Pause-Button:
		{
			this.pauseButton = 
				this.makeNavigationButton("pauseIcon", 
				                          //"PAUSE_CMD",
				                          "Pause edited module",
				                          "Pause");
			
		    toolBar.add(this.pauseButton);
		}
		//------------------------------------------------------------------------------------------
		// Stop-Button:
		{
			this.stopButton = 
				this.makeNavigationButton("stopIcon", 
				                          //"STOP_CMD",
				                          "Stop edited module",
				                          "Stop");
			
		    toolBar.add(this.stopButton);
		}
		//------------------------------------------------------------------------------------------
		// Seconds:
		{
			this.loopButton = new JCheckBox();
			
			this.loopButton.setText("Loop");
			
			toolBar.add(this.loopButton);
			//buttonGroup.add(this.loopButton);
		}
		//==========================================================================================
	}
	
	/**
	 * @param toolBar
	 * 			is the zoom Tool-bar.
	 */
	private final void buildZoomToolBar(JToolBar toolBar)
	{
		//==========================================================================================
		{
			JLabel label = new JLabel("Zoom ");
			
			toolBar.add(label);
		}
		//------------------------------------------------------------------------------------------
		// Zoom-In-Button:
		{
			this.zoomInButton = 
				this.makeNavigationButton("zoomInIcon", 
				                          "Timelines zoom in.",
				                          "+");
			
		    toolBar.add(this.zoomInButton);
		}
		//------------------------------------------------------------------------------------------
		// Zoom-Out-Button:
		{
			this.zoomOutButton = 
				this.makeNavigationButton("zoomOutIcon", 
				                          "Timelines zoom out.",
				                          "-");
			
		    toolBar.add(this.zoomOutButton);
		}
		//==========================================================================================
	}
	
	/**
	 * @param toolBar
	 * 			is the scale Tool-bar.
	 */
	private final void buildScaleToolBar(JToolBar toolBar)
	{
		//==========================================================================================
		{
			JLabel label = new JLabel("Ticks ");
			
			toolBar.add(label);
		}
		{
			this.ticksTextField = new JTextField(10);
			
			toolBar.add(this.ticksTextField);
		}
		{
			JLabel label = new JLabel(" per ");
			
			toolBar.add(label);
		}
		//------------------------------------------------------------------------------------------
		ButtonGroup buttonGroup = new ButtonGroup();

		//------------------------------------------------------------------------------------------
		// Seconds:
		{
			this.ticksSecondsButton = new JRadioButton();
			
			this.ticksSecondsButton.setText("s ");
			
			toolBar.add(this.ticksSecondsButton);
			buttonGroup.add(this.ticksSecondsButton);
		}
		//------------------------------------------------------------------------------------------
		// Milliseconds:
		{
			this.ticksMilliecondsButton = new JRadioButton();
			
			this.ticksMilliecondsButton.setText("ms ");
			
			toolBar.add(this.ticksMilliecondsButton);
			buttonGroup.add(this.ticksMilliecondsButton);
		}
		//------------------------------------------------------------------------------------------
		// bpm:
		{
			this.ticksBpmButton = new JRadioButton();
			
			this.ticksBpmButton.setText("bpm ");
			
			toolBar.add(this.ticksBpmButton);
			buttonGroup.add(this.ticksBpmButton);
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

	/**
	 * @return 
	 * 			returns the {@link #playButton}.
	 */
	public JButton getPlayButton()
	{
		return this.playButton;
	}

	/**
	 * @return 
	 * 			returns the {@link #pauseButton}.
	 */
	public JButton getPauseButton()
	{
		return this.pauseButton;
	}

	/**
	 * @return 
	 * 			returns the {@link #stopButton}.
	 */
	public JButton getStopButton()
	{
		return this.stopButton;
	}
	
	/**
	 * Create Main-Menu.
	 */
	public void buildMenuBar(/*ActionListener actionListener, ItemListener itemListener*/)
	{
		//==========================================================================================
		// File:
		{
			JMenu menu = new JMenu("File");
			menu.setMnemonic(KeyEvent.VK_F);
			this.menuBarView.add(menu);
	
			//--------------------------------------------------------------------------------------
			// File-Open:
			this.fileOpenMenuItem = new JMenuItem();
			menu.add(this.fileOpenMenuItem);
	
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// File-Save:
			this.fileSaveMenuItem = new JMenuItem();
			menu.add(this.fileSaveMenuItem);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			menu.addSeparator();
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// File-Import:
			this.fileImportMenuItem = new JMenuItem();
			menu.add(this.fileImportMenuItem);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			menu.addSeparator();
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Exit:
			this.exitMenuItem = new JMenuItem(); //"Exit", KeyEvent.VK_X);
			//this.exitMenuItem.setActionCommand(MainMenuActionListener.CMD_EXIT);
			menu.add(this.exitMenuItem);
		}
		//------------------------------------------------------------------------------------------
		// Edit:
		{
			JMenu menu = new JMenu("Edit");
			menu.setMnemonic(KeyEvent.VK_E);
			this.menuBarView.add(menu);
	
			//--------------------------------------------------------------------------------------
			// Double Timeline:
			this.doubleTimelineMenuItem = new JMenuItem();
			menu.add(this.doubleTimelineMenuItem);
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
	 * 			returns the {@link #fileSaveMenuItem}.
	 */
	public JMenuItem getFileImportMenuItem()
	{
		return this.fileImportMenuItem;
	}

	/**
	 * @return 
	 * 			returns the {@link #doubleTimelineMenuItem}.
	 */
	public JMenuItem getDoubleTimelineMenuItem()
	{
		return this.doubleTimelineMenuItem;
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

	/**
	 * @return 
	 * 			returns the {@link #zoomInButton}.
	 */
	public JButton getZoomInButton()
	{
		return this.zoomInButton;
	}

	/**
	 * @return 
	 * 			returns the {@link #zoomOutButton}.
	 */
	public JButton getZoomOutButton()
	{
		return this.zoomOutButton;
	}

	/**
	 * @return 
	 * 			returns the {@link #ticksTextField}.
	 */
	public JTextField getTicksTextField()
	{
		return this.ticksTextField;
	}

	/**
	 * @return 
	 * 			returns the {@link #ticksSecondsButton}.
	 */
	public JRadioButton getTicksSecondsButton()
	{
		return this.ticksSecondsButton;
	}

	/**
	 * @return 
	 * 			returns the {@link #ticksMilliecondsButton}.
	 */
	public JRadioButton getTicksMilliecondsButton()
	{
		return this.ticksMilliecondsButton;
	}

	/**
	 * @return 
	 * 			returns the {@link #ticksBpmButton}.
	 */
	public JRadioButton getTicksBpmButton()
	{
		return this.ticksBpmButton;
	}

	/**
	 * @return 
	 * 			returns the {@link #loopButton}.
	 */
	public JCheckBox getLoopButton()
	{
		return this.loopButton;
	}

}
