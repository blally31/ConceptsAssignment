package com.softwareconcepts.View;

import com.softwareconcepts.Controller.NFController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class NFWindow extends JFrame {

    private DefaultListModel<String> headlineResults;
    private DefaultListModel<String> downloadResults;

    public NFWindow(NFController controller) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TimePanel timePanel = new TimePanel();
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
                //controller.startDownload();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Call method in controller
                //Interrupt the download if (downloading) then cancel else ignore

            }
        });

        headlineResults = new DefaultListModel<>();
        //downloadResults = new DefaultListModel<>();
        JList<String> list = new JList<String>(headlineResults);

        JScrollPane headlinePanel = new JScrollPane(list);
        //JScrollPane downloadsPanel = new JScrollPane(new JList<String>(downloadResults));

        //Main GUI container that holds all the different panels.
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(timePanel, BorderLayout.NORTH);
        contentPane.add(headlinePanel, BorderLayout.CENTER);
        //contentPane.add(downloadsPanel);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }

    public void addHeadline(String headline) {
        //System.out.println("ADD HEADLINE: " + headline);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String time = LocalDateTime.now().format(
                        DateTimeFormatter.ofLocalizedDateTime(
                                FormatStyle.MEDIUM, FormatStyle.SHORT));
                StringBuilder line = new StringBuilder();
                line.append(headline + " (" + time +")");
                headlineResults.addElement(line.toString());
            }
        });
    }

    public void addDownload(String download) {

        downloadResults.addElement(download);
    }
}
