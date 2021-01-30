package management.gui;

import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ReportsMenu extends JFrame {
    JButton[] buttons;
    public ReportsMenu(MusicPublisherDatabase db) {
        buttons = new JButton[7];
        // ========== ADD ALBUM SELL ===========
        buttons[0] = new JButton("Dodaj sprzedaż dla albumu");
        buttons[0].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new AddAlbumSell(db);
            }
        });
        add(buttons[0]);
        // ========== EDIT ALBUM SELL ==========
        buttons[1] = new JButton("Modyfikuj sprzedaż");
        buttons[1].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new EditAlbumSell(db);
            }
        });
        add(buttons[1]);
        // ========== ALBUM SELL REPORT =============
        buttons[2] = new JButton("Wygeneruj raport sprzedaży albumów");
        buttons[2].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new SellReport(db);
            }
        });
        add(buttons[2]);
        // ========== CONCERT REPORT =============
        buttons[3] = new JButton("Wygeneruj raport sprzedaży albumów");
        buttons[3].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new ConcertReport(db);
            }
        });
        add(buttons[3]);
        // ========== BILL REPORT ============
        buttons[4] = new JButton("Wygeneruj raport rachunków");
        buttons[4].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new BillReport(db);
            }
        });
        add(buttons[4]);
        // ========== CONTRACT REPORT ===========
        buttons[5] = new JButton("Wygeneruj raport kontraktów");
        buttons[5].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new ContractReport(db);
            }
        });
        add(buttons[5]);
        // ========== FULL REPORT ===========
        buttons[6] = new JButton("Wygeneruj pełny raport");
        buttons[6].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new FullReport(db);
            }
        });
        add(buttons[6]);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(200,400);
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                ReportsMenu.super.dispose();
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {

            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {

            }
        });
    }
}
