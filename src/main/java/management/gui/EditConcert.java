package management.gui;

import management.dblogic.MusicPublisherDatabase;
import containers.ConcertContainer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class EditConcert extends JFrame {
    private long tmp_id;
    private Vector<ConcertContainer> vcc;
    public EditConcert(MusicPublisherDatabase db) {
        vcc = db.getConcerts();
        if(vcc.size() == 0) {
            new MessageWindow("Brak koncertów", "Brak koncertów, które można zmodyfikować, dodaj jakieś");
            EditConcert.super.dispose();
        }
        JList<ConcertContainer> jl = new JList<>(vcc);
        JScrollPane sp = new JScrollPane(jl);
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
                if(jl.getSelectedIndex() != -1) {
                    if(db.editConcert(tmp_id, new Date(((java.util.Date) ftf.getValue()).getTime()), tf.getText(), tf2.getText(), tf3.getText(), (Double) ftf2.getValue())) {
                        EditConcert.super.dispose();
                    }
                } else {
                    new MessageWindow("Wybierz koncert", "Wybierz koncert, który chcesz zmodyfikować");
                }
            }
        });
        jl.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    tmp_id = jl.getSelectedValue().getId();
                    ftf.setValue(jl.getSelectedValue().getData());
                    tf.setText(jl.getSelectedValue().getKraj());
                    tf2.setText(jl.getSelectedValue().getMiasto());
                    tf3.setText(jl.getSelectedValue().getUlica());
                    ftf2.setValue(jl.getSelectedValue().getZysk());
                }
            }
        });
        sp.setBounds(10, 10, 250, 500);
        l.setBounds(10, 510, 100, 30);
        ftf.setBounds(120, 510, 100, 30);
        l2.setBounds(10, 540, 100, 30);
        tf.setBounds(120, 540, 100, 30);
        l3.setBounds(10, 570, 100, 30);
        tf2.setBounds(120, 570, 100, 30);
        l4.setBounds(10, 600, 100, 30);
        tf3.setBounds(120, 600, 100, 30);
        l5.setBounds(10, 630, 100, 30);
        ftf2.setBounds(120, 630, 100, 30);
        jb.setBounds(120, 660, 100, 40);
        add(sp);
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
        setSize(290, 740);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
