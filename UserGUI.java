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


public class UserGUI {
    private final JFrame frame = new JFrame("Boilermaker Market");  //ADD TITLE HERE
    private final JButton LOGIN_BUTTON = new JButton("Log In");
    private final JButton SIGN_UP_BUTTON = new JButton("Sign Up");

    InitialClient initialClient;
    public UserGUI(InitialClient initialClient) {
        this.initialClient = initialClient;
        mainMenuDisplay();
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == LOGIN_BUTTON) {
                logIn();
                frame.dispose();
            }
            if (e.getSource() == SIGN_UP_BUTTON) {
                signUp();
                frame.dispose();
            }
        }
    };

    private void logIn() {
        System.out.println("This is where Log In GUI starts");  //Change to call LogIn GUI when implemented
        initialClient.loginPage();
    }

    private void signUp() {
        System.out.println("This is where Sign Up Gui starts");  //Change to call SignUp GUI when implemented
        initialClient.signUpPage();
    }

    public void mainMenuDisplay () {
        //Set up frame for the display
        frame.setSize(320, 150);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Set up panel to hold buttons in frame
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(LOGIN_BUTTON);
        buttonPanel.add(SIGN_UP_BUTTON);
        LOGIN_BUTTON.addActionListener(actionListener);
        SIGN_UP_BUTTON.addActionListener(actionListener);

        //Set up textArea to hold welcome message
        JTextArea welcomeMessage = new JTextArea("\nWelcome to the Boilermaker Market!", 4, 5);
        welcomeMessage.setFont(new Font("Georgia", Font.PLAIN,18));

        //Set up container to hold the button panel and textArea
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        content.add(buttonPanel, BorderLayout.SOUTH);
        content.add(welcomeMessage, BorderLayout.CENTER);

        //Set the frame to visible
        frame.setVisible(true);
    }

    //remove when done testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserGUI(null);
            }
        });
    }
}
