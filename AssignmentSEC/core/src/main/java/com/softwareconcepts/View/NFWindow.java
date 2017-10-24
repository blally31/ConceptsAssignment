package com.softwareconcepts.View;

import com.softwareconcepts.Controller.NFController;
import com.softwareconcepts.Model.Headline;
import com.softwareconcepts.Model.NFListModel;
import com.softwareconcepts.Model.NewsPlugin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class NFWindow extends JFrame {

    private NFListModel headlineResults;
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
                Thread updateThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        controller.updateDownloads();
                    }
                });
                updateThread.start();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Call method in controller
                //Interrupt the download if (downloading) then cancel else ignore
                controller.cancelDownloads();
            }
        });

        headlineResults = new NFListModel();
        downloadResults = new DefaultListModel<>();
        JList<Headline> list = new JList<Headline>(headlineResults);

        Panel headlinePanel = new Panel();
        headlinePanel.setLayout(new BorderLayout());
        JScrollPane headlineScroll = new JScrollPane(list);
        headlinePanel.add(new JLabel("News Headlines", SwingConstants.CENTER), BorderLayout.NORTH);
        headlinePanel.add(headlineScroll, BorderLayout.CENTER);

        Panel downloadsPanel = new Panel();
        downloadsPanel.setLayout(new BorderLayout());
        JScrollPane downloadsScroll = new JScrollPane(new JList<NewsPlugin>(downloadResults));
        downloadsPanel.add(new JLabel("Current download source", SwingConstants.CENTER), BorderLayout.NORTH);
        downloadsPanel.add(downloadsScroll, BorderLayout.CENTER);

        Panel listPanel = new Panel();
        listPanel.setLayout(new BorderLayout());
        listPanel.add(headlinePanel, BorderLayout.CENTER);
        listPanel.add(downloadsPanel, BorderLayout.SOUTH);

        //Main GUI container that holds all the different panels.
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(timePanel, BorderLayout.NORTH);
        contentPane.add(listPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        setSize(800, 800);
    }

    public void addHeadlines(HashMap<String, Headline> headlines) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                headlineResults.updateHeadlines(headlines);
            }
        });
    }

    public void addDownload(NewsPlugin download) {

        downloadResults.addElement(download);
    }

    public void removeDownload(NewsPlugin download) {

        downloadResults.removeElement(download);
    }
}
