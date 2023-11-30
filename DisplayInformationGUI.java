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
 * @version November 29, 2023
 */
public class DisplayInformationGUI {

    public JFrame displayDashboard(String dashboardType, JScrollPane scrollPane) {
        JFrame dashboardFrame = new JFrame(dashboardType + " Dashboard");
        dashboardFrame.setSize(900, 750);
        dashboardFrame.setLocationRelativeTo(null);
        dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboardFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        return dashboardFrame;
    }

    public JFrame displayMiscInfo(String informationType, String data) {
        JFrame infoFrame = new JFrame(informationType);
        infoFrame.setSize(900, 750);
        infoFrame.setLocationRelativeTo(null);
        infoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextArea textArea = new JTextArea(data);
        textArea.setEditable(false);
        infoFrame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        return infoFrame;
    }
}