package de.schmiereck.noiseComp.desctopPage.widgets;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import de.schmiereck.noiseComp.desctopPage.ActivateWidgetListenerInterface;
import de.schmiereck.noiseComp.desctopPage.ClickedWidgetListenerInterface;
import de.schmiereck.noiseComp.soundData.SoundData;


/**
 * Verwaltet eine Liste aus {@link TrackGraficData}-Objekten.
 * Jeder Track enthält einen Generator.
 *
 * @author smk
 * @version 26.01.2004
 */
public class GeneratorsGraphicData
extends WidgetData
implements ActivateWidgetListenerInterface, ClickedWidgetListenerInterface
{
	public static final int HIT_PART_NONE		= 0;
	public static final int HIT_PART_GENERATOR	= 1;
	
	/**
	 * List of {@link TrackGraficData}-Objects.
	 */
	private Vector	tracksVector = new Vector();

	/**
	 * List of {@link TrackGraficData}-Objects with the String-Name as Key.
	 */
	private HashMap	tracksHash = new HashMap();
	
	/**
	 * Höhe eines Tracks in Points.
	 */
	private int trackHeight = 32;
	
	/**
	 * Breite der Spalte mit den Generatornamen in Points.
	 */
	private int generatorsLabelSizeX;
	
	/**
	 * Eine Sekunde sind 60 Points.
	 */
	private float generatorScaleX		= 60.0F;
	
	private ScrollbarData verticalScrollbarData;
	private ScrollbarData horizontalScrollbarData;
	
	/**
	 * Wird verwaltet um Zugriff auf die Liste der Generatoren zu haben.
	 * TODO Könnte man über eine etwas abstraktere Schnittstelle handeln, smk
	 */
	private SoundData soundData;

	/**
	 * Enthält einen Verweiss auf den gerade mit der Maus überfahrenen Generator.
	 */
	private TrackGraficData activeTrackGraficData = null;

	/**
	 * Der mit der Maus überfahrene Part des Generators.
	 * @see #HIT_PART_NONE
	 * @see #HIT_PART_GENERATOR
	 */
	private int hitGeneratorPart;
	private TrackGraficData selectedTrackGraficData;
	private boolean generatorIsSelected;
	
	/**
	 * Constructor.
	 * 
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 */
	public GeneratorsGraphicData(int posX, int posY, int sizeX, int sizeY,
								 int generatorsLabelSizeX,
								 SoundData soundData, 
								 ScrollbarData verticalScrollbarData, ScrollbarData horizontalScrollbarData)
	{
		super(posX, posY, sizeX, sizeY);
	
		this.generatorsLabelSizeX = generatorsLabelSizeX;
		this.soundData = soundData;
		this.verticalScrollbarData = verticalScrollbarData;
		this.horizontalScrollbarData = horizontalScrollbarData;
		
		// Momentane Anzahl Generatoren zu...
		//this.verticalScrollbarData.setScrollerLength(this.soundData.getGeneratorsCount());
		// ...maximal mögliche Anzahl angezeigter Generatoren.
		this.verticalScrollbarData.setScrollerSize(this.getSizeY() / this.getTrackHeight());
		
		// Momentan Breitester Generator (in Sekunden)...
		this.horizontalScrollbarData.setScrollerLength(20.0F);
		// ...zu Anzahl angezeigter Sekunden.
		this.horizontalScrollbarData.setScrollerSize((this.getSizeX() - this.generatorsLabelSizeX) / this.generatorScaleX);
	}

	/**
	 * @return the attribute {@link #generatorsLabelSizeX}.
	 */
	public int getGeneratorsLabelSizeX()
	{
		return this.generatorsLabelSizeX;
	}
	/**
	 * @param generatorsLabelSizeX is the new value for attribute {@link #generatorsLabelSizeX} to set.
	 */
	public void setGeneratorsLabelSizeX(int generatorsLabelSizeX)
	{
		this.generatorsLabelSizeX = generatorsLabelSizeX;
	}
	/**
	 * @return the attribute {@link #verticalScrollbarData}.
	 */
	public ScrollbarData getVerticalScrollbarData()
	{
		return this.verticalScrollbarData;
	}
	/**
	 * @return the attribute {@link #horizontalScrollbarData}.
	 */
	public ScrollbarData getHorizontalScrollbarData()
	{
		return this.horizontalScrollbarData;
	}
	/**
	 * @return the attribute {@link #soundData}.
	 */
	public SoundData getSoundData()
	{
		return this.soundData;
	}
	
	/**
	 * @return the attribute {@link #trackHeight}.
	 */
	public int getTrackHeight()
	{
		return this.trackHeight;
	}
	/**
	 * @return the attribute {@link #generatorScaleX}.
	 */
	public float getGeneratorScaleX()
	{
		return this.generatorScaleX;
	}

	/**
	 * @return die Anzahl Tracks um die gescrollt wurde.
	 */
	public int getTrackScrollPos()
	{
		return (int)this.verticalScrollbarData.getScrollerPos();
	}

	/**
	 * @return die Zeit, um die gescrollt wurde (in Sekunden).
	 */
	public float getTimeScrollPos()
	{
		return this.horizontalScrollbarData.getScrollerPos();
	}
	
	/**
	 * @return the count of {@link TrackGraficData}-Objects.
	 */
	public int getTracksCount()
	{
		return this.tracksVector.size();
	}

	/**
	 * @param generatorPos
	 * @return
	 */
	public TrackGraficData getTrack(int generatorPos)
	{
		return (TrackGraficData)this.tracksVector.get(generatorPos);
	}

	/**
	 * @see #activeTrackGraficData
	 */
	public void setActiveTrackGraficData(TrackGraficData trackGraficData, int hitGeneratorPart)
	{
		this.activeTrackGraficData = trackGraficData;
		this.hitGeneratorPart = hitGeneratorPart;
	}

	/**
	 * @see #activeTrackGraficData
	 */
	public TrackGraficData getActiveTrackGraficData()
	{
		return this.activeTrackGraficData;
	}

	public void addTrack(TrackGraficData trackGraficData)
	{
		synchronized (this)
		{
			int trackPos = this.tracksVector.size();
			trackGraficData.setTrackPos(trackPos);

			this.tracksVector.add(trackGraficData);
			this.tracksHash.put(trackGraficData.getName(), trackGraficData);
			this.soundData.addGenerator(trackGraficData.getGenerator());
	
			this.verticalScrollbarData.setScrollerLength(this.getTracksCount());
		}
	}

	public void removeSelectedTrack()
	{
		synchronized (this)
		{
			TrackGraficData selectedTrackGraficData = this.getSelectedTrackGraficData();
			
			if (selectedTrackGraficData != null)
			{
				this.deselectGenerator();
				
				int trackPos = selectedTrackGraficData.getTrackPos();

				this.tracksVector.remove(trackPos);
				
				for (int pos = trackPos; pos < this.tracksVector.size(); pos++)
				{
					((TrackGraficData)this.tracksVector.get(pos)).setTrackPos(pos);
				}
				this.tracksHash.remove(selectedTrackGraficData.getName());
				this.soundData.removeGenerator(trackPos);
				
				this.verticalScrollbarData.setScrollerLength(this.getTracksCount());
			}
		}
	}
	
	/**
	 * @return a Iterator over the {@link TrackGraficData}-Objects.
	 */
	public Iterator getTracksIterator()
	{
		return this.tracksVector.iterator();
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desctopPage.ActivateWidgetListenerInterface#notifyActivateWidget(de.schmiereck.noiseComp.desctopPage.widgets.WidgetData)
	 */
	public void notifyActivateWidget(WidgetData widgetData)
	{
		
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desctopPage.ActivateWidgetListenerInterface#notifyDeactivateWidget(de.schmiereck.noiseComp.desctopPage.widgets.WidgetData)
	 */
	public void notifyDeactivateWidget(WidgetData widgetData)
	{
		if (this.activeTrackGraficData != null)
		{
			this.activeTrackGraficData = null;
		}
	}
	/**
	 * @return the attribute {@link #hitGeneratorPart}.
	 */
	public int getHitGeneratorPart()
	{
		return this.hitGeneratorPart;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desctopPage.ClickedWidgetListenerInterface#notifyClickedWidget(de.schmiereck.noiseComp.desctopPage.widgets.WidgetData)
	 */
	public void notifyClickedWidget(WidgetData widgetData)
	{
		if (this.activeTrackGraficData != null)
		{
			this.selectedTrackGraficData = this.activeTrackGraficData;
			
			if (this.hitGeneratorPart != HIT_PART_NONE)
			{
				this.generatorIsSelected = true;
			}
			else
			{
				this.generatorIsSelected = false;
			}
		}
		else
		{
			this.deselectGenerator();
		}
	}
	private void deselectGenerator()
	{
		this.hitGeneratorPart = HIT_PART_NONE;
		this.selectedTrackGraficData = null;
		this.generatorIsSelected = false;
		this.activeTrackGraficData = null;
	}

	/**
	 * @return the attribute {@link #selectedTrackGraficData}.
	 */
	public TrackGraficData getSelectedTrackGraficData()
	{
		return this.selectedTrackGraficData;
	}
	/**
	 * @return the attribute {@link #generatorIsSelected}.
	 */
	public boolean getGeneratorIsSelected()
	{
		return this.generatorIsSelected;
	}

	/**
	 * @param name of the Generator.
	 * @return
	 */
	public TrackGraficData searchTrackGraficData(String name)
	{
		return (TrackGraficData)this.tracksHash.get(name);
	}
}
