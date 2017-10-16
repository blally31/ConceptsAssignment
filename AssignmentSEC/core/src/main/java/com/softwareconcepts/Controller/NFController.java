package com.softwareconcepts.Controller;

import com.softwareconcepts.Model.Headline;
import com.softwareconcepts.Model.NewsPlugin;
import com.softwareconcepts.View.NFWindow;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 */
public class NFController {

    private NFWindow window = null;
    private Object mutex;
    //The controller has instances of the models (plugins) so it can control when and how they are called.
    private LinkedList<NewsPlugin> newsPages;
    private HashSet<NewsPlugin> currentDownloads; // May need to synchronise!!!!!

    /**
     *  Default constructor.
     */
    public NFController() {
        this.newsPages = new LinkedList<>();
        currentDownloads = new HashSet<>();
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
    public void initDownloads() {

        for (NewsPlugin p: newsPages) {

            Timer timer = new Timer(p.getName());
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Adding to downloads set");
                    currentDownloads.add(p);
                    window.addDownload(p);
                    System.out.println("Starting download: " + p.getName());
                    System.out.println("URL: " + p.getURL());
                    p.download(window);
                    System.out.println("Removing from downloads set");
                    currentDownloads.remove(p);
                    window.removeDownload(p);

                }
            }, 1000, p.getUpdateFrequency());
        }
    }

    public void forceDownload() {

        /*for (NewsPlugin p: newsPages) {

            //Check the downloading set to see if the website is currently downloading
            if (!currentDownloads.contains(p)) {
                // download
                Thread download = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        p.download(window);
                    }
                });
                download.start();
            }
        }*/
        Headline h1 = new Headline("ars", "headline");
        Headline h2 = new Headline("ars", "headline1");
        Headline h3 = new Headline("ars", "headline2");
        Headline h4 = new Headline("ars", "headline3");
        Headline h5 = new Headline("ars", "headline4");
        window.addHeadline(h1);
        window.addHeadline(h2);
        window.addHeadline(h3);
        window.addHeadline(h4);
        window.addHeadline(h5);
    }

}
