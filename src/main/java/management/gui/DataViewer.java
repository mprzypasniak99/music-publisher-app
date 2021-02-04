package management.gui;

import containers.*;
import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class DataViewer extends JFrame {
    private final String[] tabele = {"Album", "Artysta", "Zespół", "Kontrakt", "Pracownik", "Rachunek", "Koncert", "Trasa", "Sesja", "Studio"};
    private final String[] etaty = {"MENEDŻER", "TECHNICZNY", "INNY"};
    private final JComboBox<String> jcb = new JComboBox<>(tabele);

    private final JLabel l = new JLabel();
    private final JTextField tf = new JTextField();

    private final JLabel l2 = new JLabel();
    private final JTextField tf2 = new JTextField();

    private final JLabel l3 = new JLabel();
    private final JTextField tf3 = new JTextField();

    private final JLabel l4 = new JLabel();
    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private final DateFormatter dff = new DateFormatter(df);
    private final JFormattedTextField ftf = new JFormattedTextField(dff);

    private final JLabel l5 = new JLabel();
    private final DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
    private final DateFormatter dff2 = new DateFormatter(df2);
    private final JFormattedTextField ftf2 = new JFormattedTextField(dff2);

    private final JLabel l6 = new JLabel();
    private final NumberFormat nf = new DecimalFormat("#000.00");
    private final NumberFormatter nft = new NumberFormatter(nf);
    private final JFormattedTextField ftf3 = new JFormattedTextField(nft);

    private final JLabel l7 = new JLabel();
    private final JTextField tf4 = new JTextField();

    private final JLabel l8 = new JLabel();
    private final JTextField tf5 = new JTextField();

    private final JLabel l9 = new JLabel();
    private final JTextField tf6 = new JTextField();

    private final JRadioButton r1 = new JRadioButton("Artysta");
    private final JRadioButton r2 = new JRadioButton("Zespół");
    private final JRadioButton r3 = new JRadioButton("Żadne");
    private final ButtonGroup bg = new ButtonGroup();

    JComboBox<String> cb = new JComboBox<>(etaty);

    private final JButton jb = new JButton("Wyświetl");
    private final JButton jb2 = new JButton();
    private final JButton jb3 = new JButton();

    private JTable jt;
    private JScrollPane js;

    private Vector<AlbumContainer> vac;
    private Vector<AuthorContainer> vauc;
    private Vector<ContractContainer> vcc;
    private Vector<EmployeeContainer> vec;
    private Vector<BillContainer> vbc;
    private Vector<ConcertContainer> vcoc;
    private Vector<TourContainer> vtc;
    private Vector<SessionContainer> vsc;
    private Vector<StudioContainer> vstc;
    public DataViewer(MusicPublisherDatabase db) {
        nft.setValueClass(Double.class);
        bg.add(r1);bg.add(r2);bg.add(r3);
        r1.setBounds(10, 40, 100, 30);
        r2.setBounds(110, 40, 100, 30);
        r3.setBounds(210, 40, 100, 30);
        r1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(r1.isSelected()) {
                    l.setText("Imię:");
                    l.setBounds(10, 70, 150, 30);
                    add(l);
                    tf.setBounds(160, 70, 150, 30);
                    tf.setText("");
                    add(tf);

                    l2.setText("Nazwisko:");
                    l2.setBounds(10, 100, 150, 30);
                    add(l2);
                    tf2.setBounds(160, 100, 150, 30);
                    tf2.setText("");
                    add(tf2);

                    l3.setText("Pseudonim:");
                    l3.setBounds(10, 130, 150, 30);
                    add(l3);
                    tf3.setBounds(160, 130, 150, 30);
                    tf3.setText("");
                    add(tf3);
                    repaint();
                }
            }
        });
        r2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(r2.isSelected()) {
                    l.setText("Nazwa:");
                    l.setBounds(10, 70, 150, 30);
                    add(l);
                    tf.setBounds(160, 70, 150, 30);
                    tf.setText("");
                    add(tf);

                    remove(l2);
                    remove(tf2);
                    remove(l3);
                    remove(tf3);

                    repaint();
                }
            }
        });
        r3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(l);
                remove(tf);
                remove(l2);
                remove(tf2);
                remove(l3);
                remove(tf3);

                repaint();
            }
        });
        jcb.setBounds(10, 10, 150, 30);
        add(jcb);
        cb.setBounds(10, 100, 150, 30);
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cb.getSelectedItem() == "INNY") {
                    l3.setText("Etat:");
                    l3.setBounds(15, 130, 150, 30);
                    add(l3);
                    tf3.setBounds(160, 130, 150, 30);
                    add(tf3);
                }
                else {
                    remove(l3);
                    remove(tf3);
                }
                repaint();
            }
        });
        jcb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jcb.getSelectedIndex() == -1) {
                    remAll();
                    repaint();
                } else if (jcb.getSelectedIndex() == 0) {
                    remAll();

                    add(r1);add(r2);add(r3);

                    l7.setText("Nazwa:");
                    l7.setBounds(10, 160, 150, 30);
                    add(l7);
                    tf4.setBounds(160, 160, 150, 30);
                    tf4.setText("");
                    add(tf4);

                    l8.setText("Gatunek:");
                    l8.setBounds(10, 190, 150, 30);
                    add(l8);
                    tf5.setBounds(160, 190, 150, 30);
                    tf5.setText("");
                    add(tf5);

                    repaint();
                } else if (jcb.getSelectedIndex() == 1) {
                    remAll();

                    l.setText("Imię:");
                    l.setBounds(10, 40, 150, 30);
                    add(l);
                    tf.setBounds(160, 40, 150, 30);
                    tf.setText("");
                    add(tf);

                    l2.setText("Nazwisko:");
                    l2.setBounds(10, 70, 150, 30);
                    add(l2);
                    tf2.setBounds(160, 70, 150, 30);
                    tf2.setText("");
                    add(tf2);

                    l3.setText("Pseudonim:");
                    l3.setBounds(10, 100, 150, 30);
                    add(l3);
                    tf3.setBounds(160, 100, 150, 30);
                    tf3.setText("");
                    add(tf3);
                    repaint();
                } else if(jcb.getSelectedIndex() == 2) {
                    remAll();

                    l.setText("Nazwa:");
                    l.setBounds(10, 40, 150, 30);
                    add(l);
                    tf.setBounds(160, 40, 150, 30);
                    tf.setText("");
                    add(tf);
                    repaint();
                } else if(jcb.getSelectedIndex() == 3){
                    remAll();

                    add(r1);add(r2);add(r3);

                    l7.setText("Imię menedżera:");
                    l7.setBounds(10, 160, 150, 30);
                    add(l7);
                    tf4.setBounds(160, 160, 150, 30);
                    tf4.setText("");
                    add(tf4);

                    l8.setText("Nazwisko menedżera:");
                    l8.setBounds(10, 190, 150, 30);
                    add(l8);
                    tf5.setBounds(160, 190, 150, 30);
                    tf5.setText("");
                    add(tf5);

                    l4.setText("Data zaw. (yyyy-MM-dd):");
                    l4.setBounds(10, 220, 150, 30);
                    add(l4);
                    ftf.setValue(null);
                    ftf.setBounds(160, 220, 150, 30);
                    add(ftf);

                    l5.setText("Data zak. (yyyy-MM-dd):");
                    l5.setBounds(10, 250, 150, 30);
                    add(l5);
                    ftf2.setValue(null);
                    ftf2.setBounds(160, 250, 150, 30);
                    add(ftf2);

                    l6.setText("Kwota:");
                    l6.setBounds(10, 280, 150, 30);
                    add(l6);
                    ftf3.setValue(null);
                    ftf3.setBounds(160, 280, 150, 30);
                    add(ftf3);

                    repaint();
                } else if (jcb.getSelectedIndex() == 4) {
                    remAll();

                    l.setText("Imię:");
                    l.setBounds(10, 40, 150, 30);
                    add(l);
                    tf.setBounds(160, 40, 150, 30);
                    tf.setText("");
                    add(tf);

                    l2.setText("Nazwisko:");
                    l2.setBounds(10, 70, 150, 30);
                    add(l2);
                    tf2.setBounds(160, 70, 150, 30);
                    tf2.setText("");
                    add(tf2);

                    add(cb);

                    repaint();
                } else if (jcb.getSelectedIndex() == 5) {
                    remAll();

                    l.setText("Imię:");
                    l.setBounds(10, 40, 150, 30);
                    add(l);
                    tf.setBounds(160, 40, 150, 30);
                    tf.setText("");
                    add(tf);

                    l2.setText("Nazwisko:");
                    l2.setBounds(10, 70, 150, 30);
                    add(l2);
                    tf2.setBounds(160, 70, 150, 30);
                    tf2.setText("");
                    add(tf2);

                    add(cb);

                    l4.setText("Data zaw. (yyyy-MM-dd):");
                    l4.setBounds(10, 160, 150, 30);
                    add(l4);
                    ftf.setValue(null);
                    ftf.setBounds(160, 160, 150, 30);
                    add(ftf);

                    l5.setText("Data zak. (yyyy-MM-dd):");
                    l5.setBounds(10, 190, 150, 30);
                    add(l5);
                    ftf2.setValue(null);
                    ftf2.setBounds(160, 190, 150, 30);
                    add(ftf2);

                    l6.setText("Kwota:");
                    l6.setBounds(10, 220, 150, 30);
                    add(l6);
                    ftf3.setValue(null);
                    ftf3.setBounds(160, 220, 150, 30);
                    add(ftf3);

                    repaint();
                } else if (jcb.getSelectedIndex() == 6) {
                    remAll();

                    l.setText("Kraj:");
                    l.setBounds(10, 40, 150, 30);
                    add(l);
                    tf.setBounds(160, 40, 150, 30);
                    tf.setText("");
                    add(tf);

                    l2.setText("Miasto:");
                    l2.setBounds(10, 70, 150, 30);
                    add(l2);
                    tf2.setBounds(160, 70, 150, 30);
                    tf2.setText("");
                    add(tf2);

                    l3.setText("Ulica:");
                    l3.setBounds(10, 100, 150, 30);
                    add(l3);
                    tf3.setBounds(160, 100, 150, 30);
                    tf3.setText("");
                    add(tf3);

                    l4.setText("Data (yyyy-MM-dd):");
                    l4.setBounds(10, 130, 150, 30);
                    add(l4);
                    ftf.setValue(null);
                    ftf.setBounds(160, 130, 150, 30);
                    add(ftf);

                    l6.setText("Kwota:");
                    l6.setBounds(10, 160, 150, 30);
                    add(l6);
                    ftf3.setValue(null);
                    ftf3.setBounds(160, 160, 150, 30);
                    add(ftf3);

                    repaint();
                } else if (jcb.getSelectedIndex() == 7) {
                    remAll();

                    l.setText("Nazwa trasy:");
                    l.setBounds(10, 40, 150, 30);
                    add(l);
                    tf.setBounds(160, 40, 150, 30);
                    tf.setText("");
                    add(tf);

                    l4.setText("Data rozp. (yyyy-MM-dd):");
                    l4.setBounds(10, 70, 150, 30);
                    add(l4);
                    ftf.setValue(null);
                    ftf.setBounds(160, 70, 150, 30);
                    add(ftf);

                    l5.setText("Data zak. (yyyy-MM-dd):");
                    l5.setBounds(10, 100, 150, 30);
                    add(l5);
                    ftf2.setValue(null);
                    ftf2.setBounds(160, 100, 150, 30);
                    add(ftf2);

                    repaint();
                } else if (jcb.getSelectedIndex() == 8) {
                    remAll();

                    add(r1);add(r2);add(r3);

                    l7.setText("Kraj:");
                    l7.setBounds(10, 160, 150, 30);
                    add(l7);
                    tf4.setBounds(160, 160, 150, 30);
                    tf4.setText("");
                    add(tf4);

                    l8.setText("Miasto:");
                    l8.setBounds(10, 190, 150, 30);
                    add(l8);
                    tf5.setBounds(160, 190, 150, 30);
                    tf5.setText("");
                    add(tf5);

                    l9.setText("Ulica:");
                    l9.setBounds(10, 220, 150, 30);
                    add(l9);
                    tf6.setBounds(160, 220, 150, 30);
                    tf6.setText("");
                    add(tf6);

                    l4.setText("Data (yyyy-MM-dd):");
                    l4.setBounds(10, 250, 150, 30);
                    add(l4);
                    ftf.setValue(null);
                    ftf.setBounds(160, 250, 150, 30);
                    add(ftf);

                    repaint();
                } else if (jcb.getSelectedIndex() == 9) {
                    remAll();

                    l.setText("Kraj:");
                    l.setBounds(10, 40, 150, 30);
                    add(l);
                    tf.setBounds(160, 40, 150, 30);
                    tf.setText("");
                    add(tf);

                    l2.setText("Miasto:");
                    l2.setBounds(10, 70, 150, 30);
                    add(l2);
                    tf2.setBounds(160, 70, 150, 30);
                    tf2.setText("");
                    add(tf2);

                    l3.setText("Ulica:");
                    l3.setBounds(10, 100, 150, 30);
                    add(l3);
                    tf3.setBounds(160, 100, 150, 30);
                    tf3.setText("");
                    add(tf3);

                    repaint();
                }
            }
        });
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jcb.getSelectedIndex() == -1) {
                    new MessageWindow("Nie wybrano rodzaju danych", "Wybierz rodzaj danych, który chcesz obejrzeć");
                } else if (jcb.getSelectedIndex() == 0) {
                    if(r1.isSelected()) {
                        vac = db.getAlbums(false, whereAlbums());
                    } else if(r2.isSelected()) {
                        vac = db.getAlbums(true, whereAlbums());
                    } else {
                        vac = new Vector<>();
                        vac.addAll(db.getAlbums(false, whereAlbums()));
                        vac.addAll(db.getAlbums(true, whereAlbums()));
                    }
                    String[] columns = {"Autor", "Nazwa", "Gatunek"};
                    String[][] data = new String[vac.size()][3];
                    for(int i = 0; i < vac.size(); i++) {
                        data[i][0] = vac.get(i).getAutor();
                        data[i][1] = vac.get(i).getNazwa();
                        data[i][2] = vac.get(i).getGatunek();
                    }
                    jt = new JTable(data, columns){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    jt.clearSelection();
                    if(js != null) remove(js);
                    js = new JScrollPane(jt);
                    js.setBounds(320, 10, 500, 500);
                    add(js);
                    repaint();
                } else if (jcb.getSelectedIndex() == 1) {
                    vauc = db.getAuthors(false, whereArtist());
                    String[] columns = {"Imię", "Nazwisko", "Pseudonim"};
                    String[][] data = new String[vauc.size()][3];
                    for(int i = 0; i < vauc.size(); i++) {
                        data[i][0] = vauc.get(i).getImie();
                        data[i][1] = vauc.get(i).getNazwisko();
                        data[i][2] = vauc.get(i).getPseudonim();
                    }
                    jt = new JTable(data, columns){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    jt.clearSelection();
                    if(js != null) remove(js);
                    js = new JScrollPane(jt);
                    js.setBounds(320, 10, 500, 500);
                    add(js);
                    repaint();
                } else if (jcb.getSelectedIndex() == 2) {
                    vauc = db.getAuthors(true, whereBand());
                    String[] columns = {"Nazwa"};
                    String[][] data = new String[vauc.size()][1];
                    for(int i = 0; i < vauc.size(); i++) {
                        data[i][0] = vauc.get(i).getPseudonim();
                    }
                    jt = new JTable(data, columns){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    jt.clearSelection();
                    if(js != null) remove(js);
                    js = new JScrollPane(jt);
                    js.setBounds(320, 10, 500, 500);
                    add(js);
                    repaint();
                } else if (jcb.getSelectedIndex() == 3) {
                    vcc = db.getContracts(whereContract());
                    String[] columns = {"Autor", "Menedżer", "Data rozp.", "Data zak.", "Kwota", "Rozpoczęty"};
                    String[][] data = new String[vcc.size()][6];
                    for(int i = 0; i < vcc.size(); i++) {
                        data[i][0] = vcc.get(i).getAutor();
                        data[i][1] = vcc.get(i).getMenedzer();
                        data[i][2] = vcc.get(i).getData_rozp().toString();
                        data[i][3] = vcc.get(i).getData_zak().toString();
                        data[i][4] = String.format("%.2f", vcc.get(i).getKwota());
                        data[i][5] = (vcc.get(i).getStarted() ? "Tak" : "Nie");
                    }
                    jt = new JTable(data, columns){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    jt.clearSelection();
                    if(js != null) remove(js);
                    js = new JScrollPane(jt);
                    js.setBounds(320, 10, 500, 500);
                    add(js);
                    repaint();
                } else if (jcb.getSelectedIndex() == 4) {
                    vec = db.getEmployees(whereEmployee());
                    String[] columns = {"Imię", "Nazwisko", "Etat"};
                    String[][] data = new String[vec.size()][3];
                    for(int i = 0; i < vec.size(); i++) {
                        data[i][0] = vec.get(i).getImie();
                        data[i][1] = vec.get(i).getNazwisko();
                        data[i][2] = vec.get(i).getEtat();
                    }
                    jt = new JTable(data, columns){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    jt.clearSelection();
                    if(js != null) remove(js);
                    js = new JScrollPane(jt);
                    js.setBounds(320, 10, 500, 500);
                    add(js);
                    repaint();
                } else if (jcb.getSelectedIndex() == 5) {
                    vbc = db.getBills(whereBill());
                    String[] columns = {"Pracownik", "Data rozp.", "Data zak.", "Kwota", "Rozpoczęty"};
                    String[][] data = new String[vbc.size()][5];
                    for(int i = 0; i < vbc.size(); i++) {
                        data[i][0] = vbc.get(i).getPracownik();
                        data[i][1] = vbc.get(i).getData_rozp().toString();
                        data[i][2] = vbc.get(i).getData_zak().toString();
                        data[i][3] = String.format("%.2f", vbc.get(i).getKwota());
                        data[i][4] = (vbc.get(i).getStarted() ? "Tak" : "Nie");
                    }
                    jt = new JTable(data, columns){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    jt.clearSelection();
                    if(js != null) remove(js);
                    js = new JScrollPane(jt);
                    js.setBounds(320, 10, 500, 500);
                    add(js);
                    repaint();
                } else if (jcb.getSelectedIndex() == 6) {
                    vcoc = db.getConcerts(whereConcert());
                    String[] columns = {"Data koncertu", "Kraj", "Ulica", "Miasto", "Zysk"};
                    String[][] data = new String[vcoc.size()][5];
                    for(int i = 0; i < vcoc.size(); i++) {
                        data[i][0] = vcoc.get(i).getData().toString();
                        data[i][1] = vcoc.get(i).getKraj();
                        data[i][2] = vcoc.get(i).getMiasto();
                        data[i][3] = vcoc.get(i).getUlica();
                        data[i][4] = String.format("%.2f", vcoc.get(i).getZysk());
                    }
                    jt = new JTable(data, columns){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    jt.clearSelection();
                    if(js != null) remove(js);
                    js = new JScrollPane(jt);
                    js.setBounds(320, 10, 500, 500);
                    add(js);
                    repaint();
                } else if (jcb.getSelectedIndex() == 7) {
                    vtc = db.getTours(whereTour());
                    String[] columns = {"Nazwa", "Data rozp.", "Data zak.", "Rozpoczęty"};
                    String[][] data = new String[vtc.size()][4];
                    for(int i = 0; i < vtc.size(); i++) {
                        data[i][0] = vtc.get(i).getNazwa();
                        data[i][1] = vtc.get(i).getData_rozp().toString();
                        data[i][2] = vtc.get(i).getData_zak().toString();
                        data[i][3] = (vtc.get(i).getStarted() ? "Tak" : "Nie");
                    }
                    jt = new JTable(data, columns){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    jt.clearSelection();
                    if(js != null) remove(js);
                    js = new JScrollPane(jt);
                    js.setBounds(320, 10, 500, 500);
                    add(js);
                    repaint();
                } else if (jcb.getSelectedIndex() == 8) {
                    vsc = db.getSessions(whereSession());
                    String[] columns = {"Autor", "Data", "Studio"};
                    String[][] data = new String[vsc.size()][3];
                    for(int i = 0; i < vsc.size(); i++) {
                        data[i][0] = vsc.get(i).getAutor();
                        data[i][1] = vsc.get(i).getData_sesji().toString();
                        data[i][2] = vsc.get(i).getStudio();
                    }
                    jt = new JTable(data, columns){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    jt.clearSelection();
                    if(js != null) remove(js);
                    js = new JScrollPane(jt);
                    js.setBounds(320, 10, 500, 500);
                    add(js);
                    repaint();
                } else if (jcb.getSelectedIndex() == 9) {
                    vstc = db.getStudios(whereStudio());
                    String[] columns = {"Kraj", "Miasto", "Ulica"};
                    String[][] data = new String[vstc.size()][3];
                    for(int i = 0; i < vstc.size(); i++) {
                        data[i][0] = vstc.get(i).getKraj();
                        data[i][1] = vstc.get(i).getMiasto();
                        data[i][2] = vstc.get(i).getUlica();
                    }
                    jt = new JTable(data, columns){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    jt.clearSelection();
                    if(js != null) remove(js);
                    js = new JScrollPane(jt);
                    js.setBounds(320, 10, 500, 500);
                    add(js);
                    repaint();
                }
            }
        });
        jb.setBounds(210, 320, 100, 40);
        add(jb);
        setLayout(null);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void remAll() {
        remove(l);
        remove(tf);

        remove(l2);
        remove(tf2);

        remove(l3);
        remove(tf3);

        remove(l4);
        remove(ftf);

        remove(l5);
        remove(ftf2);

        remove(l6);
        remove(ftf3);

        remove(l7);
        remove(tf4);

        remove(l8);
        remove(tf5);

        remove(l9);
        remove(tf6);

        bg.clearSelection();
        remove(r1);
        remove(r2);
        remove(r3);

        cb.setSelectedIndex(-1);
        remove(cb);
    }

    private String and(String str) {
        if(!str.equals("")) {
            return " AND ";
        } else {
            return "";
        }
    }

    private String whereAlbums() {
        String tmp = "";
        if(r1.isSelected()) {
            if(!tf.getText().equals("")) {
                tmp += "imie = " + tf.getText();
            }
            if(!tf2.getText().equals("")) {
                tmp += and(tmp) + "nazwisko = " + tf2.getText();
            }
            if(!tf3.getText().equals("")) {
                tmp += and(tmp) + "pseudonim = " + tf3.getText();
            }
        } else if (r2.isSelected()) {
            if(!tf.getText().equals("")) {
                tmp += "nazwa_zespolu = " + tf.getText();
            }
        }
        if(!tf4.getText().equals("")) {
            tmp += and(tmp) + "nazwa = " + tf4.getText();
        }
        if(!tf5.getText().equals("")) {
            tmp += and(tmp) + "gatunek_muzyczny = " + tf5.getText();
        }
        return tmp;
    }

    private String whereArtist() {
        String tmp = "";
        if(!tf.getText().equals("")) {
            tmp += "imie = " + tf.getText();
        }
        if(!tf2.getText().equals("")) {
            tmp += and(tmp) + "nazwisko = " + tf2.getText();
        }
        if(!tf3.getText().equals("")) {
            tmp += and(tmp) + "pseudonim = " + tf3.getText();
        }
        return tmp;
    }

    private String whereBand() {
        String tmp = "";
        if(!tf.getText().equals("")) {
            tmp += "nazwa_zespolu = " + tf.getText();
        }
        return tmp;
    }

    private String whereContract() {
        String tmp = "";
        if(r1.isSelected()) {
            if(!tf.getText().equals("")) {
                tmp += "a.imie = " + tf.getText();
            }
            if(!tf2.getText().equals("")) {
                tmp += and(tmp) + "a.nazwisko = " + tf2.getText();
            }
            if(!tf3.getText().equals("")) {
                tmp += and(tmp) + "a.pseudonim = " + tf3.getText();
            }
        } else if (r2.isSelected()) {
            if(!tf.getText().equals("")) {
                tmp += "nazwa_zespolu = " + tf.getText();
            }
        }
        if(!tf4.getText().equals("")){
            tmp += and(tmp) + "imie = " + tf4.getText();
        }
        if(!tf5.getText().equals("")){
            tmp += and(tmp) + "nazwisko = " + tf5.getText();
        }
        if(ftf.getValue() != null) {
            tmp += and(tmp) + "data_zawarcia = TO_DATE('" + ftf.getText() + "', 'YYYY-MM-DD') ";
        }
        if(ftf2.getValue() != null) {
            tmp += and(tmp) + "data_wygasniecia = TO_DATE('" + ftf2.getText() + "', 'YYYY-MM-DD') ";
        }
        if(ftf3.getValue() != null) {
            tmp += and(tmp) + "kwota = " + ftf3.getText();
        }
        return tmp;
    }

    private String whereEmployee() {
        String tmp = "";
        if(!tf.getText().equals("")){
            tmp += and(tmp) + "imie = " + tf.getText();
        }
        if(!tf.getText().equals("")){
            tmp += and(tmp) + "nazwisko = " + tf.getText();
        }
        if(cb.getSelectedIndex() != -1) {
            if(cb.getSelectedItem().equals("MENEDŻER")) {
                tmp += and(tmp) + "etat = " + cb.getSelectedItem();
            }
            if(cb.getSelectedItem().equals("TECHNICZNY")) {
                tmp += and(tmp) + "etat = " + cb.getSelectedItem();
            }
            if(cb.getSelectedItem().equals("INNY")) {
                if(!tf3.getText().equals("")) {
                    tmp += and(tmp) + "etat = " + tf3.getText();
                }
            }
        }
        return tmp;
    }

    private String whereBill() {
        String tmp = "";
        if(!tf.getText().equals("")){
            tmp += and(tmp) + "imie = " + tf.getText();
        }
        if(!tf.getText().equals("")){
            tmp += and(tmp) + "nazwisko = " + tf.getText();
        }
        if(cb.getSelectedIndex() != -1) {
            if(cb.getSelectedItem().equals("MENEDŻER")) {
                tmp += and(tmp) + "etat = " + cb.getSelectedItem();
            }
            if(cb.getSelectedItem().equals("TECHNICZNY")) {
                tmp += and(tmp) + "etat = " + cb.getSelectedItem();
            }
            if(cb.getSelectedItem().equals("INNY")) {
                if(!tf3.getText().equals("")) {
                    tmp += and(tmp) + "etat = " + tf3.getText();
                }
            }
        }
        if(ftf.getValue() != null) {
            tmp += and(tmp) + "data_rozp = TO_DATE('" + ftf.getText() + "', 'YYYY-MM-DD') ";
        }
        if(ftf2.getValue() != null) {
            tmp += and(tmp) + "data_zak = TO_DATE('" + ftf2.getText() + "', 'YYYY-MM-DD') ";
        }
        if(ftf3.getValue() != null) {
            tmp += and(tmp) + "kwota = " + ftf3.getText();
        }
        return tmp;
    }

    private String whereConcert() {
        String tmp = "";
        if(ftf.getValue() != null) {
            tmp += and(tmp) + "data_koncertu = TO_DATE('" + ftf.getText() + "', 'YYYY-MM-DD') ";
        }
        if(!tf.getText().equals("")) {
            tmp += "kraj = " + tf.getText();
        }
        if(!tf2.getText().equals("")) {
            tmp += and(tmp) + "miasto = " + tf2.getText();
        }
        if(!tf3.getText().equals("")) {
            tmp += and(tmp) + "ulica = " + tf3.getText();
        }
        return tmp;
    }

    private String whereTour() {
        String tmp = "";
        if(!tf.getText().equals("")) {
            tmp += "nazwa_trasy = " + tf.getText();
        }
        if(ftf.getValue() != null) {
            tmp += and(tmp) + "data_rozpoczecia = TO_DATE('" + ftf.getText() + "', 'YYYY-MM-DD') ";
        }
        if(ftf2.getValue() != null) {
            tmp += and(tmp) + "data_zakonczenia = TO_DATE('" + ftf2.getText() + "', 'YYYY-MM-DD') ";
        }
        return tmp;
    }

    private String whereSession() {
        String tmp = "";
        if(r1.isSelected()) {
            if(!tf.getText().equals("")) {
                tmp += "imie = " + tf.getText();
            }
            if(!tf2.getText().equals("")) {
                tmp += and(tmp) + "nazwisko = " + tf2.getText();
            }
            if(!tf3.getText().equals("")) {
                tmp += and(tmp) + "pseudonim = " + tf3.getText();
            }
        } else if (r2.isSelected()) {
            if(!tf.getText().equals("")) {
                tmp += "nazwa_zespolu = " + tf.getText();
            }
        }
        if(!tf4.getText().equals("")) {
            tmp += "kraj = " + tf4.getText();
        }
        if(!tf5.getText().equals("")) {
            tmp += and(tmp) + "miasto = " + tf5.getText();
        }
        if(!tf6.getText().equals("")) {
            tmp += and(tmp) + "ulica = " + tf6.getText();
        }
        if(ftf.getValue() != null) {
            tmp += and(tmp) + "data_sesji = TO_DATE('" + ftf.getText() + "', 'YYYY-MM-DD') ";
        }
        return tmp;
    }

    private String whereStudio() {
        String tmp = "";
        if(!tf.getText().equals("")) {
            tmp += "kraj = " + tf.getText();
        }
        if(!tf2.getText().equals("")) {
            tmp += and(tmp) + "miasto = " + tf2.getText();
        }
        if(!tf3.getText().equals("")) {
            tmp += and(tmp) + "ulica = " + tf3.getText();
        }
        return tmp;
    }
}
