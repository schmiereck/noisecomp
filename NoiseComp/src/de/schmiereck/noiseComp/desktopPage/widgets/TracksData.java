package de.schmiereck.noiseComp.desktopPage.widgets;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import de.schmiereck.noiseComp.desktopPage.ActivateWidgetListenerInterface;
import de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface;
import de.schmiereck.noiseComp.desktopPage.HitWidgetListenerInterface;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.soundData.SoundData;


/**
 * Verwaltet eine Liste aus {@link TrackData}-Objekten.
 * Jeder Track enthält einen Generator.
 *
 * @author smk
 * @version 26.01.2004
 */
public class TracksData
extends WidgetData
implements ActivateWidgetListenerInterface, ClickedWidgetListenerInterface, HitWidgetListenerInterface
{
	public static final int HIT_PART_NONE		= 0;
	public static final int HIT_PART_GENERATOR	= 1;
	
	/**
	 * List of {@link TrackData}-Objects.
	 */
	private Vector	tracksVector = new Vector();

	/**
	 * List of {@link TrackData}-Objects with the Generator as Key.
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
	private TrackData activeTrackData = null;

	/**
	 * Der mit der Maus überfahrene Part des Generators.
	 * @see #HIT_PART_NONE
	 * @see #HIT_PART_GENERATOR
	 */
	private int hitGeneratorPart;
	private TrackData selectedTrackData;
	private boolean generatorIsSelected;
	private GeneratorSelectedListenerInterface listenerLogic;
	
	/**
	 * Constructor.
	 * 
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 */
	public TracksData(int posX, int posY, int sizeX, int sizeY,
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
	 * @return the count of {@link TrackData}-Objects.
	 */
	public int getTracksCount()
	{
		return this.tracksVector.size();
	}

	/**
	 * @param generatorPos
	 * @return
	 */
	public TrackData getTrack(int generatorPos)
	{
		return (TrackData)this.tracksVector.get(generatorPos);
	}

	/**
	 * @see #activeTrackData
	 */
	public void setActiveTrackData(TrackData trackData, int hitGeneratorPart)
	{
		this.activeTrackData = trackData;
		this.hitGeneratorPart = hitGeneratorPart;
	}

	/**
	 * @see #activeTrackData
	 */
	public TrackData getActiveTrackData()
	{
		return this.activeTrackData;
	}

	public void addTrack(TrackData trackData)
	{
		synchronized (this)
		{
			int trackPos = this.tracksVector.size();
			trackData.setTrackPos(trackPos);

			this.tracksVector.add(trackData);
			//this.tracksHash.put(trackData.getName(), trackData);
			this.tracksHash.put(trackData.getGenerator(), trackData);
			this.soundData.addGenerator(trackData.getGenerator());
	
			this.verticalScrollbarData.setScrollerLength(this.getTracksCount());
		}
	}

	public void removeSelectedTrack()
	{
		synchronized (this)
		{
			TrackData selectedTrackData = this.getSelectedTrackData();
			
			if (selectedTrackData != null)
			{
				this.deselectGenerator();
				
				int trackPos = selectedTrackData.getTrackPos();

				this.tracksVector.remove(trackPos);
				
				for (int pos = trackPos; pos < this.tracksVector.size(); pos++)
				{
					((TrackData)this.tracksVector.get(pos)).setTrackPos(pos);
				}
				//this.tracksHash.remove(selectedTrackData.getName());
				this.tracksHash.remove(selectedTrackData.getGenerator());
				this.soundData.removeGenerator(trackPos);
				
				this.verticalScrollbarData.setScrollerLength(this.getTracksCount());
			}
		}
	}
	
	/**
	 * @return a Iterator over the {@link TrackData}-Objects.
	 */
	public Iterator getTracksIterator()
	{
		return this.tracksVector.iterator();
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ActivateWidgetListenerInterface#notifyActivateWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyActivateWidget(WidgetData widgetData)
	{
		
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ActivateWidgetListenerInterface#notifyDeactivateWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyDeactivateWidget(WidgetData widgetData)
	{
		if (this.activeTrackData != null)
		{
			this.activeTrackData = null;
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
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyClickedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyClickedWidget(WidgetData widgetData)
	{
		// Some track is Aktive (Rollover) ?
		if (this.activeTrackData != null)
		{
			// Use this as the new selected Track.
			this.selectedTrackData = this.activeTrackData;
			
			// Is the Generator in the selected Track Active ?
			if (this.hitGeneratorPart != HIT_PART_NONE)
			{
				// Select the Generator.
				this.generatorIsSelected = true;
				
				if (this.listenerLogic != null)
				{
					this.listenerLogic.notifyGeneratorSelected(this.selectedTrackData);
				}
			}
			else
			{
				// Deselect the Generator.
				this.generatorIsSelected = false;
			
				if (this.listenerLogic != null)
				{
					this.listenerLogic.notifyGeneratorDeselected(this.selectedTrackData);
				}
			}
		}
		else
		{
			this.deselectGenerator();
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyReleasedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyReleasedWidget(WidgetData selectedWidgetData)
	{
		
	}
	
	private void deselectGenerator()
	{
		TrackData trackData = this.selectedTrackData;
		this.hitGeneratorPart = HIT_PART_NONE;
		this.selectedTrackData = null;
		this.generatorIsSelected = false;
		this.activeTrackData = null;
		if (this.listenerLogic != null)
		{
			this.listenerLogic.notifyGeneratorDeselected(trackData);
		}
	}

	/**
	 * @return the attribute {@link #selectedTrackData}.
	 */
	public TrackData getSelectedTrackData()
	{
		return this.selectedTrackData;
	}
	/**
	 * @return the attribute {@link #generatorIsSelected}.
	 */
	public boolean getGeneratorIsSelected()
	{
		return this.generatorIsSelected;
	}

	/**
	 * @param generator Generator to search for.
	 * @return Track of the generator or null.
	 */
	public TrackData searchTrackData(Generator generator)
	{
		//String name = generator.getName();
		//return (TrackData)this.tracksHash.get(name);
		return (TrackData)this.tracksHash.get(generator);
	}
	/**
	 * @param listenerLogic is the new value for attribute {@link #listenerLogic} to set.
	 */
	public void setListenerLogic(GeneratorSelectedListenerInterface listenerLogic)
	{
		this.listenerLogic = listenerLogic;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.HitWidgetListenerInterface#notifyHitWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData, int, int)
	 */
	public void notifyHitWidget(WidgetData activeWidgetData, int pointerPosX, int pointerPosY)
	{
		TrackData trackData = TracksData.calcHitTrack(pointerPosX, pointerPosY, this);
		
		int hitGeneratorPart;
		
		if (trackData != null)
		{
			hitGeneratorPart = TracksData.calcHitGeneratorPart(pointerPosX, pointerPosY, this, trackData);
		}
		else
		{
			hitGeneratorPart = 0;
		}
		
		this.setActiveTrackData(trackData, hitGeneratorPart);
	}

	private static TrackData calcHitTrack(int pointerPosX, int pointerPosY, TracksData generatorsGraphicData)
	{
		TrackData trackData = null;
		
		// Die Positionsnummer des Generators in der Liste.
		int generatorPos = (pointerPosY / generatorsGraphicData.getTrackHeight()) + generatorsGraphicData.getTrackScrollPos();
		
		// Innerhalb der Anzahl der vorhandneen generatoren ?
		if (generatorPos < generatorsGraphicData.getTracksCount())
		{
			trackData = generatorsGraphicData.getTrack(generatorPos);
		}
		
		return trackData;
	}

	/**
	 * @return 	Der mit der Maus überfahrene Part des Generators:<br/>
	 * 			{@link GeneratorsGraphicData#HIT_PART_NONE}:		kein Hit.<br/>
	 * 			{@link GeneratorsGraphicData#HIT_PART_GENERATOR}:	Generator überfahren.
	 */
	private static int calcHitGeneratorPart(int pointerPosX, int pointerPosY, TracksData generatorsGraphicData, TrackData trackData)
	{
		int sizeX = generatorsGraphicData.getSizeX();

		int hitGeneratorPart = TracksData.HIT_PART_NONE;

		// Inerhalb des Trackbereichs ?
		if ((pointerPosX >= generatorsGraphicData.getGeneratorsLabelSizeX()) && (pointerPosX <= sizeX))
		{
			float timePos = ((pointerPosX - generatorsGraphicData.getGeneratorsLabelSizeX()) / generatorsGraphicData.getGeneratorScaleX()) - generatorsGraphicData.getTrackScrollPos();
			
			Generator generator = trackData.getGenerator();
			
			if ((timePos >= generator.getStartTimePos()) && (timePos <= generator.getEndTimePos()))
			{
				hitGeneratorPart = TracksData.HIT_PART_GENERATOR;
			}
		}
		return hitGeneratorPart;
	}
}
