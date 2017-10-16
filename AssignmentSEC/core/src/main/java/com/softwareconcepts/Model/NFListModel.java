package com.softwareconcepts.Model;

import javax.swing.*;
import java.util.TreeSet;

public class NFListModel extends AbstractListModel {

    private TreeSet<Headline> set;

    public NFListModel() {

        set = new TreeSet<>();
    }

    public void addHeadline(Headline headline) {

        if (set.add(headline)) {
            System.out.println("firecontentschanged");
            fireContentsChanged(this, 0, getSize());
        }
    }

    public void removeHeadline(Headline headline) {
        if (set.remove(headline)) {
            fireContentsChanged(this, 0, getSize());
        }
    }

    @Override
    public int getSize() {
        return set.size();
    }

    @Override
    public Headline getElementAt(int index) {
        return (Headline) set.toArray()[index];
    }
}
