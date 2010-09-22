/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * <p>
 * 	Timeline Scroll-Panel.
 * </p>
 * 
 * see: http://java.sun.com/docs/books/tutorial/uiswing/components/scrollpane.html
 * 
 * @author smk
 * @version <p>17.06.2010:	created, smk</p>
 */
public class TimelinesScrollPanelView
extends JPanel
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Timelines Scroll-Panel Model.
	 */
	@SuppressWarnings("unused")
	private TimelinesScrollPanelModel timelinesScrollPanelModel;
	
	private TimelinesRuleView columnView;
	private TimelinesRuleView rowView;

	private JScrollPane scrollPane;
	
	private TimelinesDrawPanelView timelinesDrawPanelView = null;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelinesScrollPanelModel
	 * 			is the Timelines Scroll-Panel Model.
	 */
	public TimelinesScrollPanelView(TimelinesScrollPanelModel timelinesScrollPanelModel)
		throws HeadlessException
	{
		//==========================================================================================
		this.timelinesScrollPanelModel = timelinesScrollPanelModel;
		
		//==========================================================================================
//		this.setLayout(new BorderLayout());
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
	    
	    //------------------------------------------------------------------------------------------
		// Create the row and column headers.
	    this.columnView = new TimelinesRuleView(TimelinesRuleView.HORIZONTAL, true);
	    this.rowView = new TimelinesRuleView(TimelinesRuleView.VERTICAL, true);
	    
	    //------------------------------------------------------------------------------------------
	    this.scrollPane = new JScrollPane();
	    
//	    this.scrollPane.getViewport().add(scroll);
	    
	    this.scrollPane.setColumnHeaderView(this.columnView);
	    this.scrollPane.setRowHeaderView(this.rowView);
	    
	    this.scrollPane.setOpaque(true); //content panes must be opaque
	    
	    this.add(this.scrollPane, BorderLayout.CENTER);
		
	    //------------------------------------------------------------------------------------------
	    this.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

		//==========================================================================================
	}
	
	/**
	 * @param timelinesDrawPanelView 
	 * 			is the Timeline Draw-Panel View.
	 */
	public void setTimelinesDrawPanelView(TimelinesDrawPanelView timelinesDrawPanelView)
	{
		//==========================================================================================
		this.timelinesDrawPanelView = timelinesDrawPanelView;
	    
		this.scrollPane.setViewportView(this.timelinesDrawPanelView);
		
	    Dimension dimension = this.timelinesDrawPanelView.getDimension();

	    this.columnView.setPreferredWidth((int)dimension.getWidth());
	    this.rowView.setPreferredHeight((int)dimension.getHeight());
	    
//	    this.scrollPane.getViewport().revalidate();
//	    this.scrollPane.revalidate();
	    
		//==========================================================================================
	}
}		

