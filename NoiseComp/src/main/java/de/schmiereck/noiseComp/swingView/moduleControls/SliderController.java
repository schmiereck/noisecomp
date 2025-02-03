package de.schmiereck.noiseComp.swingView.moduleControls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class SliderController {
    private final SliderView view;
    private final Map<String, SliderModel> sliders;
    private boolean editMode = false;
    private Point dragOffset;

    public SliderController(SliderView view) {
        this.view = view;
        this.sliders = new HashMap<>();

        view.getAddButton().addActionListener(e -> showEditDialog(null));

        view.getSliderPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    Component comp = view.getSliderPanel().getComponentAt(e.getPoint());
                    showEditMenu(comp, e.getX(), e.getY(), comp instanceof JSlider);
                }
            }
        });
    }

    private void showEditDialog(SliderModel sliderModel) {
        boolean isEditMode = sliderModel != null;
        String initialLabel = isEditMode ? sliderModel.getLabel() : "";
        int initialMin = isEditMode ? sliderModel.getMin() : 0;
        int initialMax = isEditMode ? sliderModel.getMax() : 100;
        int initialWidth = isEditMode ? sliderModel.getWidth() : 200;
        int initialHeight = isEditMode ? sliderModel.getHeight() : 50;

        JPanel panel = new JPanel(new GridLayout(5, 2));
        JTextField labelField = new JTextField(initialLabel);
        JTextField minField = new JTextField(String.valueOf(initialMin));
        JTextField maxField = new JTextField(String.valueOf(initialMax));
        JTextField widthField = new JTextField(String.valueOf(initialWidth));
        JTextField heightField = new JTextField(String.valueOf(initialHeight));

        panel.add(new JLabel("Label:"));
        panel.add(labelField);
        panel.add(new JLabel("Min Value:"));
        panel.add(minField);
        panel.add(new JLabel("Max Value:"));
        panel.add(maxField);
        panel.add(new JLabel("Width:"));
        panel.add(widthField);
        panel.add(new JLabel("Height:"));
        panel.add(heightField);

        int result = JOptionPane.showConfirmDialog(view, panel, isEditMode ? "Edit Slider" : "Add Slider", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String label = labelField.getText().trim();
                int min = Integer.parseInt(minField.getText().trim());
                int max = Integer.parseInt(maxField.getText().trim());
                int width = Integer.parseInt(widthField.getText().trim());
                int height = Integer.parseInt(heightField.getText().trim());

                if (min >= max) {
                    throw new IllegalArgumentException("Min must be less than max.");
                }

                if (label.isEmpty()) {
                    throw new IllegalArgumentException("Label cannot be empty.");
                }

                if (isEditMode) {
                    updateSlider(sliderModel, label, min, max, width, height);
                } else {
                    addSlider(label, min, max, width, height);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addSlider(String label, int min, int max, int width, int height) {
        if (sliders.containsKey(label)) {
            JOptionPane.showMessageDialog(view, "Slider with this label already exists.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JSlider slider = createSlider(label, min, max, width, height);
        SliderModel model = new SliderModel(label, min, max, width, height);
        sliders.put(label, model);
        view.getSliderListModel().addElement(label);
        view.getSliderPanel().add(slider);
        view.getSliderPanel().revalidate();
        view.getSliderPanel().repaint();
    }

    private void updateSlider(SliderModel model, String label, int min, int max, int width, int height) {
        JSlider slider = getSliderByLabel(model.getLabel());
        if (slider != null) {
            slider.setMinimum(min);
            slider.setMaximum(max);
            slider.setBorder(BorderFactory.createTitledBorder(label));
            slider.setSize(width, height);
            if (!label.equals(model.getLabel())) {
                sliders.remove(model.getLabel());
                sliders.put(label, model);
            }
            model.setLabel(label);
            model.setMin(min);
            model.setMax(max);
            model.setWidth(width);
            model.setHeight(height);
            refreshSliderList();
        }
    }

    private JSlider createSlider(String label, int min, int max, int width, int height) {
        JSlider slider = new JSlider(min, max);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBorder(BorderFactory.createTitledBorder(label));
        slider.setBounds(50, 50, width, height);

        slider.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (editMode) {
                    dragOffset = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (editMode) {
                    saveSliderPosition(slider);
                }
            }
        });

        slider.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (editMode) {
                    Point location = slider.getLocation();
                    location.translate(e.getX() - dragOffset.x, e.getY() - dragOffset.y);
                    slider.setLocation(location);
                    view.getSliderPanel().repaint();
                }
            }
        });

        slider.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    //Component comp = view.getSliderPanel().getComponentAt(e.getPoint());
                    Component comp = (Component) e.getSource();
                    if (comp instanceof JSlider) {
                        showEditMenu((JSlider) comp, e.getX(), e.getY(), true);
                        //showEditMenu((JSlider) comp, comp.getX(), comp.getY(), true);
                    }
                }
            }
        });

        return slider;
    }

    private void saveSliderPosition(JSlider slider) {
        SliderModel model = sliders.get(((javax.swing.border.TitledBorder) slider.getBorder()).getTitle());
        if (model != null) {
            model.setX(slider.getX());
            model.setY(slider.getY());
        }
    }

    private void showEditMenu(final Component comp, final int x, final int y, final boolean hitSlider) {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem editItem = new JMenuItem("Edit Slider");
        editItem.setEnabled(hitSlider);
        editItem.addActionListener(e -> showEditDialog(sliders.get(((javax.swing.border.TitledBorder) ((JSlider) comp).getBorder()).getTitle())));
        menu.add(editItem);

        JMenuItem toggleModeItem = new JMenuItem(editMode ? "Switch to Use Mode" : "Switch to Edit Mode");
        toggleModeItem.addActionListener(e -> toggleMode());
        menu.add(toggleModeItem);

        int adjustedX = comp.getLocation().x + x;
        int adjustedY = comp.getLocation().y + y;

        //menu.show(view.getSliderPanel(), x, y);
        menu.show(view.getSliderPanel(), adjustedX, adjustedY);
    }

    private void toggleMode() {
        editMode = !editMode;
        JOptionPane.showMessageDialog(view, "Mode switched to " + (editMode ? "Edit Mode" : "Use Mode"), "Mode Change", JOptionPane.INFORMATION_MESSAGE);
    }

    private JSlider getSliderByLabel(String label) {
        for (Component comp : view.getSliderPanel().getComponents()) {
            if (comp instanceof JSlider) {
                JSlider slider = (JSlider) comp;
                if (((javax.swing.border.TitledBorder) slider.getBorder()).getTitle().equals(label)) {
                    return slider;
                }
            }
        }
        return null;
    }

    private void refreshSliderList() {
        view.getSliderListModel().clear();
        for (String label : sliders.keySet()) {
            view.getSliderListModel().addElement(label);
        }
    }
}


