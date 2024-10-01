package de.schmiereck.screenTools.scheduler;

import java.util.Objects;

public class CalcLogic {
    private final PipelineSchedulerLogic pipelineSchedulerLogic;

    public CalcLogic(final PipelineSchedulerLogic pipelineSchedulerLogic) {
        this.pipelineSchedulerLogic = pipelineSchedulerLogic;
    }

    public void runCalc() {
        long tm = System.currentTimeMillis();

        //while (Thread.currentThread() == this.pipelineSchedulerLogic.calcThread)
        while (Objects.nonNull(this.pipelineSchedulerLogic.calcThread))
        {
            long waitPerFramesMillis = this.pipelineSchedulerLogic.actualWaitPerFramesMillis / 2;

            tm += waitPerFramesMillis;

            long sleepMillis = tm - System.currentTimeMillis();

            long d1 = System.currentTimeMillis();
            this.pipelineSchedulerLogic.incRunCounterCalc();
            this.pipelineSchedulerLogic.notifyRunSchedulCalc(sleepMillis);
            long d2 = System.currentTimeMillis();

            System.out.println("XXX: " + waitPerFramesMillis + " / " + sleepMillis + " : " + (d2 - d1));

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
}
