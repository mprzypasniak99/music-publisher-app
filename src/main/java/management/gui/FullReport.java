package management.gui;

import containers.AlbumContainer;
import containers.BillContainer;
import containers.ConcertContainer;
import containers.ContractContainer;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class FullReport extends JFrame {
    private final String[] columns_a = {"Album", "Zysk"};
    private String[][] data_a;
    private Double sum_a = 0.0;
    private JTable jt_a;

    private final String[] columns_c = {"Koncert", "Zysk"};
    private String[][] data_c;
    private Double sum_c = 0.0;
    private JTable jt_c;

    private final String[] columns_co = {"Kontrakt", "Kwota"};
    private String[][] data_co;
    private Double sum_co = 0.0;
    private JTable jt_co;

    private final String[] columns_b = {"Rachunek", "Kwota"};
    private String[][] data_b;
    private Double sum_b = 0.0;
    private JTable jt_b;

    private double total;

    public FullReport(MusicPublisherDatabase db) {
        JProgressBar pb = new JProgressBar(0,4);
        pb.setValue(0);
        pb.setBounds(40, 40, 160, 30);
        setSize(250, 150);
        setLayout(null);
        setVisible(true);
        setLocationRelativeTo(null);
        add(pb);

        Vector<AlbumContainer> vac = db.getAlbums();
        if(vac.size() == 0) {
            DefaultTableModel tb_a = new DefaultTableModel(20, 2);
            tb_a.setColumnIdentifiers(columns_a);
            jt_a = new JTable(tb_a);
        } else {
            data_a = new String[vac.size()][2];
            for(int i = 0; i < vac.size(); i++) {
                data_a[i][0] = vac.get(i).toString();
                Double tmp = db.getSellReportForAlbum(vac.get(i).getId_albumu());
                data_a[i][1] = String.format("%.2f", tmp);
                sum_a += tmp;
            }
            jt_a = new JTable(data_a, columns_a){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        }

        pb.setValue(1);

        Vector<ConcertContainer> vcc = db.getConcerts();
        if(vcc.size() == 0) {
            DefaultTableModel tb_c = new DefaultTableModel(20, 2);
            tb_c.setColumnIdentifiers(columns_c);
            jt_c = new JTable(tb_c);
        } else {
            data_c = new String[vcc.size()][2];
            for (int i = 0; i < vcc.size(); i++) {
                data_c[i][0] = vcc.get(i).toString();
                Double tmp = vcc.get(i).getZysk();
                data_c[i][1] = String.format("%.2f", tmp);
                sum_c += tmp;
            }
            jt_c = new JTable(data_c, columns_c){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        }

        pb.setValue(2);

        Vector<ContractContainer> vcoc = db.getContracts();
        if(vcoc.size() == 0) {
            DefaultTableModel tb_co = new DefaultTableModel(20, 2);
            tb_co.setColumnIdentifiers(columns_co);
            jt_co = new JTable(tb_co);
        } else {
            data_co = new String[vcoc.size()][2];
            for (int i = 0; i < vcoc.size(); i++) {
                data_co[i][0] = vcoc.get(i).toString();
                Double tmp = vcoc.get(i).getKwota();
                data_co[i][1] = String.format("%.2f", tmp);
                sum_co += tmp;
            }
            jt_co = new JTable(data_co, columns_co){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        }

        pb.setValue(3);

        Vector<BillContainer> vbc = db.getBills();
        if(vbc.size() == 0) {
            DefaultTableModel tb_b = new DefaultTableModel(20, 2);
            tb_b.setColumnIdentifiers(columns_b);
            jt_b = new JTable(tb_b);
        } else {
            data_b = new String[vbc.size()][2];
            for (int i = 0; i < vbc.size(); i++) {
                data_b[i][0] = vbc.get(i).toString();
                Double tmp = vbc.get(i).getKwota();
                data_b[i][1] = String.format("%.2f", tmp);
                sum_b += tmp;
            }
            jt_b = new JTable(data_b, columns_b){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        }

        pb.setValue(4);

        setVisible(false);
        remove(pb);

        JScrollPane sp_a = new JScrollPane(jt_a);
        JTextField tf_a = new JTextField(String.format("%.2f", sum_a));
        sp_a.setBounds(10, 10, 300, 500);
        tf_a.setBounds(160, 510, 150, 30);
        tf_a.setForeground(Color.GREEN);
        tf_a.setEditable(false);
        add(sp_a);
        add(tf_a);

        JScrollPane sp_c = new JScrollPane(jt_c);
        sp_c.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp_c.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JTextField tf_c = new JTextField(String.format("%.2f", sum_c));
        sp_c.setBounds(10+300, 10, 300, 500);
        tf_c.setBounds(160+300, 510, 150, 30);
        tf_c.setForeground(Color.GREEN);
        tf_c.setEditable(false);
        add(sp_c);
        add(tf_c);

        JScrollPane sp_co = new JScrollPane(jt_co);
        JTextField tf_co = new JTextField(String.format("%.2f", sum_co));
        sp_co.setBounds(10+600, 10, 300, 500);
        tf_co.setBounds(160+600, 510, 150, 30);
        tf_co.setForeground(Color.RED);
        tf_co.setEditable(false);
        add(sp_co);
        add(tf_co);

        JScrollPane sp_b = new JScrollPane(jt_b);
        JTextField tf_b = new JTextField(String.format("%.2f", sum_b));
        sp_b.setBounds(10+900, 10, 300, 500);
        tf_b.setBounds(160+900, 510, 150, 30);
        tf_b.setForeground(Color.RED);
        tf_b.setEditable(false);
        add(sp_b);
        add(tf_b);

        total = sum_a + sum_c - sum_co - sum_b;

        JTextField tf_s = new JTextField(String.format("%.2f", total));
        tf_s.setBounds(160+900, 540, 250, 30);
        JLabel jl = new JLabel("Suma:");
        jl.setBounds(60+900, 540, 100,30);

        if(total > 0) {
            tf_s.setForeground(Color.GREEN);
        } else if (total < 0){
            tf_s.setForeground(Color.RED);
        }
        tf_s.setEditable(false);
        add(jl);
        add(tf_s);

        setLayout(null);
        setSize(1220, 610);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
