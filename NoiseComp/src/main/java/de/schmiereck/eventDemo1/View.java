package de.schmiereck.eventDemo1;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View {
    private final ViewModel viewModel;
    private final JTextField textField;
    private final JButton updateButton = new JButton("Update");
    private boolean isUpdatingFromModel = false;

    public View(final ViewModel viewModel) {
        this.viewModel = viewModel;
        this.textField = new JTextField(viewModel.getProperty());
        this.textField.addActionListener(e -> viewModel.setProperty(textField.getText()));

        // changes in the views are transmitted with each keystroke, not only when the focus is lost
        this.textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                this.updateModel(e);
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                this.updateModel(e);
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                this.updateModel(e);
            }

            private void updateModel(final DocumentEvent e) {
                if (!isUpdatingFromModel) {
                    viewModel.setProperty(textField.getText());
                }
            }
        });

        this.viewModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("property".equals(evt.getPropertyName())) {
                    isUpdatingFromModel = true;
                    //View.this.textField.setText((String) evt.getNewValue());
                    SwingUtilities.invokeLater(() -> {
                        View.this.textField.setText((String) evt.getNewValue());
                        isUpdatingFromModel = false;
                    });
                }
            }
        });
    }

    public void updateView(final String newValue) {
        this.textField.setText(newValue);
    }

    public JTextField getTextField() {
        return this.textField;
    }

    public JButton getUpdateButton() {
        return this.updateButton;
    }
}
