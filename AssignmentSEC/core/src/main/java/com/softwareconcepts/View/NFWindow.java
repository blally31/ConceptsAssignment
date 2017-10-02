package com.softwareconcepts.View;

import com.softwareconcepts.Controller.NFController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NFWindow extends JFrame {

    public NFWindow(NFController controller) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TimePanel timePanel = new TimePanel();

        JPanel headlinePanel = new JPanel(new GridBagLayout());
        JPanel downloadsPanel = new JPanel(new GridBagLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Call method in controller
                //Probably start a new thread to download page headlines
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Call method in controller
                //Interrupt the download if (downloading) then cancel else ignore

            }
        });

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(timePanel, BorderLayout.NORTH);
        contentPane.add(headlinePanel);
        contentPane.add(downloadsPanel);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }
}
