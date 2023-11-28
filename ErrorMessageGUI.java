import javax.swing.JOptionPane;
/**
 * Project 5 - ErrorMessageGUI.java
 * 
 * The interface associated with displaying error messages caused due to actions taken by both customers and sellers.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 * 
 * @version November 28, 2023
 */
public class ErrorMessageGUI {
    public ErrorMessageGUI(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
