package management.gui;

import containers.AlbumContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class SellReport extends JFrame {
    private final String[] columns = {"Album", "Zysk"};
    private String[][] data;
    private double sum = 0;
    public SellReport(MusicPublisherDatabase db) {
        Vector<AlbumContainer> vac = db.getAlbums();
        if(vac.size() == 0) {
            new MessageWindow("Brak albumów", "Nie ma albumów, dla których można wygenerować");
            SellReport.super.dispose();
        }
        data = new String[vac.size()][2];
        for(int i = 0; i < vac.size(); i++) {
            data[i][0] = vac.get(i).toString();
            Double tmp = db.getSellReportForAlbum(vac.get(i).getId_albumu());
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
