import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Project 5 - LoginGui.java
 * <p>
 * Class that constructs the GUI for a user that is logging in
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * @version November 20, 2023
 */
public class LoginGUI {
    private final JFrame FRAME = new JFrame("Log-In");
    private final JLabel EMAIL_LABEL = new JLabel("Email:");
    private JTextField email = new JTextField(15);
    private final JLabel PASSWORD_LABEL = new JLabel("Password:");
    private JTextField password = new JTextField(15);
    private final JButton LOG_IN_BUTTON = new JButton("Log In");
    private Database db = new Database();


    public LoginGUI() {
        logInDisplay();
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == LOG_IN_BUTTON) {
                if (validateLogin()) {
                    FRAME.setVisible(false);
                }

            }
        }
    };

    private boolean validateLogin() {
        String valid = db.retrieveUserMatchForLogin(email.getText(), password.getText());
        if (valid == null) {
            JOptionPane.showMessageDialog(null, "Email or Password is incorrect",
                    "Login Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {


            JOptionPane.showMessageDialog(null, "Login Successful",
                    "Success", JOptionPane.PLAIN_MESSAGE);
            return true;
        }
    }

    private void logInDisplay() {
        //Set up frame for the display
        FRAME.setSize(275, 125);
        FRAME.setLocationRelativeTo(null);
        FRAME.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Set up panel to hold buttons in frame
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(LOG_IN_BUTTON);
        LOG_IN_BUTTON.addActionListener(actionListener);
        FRAME.add(buttonPanel, BorderLayout.SOUTH);

        //Add panel for text fields and labels
        JPanel textPanel = new JPanel();
        textPanel.add(EMAIL_LABEL);
        textPanel.add(email);
        textPanel.add(PASSWORD_LABEL);
        textPanel.add(password);
        FRAME.add(textPanel);


        FRAME.setVisible(true);
    }

    //remove when done testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGUI();
            }
        });
    }
}
