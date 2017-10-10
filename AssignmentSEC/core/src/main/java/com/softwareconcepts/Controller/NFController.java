package com.softwareconcepts.Controller;

import com.softwareconcepts.Model.NewsPlugin;
import com.softwareconcepts.View.NFWindow;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class NFController {

    private NFWindow window = null;
    private LinkedList<NewsPlugin> newsPages;

    public NFController() {
        this.newsPages = new LinkedList<>();
    }

    public void setWindow(NFWindow window) {
        this.window = window;
    }

    public void addPlugin(NewsPlugin plugin) {
        newsPages.add(plugin);
    }

    public void startDownload() {
        //new thread
        for (NewsPlugin p: newsPages) {
            Timer timer = new Timer(p.getName());
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Starting download: " + p.getName());
                    System.out.println("URL: " + p.getURL());
                    p.download();
                }
            }, 2000, p.getUpdateFrequency());
        }
    }
}
