package com.softwareconcepts.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Headline implements Comparable<Headline> {

    private String website;
    private String headline;
    private String dateTime;

    public Headline(String website, String headline) {
        this.website = website;
        this.headline = headline;
        this.dateTime = LocalDateTime.now().format(
                DateTimeFormatter.ofLocalizedDateTime(
                        FormatStyle.MEDIUM, FormatStyle.SHORT));
    }

    public String toString() {

        return website + ":  " + headline + "  (" + dateTime + ")";
    }

    @Override
    public int compareTo(Headline headline) {

        return 1;
    }
}
