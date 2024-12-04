/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesGeneratorsRule;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JComponent;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeInfoData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntriesModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelUtils;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesScrollPanel.TimelinesScrollPanelModel;
import de.schmiereck.noiseComp.swingView.utils.OutputUtils;
import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * <p>
 * 	Timelines-Generators-Rule View.
 * </p>
 * 
 * @author smk
 * @version <p>23.09.2010:	created, smk</p>
 */
public class TimelinesGeneratorsRuleView
extends JComponent {
	//**********************************************************************************************
	// Constats:

//	public static final int	DPI		= Toolkit.getDefaultToolkit().getScreenResolution();
	public static final int	SIZE_X	= 140;
	
	/**
	 * Color - Background (dirty brown/orange).
	 */
	private static final Color	COLOR_BACKGROUND			= new Color(0xFFF1E1); //230, 163, 4);
	private static final Color	COLOR_SELECTED_BACKGROUND	= new Color(0xBFB2AE);
	private static final Color	COLOR_SELECTED_BORDER		= new Color(0xAFA29E);
	private static final Color	COLOR_HIGHLIGHTED_BORDER	= new Color(0x8F827E);
	
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Timeline-Generators Rule Model.
	 */
	private final TimelinesGeneratorsRuleModel timelinesGeneratorsRuleModel;
	
	private final ModelPropertyChangedListener timelineGeneratorModelChangedListener;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelinesGeneratorsRuleModel
	 * 			is the Timeline-Generators Rule Model.
	 */
	public TimelinesGeneratorsRuleView(final TimelinesGeneratorsRuleModel timelinesGeneratorsRuleModel) {
		//==========================================================================================
		this.timelinesGeneratorsRuleModel = timelinesGeneratorsRuleModel;
		
		//------------------------------------------------------------------------------------------
		this.addMouseListener(
		 	new TimelinesGeneratorsRuleMouseListener(timelinesGeneratorsRuleModel, this)
		);
		this.addMouseMotionListener(
		 	new TimelinesGeneratorsRuleMouseMotionListener(timelinesGeneratorsRuleModel, this)
		);
		//------------------------------------------------------------------------------------------
		this.timelineGeneratorModelChangedListener = new ModelPropertyChangedListener() {
			@Override
			public void notifyModelPropertyChanged() {
				repaint();
			}
	 	};
		//==========================================================================================
	}

//	public void setGeneratorSizeY(int generatorSizeY)
//	{
//		this.generatorSizeY = generatorSizeY; //this.units / 2;
//		this.repaint();
//	}

	public void setHeight(final int ph) {
		//==========================================================================================
		final Dimension dimension = new Dimension(SIZE_X, ph);
		
//		this.setSize(dimension);
		this.setPreferredSize(dimension);
		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(final Graphics g) {
		//==========================================================================================
		final TimelinesScrollPanelModel timelinesScrollPanelModel = this.timelinesGeneratorsRuleModel.getTimelinesScrollPanelModel();

		final SelectedTimelineModel selectedTimelineModel = this.timelinesGeneratorsRuleModel.getSelectedTimelineModel();
		
		//==========================================================================================
		final Graphics2D g2 = (Graphics2D)g;
		   
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	   
		//------------------------------------------------------------------------------------------
		final Rectangle drawRectangle = g.getClipBounds();
		
		// Fill clipping area.
		g.setColor(COLOR_BACKGROUND);
		g.fillRect(drawRectangle.x, drawRectangle.y, drawRectangle.width, drawRectangle.height);

		final Font labelFont = new Font("SansSerif", Font.PLAIN, 11);
		final Font numFont = new Font("SansSerif", Font.PLAIN, 10);

		g.setColor(Color.BLACK);

		//------------------------------------------------------------------------------------------
		final TimelineSelectEntryModel selectedTimelineSelectEntryModel =
			selectedTimelineModel.getSelectedTimelineSelectEntryModel();

		final TimelineSelectEntryModel highlightedTimelineSelectEntryModel =
			this.timelinesGeneratorsRuleModel.getHighlightedTimelineSelectEntryModel();

		//------------------------------------------------------------------------------------------
		final TimelineSelectEntriesModel timelineSelectEntriesModel =
			this.timelinesGeneratorsRuleModel.getTimelineSelectEntriesModel();

		final List<TimelineSelectEntryModel> timelineSelectEntryModels =
			timelineSelectEntriesModel.getTimelineSelectEntryModelList();
		
		int yPosGenerator = 0;

		// ticks and labels
		for (int generatorPos = 0; generatorPos < timelineSelectEntryModels.size(); generatorPos++) {
		//for (int yPosTick = start; yPosTick < end; yPosTick += ySizeGenerator) {
			final TimelineSelectEntryModel timelineSelectEntryModel = timelineSelectEntryModels.get(generatorPos);

			final int ySizeGenerator = TimelinesDrawPanelUtils.calcYSizeGenerator(timelineSelectEntryModel);

			// Use clipping bounds to calculate first and last tick locations.
			if (((yPosGenerator + ySizeGenerator) > drawRectangle.y) &&
				(yPosGenerator < (drawRectangle.y + drawRectangle.height))) {
				final int yPosTick = yPosGenerator;
				final int yPosString;

				// Make a special case of 0 to display the number
				// within the rule and draw a units label.
//			if (yPosTick == 0)
//			{
//				yPosString = 10;
//			}
//			else
//			{
//				yPosString = yPosTick + 3;
//			}
				yPosString = yPosTick + 10;

				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				g.drawLine(0, yPosTick, SIZE_X - 1, yPosTick);

				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				final String generatorName;
				final String generatorTypeName;
				final float valueMax;
				final float valueMin;

				if (generatorPos < timelineSelectEntryModels.size()) {
					generatorName = timelineSelectEntryModel.getName();

					final Timeline timeline = timelineSelectEntryModel.getTimeline();

					if (timeline != null) {
						valueMax = timeline.getValueMax();
						valueMin = timeline.getValueMin();

						final Generator generator = timeline.getGenerator();

						if (generator != null) {
							final GeneratorTypeInfoData generatorTypeInfoData = generator.getGeneratorTypeData();

							if (generatorTypeInfoData != null) {
								generatorTypeName = generatorTypeInfoData.getGeneratorTypeName();
							} else {
								generatorTypeName = null;
							}
						} else {
							generatorTypeName = null;
						}
					} else {
						generatorTypeName = null;

						valueMax = 1.0F;
						valueMin = -1.0F;
					}

					// Selected timeline?
					if (selectedTimelineSelectEntryModel == timelineSelectEntryModel) {
						g.setColor(COLOR_SELECTED_BACKGROUND);
						g.fillRect(0, yPosTick,
								SIZE_X - 1, ySizeGenerator - 1);
						g.setColor(COLOR_SELECTED_BORDER);
						g.draw3DRect(0, yPosTick,
								SIZE_X - 1, ySizeGenerator - 1,
								true);
					}

					if (highlightedTimelineSelectEntryModel == timelineSelectEntryModel) {
						final boolean raised = true;

						g.setColor(COLOR_HIGHLIGHTED_BORDER);
						g.draw3DRect(0, yPosTick,
								SIZE_X - 1, ySizeGenerator - 1,
								raised);
					}
				} else {
					generatorName = "";
					generatorTypeName = null;

					valueMax = Float.NaN;
					valueMin = Float.NaN;
				}

				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				g.setFont(labelFont);
				g.setColor(Color.BLACK);

				{
					final String text = Integer.toString(generatorPos) + ":" + generatorName;

					g.drawString(text, 9, yPosString);
				}

				if (generatorTypeName != null) {
					g.drawString("(" + generatorTypeName + ")", 17, yPosString + 12);
				}

				g.setFont(numFont);
				{
					if (Float.isNaN(valueMax) == false) {
						final String text = OutputUtils.makeFloatText(valueMax, 2);
						final FontMetrics fm = getFontMetrics(g.getFont());
						final int stringWidth = fm.stringWidth(text);

						g.drawString(text,
								SIZE_X - stringWidth - 2,
								yPosString);
					}
				}
				{
					if ((Float.isNaN(valueMax) == false) ||
							(Float.isNaN(valueMin) == false)) {
						final String text = OutputUtils.makeFloatText(valueMin, 2);
						final FontMetrics fm = getFontMetrics(g.getFont());
						final int stringWidth = fm.stringWidth(text);
						Rectangle2D stringBoundsRect = fm.getStringBounds(text, g);
						final int stringHeight = stringBoundsRect.getBounds().height;

						g.drawString(text,
								SIZE_X - stringWidth - 2,
								yPosString + ySizeGenerator - stringHeight);
					}
				}
			}
			yPosGenerator += ySizeGenerator;
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineGeneratorModelChangedListener}.
	 */
	public ModelPropertyChangedListener getTimelineGeneratorModelChangedListener() {
		//==========================================================================================
		return this.timelineGeneratorModelChangedListener;
	}

	/**
	 * @param point2D
	 * 			is the point.
	 * @return
	 * 			is the Timeline at given point.
	 */
	public TimelineSelectEntryModel searchGenerator(final Point2D point2D) {
		//==========================================================================================
		final TimelineSelectEntriesModel timelineSelectEntriesModel = this.timelinesGeneratorsRuleModel.getTimelineSelectEntriesModel();

		//==========================================================================================
		TimelineSelectEntryModel retTimelineSelectEntryModel = null;
		double generatorPosY = 0.0D;
		
		for (final TimelineSelectEntryModel timelineSelectEntryModel : timelineSelectEntriesModel.getTimelineSelectEntryModelList()) {
			final int generatorSizeY = TimelinesDrawPanelUtils.calcYSizeGenerator(timelineSelectEntryModel);

			if ((point2D.getY() >= generatorPosY) &&
				(point2D.getY() <= (generatorPosY + generatorSizeY))) {
				retTimelineSelectEntryModel = timelineSelectEntryModel;
				break;
			}
			
			generatorPosY += generatorSizeY;
		}
		//==========================================================================================
		return retTimelineSelectEntryModel;
	}
}
