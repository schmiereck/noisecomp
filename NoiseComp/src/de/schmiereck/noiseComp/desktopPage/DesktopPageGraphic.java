package de.schmiereck.noiseComp.desktopPage;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Iterator;

import de.schmiereck.noiseComp.desktopPage.widgets.ButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.FunctionButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.GeneratorsGraphicData;
import de.schmiereck.noiseComp.desktopPage.widgets.PaneData;
import de.schmiereck.noiseComp.desktopPage.widgets.ScrollbarData;
import de.schmiereck.noiseComp.desktopPage.widgets.TrackGraficData;
import de.schmiereck.noiseComp.desktopPage.widgets.WidgetData;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.SoundSample;
import de.schmiereck.screenTools.graphic.ScreenGraficInterface;

/**
 * TODO docu
 *
 * @author smk
 * @version 01.02.2004
 */
public class DesktopPageGraphic
{

	/**
	 * Farbe des Mauszeigers.
	 */
	private Color pointerColor = new Color(0F, 0F, 1.0F, 0.3F);
	
	private Color activeButtonColor = new Color(0xE0, 0xE8, 0xE4);
	
	private Color starColor = new Color(0xE0, 0xD0, 0xD0);

	//private Color sampleColor = new Color(0xA0, 0xA0, 0xA0, 255/10);
	private Color sampleColor = Color.BLACK; 
	private Color timestepColor = new Color(0xF0, 0xFF, 0xF0);

	private Color activeGeneratorBackgroundColor 	= new Color(0xFF, 0xFF, 0xFF);

	private Color inactiveGeneratorBackgroundColor	= new Color(0xDF, 0xFF, 0xDF);

	private Color selectedGeneratorSelectorColor	= new Color(0x00, 0x00, 0xFF);
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public DesktopPageGraphic()
	{
		super();
	}

	/**
	 * Zeichnet den Mauszeiger.
	 */
	public void drawPointer(Graphics g, ScreenGraficInterface screenGrafic, DesktopPageData desctopPageData)
	{
		screenGrafic.setColor(g, this.pointerColor);
		
		// Vertikale Linie:
		screenGrafic.drawLine(g, desctopPageData.getPointerPosX(), 0, 
				0, desctopPageData.getDesctopSizeY());
		// Horizontale Linie:
		screenGrafic.drawLine(g, 0, desctopPageData.getPointerPosY(), 
				desctopPageData.getDesctopSizeX(), 0);
	}
	
	/**
	 * Zeichnet die Eingabeelemente.
	 */
	public void drawWidgets(Graphics g, ScreenGraficInterface screenGrafic, DesktopPageData desctopPageData)
	{
		Iterator widgetIterator = desctopPageData.getWidgetsIterator();
		
		while (widgetIterator.hasNext())
		{
			WidgetData widgetData = (WidgetData)widgetIterator.next();
			
			if (widgetData instanceof FunctionButtonData)
			{	
				FunctionButtonData buttonData = (FunctionButtonData)widgetData;
				
				ButtonData activeButtonData = desctopPageData.getActiveButtonData();
				
				boolean active;
				boolean pressed;
				
				if (activeButtonData == buttonData)
				{
					active = true;
					
					if (desctopPageData.getPressedButtonData() == buttonData)
					{
						pressed = true;
					}
					else
					{
						pressed = false;
					}
				}
				else
				{
					active = false;
					pressed = false;
				}
				
				this.drawButton(g, screenGrafic, buttonData, active, pressed);
			}
			else
			{
				if (widgetData instanceof ScrollbarData)
				{	
					ScrollbarData scrollbarData = (ScrollbarData)widgetData;
					
					boolean active;
					int activeScrollbarPart;
					
					if (desctopPageData.getActiveScrollbarData() == scrollbarData)
					{
						active = true;
						activeScrollbarPart = desctopPageData.getActiveScrollbarPart();
					}
					else
					{
						active = false;
						activeScrollbarPart = 0;
					}
					
					this.drawScrollbar(g, screenGrafic, scrollbarData, active, activeScrollbarPart);
				}
				else
				{
					if (widgetData instanceof PaneData)
					{	
						PaneData paneData = (PaneData)widgetData;
						
						this.drawPane(g, screenGrafic, paneData);
					}
					else
					{
						if (widgetData instanceof GeneratorsGraphicData)
						{	
							GeneratorsGraphicData generatorsGraphicData = (GeneratorsGraphicData)widgetData;
							
							this.drawGenerators(g, screenGrafic, generatorsGraphicData, desctopPageData);
						}
					}
				}
			}
		}
	}

	/**
	 * @param g
	 * @param buttonData
	 */
	private void drawButton(Graphics g, ScreenGraficInterface screenGrafic, FunctionButtonData buttonData, boolean active, boolean pressed)
	{
		String labelText = buttonData.getLabelText();
		int posX = buttonData.getPosX();
		int posY = buttonData.getPosY();
		int sizeX = buttonData.getSizeX();
		int sizeY = buttonData.getSizeY();
		
		int offX = 0;
		int offY = 0;
		
		if (active == true)
		{	
			g.setColor(this.activeButtonColor);
			
			screenGrafic.fillRect(g, posX, posY, sizeX, sizeY);

			if (pressed == true)
			{	
				screenGrafic.draw3DRect(g, posX, posY, sizeX, sizeY, false);

				offX = 1;
				offY = 1;
			}
			else
			{	
				screenGrafic.draw3DRect(g, posX, posY, sizeX, sizeY, true);
			}
		}
		else
		{
			g.setColor(Color.LIGHT_GRAY);
			
			screenGrafic.fillRect(g, posX, posY, sizeX, sizeY);

			g.setColor(Color.GRAY);
			
			screenGrafic.drawRect(g, posX, posY, sizeX, sizeY);
		}

		g.setColor(Color.BLACK);
		
		screenGrafic.setFont(g, "Dialog", Font.PLAIN, sizeY - 4);
		
		FontMetrics fontMetrics = g.getFontMetrics();
		
		int stringWidth = fontMetrics.stringWidth(labelText);
		
		screenGrafic.drawString(g, posX + offX + (sizeX / 2) - (stringWidth / 2), 
				posY + offY + (sizeY / 2) + (fontMetrics.getAscent() / 2) + (fontMetrics.getDescent() / 2), labelText);
	}

	/**
	 * @param g
	 * @param scrollbarData
	 */
	private void drawScrollbar(Graphics g, ScreenGraficInterface screenGrafic, ScrollbarData scrollbarData, boolean active, int activeScrollbarPart)
	{
		int posX = scrollbarData.getPosX();
		int posY = scrollbarData.getPosY();
		int sizeX = scrollbarData.getSizeX();
		int sizeY = scrollbarData.getSizeY();

		int width;
		
		if (scrollbarData.getDoScrollVertical() == true)
		{
			width = sizeX;
		}
		else
		{
			width = sizeY;
		}

		// Background:
		g.setColor(Color.WHITE);
		screenGrafic.fillRect(g, posX, posY, sizeX, sizeY);

		g.setColor(Color.GRAY);
		screenGrafic.draw3DRect(g, posX, posY, sizeX, sizeY, false);

		// Scroll-Up-Arrow:
		if ((active == true) && (activeScrollbarPart == 1))
		{
			g.setColor(this.activeButtonColor);
		}
		else
		{
			g.setColor(Color.LIGHT_GRAY);
		}
		screenGrafic.fillRect(g, posX, posY, width, width);
		g.setColor(Color.GRAY);
		screenGrafic.draw3DRect(g, posX, posY, width, width, true);
		g.setColor(Color.BLACK);
		if (scrollbarData.getDoScrollVertical() == true)
		{
			screenGrafic.drawLine(g, posX, posY + width, width / 2, -width);
			screenGrafic.drawLine(g, posX + width / 2, posY, width / 2, width);
		}
		else
		{
			screenGrafic.drawLine(g, posX + width, posY, -width, width / 2);
			screenGrafic.drawLine(g, posX, posY + width / 2, width, width / 2);
		}
		
		// Scroll-Down-Arrow:
		if ((active == true) && (activeScrollbarPart == 3))
		{
			g.setColor(this.activeButtonColor);
		}
		else
		{
			g.setColor(Color.LIGHT_GRAY);
		}
		if (scrollbarData.getDoScrollVertical() == true)
		{
			screenGrafic.fillRect(g, posX, posY + (sizeY - width), width, width);
		}
		else
		{
			screenGrafic.fillRect(g, posX + (sizeX - width), posY, width, width);
		}
		g.setColor(Color.GRAY);
		if (scrollbarData.getDoScrollVertical() == true)
		{
			screenGrafic.draw3DRect(g, posX, posY + (sizeY - width), width, width, true);
		}
		else
		{
			screenGrafic.draw3DRect(g, posX + (sizeX - width), posY, width, width, true);
		}
		g.setColor(Color.BLACK);
		if (scrollbarData.getDoScrollVertical() == true)
		{
			screenGrafic.drawLine(g, posX, posY + (sizeY - width), width / 2, width);
			screenGrafic.drawLine(g, posX + width / 2, posY + (sizeY), width / 2, -width);
		}
		else
		{
			screenGrafic.drawLine(g, posX + (sizeX - width), posY, width, width / 2);
			screenGrafic.drawLine(g, posX + (sizeX), posY + width / 2, -width, width / 2);
		}

		// Scroller:
		int scrollerSize = scrollbarData.getScreenScrollerSize();
		int scrollerPos = scrollbarData.getScreenScrollerPos();
		
		if ((active == true) && (activeScrollbarPart == 2))
		{
			g.setColor(this.activeButtonColor);
		}
		else
		{
			g.setColor(Color.LIGHT_GRAY);
		}
		if (scrollbarData.getDoScrollVertical() == true)
		{
			screenGrafic.fillRect(g, posX, posY + scrollerPos, width, scrollerSize);
			g.setColor(Color.GRAY);
			screenGrafic.draw3DRect(g, posX, posY + scrollerPos, width, scrollerSize, true);
		}
		else
		{
			screenGrafic.fillRect(g, posX + scrollerPos, posY, scrollerSize, width);
			g.setColor(Color.GRAY);
			//screenGrafic.draw3DRect(g, posX + scrollerPos, posY, scrollerSize, width, true);
		}
	}

	/**
	 * @param g
	 * @param paneData
	 */
	private void drawPane(Graphics g, ScreenGraficInterface screenGrafic, PaneData paneData)
	{
		int posX = paneData.getPosX();
		int posY = paneData.getPosY();
		int sizeX = paneData.getSizeX();
		int sizeY = paneData.getSizeY();
		
		g.setColor(Color.LIGHT_GRAY);
		
		screenGrafic.fillRect(g, posX, posY, sizeX, sizeY);
		screenGrafic.draw3DRect(g, posX, posY, sizeX, sizeY, true);

		g.setColor(starColor);
		
		int sx = sizeX / 2;
		int sy = sizeY / 2;
		
		for (int pos = 0; pos < PaneData.STARS_COUNT; pos++)
		{
			int x = paneData.getStarX(pos) / PaneData.SIZE + posX + sx;
			int y = paneData.getStarY(pos) / PaneData.SIZE + posY + sy;
			
			int size = paneData.getStarX(pos);
			
			if (size < 0)
			{
				size = -size;
			}
			
			//this.drawPoint(g, x, y, size / (sx / (PaneData.SIZE / 2)) + 2);
			screenGrafic.drawPoint(g, x, y, 3);
		}
		
		paneData.anim();
	}
	
	/**
	 * @param g
	 * @param generatorsGraphicData
	 */
	private void drawGenerators(Graphics g, ScreenGraficInterface screenGrafic, GeneratorsGraphicData generatorsGraphicData, DesktopPageData desctopPageData)
	{
		int posX = generatorsGraphicData.getPosX();
		int posY = generatorsGraphicData.getPosY();
		int sizeX = generatorsGraphicData.getSizeX();
		int sizeY = generatorsGraphicData.getSizeY();
		
		int trackHeight = generatorsGraphicData.getTrackHeight();
		
		float scaleX = generatorsGraphicData.getGeneratorScaleX();
		
		// Generator Labels Background:
		g.setColor(Color.LIGHT_GRAY);
		
		screenGrafic.fillRect(g, posX, posY, generatorsGraphicData.getGeneratorsLabelSizeX(), sizeY);
		
		screenGrafic.setFont(g, "Dialog", Font.PLAIN, 12);
		
		float scrollStartTime = generatorsGraphicData.getHorizontalScrollbarData().getScrollerPos();
		float scrollEndTime = scrollStartTime + generatorsGraphicData.getHorizontalScrollbarData().getScrollerSize();
		
		// Zeitschritte:
		g.setColor(this.timestepColor);
		
		for (float timePos = scrollStartTime; timePos < scrollEndTime; timePos += 1.0)
		{
			int screenPosX = generatorsGraphicData.getGeneratorsLabelSizeX() + (int)(timePos * scaleX);
			
			screenGrafic.drawLine(g, screenPosX, posY, 0, sizeY);
		}

		int scrollerStartPos = (int)(generatorsGraphicData.getVerticalScrollbarData().getScrollerPos());
		int scrollerEndPos = (int)(scrollerStartPos + generatorsGraphicData.getVerticalScrollbarData().getScrollerSize());
		
		synchronized (generatorsGraphicData)
		{
			int trackPos = 0;
			int trackPosY = 0;
			Iterator generatorsIterator = generatorsGraphicData.getTracksIterator();
			
			while (generatorsIterator.hasNext())
			{
				TrackGraficData trackGraficData = (TrackGraficData)generatorsIterator.next();
				
				if (trackPos >= scrollerStartPos)
				{	
					boolean trackIsActive;
					boolean generatorIsActive;
					
					if (generatorsGraphicData.getActiveTrackGraficData() == trackGraficData)
					{
						trackIsActive = true;

						if (generatorsGraphicData.getHitGeneratorPart() != GeneratorsGraphicData.HIT_PART_NONE)
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
					
					if (generatorsGraphicData.getSelectedTrackGraficData() == trackGraficData)
					{
						trackIsSelected = true;
						
						if (generatorsGraphicData.getGeneratorIsSelected() == true)
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

					Generator generator = trackGraficData.getGenerator();
					
					int screenPosY = trackPosY + posY;

					if (trackIsSelected == true)
					{
						g.setColor(Color.GRAY);
						screenGrafic.fillRect(g, 0, screenPosY, generatorsGraphicData.getGeneratorsLabelSizeX(), trackHeight);
					}

					// Bottom-Line.
					g.setColor(Color.GRAY);
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
					screenGrafic.drawString(g, 10, screenPosY + trackHeight / 2, trackGraficData.getName());
					
					this.drawGeneratorArea(g, screenGrafic, generatorsGraphicData, 
							generator, screenPosY, trackHeight,
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
			screenGrafic.drawLine(g, generatorsGraphicData.getGeneratorsLabelSizeX(), posY, 0, sizeY);
			
			// Input Connectors:
			if (generatorsGraphicData.getSelectedTrackGraficData() != null)
			{
				g.setColor(Color.RED);
				
				TrackGraficData selectedTrackGraficData = generatorsGraphicData.getSelectedTrackGraficData();
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
						
						TrackGraficData inputTrackGraficData = generatorsGraphicData.searchTrackGraficData(inputGenerator.getName());

						// Generator mit diesem Namen gefunden ?
						if (inputTrackGraficData != null)
						{	
							int inputPos = inputTrackGraficData.getTrackPos();
							
							int inputOffsetScreenX = inputNr * selectedScreenInputOffset;
							
							int inputScreenPosX = (int)((inputGenerator.getEndTimePos() - scrollStartTime) * scaleX);
							
							int inp1X = (int)(generatorsGraphicData.getGeneratorsLabelSizeX() + selectedScreenPosX + inputOffsetScreenX);
							int inp1Y = posY + selectedPos * trackHeight;
							int inp2X = (int)(generatorsGraphicData.getGeneratorsLabelSizeX() + inputScreenPosX);
							int inp2Y = posY + inputPos * trackHeight + trackHeight / 2;
							
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
			
			float playTimePos = generatorsGraphicData.getSoundData().getSoundBufferManager().getActualTime();
			
			if ((playTimePos >= scrollStartTime) && (playTimePos <= scrollEndTime))
			{	
				int soundPosX = (int)((playTimePos - scrollStartTime) * generatorsGraphicData.getGeneratorScaleX());
				
				screenGrafic.drawLine(g, generatorsGraphicData.getGeneratorsLabelSizeX() + soundPosX, posY, 0, sizeY);
			}
		}
	}
	
	private void drawGeneratorArea(Graphics g, ScreenGraficInterface screenGrafic, 
								   GeneratorsGraphicData generatorsGraphicData, Generator generator, 
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
				float scaleX = generatorsGraphicData.getGeneratorScaleX();
				
				int screenPosX = generatorsGraphicData.getGeneratorsLabelSizeX() + (int)((generatorStartTime - scrollStartTime) * scaleX);
				int screenSizeX = (int)(timeLength * scaleX);
		
				// Generator Area:
				if (generatorIsActive == true)
				{	
					screenGrafic.setColor(g, this.activeGeneratorBackgroundColor);
				}
				else
				{	
					screenGrafic.setColor(g, this.inactiveGeneratorBackgroundColor);
				}
				screenGrafic.fillRect(g, screenPosX, screenPosY, screenSizeX, trackHeight);
				
				// Samples:
				g.setColor(this.sampleColor);
				
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
					screenGrafic.setColor(g, this.selectedGeneratorSelectorColor);
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
