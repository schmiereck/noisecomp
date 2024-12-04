package de.schmiereck.eventDemo1;

public class ViewController {
    private final ViewModel viewModel;
    private final CentralModel centralModel;

    public ViewController(final ViewModel viewModel, final CentralModel centralModel) {
        this.viewModel = viewModel;
        this.centralModel = centralModel;

        this.centralModel.addPropertyChangeListener(evt -> {
            if ("property".equals(evt.getPropertyName())) {
                this.viewModel.setProperty((String) evt.getNewValue());
            }
        });

        this.viewModel.addPropertyChangeListener(evt -> {
            if ("property".equals(evt.getPropertyName())) {
                centralModel.setProperty((String) evt.getNewValue());
            }
        });
    }

    public void updateCentralModel(final String newValue) {
        this.centralModel.setProperty(newValue);
    }
}
