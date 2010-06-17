/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView;

import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * <p>
 * 	App View.
 * </p>
 * 
 * see: http://java.sun.com/docs/books/tutorial/uiswing/components/scrollpane.html
 * see: http://java.sun.com/docs/books/tutorial/uiswing/examples/components/index.html#ScrollDemo
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
	 * Left Split Pane.
	 */
	private JSplitPane splitPane;

	private TimelineScrollPanelView timelineView;

	private JPanel editPane;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @throws HeadlessException
	 * 			if an Error occurse.
	 */
	public AppView()
		throws HeadlessException
	{
		//==========================================================================================
		this.timelineView = new TimelineScrollPanelView();

		//------------------------------------------------------------------------------------------
		// Left Split Pane:
		
		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
		                                this.timelineView, //,
		                                new JLabel("Label 1"));//this.editPane);
		
		this.splitPane.setOneTouchExpandable(true);
		  
		this.add(this.splitPane);

		this.splitPane.setDividerLocation(400);
		
	    //------------------------------------------------------------------------------------------
//	    this.add(this.timelinePanel);
	    
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		//==========================================================================================
	}
}
