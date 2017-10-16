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

    public String getHeadLine() {
        return this.headline;
    }
    public String getTime() { return this.dateTime; }


    public String toString() {

        return website + ":  " + headline + "  (" + dateTime + ")";
    }

    public boolean equals(Headline headline) {

        if (this.getHeadLine().equals(headline.getHeadLine())) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Headline headline) {

        /*if (this.dateTime.compareTo(headline.dateTime) > 0) {
            return -1;
        }
        else if (this.dateTime.compareTo(headline.dateTime) == 0) {
            if (this.headline.compareTo(headline.getHeadLine()) > 0) {
                return 1;
            }
            else if (this.headline.compareTo(headline.getHeadLine()) < 0) {
                return-1;
            }
            return 0;
        }
        return 1;*/

        if (this.getHeadLine().equals(headline.getHeadLine())) {
            return 0;
        }
        else if (this.getTime().compareTo(headline.getTime()) > 0) {
            return -1;
        }
        return 1;
    }
}
