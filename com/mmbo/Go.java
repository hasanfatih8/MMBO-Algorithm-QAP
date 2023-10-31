package com.mmbo;

import java.io.File;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class Go {
    public static final boolean DEBUG_MODE = true;

    public static void main(String[] args) {
        String file = "";
        String params;

        // Step 1: Choose a QAP input file
        JOptionPane.showMessageDialog(null, 
            "This program is written for demonstrating the performance of the MBO algorithm." +
            "\nEven though MBO algorithm can be applied to any combinatorial optimization problem, " +
            "\nthis demo is specifically designed for solving QAP instances." +
            "\nTherefore the input format should be in the form of QAPLIB standards." +
            "\nWe also provided some sample QAPLIB instances within the package." +
            "\nNow please specify a QAP input file.", "Multimeme Migrating Bird Optimization for QAP", JOptionPane.INFORMATION_MESSAGE);
        
        JFileChooser fileChooser = new JFileChooser(new File("."));
        int returnVal = fileChooser.showOpenDialog(null);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile().getAbsolutePath();

            // Step 2: Input parameters
            JPanel paramPanel = new JPanel(new GridLayout(0, 1));
            paramPanel.add(new JLabel("n - number of initial solutions:"));
            JTextField nField = new JTextField(5);
            paramPanel.add(nField);
            paramPanel.add(new JLabel("\nk - number of neighbor solutions to be considered:"));
            JTextField kField = new JTextField(5);
            paramPanel.add(kField);
            paramPanel.add(new JLabel("\nm - number of tours:"));
            JTextField mField = new JTextField(5);
            paramPanel.add(mField);
            paramPanel.add(new JLabel("\nx - number of neighbor solutions to be shared with the next solution:"));
            JTextField xField = new JTextField(5);
            paramPanel.add(xField);

            int paramResult = JOptionPane.showConfirmDialog(null, paramPanel,
                "Please enter the parameters of the algorithm", JOptionPane.OK_CANCEL_OPTION);

            if (paramResult == JOptionPane.OK_OPTION) {
                try {
                    int numberOfInitialSolutions = Integer.parseInt(nField.getText());
                    int numberOfNeighborSolutions = Integer.parseInt(kField.getText());
                    int numberOfTours = Integer.parseInt(mField.getText());
                    int numberOfSharedWithNextSolution = Integer.parseInt(xField.getText());

                    // Your BirdsAlgorithm instantiation here with the obtained parameters.
                    BirdsAlgorithm ba = new BirdsAlgorithm(numberOfInitialSolutions, numberOfNeighborSolutions, numberOfTours, numberOfSharedWithNextSolution, 1, 1, 1, file);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Wrong parameter format", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
