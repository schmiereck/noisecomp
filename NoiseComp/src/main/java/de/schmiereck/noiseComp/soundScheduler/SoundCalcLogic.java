package de.schmiereck.noiseComp.soundScheduler;

import de.schmiereck.noiseComp.soundBuffer.SoundBufferManager;

public class SoundCalcLogic {

    private final SoundSchedulerData soundSchedulerData;

    private final SoundDataLogic soundDataLogic;

    /**
     * Counts of calling {@link SoundCalcLogic#notifyRunSchedulCalc()}.
     */
    private long runCounterCalc	= 0L;

    private boolean logicIsRunning = false;

    public SoundCalcLogic(final SoundSchedulerData soundSchedulerData, final SoundDataLogic soundDataLogic) {
        this.soundSchedulerData = soundSchedulerData;
        this.soundDataLogic = soundDataLogic;
    }

    public void runCalc() {
        this.runCounterCalc = 0;
        this.logicIsRunning = true;

        long tm = System.currentTimeMillis();

        //while (Thread.currentThread() == this.pipelineSchedulerLogic.calcThread)
        //while (Objects.nonNull(this.pipelineSchedulerLogic.calcThread))
        while (this.logicIsRunning) {
            long waitPerFramesMillis = this.soundSchedulerData.getActualWaitPerFramesMillis() / 2;

            tm += waitPerFramesMillis;

            long sleepMillis = tm - System.currentTimeMillis();

            long d1 = System.currentTimeMillis();
            this.incRunCounterCalc();
            this.notifyRunSchedulCalc();
            long d2 = System.currentTimeMillis();

            //tm = ctm;

            // Out thread not started jet ?
//            if (this.pipelineSchedulerLogic.outThread == null)
//            {
//                // Start out thread after first calculate is done.
//                System.out.println("YYY: startOutThread.");
//                this.pipelineSchedulerLogic.startOutThread();
//            }

            try
            {
                Thread.sleep(Math.max(0, sleepMillis));
                //this.calcThread.wait(Math.max(0, sleepMillis));
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace(System.err);
            }
        }
    }

    public void notifyRunSchedulCalc() {
        //==========================================================================================
        if (this.soundSchedulerData.getPlaybackPaused() == false)
        {
            final SoundBufferManager soundBufferManager = this.soundDataLogic.getSoundBufferManager();

            soundBufferManager.pollGenerate();
        }
        //==========================================================================================
    }

    /**
     * @return the attribute {@link #runCounterCalc}.
     */
    public long getRunCounterCalc()
    {
        return this.runCounterCalc;
    }

    public void incRunCounterCalc() {
        this.runCounterCalc++;
    }

    public void stopRunning() {
        this.logicIsRunning = false;
    }
}
