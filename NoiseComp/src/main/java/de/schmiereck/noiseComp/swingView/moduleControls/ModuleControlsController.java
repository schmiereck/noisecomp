package de.schmiereck.noiseComp.swingView.moduleControls;

import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.moduleInputs.ModuleInputTypesModel;
import de.schmiereck.noiseComp.swingView.moduleInputs.ModuleInputTypesView;

import java.awt.*;

public class ModuleControlsController {
    //**********************************************************************************************
    // Fields:

    private final ModuleControlsModel moduleControlsModel;
    private final ModuleControlsView moduleControlsView;

    //**********************************************************************************************
    // Functions:

    /**
     * Constructor.
     *
     * @param appController
     * 			is the App Controller.
     * @param appModelChangedObserver
     * 			is the AppModelChangedObserver.
     */
    public ModuleControlsController(final AppController appController,
                                      final AppModelChangedObserver appModelChangedObserver) {
        //==========================================================================================
        this.moduleControlsModel = new ModuleControlsModel();
        this.moduleControlsView = new ModuleControlsView(appController.getAppView());

        //------------------------------------------------------------------------------------------
    }

    public ModuleControlsView getModuleControlsView() {
        return this.moduleControlsView;
    }
}
