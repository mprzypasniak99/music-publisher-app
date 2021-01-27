package management.gui;

import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class EventsMenu extends JFrame {
    private JButton[] buttons;
    public EventsMenu(MusicPublisherDatabase db) {
        buttons = new JButton[10];
        // ========== ADD CONCERT ===========
        buttons[0] = new JButton("Dodaj koncert");
        buttons[0].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new AddConcert(db);
            }
        });
        add(buttons[0]);
        // ========== EDIT CONCERT ==========
        buttons[1] = new JButton("Modyfikuj koncert");
        buttons[1].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new EditConcert(db);
            }
        });
        add(buttons[1]);
        // ========== EDIT CONCERT PERFORMERS ==========
        buttons[2] = new JButton("Zarządzaj wykonawcami na koncercie");
        buttons[2].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new EditConcertPerformers(db);
            }
        });
        add(buttons[2]);
        // ========== EDIT CONCERT PERFORMERS ==========
        buttons[3] = new JButton("Zarządzaj obsługą techniczną koncertu");
        buttons[3].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new EditConcertTechnicians(db);
            }
        });
        add(buttons[3]);
        // ========== ADD TOUR ============
        buttons[4] = new JButton("Dodaj trasę");
        buttons[4].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new AddTour(db);
            }
        });
        add(buttons[4]);
        // ========== EDIT TOUR ===========
        buttons[5] = new JButton("Modyfikuj trasę");
        buttons[5].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new EditTour(db);
            }
        });
        add(buttons[5]);
        // ========== ADD STUDIO =========
        buttons[6] = new JButton("Dodaj studio");
        buttons[6].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new AddStudio(db);
            }
        });
        add(buttons[6]);
        // ========== EDIT STUDIO =========
        buttons[7] = new JButton("Modyfikuj studio");
        buttons[7].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new EditStudio(db);
            }
        });
        add(buttons[7]);
        // ========== ADD SESSION =========
        buttons[8] = new JButton("Dodaj sesję");
        buttons[8].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new AddSession(db);
            }
        });
        add(buttons[8]);
        // ========== EDIT SESSION =========
        buttons[9] = new JButton("Modyfikuj sesję");
        buttons[9].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new EditSession(db);
            }
        });
        add(buttons[9]);
        // ========== EDIT SESSION PERFORMERS ==========
        buttons[3] = new JButton("Zarządzaj obsługą techniczną sesji");
        buttons[3].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new EditSessionTechnicians(db);
            }
        });

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(200,500);
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                EventsMenu.super.dispose();
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
