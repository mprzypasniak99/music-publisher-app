package management.gui;

import containers.BillContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class BillReport extends JFrame {
    private final String[] columns = {"Rachunek", "Kwota"};
    private String[][] data;
    private double sum = 0;
    public BillReport(MusicPublisherDatabase db) {
        Vector<BillContainer> vbc = db.getBills();
        if(vbc.size() == 0) {
            new MessageWindow("Brak rachunków", "Brak rachunków, dla których można wygenerować raport");
            BillReport.super.dispose();
        }
        data = new String[vbc.size()][2];
        for(int i = 0; i < vbc.size(); i++) {
            data[i][0] = vbc.get(i).toString();
            Double tmp = vbc.get(i).getKwota();
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
        tf.setForeground(Color.RED);
        tf.setEditable(false);
        add(sp);
        add(tf);
        setLayout(null);
        setSize(530, 570);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
