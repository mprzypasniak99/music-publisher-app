package management.gui;

import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame implements ActionListener {
    private MusicPublisherDatabase database; // reference to the database
    private JTextField username; // field for username
    private JPasswordField pass; // field for password
    private JButton button; // button to send the data

    public LoginWindow(MusicPublisherDatabase db) { // code managing creating and placing window elements
        super("Log in");
        database = db; // save database reference

        // ============== Username field ==========
        username = new JTextField();
        username.setBounds(100, 100, 210, 30);

        JLabel lUs = new JLabel("Username:");
        lUs.setBounds(10, 100, 70, 30);

        // ============= Password field ==========
        pass = new JPasswordField();
        pass.setBounds(100, 150, 210, 30);

        JLabel lPas = new JLabel("Password: ");
        lPas.setBounds(10, 150, 70, 30);

        // ============= Log in button ============
        button = new JButton("Log in");
        button.setBounds(150, 200, 100, 30);

        button.addActionListener(this); // assign LoginWindow object as action listener to react to
                                            // user clicking the button

        add(username); add(pass); add(button); // add elements to the window

        setSize(400, 400);

        setLocationRelativeTo(null);

        add(lUs); add(lPas);

        setLayout(null);

        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // finish application upon clicking close button
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) { // action performed when the button is clicked
        if(actionEvent.getSource() == button) {
            database.addLoginData(username.getText(), new String(pass.getPassword()));
            // send data from the fields to the database connection handler

            if(login()) {
                setVisible(false);
                new MainMenuWindow(database); // handle the logging in procedure
            }
        }
    }

    private boolean login() { // handle logging in to the database
        if(!database.connect()) { // upon failing to log in, show message box
            new MessageWindow("Failed to log in!",
                    "Program was unable to log in to the database. Please try again");
            return false;
        }
        else { // for now, disconnect from the database and end application after successful login
            return true;
            //new MessageWindow("Successfully logged in1",
                    //"Program successfully logged in to the database.");
            //System.exit(1);
        } // will be changed later
    }
}
