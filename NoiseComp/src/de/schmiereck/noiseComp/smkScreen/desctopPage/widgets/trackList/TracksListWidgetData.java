package de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.trackList;

import java.util.Iterator;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.TrackData;
import de.schmiereck.noiseComp.generator.TracksData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.ActivateWidgetListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.ClickedWidgetListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.HitWidgetListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.GeneratorSelectedListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ListWidgetData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ListWidgetGraphic;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ScrollbarData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.WidgetData;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.controller.DataChangedObserver;


/**
 * Verwaltet eine Liste aus {@link TrackData}-Objekten.
 * Jeder Track enthält einen Generator.
 *
 * @author smk
 * @version 26.01.2004
 */
public class TracksListWidgetData
extends ListWidgetData
implements ActivateWidgetListenerInterface, ClickedWidgetListenerInterface, HitWidgetListenerInterface
{
	public static final int HIT_PART_NONE		= 0;
	public static final int HIT_PART_GENERATOR	= 1;
	
	private ListWidgetGraphic listWidgetGraphic = null;

	//private TracksData tracksData = new TracksData();
	private TracksData tracksData = null;
	
	/**
	 * Breite der Spalte mit den Generatornamen in Points.
	 */
	private int generatorsLabelSizeX;
	
	/**
	 * Eine Sekunde sind 60 Points.
	 */
	private float generatorScaleX		= 60.0F;
	
	/**
	 * Wird verwaltet um Zugriff auf die aktuelle Abspielposition zu haben.
	 * TODO Könnte man über eine etwas abstraktere Schnittstelle handeln, smk
	 */
	private SoundData soundData;

	/**
	 * Enthält einen Verweiss auf den gerade mit der Maus überfahrenen Generator.
	 */
	private TrackData activeTrackData = null;

	/**
	 * <code>true</code> if a track is dragged with the mouse.
	 */
	private boolean doDragging = false;

	/**
	 * Der mit der Maus überfahrene Part des Generators.
	 * @see #HIT_PART_NONE
	 * @see #HIT_PART_GENERATOR
	 */
	private int hitGeneratorPart = HIT_PART_NONE;
	
	private TrackData selectedTrackData = null;
	private boolean generatorIsSelected = false;
	private GeneratorSelectedListenerInterface generatorSelectedListener = null;
	
	private TrackData draggedTrackData = null;
	private int dragTargetOffsetY = 0;
	private int dragTargetPosY = 0;
	
	/**
	 * Constructor.
	 * 
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 * @param soundData	is used for getting the actual play time position.
	 */
	public TracksListWidgetData(ControllerData controllerData,
								  DataChangedObserver dataChangedObserver,
								int posX, int posY, int sizeX, int sizeY,
								int generatorsLabelSizeX,
								SoundData soundData, 
								ScrollbarData verticalScrollbarData, ScrollbarData horizontalScrollbarData)
	{
		super(controllerData, dataChangedObserver,
			  posX, posY, sizeX, sizeY, 32, verticalScrollbarData, horizontalScrollbarData);
	
		this.generatorsLabelSizeX = generatorsLabelSizeX;
		this.soundData = soundData;

		// Momentane Anzahl Generatoren zu...
		//this.verticalScrollbarData.setScrollerLength(this.soundData.getGeneratorsCount());
		// ...maximal m�gliche Anzahl angezeigter Generatoren.
		this.setVerticalScrollbarRange((float)this.getSizeY() / (float)this.getListEntryHeight());

		// Momentan Breitester Generator (in Sekunden)...
		this.setHorizontalScrollerLength(20.0F);
		// ...zu Anzahl angezeigter Sekunden.
		this.setHorizontalScrollbarRange((this.getSizeX() - this.generatorsLabelSizeX) / this.generatorScaleX);
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

		this.dataChangedVisible();
	}

	/**
	 * @return the attribute {@link #soundData}.
	 */
	public float getSoundPlayTimePos()
	{
		float soundPlayTimePos = this.soundData.getSoundBufferManager().getActualTime();
		
		return soundPlayTimePos;
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

		this.dataChangedVisible();
	}
	
	/**
	 * @see TracksData#getTracksCount()
	 */
	public int getTracksCount()
	{
		return this.tracksData.getTracksCount();
	}

	/**
	 * @see TracksData#getTrack(int)
	 */
	public TrackData getTrack(int generatorPos)
	{
		return this.tracksData.getTrack(generatorPos);
	}

	/**
	 * @see #setActiveTrackData(TrackData)
	 * @see #hitGeneratorPart
	 */
	public void setActiveTrackData(TrackData trackData, int hitGeneratorPart)
	{
		this.setActiveTrackData(trackData);
		this.hitGeneratorPart = hitGeneratorPart;
	}

	/**
	 * @see #activeTrackData
	 */
	public void setActiveTrackData(TrackData trackData)
	{
		this.activeTrackData = trackData;
		
		this.dataChangedVisible();
	}
	
	/**
	 * @see #activeTrackData
	 */
	public TrackData getActiveTrackData()
	{
		return this.activeTrackData;
	}
	
	/**
	 * @return
	 * 		true, if some track is active.
	 */
	private boolean getIsTrackActive()
	{
		return this.getActiveTrackData() != null;
	}
	/*
	public void addTrack(TrackData trackData)
	{
		this.tracksData.addTrack(trackData);
	
		this.setVerticalScrollerLength(this.getTracksCount());

		this.dataChangedVisible();
	}
	*/
	
	public Generator removeSelectedTrack()
	{
		Generator retGenerator;
		
		synchronized (this)
		{
			TrackData selectedTrackData = this.getSelectedTrackData();
			
			if (selectedTrackData != null)
			{
				this.deselectGenerator();
				
				int trackPos = selectedTrackData.getTrackPos();

				this.tracksData.removeSelectedTrack(trackPos);
				
				this.setVerticalScrollerLength(this.getTracksCount());

				retGenerator = selectedTrackData.getGenerator();

				this.dataChangedVisible();
			}
			else
			{
				retGenerator = null;
			}
		}

		return retGenerator;
	}

	/*
	public void clearTracks()
	{
		synchronized (this)
		{
			this.deselectGenerator();
			
			this.tracksData.clearTracks();
			
			this.setVerticalScrollerLength(this.getTracksCount());

			this.dataChangedVisible();
		}
	}
	*/
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
	public Iterator<TrackData> getListEntrysIterator()
	{
		return this.tracksData.getTracksIterator();
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
		// Some track is active?
		if (this.getIsTrackActive() == true)
		{
			this.setActiveTrackData(null);
		}

		this.dataChangedVisible();
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
		if (this.getIsTrackActive() == true)
		{
			// Use this as the new selected Track.
			this.selectedTrackData = this.getActiveTrackData();
			
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

		this.dataChangedVisible();
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyReleasedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyReleasedWidget(WidgetData selectedWidgetData)
	{
		if (this.doDragging == true)
		{
			this.doDragging = false;
			System.out.println("stop drag");
		}
	}
	
	private void deselectGenerator()
	{
		TrackData trackData = this.selectedTrackData;
		this.hitGeneratorPart = HIT_PART_NONE;
		this.selectedTrackData = null;
		this.generatorIsSelected = false;
		this.setActiveTrackData(null);
		
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
		return this.tracksData.searchTrackData(generator);
	}

	/**
	 * @param name of the Generator to search for.
	 * @return Track of the generator or null.
	 */
	public TrackData searchTrackData(String name)
	{
		return this.tracksData.searchTrackData(name);
	}
	
	/**
	 * @param generatorSelectedListener is the new value for attribute {@link #generatorSelectedListener} to set.
	 */
	public void registerGeneratorSelectedListener(GeneratorSelectedListenerInterface generatorSelectedListener)
	{
		this.generatorSelectedListener = generatorSelectedListener;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.HitWidgetListenerInterface#notifyHitWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData, int, int)
	 */
	public void notifyHitWidget(WidgetData activeWidgetData, int pointerPosX, int pointerPosY)
	{
		TrackData trackData = TracksListWidgetData.calcHitTrack(pointerPosX, pointerPosY, this);

		int hitGeneratorPart;
		
		if (trackData != null)
		{
			hitGeneratorPart = TracksListWidgetData.calcHitGeneratorPart(pointerPosX, pointerPosY, this, trackData);
		}
		else
		{
			hitGeneratorPart = 0;
		}
		
		this.setActiveTrackData(trackData, hitGeneratorPart);

		this.dataChangedVisible();
	}

	private static int calcHitTrackPos(int pointerPosX, int pointerPosY, TracksListWidgetData tracksData)
	{
		int screenTrackPos = (pointerPosY / tracksData.getListEntryHeight());
		
		// Die Positionsnummer des Generators in der Liste.
		int generatorPos = screenTrackPos + (int)(tracksData.getVerticalScrollerPos());
		
		return generatorPos;
	}
	
	private static TrackData calcHitTrack(int pointerPosX, int pointerPosY, TracksListWidgetData tracksData)
	{
		TrackData trackData = null;
		
		// Die Positionsnummer des Generators in der Liste.
		int generatorPos = TracksListWidgetData.calcHitTrackPos(pointerPosX, pointerPosY, tracksData);
		
		// Innerhalb der Anzahl der vorhandenen Generatoren ?
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
	 * @return 	Der mit der Maus �berfahrene Part des Generators:<br/>
	 * 			{@link GeneratorsGraphicData#HIT_PART_NONE}:		kein Hit.<br/>
	 * 			{@link GeneratorsGraphicData#HIT_PART_GENERATOR}:	Generator �berfahren.
	 */
	private static int calcHitGeneratorPart(int pointerPosX, int pointerPosY, TracksListWidgetData tracksData, TrackData trackData)
	{
		int sizeX = tracksData.getSizeX();

		int hitGeneratorPart = TracksListWidgetData.HIT_PART_NONE;

		// Inerhalb des Trackbereichs ?
		if ((pointerPosX >= tracksData.getGeneratorsLabelSizeX()) && (pointerPosX <= sizeX))
		{
			float timePos = ((pointerPosX - tracksData.getGeneratorsLabelSizeX()) / tracksData.getGeneratorScaleX()) + tracksData.getHorizontalScrollerPos();
			
			Generator generator = trackData.getGenerator();
			
			if ((timePos >= generator.getStartTimePos()) && (timePos <= generator.getEndTimePos()))
			{
				hitGeneratorPart = TracksListWidgetData.HIT_PART_GENERATOR;
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
			this.listWidgetGraphic = new TracksListWidgetGraphic();
		}
		return this.listWidgetGraphic;
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyDragWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData, int, int)
	 */
	public void notifyDragWidget(WidgetData selectedWidgetData, 
								 int pointerPosX, int pointerPosY)
	{
		int widgetPointerPosY = pointerPosY - this.getPosY();
		
		// No dragging jet?
		if (this.doDragging == false)
		{
			// Some Track is active?
			if (this.getIsTrackActive() == true)
			{
				System.out.println("start drag");
				this.draggedTrackData = this.getActiveTrackData();

				this.doDragging = true;
				
				//int tagetTrackPos = TracksListWidgetData.calcHitTrackPos(pointerPosX, pointerPosY, this);

				this.dragTargetOffsetY = widgetPointerPosY % this.getListEntryHeight();
				this.dragTargetPosY = pointerPosY;
			}
		}
		else
		{
			this.dragTargetPosY = pointerPosY;
			
			int tagetTrackPos = TracksListWidgetData.calcHitTrackPos(pointerPosX, 
																	 (widgetPointerPosY - this.dragTargetOffsetY) + 
																	 (this.getListEntryHeight() / 2), 
																	 this);
			
			if (tagetTrackPos != this.draggedTrackData.getTrackPos())
			{
				System.out.println("notifyDragWidget: " + tagetTrackPos + " - " + widgetPointerPosY);
				
				int sourceTrackPos = this.draggedTrackData.getTrackPos();
				
				this.tracksData.switchTracksByPos(sourceTrackPos, tagetTrackPos);
			}

			//System.out.println("notifyDragWidget");
			/*
			TrackData activeTrackData = this.getActiveTrackData();
			
			if (activeTrackData != null)
			{
				if (activeTrackData.getTrackPos() != this.draggedTrackData.getTrackPos())
				{
					System.out.println("notifyDragWidget: " + activeTrackData.getTrackPos());
				}
			}
			*/
		}
	}

	/**
	 * @see #soundData#getGenerators()
	 */
	///public Generators getGenerators()
	//{
	//	return this.soundData.getGenerators();
	//}
	/**
	 * @return returns the {@link #doDragging}.
	 */
	protected boolean getDoDragging()
	{
		return this.doDragging;
	}
	/**
	 * @return returns the {@link #dragTargetOffsetY}.
	 */
	protected int getDragTargetOffsetY()
	{
		return this.dragTargetOffsetY;
	}
	/**
	 * @return returns the {@link #dragTargetPosY}.
	 */
	protected int getDragTargetPosY()
	{
		return this.dragTargetPosY;
	}
	/**
	 * @param tracksData to set {@link #tracksData}.
	 */
	public void setTracksData(TracksData tracksData)
	{
		this.tracksData = tracksData;

		this.hitGeneratorPart = HIT_PART_NONE;
		this.selectedTrackData = null;
		this.generatorIsSelected = false;
		
		this.draggedTrackData = null;
		this.dragTargetOffsetY = 0;
		this.dragTargetPosY = 0;

		this.activeTrackData = null;
		this.doDragging = false;
	}

	/**
	 * @return 
	 * 			returns the {@link #soundData}.
	 */
	public SoundData getSoundData()
	{
		return this.soundData;
	}
}
