package management.gui;

import management.dblogic.MusicPublisherDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class EmployeesMenu extends JFrame{
    private JButton[] buttons;
    public EmployeesMenu(MusicPublisherDatabase db) {
        buttons = new JButton[6];
        // ========== ADD EMPLOYEE ===========
        buttons[0] = new JButton("Dodaj pracownika");
        buttons[0].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new AddEmployee(db);
            }
        });
        add(buttons[0]);
        // ========== EDIT EMPLOYEE ==========
        buttons[1] = new JButton("Modyfikuj pracownika");
        buttons[1].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new EditEmployee(db);
            }
        });
        add(buttons[1]);
        // ========== ADD BILL ============
        buttons[2] = new JButton("Dodaj rachunek");
        buttons[2].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new AddBill(db);
            }
        });
        add(buttons[2]);
        // ========== EDIT BILL ===========
        buttons[3] = new JButton("Modyfikuj rachunek");
        buttons[3].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new EditBill(db);
            }
        });
        add(buttons[3]);
        // ========== ADD CONTRACT =========
        buttons[4] = new JButton("Dodaj kontrakt");
        buttons[4].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new AddContract(db);
            }
        });
        add(buttons[4]);
        // ========== EDIT CONTRACT =========
        buttons[5] = new JButton("Modyfikuj kontrakt");
        buttons[5].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new EditContract(db);
            }
        });
        add(buttons[5]);

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
                EmployeesMenu.super.dispose();
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
