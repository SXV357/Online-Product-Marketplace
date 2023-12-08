import java.awt.BorderLayout;
import javax.swing.*;

/**
 * Project 5 - DisplayInformationGUI.java
 * 
 * The interface associated with displaying various kinds of information for both customers and sellers.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version December 7, 2023
 */
public class DisplayDashboardGUI {

    /**
     * Displays the corresponding dashboard for both customers and sellers based on the data passed in
     * 
     * @param dashboardType The type of the dashboard to display
     * @param scrollPane The data to display in the dashboard
     */
    public void showDashboard(String dashboardType, JScrollPane scrollPane) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame dashboardFrame = new JFrame(dashboardType + " Dashboard");
                dashboardFrame.setSize(900, 750);
                dashboardFrame.setLocationRelativeTo(null);
                dashboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dashboardFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
                dashboardFrame.setVisible(true);
            }
        });
    }
}