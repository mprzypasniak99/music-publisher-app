package management.gui;

import containers.ConcertContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class ConcertReport extends JFrame {
    private final String[] columns = {"Koncert", "Zysk"};
    private String[][] data;
    private double sum = 0;
    public ConcertReport(MusicPublisherDatabase db) {
        Vector<ConcertContainer> vcc = db.getConcerts();
        if(vcc.size() == 0) {
            new MessageWindow("Brak koncertów", "Brak koncertów, dla których można wygenerować raport");
            ConcertReport.super.dispose();
        }
        data = new String[vcc.size()][2];
        for(int i = 0; i < vcc.size(); i++) {
            data[i][0] = vcc.get(i).toString();
            Double tmp = vcc.get(i).getZysk();
            data[i][1] = String.format("%.2f", tmp);
            sum += tmp;
        }
        JTable jt = new JTable(data, columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane sp = new JScrollPane(jt);
        JTextField tf = new JTextField(String.format("%.2f", sum));
        sp.setBounds(10, 10, 500, 500);
        tf.setBounds(260, 510, 250, 30);
        tf.setForeground(Color.GREEN);
        tf.setEditable(false);
        add(sp);
        add(tf);
        setLayout(null);
        setSize(530, 570);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
