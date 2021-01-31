package management.gui;

import containers.EmployeeContainer;
import containers.SessionContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

public class EditSessionTechnicians extends JFrame {
    private final Vector<SessionContainer> vsc;
    private final Vector<EmployeeContainer> vec;
    private Vector<EmployeeContainer> vec_con;
    private DefaultListModel<EmployeeContainer> dfla = new DefaultListModel<>();
    private JList<EmployeeContainer>  jl2 = new JList<>(dfla);
    private JScrollPane sp2 = new JScrollPane(jl2);
    public EditSessionTechnicians(MusicPublisherDatabase db) {
        vsc = db.getSessions();
        if (vsc.size() == 0) {
            new MessageWindow("Brak sesji", "Brak sesji, którym można przypisać obsługę, zaplanuj jakieś.");
            EditSessionTechnicians.super.dispose();
        }
        vec = db.getTechEmployees();
        if (vec.size() == 0) {
            new MessageWindow("Brak pracowników", "Nie posiadasz żadnych pracowników, których mógłbyś dodać.");
            EditSessionTechnicians.super.dispose();
        }
        JList<SessionContainer> jl = new JList<>(vsc);
        JScrollPane sp = new JScrollPane(jl);
        JButton jb = new JButton("Dodaj");
        JButton jb2 = new JButton("Usuń");
        JList<EmployeeContainer> jl3 = new JList<>(vec);
        JScrollPane sp3 = new JScrollPane(jl3);
        JButton jb3 = new JButton("Zatwierdź");

        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jl3.getSelectedIndex() != -1 && jl.getSelectedIndex() != -1) {
                    if(db.addSesTech(jl3.getSelectedValue().getId(), jl.getSelectedValue().getData_sesji(), jl.getSelectedValue().getId_autora())) {
                        dfla.addElement(jl3.getSelectedValue());
                    }
                } else {
                    new MessageWindow("Wybierz pola", "Wybierz koncert i artystę, którego chciałbyś dodać do tego koncertu");
                }
            }
        });

        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jl2.getSelectedIndex() != -1) {
                    if(db.deleteSesTech(jl3.getSelectedValue().getId(), jl.getSelectedValue().getData_sesji(), jl.getSelectedValue().getId_autora())) {
                        dfla.removeElement(jl2.getSelectedValue());
                    }
                } else {
                    new MessageWindow("Nie wybrano pracownika", "Wybierz pracownika, którego chcesz usunąć z koncertu");
                }
            }
        });

        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(db.commit()) {
                    new MessageWindow("Zatwierdzone", "Pomyślnie dokonano zmian w członkach zespołu");
                    EditSessionTechnicians.super.dispose();
                }
            }
        });

        jl.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jl.getSelectedIndex() != -1) {
                    vec_con = db.getSesTech(jl.getSelectedValue().getData_sesji(), jl.getSelectedValue().getId_autora());
                    dfla.clear();
                    for (EmployeeContainer s : vec_con) {
                        dfla.addElement(s);
                    }
                    jl2.setModel(dfla);
                    sp2.add(jl2);
                }
            }
        });

        sp.setBounds(15, 10, 250, 500);
        sp2.setBounds(280, 10, 250, 500);
        sp3.setBounds(545, 10, 250, 500);
        jb.setBounds(620, 520, 100, 40);
        jb2.setBounds(355, 520, 100, 40);
        jb3.setBounds(695, 570, 100, 40);
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
                EditSessionTechnicians.super.dispose();
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
