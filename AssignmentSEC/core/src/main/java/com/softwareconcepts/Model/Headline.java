package com.softwareconcepts.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 *
 */
public class Headline implements Comparable<Headline> {

    private String website;
    private String headline;
    //private String dateTime;
    private LocalDateTime dateTime;

    public Headline(String website, String headline) {
        this.website = website;
        this.headline = headline;
        this.dateTime = LocalDateTime.now();
    }

    public String getHeadLine() {
        return this.headline;
    }
    public LocalDateTime getTime() { return this.dateTime; }

    public String toString() {

        return website + ":  " + headline + "  (" + dateTime.format(
                DateTimeFormatter.ofLocalizedDateTime(
                        FormatStyle.MEDIUM, FormatStyle.SHORT)) + ")";
    }

    public boolean equals(Headline headline) {

        if (this.getHeadLine().equals(headline.getHeadLine())) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Headline h) {
        if (this.getTime().isBefore(h.getTime())) {
            return 1;
        }
        else if (this.getTime().isAfter(h.getTime())) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
