package management.gui;

import containers.BillContainer;
import management.dblogic.MusicPublisherDatabase;

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

public class EditBill extends JFrame {
    private long tmp_id;
    public EditBill(MusicPublisherDatabase db) {
        Vector<BillContainer> vec = db.getBills();
        if(vec.size() == 0) {
            new MessageWindow("Brak rachunków", "Brak rachunków, które można zmodyfikować, dodaj jakieś");
            EditBill.super.dispose();
        }
        JList<BillContainer> jl = new JList<>(vec);
        JScrollPane sp = new JScrollPane(jl);
        JLabel l = new JLabel("Data rozp.(yyyy-MM-dd)");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dff = new DateFormatter(df);
        JFormattedTextField ftf = new JFormattedTextField(dff);
        JLabel l2 = new JLabel("Data zak.(yyyy-MM-dd)");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dff2 = new DateFormatter(df2);
        JFormattedTextField ftf2 = new JFormattedTextField(dff2);
        JLabel l3 = new JLabel("Kwota");
        NumberFormat nf = new DecimalFormat("#000.00");
        NumberFormatter nft = new NumberFormatter(nf);
        nft.setValueClass(Double.class);
        JFormattedTextField ftf3 = new JFormattedTextField(nft);
        JButton jb = new JButton("Modyfikuj");
        jl.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    tmp_id = jl.getSelectedValue().getId_rachunku();
                    ftf.setValue(jl.getSelectedValue().getData_rozp());
                    ftf2.setValue(jl.getSelectedValue().getData_zak());
                    ftf3.setValue(jl.getSelectedValue().getKwota());
                    ftf.setEditable(!jl.getSelectedValue().getStarted());
                }
            }
        });
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    db.editBill(tmp_id, new Date(((java.util.Date)ftf.getValue()).getTime()), new Date(((java.util.Date)ftf2.getValue()).getTime()), (Double)ftf3.getValue());
                    new MessageWindow("Zmodyfikowano", "Pomyślnie zmodyfikowano rachunek");
                } else {
                    new MessageWindow("Wybierz rachunek", "Wybierz rachunek, który chcesz zmodyfikować");
                }
            }
        });
        sp.setBounds(10, 10, 250,500);
        l.setBounds(10, 510, 100, 40);
        ftf.setBounds(120, 510, 120, 40);
        l2.setBounds(10, 560, 100, 40);
        ftf2.setBounds(120, 560, 120, 40);
        l3.setBounds(10, 610, 100, 40);
        ftf3.setBounds(120, 610, 120, 40);
        jb.setBounds(190, 660, 100, 40);
        add(l);
        add(ftf);
        add(l2);
        add(ftf2);
        add(l3);
        add(ftf3);
        add(sp);
        add(jb);
        setLayout(null);
        setSize(300,750);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
