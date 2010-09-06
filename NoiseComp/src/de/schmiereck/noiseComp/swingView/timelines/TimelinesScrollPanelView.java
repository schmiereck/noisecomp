/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelines;

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
	
	private TimelinesRuleView columnView;
	private TimelinesRuleView rowView;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelinesDrawPanelView 
	 * 			is the Timeline Draw-Panel View.
	 */
	public TimelinesScrollPanelView(TimelinesDrawPanelView timelinesDrawPanelView)
		throws HeadlessException
	{
		//==========================================================================================
//		this.setLayout(new BorderLayout());
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
	    
	    //------------------------------------------------------------------------------------------
		// Create the row and column headers.
	    this.columnView = new TimelinesRuleView(TimelinesRuleView.HORIZONTAL, true);
	    this.rowView = new TimelinesRuleView(TimelinesRuleView.VERTICAL, true);
	    
	    Dimension dimension = timelinesDrawPanelView.getDimension();
	    
	    this.columnView.setPreferredWidth((int)dimension.getWidth());
	    this.rowView.setPreferredHeight((int)dimension.getHeight());

	    //------------------------------------------------------------------------------------------
	    JScrollPane scrollPane = new JScrollPane(timelinesDrawPanelView);
	    
//	    scrollPane.getViewport().add(scroll);
	    
	    scrollPane.setColumnHeaderView(this.columnView);
	    scrollPane.setRowHeaderView(this.rowView);
	    
	    scrollPane.getViewport().revalidate();
	    
	    scrollPane.setOpaque(true); //content panes must be opaque
	    
	    this.add(scrollPane, BorderLayout.CENTER);
		
	    //------------------------------------------------------------------------------------------
	    this.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

		//==========================================================================================
	}
}		

