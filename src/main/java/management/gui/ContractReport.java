package management.gui;

import containers.ContractContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class ContractReport extends JFrame{
    private final String[] columns = {"Kontrakt", "Kwota"};
    private String[][] data;
    private double sum = 0;
    public ContractReport(MusicPublisherDatabase db) {
        Vector<ContractContainer> vcc = db.getContracts();
        if(vcc.size() == 0) {
            new MessageWindow("Brak kontraktów", "Brak kontraktów, dla których można wygenerować raport");
            ContractReport.super.dispose();
        }
        data = new String[vcc.size()][2];
        for(int i = 0; i < vcc.size(); i++) {
            data[i][0] = vcc.get(i).toString();
            Double tmp = vcc.get(i).getKwota();
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
