package de.schmiereck.noiseComp.generator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/*
 * Created on 03.04.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	TODO docu type
 * </p>
 * 
 * @author smk
 * @version <p>03.04.2005:	created, smk</p>
 */
public class TracksData
{
	/**
	 * List of {@link TrackData}-Objects.
	 */
	private Vector	tracksVector = new Vector();

	/**
	 * List of {@link TrackData}-Objects with the Generator as Key.
	 */
	private HashMap	tracksHash = new HashMap();
	
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

	public void addTrack(TrackData trackData)
	{
		synchronized (this)
		{
			int trackPos = this.getTracksCount();
			
			trackData.setTrackPos(trackPos);

			this.tracksVector.add(trackData);

			this.tracksHash.put(trackData.getGenerator(), trackData);
		}
	}

	public TrackData removeSelectedTrack(int trackPos)
	{
		TrackData retTrackData;
		
		synchronized (this)
		{
			retTrackData = (TrackData)this.tracksVector.remove(trackPos);
				
			for (int pos = trackPos; pos < this.tracksVector.size(); pos++)
			{
				((TrackData)this.tracksVector.get(pos)).setTrackPos(pos);
			}
			
			Generator generator = retTrackData.getGenerator();
				
			this.tracksHash.remove(generator);
		}
		
		return retTrackData;
	}
	
	public void clearTracks()
	{
		synchronized (this)
		{
			this.tracksVector.clear();
			this.tracksHash.clear();
		}
	}

	public Iterator getTracksIterator()
	{
		return this.tracksVector.iterator();
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
	 * @param sourceTrackPos
	 * @param tagetTrackPos
	 */
	public void switchTracksByPos(int sourceTrackPos, int tagetTrackPos)
	{
		if ((sourceTrackPos >= 0) &&
			(tagetTrackPos >= 0) &&
			(sourceTrackPos < this.getTracksCount()) &&
			(tagetTrackPos < this.getTracksCount()))
		{
			TrackData sourceTrackData = this.getTrack(sourceTrackPos);
			TrackData targetTrackData = this.getTrack(tagetTrackPos);
			
			this.tracksVector.setElementAt(sourceTrackData, tagetTrackPos);
			this.tracksVector.setElementAt(targetTrackData, sourceTrackPos);
			
			targetTrackData.setTrackPos(sourceTrackPos);
			sourceTrackData.setTrackPos(tagetTrackPos);
		}
	}
}
