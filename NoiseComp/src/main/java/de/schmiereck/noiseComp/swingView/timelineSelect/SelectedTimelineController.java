package de.schmiereck.noiseComp.swingView.timelineSelect;

import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesModel;
import de.schmiereck.noiseComp.swingView.appModel.InputEntryModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectView;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel;

import java.util.Objects;

public class SelectedTimelineController {
    //**********************************************************************************************
    // Fields:

    private final SoundSourceLogic soundSourceLogic;

    private final SelectedTimelineModel selectedTimelineModel;

    //**********************************************************************************************
    // Functions:

    /**
     * Constructor.
     */
    public SelectedTimelineController(final SoundSourceLogic soundSourceLogic,
                                      final SelectedTimelineModel selectedTimelineModel) {
        //==========================================================================================
        this.soundSourceLogic = soundSourceLogic;
        this.selectedTimelineModel = selectedTimelineModel;
        //this.inputSelectView = new InputSelectView(this.inputSelectModel);

        //==========================================================================================
    }

    public void setSelectedInputEntryByRowNo(final Integer selectedRowNo) {
        //==========================================================================================
        final InputEntryModel selectedInputEntry;

        if (Objects.nonNull(selectedRowNo)) {
            final InputEntriesModel inputEntriesModel = this.selectedTimelineModel.getInputEntriesModel();

            selectedInputEntry = inputEntriesModel.searchInputEntry(selectedRowNo);
        } else {
            selectedInputEntry = null;
        }
        this.selectedTimelineModel.setSelectedInputEntry(selectedInputEntry);

        //==========================================================================================
    }
}
