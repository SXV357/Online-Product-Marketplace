import java.awt.BorderLayout;
import javax.swing.*;

/**
 * Project 5 - DisplayInformationGUI.java
 * 
 * The interface associated with displaying various kinds of information for both customers and sellers.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 * 
 * @version December 2, 2023
 */
public class DisplayInformationGUI {

    public void displayDashboard(String dashboardType, JScrollPane scrollPane) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame dashboardFrame = new JFrame(dashboardType + " Dashboard");
                dashboardFrame.setSize(900, 750);
                dashboardFrame.setLocationRelativeTo(null);
                dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dashboardFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
                dashboardFrame.setVisible(true);
            }
        });
    }

    public void displayMiscInfo(String informationType, String data) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame infoFrame = new JFrame(informationType);
                infoFrame.setSize(900, 750);
                infoFrame.setLocationRelativeTo(null);
                infoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JTextArea textArea = new JTextArea(data);
                textArea.setEditable(false);
                infoFrame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
                infoFrame.setVisible(true);
            }
        });
    }
}