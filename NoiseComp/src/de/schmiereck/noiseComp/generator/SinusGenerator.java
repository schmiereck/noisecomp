package de.schmiereck.noiseComp.generator;

/**
 * Erzeugt einzelne Samples anhand der angegebenen Frame-Position
 * auf der Timeline.
 *
 * @author smk
 * @version 21.01.2004
 */
public class SinusGenerator
extends Generator
{
	/**
	 * Input für die Amplitude des Sinus-Signals.
	 */
	private GeneratorInterface amplitudeGenerator = null;
	
	/**
	 * Frequenz des generierten Sinus-Signals.
		 Frequenz  Note  Instrument  
		 16.5Hz  C2  Taste C im 32' der Orgel 
		 33Hz  C1  C-Saite bei fünfseitigen Kontrabässen 
		 66Hz  C  C-Saite der Violoncelli 
		 131Hz  c  C-Saite der Bratschen 
		 262Hz  c'  tiefstes c der Geigen 
		 524Hz  c''  hohes c der Tenöre 
		 1047Hz  c'''  hohes c der Soprane 
		 2093Hz  c4  höchstes c der Geigen 
		 4185Hz  c5  höchstes c der Piccolo-Flöten 
	 */
	private float signalFrequency;
	
	/**
	 * Constructor.
	 * 
	 * Frames per Second
	 */
	public SinusGenerator(String name, float signalFrequency, float frameRate)
	{
		super(name, frameRate);
		
		this.signalFrequency = signalFrequency;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.soundGenerator.Generator#calculateSoundSample(long, de.schmiereck.soundGenerator.SoundSample)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample)
	{
		// Amplitude des gerade generierten Sinus-Siganls.
		float amplitude;
		if (amplitudeGenerator != null)
		{
			SoundSample amplitudeSoundSample = this.amplitudeGenerator.generateFrameSample(framePosition);
			
			if (amplitudeSoundSample != null)
			{			
				amplitude = amplitudeSoundSample.getMonoValue();
			}
			else
			{			
				amplitude = 0.0F;
			}
		}
		else
		{
			amplitude = 1.0F;
		}
		
		// Relativer Zeitpunkt im Generator.
		//float timePos = frameTime - (this.getStartTimePos());
		
		// Länge einer Sinus-Periode in Frames.
		int periodLengthInFrames = Math.round(this.getFrameRate() / this.signalFrequency);
		float	periodPosition = (float) (framePosition) / (float)periodLengthInFrames;
		float value = ((float)Math.sin(periodPosition * 2.0 * Math.PI)) * amplitude;
		
		//float value = ((float)Math.sin(frameTime * 2.0 * Math.PI)) * amplitude;
		
		soundSample.setStereoValues(value, value);
	}
}
