package management.gui;

import containers.AuthorContainer;
import containers.SongContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Vector;

public class AddAlbum extends JFrame{
    private final Vector<SongContainer> vsc = new Vector<>();
    public AddAlbum(MusicPublisherDatabase db) {
        Vector<AuthorContainer>  vac = new Vector<>();
        vac.addAll(db.getAuthors(false));
        vac.addAll(db.getAuthors(true));
        if(vac.size() == 0) {
            new MessageWindow("Brak artystów", "Brak artystów, dla których można dodać album.");
            AddAlbum.super.dispose();
        }
        JLabel l1 = new JLabel("Nazwa:");
        JTextField tf1 = new JTextField();
        JLabel l2 = new JLabel("Gatunek:");
        JTextField tf2 = new JTextField();
        JLabel l3 = new JLabel("Nazwa utworu:");
        JTextField tf3 = new JTextField();
        JLabel l4 = new JLabel("Długość:");
        JSpinner sm1 = new JSpinner(new SpinnerNumberModel(3, 0, 59, 1));
        JLabel l5 = new JLabel(":");
        JSpinner sm2 = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        JButton jb = new JButton("Dodaj");
        JButton jb2 = new JButton("Dodaj utwór");
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vsc.add(new SongContainer(tf3.getText(), (Integer)sm1.getValue(), (Integer)sm2.getValue()));
            }
        });
        JList<AuthorContainer> jl = new JList<>(vac);
        JScrollPane sp = new JScrollPane(jl);
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    if(db.addAlbum(jl.getSelectedValue().getId(), tf1.getText(), tf2.getText(), vsc)) {
                        new MessageWindow("Dodano!", "Dodałeś nowy album");
                        AddAlbum.super.dispose();
                    }
                }
                else {
                    new MessageWindow("Wybierz artystę", "Wybierz artystę, dla którego chcesz dodać album.");
                }
            }
        });
        l1.setBounds(15, 10, 80, 30);
        l2.setBounds(15, 40, 80, 30);
        l3.setBounds(15, 70, 80, 30);
        l4.setBounds(15, 100, 80, 30);
        l5.setBounds(95, 100, 10, 30);
        tf1.setBounds(95, 10, 150, 30);
        tf2.setBounds(95, 40, 150, 30);
        tf3.setBounds(95, 70, 150, 30);
        sm1.setBounds(95, 100, 50, 30);
        sm2.setBounds(145, 100, 50, 30);
        sp.setBounds(15, 170, 250, 470);
        jb.setBounds(160, 660, 100, 40);
        jb2.setBounds(140, 130, 140, 30);
        add(l1);
        add(l2);
        add(tf1);
        add(tf2);
        add(jb);
        add(l3);
        add(tf3);
        add(l4);
        add(sm1);
        add(l5);
        add(sm2);
        add(jb2);
        add(sp);
        setSize(300, 760);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }
}
