package management.gui;

import containers.AuthorContainer;
import containers.StudioContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class AddSession extends JFrame {
    public AddSession(MusicPublisherDatabase db) {
        Vector<AuthorContainer> vac = new Vector<>();
        vac.addAll(db.getAuthors(false));
        vac.addAll(db.getAuthors(true));
        if(vac.size() == 0) {
            new MessageWindow("Brak artystów", "Brak artystów, dla których można stworzyć sesję");
            AddSession.super.dispose();
        }
        Vector<StudioContainer> vsc = db.getStudios();
        if(vac.size() == 0) {
            new MessageWindow("Brak studiów", "Brak studiów, w których może odbyć się sesja");
            AddSession.super.dispose();
        }
        JList<AuthorContainer> jl = new JList<>(vac);
        JScrollPane sp = new JScrollPane(jl);
        JList<StudioContainer> jl2 = new JList<>(vsc);
        JScrollPane sp2 = new JScrollPane(jl2);
        JLabel l = new JLabel("Data sesji (yyyy-MM-dd):");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dff = new DateFormatter(df);
        JFormattedTextField ftf = new JFormattedTextField(dff);
        JButton jb = new JButton("Dodaj");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl.getSelectedIndex() != -1 && jl2.getSelectedIndex() != -1) {
                    if(db.addSession(jl.getSelectedValue().getId(), new Date(((java.util.Date) ftf.getValue()).getTime()), jl2.getSelectedValue().getId())) {
                        new MessageWindow("Dodano", "Pomyślnie dodano sesję nagraniową");
                        AddSession.super.dispose();
                    }
                } else {
                    new MessageWindow("Nie wybrano elementów", "Przed dodaniem wybierz studio i artystę, dla którego chcesz dodać sesję");
                }
            }
        });
        sp.setBounds(10, 10, 250, 500);
        sp2.setBounds(260, 10, 250, 500);
        l.setBounds(10, 510, 150, 30);
        ftf.setBounds(160, 510, 150, 30);
        jb.setBounds(400, 540, 100, 40);
        add(sp);
        add(sp2);
        add(l);
        add(ftf);
        add(jb);
        setLayout(null);
        setSize(530, 620);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
