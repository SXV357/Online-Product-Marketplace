import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Project 5 - ErrorMessageGUI.java
 * 
 * The interface associated with displaying error messages caused due to actions taken by both customers and sellers.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version December 6, 2023
 */
public class ErrorMessageGUI {
    public ErrorMessageGUI(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
