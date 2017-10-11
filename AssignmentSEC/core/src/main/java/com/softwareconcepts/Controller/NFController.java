package com.softwareconcepts.Controller;

import com.softwareconcepts.Model.NewsPlugin;
import com.softwareconcepts.View.NFWindow;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 */
public class NFController {

    private NFWindow window = null;
    //The controller has instances of the models (plugins) so it can control when and how they are called.
    private LinkedList<NewsPlugin> newsPages;

    /**
     *  Default constructor.
     */
    public NFController() {
        this.newsPages = new LinkedList<>();
    }

    /**
     * Sets the link between the controller and the view (MVC).
     *
     * @param window The GUI view (MVC).
     */
    public void setWindow(NFWindow window) {
        this.window = window;
    }

    /**
     * Adds a new news website plugin into the list.
     *
     * @param plugin A NewsFeed plugin.
     */
    public void addPlugin(NewsPlugin plugin) {
        newsPages.add(plugin);
    }

    /**
     *
     */
    public void startDownloads() {

        for (NewsPlugin p: newsPages) {

            Timer timer = new Timer(p.getName());
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Starting download: " + p.getName());
                    System.out.println("URL: " + p.getURL());
                    p.download(window);
                }
            }, 1000, p.getUpdateFrequency());
        }
    }
}
