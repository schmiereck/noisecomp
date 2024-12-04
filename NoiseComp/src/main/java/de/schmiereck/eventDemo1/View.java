package de.schmiereck.eventDemo1;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class View {
    private final ViewModel viewModel;
    private final JTextField textField1;
    private final JTextField textField2;
    private final JTextField textField3;
    private final JButton updateButton = new JButton("Update");
    private boolean isUpdatingFromModel = false;

    public View(final ViewModel viewModel) {
        this.viewModel = viewModel;

        this.textField1 = new JTextField(viewModel.getProperty1());
        this.textField1.addActionListener(e -> viewModel.setProperty1(textField1.getText()));

        this.textField2 = new JTextField(viewModel.getProperty2());
        this.textField2.addActionListener(e -> viewModel.setProperty2(textField2.getText()));

        this.textField3 = new JTextField(ParseValueUtils.parseIntegerProperty(viewModel.getProperty3()));
        ((AbstractDocument) this.textField3.getDocument()).setDocumentFilter(new IntegerDocumentFilter());
        this.textField3.addActionListener(e -> viewModel.setProperty3(Integer.valueOf(textField3.getText())));

        // changes in the views are transmitted with each keystroke, not only when the focus is lost
        this.textField1.getDocument().addDocumentListener(new DocumentListener() {
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
                    viewModel.setProperty1(View.this.textField1.getText());
                }
            }
        });

        this.viewModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                switch (evt.getPropertyName()) {
                    case "property1" -> {
                        SwingUtilities.invokeLater(() -> {
                            isUpdatingFromModel = true;
                            View.this.textField1.setText((String) evt.getNewValue());
                            isUpdatingFromModel = false;
                        });
                    }
                    case "property2" -> {
                        SwingUtilities.invokeLater(() -> {
                            isUpdatingFromModel = true;
                            View.this.textField2.setText((String) evt.getNewValue());
                            isUpdatingFromModel = false;
                        });
                    }
                    case "property3" -> {
                        SwingUtilities.invokeLater(() -> {
                            isUpdatingFromModel = true;
                            View.this.textField3.setText(String.valueOf(evt.getNewValue()));
                            isUpdatingFromModel = false;
                        });
                    }
                    default -> {
                        throw new RuntimeException("Unexpected Property \"%s\".".formatted(evt.getPropertyName()));
                    }
                }
            }
        });
    }

    public void updateProperty1(final String property1) {
        this.textField1.setText(property1);
    }

    public JTextField getProperty1TextField() {
        return this.textField1;
    }

    public void updateProperty2(final String property2) {
        this.textField2.setText(property2);
    }

    public JTextField getProperty2TextField() {
        return this.textField2;
    }

    public void updateProperty3(final Integer property3) {
        this.textField3.setText(String.valueOf(property3));
    }

    public JTextField getProperty3TextField() {
        return this.textField3;
    }

    public JButton getUpdateButton() {
        return this.updateButton;
    }
}
