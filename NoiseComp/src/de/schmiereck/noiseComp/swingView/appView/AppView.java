/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appView;

import java.awt.Component;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import de.schmiereck.noiseComp.swingView.appModel.AppModel;
import de.schmiereck.noiseComp.swingView.appModel.EditModuleChangedListener;
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
	private AppModel appModel;
	
//	/**
//	 * Timeline Draw Panel Model.
//	 */
//	private TimelinesDrawPanelModel timelinesDrawPanelModel;
	
	/**
	 * Modules-Tree Scroll-Pane.
	 */
	private JScrollPane modulesTreeScrollPane;
	
	/**
	 * Modul-Timeline Split Pane.
	 */
	private JSplitPane modulSplitPane;
	
	/**
	 * Modul-Edit Split Pane.
	 */
	private JSplitPane modulEditSplitPane;
	
	/**
	 * Timeline-Edit Split Pane.
	 */
	private JSplitPane timelineSplitPane;
	
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
	 * | | ||                     || | | ||                    || |                  | | | | |
	 * | | ||                     || | | ||                    || |                  | | | | |
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
		// Modul Select Panel:
		
		this.modulesTreeScrollPane = new JScrollPane();
		
		//------------------------------------------------------------------------------------------
		// Timeline Split-Pane:
		
		this.timelineSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			
		this.timelineSplitPane.setOneTouchExpandable(true);
		
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
//		this.timelineEditView = timelineEditView;
		
		this.timelineSplitPane.setRightComponent(timelineEditView);
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
	 * @param modulesTreeView
	 */
	public void setModulesTreeView(ModulesTreeView modulesTreeView)
	{
//		this.modulesTreeScrollPane = new JScrollPane(this.modulesTreeView);
		this.modulesTreeScrollPane.setViewportView(modulesTreeView);
	}
}
