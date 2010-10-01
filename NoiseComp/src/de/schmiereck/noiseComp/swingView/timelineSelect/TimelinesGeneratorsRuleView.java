/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JComponent;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;

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
	public static final int	SIZE	= 80;
	
	/**
	 * Color - Background (dirty brown/orange).
	 */
	private static final Color	COLOR_BACKGROUND	= new Color(230, 163, 4);
	
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Timeline-Generators Rule Model.
	 */
	private final TimelinesGeneratorsRuleModel timelinesGeneratorsRuleModel;
	
	private final ModelPropertyChangedListener timelineGeneratorModelChangedListener =
	 	new ModelPropertyChangedListener()
 	{
		@Override
		public void notifyModelPropertyChanged()
		{
			repaint();
		}
 	};
	
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
		this.timelinesGeneratorsRuleModel = timelinesGeneratorsRuleModel;
	}

//	public void setGeneratorSizeY(int generatorSizeY)
//	{
//		this.generatorSizeY = generatorSizeY; //this.units / 2;
//		this.repaint();
//	}

	public void setHeight(int ph)
	{
		Dimension dimension = new Dimension(SIZE, ph);
		
//		this.setSize(dimension);
		this.setPreferredSize(dimension);
	}

	protected void paintComponent(Graphics g)
	{
		Rectangle drawHere = g.getClipBounds();
		
		// Fill clipping area.
		g.setColor(COLOR_BACKGROUND);
		g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);
		
		// Do the ruler labels in a small font that's black.
		g.setFont(new Font("SansSerif", Font.PLAIN, 10));
		g.setColor(Color.BLACK);
		
		TimelinesScrollPanelModel timelinesScrollPanelModel = this.timelinesGeneratorsRuleModel.getTimelinesScrollPanelModel();
		
		int generatorSizeY = timelinesScrollPanelModel.getGeneratorSizeY();

		List<TimelineGeneratorModel> timelineGeneratorModels = this.timelinesGeneratorsRuleModel.getTimelineGeneratorModels();
		
		// Use clipping bounds to calculate first and last tick locations.
		int start = (drawHere.y / generatorSizeY) * generatorSizeY;
		int end = (((drawHere.y + drawHere.height) / generatorSizeY) + 1) * generatorSizeY;

		// ticks and labels
		for (int tickPos = start; tickPos < end; tickPos += generatorSizeY)
		{
			int stringPosY;
			
			// Make a special case of 0 to display the number
			// within the rule and draw a units label.
			if (tickPos == 0)
			{
				stringPosY = 10;
			}
			else
			{
				stringPosY = tickPos + 3;
			}
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			int tickLength = 10;
			
			g.drawLine(SIZE - 1, tickPos, SIZE - tickLength - 1, tickPos);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			int generatorPos = tickPos / generatorSizeY;
			
			String generatorName;
			String generatorTypeName;
			
			if (generatorPos < timelineGeneratorModels.size())
			{
				TimelineGeneratorModel timelineGeneratorModel = timelineGeneratorModels.get(generatorPos);
				
				generatorName = timelineGeneratorModel.getName();

				Generator generator = timelineGeneratorModel.getGenerator();
				
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
				generatorName = "";
				generatorTypeName = null;
			}
			
			String text = Integer.toString(generatorPos) + " " + generatorName;
			
			g.drawString(text, 9, stringPosY);
			
			if (generatorTypeName != null)
			{
				g.drawString("(" + generatorTypeName + ")", 9, stringPosY + 10);
			}
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineGeneratorModelChangedListener}.
	 */
	public ModelPropertyChangedListener getTimelineGeneratorModelChangedListener()
	{
		return this.timelineGeneratorModelChangedListener;
	}
}
