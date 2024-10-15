/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import de.schmiereck.noiseComp.swingView.appModel.InputEntryGroupModel;
import de.schmiereck.noiseComp.swingView.appModel.InputEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.InputEntryTargetModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelineHandler.TimelineHandlerModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel.HighlightedTimelineHandler;
import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * <p>
 * 	Timelines-Draw Mouse-Motion-Listerner.
 * </p>
 * 
 * @author smk
 * @version <p>22.02.2011:	created, smk</p>
 */
public class TimelinesDrawMouseMotionListener
		extends MouseAdapter implements MouseMotionListener
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Timeline Draw-Panel Model. 
	 */
	private final TimelinesDrawPanelModel	timelinesDrawPanelModel;

	/**
	 * Timeline Draw-Panel View. 
	 */
	private final TimelinesDrawPanelView timelinesDrawPanelView;

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelinesDrawPanelModel
	 * 			is the Timeline Draw-Panel Model.
	 * @param timelinesDrawPanelView 
	 * 			is the Timeline Draw-Panel View.
	 */
	public TimelinesDrawMouseMotionListener(TimelinesDrawPanelModel timelinesDrawPanelModel, 
	                                         TimelinesDrawPanelView timelinesDrawPanelView)
	{
		//==========================================================================================
		this.timelinesDrawPanelModel = timelinesDrawPanelModel;
		this.timelinesDrawPanelView = timelinesDrawPanelView;
		
		//==========================================================================================
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (this.timelinesDrawPanelModel.getTimelineHandlerMoved()) {
			this.timelinesDrawPanelModel.setTimelineHandlerMoved(false);

			final TimelineSelectEntryModel highlightedTimelineSelectEntryModel =
					this.timelinesDrawPanelModel.getHighlightedTimelineSelectEntryModel();

			if (Objects.nonNull(highlightedTimelineSelectEntryModel)) {
				HighlightedTimelineHandler timelineHandler =
						this.timelinesDrawPanelModel.getHighlightedTimelineHandler();

				if (timelineHandler != HighlightedTimelineHandler.NONE) {
					switch (timelineHandler) {
						case LEFT: {
							this.timelinesDrawPanelModel.notifyTimelineStartTimePosChangedListeners(highlightedTimelineSelectEntryModel);
							this.timelinesDrawPanelModel.setTimelineHandlerMoved(false);
							this.timelinesDrawPanelModel.setNearestSnapToTimpePos(Double.NaN);
							break;
						}
						case RIGHT: {
							this.timelinesDrawPanelModel.notifyTimelineEndTimePosChangedListeners(highlightedTimelineSelectEntryModel);
							this.timelinesDrawPanelModel.setTimelineHandlerMoved(false);
							this.timelinesDrawPanelModel.setNearestSnapToTimpePos(Double.NaN);
							break;
						}
					}
				}
			}
		}
		if (this.timelinesDrawPanelModel.getTimelineMoved()) {
			final TimelineSelectEntryModel highlightedTimelineSelectEntryModel =
					this.timelinesDrawPanelModel.getHighlightedTimelineSelectEntryModel();

			if (Objects.nonNull(highlightedTimelineSelectEntryModel)) {
				this.timelinesDrawPanelModel.notifyTimelineStartTimePosChangedListeners(highlightedTimelineSelectEntryModel);
				this.timelinesDrawPanelModel.notifyTimelineEndTimePosChangedListeners(highlightedTimelineSelectEntryModel);
				this.timelinesDrawPanelModel.setNearestSnapToTimpePos(Double.NaN);
			}
			this.timelinesDrawPanelModel.setTimelineMoved(false);
		}
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		//==========================================================================================
		final SelectedTimelineModel selectedTimelineModel = this.timelinesDrawPanelModel.getSelectedTimelineModel();
		
		final Point mousePoint = e.getPoint();
		final Point2D mousePoint2D = this.timelinesDrawPanelView.mousePos(mousePoint);
		
		final HighlightedTimelineHandler timelineHandler = this.timelinesDrawPanelModel.getHighlightedTimelineHandler();
		
		// Timeline Handler dragged?
		if (timelineHandler != HighlightedTimelineHandler.NONE) {
			this.dragTimelineHandler(selectedTimelineModel, mousePoint2D, timelineHandler);
		} else {
			final InputEntryModel selectedInputEntry = selectedTimelineModel.getSelectedInputEntry();
			
			if (Objects.nonNull(selectedInputEntry)) {
				this.dragInput(selectedTimelineModel, mousePoint2D, mousePoint, selectedInputEntry);
			} else {
				final TimelineSelectEntryModel selectedTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();

				if (Objects.nonNull(selectedTimelineSelectEntryModel)) {
					final TimelineSelectEntryModel targetTimelineSelectEntryModel = this.timelinesDrawPanelView.searchTimeline(mousePoint2D);

					// Drag Up/Down to other Timeline?
					if (Objects.nonNull(targetTimelineSelectEntryModel) &&
							(targetTimelineSelectEntryModel != selectedTimelineSelectEntryModel)) {
						this.dragTimelineListPosition(selectedTimelineModel,
								selectedTimelineSelectEntryModel, targetTimelineSelectEntryModel, mousePoint2D);
					} else {
						this.dragTimeline(selectedTimelineSelectEntryModel, mousePoint2D);
					}
				}
			}
		}
		//==========================================================================================
	}

	private void dragTimeline(final TimelineSelectEntryModel selectedTimelineSelectEntryModel, final Point2D mousePoint2D) {
		final double mouseTimePos = mousePoint2D.getX();

		final Timeline timeline = selectedTimelineSelectEntryModel.getTimeline();

		final float startTimePos = timeline.getGeneratorStartTimePos();
		final float endTimePos = timeline.getGeneratorEndTimePos();

		if (!this.timelinesDrawPanelModel.getTimelineMoved()) {
			this.timelinesDrawPanelModel.setTimelineMoved(true);
			this.timelinesDrawPanelModel.setDragOffsetX(mouseTimePos - startTimePos);
		}

		//final double dragOffsetX = mouseTimePos - startTimePos;
		final double timePos = mouseTimePos - this.timelinesDrawPanelModel.getDragOffsetX();

		final double nearestSnapToTimePos =
				this.timelinesDrawPanelView.searchNearestSnapToTimePos(this.timelinesDrawPanelModel,
						selectedTimelineSelectEntryModel, timePos);

		final AffineTransform at = this.timelinesDrawPanelView.getAt();

		final boolean handlerSnaped;
		final double pos;
		final double snapDif = Math.abs(nearestSnapToTimePos - timePos);
		final double d = snapDif * at.getScaleX();

		if (d < 6.0D) {
			handlerSnaped = true;
			pos = Math.max(0.0D, nearestSnapToTimePos);
		} else {
			handlerSnaped = false;
			pos = Math.max(0.0D, timePos);
		}
		//System.out.println("timePos:" + timePos +
		//		", dragOffsetX:" +  + this.timelinesDrawPanelModel.getDragOffsetX() +
		//		", nearestSnapToTimePos:" +  + nearestSnapToTimePos +
		//		", d: " + d +
		//		", pos:" + pos +
		//		", " + at.getScaleX());

		this.timelinesDrawPanelModel.setHandlerSnaped(handlerSnaped);
		this.timelinesDrawPanelModel.setNearestSnapToTimpePos(nearestSnapToTimePos);

		final float lengthTime = endTimePos - startTimePos;

		selectedTimelineSelectEntryModel.setStartTimePos((float)pos);
		selectedTimelineSelectEntryModel.setEndTimePos((float)pos + lengthTime);
		//this.timelinesDrawPanelModel.setTimelineHandlerMoved(true);
		this.timelinesDrawPanelView.repaint();
	}

	private void dragTimelineListPosition(final SelectedTimelineModel selectedTimelineModel,
										  final TimelineSelectEntryModel selectedTimelineSelectEntryModel,
										  final TimelineSelectEntryModel targetTimelineSelectEntryModel,
										  final Point2D mousePoint2D) {
		if (targetTimelineSelectEntryModel != selectedTimelineSelectEntryModel) {
			selectedTimelineModel.notifyDoChangeTimelinesPositionListeners(selectedTimelineSelectEntryModel,
					targetTimelineSelectEntryModel);
		}
		final AffineTransform at = this.timelinesDrawPanelView.getAt();

		final Rectangle rect = new Rectangle((int) (mousePoint2D.getX() * at.getScaleX() - 32),
				(int) (mousePoint2D.getY() - 32),
				64, 64);

		this.timelinesDrawPanelView.scrollRectToVisible(rect);
	}

	private void dragInput(final SelectedTimelineModel selectedTimelineModel,
						   final Point2D mousePoint2D, final Point mousePoint, final InputEntryModel selectedInputEntry) {
		final TimelineSelectEntryModel selectedTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();

		final InputEntryTargetModel selectedInputEntryTargetModel;
		final InputEntryTargetModel inputEntryTargetModel = selectedTimelineModel.getInputEntryTargetModel();

		if (Objects.nonNull(inputEntryTargetModel)) {
			selectedInputEntryTargetModel = inputEntryTargetModel;
		} else {
			selectedInputEntryTargetModel = new InputEntryTargetModel();

			selectedTimelineModel.setInputEntryTargetModel(selectedInputEntryTargetModel);
		}

		final TimelineSelectEntryModel targetTimelineSelectEntryModel =
			this.timelinesDrawPanelView.searchTimeline(mousePoint2D);

		selectedInputEntryTargetModel.setTargetPoint2D(mousePoint2D);
		selectedInputEntryTargetModel.setTargetTimelineSelectEntryModel(targetTimelineSelectEntryModel);

		final Timeline selectedTimeline = selectedTimelineSelectEntryModel.getTimeline();

		if (Objects.nonNull(targetTimelineSelectEntryModel)) {
			final Timeline targetTimeline = targetTimelineSelectEntryModel.getTimeline();

			// Selected and target input timeline are not the same?
			if (selectedTimeline != targetTimeline) {
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Check if is input is disabled.
				// Output generators of edited generator is already an input generator?
				if ((selectedTimeline.checkIsOutputTimeline(targetTimeline))) {
					selectedInputEntryTargetModel.setTargetEnabled(false);
				} else {
					selectedInputEntryTargetModel.setTargetEnabled(true);
				}
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			} else {
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Selected and target input timeline are the same:
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				selectedInputEntryTargetModel.setTargetEnabled(false);

				this.changeInputPositions(selectedTimelineModel,
						mousePoint, mousePoint2D,
						selectedInputEntry);

				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			}
		}
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		this.timelinesDrawPanelView.repaint();
	}

	private void dragTimelineHandler(final SelectedTimelineModel selectedTimelineModel,
									 final Point2D mousePoint2D, final HighlightedTimelineHandler timelineHandler) {
		final TimelineSelectEntryModel selectedTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();
		//TimelineSelectEntryModel highlightedTimelineSelectEntryModel = timelinesDrawPanelModel.getHighlightedTimelineSelectEntryModel();

		if (selectedTimelineSelectEntryModel != null) {
			final AffineTransform at = this.timelinesDrawPanelView.getAt();
			final double timePos = mousePoint2D.getX();

			final double nearestSnapToTimpePos =
				this.timelinesDrawPanelView.searchNearestSnapToTimePos(this.timelinesDrawPanelModel,
						selectedTimelineSelectEntryModel, timePos);
//				System.out.println(nearestSnapToTimpePos);

			final boolean handlerSnaped;
			final double pos;
			final double snapDif = Math.abs(timePos - nearestSnapToTimpePos);
			final double d = snapDif * at.getScaleX();
//				System.out.println(d + ", " + at.getScaleX());

			if (d < 6.0D) {
				handlerSnaped = true;
				pos = Math.max(0.0D, nearestSnapToTimpePos);
			} else {
				handlerSnaped = false;
				pos = Math.max(0.0D, timePos);
			}

			this.timelinesDrawPanelModel.setHandlerSnaped(handlerSnaped);
			this.timelinesDrawPanelModel.setNearestSnapToTimpePos(nearestSnapToTimpePos);

			switch (timelineHandler) {
				case LEFT: {
					selectedTimelineSelectEntryModel.setStartTimePos((float)pos);
					this.timelinesDrawPanelModel.setTimelineHandlerMoved(true);
					this.timelinesDrawPanelView.repaint();
					break;
				}
				case RIGHT: {
					selectedTimelineSelectEntryModel.setEndTimePos((float)pos);
					this.timelinesDrawPanelModel.setTimelineHandlerMoved(true);
					this.timelinesDrawPanelView.repaint();
					break;
				}
			}

			{
//					        JViewport vport = scrollPane.getViewport();
//					        Point viewPos = vport.getViewPosition();
//					        Dimension size = vport.getExtentSize();
//					        int vportx = viewPos.x;
//					        int vporty = viewPos.y;
//					        int dx = evt.getX() - startX;
//					        int dy = evt.getY() - startY;
//
//					        int newvportx = vportx - dx;
//					        int newvporty = vporty - dy;
//					        Rectangle rect = new Rectangle(newvportx, newvporty, size.width, size.height);
//					        mPanel.scrollRectToVisible(rect);

				final Rectangle rect = new Rectangle((int)(mousePoint2D.getX() * at.getScaleX() - 32),
											   (int)(mousePoint2D.getY() - 32),
											   64, 64);

				this.timelinesDrawPanelView.scrollRectToVisible(rect);
			}
		}
	}

	/**
	 * Change Input positions of inputs of selected timeline in same group by drag and drop.
	 */
	private void changeInputPositions(final SelectedTimelineModel selectedTimelineModel, 
	                                  final Point point,
	                                  final Point2D point2D, 
	                                  final InputEntryModel selectedInputEntry) {
		//==========================================================================================
		final InputPosEntriesModel targetInputPosEntryModel = 
			this.timelinesDrawPanelView.searchInputEntry(selectedTimelineModel,
			                                             point,
			                                             point2D);

		// Found Target Input?
		if (targetInputPosEntryModel != null) {
			final InputEntryGroupModel selectedInputEntryGroupModel = selectedInputEntry.getInputEntryGroupModel();
			final InputEntryGroupModel targetInputEntryGroupModel = targetInputPosEntryModel.getInputEntryGroupModel();
			
			// Same group?
			if (selectedInputEntryGroupModel == targetInputEntryGroupModel) {
				final InputPosEntriesModel timelineInputPosEntriesModel =
					selectedTimelineModel.getInputPosEntriesModel();

				final InputPosEntriesModel selectedInputPosEntryModel =
					timelineInputPosEntriesModel.searchInputPosEntry(selectedInputEntry);
				
				// Not the same input entry?
				if (selectedInputPosEntryModel != targetInputPosEntryModel) {
					final InputEntryModel selectedInputEntryModel = selectedInputPosEntryModel.getInputEntryModel();
					final InputEntryModel targetInputEntryModel = targetInputPosEntryModel.getInputEntryModel();
					
					if ((selectedInputEntryModel.getInputData() != null) &&
						(targetInputEntryModel.getInputData() != null)) {
						// Change input positions.
						
						selectedTimelineModel.changeInputPositions(selectedInputEntryGroupModel,
						                                           selectedInputEntryModel,
						                                           targetInputEntryModel);
					}
				}
			}
		}
		//==========================================================================================
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//==========================================================================================
		Point point = e.getPoint();
		Point2D point2D = this.timelinesDrawPanelView.mousePos(point);
		
		//------------------------------------------------------------------------------------------
		this.updateTimelineHandlers(point, 
		                            point2D);
		
		//------------------------------------------------------------------------------------------
		this.updateInputHandler(point, 
	                            point2D);
		
		//==========================================================================================
	}

	/**
	 * @param point
	 * 			is the mouse position.
	 * @param point2D
	 * 			is the scaled mouse position.
	 */
	private void updateTimelineHandlers(final Point point,
	                                    final Point2D point2D) {
		//==========================================================================================
		final AffineTransform at = this.timelinesDrawPanelView.getAt();
		
		//==========================================================================================
		boolean resetHighlightedTimelineHandler;
		
		TimelineSelectEntryModel timelineSelectEntryModel =  this.timelinesDrawPanelView.searchGenerator(point2D);
		
		//------------------------------------------------------------------------------------------
		if (this.timelinesDrawPanelModel.getHighlightedTimelineSelectEntryModel() != timelineSelectEntryModel) {
			this.timelinesDrawPanelModel.setHighlightedTimelineSelectEntryModel(timelineSelectEntryModel);
			this.timelinesDrawPanelView.repaint();
		}

		//------------------------------------------------------------------------------------------
		if (timelineSelectEntryModel != null) {
			float maxUnitIncrementY = this.timelinesDrawPanelModel.getMaxUnitIncrementY();
			
			int timelineGeneratorPos = (int)(point2D.getY() / maxUnitIncrementY);
			
			float startTimePos = timelineSelectEntryModel.getStartTimePos();
			float endTimePos = timelineSelectEntryModel.getEndTimePos();
			float generatorPosY = timelineGeneratorPos * maxUnitIncrementY;
			
			float timeLength = endTimePos - startTimePos;
			
			Rectangle2D rectangle = new Rectangle2D.Float(startTimePos,
			                                              generatorPosY,
			                                              timeLength,
			                                              maxUnitIncrementY);
			
			Shape shape = at.createTransformedShape(rectangle);
			
			Rectangle2D bounds2D = shape.getBounds2D();
			
			TimelineHandlerModel timelineHandlerModel = 
				this.timelinesDrawPanelView.makeTimelineHandlerModel(bounds2D);
			
			if (timelineHandlerModel.getRect1().contains(point)) {
				if (this.timelinesDrawPanelModel.getHighlightedTimelineHandler() != HighlightedTimelineHandler.LEFT) {
					this.timelinesDrawPanelModel.setHighlightedTimelineHandler(HighlightedTimelineHandler.LEFT);
					this.timelinesDrawPanelView.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
				}
				resetHighlightedTimelineHandler = false;
			} else {
				if (timelineHandlerModel.getRect2().contains(point)) {
					if (this.timelinesDrawPanelModel.getHighlightedTimelineHandler() != HighlightedTimelineHandler.RIGHT) {
						this.timelinesDrawPanelModel.setHighlightedTimelineHandler(HighlightedTimelineHandler.RIGHT);
						this.timelinesDrawPanelView.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
					}
					resetHighlightedTimelineHandler = false;
				} else {
					resetHighlightedTimelineHandler = true;
				}
			}
		} else {
			resetHighlightedTimelineHandler = true;
		}
		
		if (resetHighlightedTimelineHandler) {
			if (this.timelinesDrawPanelModel.getHighlightedTimelineHandler() != HighlightedTimelineHandler.NONE) {
				this.timelinesDrawPanelModel.setHighlightedTimelineHandler(HighlightedTimelineHandler.NONE);
				this.timelinesDrawPanelView.setCursor(this.timelinesDrawPanelView.getDefaultCursor());
			}
		}
		//==========================================================================================
	}

	/**
	 * @param point
	 * 			is the mouse position.
	 * @param point2D
	 * 			is the mouse position.
	 */
	private void updateInputHandler(final Point point, final Point2D point2D) {
		//==========================================================================================
		final SelectedTimelineModel selectedTimelineModel =  this.timelinesDrawPanelModel.getSelectedTimelineModel();
		
		//==========================================================================================
		InputPosEntriesModel inputPosEntryModel = 
			this.timelinesDrawPanelView.searchInputEntry(selectedTimelineModel,
			                                             point,
			                                             point2D);

		if (inputPosEntryModel != null) {
			InputEntryModel inputEntryModel = inputPosEntryModel.getInputEntryModel();
			
			if (selectedTimelineModel.getHighlightedInputEntry() != inputEntryModel) {
				selectedTimelineModel.setHighlightedInputEntry(inputEntryModel);
			}
		} else {
			selectedTimelineModel.setHighlightedInputEntry(null);
		}
		//==========================================================================================
	}
}
