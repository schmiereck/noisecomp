package de.schmiereck.noiseComp.soundScheduler;

import de.schmiereck.noiseComp.soundBuffer.SoundBufferManager;

import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class SoundOutLogic {
    private final SoundSchedulerData soundSchedulerData;

    private final SoundDataLogic soundDataLogic;

    //==============================================================================================
    /**
     * Playback-Pos Changed Listeners.
     */
    private final List<PlaybackPosChangedListenerInterface> playbackPosChangedListeners = new Vector<>();

    /**
     * Counts of calling {@link SoundOutLogic#notifyRunSchedulOut()}.
     */
    private long runCounterOut	= 0L;

    private boolean logicIsRunning = false;

    private byte lineBufferData[];

    public SoundOutLogic(final SoundSchedulerData soundSchedulerData, final SoundDataLogic soundDataLogic) {
        this.soundSchedulerData = soundSchedulerData;
        this.soundDataLogic = soundDataLogic;

        this.lineBufferData = new byte[SoundDataLogic.BUFFER_SIZE];
    }

    public void runOut() {
        this.runCounterOut = 0;
        this.logicIsRunning = true;

        // Enthält die 'Systemzeit' des Programms.
        // Der Scheduler versucht die 'wirkliche' Zeit mit dieser
        // zu syncronisieren.
        long tm = System.currentTimeMillis();

        // Millisekunden, die zwischen zwei Frames gewartet werden soll,
        // um die gewünschte Framerate hinzubekommen.
        long targetWaitPerFramesMillis = 1000 / this.soundSchedulerData.getTargetFramesPerSecond();

        // Millisekunden die momentan zwischen zwei Frames gewartet wird.
        this.soundSchedulerData.setActualWaitPerFramesMillis(targetWaitPerFramesMillis);

        //while (null != this.outThread)
        //while (Thread.currentThread() == this.pipelineSchedulerLogic.outThread)
        //while (Objects.nonNull(this.pipelineSchedulerLogic.outThread))
        while (this.logicIsRunning) {
            try
            {
                tm += this.soundSchedulerData.getActualWaitPerFramesMillis();
                long sleepMillis = tm - System.currentTimeMillis();

                // Hat das Zeichnen des Frames länger gedauert
                // als die momentane Framerate erlaubt?
                if (sleepMillis < 0)
                {
                    // Setze die Wartezeit zwischen den Frames hoch (Framerate heruntersetzen).
                    this.soundSchedulerData.setActualWaitPerFramesMillis(this.soundSchedulerData.getActualWaitPerFramesMillis() + 1);//((-sleepMillis) / 2);
                }
                else
                {
                    // Hat das Zeichnen des Frames kürzer gedauert,
                    // als die momentane Framerate Zeit zur Verfügung stellt?
                    if (sleepMillis > 0)
                    {
                        // Ist es das Ziel, weniger Zeit für einen Frame zu benötigen
                        // als momentan zur verfügung gestellt wird?
                        if (targetWaitPerFramesMillis < this.soundSchedulerData.getActualWaitPerFramesMillis())
                        {
                            // Setze die Wartezeit zwischen den Frames runter (Framerate hochsetzen).
                            this.soundSchedulerData.setActualWaitPerFramesMillis(this.soundSchedulerData.getActualWaitPerFramesMillis() - 1);//((-sleepMillis) / 2);
                        }
                    }
                }
                long sleepMs = Math.max(0, sleepMillis);

                Thread.sleep(sleepMs);

                this.incRunCounterOut();
                this.notifyRunSchedulOut();
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace(System.err);
            }
        }
    }

    /**
     * @param playbackPosChangedListener
     * 			to add to the {@link #playbackPosChangedListeners}.
     */
    public void addPlaybackPosChangedListener(PlaybackPosChangedListenerInterface playbackPosChangedListener) {
        this.playbackPosChangedListeners.add(playbackPosChangedListener);
    }

    public void notifyRunSchedulOut() {
        //==========================================================================================
        if (this.soundSchedulerData.getPlaybackPaused() == false)
        {
//			System.out.println("OUT: " + actualWaitPerFramesMillis);
            SourceDataLine line = this.soundDataLogic.getLine();

            SoundBufferManager soundBufferManager = this.soundDataLogic.getSoundBufferManager();

            System.out.println("PLAY: %f".formatted(soundBufferManager.getActualTime()));

            this.notifyPlaybackPosChangedListeners();

            //soundBufferManager.pollGenerate();

            try {
                //int numBytesToRead = line.available();
                //if (numBytesToRead == -1) break;
                //int nRead = soundBufferManager.read(abData, 0, numBytesToRead);

                //int nRead = soundBufferManager.read(abData);
                //int	nWritten =
                //line.write(abData, 0, nRead);

                int availableBytes = line.available();
                //if (availableBytes > 0) {
                if (availableBytes >= this.lineBufferData.length) {
                    int nRead = soundBufferManager.read(this.lineBufferData, 0, this.lineBufferData.length);
                    line.write(this.lineBufferData, 0, nRead);
                }

                //System.out.println("actualWaitPerFramesMillis: " + actualWaitPerFramesMillis + ", nWritten:" + nWritten + ", nRead: " + nRead);
            }
            catch (IOException ex) {
                throw new RuntimeException("read sound data", ex);
            }
        }
        //==========================================================================================
    }

    /**
     * Notify the {@link #playbackPosChangedListeners}.
     */
    private void notifyPlaybackPosChangedListeners() {
        //==========================================================================================
        SoundBufferManager soundBufferManager = this.soundDataLogic.getSoundBufferManager();

        //==========================================================================================
        for (PlaybackPosChangedListenerInterface playbackPosChangedListener : this.playbackPosChangedListeners)
        {
            playbackPosChangedListener.notifyPlaybackPosChanged(soundBufferManager.getActualTime());
        }
        //==========================================================================================
    }

    public void incRunCounterOut() {
        this.runCounterOut++;
    }

    /**
     * @return the attribute {@link #runCounterOut}.
     */
    public long getRunCounterOut() {
        return this.runCounterOut;
    }

    public void stopRunning() {
        this.logicIsRunning = false;
    }
}
