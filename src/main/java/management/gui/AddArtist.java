package management.gui;

import containers.AuthorContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.Vector;

public class AddArtist extends JFrame{
    private JRadioButton r1;
    private JRadioButton r2;
    private JButton b;
    private JButton b1;
    private JScrollPane sp;
    private Vector<AuthorContainer> vac, ata;
    private JList<AuthorContainer> jl;

    public AddArtist(MusicPublisherDatabase db) {
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
        setSize(245, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);

    }
    public void artistTrigger(MusicPublisherDatabase db){
        remove(r1);
        remove(r2);
        remove(b);
        JLabel l1 = new JLabel("Imię:");
        JLabel l2 = new JLabel("Nazwisko:");
        JLabel l3 = new JLabel("Pseudonim");
        JTextField tf1 = new JTextField();
        JTextField tf2 = new JTextField();
        JTextField tf3 = new JTextField();
        JButton jb = new JButton("Dodaj");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.addAuthor(tf1.getText(), tf2.getText(), tf3.getText());
                new MessageWindow("Dodano!", "Dodałeś nowego artystę");
                AddArtist.super.dispose();
            }
        });
        l1.setBounds(15, 10, 50, 30);
        l2.setBounds(15, 40, 50, 30);
        l3.setBounds(15, 70, 50, 30);
        tf1.setBounds(65, 10, 150, 30);
        tf2.setBounds(65, 40, 150, 30);
        tf3.setBounds(65, 70, 150, 30);
        jb.setBounds(130, 400, 100, 40);
        add(l1);
        add(l2);
        add(l3);
        add(tf1);
        add(tf2);
        add(tf3);
        add(jb);
        repaint();
    }
    public void bandTrigger(MusicPublisherDatabase db){
        remove(r1);
        remove(r2);
        remove(b);
        ata = new Vector<>();
        JLabel l1 = new JLabel("Nazwa:");
        JTextField tf1 = new JTextField();
        JCheckBox cb = new JCheckBox("Dodać członków");
        JButton jb = new JButton("Dodaj");
        cb.setBounds(30, 50, 150, 30);
        cb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == 1) {
                    vac = db.getAuthors(false);
                    if(vac.size() != 0) {
                        jl = new JList<>(vac);
                        sp = new JScrollPane(jl);
                        sp.setBounds(15,100,250,500);
                        b1 = new JButton("Dodaj");
                        b1.setBounds(200, 50, 70, 40);
                        b1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(jl.getSelectedIndex() == -1) {
                                    new MessageWindow("Błąd", "Nie wybrano żadnego artysty");
                                } else {
                                    ata.add(jl.getSelectedValue());
                                    new MessageWindow("Ok", "Dodano wybranego artystę");
                                }
                            }
                        });
                        jb.setBounds(130, 610, 100, 40);
                        add(b1);
                        add(sp);
                        setSize(300, 700);
                        repaint();
                    } else {
                        new MessageWindow("Błąd", "Brak artystów do dodania");
                    }
                } else {
                    jb.setBounds(130, 400, 100, 40);
                    setSize(245, 500);
                    remove(b1);
                    remove(sp);
                }
            }
        });
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.addAuthor(tf1.getText(), ata);
                new MessageWindow("Dodano!", "Dodałeś nowy zespół");
                AddArtist.super.dispose();
            }
        });
        l1.setBounds(15, 10, 50, 30);
        tf1.setBounds(65, 10, 150, 30);
        jb.setBounds(130, 400, 100, 40);
        add(cb);
        add(l1);
        add(tf1);
        add(jb);
        repaint();
    }
}
