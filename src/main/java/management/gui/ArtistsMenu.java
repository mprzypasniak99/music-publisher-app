package management.gui;

import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ArtistsMenu extends JFrame {
    JButton[] buttons;
    public ArtistsMenu(MusicPublisherDatabase db) {
        buttons = new JButton[5];
        // ========== ADD ARTIST ===========
        buttons[0] = new JButton("Dodaj artystę");
        buttons[0].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new AddArtist(db);
            }
        });
        add(buttons[0]);
        // ========== EDIT ARTIST ==========
        buttons[1] = new JButton("Modyfikuj artystę");
        buttons[1].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new EditArtist(db);
            }
        });
        add(buttons[1]);
        // ========== EDIT BAND =============
        buttons[2] = new JButton("Modyfikuj członków zespołu");
        buttons[2].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new EditBandMembers(db);
            }
        });
        add(buttons[2]);
        // ========== ADD ALBUM ============
        buttons[3] = new JButton("Dodaj album");
        buttons[3].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new AddAlbum(db);
            }
        });
        add(buttons[3]);
        // ========== EDIT ALBUM ===========
        buttons[4] = new JButton("Modyfikuj album");
        buttons[4].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new EditAlbum(db);
            }
        });
        add(buttons[4]);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(200,400);
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                ArtistsMenu.super.dispose();
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
