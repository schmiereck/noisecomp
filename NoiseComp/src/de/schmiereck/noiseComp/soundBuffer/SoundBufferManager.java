package de.schmiereck.noiseComp.soundBuffer;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;

/**
 * <p>
 * 	Manages three {@link SoundBuffer}-Objects:<br/>
 * 	playing buffer: {@link #playingGeneratorBuffer}<br/>
 * 	waiting buffer: {@link #waitingGeneratorBuffer}<br/>
 * 	generating buffer: {@link #generatingGeneratorBuffer}<br/>
 * </p>
 * <p>
 * 	{@link #pollGenerate()}:<br/>
 * 	Verwaltet die SoundBuffer füllt diese bei Bedarf mit neu vom {@link #soundGenerator} generierten Werten.
 * </p>
 * <p>
 * 	{@link #read(byte[], int, int)}:<br/>
 * 	Schreibt bei jedem Aufruf Buffer-Inhalte raus und markiert diese als ausgegeben.
 * </p>
 *
 * @author smk
 * @version 21.01.2004
 */
public class SoundBufferManager 
extends AudioInputStream
{
//	private static int bitsPerMonoSample = 16;
//	private static int bytesPerMonoSample = 2;
//	private static int bytesPerStereoSample = 4;
	
	/**
	 * Wenn != <code>null</code>, wird dieser Buffer gerade abgespielt.
	 */
	private SoundBuffer playingGeneratorBuffer = null;
	
	/**
	 * Wenn != null, sind hier bereits generierte Daten enthalten, die
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
	
	/**
	 * Referenc to the actual used Generator that generates the output signal.
	 */
	//private GeneratorInterface soundGenerator;
	private SoundSourceLogic soundSourceLogic;
	
	/**
	 * A try to prevent for: see: isPollingExceptionPoint
	 */
	private boolean isPolling = false;
	
	/**
	 * Constructor.
	 * 
	 */
	public SoundBufferManager(AudioFormat audioFormat, 
							  long length, 
							  int bufferSize, 
							  SoundSourceLogic soundSourceLogic)
	{
		super(new ByteArrayInputStream(new byte[0]), 
			  new AudioFormat(audioFormat.getEncoding(),
							  audioFormat.getSampleRate(),
							  audioFormat.getSampleSizeInBits(), //bitsPerMonoSample,
							  audioFormat.getChannels(), //bytesPerMonoSample,
							  audioFormat.getFrameSize(), //bytesPerStereoSample,
							  audioFormat.getFrameRate(),
							  audioFormat.isBigEndian()), 
							  length);
		
		this.frameRate = audioFormat.getFrameRate();
		
		this.bufferSize = bufferSize;
		
		///this.soundGenerator = soundGenerator;
		this.soundSourceLogic = soundSourceLogic;
	}

	/* (non-Javadoc)
	 * @see java.io.InputStream#read(byte[], int, int)
	 */
	public int read(byte[] abData, int nOffset, int nLength)
		throws IOException
	{
		if (nLength % getFormat().getFrameSize() != 0)
		{
			throw new IOException("length must be an integer multiple of frame size");
		}

		int copyiedBytes;
		
		synchronized (this)
		{
			if (this.isPolling == true)
			{	
				if (this.playingGeneratorBuffer.getIsBufferIsEmpty())
				{
					if (this.waitingGeneratorBuffer == null)
					{
						// isPollingExceptionPoint
						//throw new IOException("waiting buffer is empty (maybe you should increase the Scheduler updates ?)");
					}
					
					if (this.waitingGeneratorBuffer != null)
					{
						// Der alte Playbuffer ist leer, muss neu generiert werden.
						this.generatingGeneratorBuffer = this.playingGeneratorBuffer;
						
						// der aktuellen wartebuffer wird jetzt abgespielt.
						this.playingGeneratorBuffer = this.waitingGeneratorBuffer;
						
						// nichts wartet mehr.
						this.waitingGeneratorBuffer = null;
					}
				}
				copyiedBytes = this.playingGeneratorBuffer.copyBytes(abData, nOffset, nLength);
			}
			else
			{
				copyiedBytes = 0;
			}
		}
		
		return copyiedBytes;
	}

	/**
	 * Generiert den nächsten Abschnitt in den leeren Warte-Buffer
	 * (wenn nötig, da leere Buffer warten).
	 */
	public void pollGenerate()
	{
		synchronized (this)
		{
			// Kein gefüllter Buffer wartet mehr, das er abgespielt wird ?
			if (this.waitingGeneratorBuffer == null)
			{
				//SoundBuffer generatingSoundBuffer = (SoundBuffer)this.generatingGeneratorBuffer.firstElement();

				if (this.generatingGeneratorBuffer == null)
				{
					//this.waitingGeneratorBuffer = new GeneratorBuffer(this.bufferSize, this.getFormat());
					this.generatingGeneratorBuffer = new SoundBuffer(this.bufferSize, 
																	 this.getFormat(), 
																	 this.soundSourceLogic);
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
				// Es wartet kein Buffer darauf, abgespielt zu werden ?
				if (this.waitingGeneratorBuffer == null)
				{	
					// Dann stellen wir den neu generierten in die leere Warteschleife.
					this.waitingGeneratorBuffer = this.generatingGeneratorBuffer;
					this.generatingGeneratorBuffer = null;
				}
			}
			
			this.isPolling = true;
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
	 * 
	 * @see java.io.InputStream#available()
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
	 * 
	 * @see java.io.InputStream#read()
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
			this.isPolling = false;
			
			this.actualFrame = 0;
		
			if (this.playingGeneratorBuffer != null)
			{	
				this.playingGeneratorBuffer.setBufferToEmpty();
			}
			
			this.waitingGeneratorBuffer = null;
		
			this.generatingGeneratorBuffer = null;
		}
	}
	/**
	 * @return the attribute {@link #generatingGeneratorBuffer}.
	 */
	public SoundBuffer getGeneratingGeneratorBuffer()
	{
		return this.generatingGeneratorBuffer;
	}
	/**
	 * @return the attribute {@link #playingGeneratorBuffer}.
	 */
	public SoundBuffer getPlayingGeneratorBuffer()
	{
		return this.playingGeneratorBuffer;
	}
	/**
	 * @return the attribute {@link #waitingGeneratorBuffer}.
	 */
	public SoundBuffer getWaitingGeneratorBuffer()
	{
		return this.waitingGeneratorBuffer;
	}

	/**
	 * @return 
	 * 			returns the {@link #actualFrame}.
	 */
	public long getActualFrame()
	{
		return this.actualFrame;
	}
}
