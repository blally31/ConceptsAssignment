package com.softwareconcepts.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class TimePanel extends JPanel {

    private JLabel timeValue;
    private Timer timer;

    public TimePanel() {

        super(new FlowLayout());
        timeValue = new JLabel();
        // Set initial time and date on program start.
        timeValue.setText(LocalDateTime.now().format(
                DateTimeFormatter.ofLocalizedDateTime(
                        FormatStyle.MEDIUM, FormatStyle.SHORT)));
        add(timeValue);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeValue.setText(LocalDateTime.now().format(
                        DateTimeFormatter.ofLocalizedDateTime(
                                FormatStyle.MEDIUM, FormatStyle.SHORT)));
            }
        });
        timer.start();
    }



}
