package de.schmiereck.screenTools.scheduler;

import java.util.Objects;

public class OutLogic {
    private final PipelineSchedulerLogic pipelineSchedulerLogic;

    public OutLogic(final PipelineSchedulerLogic pipelineSchedulerLogic) {
        this.pipelineSchedulerLogic = pipelineSchedulerLogic;
    }

    public void runOut() {
        this.pipelineSchedulerLogic.isRunning = true;

        // Enthält die 'Systemzeit' des Programms.
        // Der Scheduler versucht die 'wirkliche' Zeit mit dieser
        // zu syncronisieren.
        long tm = System.currentTimeMillis();

        // Millisekunden, die zwischen zwei Frames gewartet werden soll,
        // um die gewümschte Framerate hinzubekommen.
        long targetWaitPerFramesMillis = 1000 / this.pipelineSchedulerLogic.getTargetFramesPerSecond();

        // Millisekunden die momentan zwischen zwei Frames gewartet wird.
        this.pipelineSchedulerLogic.actualWaitPerFramesMillis = targetWaitPerFramesMillis;

        //while (null != this.outThread)
        //while (Thread.currentThread() == this.pipelineSchedulerLogic.outThread)
        while (Objects.nonNull(this.pipelineSchedulerLogic.outThread))
        {
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
                System.out.println("Out: " + this.pipelineSchedulerLogic.getRunCounterOut() + ", sleepMs: " + sleepMs);

                Thread.sleep(sleepMs);

                this.pipelineSchedulerLogic.incRunCounterOut();
                this.pipelineSchedulerLogic.notifyRunSchedulOut(this.pipelineSchedulerLogic.getActualWaitPerFramesMillis());
                System.out.println("Out2: ");
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace(System.err);
            }
        }

        this.pipelineSchedulerLogic.isRunning = false;
    }
}
