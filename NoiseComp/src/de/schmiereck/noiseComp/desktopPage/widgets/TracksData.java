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
extends ListWidgetData
implements ActivateWidgetListenerInterface, ClickedWidgetListenerInterface, HitWidgetListenerInterface
{
	public static final int HIT_PART_NONE		= 0;
	public static final int HIT_PART_GENERATOR	= 1;
	
	private ListWidgetGraphic listWidgetGraphic = null;

	/**
	 * List of {@link TrackData}-Objects.
	 */
	private Vector	tracksVector = new Vector();

	/**
	 * List of {@link TrackData}-Objects with the Generator as Key.
	 */
	private HashMap	tracksHash = new HashMap();
	
	/**
	 * Breite der Spalte mit den Generatornamen in Points.
	 */
	private int generatorsLabelSizeX;
	
	/**
	 * Eine Sekunde sind 60 Points.
	 */
	private float generatorScaleX		= 60.0F;
	
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
	private GeneratorSelectedListenerInterface generatorSelectedListener;
	
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
		super(posX, posY, sizeX, sizeY, 32, verticalScrollbarData, horizontalScrollbarData);
	
		this.generatorsLabelSizeX = generatorsLabelSizeX;
		this.soundData = soundData;

		// Momentane Anzahl Generatoren zu...
		//this.verticalScrollbarData.setScrollerLength(this.soundData.getGeneratorsCount());
		// ...maximal mögliche Anzahl angezeigter Generatoren.
		this.setVerticalScrollbarRange(this.getVerticalScrollbarRange());

		// Momentan Breitester Generator (in Sekunden)...
		this.setHorizontalScrollerLength(20.0F);
		// ...zu Anzahl angezeigter Sekunden.
		this.setHorizontalScrollbarRange(this.getHorizontalScrollbarRange());
	}

	public float getVerticalScrollbarRange()
	{
		return (float)this.getSizeY() / (float)this.getListEntryHeight();
	}
	
	public float getHorizontalScrollbarRange()
	{
		return (this.getSizeX() - this.generatorsLabelSizeX) / this.generatorScaleX;
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
	 * @return the attribute {@link #soundData}.
	 */
	public SoundData getSoundData()
	{
		return this.soundData;
	}

	/**
	 * @return the attribute {@link #generatorScaleX}.
	 */
	public float getGeneratorScaleX()
	{
		return this.generatorScaleX;
	}

	/**
	 * @see #generatorScaleX
	 */
	public void setGeneratorScaleX(float generatorScaleX)
	{
		this.generatorScaleX = generatorScaleX;
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
	
			this.setVerticalScrollerLength(this.getTracksCount());
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
				
				this.setVerticalScrollerLength(this.getTracksCount());
			}
		}
	}
	
	public void clearTracks()
	{
		synchronized (this)
		{
			this.deselectGenerator();
			
			this.tracksVector.clear();
			this.tracksHash.clear();
			this.soundData.clear();
			
			this.setVerticalScrollerLength(this.getTracksCount());
		}
	}
	
	/**
	 * @return die Zeit, um die gescrollt wurde (in Sekunden).
	 */
	public float getTimeScrollPos()
	{
		return this.getHorizontalScrollerPos();
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetData#getListEntrysIterator()
	 * @return a Iterator over the {@link TrackData}-Objects.
	 */
	public Iterator getListEntrysIterator()
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
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyClickedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData, int, int)
	 */
	public void notifyClickedWidget(WidgetData widgetData, int pointerPosX, int pointerPosY)
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
				
				if (this.generatorSelectedListener != null)
				{
					this.generatorSelectedListener.notifyGeneratorSelected(this.selectedTrackData);
				}
			}
			else
			{
				// Deselect the Generator.
				this.generatorIsSelected = false;
			
				if (this.generatorSelectedListener != null)
				{
					this.generatorSelectedListener.notifyGeneratorDeselected(this.selectedTrackData);
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
		if (this.generatorSelectedListener != null)
		{
			this.generatorSelectedListener.notifyGeneratorDeselected(trackData);
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
	 * @param name of the Generator to search for.
	 * @return Track of the generator or null.
	 */
	public TrackData searchTrackData(String name)
	{
		TrackData retTrackData = null;
		
		Iterator trackDataIterator = this.tracksHash.values().iterator();
		
		while (trackDataIterator.hasNext())
		{
			TrackData trackData = (TrackData)trackDataIterator.next();
			
			if (trackData.getName().equals(name))
			{
				retTrackData = trackData;
				break;
			}
		}
		
		return retTrackData;
	}
	
	/**
	 * @param generatorSelectedListener is the new value for attribute {@link #generatorSelectedListener} to set.
	 */
	public void setGeneratorSelectedListener(GeneratorSelectedListenerInterface generatorSelectedListener)
	{
		this.generatorSelectedListener = generatorSelectedListener;
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

	private static TrackData calcHitTrack(int pointerPosX, int pointerPosY, TracksData tracksData)
	{
		TrackData trackData = null;
		
		int screenTrackPos = Math.round((float)pointerPosY / (float)tracksData.getListEntryHeight());
		
		// Die Positionsnummer des Generators in der Liste.
		int generatorPos = screenTrackPos + (int)(tracksData.getVerticalScrollerPos() + 0.5F);
		
		// Innerhalb der Anzahl der vorhandneen generatoren ?
		if (generatorPos >= 0)
		{	
			if (generatorPos < tracksData.getTracksCount())
			{
				trackData = tracksData.getTrack(generatorPos);
			}
		}
		return trackData;
	}

	/**
	 * @return 	Der mit der Maus überfahrene Part des Generators:<br/>
	 * 			{@link GeneratorsGraphicData#HIT_PART_NONE}:		kein Hit.<br/>
	 * 			{@link GeneratorsGraphicData#HIT_PART_GENERATOR}:	Generator überfahren.
	 */
	private static int calcHitGeneratorPart(int pointerPosX, int pointerPosY, TracksData tracksData, TrackData trackData)
	{
		int sizeX = tracksData.getSizeX();

		int hitGeneratorPart = TracksData.HIT_PART_NONE;

		// Inerhalb des Trackbereichs ?
		if ((pointerPosX >= tracksData.getGeneratorsLabelSizeX()) && (pointerPosX <= sizeX))
		{
			float timePos = ((pointerPosX - tracksData.getGeneratorsLabelSizeX()) / tracksData.getGeneratorScaleX()) - tracksData.getVerticalScrollerPos();
			
			Generator generator = trackData.getGenerator();
			
			if ((timePos >= generator.getStartTimePos()) && (timePos <= generator.getEndTimePos()))
			{
				hitGeneratorPart = TracksData.HIT_PART_GENERATOR;
			}
		}
		return hitGeneratorPart;
	}


	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetData#getGraphicInstance()
	 */
	public ListWidgetGraphic getGraphicInstance()
	{
		if (this.listWidgetGraphic == null)
		{
			this.listWidgetGraphic = new TracksGraphic();
		}
		return this.listWidgetGraphic;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyDragWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData, int, int)
	 */
	public void notifyDragWidget(WidgetData selectedWidgetData, int pointerPosX, int pointerPosY)
	{
		// TODO Auto-generated method stub
		
	}
}
