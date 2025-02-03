package de.schmiereck.noiseComp.swingView.moduleControls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class SliderController2 {
    private final SliderView view;
    private final Map<String, JSlider> sliders;
    private boolean editMode = false;
    private Point dragOffset;

    public SliderController2(SliderView view) {
        this.view = view;
        this.sliders = new HashMap<>();

        view.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSlider();
            }
        });

        view.getSliderPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    Component comp = view.getSliderPanel().getComponentAt(e.getPoint());
                    showEditMenu(comp, e.getX(), e.getY(), false);
                }
            }
        });
    }

    private void addSlider() {
        String label = view.getLabelField().getText().trim();
        int min, max;
        try {
            min = Integer.parseInt(view.getMinField().getText().trim());
            max = Integer.parseInt(view.getMaxField().getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter valid integers for min and max values.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (min >= max) {
            JOptionPane.showMessageDialog(view, "Min value must be less than max value.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (label.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Label cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (sliders.containsKey(label)) {
            JOptionPane.showMessageDialog(view, "Slider with this label already exists.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JSlider slider = new JSlider(min, max);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBorder(BorderFactory.createTitledBorder(label));
        slider.setBounds(50, 50, 200, 50); // Default position

        slider.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (editMode) {
                    dragOffset = e.getPoint();
                }
            }
        });

        slider.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (editMode) {
                    JSlider source = (JSlider) e.getSource();
                    Point location = source.getLocation();
                    location.translate(e.getX() - dragOffset.x, e.getY() - dragOffset.y);
                    source.setLocation(location);
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

        sliders.put(label, slider);
        view.getSliderListModel().addElement(label);
        view.getSliderPanel().add(slider);
        view.getSliderPanel().revalidate();
        view.getSliderPanel().repaint();
    }

    private void showEditMenu(final Component slider, final int x, final int y, final boolean hitSlider) {
        final JPopupMenu menu = new JPopupMenu();

        final JMenuItem editItem = new JMenuItem("Edit Slider");
        if (hitSlider) {
            editItem.setEnabled(true);
        } else {
            editItem.setEnabled(false);
        }
        editItem.addActionListener(e -> editSlider((JSlider)slider));
        menu.add(editItem);

        final JMenuItem toggleModeItem = new JMenuItem(editMode ? "Switch to Use Mode" : "Switch to Edit Mode");
        toggleModeItem.addActionListener(e -> toggleMode());
        menu.add(toggleModeItem);

        int adjustedX = slider.getLocation().x + x;
        int adjustedY = slider.getLocation().y + y;

        //menu.show(view.getSliderPanel(), x, y);
        menu.show(view.getSliderPanel(), adjustedX, adjustedY);
    }

    private void toggleMode() {
        editMode = !editMode;
        JOptionPane.showMessageDialog(view, "Mode switched to " + (editMode ? "Edit Mode" : "Use Mode"), "Mode Change", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editSlider(JSlider slider) {
        String newLabel = JOptionPane.showInputDialog(view, "Enter new label:", ((javax.swing.border.TitledBorder) slider.getBorder()).getTitle());
        if (newLabel != null && !newLabel.trim().isEmpty()) {
            slider.setBorder(BorderFactory.createTitledBorder(newLabel));
        }

        String newMin = JOptionPane.showInputDialog(view, "Enter new min value:", slider.getMinimum());
        String newMax = JOptionPane.showInputDialog(view, "Enter new max value:", slider.getMaximum());
        try {
            int minValue = Integer.parseInt(newMin);
            int maxValue = Integer.parseInt(newMax);
            if (minValue < maxValue) {
                slider.setMinimum(minValue);
                slider.setMaximum(maxValue);
            } else {
                JOptionPane.showMessageDialog(view, "Min value must be less than max value.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Invalid numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }

        view.getSliderPanel().revalidate();
        view.getSliderPanel().repaint();
    }
}


