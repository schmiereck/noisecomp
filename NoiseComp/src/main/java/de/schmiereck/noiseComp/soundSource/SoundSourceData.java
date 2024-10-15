package de.schmiereck.noiseComp.soundSource;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.timeline.Timeline;

import java.util.List;
import java.util.Vector;

public class SoundSourceData {
    private final List<Timeline> timelineList = new Vector<>();

    /**
     * Output Timeline generates the output samples.
     */
    private Timeline outputTimeline = null;

    public List<Timeline> getTimelineList() {
        return this.timelineList;
    }

    public void addTimeline(final Timeline timeline) {
        this.timelineList.add(timeline);
    }

    public Timeline getOutputTimeline() {
        return this.outputTimeline;
    }

    public void setOutputTimeline(final Timeline outputTimeline) {
        this.outputTimeline = outputTimeline;
    }
}
