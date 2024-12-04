package de.schmiereck.eventDemo1;

public class AppController {
    private final CentralModel centralModel = new CentralModel();

    public void addView(final View view, final ViewController viewController) {
        this.centralModel.addPropertyChangeListener(
                evt -> {
                    if ("property".equals(evt.getPropertyName())) {
                        view.updateView((String) evt.getNewValue());
                    }
                }
        );

        view.getTextField().addActionListener(
                e -> viewController.updateCentralModel(view.getTextField().getText())
        );

        view.getUpdateButton().addActionListener((e) ->
                viewController.updateCentralModel(view.getTextField().getText()+"-upd"));
    }
}
