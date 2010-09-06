package de.schmiereck.noiseComp.smkScreen.desctopPage;

import java.awt.Graphics;
import java.util.Iterator;

import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.FunctionButtonData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.FunctionButtonGraphic;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputlineData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputlineGraphic;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.LabelData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.LabelGraphic;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ListWidgetData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ListWidgetGraphic;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.PaneData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.PaneGraphic;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ScrollbarData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ScrollbarGraphic;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.SelectData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.SelectGraphic;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.TextWidgetData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.TextWidgetGraphic;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.WidgetData;
import de.schmiereck.screenTools.graphic.ScreenGraficInterface;

/**
 * Draws the representation of the Data-Objects on the Screen.
 *
 * @author smk
 * @version 01.02.2004
 */
public class DesktopPageGraphic
{
	private DesktopColors	desktopColors;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public DesktopPageGraphic()
	{
		super();

		this.desktopColors = new DesktopColors();
		
	}

	/**
	 * @param g
	 * 			ist die Grafik.
	 * @param screenGrafic
	 * 			sind die Funktionen zum Zeichnen.
	 * @param desktopPageData
	 * 			sind die Daten des Desktops.
	 */
	public void drawPointer(Graphics g, ScreenGraficInterface screenGrafic, DesktopPageData desktopPageData)
	{
		screenGrafic.setColor(g, this.desktopColors.getPointerColor());
		
		// Vertikale Linie:
		screenGrafic.drawLine(g, 
							  desktopPageData.getPointerPosX(), 0, 
							  0, desktopPageData.getDesktopSizeY());
		
		// Horizontale Linie:
		screenGrafic.drawLine(g, 
							  0, desktopPageData.getPointerPosY(), 
							  desktopPageData.getDesktopSizeX(), 0);

		screenGrafic.fillRect(g, 
							  desktopPageData.getPointerPosX() - 6, desktopPageData.getPointerPosY() - 6, 
							  12, 12);
	}
	
	/**
	 * Zeichnet die Eingabeelemente.
	 * 
	 * @param g
	 * 			ist die Grafik.
	 * @param screenGrafic
	 * 			sind die Funktionen zum Zeichnen.
	 * @param desktopPageData
	 * 			sind die Daten des Desktops.
	 */
	public void drawWidgets(Graphics g, 
	                        ScreenGraficInterface screenGrafic, 
	                        DesktopPageData desktopPageData)
	{
		Iterator widgetIterator = desktopPageData.getWidgetsIterator();
		
		while (widgetIterator.hasNext())
		{
			WidgetData widgetData = (WidgetData)widgetIterator.next();
			
			if (widgetData instanceof FunctionButtonData)
			{	
				FunctionButtonData buttonData = (FunctionButtonData)widgetData;
				
				InputWidgetData activeButtonData = desktopPageData.getActiveButtonData();
				
				boolean active;
				boolean pressed;
				
				if (activeButtonData == buttonData)
				{
					active = true;
					
					if (desktopPageData.getPressedButtonData() == buttonData)
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
				
				FunctionButtonGraphic.drawFunctionButton(g, screenGrafic, desktopColors, buttonData, active, pressed);
			}
			else
			{
				if (widgetData instanceof ScrollbarData)
				{	
					ScrollbarData scrollbarData = (ScrollbarData)widgetData;
					
					boolean active;
					int activeScrollbarPart;
					
					if (desktopPageData.getActiveScrollbarData() == scrollbarData)
					{
						active = true;
						activeScrollbarPart = desktopPageData.getActiveScrollbarData().getActiveScrollbarPart();
					}
					else
					{
						active = false;
						activeScrollbarPart = 0;
					}
					
					ScrollbarGraphic.drawScrollbar(g, screenGrafic, desktopColors, scrollbarData, active, activeScrollbarPart);
				}
				else
				{
					if (widgetData instanceof PaneData)
					{	
						PaneData paneData = (PaneData)widgetData;
						
						PaneGraphic.drawPane(g, screenGrafic, desktopColors, paneData);
					}
					else
					{
						if (widgetData instanceof LabelData)
						{	
							LabelData labelData = (LabelData)widgetData;
							
							LabelGraphic.drawLabel(g, screenGrafic, desktopColors, labelData);
						}
						else
						{
							/*
							if (widgetData instanceof TracksListWidgetData)
							{	
								TracksListWidgetData tracksData = (TracksListWidgetData)widgetData;
								
								ListWidgetGraphic listWidgetGraphic = tracksData.getGraphicInstance();
								
								listWidgetGraphic.drawList(g, screenGrafic, desktopColors, 
										tracksData);
								//TracksListWidgetGraphic.drawGenerators(g, screenGrafic, desktopColors, 
								//		tracksData);
							}
							else
							*/
							{
								if (widgetData instanceof InputlineData)
								{	
									InputlineData inputlineData = (InputlineData)widgetData;
									
									WidgetData activeWidgetData = desktopPageData.getActiveWidgetData();
									
									boolean active;
									
									if (activeWidgetData == inputlineData)
									{
										active = true;
									}
									else
									{
										active = false;
									}

									boolean focused;
									
									if (desktopPageData.getFocusedWidgetData() == inputlineData)
									{
										focused = true;
									}
									else
									{
										focused = false;
									}
									
									InputlineGraphic.drawInputline(g, screenGrafic, desktopColors, inputlineData, active, focused);
								}
								else
								{
									/*
									if (widgetData instanceof InputsWidgetData)
									{	
										InputsWidgetData inputsWidgetData = (InputsWidgetData)widgetData;
										
										ListWidgetGraphic listWidgetGraphic = inputsWidgetData.getGraphicInstance();
										
										listWidgetGraphic.drawList(g, screenGrafic, desktopColors, 
												inputsWidgetData);
									}
									else
									*/
									{
										if (widgetData instanceof SelectData)
										{	
											SelectData selectData = (SelectData)widgetData;
											
											WidgetData activeWidgetData = desktopPageData.getActiveWidgetData();
											
											boolean active;
											
											if (activeWidgetData == selectData)
											{
												active = true;
											}
											else
											{
												active = false;
											}

											boolean focused;
											
											if (desktopPageData.getFocusedWidgetData() == selectData)
											{
												focused = true;
											}
											else
											{
												focused = false;
											}
											
											SelectGraphic.drawWidget(g, screenGrafic, desktopColors, selectData, active, focused);
										}
										else
										{
											if (widgetData instanceof ListWidgetData)
											{	
												ListWidgetData listWidgetData = (ListWidgetData)widgetData;
												
												ListWidgetGraphic listWidgetGraphic = listWidgetData.getGraphicInstance();
												
												listWidgetGraphic.drawList(g, screenGrafic, desktopColors, 
														listWidgetData);
											}
											else
											{
												if (widgetData instanceof TextWidgetData)
												{	
													TextWidgetData textWidgetData = (TextWidgetData)widgetData;
													
													TextWidgetGraphic.drawLabel(g, screenGrafic, desktopColors, textWidgetData);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}


}
