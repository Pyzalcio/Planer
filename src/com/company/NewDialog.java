package com.company;

import javax.swing.*;
import java.awt.*;

public class NewDialog extends JDialog
{
    public NewDialog(JFrame parent, String title, String text)
    {
        super(parent, true);
        this.setBounds(500, 300, 200, 100);
        this.setDefaultCloseOperation(2);
        this.setTitle(title);
        label.setText(text);
        initComp(parent);
        pack();
    }

    private void initComp(JFrame parent)
    {
        int xParent = parent.getBounds().x;
        int yParent = parent.getBounds().y;
        int wParent = parent.getBounds().width;
        int hParent = parent.getBounds().height;
        int wThis = this.getBounds().width;
        int hThis = this.getBounds().height;
        this.setLocation(xParent + (wParent-wThis)/2, yParent + (hParent-hThis)/2);

        button.addActionListener(e -> tenDialog.dispose());
        panelLabel.add(label);
        panelButton.add(button);
        this.getContentPane().add(panelLabel, BorderLayout.CENTER);
        this.getContentPane().add(panelButton, BorderLayout.SOUTH);
    }

    private JPanel panelLabel = new JPanel();
    private JPanel panelButton = new JPanel();
    private JButton button = new JButton("OK");
    private JLabel label = new JLabel();

    private JDialog tenDialog = this;

    public static void main(String[] args) {
        new Main().setVisible(true);
    }
}
