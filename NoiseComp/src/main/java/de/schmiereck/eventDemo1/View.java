package de.schmiereck.eventDemo1;

import javax.swing.*;

public class View {
    private final ViewModel viewModel;
    private final JTextField textField;

    public View(final ViewModel viewModel) {
        this.viewModel = viewModel;
        this.textField = new JTextField(viewModel.getProperty());
        this.textField.addActionListener(e -> viewModel.setProperty(textField.getText()));
    }

    public void updateView(final String newValue) {
        this.textField.setText(newValue);
    }

    public JTextField getTextField() {
        return this.textField;
    }
}
