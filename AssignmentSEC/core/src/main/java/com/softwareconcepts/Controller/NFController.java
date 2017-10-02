package com.softwareconcepts.Controller;

import com.softwareconcepts.Model.NewsPlugin;
import com.softwareconcepts.View.NFWindow;

import java.util.LinkedList;

public class NFController {

    private NFWindow window = null;
    private LinkedList<NewsPlugin> pages;

    public NFController() {
        this.pages = new LinkedList<>();
    }

    public void setWindow(NFWindow window) {
        this.window = window;
    }

    public void startDownload() {
        //new thread

    }

    public void addPlugin(NewsPlugin plugin) {
        pages.add(plugin);
    }
}
