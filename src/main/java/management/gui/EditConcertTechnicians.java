package management.gui;

import containers.ConcertContainer;
import containers.EmployeeContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

public class EditConcertTechnicians extends JFrame {
    private final Vector<ConcertContainer> vcc;
    private final Vector<EmployeeContainer> vec;
    private Vector<EmployeeContainer> vec_con;
    private DefaultListModel<EmployeeContainer> dfla = new DefaultListModel<>();
    private JList<EmployeeContainer>  jl2 = new JList<>(dfla);
    private JScrollPane sp2 = new JScrollPane(jl2);
    public EditConcertTechnicians(MusicPublisherDatabase db) {
        vcc = db.getConcerts();
        if(vcc.size() == 0) {
            new MessageWindow("Brak koncertów", "Brak koncertów, którym można przypisać obsługę, zaplanuj jakieś.");
            EditConcertTechnicians.super.dispose();
        }
        vec = db.getTechEmployees();
        if(vec.size() == 0) {
            new MessageWindow("Brak pracowników", "Nie posiadasz żadnych pracowników, których mógłbyś dodać.");
            EditConcertTechnicians.super.dispose();
        }
        JList<ConcertContainer> jl = new JList<>(vcc);
        JScrollPane sp = new JScrollPane(jl);
        JButton jb = new JButton("Dodaj");
        JButton jb2 = new JButton("Usuń");
        JList<EmployeeContainer> jl3 = new JList<>(vec);
        JScrollPane sp3 = new JScrollPane(jl3);
        JButton jb3 = new JButton("Zatwierdź");

        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl3.getSelectedIndex() != -1 && jl.getSelectedIndex() != -1) {
                    db.addConTech(jl3.getSelectedValue().getId(), jl.getSelectedValue().getId());
                    dfla.addElement(jl3.getSelectedValue());
                } else {
                    new MessageWindow("Wybierz pola", "Wybierz koncert i artystę, którego chciałbyś dodać do tego koncertu");
                }
            }
        });

        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl2.getSelectedIndex() != -1) {
                    db.deleteConTech(jl3.getSelectedValue().getId(), jl.getSelectedValue().getId());
                    dfla.removeElement(jl2.getSelectedValue());
                } else {
                    new MessageWindow("Nie wybrano pracownika", "Wybierz pracownika, którego chcesz usunąć z koncertu");
                }
            }
        });

        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.commit();
                new MessageWindow("Zatwierdzone", "Pomyślnie dokonano zmian w członkach zespołu");
                EditConcertTechnicians.super.dispose();
            }
        });

        jl.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    vec_con = db.getConTech(jl.getSelectedValue().getId());
                    dfla.clear();
                    for(EmployeeContainer s : vec_con) {
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
                EditConcertTechnicians.super.dispose();
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
