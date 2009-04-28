package de.schmiereck.noiseComp.view.desctopPage.widgets.trackList;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.SoundSample;
import de.schmiereck.noiseComp.generator.TrackData;
import de.schmiereck.noiseComp.view.desctopPage.DesktopColors;
import de.schmiereck.noiseComp.view.desctopPage.widgets.ListWidgetData;
import de.schmiereck.noiseComp.view.desctopPage.widgets.ListWidgetGraphic;
import de.schmiereck.screenTools.graphic.ScreenGraficInterface;

/**
 * <p>
 * 	Drawing a widget to display a list of tracks.
 * </p>
 *
 * @author smk
 * @version 08.02.2004
 */
public class TracksListWidgetGraphic
extends ListWidgetGraphic
{
	private static Color dragColor1 = new Color(100, 100, 100, 75);
	private static Color dragColor2 = new Color(120, 120, 120);
	
	private static void drawGeneratorArea(Graphics g, ScreenGraficInterface screenGrafic, DesktopColors desktopColors,
	                                      TracksListWidgetData tracksData, Generator generator, 
	                                      int screenPosY, int trackHeight, 
	                                      float scrollStartTime, float scrollEndTime, 
	                                      boolean generatorIsActive, boolean generatorIsSelected)
	{
		// While calulate the scrolling, remember the part of the left row:
		
		ModulGenerator parentModulGenerator = null;	//TODO make it real, smk
		
		float generatorStartTime;
		
		if (generator.getStartTimePos() < scrollStartTime)
		{
			generatorStartTime = scrollStartTime;
		}
		else
		{
			generatorStartTime = generator.getStartTimePos();
		}

		// Is the generator visible and is he right side by the scroll-area ?
		if (generatorStartTime < scrollEndTime)
		{	
			// Beim Scrollen den rechts abgeschnittenen Teil ber�cksichtigen.
			float timeLength;
			
			if (generator.getEndTimePos() < scrollEndTime)
			{
				timeLength = generator.getEndTimePos() - generatorStartTime;
			}
			else
			{
				timeLength = scrollEndTime - generatorStartTime;
			}
			
			// Überhaupt noch etwas sichtbar oder alles nach links rausgescrollt ?
			if (timeLength > 0.0F)
			{		
				float scaleX = tracksData.getGeneratorScaleX();
				
				int screenPosX = tracksData.getGeneratorsLabelSizeX() + (int)((generatorStartTime - scrollStartTime) * scaleX);
				int screenSizeX = (int)(timeLength * scaleX);
				
				// Generator Area:
				if (generatorIsActive == true)
				{	
					screenGrafic.setColor(g, desktopColors.getActiveGeneratorBackgroundColor());
				}
				else
				{	
					if (generatorIsSelected == true)
					{
						screenGrafic.setColor(g, desktopColors.getSelectedGeneratorBackgroundColor());
					}
					else
					{
						screenGrafic.setColor(g, desktopColors.getInactiveGeneratorBackgroundColor());
					}
				}
				screenGrafic.fillRect(g, screenPosX, screenPosY, screenSizeX, trackHeight);
				
				// Samples:
				g.setColor(desktopColors.getSampleColor());
				
				float generatorSampleScale = generator.getGeneratorSampleDrawScale(parentModulGenerator);
				
				float frameRate = generator.getSoundFrameRate();
				boolean firstSample = true;
				int lastX = screenPosX;
				int lastY = screenPosY;
				
				//for (float timePos = 0; timePos < timeLength; timePos += (0.5F / scaleX))
				for (float timePos = 0; timePos < timeLength; timePos += (2.0F / scaleX))
				{
					// TODO hier auf den Track zugreifen, der soll einen Puffer verwalten.
					//SoundSample soundSample = null;//XXX generator.generateFrameSample((long)((generatorStartTime + timePos) * frameRate), parentModulGenerator);
					SoundSample soundSample = generator.generateFrameSample((long)((generatorStartTime + timePos) * frameRate), parentModulGenerator);
					
					if (soundSample != null)
					{	
						int samplePosX = screenPosX + (int)(timePos * scaleX);
						int samplePosY = screenPosY + 16 - (int)((soundSample.getMonoValue() * generatorSampleScale) * 16);
						if (firstSample == true)
						{
							firstSample = false;
						}
						else
						{	
							//g.setColor(Color.LIGHT_GRAY);
							//screenGrafic.drawAbsLine(g, lastX, lastY, samplePosX, samplePosY);
							g.setColor(desktopColors.getSampleColor());
							screenGrafic.drawPoint(g, lastX, lastY, 1);
						}
						lastX = samplePosX;
						lastY = samplePosY;
					}
				}
				
				/*
				 if (generator instanceof FaderGenerator)
				 {
				 FaderGenerator faderGenerator = (FaderGenerator)generator;
				 
				 int start = (int)(faderGenerator.getStartFadeValue() * generatorHeight);
				 int dif = (int)((faderGenerator.getEndFadeValue() - faderGenerator.getStartFadeValue()) * generatorHeight);
				 
				 g.setColor(Color.BLACK);
				 screenGrafic.drawLine(g, screenPosX, screenPosY + generatorHeight - start, 
				 screenSizeX, -dif);
				 }
				 */

				// Generator Start-Pos.
				g.setColor(Color.BLACK);
				screenGrafic.drawLine(g, screenPosX, screenPosY, 0, trackHeight);
				
				// Generator End-Pos.
				screenGrafic.drawLine(g, screenPosX + screenSizeX, screenPosY, 0, trackHeight);
				
				if (generatorIsSelected == true)
				{
					screenGrafic.setColor(g, desktopColors.getSelectedGeneratorSelectorColor());
					screenGrafic.fillRect(g, screenPosX, screenPosY, 8, 8);
					screenGrafic.fillRect(g, screenPosX + (screenSizeX - 8), screenPosY, 8, 8);
					screenGrafic.fillRect(g, screenPosX, screenPosY + (trackHeight - 8), 8, 8);
					screenGrafic.fillRect(g, screenPosX + (screenSizeX - 8), screenPosY + (trackHeight - 8), 8, 8);
					screenGrafic.drawRect(g, screenPosX, screenPosY, screenSizeX - 1, trackHeight - 1);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetGraphic#drawListEntry(java.awt.Graphics, de.schmiereck.screenTools.graphic.ScreenGraficInterface, de.schmiereck.noiseComp.desktopPage.DesktopColors, de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetData, java.lang.Object, int, int, int)
	 */
	public void drawListEntry(Graphics g, ScreenGraficInterface screenGrafic, DesktopColors desktopColors, 
							  ListWidgetData listWidgetData, Object entry, 
							  int entryPos, int screenPosY, int entryHeight,
							  float scrollStartTime, float scrollEndTime,
							  float verticalScrollerStart, float verticalScrollerEnd)
	{
		TracksListWidgetData tracksData = (TracksListWidgetData)listWidgetData;
		TrackData trackData = (TrackData)entry;
		
		int posX = listWidgetData.getPosX();
		int posY = listWidgetData.getPosY();
		int sizeX = listWidgetData.getSizeX();
		int sizeY = listWidgetData.getSizeY();

		boolean trackIsActive;
		boolean generatorIsActive;
		
		if (tracksData.getActiveTrackData() == trackData)
		{
			trackIsActive = true;

			if (tracksData.getHitGeneratorPart() != TracksListWidgetData.HIT_PART_NONE)
			{
				generatorIsActive = true;
			}
			else
			{
				generatorIsActive = false;
			}
		}
		else
		{
			trackIsActive = false;
			generatorIsActive = false;
		}
		
		boolean trackIsSelected;
		boolean generatorIsSelected;
		
		if (tracksData.getSelectedTrackData() == trackData)
		{
			trackIsSelected = true;
			
			if (tracksData.getGeneratorIsSelected() == true)
			{
				generatorIsSelected = true;
			}
			else
			{
				generatorIsSelected = false;
			}
		}
		else
		{
			trackIsSelected = false;
			generatorIsSelected = false;
		}

		Generator generator = trackData.getGenerator();
		
		if (trackIsSelected == true)
		{
			g.setColor(Color.GRAY);
			screenGrafic.fillRect(g, 0, screenPosY, tracksData.getGeneratorsLabelSizeX(), entryHeight);
		}

		// Bottom-Line.
		g.setColor(desktopColors.getGeneratorBottomLineColor());
		screenGrafic.drawLine(g, 0, screenPosY + entryHeight, sizeX, 0);
		
		// Label-Text.
		if (trackIsActive == true)
		{	
			screenGrafic.setColor(g, Color.WHITE);
		}
		else
		{
			screenGrafic.setColor(g, Color.BLACK);
		}
		screenGrafic.drawString(g, 10, screenPosY + entryHeight / 2 - 4, trackData.getName());
		screenGrafic.drawString(g, 10, screenPosY + entryHeight - 4, "(" + trackData.getGenerator().getGeneratorTypeData().getGeneratorTypeName() + ")");
		
		TracksListWidgetGraphic.drawGeneratorArea(g, screenGrafic, desktopColors,
				tracksData, generator, screenPosY, entryHeight,
				scrollStartTime, scrollEndTime, generatorIsActive, generatorIsSelected);
	}
	

	public void drawBackground(Graphics g, ScreenGraficInterface screenGrafic, DesktopColors desktopColors, 
							   ListWidgetData listWidgetData, 
							   int posX, int posY, int sizeX, int sizeY, 
							   float horizontalScrollStart, float horizontalScrollEnd)
	{
		TracksListWidgetData tracksData = (TracksListWidgetData)listWidgetData;
		
		float scaleX = tracksData.getGeneratorScaleX();
		int labelSizeX = tracksData.getGeneratorsLabelSizeX();

		// Generator Labels Background:
		g.setColor(desktopColors.getGeneratorLabelsBackgroundColor());
		
		screenGrafic.fillRect(g, posX, posY, labelSizeX, sizeY);

		// Generators Background:
		g.setColor(desktopColors.getGeneratorsBackgroundColor());
		
		screenGrafic.fillRect(g, posX + labelSizeX, posY, sizeX - labelSizeX, sizeY);
		
		screenGrafic.setFont(g, "Dialog", Font.PLAIN, 12);
		
		// Zeitschritte:
		g.setColor(desktopColors.getTimestepColor());
		
		float visibleTime = horizontalScrollEnd - horizontalScrollStart;
		
		for (float timePos = Math.round(horizontalScrollStart); timePos < horizontalScrollEnd; timePos += 1.0)
		{
			int screenPosX = tracksData.getGeneratorsLabelSizeX() + (int)((timePos - horizontalScrollStart) * scaleX);
			
			screenGrafic.drawLine(g, screenPosX, posY, 0, sizeY);
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetGraphic#drawSelectors(java.awt.Graphics, de.schmiereck.screenTools.graphic.ScreenGraficInterface, de.schmiereck.noiseComp.desktopPage.DesktopColors, de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetData, int, int, int, int, int, float, float, float, float)
	 */
	public void drawSelectors(Graphics g, ScreenGraficInterface screenGrafic, DesktopColors desktopColors, 
							  ListWidgetData listWidgetData, 
							  int posX, int posY, int sizeX, int sizeY, int entryHeight, 
							  float horizontalScrollStart, float horizontalScrollEnd, 
							  float verticalScrollerStart, float verticalScrollerEnd)
	{
		TracksListWidgetData tracksData = (TracksListWidgetData)listWidgetData;
		
		float scaleX = tracksData.getGeneratorScaleX();
		int labelSizeX = tracksData.getGeneratorsLabelSizeX();

		// Generator Labels Lines:
		g.setColor(Color.GRAY);
		
		screenGrafic.drawLine(g, 0, posY, sizeX, 0);
		screenGrafic.drawLine(g, tracksData.getGeneratorsLabelSizeX(), posY, 0, sizeY);
		
		//----------------------------------------------------------------------
		// Draw Input Connectors:
		
		if (tracksData.getSelectedTrackData() != null)
		{
			g.setColor(Color.RED);
			
			TrackData selectedTrackGraficData = tracksData.getSelectedTrackData();
			Generator selectedGenerator = selectedTrackGraficData.getGenerator();
			
			int selectedPos = selectedTrackGraficData.getTrackPos();
			
			Generator generator = selectedTrackGraficData.getGenerator();
			
			Iterator inputsIterator = generator.getInputsIterator();
			
			if (inputsIterator != null)
			{
				int selectedScreenPosX = (int)((selectedGenerator.getStartTimePos() - horizontalScrollStart) * scaleX);
				int selectedScreenInputOffset = (int)(((selectedGenerator.getEndTimePos() - selectedGenerator.getStartTimePos()) * scaleX) / (generator.getInputsCount() + 1));
				
				int inputNr = 1;
				while (inputsIterator.hasNext())
				{
					InputData inputData = (InputData)inputsIterator.next();

					Generator inputGenerator = (Generator)inputData.getInputGenerator();
					
					TrackData inputTrackData = tracksData.searchTrackData(inputGenerator);

					// Generator mit diesem Namen gefunden ?
					if (inputTrackData != null)
					{	
						int inputPos = inputTrackData.getTrackPos();
						
						int inputOffsetScreenX = inputNr * selectedScreenInputOffset;
						/*
						int inputScreenPosX = (int)((inputGenerator.getEndTimePos() - scrollStartTime) * scaleX);
						
						int inp1X = (int)(tracksData.getGeneratorsLabelSizeX() + selectedScreenPosX + inputOffsetScreenX);
						int inp1Y = posY - (scrollerStartPos * trackHeight) + selectedPos * trackHeight;
						int inp2X = (int)(tracksData.getGeneratorsLabelSizeX() + inputScreenPosX);
						int inp2Y = posY - (scrollerStartPos * trackHeight) + inputPos * trackHeight + trackHeight / 2;
						*/
						int inputScreenPosX = (int)((inputGenerator.getEndTimePos() - horizontalScrollStart) * scaleX);
						
						int inp1X = (int)(tracksData.getGeneratorsLabelSizeX() + selectedScreenPosX + inputOffsetScreenX);
						int inp1Y = (int)(posY - ((int)(verticalScrollerStart + 1) * entryHeight) + selectedPos * entryHeight);
						int inp2X = (int)(tracksData.getGeneratorsLabelSizeX() + inputScreenPosX);
						int inp2Y = (int)(posY - ((int)(verticalScrollerStart + 1) * entryHeight) + inputPos * entryHeight + entryHeight / 2);
						
						screenGrafic.drawAbsLine(g, 
								inp1X, inp1Y, 
								inp1X, inp2Y);
						screenGrafic.drawAbsLine(g, 
								inp1X, inp2Y, 
								inp2X, inp2Y);
						
						screenGrafic.fillRect(g, inp1X - 2, inp1Y, 8, 4);
					}
					inputNr++;
				}
			}
		}
		
		//----------------------------------------------------------------------
		// Play Sound Pos:
		g.setColor(Color.RED);
		
		float playTimePos = tracksData.getSoundPlayTimePos();
		
		//screenGrafic.setFont()
		screenGrafic.drawString(g, posX + 6, posY, "time:" + playTimePos);
		
		if ((playTimePos >= horizontalScrollStart) && (playTimePos <= horizontalScrollEnd))
		{	
			int soundPosX = (int)((playTimePos - horizontalScrollStart) * tracksData.getGeneratorScaleX());
			
			screenGrafic.drawLine(g, tracksData.getGeneratorsLabelSizeX() + soundPosX, posY, 0, sizeY);
		}
		
		//----------------------------------------------------------------------
		// Drag-Border:

		if (tracksData.getDoDragging() == true)
		{
			g.setColor(dragColor1);
			//g.setXORMode(dragColor2);
			
			int y = tracksData.getDragTargetPosY() - tracksData.getDragTargetOffsetY();
			
			screenGrafic.fillRect(g, 
								  0, y, 
								  sizeX, tracksData.getListEntryHeight());

			//g.setXORMode(Color.WHITE);
		}
	}
}
