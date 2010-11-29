/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JComponent;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
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
extends JComponent
{
	//**********************************************************************************************
	// Constats:

//	public static final int	DPI		= Toolkit.getDefaultToolkit().getScreenResolution();
	public static final int	SIZE_X	= 120;
	
	/**
	 * Color - Background (dirty brown/orange).
	 */
	private static final Color	COLOR_BACKGROUND	= new Color(0xFFF1E1); //230, 163, 4);
	private static final Color	COLOR_SELECTED_BACKGROUND	= new Color(0xBFB2AE);
	
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
	public TimelinesGeneratorsRuleView(TimelinesGeneratorsRuleModel timelinesGeneratorsRuleModel)
	{
		//==========================================================================================
		this.timelinesGeneratorsRuleModel = timelinesGeneratorsRuleModel;
		
		//------------------------------------------------------------------------------------------
		this.timelineGeneratorModelChangedListener = new ModelPropertyChangedListener()
	 	{
			@Override
			public void notifyModelPropertyChanged()
			{
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

	public void setHeight(int ph)
	{
		//==========================================================================================
		Dimension dimension = new Dimension(SIZE_X, ph);
		
//		this.setSize(dimension);
		this.setPreferredSize(dimension);
		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g)
	{
		//==========================================================================================
		TimelinesScrollPanelModel timelinesScrollPanelModel = this.timelinesGeneratorsRuleModel.getTimelinesScrollPanelModel();
		
		//==========================================================================================
		Rectangle drawHere = g.getClipBounds();
		
		// Fill clipping area.
		g.setColor(COLOR_BACKGROUND);
		g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);
		
		// Do the ruler labels in a small font that's black.
		g.setFont(new Font("SansSerif", Font.PLAIN, 10));
		g.setColor(Color.BLACK);
		
		int generatorSizeY = timelinesScrollPanelModel.getGeneratorSizeY();

		//------------------------------------------------------------------------------------------
		List<TimelineSelectEntryModel> timelineSelectEntryModels = this.timelinesGeneratorsRuleModel.getTimelineSelectEntryModels();
		
		TimelineSelectEntryModel selectedTimelineSelectEntryModel = this.timelinesGeneratorsRuleModel.getSelectedTimelineSelectEntryModel();
		
		// Use clipping bounds to calculate first and last tick locations.
		int start = (drawHere.y / generatorSizeY) * generatorSizeY;
		int end = (((drawHere.y + drawHere.height) / generatorSizeY) + 1) * generatorSizeY;

		// ticks and labels
		for (int tickPos = start; tickPos < end; tickPos += generatorSizeY)
		{
			int stringPosY;
			
			// Make a special case of 0 to display the number
			// within the rule and draw a units label.
//			if (tickPos == 0)
//			{
//				stringPosY = 10;
//			}
//			else
//			{
//				stringPosY = tickPos + 3;
//			}
			stringPosY = tickPos + 10;
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			int tickLength = 10;
			
			g.drawLine(SIZE_X - 1, tickPos, SIZE_X - tickLength - 1, tickPos);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			int generatorPos = tickPos / generatorSizeY;
			
			String generatorName;
			String generatorTypeName;
			float valueMax;
			float valueMin;
			
			if (generatorPos < timelineSelectEntryModels.size())
			{
				TimelineSelectEntryModel timelineSelectEntryModel = timelineSelectEntryModels.get(generatorPos);
				
				generatorName = timelineSelectEntryModel.getName();

				Timeline timeline = timelineSelectEntryModel.getTimeline();
				
				if (timeline != null)
				{
					valueMax = timeline.getValueMax();
					valueMin = timeline.getValueMin();
					
					Generator generator = timeline.getGenerator();
					
					if (generator != null)
					{
						GeneratorTypeData generatorTypeData = generator.getGeneratorTypeData();
						
						if (generatorTypeData != null)
						{
							generatorTypeName = generatorTypeData.getGeneratorTypeName();
						}
						else
						{
							generatorTypeName = null;
						}
					}
					else
					{
						generatorTypeName = null;
					}
				}
				else
				{
					generatorTypeName = null;
					
					valueMax = 1.0F;
					valueMin = -1.0F;
				}
				
				// Selected timeline?
				if (selectedTimelineSelectEntryModel == timelineSelectEntryModel)
				{
					g.setColor(COLOR_SELECTED_BACKGROUND);
					g.fillRect(0, tickPos, 
					           SIZE_X, generatorSizeY);
				}
			}
			else
			{
				generatorName = "";
				generatorTypeName = null;
				
				valueMax = 1.0F;
				valueMin = -1.0F;
			}
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			g.setColor(Color.BLACK);
			
			{
				String text = Integer.toString(generatorPos) + ":" + generatorName;
				
				g.drawString(text, 9, stringPosY);
			}
			
			if (generatorTypeName != null)
			{
				g.drawString("(" + generatorTypeName + ")", 17, stringPosY + 10);
			}
			
			{
				String text = OutputUtils.makeFloatText(valueMax, 2);
			
				FontMetrics fm = getFontMetrics(g.getFont());
				
				int stringWidth = fm.stringWidth(text);
				
				g.drawString(text, SIZE_X - stringWidth, stringPosY);
			}
			{
				String text = OutputUtils.makeFloatText(valueMin, 2);
			
				FontMetrics fm = getFontMetrics(g.getFont());
				
				int stringWidth = fm.stringWidth(text);
				
				g.drawString(text, SIZE_X - stringWidth, (int)(stringPosY + TimelinesDrawPanelModel.SIZE_TIMELINE_Y));
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineGeneratorModelChangedListener}.
	 */
	public ModelPropertyChangedListener getTimelineGeneratorModelChangedListener()
	{
		//==========================================================================================
		return this.timelineGeneratorModelChangedListener;
	}
}
