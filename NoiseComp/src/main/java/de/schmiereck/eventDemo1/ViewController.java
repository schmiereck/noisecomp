package de.schmiereck.eventDemo1;

public class ViewController {
    private final ViewModel viewModel;
    private final CentralModel centralModel;

    public ViewController(final ViewModel viewModel, final CentralModel centralModel) {
        this.viewModel = viewModel;
        this.centralModel = centralModel;

        this.centralModel.addPropertyChangeListener(evt -> {
            switch (evt.getPropertyName()) {
                case "property1" -> this.viewModel.setProperty1((String) evt.getNewValue());
                case "property2" -> this.viewModel.setProperty2((String) evt.getNewValue());
                case "property3" -> this.viewModel.setProperty3((Integer) evt.getNewValue());
                default -> throw new RuntimeException("Unexpected Property \"%s\".".formatted(evt.getPropertyName()));
            }
        });

        this.viewModel.addPropertyChangeListener(evt -> {
            switch (evt.getPropertyName()) {
                case "property1" -> this.centralModel.setProperty1((String) evt.getNewValue());
                case "property2" -> this.centralModel.setProperty2((String) evt.getNewValue());
                case "property3" -> this.centralModel.setProperty3((Integer) evt.getNewValue());
                default -> throw new RuntimeException("Unexpected Property \"%s\".".formatted(evt.getPropertyName()));
            }
        });
    }

    public void updateProperty1CentralModel(final String property1) {
        this.centralModel.setProperty1(property1);
    }

    public void updateProperty2CentralModel(final String property2) {
        this.centralModel.setProperty2(property2);
    }

    public void updateProperty3CentralModel(final Integer property3) {
        this.centralModel.setProperty3(property3);
    }
}
