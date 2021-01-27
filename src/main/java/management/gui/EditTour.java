package management.gui;

import containers.ConcertContainer;
import containers.TourContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DateFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class EditTour extends JFrame {
    DefaultListModel<ConcertContainer> dflc = new DefaultListModel<>();
    JList<ConcertContainer> jl3 = new JList<>(dflc);
    JScrollPane sp3 = new JScrollPane(jl3);
    public EditTour(MusicPublisherDatabase db) {
        Vector<TourContainer> vtc = db.getTours();
        if(vtc.size() == 0) {
            new MessageWindow("Brak tras", "Brak tras, do modyfikacji, dodaj jakieś trasy");
            EditTour.super.dispose();
        }
        Vector<ConcertContainer> vcc = db.getConcertsNotInTour();
        JList<TourContainer> jl = new JList<>(vtc);
        JScrollPane sp = new JScrollPane(jl);
        JList<ConcertContainer> jl2 = new JList<>(vcc);
        JScrollPane sp2 = new JScrollPane(jl2);
        JLabel l = new JLabel("Nazwa trasy:");
        JTextField tf = new JTextField();
        JLabel l2 = new JLabel("Data rozpoczęcia (yyyy-MM-dd):");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dff = new DateFormatter(df);
        JFormattedTextField ftf = new JFormattedTextField(dff);
        JLabel l3 = new JLabel("Data zakończenia (yyyy-MM-dd):");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dff2 = new DateFormatter(df2);
        JFormattedTextField ftf2 = new JFormattedTextField(dff2);
        JButton jb = new JButton("Modyfikuj");
        JButton jb2 = new JButton("Dodaj koncert");
        JButton jb3 = new JButton("Usuń koncert");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    db.editTour(jl.getSelectedValue().getId_trasy(), jl.getSelectedValue().getNazwa(), jl.getSelectedValue().getData_rozp(), jl.getSelectedValue().getData_zak());
                    new MessageWindow("Zrobione", "Pomyślnie zmodyfikowano trasę");
                    EditTour.super.dispose();
                }
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl.getSelectedIndex() != -1 && jl2.getSelectedIndex() != -1) {
                    dflc.addElement(jl2.getSelectedValue());
                    db.addConToTour(jl.getSelectedValue().getId_trasy(), jl2.getSelectedValue().getId());
                } else {
                    new MessageWindow("Wybierz trasę i koncert", "Nie wybrano trasy lub koncertu");
                }
            }
        });
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl.getSelectedIndex() != -1 && jl3.getSelectedIndex() != -1) {
                    dflc.removeElement(jl3.getSelectedValue());
                    db.deleteConFromTour(jl2.getSelectedValue().getId());
                } else {
                    new MessageWindow("Wybierz trasę i koncert", "Nie wybrano trasy lub koncertu");
                }
            }
        });
        jl.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    Vector<ConcertContainer> vcc_tmp = db.getTourConcerts(jl.getSelectedValue().getId_trasy());
                    dflc.clear();
                    for(ConcertContainer cc : vcc_tmp) {
                        dflc.addElement(cc);
                    }
                    jl3.setModel(dflc);
                    sp3.add(jl3);
                    tf.setText(jl.getSelectedValue().getNazwa());
                    ftf.setValue(jl.getSelectedValue().getData_rozp());
                    ftf2.setValue(jl.getSelectedValue().getData_zak());
                    ftf.setEditable(!jl.getSelectedValue().getStarted());
                }
                db.rollback();
            }
        });
        sp.setBounds(10, 10, 250, 500);
        sp3.setBounds(260, 10, 250, 500);
        sp2.setBounds(510, 10, 250, 500);
        l.setBounds(10, 510, 150, 30);
        tf.setBounds(160, 510, 150, 30);
        l2.setBounds(10,540,150,30);
        ftf.setBounds(160,540,150,30);
        l3.setBounds(10,570,150,30);
        ftf2.setBounds(160,570,150,30);
        jb2.setBounds(560, 510,150,40);
        jb3.setBounds(310, 510, 150, 40);
        jb.setBounds(610, 610, 100, 40);
        add(sp);
        add(sp2);
        add(sp3);
        add(l);
        add(tf);
        add(l2);
        add(ftf);
        add(l3);
        add(ftf2);
        add(jb2);
        add(jb3);
        add(jb);
        setLayout(null);
        setSize(740, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                db.rollback();
                EditTour.super.dispose();
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
