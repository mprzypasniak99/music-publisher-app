package management.gui;

import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainMenuWindow extends JFrame {
    private MusicPublisherDatabase database;

    public MainMenuWindow(MusicPublisherDatabase db) {
        super("Main Menu");

        database = db;

        // ============ BUTTONS ================
        JButton artists = new JButton("Artyści, Albumy");
        artists.setBounds(75, 50, 450, 50);

        JButton employees = new JButton("Pracownicy, rachunki");
        employees.setBounds(75, 175, 450, 50);

        JButton events = new JButton("Koncerty, trasy");
        events.setBounds(75, 300, 450, 50);

        setSize(600, 600);

        add(artists);
        add(employees);
        add(events);

        setLayout(null);

        setVisible(true);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                database.disconnect();
                MainMenuWindow.super.dispose();
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {

            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {

            }
        });
    }


}
