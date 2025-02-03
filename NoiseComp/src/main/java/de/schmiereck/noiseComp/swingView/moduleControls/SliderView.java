package de.schmiereck.noiseComp.swingView.moduleControls;

import javax.swing.*;
import java.awt.*;

public class SliderView extends JFrame {
    private final JPanel sliderPanel;
    private final JButton addButton;
    private final JTextField labelField;
    private final JTextField minField;
    private final JTextField maxField;
    private final DefaultListModel<String> sliderListModel;
    private final JList<String> sliderList;

    public SliderView() {
        setTitle("Synthesizer Controller");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        sliderPanel = new JPanel(null); // Set layout to null for free positioning
        sliderPanel.setPreferredSize(new Dimension(400, 400));
        sliderPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JPanel controlPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add Slider");
        labelField = new JTextField(8);
        minField = new JTextField(5);
        maxField = new JTextField(5);

        controlPanel.add(new JLabel("Label:"));
        controlPanel.add(labelField);
        controlPanel.add(new JLabel("Min:"));
        controlPanel.add(minField);
        controlPanel.add(new JLabel("Max:"));
        controlPanel.add(maxField);
        controlPanel.add(addButton);

        sliderListModel = new DefaultListModel<>();
        sliderList = new JList<>(sliderListModel);

        JScrollPane listScrollPane = new JScrollPane(sliderList);
        listScrollPane.setPreferredSize(new Dimension(150, 200));

        add(controlPanel, BorderLayout.NORTH);
        add(listScrollPane, BorderLayout.WEST);
        add(new JScrollPane(sliderPanel), BorderLayout.CENTER);

        setSize(600, 500);
        setVisible(true);
    }

    public JButton getAddButton() { return addButton; }
    public JTextField getLabelField() { return labelField; }
    public JTextField getMinField() { return minField; }
    public JTextField getMaxField() { return maxField; }
    public JList<String> getSliderList() { return sliderList; }
    public DefaultListModel<String> getSliderListModel() { return sliderListModel; }
    public JPanel getSliderPanel() { return sliderPanel; }
}
