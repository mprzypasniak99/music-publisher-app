package management.gui;

import containers.EmployeeContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Vector;

public class AddBill extends JFrame {
    private long tmp_id;
    public AddBill(MusicPublisherDatabase db) {
        Vector<EmployeeContainer> vec = db.getEmployees();
        if(vec.size() == 0) {
            new MessageWindow("Brak pracowników", "Brak pracowników, dla których można dodać rachunek");
            AddBill.super.dispose();
        }
        JList<EmployeeContainer> jl = new JList<>(vec);
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
        JButton jb = new JButton("Dodaj");
        jl.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    tmp_id = jl.getSelectedValue().getId();
                }
            }
        });
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    db.addBill(jl.getSelectedValue().getId(), new Date(((java.util.Date)ftf.getValue()).getTime()), new Date(((java.util.Date)ftf2.getValue()).getTime()), (Double)ftf3.getValue());
                    new MessageWindow("Dodano", "Pomyślnie dodano nowy rachunek");
                    AddBill.super.dispose();
                } else {
                    new MessageWindow("Wybierz pracownika", "Wybierz pracownika, dla którego chcesz dodać rachunek");
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
