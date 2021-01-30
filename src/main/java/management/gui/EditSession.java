package management.gui;

import containers.AuthorContainer;
import containers.SessionContainer;
import containers.StudioContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DateFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class EditSession extends JFrame {
    private long tmp_id_autora;
    private Date tmp_date;
    public EditSession(MusicPublisherDatabase db) {
        Vector<SessionContainer> vssc = db.getSessions();
        if(vssc.size() == 0) {
            new MessageWindow("Brak sesji", "Brak sesji, które można zmodyfikować, warto najpierw jakąś stworzyć.");
            EditSession.super.dispose();
        }
        Vector<AuthorContainer> vac = new Vector<>();
        vac.addAll(db.getAuthors(false));
        vac.addAll(db.getAuthors(true));
        if(vac.size() == 0) {
            new MessageWindow("Brak artystów", "Brak artystów, dla których można zmodyfikować sesję");
            EditSession.super.dispose();
        }
        Vector<StudioContainer> vsc = db.getStudios();
        if(vac.size() == 0) {
            new MessageWindow("Brak studiów", "Brak studiów, w których może odbyć się sesja");
            EditSession.super.dispose();
        }
        JList<AuthorContainer> jl = new JList<>(vac);
        JScrollPane sp = new JScrollPane(jl);
        JList<StudioContainer> jl2 = new JList<>(vsc);
        JScrollPane sp2 = new JScrollPane(jl2);
        JList<SessionContainer> jl3 = new JList<>(vssc);
        JScrollPane sp3 = new JScrollPane(jl3);
        JLabel l = new JLabel("Data sesji (yyyy-MM-dd):");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dff = new DateFormatter(df);
        JFormattedTextField ftf = new JFormattedTextField(dff);
        JButton jb = new JButton("Dodaj");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jl.getSelectedIndex() != -1 && jl2.getSelectedIndex() != -1 && jl3.getSelectedIndex() != -1) {
                    db.editSession(jl.getSelectedValue().getId(), tmp_id_autora, new Date(((java.util.Date) ftf.getValue()).getTime()), tmp_date, jl2.getSelectedValue().getId());
                    new MessageWindow("Zmdyfikowano", "Pomyślnie zmodyfikowano sesję nagraniową");
                    EditSession.super.dispose();
                } else {
                    new MessageWindow("Nie wybrano elementów", "Przed dodaniem wybierz sesję, studio i artystę do modyfikacji");
                }
            }
        });
        jl3.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(jl3.getSelectedIndex() != -1) {
                    tmp_id_autora = jl3.getSelectedValue().getId_autora();
                    tmp_date = jl3.getSelectedValue().getData_sesji();
                    for(AuthorContainer  ac : vac) {
                        if(tmp_id_autora == ac.getId()) {
                            jl.setSelectedValue(ac, true);
                        }
                    }
                    for(StudioContainer  sc : vsc) {
                        if(jl3.getSelectedValue().getStudioId() == sc.getId()) {
                            jl2.setSelectedValue(sc, true);
                        }
                    }
                    ftf.setValue(jl3.getSelectedValue().getData_sesji());
                }
            }
        });
        sp3.setBounds(10, 10, 250, 500);
        sp.setBounds(260, 10, 250, 500);
        sp2.setBounds(510, 10, 250, 500);
        l.setBounds(10, 510, 150, 30);
        ftf.setBounds(160, 510, 150, 30);
        jb.setBounds(660, 540, 100, 40);
        add(sp);
        add(sp2);
        add(sp3);
        add(l);
        add(ftf);
        add(jb);
        setLayout(null);
        setSize(780, 620);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
