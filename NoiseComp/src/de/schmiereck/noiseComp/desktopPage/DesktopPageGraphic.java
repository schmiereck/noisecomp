package de.schmiereck.noiseComp.desktopPage;

import java.awt.Graphics;
import java.util.Iterator;

import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.FunctionButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.FunctionButtonGraphic;
import de.schmiereck.noiseComp.desktopPage.widgets.InputlineData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputlineGraphic;
import de.schmiereck.noiseComp.desktopPage.widgets.InputsData;
import de.schmiereck.noiseComp.desktopPage.widgets.LabelData;
import de.schmiereck.noiseComp.desktopPage.widgets.LabelGraphic;
import de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetGraphic;
import de.schmiereck.noiseComp.desktopPage.widgets.PaneData;
import de.schmiereck.noiseComp.desktopPage.widgets.PaneGraphic;
import de.schmiereck.noiseComp.desktopPage.widgets.ScrollbarData;
import de.schmiereck.noiseComp.desktopPage.widgets.ScrollbarGraphic;
import de.schmiereck.noiseComp.desktopPage.widgets.SelectData;
import de.schmiereck.noiseComp.desktopPage.widgets.SelectGraphic;
import de.schmiereck.noiseComp.desktopPage.widgets.TracksData;
import de.schmiereck.noiseComp.desktopPage.widgets.WidgetData;
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
	 * Zeichnet den Mauszeiger.
	 */
	public void drawPointer(Graphics g, ScreenGraficInterface screenGrafic, DesktopPageData desktopPageData)
	{
		screenGrafic.setColor(g, this.desktopColors.getPointerColor());
		
		// Vertikale Linie:
		screenGrafic.drawLine(g, desktopPageData.getPointerPosX(), 0, 
				0, desktopPageData.getDesktopSizeY());
		// Horizontale Linie:
		screenGrafic.drawLine(g, 0, desktopPageData.getPointerPosY(), 
				desktopPageData.getDesktopSizeX(), 0);

		screenGrafic.fillRect(g, 
				desktopPageData.getPointerPosX() - 6, desktopPageData.getPointerPosY() - 6, 
				12, 12);
	}
	
	/**
	 * Zeichnet die Eingabeelemente.
	 */
	public void drawWidgets(Graphics g, ScreenGraficInterface screenGrafic, DesktopPageData desktopPageData)
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
							if (widgetData instanceof TracksData)
							{	
								TracksData tracksData = (TracksData)widgetData;
								
								ListWidgetGraphic listWidgetGraphic = tracksData.getGraphicInstance();
								
								listWidgetGraphic.drawList(g, screenGrafic, desktopColors, 
										tracksData);
								//TracksGraphic.drawGenerators(g, screenGrafic, desktopColors, 
								//		tracksData);
							}
							else
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
									if (widgetData instanceof InputsData)
									{	
										InputsData inputsData = (InputsData)widgetData;
										
										ListWidgetGraphic listWidgetGraphic = inputsData.getGraphicInstance();
										
										listWidgetGraphic.drawList(g, screenGrafic, desktopColors, 
																 inputsData);
									}
									else
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
