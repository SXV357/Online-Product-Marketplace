import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


/**
 * Project 5 - SignUpForm.java
 *
 * The interface associated with user signup.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 *
 * @version November 29, 2023
 */

public class SignUp extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JComboBox<String> roleComboBox;
    private JButton confirmButton;

    // Constructor to initialize and set up the GUI components.
    public SignUp() {
        createUI();
    }

    // Creates and arranges all the GUI components in the form.
    private void createUI() {
        setTitle("Sign Up");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout(5, 2, 5, 5));

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Confirm Password:"));
        confirmField = new JPasswordField();
        add(confirmField);

        add(new JLabel("Role:"));
        roleComboBox = new JComboBox<>(new String[]{"Customer", "Seller"});
        add(roleComboBox);

        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(this::confirmAction);
        add(confirmButton);
    }

    // Defines the action to be performed when the confirm button is clicked.
    // It checks if the entered passwords match and displays a message accordingly.
    private void confirmAction(ActionEvent e) {
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmField.getPassword());

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
        } else {
            JOptionPane.showMessageDialog(this, "Registration form submitted.");
        }
    }

    // Clears all input fields in the form.
    private void clearFields() {
        emailField.setText("");
        passwordField.setText("");
        confirmField.setText("");
        roleComboBox.setSelectedIndex(0); // Resets to the first role option
    }

    // The entry point of the application. It creates and shows the sign-up form.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SignUp().setVisible(true));
    }
}
