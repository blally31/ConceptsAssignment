package com.softwareconcepts.Model;

import java.util.Comparator;

public class DownloadTimeComparator implements Comparator<Headline> {

    @Override
    public int compare(Headline h1, Headline h2) {
        if (h1.getTime().equals(h2.getTime())) {
            return 0;
        }
        else if (h1.getTime().isBefore(h2.getTime())) {
            return 1;
        }
        return -1;
    }
}
