/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appModel.InputEntryGroupModel;
import de.schmiereck.noiseComp.swingView.appModel.InputEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.InputEntryTargetModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntriesModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel.HighlightedTimelineHandler;

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
	 * App Controller.
	 */
	private final AppController appController;
	
	/**
	 * Timelines-Draw Panel-Model.
	 */
	private final TimelinesDrawPanelModel timelinesDrawPanelModel;
	
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
	 * @param appController
	 * 			is the App Controller.
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
		this.appController = appController;
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
						TimelineSelectEntriesModel timelineSelectEntriesModel = 
							timelinesDrawPanelModel.getTimelineSelectEntriesModel();
						
						TimelineSelectEntryModel selectedTimelineSelectEntryModel = 
							selectedTimelineModel.getSelectedTimelineSelectEntryModel();
						
						timelineSelectEntriesModel.removeTimelineSelectEntryModel(timelinesDrawPanelModel,
						                                                          selectedTimelineSelectEntryModel);
						
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
			final InputEntryModel selectedInputEntry = selectedTimelineModel.getSelectedInputEntry();
			
			if (selectedInputEntry != null)
			{
				final InputEntryTargetModel inputEntryTargetModel = selectedTimelineModel.getInputEntryTargetModel();
				
				if (inputEntryTargetModel != null)
				{
					final InputPosEntriesModel inputPosEntriesModel = selectedTimelineModel.getInputPosEntriesModel();
					final TimelineSelectEntryModel selectedTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();
					final TimelineSelectEntryModel targetTimelineSelectEntryModel = inputEntryTargetModel.getTargetTimelineSelectEntryModel();
					
					final InputPosEntriesModel inputPosEntryModel = inputPosEntriesModel.searchInputPosEntry(selectedInputEntry);
					
					final InputEntryGroupModel inputEntryGroupModel = inputPosEntryModel.getInputEntryGroupModel();
					
					final InputTypeData inputTypeData = inputEntryGroupModel.getInputTypeData();
					
					if (selectedTimelineSelectEntryModel != null)
					{
						this.appController.doUpdateTimelineInput(selectedTimelineSelectEntryModel,
						                                         selectedInputEntry,
						                                         inputTypeData,
						                                         targetTimelineSelectEntryModel);
					}
					
					inputEntryTargetModel.setTargetTimelineSelectEntryModel(null);
				}
				
//				selectedTimelineModel.setHighlightedInputEntry(null);
//				selectedTimelineModel.setSelectedInputEntry(null);
				selectedTimelineModel.setInputEntryTargetModel(null);
			}
			else
			{
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
			}
			//--------------------------------------------------------------------------------------
		}
		//==========================================================================================
	}
}
