package management.gui;

import containers.AuthorContainer;
import containers.EmployeeContainer;
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

public class AddContract extends JFrame {
    private long tmp_id_me;
    private long tmp_id_au;
    public AddContract(MusicPublisherDatabase db) {
        Vector<EmployeeContainer> vec = db.getManagers();
        if (vec.size() == 0) {
            new MessageWindow("Brak menedżerów", "Brak menedżerów, którzy mogą być częścią kontraktu");
            AddContract.super.dispose();
        }
        Vector<AuthorContainer> vac = new Vector<>();
        vac.addAll(db.getAuthors(false));
        vac.addAll(db.getAuthors(true));
        if (vac.size() == 0) {
            new MessageWindow("Brak autorów", "Brak autorów, dla których można dodać kontrakt, dodaj zespół lub artystów");
            AddContract.super.dispose();
        }
        JList<EmployeeContainer> jl = new JList<>(vec);
        JScrollPane sp = new JScrollPane(jl);
        JLabel l = new JLabel("Data zawarcia (yyyy-MM-dd)");
        JList<AuthorContainer> jl2 = new JList<>(vac);
        JScrollPane sp2 = new JScrollPane(jl2);
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
                if (jl.getSelectedIndex() != -1) {
                    tmp_id_me = jl.getSelectedValue().getId();
                }
            }
        });
        jl2.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jl2.getSelectedIndex() != -1) {
                    tmp_id_au = jl2.getSelectedValue().getId();
                }
            }
        });
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl.getSelectedIndex() != -1 && jl2.getSelectedIndex() != -1) {
                    db.addContract(tmp_id_au, tmp_id_me, new Date(((java.util.Date)ftf.getValue()).getTime()), new Date(((java.util.Date)ftf2.getValue()).getTime()), (Double)ftf3.getValue());
                    new MessageWindow("Dodano kontrakt", "Dodano nowy kontrakt");
                    AddContract.super.dispose();
                } else {
                    new MessageWindow("Błąd", "Nie wybrano autora lub menedżera");
                }
            }
        });
        sp2.setBounds(10, 10, 270,500);
        sp.setBounds(280, 10, 250, 500);
        l.setBounds(10, 510, 120, 40);
        ftf.setBounds(180, 510, 100, 40);
        l2.setBounds(10, 560, 150, 40);
        ftf2.setBounds(180, 560, 100, 40);
        l3.setBounds(10, 610, 100, 40);
        ftf3.setBounds(160, 610, 120, 40);
        jb.setBounds(180, 660, 100, 40);
        add(l);
        add(ftf);
        add(l2);
        add(ftf2);
        add(l3);
        add(ftf3);
        add(sp);
        add(sp2);
        add(jb);
        setLayout(null);
        setSize(550,750);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
