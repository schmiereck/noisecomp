/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appView;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
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
	private final AppModel appModel;
	
//	/**
//	 * Timeline Draw Panel Model.
//	 */
//	private final TimelinesDrawPanelModel timelinesDrawPanelModel;
	
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
		// http://download.oracle.com/javase/tutorial/uiswing/components/toolbar.html
		
		JToolBar playToolBar = new JToolBar("Play");
        
		this.addPlayButtons(playToolBar);
	    
		this.add(playToolBar, BorderLayout.PAGE_START);

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
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
	private final void addPlayButtons(JToolBar toolBar)
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
}
