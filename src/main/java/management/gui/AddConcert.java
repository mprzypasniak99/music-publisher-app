package management.gui;

import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class AddConcert extends JFrame {
    public AddConcert(MusicPublisherDatabase db) {
        JLabel l = new JLabel("Data (yyyy-MM-dd)");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dff = new DateFormatter(df);
        JFormattedTextField ftf = new JFormattedTextField(dff);
        JLabel l2 = new JLabel("Kraj:");
        JTextField tf = new JTextField();
        JLabel l3 = new JLabel("Miasto:");
        JTextField tf2 = new JTextField();
        JLabel l4 = new JLabel("Ulica:");
        JTextField tf3 = new JTextField();
        JLabel l5 = new JLabel("Zysk");
        NumberFormat nf = new DecimalFormat("#000.00");
        NumberFormatter nft = new NumberFormatter(nf);
        nft.setValueClass(Double.class);
        JFormattedTextField ftf2 = new JFormattedTextField(nft);
        JButton jb = new JButton("Dodaj");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ftf2.getValue() != null) {
                    db.addConcert(new Date(((java.util.Date) ftf.getValue()).getTime()), tf.getText(), tf2.getText(), tf3.getText(), (Double) ftf2.getValue());
                } else {
                    db.addConcert(new Date(((java.util.Date) ftf.getValue()).getTime()), tf.getText(), tf2.getText(), tf3.getText());
                }
                AddConcert.super.dispose();
            }
        });
        l.setBounds(15, 10, 100, 30);
        ftf.setBounds(120, 10, 100, 30);
        l2.setBounds(15, 40, 100, 30);
        tf.setBounds(120, 40, 100,30);
        l3.setBounds(15, 70, 100, 30);
        tf2.setBounds(120, 70, 100,30);
        l4.setBounds(15, 100, 100, 30);
        tf3.setBounds(120, 100, 100,30);
        l5.setBounds(15, 130, 100, 30);
        ftf2.setBounds(120, 130, 100, 30);
        jb.setBounds(120, 160,100,40);
        add(l);
        add(l2);
        add(l3);
        add(l4);
        add(l5);
        add(ftf);
        add(ftf2);
        add(tf);
        add(tf2);
        add(tf3);
        add(jb);
        setLayout(null);
        setSize(240,220);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
