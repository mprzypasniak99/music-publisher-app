package management.gui;

import management.dblogic.MusicPublisherDatabase;
import containers.StudioContainer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class EditStudio extends JFrame {
    public EditStudio(MusicPublisherDatabase db) {
        Vector<StudioContainer> vsc = db.getStudios();
        if(vsc.size() == 0) {
            new MessageWindow("Brak studiów", "Dodaj jakieś studia zanim zechcesz je zmodyfikować");
            EditStudio.super.dispose();
        }
        JList<StudioContainer> jl = new JList<>(vsc);
        JScrollPane sp = new JScrollPane(jl);
        JLabel l1 = new JLabel("Kraj:");
        JTextField tf1 = new JTextField();
        JLabel l2 = new JLabel("Miasto:");
        JTextField tf2 = new JTextField();
        JLabel l3 = new JLabel("Ulica:");
        JTextField tf3 = new JTextField();
        JButton jb = new JButton("Dodaj");
        sp.setBounds(10, 10, 250, 500);
        l1.setBounds(10, 510, 100, 30);
        tf1.setBounds(110, 510, 100, 30);
        l2.setBounds(10, 540, 100, 30);
        tf2.setBounds(110, 540, 100, 30);
        l3.setBounds(10, 570, 100, 30);
        tf3.setBounds(110, 570, 100, 30);
        jb.setBounds(110, 600, 100, 40);
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    db.editStudio(jl.getSelectedValue().getId(), tf1.getText(), tf2.getText(), tf3.getText());
                    new MessageWindow("Zrobione", "Zmodyfikowano studio");
                    EditStudio.super.dispose();
                } else {
                    new MessageWindow("Nie wybrano sstudia", "Najpierw wybierz z listy studio, które chcesz edytować");
                }
            }
        });
        jl.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(jl.getSelectedIndex() != -1) {
                    tf1.setText(jl.getSelectedValue().getKraj());
                    tf2.setText(jl.getSelectedValue().getMiasto());
                    tf3.setText(jl.getSelectedValue().getUlica());
                }
            }
        });
        add(l1);
        add(tf1);
        add(l2);
        add(tf2);
        add(l3);
        add(tf3);
        add(jb);
        add(sp);
        setLayout(null);
        setSize(240, 640);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
