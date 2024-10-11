package de.schmiereck.noiseComp.soundScheduler;

public class SoundCalcSchedulerLogic {

    private Thread calcThread = null;
    private final SoundCalcLogic soundCalcLogic;

    public SoundCalcSchedulerLogic(SoundSchedulerData soundSchedulerData, SoundDataLogic soundDataLogic) {
        this.soundCalcLogic = new SoundCalcLogic(soundSchedulerData, soundDataLogic);
    }

    public void startCalcThread(final Runnable task) {
        if (this.calcThread != null) {
            throw new RuntimeException("calc thread already startet");
        }

        this.calcThread = new Thread(task);
        this.calcThread.start();
    }

    public void runCalc() {
        this.soundCalcLogic.runCalc();
    }

    public void stopCalcThread() {
        if (this.calcThread == null) {
            throw new RuntimeException("calc thread not startet");
        }

        this.soundCalcLogic.stopRunning();
        this.calcThread = null;
    }
}
