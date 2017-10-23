package com.softwareconcepts.Model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ChainedComparator implements Comparator<Headline> {

    private List<Comparator<Headline>> listComparators;

    @SafeVarargs
    public ChainedComparator(Comparator<Headline>... comparators) {
        this.listComparators = Arrays.asList(comparators);
    }

    @Override
    public int compare(Headline h1, Headline h2) {
        for (Comparator<Headline> comparator : listComparators) {
            int result = comparator.compare(h1, h2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
}
