package de.schmiereck.noiseComp.soundBuffer;

import javax.sound.sampled.AudioFormat;

import de.schmiereck.noiseComp.generator.GeneratorInterface;
import de.schmiereck.noiseComp.generator.SoundSample;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;

/**
 * TODO docu
 *
 * @author smk
 * @version 21.01.2004
 */
public class SoundBuffer
{
	/**
	 * Buffered Data.
	 */
	private byte[]			bufferData;
	
	/**
	 * Beschreibung des zu generierenden Formates.
	 */
	private AudioFormat audioFormat;
	
	/**
	 * Maximale-Länge des Puffers.
	 */
	private int maxBufferSize = 16000; //32000;
	
	/**
	 * Startposition der verbliebenen Bytes im Buffer.
	 */
	private int generatedBytesStartBufferPos = 0;
	
	/**
	 * Anzahl der im Buffer noch mit Daten belegten Bytes.
	 */
	private int generatedBytesInBuffer = 0;
	
	/**
	 * true, wenn genrate() aufgerufen werden kann, 
	 * da der Buffer leer ist.
	 */
	private boolean bufferIsEmpty = true;
	
	/**
	 * Faktor mit dem ein float zwischen 0.0 und 1.0 multiploziert
	 * werden muss, um einen Integer-Wert eines Samples zu erhalten.
	 */
	private float intAmplitude;
	
	/**
	 * Referenz auf den Soundgenerator der die einzelnen Samples
	 * für jeden Frame erzeugt.
	 */
	//private GeneratorInterface soundGenerator;
	private SoundSourceLogic soundSourceLogic;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public SoundBuffer(int bufferSize, AudioFormat audioFormat, 
			SoundSourceLogic soundSourceLogic)
			//GeneratorInterface soundGenerator)
	{
		super();
		
		this.maxBufferSize = bufferSize;
		
		this.audioFormat = audioFormat;
		
		this.soundSourceLogic = soundSourceLogic;
		
		this.bufferData = new byte[this.maxBufferSize];

		this.intAmplitude = (float) (Math.pow(2, this.audioFormat.getSampleSizeInBits() - 1) - 1);
	}

	/**
	 * Generiert ab dem startFrame soviel Sound wie in den Buffer passt und 
	 * liefert die Anzahl generierter Frames zurück.
	 * 
	 * @param startFrame Frame der Timeline des Sounds, ab dem generiert werden soll.
	 * @return Anzahl der in den Buffer generierten Frames.
	 */
	public int generate(long startFrame)
	{
		//System.out.println("startFrame:" + startFrame);
		
		if (this.bufferIsEmpty == false)
		{
			throw new RuntimeException("buffer is not empty");
		}
		
		int frameSize = this.audioFormat.getFrameSize();
		
		// Anzahl Frames die in den Buffer passen.
		int frameCount = this.maxBufferSize / frameSize;
		
		int byteBufferPos = 0;
		
		for (int nFrame = 0; nFrame < frameCount; nFrame++)
		{
			long frame = nFrame + startFrame;
			
			this.generateFrame(frame, frameSize, this.bufferData, byteBufferPos);
			
			byteBufferPos += frameSize;
		}
		
		this.generatedBytesInBuffer = byteBufferPos;
		
		if (this.generatedBytesInBuffer > 0)
		{	
			this.bufferIsEmpty = false;
		}
		
		return frameCount;
	}
	
	/**
	 * Generates a stereo output for the given frame.
	 * Uses the generatted output value for the frame position 
	 * of the {@link #soundGenerator}-Object of the SoundBuffer.
	 * 
	 * @param frame			is the absolute frame position in the song-sound (depends on sampleRate).
	 * @param frameSize		(don't remember ???)
	 * @param bufferData	is a byte buffer for the output. 
	 * 						His internal structure depends on the audio format of the buffer.  
	 * @param bufferPos		Is the byte position, the next calculated frame should writen in the buffer.
	 */
	private void generateFrame(long frame, int frameSize, byte bufferData[], int bufferPos)
	{
		if (this.soundSourceLogic != null)
		{	
			int leftSampleValue;
			int	rightSampleValue;
			
			//SoundSample soundSample = this.soundGenerator.generateFrameSample(frame, null);
			SoundSample soundSample = this.soundSourceLogic.generateFrameSample(frame);
			
			if (soundSample != null)
			{	
				leftSampleValue = Math.round(soundSample.getLeftValue() * this.intAmplitude);
				rightSampleValue = Math.round(soundSample.getRightValue() * this.intAmplitude);
			}
			else
			{	
				leftSampleValue = 0;
				rightSampleValue = 0;
			}
	
			// this is for 16 bit stereo, little endian
			// left:
			bufferData[bufferPos + 0] = (byte) (leftSampleValue & 0xFF);
			bufferData[bufferPos + 1] = (byte) ((leftSampleValue >>> 8) & 0xFF);
			// right:
			bufferData[bufferPos + 2] = (byte) (rightSampleValue & 0xFF);
			bufferData[bufferPos + 3] = (byte) ((rightSampleValue >>> 8) & 0xFF);
		}
	}
	
	/**
	 * <p>
	 * 	Liefert die Anzahl der noch im Buffer zum abspielen übrigen Bytes.
	 * 	Wenn 0 geliefert wird, ist der Buffer leer.
	 * </p>
	 * <p>
	 * 	Nur gültig, wenn {@link #bufferIsEmpty} false ist.
	 * </p>
	 * 
	 * @return the attribute {@link #generatedBytesInBuffer}.
	 */
	public int getGeneratedBytesInBuffer()
	{
		return this.generatedBytesInBuffer - this.generatedBytesStartBufferPos;
	}

	/**
	 * Copiert soviele wie möglich Bytes aus dem Buffer in abData (ab die Position offset).
	 * Es wird versucht maximal length bytes zu kopieren, wenn weniger vorhanden sind, 
	 * werden nur die vorhandnen kopiert.
	 * Es wird die Anzahl der bytes zurückgeliefert, die kopiert werden konnte.
	 * 
	 * @param abData
	 * @param offset
	 * @param length
	 * @return
	 */
	public int copyBytes(byte[] abData, int offset, int length)
	{
		int len = this.getGeneratedBytesInBuffer();
		
		int bytesCanCopied = Math.min(len, length);
		
		System.arraycopy(this.bufferData, 0, abData, offset, bytesCanCopied);
		
		this.generatedBytesStartBufferPos += len;
		
		if (this.generatedBytesStartBufferPos == this.generatedBytesInBuffer)
		{
			this.setBufferToEmpty();
		}
		
		return bytesCanCopied;
	}
	/**
	 * @return the attribute {@link #bufferIsEmpty}.
	 */
	public boolean getIsBufferIsEmpty()
	{
		return this.bufferIsEmpty;
	}

	/**
	 * 
	 */
	public void setBufferToEmpty()
	{
		this.generatedBytesStartBufferPos = 0;
		this.generatedBytesInBuffer = 0;
		this.bufferIsEmpty = true;
	}
}
