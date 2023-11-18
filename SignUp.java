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
 * @version November 18, 2023
 */

public class SignUpForm extends JFrame implements ActionListener {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JButton confirmButton;

    /**
     * Constructor to initialize the Signup Form UI.
     */
    public SignUpForm() {
        // Set window title
        super("Sign Up");

        // Initialize text fields and button
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmField = new JPasswordField(20);
        confirmButton = new JButton("Confirm");

        // Set layout
        setLayout(new FlowLayout());
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Confirm Password:"));
        add(confirmField);
        add(confirmButton);

        // Add action listener to the button
        confirmButton.addActionListener(this);

        // Set window size and behavior
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Action performed when the Confirm button is clicked.
     *
     * @param e The ActionEvent triggering this method.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            String email = emailField.getText();
            char[] password = passwordField.getPassword();
            char[] confirm = confirmField.getPassword();

            // Actual registration logic should be added here
            if (isPasswordMatching(password, confirm)) {
                JOptionPane.showMessageDialog(this, "Registration Successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Passwords do not match!");
            }
        }
    }

    /**
     * Checks if the entered password and confirmation match.
     *
     * @param pass        The password entered by the user.
     * @param confirmPass The confirmation password entered by the user.
     * @return True if the passwords match, False otherwise.
     */
    private boolean isPasswordMatching(char[] pass, char[] confirmPass) {
        return Arrays.equals(pass, confirmPass);
    }

    /**
     * The main method to run the GUI, ensuring it operates within the event dispatch thread.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // GUI operations must run in the event dispatch thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SignUpForm();
            }
        });
    }
}
