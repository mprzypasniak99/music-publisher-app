package management.gui;

import containers.AlbumContainer;
import containers.StorageMediumContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;

public class AddAlbumSell extends JFrame {
    private DefaultListModel<StorageMediumContainer> dflms = new DefaultListModel<>();
    private JList<StorageMediumContainer> jl2 = new JList<>(dflms);
    private JScrollPane sp2 = new JScrollPane(jl2);
    public AddAlbumSell(MusicPublisherDatabase db) {
        Vector<AlbumContainer> vac = db.getAlbums();
        if(vac.size() == 0) {
            new MessageWindow("Brak albumów", "Brak albumów, dla których można dodać sprzedaż");
            AddAlbumSell.super.dispose();
        }
        Vector<StorageMediumContainer> vsmc = db.getStorageMedia();
        for(StorageMediumContainer smc : vsmc) {
            dflms.addElement(smc);
        }
        //jl2.setModel(dflms);
        //sp2.add(jl2);
        JList<AlbumContainer> jl = new JList<>(vac);
        JScrollPane sp = new JScrollPane(jl);
        JLabel l = new JLabel("Cena za sztukę:");
        NumberFormat nf = new DecimalFormat("#00.00");
        NumberFormatter nft = new NumberFormatter(nf);
        nft.setValueClass(Double.class);
        JFormattedTextField ftf = new JFormattedTextField(nft);
        JLabel l2 = new JLabel("Ilość sprzedanych");
        NumberFormat nf2 = new DecimalFormat("#0");
        NumberFormatter nft2 = new NumberFormatter(nf2);
        nft2.setValueClass(Long.class);
        JFormattedTextField ftf2 = new JFormattedTextField(nft2);
        JLabel l3 = new JLabel("Typ");
        JTextField tf = new JTextField();
        JLabel l4 = new JLabel("Cena za nośnik:");
        NumberFormat nf3 = new DecimalFormat("#00.00");
        NumberFormatter nft3 = new NumberFormatter(nf3);
        nft3.setValueClass(Double.class);
        JFormattedTextField ftf3 = new JFormattedTextField(nft3);
        JButton jb = new JButton("Dodaj nośnik");
        JButton jb2 = new JButton("Dodaj sprzedaż");
        JButton jb3 = new JButton("Zmień cenę nośnika");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ftf3.getValue() != null) {
                    StorageMediumContainer tmp = new StorageMediumContainer(tf.getText(), (Double) ftf3.getValue());
                    db.addStorageMedium(tf.getText(), (Double) ftf3.getValue());
                    dflms.addElement(tmp);
                    //jl2.setModel(dflms);
                    jl2.setSelectedValue(tmp, true);
                } else {
                    new MessageWindow("Brak wartości", "Dodaj wszystkie wymagane wartości przed dodaniem nośnika");
                }
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl.getSelectedIndex() != -1 && jl2.getSelectedIndex() != -1) {
                    db.addAlbumSell(jl.getSelectedValue().getId_albumu(), (Double) ftf.getValue(), (Long) ftf2.getValue(), jl2.getSelectedValue().getTyp());
                    new MessageWindow("Dodano", "Dodano sprzedaż dla albumu");
                    AddAlbumSell.super.dispose();
                } else {
                    new MessageWindow("Wybierz album i typ nośnika", "Wybierz album i typ nośnika zanim spróbujesz dodać sprzedaż");
                }
            }
        });
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl2.getSelectedIndex() != -1) {
                    db.editPrice(jl2.getSelectedValue().getTyp(), jl2.getSelectedValue().getCena());
                    dflms.setElementAt(new StorageMediumContainer(jl2.getSelectedValue().getTyp(), jl2.getSelectedValue().getCena()), jl.getSelectedIndex());
                } else {
                    new MessageWindow("Wybierz nośnik", "Wybierz nośnik, którego cenę chcesz zmodyfikować");
                }
            }
        });
        jl2.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(jl2.getSelectedIndex() != -1) {
                    tf.setText(jl2.getSelectedValue().getTyp());
                    ftf3.setValue(jl2.getSelectedValue().getCena());
                }
            }
        });

        sp.setBounds(10, 10, 250, 500);
        sp2.setBounds(270, 10, 250, 500);
        l.setBounds(10, 510, 150, 30);
        ftf.setBounds(160, 510, 90, 30);
        l2.setBounds(10, 540, 150, 30);
        ftf2.setBounds(160, 540, 90, 30);
        l3.setBounds(260, 510, 50, 30);
        tf.setBounds(310, 510, 140, 30);
        l4.setBounds(260, 540, 150, 30);
        ftf3.setBounds(410, 540, 90, 30);
        jb.setBounds(260, 570, 125, 40);
        jb3.setBounds(385, 570, 125, 40);
        jb2.setBounds(410, 620, 100, 40);

        add(sp);
        add(sp2);
        add(l);
        add(l2);
        add(l3);
        add(l4);
        add(ftf);
        add(tf);
        add(ftf2);
        add(ftf3);
        add(jb);
        add(jb2);
        add(jb3);

        setLayout(null);
        setSize(540, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                db.rollback();
                AddAlbumSell.super.dispose();
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
