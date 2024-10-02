package de.schmiereck.screenTools.scheduler;

public class OutLogic {
    private final PipelineSchedulerLogic pipelineSchedulerLogic;

    /**
     * Counts of calling {@link PipelineSchedulerLogic#notifyRunSchedulOut(long)}.
     */
    private long runCounterOut	= 0L;

    private boolean logicIsRunning = false;

    public OutLogic(final PipelineSchedulerLogic pipelineSchedulerLogic) {
        this.pipelineSchedulerLogic = pipelineSchedulerLogic;
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
        long targetWaitPerFramesMillis = 1000 / this.pipelineSchedulerLogic.getTargetFramesPerSecond();

        // Millisekunden die momentan zwischen zwei Frames gewartet wird.
        this.pipelineSchedulerLogic.actualWaitPerFramesMillis = targetWaitPerFramesMillis;

        //while (null != this.outThread)
        //while (Thread.currentThread() == this.pipelineSchedulerLogic.outThread)
        //while (Objects.nonNull(this.pipelineSchedulerLogic.outThread))
        while (this.logicIsRunning) {
            try
            {
                tm += this.pipelineSchedulerLogic.actualWaitPerFramesMillis;
                long sleepMillis = tm - System.currentTimeMillis();

                // Hat das Zeichnen des Frames länger gedauert
                // als die momentane Framerate erlaubt?
                if (sleepMillis < 0)
                {
                    // Setze die Wartezeit zwischen den Frames hoch (Framerate heruntersetzen).
                    this.pipelineSchedulerLogic.actualWaitPerFramesMillis += 1;//((-sleepMillis) / 2);
                }
                else
                {
                    // Hat das Zeichnen des Frames kürzer gedauert,
                    // als die momentane Framerate Zeit zur Verfügung stellt?
                    if (sleepMillis > 0)
                    {
                        // Ist es das Ziel, weniger Zeit für einen Frame zu benötigen
                        // als momentan zur verfügung gestellt wird?
                        if (targetWaitPerFramesMillis < this.pipelineSchedulerLogic.getActualWaitPerFramesMillis())
                        {
                            // Setze die Wartezeit zwischen den Frames runter (Framerate hochsetzen).
                            this.pipelineSchedulerLogic.actualWaitPerFramesMillis -= 1;//((-sleepMillis) / 2);
                        }
                    }
                }
                long sleepMs = Math.max(0, sleepMillis);

                Thread.sleep(sleepMs);

                this.incRunCounterOut();
                this.pipelineSchedulerLogic.notifyRunSchedulOut(this.pipelineSchedulerLogic.getActualWaitPerFramesMillis());
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace(System.err);
            }
        }
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
