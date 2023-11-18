import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Project 5 - User.java
 * <p>
 * Class that represents the characteristics associated with all users in the
 * application.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * @version November 12, 2023
 */


//TODO check for if static works with the concurrency

public class UserGUI {
    private final JButton LOGIN_BUTTON = new JButton("Log In");
    private final JButton SIGN_UP_BUTTON = new JButton("Sign Up");
    private JFrame frame = new JFrame("");  //ADD TITLE HERE

    public UserGUI() {
        mainMenuDisplay();
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == LOGIN_BUTTON) {
                logIn();
                frame.setVisible(false);
            }
            if (e.getSource() == SIGN_UP_BUTTON) {
                signUp();
                frame.setVisible(false);
            }
        }
    };

    private void logIn() {
        System.out.println("This is where Log In GUI starts");  //Change to call LogIn GUI when implemented
    }

    private void signUp() {
        System.out.println("This is where Sign Up Gui starts");  //Change to call SignUp GUI when implemented
    }

    public void mainMenuDisplay () {
        //Set up frame for the display
        frame.setSize(225, 150);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Set up panel to hold buttons in frame
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(LOGIN_BUTTON);
        buttonPanel.add(SIGN_UP_BUTTON);
        LOGIN_BUTTON.addActionListener(actionListener);
        SIGN_UP_BUTTON.addActionListener(actionListener);

        //Set up textArea to hold welcome message
        JTextArea welcomeMessage = new JTextArea("\nWelcome to Title!", 4, 5);  //ADD TITLE HERE
        welcomeMessage.setFont(new Font("Georgia", Font.PLAIN,18));

        //Set up container to hold the button panel and textArea
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        content.add(buttonPanel, BorderLayout.SOUTH);
        content.add(welcomeMessage, BorderLayout.CENTER);

        //Set the frame to visible
        frame.setVisible(true);
    }

    public void editUserDisplay() {

    }


    //remove when done testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserGUI();
            }
        });

    }
}
