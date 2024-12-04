package de.schmiereck.eventDemo1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EventDemo1Main {
    public static void main(final String[] args) {
        final CentralModel centralModel = new CentralModel();
        final ViewModel viewModel1 = new ViewModel();
        final ViewModel viewModel2 = new ViewModel();

        final View view1 = new View(viewModel1);
        final View view2 = new View(viewModel2);

        final ViewController viewController1 = new ViewController(viewModel1, centralModel);
        final ViewController viewController2 = new ViewController(viewModel2, centralModel);

        final AppController appController = new AppController();

        appController.addView(view1, viewController1);
        appController.addView(view2, viewController2);

        // Display views in a JFrame or other container
        JFrame frame = new JFrame("DFT Result");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Add views in seperate Panels with border layout:
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        {
            JPanel outerViewPanel = new JPanel();
            outerViewPanel.setLayout(new BoxLayout(outerViewPanel, BoxLayout.Y_AXIS));
            outerViewPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 10));

            JPanel titlePanel = new JPanel();
            titlePanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
            titlePanel.add(new JLabel("View 1:"));
            outerViewPanel.add(titlePanel);

            JPanel formViewPanel = new JPanel();
            formViewPanel.setLayout(new GridLayout(1, 2, 10, 10));
            formViewPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 10));

            JPanel labelPanel = new JPanel();
            labelPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
            labelPanel.add(new JLabel("Property:"));
            formViewPanel.add(labelPanel);

            JTextField textField = view1.getTextField();
            JPanel textPanel = new JPanel();
            textPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
            textPanel.add(textField);
            formViewPanel.add(textPanel);
            Dimension dimension = new Dimension(120, textField.getPreferredSize().height);
            //textField.setMaximumSize(dimension);
            //textField.setMinimumSize(dimension);
            textField.setPreferredSize(dimension);
            //textField.setMaximumSize(dimension);
            //textField.setSize(dimension);
            textField.revalidate();

            outerViewPanel.add(formViewPanel);

            panel.add(outerViewPanel);
        }
        {
            JPanel viewPanel = new JPanel();
            //viewPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            //viewPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
            SpringLayout springLayout = new SpringLayout();
            viewPanel.setLayout(springLayout);

            JLabel titleLabel = new JLabel("View 2:");
            viewPanel.add(titleLabel);

            JLabel aLabel = new JLabel("Property:");
            viewPanel.add(aLabel);

            JTextField aTextField = view2.getTextField();
            aTextField.setColumns(20);
            viewPanel.add(aTextField);

            JLabel bLabel = new JLabel("Long Property:");
            viewPanel.add(bLabel);

            JTextField bTextField = new JTextField();
            bTextField.setColumns(20);
            viewPanel.add(bTextField);

            JLabel cLabel = new JLabel("Prop.:");
            viewPanel.add(cLabel);

            JTextField cTextField = new JTextField();
            cTextField.setColumns(10);
            viewPanel.add(cTextField);

            // Left-align all Labels.
            springLayout.putConstraint(SpringLayout.EAST, aLabel, 0, SpringLayout.EAST, bLabel);
            springLayout.putConstraint(SpringLayout.EAST, cLabel, 0, SpringLayout.EAST, bLabel);

            // Title to the Top.
            springLayout.putConstraint(SpringLayout.NORTH, titleLabel, 10, SpringLayout.NORTH, viewPanel);
            //springLayout.putConstraint(SpringLayout.WEST, titleLabel, 5, SpringLayout.WEST, viewPanel);

            // aLabel to the bottom of title.
            springLayout.putConstraint(SpringLayout.NORTH, aLabel, 5, SpringLayout.SOUTH, titleLabel);
            //springLayout.putConstraint(SpringLayout.WEST, aLabel, 10, SpringLayout.WEST, viewPanel);

            // Align Text Field to his Label:
            springLayout.putConstraint(SpringLayout.SOUTH, aTextField, 5, SpringLayout.SOUTH, aLabel);
            springLayout.putConstraint(SpringLayout.WEST, aTextField, 10, SpringLayout.EAST, aLabel);

            // bLabel to the bottom of aLabel.
            springLayout.putConstraint(SpringLayout.NORTH, bLabel, 5, SpringLayout.SOUTH, aLabel);
            //springLayout.putConstraint(SpringLayout.WEST, bLabel, 10, SpringLayout.WEST, aLabel);

            // Align Text Field to his Label:
            springLayout.putConstraint(SpringLayout.SOUTH, bTextField, 5, SpringLayout.SOUTH, bLabel);
            springLayout.putConstraint(SpringLayout.WEST, bTextField, 10, SpringLayout.EAST, bLabel);

            // cLabel to the bottom of bLabel.
            springLayout.putConstraint(SpringLayout.NORTH, cLabel, 5, SpringLayout.SOUTH, bLabel);

            // Align Text Field to his Label:
            springLayout.putConstraint(SpringLayout.SOUTH, cTextField, 5, SpringLayout.SOUTH, cLabel);
            springLayout.putConstraint(SpringLayout.WEST, cTextField, 10, SpringLayout.EAST, cLabel);

            panel.add(viewPanel);
        }
        frame.add(panel);

        frame.setVisible(true);
    }
}
