import javax.swing.*;
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
 * @version December 3, 2023
 */
public class LoginGUI {
    private JFrame loginFrame;
    private JLabel emailLabel;
    private JTextField emailField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton returnToMainMenuButton;
    private JButton signupButton;
    private InitialClient initialClient;

    public LoginGUI(InitialClient initialClient) {
        this.initialClient = initialClient;
        logInDisplay();
    }

    public ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                String email = emailField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                initialClient.attemptLogin(email, password);
                loginFrame.dispose();
            } else if (e.getSource() == returnToMainMenuButton) {
                loginFrame.dispose();
                new UserGUI(initialClient);
            } else if (e.getSource() == signupButton) {
                loginFrame.dispose();
                new SignUpGUI(initialClient);
            }
        }
    };

    public void logInDisplay() {
        //Set up frame for the display
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                loginFrame = new JFrame("Log In");
                loginFrame.setSize(275, 125);
                loginFrame.setLocationRelativeTo(null);
                loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                loginButton = new JButton("Log In");
                loginButton.addActionListener(actionListener);
                signupButton = new JButton("Sign Up");
                signupButton.addActionListener(actionListener);
                returnToMainMenuButton = new JButton("Main Menu");
                returnToMainMenuButton.addActionListener(actionListener);

                emailLabel = new JLabel("Email:");
                emailField = new JTextField(15);
                passwordLabel = new JLabel("Password:");
                passwordField = new JPasswordField(15); 

                // Reset contents of all fields when GUI is opened
                emailField.setText("");
                passwordField.setText(null);

                JPanel textPanel = new JPanel();
                textPanel.setLayout(new FlowLayout());
                textPanel.add(emailLabel);
                textPanel.add(emailField);
                textPanel.add(passwordLabel);
                textPanel.add(passwordField);

                JPanel buttonPanel = new JPanel();
                buttonPanel.add(loginButton);
                buttonPanel.add(returnToMainMenuButton);

                loginFrame.add(textPanel, BorderLayout.CENTER);
                loginFrame.add(buttonPanel, BorderLayout.SOUTH);

                loginFrame.setVisible(true);
            }
        });
    }
}