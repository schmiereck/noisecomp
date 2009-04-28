package de.schmiereck.noiseComp.generator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/*
 * Created on 03.04.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	Manges a List of {@link TrackData} 
 * 	in a ordered {@link Vector} and 
 * 	a {@link HashMap} with the {@link Generator} as Key.
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
	private Vector<TrackData>	tracks = new Vector<TrackData>();

	/**
	 * List of {@link TrackData}-Objects with the {@link Generator} as Key.
	 */
	private HashMap<Generator, TrackData>	tracksHash = new HashMap<Generator, TrackData>();
	
	/**
	 * @return 
	 * 			the count of {@link #tracks}.
	 */
	public int getTracksCount()
	{
		return this.tracks.size();
	}

	/**
	 * @param generatorPos
	 * 			is the Position in the {@link #tracks}.
	 * @return
	 * 			the Track-Data.
	 */
	public TrackData getTrack(int generatorPos)
	{
		return (TrackData)this.tracks.get(generatorPos);
	}

	/**
	 * Adds the given Track 
	 * to the {@link #tracks} list and 
	 * to the {@link #tracksHash} list.
	 * 
	 * @param trackData
	 * 			is the Track.
	 */
	public synchronized void addTrack(TrackData trackData)
	{
		int trackPos = this.getTracksCount();
		
		trackData.setTrackPos(trackPos);

		this.tracks.add(trackData);

		this.tracksHash.put(trackData.getGenerator(), trackData);
	}

	/**
	 * Removes the Track at the given Position
	 * from {@link #tracks} list and 
	 * from the {@link #tracksHash} list.
	 * 
	 * @param trackPos
	 * 			is the Position in the {@link #tracks} list.
	 * @return
	 * 			the removed Track.
	 */
	public synchronized TrackData removeSelectedTrack(int trackPos)
	{
		TrackData retTrackData;

		retTrackData = (TrackData)this.tracks.remove(trackPos);
			
		for (int pos = trackPos; pos < this.tracks.size(); pos++)
		{
			this.tracks.get(pos).setTrackPos(pos);
		}
		
		Generator generator = retTrackData.getGenerator();
			
		this.tracksHash.remove(generator);
		
		return retTrackData;
	}
	
	/**
	 * Clear the {@link #tracks} and the {@link #tracksHash} lists.
	 */
	public synchronized void clearTracks()
	{
		this.tracks.clear();
		this.tracksHash.clear();
	}

	/**
	 * @return
	 * 			the iterator of {@link #tracks}.
	 */
	public Iterator<TrackData> getTracksIterator()
	{
		return this.tracks.iterator();
	}

	/**
	 * @param generator 
	 * 			is the Generator to search for.
	 * @return 
	 * 			the Track of the generator or <code>null</code>.
	 */
	public TrackData searchTrackData(Generator generator)
	{
		//String name = generator.getName();
		//return (TrackData)this.tracksHash.get(name);
		return (TrackData)this.tracksHash.get(generator);
	}

	/**
	 * @param name 
	 * 			of the Generator to search for.
	 * @return 
	 * 			the Track of the generator or <code>null</code>.
	 */
	public TrackData searchTrackData(String name)
	{
		TrackData retTrackData = null;
		
		Iterator<TrackData> trackDataIterator = this.tracksHash.values().iterator();
		
		while (trackDataIterator.hasNext())
		{
			TrackData trackData = trackDataIterator.next();
			
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
	 * 			is the source Position in the {@link #tracks}.
	 * @param tagetTrackPos
	 * 			is the target Position in the {@link #tracks}.
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
			
			this.tracks.setElementAt(sourceTrackData, tagetTrackPos);
			this.tracks.setElementAt(targetTrackData, sourceTrackPos);
			
			targetTrackData.setTrackPos(sourceTrackPos);
			sourceTrackData.setTrackPos(tagetTrackPos);
		}
	}
}
