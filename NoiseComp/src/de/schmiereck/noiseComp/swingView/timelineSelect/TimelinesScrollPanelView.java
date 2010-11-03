/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.BorderFactory;
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
extends JScrollPane
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Timelines Scroll-Panel Model.
	 */
	@SuppressWarnings("unused")
	private final TimelinesScrollPanelModel timelinesScrollPanelModel;
	
	private TimelinesTimeRuleView timelinesTimeRuleView = null;
	private TimelinesGeneratorsRuleView timelinesGeneratorsRuleView = null;

//	private JScrollPane scrollPane;
	
	private TimelinesDrawPanelView timelinesDrawPanelView = null;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelinesScrollPanelModel
	 * 			is the Timelines Scroll-Panel Model.
	 */
	public TimelinesScrollPanelView(final TimelinesScrollPanelModel timelinesScrollPanelModel)
		throws HeadlessException
	{
		//==========================================================================================
		this.timelinesScrollPanelModel = timelinesScrollPanelModel;
		
		//==========================================================================================
//		this.setLayout(new BorderLayout());
//		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
	    
	    //------------------------------------------------------------------------------------------
		// Create the row and column headers.
//	    this.columnView = new TimelinesTimeRuleView(true);
	    
//	    TimelinesGeneratorsRuleModel timelinesGeneratorsRuleModel = 
//	    	new TimelinesGeneratorsRuleModel(this.timelinesScrollPanelModel.getGeneratorSizeY());
//	    this.rowView = new TimelinesGeneratorsRuleView(timelinesGeneratorsRuleModel);
	    
	    //------------------------------------------------------------------------------------------
//	    this.scrollPane = new JScrollPane();
	    
//	    this.scrollPane.getViewport().add(scroll);
	    
//	    this.scrollPane.setColumnHeaderView(this.timelinesTimeRuleView);
//	    this.scrollPane.setRowHeaderView(this.rowView);
	    
//	    this.scrollPane.setOpaque(true); //content panes must be opaque
	    this.setOpaque(true); //content panes must be opaque
	    
//	    this.add(this.scrollPane, BorderLayout.CENTER);
		
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
	    
//		this.scrollPane.setViewportView(this.timelinesDrawPanelView);
		this.setViewportView(this.timelinesDrawPanelView);
		
	    Dimension dimension = this.timelinesDrawPanelView.getTimelinesDrawPanelModel().getDimension();

	    this.timelinesTimeRuleView.setWidth((int)dimension.getWidth());
	    this.timelinesGeneratorsRuleView.setHeight((int)dimension.getHeight());
	    
//	    this.scrollPane.getViewport().revalidate();
//	    this.scrollPane.revalidate();
	    
		//==========================================================================================
	}

	/**
	 * @param timelinesTimeRuleView 
	 * 			to set {@link #timelinesTimeRuleView}.
	 */
	public void setTimelinesTimeRuleView(TimelinesTimeRuleView columnView)
	{
		//==========================================================================================
		this.timelinesTimeRuleView = columnView;

//	    this.scrollPane.setColumnHeaderView(this.timelinesTimeRuleView);
	    this.setColumnHeaderView(this.timelinesTimeRuleView);
	    
		//==========================================================================================
	}

	/**
	 * @param timelinesGeneratorsRuleView 
	 * 			to set {@link #timelinesGeneratorsRuleView}.
	 */
	public void setTimelinesGeneratorsRuleView(TimelinesGeneratorsRuleView timelinesGeneratorsRuleView)
	{
		this.timelinesGeneratorsRuleView = timelinesGeneratorsRuleView;

//	    this.scrollPane.setRowHeaderView(this.timelinesGeneratorsRuleView);
	    this.setRowHeaderView(this.timelinesGeneratorsRuleView);
	}

	/**
	 * @return 
	 * 			returns the {@link #timelinesScrollPanelModel}.
	 */
	public TimelinesScrollPanelModel getTimelinesScrollPanelModel()
	{
		return this.timelinesScrollPanelModel;
	}

//	/**
//	 * @return 
//	 * 			returns the {@link #scrollPane}.
//	 */
//	public JScrollPane getScrollPane()
//	{
//		return this.scrollPane;
//	}
}		

