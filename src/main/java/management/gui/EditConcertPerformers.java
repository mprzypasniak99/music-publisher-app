package management.gui;

import containers.AuthorContainer;
import containers.ConcertContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

public class EditConcertPerformers extends JFrame {
    private final Vector<ConcertContainer> vcc;
    private final Vector<AuthorContainer> vac = new Vector<>();
    private Vector<AuthorContainer> vac_con;
    private DefaultListModel<AuthorContainer> dfla = new DefaultListModel<>();
    private JList<AuthorContainer>  jl2 = new JList<>(dfla);
    private JScrollPane sp2 = new JScrollPane(jl2);
    private long tmp_id_koncertu;

    public EditConcertPerformers(MusicPublisherDatabase db) {
        vcc = db.getConcerts();
        if(vcc.size() == 0) {
            new MessageWindow("Brak koncertów", "Brak koncertów, którym można przypisać artystów, zaplanuj jakieś.");
            EditConcertPerformers.super.dispose();
        }
        vac.addAll(db.getAuthors(false));
        vac.addAll(db.getAuthors(true));
        if(vac.size() == 0) {
            new MessageWindow("Brak artystów", "Nie posiadasz żadnych artystów, których mógłbyś dodać.");
            EditConcertPerformers.super.dispose();
        }
        JList<ConcertContainer> jl = new JList<>(vcc);
        JScrollPane sp = new JScrollPane(jl);
        JButton jb = new JButton("Dodaj");
        JButton jb2 = new JButton("Usuń");
        JList<AuthorContainer> jl3 = new JList<>(vac);
        JScrollPane sp3 = new JScrollPane(jl3);
        JButton jb3 = new JButton("Zatwierdź");

        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl3.getSelectedIndex() != -1) {
                    db.addConPerformer(jl3.getSelectedValue().getId(), tmp_id_koncertu);
                    dfla.addElement(jl3.getSelectedValue());
                } else {
                    new MessageWindow("Wybierz artystę!", "Wybierz artystę, którego chciałbyś dodać do koncertu");
                }
            }
        });

        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl2.getSelectedIndex() != -1) {
                    db.deleteConPerformer(jl3.getSelectedValue().getId(), tmp_id_koncertu);
                    dfla.removeElement(jl2.getSelectedValue());
                } else {
                    new MessageWindow("Nie wybrano artysty", "Wybierz artystę, którego chcesz usunąć z koncertu");
                }
            }
        });

        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.commit();
                new MessageWindow("Zatwierdzone", "Pomyślnie dokonano zmian w członkach zespołu");
                EditConcertPerformers.super.dispose();
            }
        });

        jl.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    tmp_id_koncertu = jl.getSelectedValue().getId();
                    vac_con = db.getConPerformers(tmp_id_koncertu);
                    dfla.clear();
                    for(AuthorContainer s : vac_con) {
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
        setSize(820, 660);
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                db.rollback();
                EditConcertPerformers.super.dispose();
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
