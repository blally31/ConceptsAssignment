package com.softwareconcepts.Model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class NFListModel extends AbstractListModel {

    private ConcurrentHashMap<String, Headline> listModelMap;
    private List<Headline> listModel;

    public NFListModel() {

        listModelMap = new ConcurrentHashMap<>();
        listModel = new ArrayList<>();
    }

    /**
     * Function that takes a map of the current headlines for a website
     * and determines whether the list model needs to be updated. Runs
     * in the awt-event thread.
     *
     * @param current A map containing all the current headlines for a website.
     */
    public void updateHeadlines(HashMap<String, Headline> current) {
        System.out.println("Updating Headlines!!");

        //Update the listmodel map by adding new headlines.
        for (String key : current.keySet()) {
            if (!listModelMap.containsKey(key)) {
                listModelMap.put(key, current.get(key));
            }
        }
        //Add new headlines to array
        for (Headline h: listModelMap.values()) {
            if (!listModel.contains(h)) {
                listModel.add(h);
            }
        }
        Collections.sort(listModel);
        fireContentsChanged(this, 0, getSize());
    }

    /*private void removeOldHeadlines(HashMap<String, Headline> map) {
        System.out.println("Top of removeOldHeadlines");
        for (String str : listModelMap.keySet()) {
            if (!map.containsKey(str)) {
                System.out.println("Removing an old headline");
                listModelMap.remove(str);
                listModel.remove(listModelMap.get(str));
                fireContentsChanged(this, 0, getSize());
            }
            else {
                System.out.println("Set already contains");
            }
        }
    }
*/
    public boolean isEmpty() {
        if (listModelMap.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public int getSize() {
        return listModel.size();
    }

    @Override
    public Headline getElementAt(int index) {
        return (Headline) listModel.toArray()[index];
    }
}
