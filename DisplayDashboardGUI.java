import java.awt.BorderLayout;
import javax.swing.*;

/**
 * Project 5 - DisplayDashboardGUIs.java
 * 
 * The interface associated with displaying dashboards for both customers and sellers.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 * 
 * @version November 21, 2023
 */
public class DisplayDashboardGUI {

    public DisplayDashboardGUI(String dashbordType, JScrollPane scrollPane) {
        JFrame dashboardFrame = new JFrame(dashbordType + " Dashboard");
        dashboardFrame.setSize(900, 750);
        dashboardFrame.setLocationRelativeTo(null);
        dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboardFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        dashboardFrame.setVisible(true);
    }
}