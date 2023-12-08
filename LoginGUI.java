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
 * @version December 6, 2023
 */
public class LoginGUI {
    private JFrame loginFrame;
    private JLabel emailLabel;
    private JTextField emailField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton returnToMainMenuButton;
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
            } else if (e.getSource() == returnToMainMenuButton) {
                loginFrame.dispose();
                new UserGUI(initialClient);
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
                loginFrame.setSize(275, 125);
                loginFrame.setLocationRelativeTo(null);
                loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                loginButton = new JButton("Log In");
                loginButton.addActionListener(actionListener);
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