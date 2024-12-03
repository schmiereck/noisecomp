package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw;

import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntriesModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;

import java.util.List;

public abstract class TimelinesDrawPanelUtils {
    private TimelinesDrawPanelUtils() {}


    public static void recalcYPosTimelineList(final TimelineSelectEntriesModel timelineSelectEntriesModel,
                                              final int startTimelinePos) {
        final List<TimelineSelectEntryModel> timelineSelectEntryModels = timelineSelectEntriesModel.getTimelineSelectEntryModelList();
        int yPosGenerator;
        if (startTimelinePos > 0) {
            final TimelineSelectEntryModel prevTimelineSelectEntryModel = timelineSelectEntryModels.get(startTimelinePos - 1);
            yPosGenerator = prevTimelineSelectEntryModel.getYPosGenerator() + prevTimelineSelectEntryModel.getYSizeGenerator();
        } else {
            yPosGenerator = 0;
        }

        for (int timelinePos = startTimelinePos; timelinePos < timelineSelectEntryModels.size(); timelinePos++) {
            final TimelineSelectEntryModel timelineSelectEntryModel = timelineSelectEntryModels.get(timelinePos);
            timelineSelectEntryModel.setYPosGenerator(yPosGenerator);
            yPosGenerator += timelineSelectEntryModel.getYSizeGenerator();
            if (timelineSelectEntryModel.getExpanded()) {
                yPosGenerator += 64;
            }
        }
    }

}
