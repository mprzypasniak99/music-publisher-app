package management.gui;

import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStudio extends JFrame {
    public AddStudio(MusicPublisherDatabase db) {
        JLabel l1 = new JLabel("Kraj:");
        JTextField tf1 = new JTextField();
        JLabel l2 = new JLabel("Miasto:");
        JTextField tf2 = new JTextField();
        JLabel l3 = new JLabel("Ulica:");
        JTextField tf3 = new JTextField();
        JButton jb = new JButton("Dodaj");
        l1.setBounds(10, 10, 100, 30);
        tf1.setBounds(110, 10, 100, 30);
        l2.setBounds(10, 40, 100, 30);
        tf2.setBounds(110, 40, 100, 30);
        l3.setBounds(10, 70, 100, 30);
        tf3.setBounds(110, 70, 100, 30);
        jb.setBounds(110, 100, 100, 40);
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.addStudio(tf1.getText(), tf2.getText(), tf3.getText());
                new MessageWindow("Dodano", "Pomyślnie dodano studio");
                AddStudio.super.dispose();
            }
        });
        add(l1);
        add(tf1);
        add(l2);
        add(tf2);
        add(l3);
        add(tf3);
        add(jb);
        setLayout(null);
        setSize(240, 180);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
