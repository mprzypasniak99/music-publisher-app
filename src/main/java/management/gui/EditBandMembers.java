package management.gui;

import containers.AuthorContainer;
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

public class EditBandMembers extends JFrame {
    private final Vector<AuthorContainer> vac_band;
    private final Vector<AuthorContainer> vac_art;
    private Vector<AuthorContainer> vac_band_art;
    private DefaultListModel<AuthorContainer> dfla = new DefaultListModel<>();
    private JList<AuthorContainer>  jl2 = new JList<>(dfla);
    private JScrollPane sp2 = new JScrollPane(jl2);
    private long tmp_id_zespolu;

    public EditBandMembers(MusicPublisherDatabase db) {
        vac_band = db.getAuthors(true);
        if(vac_band.size() == 0) {
            new MessageWindow("Brak zespołów", "Brak zespółów, które można edytować, dodaj jakieś");
            EditBandMembers.super.dispose();
        }
        vac_art = db.getAuthors(false);
        if(vac_art.size() == 0) {
            new MessageWindow("Brak artystów", "Nie posiadasz żadnych artystów, których mógłbyś dodać.");
            EditBandMembers.super.dispose();
        }
        JList<AuthorContainer> jl = new JList<>(vac_band);
        JScrollPane sp = new JScrollPane(jl);
        JButton jb = new JButton("Dodaj");
        JButton jb2 = new JButton("Usuń");
        JList<AuthorContainer> jl3 = new JList<>(vac_art);
        JScrollPane sp3 = new JScrollPane(jl3);
        JButton jb3 = new JButton("Zatwierdź");

        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl3.getSelectedIndex() != -1) {
                    db.addBandMember(tmp_id_zespolu, jl3.getSelectedValue().getId());
                    dfla.addElement(jl3.getSelectedValue());
                } else {
                    new MessageWindow("Wybierz artystę!", "Wybierz artystę, którego chciałbyś dodać do zespołu");
                }
            }
        });

        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl2.getSelectedIndex() != -1) {
                    db.deleteBandMember(tmp_id_zespolu, jl2.getSelectedValue().getId());
                    dfla.removeElement(jl2.getSelectedValue());
                } else {
                    new MessageWindow("Nie wybrano artysty", "Wybierz artystę, którego chcesz usunąć z zespołu");
                }
            }
        });

        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.commit();
                new MessageWindow("Zatwierdzone", "Pomyślnie dokonano zmian w członkach zespołu");
                EditBandMembers.super.dispose();
            }
        });

        jl.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    tmp_id_zespolu = jl.getSelectedValue().getId();
                    vac_band_art = db.getBandMembers(tmp_id_zespolu);
                    dfla.clear();
                    for(AuthorContainer s : vac_band_art) {
                        dfla.addElement(s);
                    }
                    jl2.setModel(dfla);
                    sp2.add(jl2);
                }
            }
        });

        sp.setBounds(15,10,250,500);
        sp2.setBounds(280,10,250,500);
        sp3.setBounds(545, 10, 250, 500);
        jb.setBounds(620,520,100,40);
        jb2.setBounds(355, 520,100,40);
        jb3.setBounds(695,570,100,40);
        add(sp);
        add(sp2);
        add(sp3);
        add(jb);
        add(jb2);
        add(jb3);
        setLayout(null);
        setSize(810, 620);
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                db.rollback();
                EditBandMembers.super.dispose();
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
