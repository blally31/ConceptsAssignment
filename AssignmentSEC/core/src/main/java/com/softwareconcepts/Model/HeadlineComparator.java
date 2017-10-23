package com.softwareconcepts.Model;

import java.util.Comparator;

public class HeadlineComparator implements Comparator<Headline> {

    @Override
    public int compare(Headline h1, Headline h2) {
        if (h1.getHeadLine().equals(h2.getHeadLine())) {
            return 0;
        }
        else if (h1.getHeadLine().compareTo(h2.getHeadLine()) > 0) {
            return 1;
        }
        return -1;
    }
}
