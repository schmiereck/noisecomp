package de.schmiereck.noiseComp.soundSource;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.timeline.Timeline;

import java.util.List;
import java.util.Vector;

public class SoundSourceData {
    private final List<Timeline> timelineList = new Vector<>();

    /**
     * Output Timeline.
     */
    private Timeline outputTimeline = null;

    /**
     * This generator generates the output samples.
     *
     * TODO Remove this and only use {@link #outputTimeline}.
     */
    private Generator outputGenerator = null;

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

    public Generator getOutputGenerator() {
        return this.outputGenerator;
    }

    public void setOutputGenerator(final Generator outputGenerator) {
        this.outputGenerator = outputGenerator;
    }
}
