package de.schmiereck.noiseComp.generator;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import com.sun.media.sound.WaveFileReader;
import de.schmiereck.noiseComp.WaveResourceLoader;

/*
 * Created on 28.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	A generator load and play a Wave file.
 * </p>
 * 
 * @author smk
 * @version <p>28.03.2005:	created, smk</p>
 */
public class WaveGenerator
extends Generator
{
	public static final int	INPUT_FILE_NAME		= 1;
	
	private SoundSample[] soundSamples = null;

	private float	wafeFrameRate = 1.0F;  
	
	/**
	 * Constructor.
	 * 
	 */
	public WaveGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.ModulGenerator)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, 
									 SoundSample soundSample, 
									 ModulGenerator parentModulGenerator)
	{
		long soundFramePosition = (long)(framePosition - (this.getStartTimePos() * this.getSoundFrameRate()));
		
		synchronized (this)
		{
			if (this.soundSamples == null)
			{
				String fileName = this.calcInputStringValue(framePosition, 
															this.getGeneratorTypeData().getInputTypeData(INPUT_FILE_NAME),
															parentModulGenerator);
		
				if (fileName != null)
				{
					WaveFileReader waveFileReader = new WaveFileReader();
					
					try
					{
						//------------------------------------------------------
						//File file = new File("C:\\temp\\" + fileName);
						//URL fileURL = WaveResourceLoader.class.getResource("/" + fileName);
						//File file = new File(fileURL.toURI());
						
						File file = WaveResourceLoader.getFileResource(fileName);
						
						System.out.println("file: " + file);
						
						AudioInputStream audioInputStream = waveFileReader.getAudioInputStream(file);
						
						int waveFrameLength = (int)audioInputStream.getFrameLength();
						
						//------------------------------------------------------
						AudioFormat audioFormat = audioInputStream.getFormat();
						
						System.out.println("audioFormat: " + audioFormat.toString());
						
						this.wafeFrameRate  = audioFormat.getFrameRate();

						//  wfl     sfl
						// ----- = -----
						//  wfr     sfr

						//        wfl * sfr
						// sfl = -----------
						//           wfr
						
						long soundLength = (long)((waveFrameLength * this.getSoundFrameRate()) / this.wafeFrameRate);
						
						//------------------------------------------------------
						int waveFramePos = 0;
						
						this.soundSamples = new SoundSample[(int)soundLength];
						
						int frameSize = audioFormat.getChannels() * audioFormat.getFrameSize();
						
						//System.out.println("frameSize: " + frameSize);
						
						// WAV-Format: http://www.dlcsistemas.com/msx/doc/wave.htm

						byte bytesBuf[] = new byte[1024 * frameSize];
						
						long lastSoundFramePosition = 0;
						SoundSample lastSoundSample = new SoundSample();
						
						int available;
						
						while ((available = audioInputStream.available()) > 0)
						{
							int readBytes = audioInputStream.read(bytesBuf);
							
							//convertLinear8toMulaw8(bytesBuf, readBytes);
							
							for (int bytePos = 0; bytePos < readBytes; bytePos += frameSize)
							{
								// For all, look at:
								// http://www.jsresources.org/faq_audio.html#convert_signedness

								float leftSample;
								float rightSample;
								
								// Mono?
								if (audioFormat.getChannels() == 1)
								{
									// Mono:
									
									// 8-Bit?
									if (audioFormat.getFrameSize() == 1)
									{
										// 8-Bit:
										
										byte b	= bytesBuf[bytePos];
										{
											// unsigned:
											leftSample = (((b & 0xFF) - 128) / 128.0F);
										}
										rightSample = leftSample;
									}
									else
									{
										// 16-Bit:
										
										// little endian:
										
										leftSample = ((bytesBuf[bytePos + 0] & 0xFF) | 
													  (bytesBuf[bytePos + 1] << 8)) / 32768.0F;										
										rightSample = leftSample;
									}
								}
								else
								{
									// Stereo:
									
									// 8-Bit?
									if (audioFormat.getFrameSize() == 1)
									{
										// 8-Bit:
										
										leftSample	= ((short)bytesBuf[bytePos + 0]);
										rightSample = ((short)bytesBuf[bytePos + 1]);
									}
									else
									{
										// 16-Bit:
										
										leftSample	= (short)(((short)bytesBuf[bytePos + 1]) | (short)(((short)bytesBuf[bytePos + 0]) << 8));
										rightSample = (short)(((short)bytesBuf[bytePos + 3]) | (short)(((short)bytesBuf[bytePos + 2]) << 8));
									}
								}
								//if ((bytePos % 100) == 0)
								//{
								//	System.out.println();
								//	System.out.print("bytePos: " + bytePos + ": ");
								//}
								//System.out.print(leftSample + " ");

								//  wfp     sfp
								// ----- = -----
								//  wfr     sfr

								//        wfp * sfr
								// sfp = -----------
								//           wfr
								
								long aktSoundFramePosition = (long)((waveFramePos * this.getSoundFrameRate()) / this.wafeFrameRate);
								
								SoundSample aktSoundSample = new SoundSample(leftSample, rightSample);
								
								long betweenSamples = aktSoundFramePosition - lastSoundFramePosition;
								
								if (betweenSamples == 0)
								{
									this.soundSamples[(int)(aktSoundFramePosition)] = aktSoundSample;
								}
								else
								{
									//for (long soundPos = lastSoundFramePosition; soundPos <= aktSoundFramePosition; soundPos++)
									for (long soundPos = 0; soundPos <= betweenSamples; soundPos++)
									{
										//  soundDivPos       soundPos
										// ------------- = ----------------
										//       1.0        betweenSamples      
										
										float soundDivPos = (1.0F * soundPos) / betweenSamples;
										
										this.soundSamples[(int)(lastSoundFramePosition + soundPos)] =
										//	aktSoundSample;
											SoundSample.createInterpolate(lastSoundSample,
																		  aktSoundSample,
																		  soundDivPos);
									}
								}
								
								lastSoundFramePosition = aktSoundFramePosition + 1;
								lastSoundSample = aktSoundSample;
								
								waveFramePos++;
							}
						}
					}
					catch (IOException ex)
					{
						// ...
						ex.printStackTrace(System.err);
						
						this.soundSamples = new SoundSample[0];
					}
					catch (UnsupportedAudioFileException ex)
					{
						// ...
						ex.printStackTrace(System.err);
						
						this.soundSamples = new SoundSample[0];
					}
					catch (URISyntaxException ex)
					{
						// ...
						ex.printStackTrace(System.err);
						
						this.soundSamples = new SoundSample[0];
					}
					catch (Exception ex)
					{
						// ...
						ex.printStackTrace(System.err);
						
						this.soundSamples = new SoundSample[0];
					}
				}
			}
		}

		if (this.soundSamples != null)
		{
			//  wfp     sp
			// ----- = ----
			//  wfr     sr

			//        wfr * sp
			// wfp = ----------
			//           sr
			
			//long waveFramePos = (long)((this.wafeFrameRate * framePosition) / this.getSoundFrameRate());

			long waveFramePos = soundFramePosition;

			//------------------------------------------------------------------
			// play single times:
			if (waveFramePos < this.soundSamples.length)
			{
				soundSample.setValues(this.soundSamples[(int)waveFramePos]);
			}
			//------------------------------------------------------------------
			// play repeat times:
			{
				//long waveFramePos = framePosition % this.waveSoundSamples.length;
		
				//soundSample.setValues(this.waveSoundSamples[(int)pos]);
			}
		}
		else
		{
			//soundSample.setMonoValue(0.0F);
		}
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(WaveGenerator.class, 
																	"Wave File", "Plays a Wave file from file system.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_FILE_NAME, "fileName", 1, 1, 
															"Is the file name of a .WAV file.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeData;
	}
}
