import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Project 5 - SignUpForm.java
 *
 * The interface associated with user signup.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 *
 * @version November 19, 2023
 */
public class SignUp extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JComboBox<UserRole> roleComboBox;
    private JButton confirmButton;
    private Database db;

    public SignUp(Database database) {
        this.db = database;
        createUI();
    }

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
        roleComboBox = new JComboBox<>(UserRole.values());
        add(roleComboBox);

        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(this::confirmAction);
        add(confirmButton);
    }

    private void confirmAction(ActionEvent e) {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmField.getPassword());

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        if (db.retrieveUserMatchForSignUp(email) != null) {
            JOptionPane.showMessageDialog(this, "Email already in use. Please use a different email.");
            return;
        }

        UserRole role = (UserRole) roleComboBox.getSelectedItem();
        User newUser = new User(email, password, role);
        db.addToDatabase("users.csv", newUser.toString());

        JOptionPane.showMessageDialog(this, "Registration successful!");
        this.dispose();
    }

    public static void main(String[] args) {
        // Ensure the GUI is created in the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> new SignUp(new Database()).setVisible(true));
    }
}
