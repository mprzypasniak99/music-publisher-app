package management.gui;

import containers.EmployeeContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class EditEmployee extends JFrame {
    private final String[] etaty = {"MENEDŻER", "TECHNICZNY", "INNY"};
    private final Vector<EmployeeContainer> vec;
    private long tmp_id;
    private String old_etat;
    public EditEmployee(MusicPublisherDatabase db) {
        vec = db.getEmployees();
        if(vec.size() == 0) {
            new MessageWindow("Brak pracowników", "Nie ma pracowników, których można edytować, dodaj jakichś");
            EditEmployee.super.dispose();
        }
        JList<EmployeeContainer> jl = new JList<>(vec);
        JScrollPane sp = new JScrollPane(jl);
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
                        db.editEmployee(tmp_id, tf1.getText(), tf2.getText(), tf3.getText(), old_etat);
                        new MessageWindow("Zrobione!", "Zmodyfikowano pracownika");
                        EditEmployee.super.dispose();
                    }
                    else {
                        db.editEmployee(tmp_id, tf1.getText(), tf2.getText(), etat, old_etat);
                        new MessageWindow("Zrobione!", "Zmodyfikowano pracownika");
                        EditEmployee.super.dispose();
                    }
                } else {
                    new MessageWindow("Brak etatu!", "Wybierz etat dla tworzonego pracownika");
                }
            }
        });
        jl.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    tf1.setText(jl.getSelectedValue().getImie());
                    tf2.setText(jl.getSelectedValue().getNazwisko());
                    old_etat = jl.getSelectedValue().getEtat();
                    tmp_id = jl.getSelectedValue().getId();
                    if(old_etat.equals("MENEDŻER") || old_etat.equals("TECHNICZNY")) {
                        cb.setSelectedItem(old_etat);
                    }
                    else {
                        cb.setSelectedItem("INNY");
                        tf3.setText(old_etat);
                    }
                }
            }
        });
        sp.setBounds(15, 10, 250, 500);
        l1.setBounds(15, 510, 50, 30);
        l2.setBounds(15, 540, 50, 30);
        l3.setBounds(15, 600, 50, 30);
        tf1.setBounds(65, 510, 150, 30);
        tf2.setBounds(65, 540, 150, 30);
        tf3.setBounds(65, 600, 150, 30);
        jb.setBounds(140, 630, 100, 40);
        cb.setBounds(15, 570, 150, 30);
        add(l1);
        add(l2);
        add(tf1);
        add(tf2);
        add(jb);
        add(cb);
        add(sp);
        setLayout(null);
        setSize(280, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
