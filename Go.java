import java.io.File;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class Go {
    public static void main(String[] args) {
        String file = "", params;
        Scanner scanner;
        int numberOfInitialSolutions, numberOfNeighborSolutions, numberOfTours, numberOfSharedWithNextSolution;

        JOptionPane.showMessageDialog(null, "This program is written for demonstrating the performance of the MBO algorithm." +
                "\nEven though MBO algorithm can be applied to any combinatorial optimization problem, " +
                "\nthis demo is specifically designed for solving QAP instances." +
                "\nTherefore the input format should be in the form of QAPLIB standards." +
                "\nWe also provided some sample QAPLIB instances within the package." +
                "\nNow please specify a QAP input file.", "MBO Demo", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser chooser = new JFileChooser(new File("."));
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile().getAbsolutePath();
        } else {
            System.exit(0);
        }
        params = JOptionPane.showInputDialog(null,
                "Please enter the parameters of the algorithm " +
                        "in the order of n k m x separated by spaces\nwhere \n\t" +
                        "n denotes the number of initial solutions (birds)\n\t" +
                        "k denotes the number of neighbor solutions to be considered\n\t" +
                        "m denotes the number of tours\n\t" +
                        "and x denotes the number of neighbor solutions to be shared with the next solution.\n" +
                        "Note that n and k values should be odd.\n" +
                        "A sample input should be as follows:\n\t" +
                        "51 3 10 1", "Parameters", JOptionPane.QUESTION_MESSAGE);
        try {
            scanner = new Scanner(params);
            numberOfInitialSolutions = scanner.nextInt();
            numberOfNeighborSolutions = scanner.nextInt();
            numberOfTours = scanner.nextInt();
            numberOfSharedWithNextSolution = scanner.nextInt();
            @SuppressWarnings("unused")
            BirdsAlgorithm ba = new BirdsAlgorithm(numberOfInitialSolutions, numberOfNeighborSolutions, numberOfTours, numberOfSharedWithNextSolution, 1, 1, 1, file);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Wrong parameter format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
