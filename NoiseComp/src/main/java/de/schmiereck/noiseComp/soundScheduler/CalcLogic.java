package de.schmiereck.noiseComp.soundScheduler;

public class CalcLogic {

    private final SoundSchedulerData soundSchedulerData;

    private final SoundSchedulerLogic pipelineSchedulerLogic;

    /**
     * Counts of calling {@link SoundSchedulerLogic#notifyRunSchedulCalc(long)}.
     */
    private long runCounterCalc	= 0L;

    private boolean logicIsRunning = false;

    public CalcLogic(final SoundSchedulerData soundSchedulerData, final SoundSchedulerLogic pipelineSchedulerLogic) {
        this.soundSchedulerData = soundSchedulerData;
        this.pipelineSchedulerLogic = pipelineSchedulerLogic;
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
            this.pipelineSchedulerLogic.notifyRunSchedulCalc(sleepMillis);
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
