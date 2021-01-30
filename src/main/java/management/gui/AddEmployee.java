package management.gui;

import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEmployee extends JFrame {
    private final String[] etaty = {"MENEDŻER", "TECHNICZNY", "INNY"};
    public AddEmployee(MusicPublisherDatabase db) {
        JLabel l1 = new JLabel("Imię:");
        JTextField tf1 = new JTextField();
        JLabel l2 = new JLabel("Nazwisko:");
        JTextField tf2 = new JTextField();
        JLabel l3 = new JLabel("Etat:");
        JTextField tf3 = new JTextField();
        JButton jb = new JButton("Dodaj");
        JComboBox<String> cb = new JComboBox<>(etaty);
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cb.getSelectedItem() == "INNY") {
                    add(l3);
                    add(tf3);
                }
                else {
                    remove(l3);
                    remove(tf3);
                }
                repaint();
            }
        });
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cb.getSelectedIndex() != -1) {
                    String etat = (String) cb.getSelectedItem();
                    if (etat.equals("INNY")) {
                        db.addEmployee(tf1.getText(), tf2.getText(), tf3.getText());
                        new MessageWindow("Dodano!", "Dodano nowego pracownika");
                        AddEmployee.super.dispose();
                    }
                    else {
                        db.addEmployee(tf1.getText(), tf2.getText(), etat);
                        new MessageWindow("Dodano!", "Dodano nowego pracownika");
                        AddEmployee.super.dispose();
                    }
                } else {
                    new MessageWindow("Brak etatu!", "Wybierz etat dla tworzonego pracownika");
                }
            }
        });
        l1.setBounds(15, 10, 50, 30);
        l2.setBounds(15, 40, 50, 30);
        l3.setBounds(15, 100, 50, 30);
        tf1.setBounds(65, 10, 150, 30);
        tf2.setBounds(65, 40, 150, 30);
        tf3.setBounds(65, 100, 150, 30);
        jb.setBounds(140, 450, 100, 40);
        cb.setBounds(15, 70, 150, 30);
        add(l1);
        add(l2);
        add(tf1);
        add(tf2);
        add(jb);
        add(cb);
        setLayout(null);
        setSize(280, 530);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
