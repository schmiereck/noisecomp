package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw;

import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntriesModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;

import java.util.List;

public abstract class TimelinesDrawPanelUtils {
    private TimelinesDrawPanelUtils() {}

    public static int calcYSizeGenerator(final TimelineSelectEntryModel timelineSelectEntryModel) {
        final int ySizeGenerator;

        if (timelineSelectEntryModel.getExpanded()) {
            ySizeGenerator = timelineSelectEntryModel.getYSizeGenerator() + 64;
        } else {
            ySizeGenerator = timelineSelectEntryModel.getYSizeGenerator();
        }
        return ySizeGenerator;
    }

    public static void recalcYPosTimelineList(final TimelineSelectEntriesModel timelineSelectEntriesModel,
                                              final int startTimelinePos) {
        final List<TimelineSelectEntryModel> timelineSelectEntryModels = timelineSelectEntriesModel.getTimelineSelectEntryModelList();
        int yPosGenerator;
        if (startTimelinePos > 0) {
            final TimelineSelectEntryModel prevTimelineSelectEntryModel = timelineSelectEntryModels.get(startTimelinePos - 1);
            yPosGenerator = prevTimelineSelectEntryModel.getYPosGenerator() +
                    TimelinesDrawPanelUtils.calcYSizeGenerator(prevTimelineSelectEntryModel);
        } else {
            yPosGenerator = 0;
        }

        for (int timelinePos = startTimelinePos; timelinePos < timelineSelectEntryModels.size(); timelinePos++) {
            final TimelineSelectEntryModel timelineSelectEntryModel = timelineSelectEntryModels.get(timelinePos);
            timelineSelectEntryModel.setYPosGenerator(yPosGenerator);
            yPosGenerator += TimelinesDrawPanelUtils.calcYSizeGenerator(timelineSelectEntryModel);
        }
    }

}
