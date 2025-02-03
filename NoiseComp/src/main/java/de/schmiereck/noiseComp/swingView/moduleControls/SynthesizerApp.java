package de.schmiereck.noiseComp.swingView.moduleControls;

import javax.swing.*;

// Hauptklasse zum Starten der Anwendung
public class SynthesizerApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SliderView view = new SliderView();
            new SliderController(view);
        });
    }
}