package management.gui;

import containers.AlbumContainer;
import containers.SongContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

public class EditAlbum extends JFrame {
    private final Vector<AlbumContainer> vac;
    private Vector<SongContainer> vsc;
    private DefaultListModel<SongContainer> dfls = new DefaultListModel<>();
    private JList<SongContainer>  jl2 = new JList<>(dfls);
    private JScrollPane sp2 = new JScrollPane(jl2);
    long tmp_id_albumu;
    public EditAlbum(MusicPublisherDatabase db) {
        vac = db.getAlbums();
        if(vac.size() == 0) {
            new MessageWindow("Brak albumów", "Nie istnieje żaden album do modyfikacji, utwórz jakiś");
            EditAlbum.super.dispose();
        }
        JList<AlbumContainer> jl = new JList<>(vac);
        JScrollPane sp = new JScrollPane(jl);
        JLabel l1 = new JLabel("Nazwa:");
        JTextField tf1 = new JTextField();
        JLabel l2 = new JLabel("Gatunek:");
        JTextField tf2 = new JTextField();
        JButton jb = new JButton("Modyfikuj");
        JButton jb2 = new JButton("Dodaj");
        JButton jb3 = new JButton("Usuń");
        JLabel l3 = new JLabel("Nazwa utworu:");
        JTextField tf3 = new JTextField();
        JLabel l4 = new JLabel("Długość:");
        JSpinner sm1 = new JSpinner(new SpinnerNumberModel(3, 0, 59, 1));
        JLabel l5 = new JLabel(":");
        JSpinner sm2 = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl.getSelectedIndex() == -1) {
                    new MessageWindow("Błąd", "Nie wybrano żadnego albumu");
                } else {
                    if(db.editAlbum(tmp_id_albumu, tf1.getText(), tf2.getText())) {
                        new MessageWindow("Zrobione!", "Zmodyfikowałeś album");
                        EditAlbum.super.dispose();
                    }
                }
            }
        });
        jl.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    tf1.setText(jl.getSelectedValue().getNazwa());
                    tf2.setText(jl.getSelectedValue().getGatunek());
                    tmp_id_albumu = jl.getSelectedValue().getId_albumu();
                    vsc = db.getSongs(tmp_id_albumu);
                    dfls.clear();
                    for(SongContainer s : vsc) {
                        dfls.addElement(s);
                    }
                    jl2.setModel(dfls);
                    sp2.add(jl2);
                    db.rollback();
                }
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(db.addSong(tmp_id_albumu, new SongContainer(tf3.getText(), (Integer)sm1.getValue(), (Integer)sm2.getValue()))) {
                    vsc.add(new SongContainer(tf3.getText(), (Integer) sm1.getValue(), (Integer) sm2.getValue()));
                    jl2.setListData(vsc);
                }
            }
        });
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl2.getSelectedIndex() != -1) {
                    if(db.deleteSong(tmp_id_albumu, jl2.getSelectedValue().getNazwa())) {
                        vsc.remove(jl2.getSelectedValue());
                        jl2.setListData(vsc);
                    }
                } else {
                    new MessageWindow("Nie wybrano utworu", "Wybierz utwór, który chciałbyś usunąć z listy utworów");
                }
            }
        });
        sp.setBounds(15, 10, 250, 500);
        l1.setBounds(15, 510, 80, 30);
        l2.setBounds(15, 540, 80, 30);
        l3.setBounds(290, 510, 120, 30);
        l4.setBounds(290, 540, 50, 30);
        l5.setBounds(400, 540, 10, 30);
        tf1.setBounds(100, 510, 150, 30);
        tf2.setBounds(100, 540, 150, 30);
        tf3.setBounds(420, 510, 150, 30);
        sm1.setBounds(350, 540, 50, 30);
        sm2.setBounds(410, 540, 50, 30);
        sp2.setBounds(290, 10, 250, 500);
        jb.setBounds(480, 630, 100, 40);
        jb2.setBounds(290, 580, 100, 40);
        jb3.setBounds(480, 580, 100, 40);
        add(l1);
        add(l2);
        add(l3);
        add(l4);
        add(l5);
        add(tf1);
        add(tf2);
        add(tf3);
        add(jb);
        add(jb2);
        add(jb3);
        add(sm1);
        add(sm2);
        add(sp);
        add(sp2);
        setSize(680, 780);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                db.rollback();
                EditAlbum.super.dispose();
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
