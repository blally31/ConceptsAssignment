package com.softwareconcepts.View;

import com.softwareconcepts.Controller.NFController;
import com.softwareconcepts.Model.Headline;
import com.softwareconcepts.Model.NewsPlugin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NFWindow extends JFrame {

    private DefaultListModel<Headline> headlineResults;
    private DefaultListModel<NewsPlugin> downloadResults;

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
                controller.forceDownload();
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
        downloadResults = new DefaultListModel<>();
        JList<Headline> list = new JList<Headline>(headlineResults);

        JScrollPane headlinePanel = new JScrollPane(list);
        JScrollPane downloadsPanel = new JScrollPane(new JList<NewsPlugin>(downloadResults));

        //Main GUI container that holds all the different panels.
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(0,1));
        contentPane.add(timePanel);
        contentPane.add(headlinePanel);
        contentPane.add(downloadsPanel);
        contentPane.add(buttonPanel);
        //pack();
        setSize(750, 750);
    }

    public void addHeadline(Headline headline) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                /*StringBuilder line = new StringBuilder();
                String time = LocalDateTime.now().format(
                        DateTimeFormatter.ofLocalizedDateTime(
                                FormatStyle.MEDIUM, FormatStyle.SHORT));

                line.append(headline + " (" + time +")");*/
                headlineResults.addElement(headline);
            }
        });
    }

    public void addDownload(NewsPlugin download) {

        downloadResults.addElement(download);
    }
}
