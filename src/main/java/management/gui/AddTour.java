package management.gui;

import containers.ConcertContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class AddTour extends JFrame {
    private Vector<Long> vcc_add = new Vector<>();
    public AddTour(MusicPublisherDatabase db) {
        Vector<ConcertContainer> vcc = db.getConcerts();
        JList<ConcertContainer> jl = new JList<>(vcc);
        JScrollPane sp = new JScrollPane(jl);
        DefaultListModel<ConcertContainer> lm = new DefaultListModel<>();
        JList<ConcertContainer> jl2 = new JList<>(lm);
        JScrollPane sp2 = new JScrollPane(jl2);
        JLabel l = new JLabel("Nazwa trasy:");
        JTextField tf = new JTextField();
        JLabel l2 = new JLabel("Data rozp. (yyyy-MM-dd):");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dff = new DateFormatter(df);
        JFormattedTextField ftf = new JFormattedTextField(dff);
        JLabel l3 = new JLabel("Data zak. (yyyy-MM-dd):");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dff2 = new DateFormatter(df2);
        JFormattedTextField ftf2 = new JFormattedTextField(dff2);
        JButton jb = new JButton("Dodaj");
        JButton jb2 = new JButton("Dodaj koncert");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(db.addTour(tf.getText(), new Date(((java.util.Date) ftf.getValue()).getTime()), new Date(((java.util.Date) ftf2.getValue()).getTime()), vcc_add)) {
                    new MessageWindow("Dodano", "Dodałeś nową trasę");
                    AddTour.super.dispose();
                }
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    vcc_add.add(jl.getSelectedValue().getId());
                    lm.addElement(jl.getSelectedValue());
                    jl2.setModel(lm);
                    sp2.add(jl2);
                } else {
                    new MessageWindow("Wybierz koncert", "Wybierz koncert, który chciałbyś dodać");
                }
            }
        });
        l.setBounds(10, 10, 150, 30);
        tf.setBounds(160, 10, 150, 30);
        l2.setBounds(10, 40, 150, 30);
        ftf.setBounds(160, 40, 150, 30);
        l3.setBounds(10, 70, 150, 30);
        ftf2.setBounds(160, 70, 150, 30);
        sp2.setBounds(320, 10, 250, 500);
        sp.setBounds(570, 10, 250, 500);
        jb2.setBounds(570, 510, 150, 40);
        jb.setBounds(720, 560, 100, 40);
        add(l);
        add(l2);
        add(l3);
        add(tf);
        add(ftf);
        add(ftf2);
        add(sp);
        add(sp2);
        add(jb);
        add(jb2);
        setLayout(null);
        setSize(850, 640);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
