import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Project 5 - LoginGUI.java
 * 
 * Class that constructs the GUI for a user that is logging in
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version December 6, 2023
 */
public class LoginGUI {
    private JFrame loginFrame;
    private JLabel emailLabel;
    private JTextField emailField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;
    private JButton forgotPasswordButton;
    private InitialClient initialClient;

    /**
     * Creates a new LoginGUI instance utilizing the initial client for making requests to the server
     * 
     * @param initialClient The client for handling logging the user back into the application
     */
    public LoginGUI(InitialClient initialClient) {
        this.initialClient = initialClient;
        logInDisplay();
    }

    /**
     * Add event listeners for all buttons
     */
    public ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                String email = emailField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                loginFrame.dispose();
                initialClient.attemptLogin(email, password);
            } else if (e.getSource() == signUpButton) {
                loginFrame.dispose();
                new SignUpGUI(initialClient);
            } else if (e.getSource() == forgotPasswordButton) {
                String email = JOptionPane.showInputDialog(null, "What is your email?",
                "Email", JOptionPane.QUESTION_MESSAGE);
                if (email == null) {
                    return;
                }
                String newPassword = JOptionPane.showInputDialog(null, "What is the new password?",
                "New Password", JOptionPane.QUESTION_MESSAGE);
                if (newPassword == null) {
                    return;
                }
                loginFrame.dispose();
                initialClient.resetPassword(email, newPassword);
            }
        }
    };

    /**
     * Constructs the login GUI and displays it
     */
    public void logInDisplay() {
        //Set up frame for the display
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                loginFrame = new JFrame("Log In");
                loginFrame.setSize(350, 150);
                loginFrame.setLocationRelativeTo(null);
                loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                loginButton = new JButton("Log In");
                loginButton.addActionListener(actionListener);
                signUpButton = new JButton("Sign Up");
                signUpButton.addActionListener(actionListener);
                forgotPasswordButton = new JButton("Forgot Password?");
                forgotPasswordButton.addActionListener(actionListener);

                emailLabel = new JLabel("Email:");
                emailField = new JTextField(15);
                passwordLabel = new JLabel("Password:");
                passwordField = new JPasswordField(15);

                // Reset contents of all fields when GUI is opened
                emailField.setText("");
                passwordField.setText(null);

                JPanel textPanel = new JPanel();
                textPanel.setLayout(new GridLayout(2, 2));
                textPanel.setBorder(new EmptyBorder(0, 30, 0, 20));

                textPanel.add(emailLabel);
                textPanel.add(emailField);
                textPanel.add(passwordLabel);
                textPanel.add(passwordField);

                JPanel mainButtonPanel = new JPanel();
                mainButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
                mainButtonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

                mainButtonPanel.add(loginButton);
                mainButtonPanel.add(signUpButton);
                mainButtonPanel.add(forgotPasswordButton);

                loginFrame.add(textPanel, BorderLayout.CENTER);
                loginFrame.add(mainButtonPanel, BorderLayout.SOUTH);

                loginFrame.setVisible(true);
            }
        });
    }
}