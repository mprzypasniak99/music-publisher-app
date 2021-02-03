package management.gui;

import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class MainMenuWindow extends JFrame {
    private MusicPublisherDatabase database;

    public MainMenuWindow(MusicPublisherDatabase db) {
        super("Main Menu");

        database = db;

        // ============ BUTTONS ================
        JButton artists = new JButton("Artyści, Albumy");
        artists.setBounds(0, 0, 150, 20);
        artists.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new ArtistsMenu(db);
            }
        });

        JButton employees = new JButton("Pracownicy, rachunki");
        employees.setBounds(0, 20, 150, 20);
        employees.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new EmployeesMenu(db);
            }
        });

        JButton events = new JButton("Koncerty, trasy, sesje");
        events.setBounds(0, 40, 150, 20);
        events.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EventsMenu(db);
            }
        });
        JButton reports = new JButton("Raporty i sprzedaż");
        reports.setBounds(0, 60, 150, 20);
        reports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReportsMenu(db);
            }
        });

        JButton search = new JButton("Przegląd danych");
        search.setBounds(0,80,150,20);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DataViewer(db);
            }
        });
        setSize(800, 600);

        setLocationRelativeTo(null);

        // ============ NEXT EVENTS ============
        JLabel concerts = new JLabel("Najbliższe koncerty:");
        concerts.setBounds(350, 20, 200, 20);
        ArrayList<String> con_l = db.initializeConcerts();
        if(con_l.size() > 0) {
            DefaultListModel<String> con_dl = new DefaultListModel<String>();
            for (String s : con_l) {
                con_dl.addElement(s);
            }
            JList<String> list = new JList<>(con_dl);
            list.setBounds(350, 40, 200, 20*con_l.size());
            add(list);
        }
        JLabel recordings = new JLabel("Najbliższe sesje nagraniowe:");
        recordings.setBounds(350, 40+20*con_l.size(), 200, 20);
        ArrayList<String> rec_l = db.initializeRecordings();
        if(rec_l.size() > 0) {
            DefaultListModel<String> rec_dl = new DefaultListModel<String>();
            for (String s : rec_l) {
                rec_dl.addElement(s);
            }
            JList<String> list = new JList<>(rec_dl);
            list.setBounds(350, 60+20*con_l.size(), 200, 20*rec_l.size());
            add(list);
        }
        add(artists);
        add(employees);
        add(events);
        add(reports);
        add(search);

        add(concerts);
        add(recordings);

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
