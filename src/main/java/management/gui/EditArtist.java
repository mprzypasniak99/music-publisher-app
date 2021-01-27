package management.gui;

import management.dblogic.MusicPublisherDatabase;
import containers.AuthorContainer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

public class EditArtist extends JFrame {
    private JScrollPane sp;
    private JList<AuthorContainer> jl;
    private Vector<AuthorContainer> vac;
    private final JRadioButton r1;
    private final JRadioButton r2;
    private final JButton b;
    long tmpId;

    public EditArtist(MusicPublisherDatabase db) {
        r1 = new JRadioButton("Artysta");
        r2 = new JRadioButton("Zespół");
        r1.setBounds(15, 15, 100, 100);
        r2.setBounds(130, 15, 100, 100);
        ButtonGroup bg = new ButtonGroup();
        bg.add(r1);
        bg.add(r2);
        add(r1);
        add(r2);
        b = new JButton("Wybierz");
        b.setBounds(90, 130, 100, 40);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(r1.isSelected()) {
                    artistTrigger(db);
                }
                else if(r2.isSelected()) {
                    bandTrigger(db);
                }
            }
        });
        add(b);
        setSize(300, 670);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);

    }
    public void artistTrigger(MusicPublisherDatabase db){
        remove(r1);
        remove(r2);
        remove(b);
        vac = db.getAuthors(false);
        JLabel l1 = new JLabel("Imię:");
        JLabel l2 = new JLabel("Nazwisko:");
        JLabel l3 = new JLabel("Pseudonim");
        JTextField tf1 = new JTextField();
        JTextField tf2 = new JTextField();
        JTextField tf3 = new JTextField();
        if(vac.size() != 0) {
            jl = new JList<>(vac);
            sp = new JScrollPane(jl);
            sp.setBounds(15,15,250,500);
            jl.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(jl.getSelectedIndex() != -1) {
                        tf1.setText(jl.getSelectedValue().getImie());
                        tf2.setText(jl.getSelectedValue().getNazwisko());
                        tf3.setText(jl.getSelectedValue().getPseudonim());
                        tmpId = jl.getSelectedValue().getId();
                    }
                }
            });
        } else {
            new MessageWindow("Brak artystów", "Spróbuj dodać jakichś artystów.");
            EditArtist.super.dispose();
        }
        JButton jb = new JButton("Modyfikuj");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.editAuthor(tmpId, tf1.getText(), tf2.getText(), tf3.getText());
                new MessageWindow("Zrobione!", "Zmodyfikowałeś artystę");
                EditArtist.super.dispose();
            }
        });
        l1.setBounds(15, 525, 50, 30);
        l2.setBounds(15, 555, 50, 30);
        l3.setBounds(15, 585, 50, 30);
        tf1.setBounds(65, 525, 150, 30);
        tf2.setBounds(65, 555, 150, 30);
        tf3.setBounds(65, 585, 150, 30);
        jb.setBounds(130, 615, 100, 40);
        add(l1);
        add(l2);
        add(l3);
        add(tf1);
        add(tf2);
        add(tf3);
        add(jb);
        add(sp);
        repaint();
    }
    public void bandTrigger(MusicPublisherDatabase db){
        remove(r1);
        remove(r2);
        remove(b);
        JLabel l1 = new JLabel("Nazwa:");
        JTextField tf1 = new JTextField();
        vac = db.getAuthors(true);
        if(vac.size() != 0) {
            jl = new JList<>(vac);
            sp = new JScrollPane(jl);
            sp.setBounds(15,15,250,500);
            jl.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(jl.getSelectedIndex() != -1) {
                        tf1.setText(jl.getSelectedValue().getPseudonim());
                        tmpId = jl.getSelectedValue().getId();
                    }
                }
            });
        } else {
            new MessageWindow("Brak zespołów", "Spróbuj dodać jakieś.");
            EditArtist.super.dispose();
        }
        JButton jb = new JButton("Modyfikuj");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.editAuthor(tmpId, tf1.getText());
                new MessageWindow("Zrobione!", "Zmodyfikowałeś zespół");
                EditArtist.super.dispose();
            }
        });
        l1.setBounds(15, 525, 50, 30);
        tf1.setBounds(65, 525, 150, 30);
        jb.setBounds(130, 615, 100, 40);
        add(l1);
        add(tf1);
        add(jb);
        add(sp);
        repaint();
    }
}
