package de.schmiereck.noiseComp.swingView.moduleControls;

import de.schmiereck.noiseComp.swingView.appView.AppView;

import javax.swing.*;

public class ModuleControlsView
        extends JDialog  {
    //**********************************************************************************************
    // Fields:
    private final JPanel panel;

    //**********************************************************************************************
    // Functions:

    /**
     * Constructor.
     *
     * Constructs an invisible Dialog.
     *
     * @param appView
     * 			this is the App View.
     */
    public ModuleControlsView(final AppView appView) {
        super(appView, false);
        //==========================================================================================
        this.setVisible(false);

        this.setTitle("Module-Controls");
//		this.setModal(false);

        this.setSize(400, 300);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //------------------------------------------------------------------------------------------
        // Timeline-Edit Split-Pane:

        this.panel = new JPanel();

        this.add(this.panel);

        //------------------------------------------------------------------------------------------
        final JSlider slider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);

        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                int value = source.getValue();
                System.out.println("Slider value adjusted: " + value);
            } else {
                int value = source.getValue();
                System.out.println("Slider value changed: " + value);
            }
        });
        this.panel.add(slider);

        //==========================================================================================
    }

}
