package de.schmiereck.eventDemo1;

import javax.swing.*;
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
        JFrame frame = new JFrame("EventDemo1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Add views in seperate Panels with border layout:
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        {
            final JPanel outerViewPanel = new JPanel();
            outerViewPanel.setLayout(new BoxLayout(outerViewPanel, BoxLayout.Y_AXIS));
            outerViewPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 10));

            final JPanel titlePanel = new JPanel();
            titlePanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
            titlePanel.add(new JLabel("View 1:"));
            outerViewPanel.add(titlePanel);

            final JPanel formViewPanel = new JPanel();
            formViewPanel.setLayout(new GridLayout(3, 2, 10, 10));
            formViewPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 10));

            // Property 1:
            {
                final JPanel property1LabelPanel = new JPanel();
                property1LabelPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
                property1LabelPanel.add(new JLabel("Property 1:"));
                formViewPanel.add(property1LabelPanel);

                final JTextField property1TextField = view1.getProperty1TextField();
                final JPanel property1TextPanel = new JPanel();
                property1TextPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
                property1TextPanel.add(property1TextField);
                formViewPanel.add(property1TextPanel);
                property1TextField.setPreferredSize(new Dimension(120, property1TextField.getPreferredSize().height));
                property1TextField.revalidate();
            }
            // Property 2:
            {
                final JPanel property2LabelPanel = new JPanel();
                property2LabelPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
                property2LabelPanel.add(new JLabel("Property 2:"));
                formViewPanel.add(property2LabelPanel);

                final JTextField property2TextField = view1.getProperty2TextField();
                final JPanel property2TextPanel = new JPanel();
                property2TextPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
                property2TextPanel.add(property2TextField);
                formViewPanel.add(property2TextPanel);
                property2TextField.setPreferredSize(new Dimension(120, property2TextField.getPreferredSize().height));
                property2TextField.revalidate();
            }
            // Property 3:
            {
                final JPanel property3LabelPanel = new JPanel();
                property3LabelPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
                property3LabelPanel.add(new JLabel("Property 3:"));
                formViewPanel.add(property3LabelPanel);

                final JTextField property3TextField = view1.getProperty3TextField();
                final JPanel property3TextPanel = new JPanel();
                property3TextPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
                property3TextPanel.add(property3TextField);
                formViewPanel.add(property3TextPanel);
                property3TextField.setPreferredSize(new Dimension(120, property3TextField.getPreferredSize().height));
                property3TextField.revalidate();
            }

            outerViewPanel.add(formViewPanel);

            final JButton updateButton = view1.getUpdateButton();
            outerViewPanel.add(updateButton);

            panel.add(outerViewPanel);
        }
        {
            final JPanel viewPanel = new JPanel();
            //viewPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            //viewPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
            final SpringLayout springLayout = new SpringLayout();
            viewPanel.setLayout(springLayout);

            final JLabel titleLabel = new JLabel("View 2:");
            viewPanel.add(titleLabel);

            final JLabel property1Label = new JLabel("Property 1:");
            viewPanel.add(property1Label);

            final JTextField property1TextField = view2.getProperty1TextField();
            property1TextField.setColumns(20);
            viewPanel.add(property1TextField);

            final JLabel property2Label = new JLabel("Long Property 2:");
            viewPanel.add(property2Label);

            final JTextField property2TextField = view2.getProperty2TextField();
            property2TextField.setColumns(20);
            viewPanel.add(property2TextField);

            final JLabel property3Label = new JLabel("Prop. 3:");
            viewPanel.add(property3Label);

            final JTextField property3TextField = view2.getProperty3TextField();
            property3TextField.setColumns(10);
            viewPanel.add(property3TextField);

            final JButton updateButton = view2.getUpdateButton();
            viewPanel.add(updateButton);

            // Left-align all Labels.
            springLayout.putConstraint(SpringLayout.EAST, property1Label, 0, SpringLayout.EAST, property2Label);
            springLayout.putConstraint(SpringLayout.EAST, property3Label, 0, SpringLayout.EAST, property2Label);

            // Title to the Top.
            springLayout.putConstraint(SpringLayout.NORTH, titleLabel, 10, SpringLayout.NORTH, viewPanel);
            //springLayout.putConstraint(SpringLayout.WEST, titleLabel, 5, SpringLayout.WEST, viewPanel);

            // property1Label to the bottom of title.
            springLayout.putConstraint(SpringLayout.NORTH, property1Label, 5, SpringLayout.SOUTH, titleLabel);
            //springLayout.putConstraint(SpringLayout.WEST, property1Label, 10, SpringLayout.WEST, viewPanel);

            // Align Text Field to his Label:
            springLayout.putConstraint(SpringLayout.SOUTH, property1TextField, 5, SpringLayout.SOUTH, property1Label);
            springLayout.putConstraint(SpringLayout.WEST, property1TextField, 10, SpringLayout.EAST, property1Label);

            // property2Label to the bottom of property1Label.
            springLayout.putConstraint(SpringLayout.NORTH, property2Label, 5, SpringLayout.SOUTH, property1Label);
            //springLayout.putConstraint(SpringLayout.WEST, property2Label, 10, SpringLayout.WEST, property1Label);

            // Align Text Field to his Label:
            springLayout.putConstraint(SpringLayout.SOUTH, property2TextField, 5, SpringLayout.SOUTH, property2Label);
            springLayout.putConstraint(SpringLayout.WEST, property2TextField, 10, SpringLayout.EAST, property2Label);

            // property3Label to the bottom of property2Label.
            springLayout.putConstraint(SpringLayout.NORTH, property3Label, 5, SpringLayout.SOUTH, property2Label);

            // Align Text Field to his Label:
            springLayout.putConstraint(SpringLayout.SOUTH, property3TextField, 5, SpringLayout.SOUTH, property3Label);
            springLayout.putConstraint(SpringLayout.WEST, property3TextField, 10, SpringLayout.EAST, property3Label);

            // Update-Button.
            springLayout.putConstraint(SpringLayout.NORTH, updateButton, 15, SpringLayout.SOUTH, property3TextField);
            springLayout.putConstraint(SpringLayout.WEST, updateButton, 0, SpringLayout.WEST, property3TextField);

            panel.add(viewPanel);
        }
        frame.add(panel);

        frame.setVisible(true);
    }
}
