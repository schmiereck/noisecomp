/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelinesDrawPanelModel.HighlightedTimelineHandler;

/**
 * <p>
 * 	Timelines-Draw Mouse-Adapter.
 * </p>
 * 
 * @author smk
 * @version <p>21.02.2011:	created, smk</p>
 */
public class TimelinesDrawMouseAdapter
extends MouseAdapter 
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Timelines-Draw Panel-Model.
	 */
	final TimelinesDrawPanelModel timelinesDrawPanelModel;
	
	/**
	 * Timelines-Draw Panel-View.
	 */
	private final TimelinesDrawPanelView timelinesDrawPanelView;
	
	
	/**
	 * Generator Popup-Menu.
	 */
	private JPopupMenu generatorPopupMenu;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelinesDrawPanelController
	 * 			is the Timelines-Draw Panel-Controller.
	 * @param timelinesDrawPanelModel
	 * 			is the Timelines-Draw Panel-Model.
	 * @param timelinesDrawPanelView
	 * 			is the Timelines-Draw Panel-View.
	 */
	public TimelinesDrawMouseAdapter(final AppController appController,
	                                  final TimelinesDrawPanelController timelinesDrawPanelController,
	                                  final TimelinesDrawPanelModel timelinesDrawPanelModel,
	                                  final TimelinesDrawPanelView timelinesDrawPanelView)
	{
		//==========================================================================================
		this.timelinesDrawPanelModel = timelinesDrawPanelModel;
		this.timelinesDrawPanelView = timelinesDrawPanelView;
		
		final SelectedTimelineModel selectedTimelineModel = this.timelinesDrawPanelModel.getSelectedTimelineModel();
		
		//==========================================================================================
		// Generator Popup Menu:
		
		this.generatorPopupMenu = new JPopupMenu();
		//------------------------------------------------------------------------------------------
		// Double Timeline:
		{
			JMenuItem menuItem = new JMenuItem("Double Timeline");
			menuItem.addActionListener
			(
			 	new ActionListener()
			 	{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						//--------------------------------------------------------------------------
//						TimelineSelectEntryModel selectedTimelineSelectEntryModel = 
//							timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
						
						appController.doDoubleTimeline();
						
						//--------------------------------------------------------------------------
					}
			 	}
			);
			this.generatorPopupMenu.add(menuItem);
		}
		//------------------------------------------------------------------------------------------
		this.generatorPopupMenu.addSeparator();
		//------------------------------------------------------------------------------------------
		// Create Timeline:
		{
			JMenuItem menuItem = new JMenuItem("Create Timeline");
			menuItem.addActionListener
			(
			 	new ActionListener()
			 	{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						//--------------------------------------------------------------------------
//						TimelineSelectEntryModel selectedTimelineSelectEntryModel = 
//							timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
						
						appController.doCreateNewTimeline();
						
						//--------------------------------------------------------------------------
					}
			 	}
			);
			this.generatorPopupMenu.add(menuItem);
		}
		//------------------------------------------------------------------------------------------
		// Remove Timeline:
		{
			JMenuItem menuItem = new JMenuItem("Remove Timeline");
			menuItem.addActionListener
			(
			 	new ActionListener()
			 	{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						//--------------------------------------------------------------------------
						TimelineSelectEntryModel selectedTimelineSelectEntryModel = 
							selectedTimelineModel.getSelectedTimelineSelectEntryModel();
						
						timelinesDrawPanelModel.removeTimelineSelectEntryModel(selectedTimelineSelectEntryModel);
						
						//--------------------------------------------------------------------------
					}
			 	}
			);
			this.generatorPopupMenu.add(menuItem);
		}
		//==========================================================================================
	}
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		super.mouseClicked(e);
		//setActive(true);
		//Toolkit.getDefaultToolkit().

		HighlightedTimelineHandler timelineHandler = this.timelinesDrawPanelModel.getHighlightedTimelineHandler();
		
		if (timelineHandler != HighlightedTimelineHandler.NONE)
		{
			this.timelinesDrawPanelModel.setTimelineHandlerMoved(true);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		//==========================================================================================
		super.mouseReleased(e);

		//==========================================================================================
		final SelectedTimelineModel selectedTimelineModel = this.timelinesDrawPanelModel.getSelectedTimelineModel();
		
		//------------------------------------------------------------------------------------------
		// Popup?
		if (e.isPopupTrigger()) 
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			Point2D point2D = this.timelinesDrawPanelView.mousePos(e.getPoint());
			
			TimelineSelectEntryModel timelineSelectEntryModel = 
				this.timelinesDrawPanelView.searchGenerator(point2D);
			
			if (timelineSelectEntryModel != null)
			{
				selectedTimelineModel.setSelectedTimelineSelectEntryModel(timelineSelectEntryModel);
				
				this.generatorPopupMenu.show(e.getComponent(),
				                             e.getX(), e.getY());
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		else
		{
			//--------------------------------------------------------------------------------------
			if (this.timelinesDrawPanelModel.getTimelineHandlerMoved() == true)
			{
				this.timelinesDrawPanelModel.setTimelineHandlerMoved(false);
				
				TimelineSelectEntryModel highlightedTimelineSelectEntryModel = 
					this.timelinesDrawPanelModel.getHighlightedTimelineSelectEntryModel();
				
				if (highlightedTimelineSelectEntryModel != null)
				{
					HighlightedTimelineHandler timelineHandler = 
						this.timelinesDrawPanelModel.getHighlightedTimelineHandler();
					
					if (timelineHandler != HighlightedTimelineHandler.NONE)
					{
						switch (timelineHandler)
						{
							case LEFT:
							{
								this.timelinesDrawPanelModel.notifyTimelineStartTimePosChangedListeners(highlightedTimelineSelectEntryModel);
								this.timelinesDrawPanelModel.notifyTimelineEndTimePosChangedListeners(highlightedTimelineSelectEntryModel);
								this.timelinesDrawPanelModel.setTimelineHandlerMoved(false);
								this.timelinesDrawPanelModel.setNearestSnapToTimpePos(Double.NaN);
								break;
							}
							case RIGHT:
							{
								this.timelinesDrawPanelModel.notifyTimelineEndTimePosChangedListeners(highlightedTimelineSelectEntryModel);
								this.timelinesDrawPanelModel.setTimelineHandlerMoved(false);
								this.timelinesDrawPanelModel.setNearestSnapToTimpePos(Double.NaN);
								break;
							}
						}
					}
				}
			}
			//--------------------------------------------------------------------------------------
		}
		//==========================================================================================
	}
}
