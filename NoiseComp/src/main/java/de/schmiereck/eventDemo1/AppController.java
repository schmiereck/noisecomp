package de.schmiereck.eventDemo1;

import java.util.Objects;

public class AppController {
    private final CentralModel centralModel = new CentralModel();

    public void addView(final View view, final ViewController viewController) {
        this.centralModel.addPropertyChangeListener(
                evt -> {
                    switch (evt.getPropertyName()) {
                        case "property1" -> view.updateProperty1(String.valueOf(evt.getNewValue()));
                        case "property2" -> view.updateProperty2(String.valueOf(evt.getNewValue()));
                        case "property3" -> view.updateProperty3(Integer.valueOf((String) evt.getNewValue()));
                        default -> throw new RuntimeException("Unexpected Property \"%s\".".formatted(evt.getPropertyName()));
                    }
                }
        );

        view.getProperty1TextField().addActionListener((e) ->
                viewController.updateProperty1CentralModel(String.valueOf(view.getProperty1TextField().getText()))
        );
        view.getProperty2TextField().addActionListener((e) ->
                viewController.updateProperty2CentralModel(String.valueOf(view.getProperty2TextField().getText()))
        );
        view.getProperty3TextField().addActionListener((e) ->
                viewController.updateProperty3CentralModel(Integer.valueOf(view.getProperty3TextField().getText()))
        );

        view.getUpdateButton().addActionListener((e) -> {
                viewController.updateProperty1CentralModel(String.valueOf(view.getProperty1TextField().getText()));
                viewController.updateProperty2CentralModel(String.valueOf(view.getProperty2TextField().getText()));
                viewController.updateProperty3CentralModel(ParseValueUtils.parseIntegerValue(view.getProperty3TextField().getText()));
        });
    }
}
