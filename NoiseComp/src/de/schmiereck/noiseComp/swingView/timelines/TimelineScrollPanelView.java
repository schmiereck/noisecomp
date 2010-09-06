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
public class TimelineScrollPanelView
extends JPanel
{
	//**********************************************************************************************
	// Fields:
	
	private Dimension dimension;
	
	private TimelinesRuleView columnView;
	private TimelinesRuleView rowView;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public TimelineScrollPanelView()
		throws HeadlessException
	{
		//==========================================================================================
//		this.setLayout(new BorderLayout());
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
	    
		this.dimension = new Dimension(2000, 400);
		
	    //------------------------------------------------------------------------------------------
		// Create the row and column headers.
	    this.columnView = new TimelinesRuleView(TimelinesRuleView.HORIZONTAL, true);
	    this.rowView = new TimelinesRuleView(TimelinesRuleView.VERTICAL, true);
	    
	    this.columnView.setPreferredWidth((int)this.dimension.getWidth());
	    this.rowView.setPreferredHeight((int)this.dimension.getHeight());

	    //------------------------------------------------------------------------------------------
	    TimelineDrawPanelView scrollPanelView = new TimelineDrawPanelView();
	    
	    scrollPanelView.setPreferredSize(this.dimension);
		
	    //------------------------------------------------------------------------------------------
	    JScrollPane scrollPane = new JScrollPane(scrollPanelView);
	    
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

