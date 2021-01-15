package management.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageWindow extends JFrame implements ActionListener {
    private JLabel message;
    private JButton button;

    public MessageWindow(String wName, String text) {
        super(wName);

        // ============= TEXT IN MESSAGE BOX =============
        message = new JLabel(text);

        int width = text.indexOf("\n");
        if(width == -1) {
            width = text.length() * 7;
        }
        else {
            width *= 7;
        }
        message.setBounds(20, 50, width, 50);

        // ============= BUTTON ====================
        button = new JButton("OK");

        button.setBounds((width + 100) / 2 - 50,  125, 100, 30);

        button.addActionListener(this);

        // ============= WINDOW =====================
        setSize(width + 100, 200);

        add(message);
        add(button);

        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        dispose();
    }
}
