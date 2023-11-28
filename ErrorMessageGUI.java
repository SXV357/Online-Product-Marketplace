import javax.swing.JOptionPane;

public class ErrorMessageGUI {
    public ErrorMessageGUI(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
