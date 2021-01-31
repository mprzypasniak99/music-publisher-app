package management.gui;

import containers.SellContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;

public class EditAlbumSell extends JFrame {
    public EditAlbumSell(MusicPublisherDatabase db) {
        Vector<SellContainer> vsc = db.getSells();
        JList<SellContainer> jl = new JList<>(vsc);
        JScrollPane sp = new JScrollPane(jl);
        JLabel l = new JLabel("Nazwa albummu");
        JTextField tf = new JTextField();
        tf.setEditable(false);
        JLabel l2 = new JLabel("Cena za sztukę:");
        NumberFormat nf = new DecimalFormat("#00.00");
        NumberFormatter nft = new NumberFormatter(nf);
        nft.setValueClass(Double.class);
        JFormattedTextField ftf = new JFormattedTextField(nft);
        JLabel l3 = new JLabel("Ilość sprzedanych");
        NumberFormat nf2 = new DecimalFormat("#0");
        NumberFormatter nft2 = new NumberFormatter(nf2);
        nft2.setValueClass(Long.class);
        JFormattedTextField ftf2 = new JFormattedTextField(nft2);
        JLabel l4 = new JLabel("Typ");
        JTextField tf2 = new JTextField();
        tf2.setEditable(false);
        JLabel l5 = new JLabel("Cena za nośnik:");
        NumberFormat nf3 = new DecimalFormat("#00.00");
        NumberFormatter nft3 = new NumberFormatter(nf3);
        nft3.setValueClass(Double.class);
        JFormattedTextField ftf3 = new JFormattedTextField(nft3);
        JButton jb = new JButton("Modyfikuj nośnik");
        JButton jb2 = new JButton("Modyfikuj sprzedaż");
        jl.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    tf.setText(jl.getSelectedValue().getNazwa());
                    ftf.setValue(jl.getSelectedValue().getCena_szt());
                    ftf2.setValue(jl.getSelectedValue().getIlosc());
                    tf2.setText(jl.getSelectedValue().getTyp());
                    ftf3.setValue(jl.getSelectedValue().getCena_no());
                }
            }
        });
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    if(db.editPrice(tf2.getText(), (Double) ftf3.getValue())) {
                        db.commit();
                        new MessageWindow("Zmieniono", "Udało Ci się zmienić cenę nośnika");
                    }
                } else {
                    new MessageWindow("Wybierz sprzedaż", "Wybierz sprzedaż na podstawie której zedytujesz cenę nośnika");
                }
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    if(db.editAlbumSell(jl.getSelectedValue().getId_albumu(), (Double) ftf.getValue(), (Long) ftf2.getValue(), tf2.getText())) {
                        new MessageWindow("Zmieniono", "Udało Ci się zmodyfikować sprzedaż");
                    }
                } else {
                    new MessageWindow("Wybierz sprzedaż", "Wybierz sprzedaż, którą chcesz edytować");
                }
            }
        });
        sp.setBounds(10, 10, 250, 500);
        l.setBounds(270, 10, 150, 30);
        tf.setBounds(420, 10, 150, 30);
        l2.setBounds(270, 40, 150, 30);
        ftf.setBounds(420, 40, 150, 30);
        l3.setBounds(270, 70, 150, 30);
        ftf2.setBounds(420, 70, 150, 30);
        l4.setBounds(270, 100, 150, 30);
        tf2.setBounds(420, 100, 150, 30);
        l5.setBounds(270, 130, 150, 30);
        ftf3.setBounds(420, 130, 150, 30);
        jb2.setBounds(420, 210, 150, 40);
        jb.setBounds(420, 170, 150, 40);

        add(sp);
        add(l);
        add(tf);
        add(l2);
        add(ftf);
        add(l3);
        add(ftf2);
        add(l4);
        add(tf2);
        add(l5);
        add(ftf3);
        add(jb);
        add(jb2);

        setLayout(null);
        setSize(590, 550);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
