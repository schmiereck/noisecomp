package de.schmiereck.noiseComp.desktopPage.widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;

import de.schmiereck.noiseComp.desktopPage.DesktopColors;
import de.schmiereck.noiseComp.desktopPage.DesktopPageData;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.SoundSample;
import de.schmiereck.screenTools.graphic.ScreenGraficInterface;

/**
 * TODO docu
 *
 * @author smk
 * @version 08.02.2004
 */
public class TracksGraphic
{
	/**
	 * @param g
	 * @param generatorsGraphicData
	 */
	public static void drawGenerators(Graphics g, ScreenGraficInterface screenGrafic, 
									  DesktopColors desktopColors,
									  TracksData tracksData, DesktopPageData desktopPageData)
	{
		int posX = tracksData.getPosX();
		int posY = tracksData.getPosY();
		int sizeX = tracksData.getSizeX();
		int sizeY = tracksData.getSizeY();
		
		int trackHeight = tracksData.getTrackHeight();
		
		float scaleX = tracksData.getGeneratorScaleX();
		
		int labelSizeX = tracksData.getGeneratorsLabelSizeX();
		
		// Generator Labels Background:
		g.setColor(Color.LIGHT_GRAY);
		
		screenGrafic.fillRect(g, posX, posY, labelSizeX, sizeY);

		// Generators Background:
		g.setColor(desktopColors.getGeneratorsBackgroundColor());
		
		screenGrafic.fillRect(g, posX + labelSizeX, posY, sizeX - labelSizeX, sizeY);
		
		screenGrafic.setFont(g, "Dialog", Font.PLAIN, 12);
		
		float scrollStartTime = tracksData.getHorizontalScrollbarData().getScrollerPos();
		float scrollEndTime = scrollStartTime + tracksData.getHorizontalScrollbarData().getScrollerSize();
		
		// Zeitschritte:
		g.setColor(desktopColors.getTimestepColor());
		
		float visibleTime = scrollEndTime - scrollStartTime;
		
		for (float timePos = Math.round(scrollStartTime); timePos < scrollEndTime; timePos += 1.0)
		{
			int screenPosX = tracksData.getGeneratorsLabelSizeX() + (int)((timePos - scrollStartTime) * scaleX);
			
			screenGrafic.drawLine(g, screenPosX, posY, 0, sizeY);
		}

		int scrollerStartPos = (int)(tracksData.getVerticalScrollbarData().getScrollerPos());
		int scrollerEndPos = (int)(scrollerStartPos + tracksData.getVerticalScrollbarData().getScrollerSize());
		
		synchronized (tracksData)
		{
			int trackPos = 0;
			int trackPosY = 0;
			Iterator generatorsIterator = tracksData.getTracksIterator();
			
			while (generatorsIterator.hasNext())
			{
				TrackData trackData = (TrackData)generatorsIterator.next();
				
				if (trackPos >= scrollerStartPos)
				{	
					boolean trackIsActive;
					boolean generatorIsActive;
					
					if (tracksData.getActiveTrackData() == trackData)
					{
						trackIsActive = true;

						if (tracksData.getHitGeneratorPart() != TracksData.HIT_PART_NONE)
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
					
					int screenPosY = trackPosY + posY;

					if (trackIsSelected == true)
					{
						g.setColor(Color.GRAY);
						screenGrafic.fillRect(g, 0, screenPosY, tracksData.getGeneratorsLabelSizeX(), trackHeight);
					}

					// Bottom-Line.
					g.setColor(desktopColors.getGeneratorBottomLineColor());
					screenGrafic.drawLine(g, 0, screenPosY + trackHeight, sizeX, 0);
					
					// Label-Text.
					if (trackIsActive == true)
					{	
						screenGrafic.setColor(g, Color.WHITE);
					}
					else
					{
						screenGrafic.setColor(g, Color.BLACK);
					}
					screenGrafic.drawString(g, 10, screenPosY + trackHeight / 2, trackData.getName());
					
					TracksGraphic.drawGeneratorArea(g, screenGrafic, desktopColors,
							tracksData, generator, screenPosY, trackHeight,
							scrollStartTime, scrollEndTime, generatorIsActive, generatorIsSelected);
					
					trackPosY += trackHeight;
				}
				
				trackPos++;
				if (trackPos >= scrollerEndPos)
				{
					break;
				}
			}
			
			// Generator Labels Lines:
			g.setColor(Color.GRAY);
			
			screenGrafic.drawLine(g, 0, posY, sizeX, 0);
			screenGrafic.drawLine(g, tracksData.getGeneratorsLabelSizeX(), posY, 0, sizeY);
			
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
					int selectedScreenPosX = (int)((selectedGenerator.getStartTimePos() - scrollStartTime) * scaleX);
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
							
							int inputScreenPosX = (int)((inputGenerator.getEndTimePos() - scrollStartTime) * scaleX);
							
							int inp1X = (int)(tracksData.getGeneratorsLabelSizeX() + selectedScreenPosX + inputOffsetScreenX);
							int inp1Y = posY - (scrollerStartPos * trackHeight) + selectedPos * trackHeight;
							int inp2X = (int)(tracksData.getGeneratorsLabelSizeX() + inputScreenPosX);
							int inp2Y = posY - (scrollerStartPos * trackHeight) + inputPos * trackHeight + trackHeight / 2;
							
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
			
			// Play Sound Pos:
			g.setColor(Color.RED);
			
			float playTimePos = tracksData.getSoundData().getSoundBufferManager().getActualTime();
			
			if ((playTimePos >= scrollStartTime) && (playTimePos <= scrollEndTime))
			{	
				int soundPosX = (int)((playTimePos - scrollStartTime) * tracksData.getGeneratorScaleX());
				
				screenGrafic.drawLine(g, tracksData.getGeneratorsLabelSizeX() + soundPosX, posY, 0, sizeY);
			}
		}
	}
	
	private static void drawGeneratorArea(Graphics g, ScreenGraficInterface screenGrafic, DesktopColors desktopColors,
								   TracksData tracksData, Generator generator, 
								   int screenPosY, int trackHeight, 
								   float scrollStartTime, float scrollEndTime, 
								   boolean generatorIsActive, boolean generatorIsSelected)
	{
		// Beim Scrollen den links abgeschnittenenen Teil berücksichtigen:
		
		float generatorStartTime;
		
		if (generator.getStartTimePos() < scrollStartTime)
		{
			generatorStartTime = scrollStartTime;
		}
		else
		{
			generatorStartTime = generator.getStartTimePos();
		}

		// Ist der Generator sichtbar und liegt nichts rechts neben dem Scroll-Bereich ?
		if (generatorStartTime < scrollEndTime)
		{	
			// Beim Scrollen berücksichtigen den rechts abgeschnittenen Teil berücksichtigen.
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
					screenGrafic.setColor(g, desktopColors.getInactiveGeneratorBackgroundColor());
				}
				screenGrafic.fillRect(g, screenPosX, screenPosY, screenSizeX, trackHeight);
				
				// Samples:
				g.setColor(desktopColors.getSampleColor());
				
				float frameRate = generator.getFrameRate();
				boolean firstSample = true;
				int lastX = screenPosX;
				int lastY = screenPosY;
				
				for (float timePos = 0; timePos < timeLength; timePos += (0.5 / scaleX))
				{
					SoundSample soundSample = generator.generateFrameSample((long)((generatorStartTime + timePos) * frameRate));
					if (soundSample != null)
					{	
						int samplePosX = screenPosX + (int)(timePos * scaleX);
						int samplePosY = screenPosY + 16 - (int)(soundSample.getMonoValue() * 16);
						if (firstSample == true)
						{
							firstSample = false;
						}
						else
						{	
							screenGrafic.drawAbsLine(g, lastX, lastY, samplePosX, samplePosY);
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
					screenGrafic.drawRect(g, screenPosX, screenPosY, screenSizeX, trackHeight);
				}
			}
		}
	}
	
}
