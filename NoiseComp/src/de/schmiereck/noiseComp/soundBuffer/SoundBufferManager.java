package de.schmiereck.noiseComp.soundBuffer;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import de.schmiereck.noiseComp.generator.GeneratorInterface;

/**
 * <p>
 * 	{@link #pollGenerate()}:<br/>
 * 	Verwaltet die SoundBuffer füllt diese bei Bedarf mit neu vom
 * 	SinusGenerator generierten Werten.
 * </p>
 * <p>
 * 	{@link #read(byte[], int, int)}:<br/>
 * 	Schreibt bei jedem Aufruf Buffer-Inhalte raus und markiert diese als 
 * 	ausgegeben.
 * </p>
 *
 * @author smk
 * @version 21.01.2004
 */
public class SoundBufferManager 
extends AudioInputStream
{
	private static int bitsPerMonoSample = 16;
	private static int bytesPerMonoSample = 2;
	private static int bytesPerStereoSample = 4;
	
	/**
	 * Wenn != null, wird dieser Buffer gerade abgespielt.
	 */
	private SoundBuffer playingGeneratorBuffer;
	
	/**
	 * Wenn != null, sind hier bereits generierte daten enthalten, die
	 * als nächstes abgespielt werden, wenn de {@link #playingGeneratorBuffer}
	 * leer ist.
	 */
	private SoundBuffer waitingGeneratorBuffer = null;
	
	/**
	 * Wenn der {@link #playingGeneratorBuffer} leer ist, wird er in diese
	 * Variable eingetragen. Dieser Buffer enthält niemals Daten die
	 * angespielt werden müssen.
	 */
	private SoundBuffer generatingGeneratorBuffer = null;
	
	private long actualFrame = 0;
	
	private float frameRate;
	
	private int bufferSize;
	
	private GeneratorInterface soundGenerator;
	
	/**
	 * Constructor.
	 * 
	 */
	public SoundBufferManager(AudioFormat audioFormat, float frameRate, long length, int bufferSize, 
							  GeneratorInterface soundGenerator)
	{
		super(new ByteArrayInputStream(new byte[0]), 
			  new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
				audioFormat.getSampleRate(),
				bitsPerMonoSample,
				bytesPerMonoSample,
				bytesPerStereoSample,
				frameRate,
				audioFormat.isBigEndian()), length);
		
		this.frameRate = frameRate;
		
		this.bufferSize = bufferSize;
		
		this.soundGenerator = soundGenerator;
	}

	public int read(byte[] abData, int nOffset, int nLength)
	throws IOException
	{
		if (nLength % getFormat().getFrameSize() != 0)
		{
			throw new IOException("length must be an integer multiple of frame size");
		}

		synchronized (this)
		{
			if (this.playingGeneratorBuffer.getIsBufferIsEmpty())
			{
				if (this.waitingGeneratorBuffer == null)
				{
					throw new IOException("waiting buffer is empty");
				}
				
				// Der alte playbuffer ist leer, muss neu generiert werden.
				this.generatingGeneratorBuffer = this.playingGeneratorBuffer;
				
				// der aktuellen wartebuffer wird jetzt abgespielt.
				this.playingGeneratorBuffer = this.waitingGeneratorBuffer;
				
				// nichts wartet mehr.
				this.waitingGeneratorBuffer = null;
			}
		}
		
		int copyiedBytes = this.playingGeneratorBuffer.copyBytes(abData, nOffset, nLength);
		
		return copyiedBytes;
	}
	
	/**
	 * Generiert den nächsten Abschnitt in den leeren Warte-Buffer.
	 *
	 */
	public void pollGenerate()
	{
		synchronized (this)
		{
			// Kein gefüllter Buffer wartet mehr, das er abgespielt wird ?
			if (this.waitingGeneratorBuffer == null)
			{
				if (this.generatingGeneratorBuffer == null)
				{
					//this.waitingGeneratorBuffer = new GeneratorBuffer(this.bufferSize, this.getFormat());
					this.generatingGeneratorBuffer = new SoundBuffer(this.bufferSize, this.getFormat(), this.soundGenerator);
				}
				
				// Dann generieren wir einen.
				int generatedFrames = this.generatingGeneratorBuffer.generate(this.actualFrame); 
	
				this.actualFrame += generatedFrames;
			}
			
			// Ist momentan gar kein Play Buffer eingetragen ?
			if (this.playingGeneratorBuffer == null)
			{
				// Dann tragen wir den neu generierten gleich zum abspielen ein.
				this.playingGeneratorBuffer = this.generatingGeneratorBuffer;
				this.generatingGeneratorBuffer = null;
			}
			else
			{	
				// Es wartet kein Buffer darauf, abgelspielt zu werden ?
				if (this.waitingGeneratorBuffer == null)
				{	
					// Dann stellen wir den neu generierten in die leere Warteschleife.
					this.waitingGeneratorBuffer = this.generatingGeneratorBuffer;
					this.generatingGeneratorBuffer = null;
				}
			}
		}
	}
	
	/**	
	 * Returns the number of bytes that can be read without blocking.
	 * Since there is no blocking possible here, we simply try to
	 * return the number of bytes available at all. In case the
	 * length of the stream is indefinite, we return the highest
	 * number that can be represented in an integer. If the length
	 * if finite, this length is returned, clipped by the maximum
	 * that can be represented.
	 */
	public int available()
	{
		int len = 0;
		
		if (this.playingGeneratorBuffer != null)
		{
			len += this.playingGeneratorBuffer.getGeneratedBytesInBuffer();
		}
		if (this.waitingGeneratorBuffer != null)
		{
			len += this.waitingGeneratorBuffer.getGeneratedBytesInBuffer();
		}
		
		return len;
	}
	
	/**
	 * this method should throw an IOException if the frame size is not 1.
	 * Since we currently always use 16 bit samples, the frame size is
	 * always greater than 1. So we always throw an exception.
	 */
	public int read()
	throws IOException
	{
		throw new IOException("cannot use this method currently");
	}

	public float getActualTime()
	{
		return this.actualFrame / this.frameRate;
	}
	
	public void stopGenerate()
	{
		synchronized (this)
		{
			this.actualFrame = 0;
		
			this.playingGeneratorBuffer.setBufferToEmpty();
			
			this.waitingGeneratorBuffer = null;
		
			this.generatingGeneratorBuffer = null;
		}
	}
}
