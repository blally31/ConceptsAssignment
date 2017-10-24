package com.softwareconcepts.Controller;

import com.softwareconcepts.Model.NewsPlugin;
import com.softwareconcepts.View.NFWindow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.*;

/**
 * Controller class that holds the logic for scheduling downloads, forcing
 * update downloads and cancelling downloads.
 */
public class NFController {

    private NFWindow window = null;
    private LinkedList<NewsPlugin> newsPages;
    private HashSet<NewsPlugin> currentDownloads; // May need to synchronise!!!!!
    private ScheduledExecutorService scheduler;
    private HashMap<NewsPlugin, Future> schedFutureList;
    private HashMap<NewsPlugin, Future> updateFutureList;

    /**
     *  Default constructor.
     */
    public NFController() {
        this.newsPages = new LinkedList<>();
        this.currentDownloads = new HashSet<>();
        this.scheduler = Executors.newScheduledThreadPool(10);
        this.schedFutureList = new HashMap<>();
        this.updateFutureList = new HashMap<>();
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
     * Method that initially schedules periodic downloads from the set
     * of sources provided at runtime.
     */
    public void initDownloads() {

        for (NewsPlugin p: newsPages) {
            scheduleDownload(p, 1);
        }
    }

    /**
     * Schedules a given plugin to download. The scheduling frequency is
     * provided by the individual source plugin classes.
     *
     * @param plugin    A news source plugin.
     */
    private void scheduleDownload(NewsPlugin plugin, int delay) {
        System.out.println("Scheduling plugin: " + plugin.getName());
        ScheduledFuture future = scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                download(plugin);
            }
        }, delay, plugin.getUpdateFrequency(), TimeUnit.SECONDS);
        schedFutureList.put(plugin, future);
    }

    private void download(NewsPlugin plugin) {
        //This is to prevent simultaneous downloads from the same source.
        if (!currentDownloads.contains(plugin)) {
            currentDownloads.add(plugin);
            window.addDownload(plugin);
            plugin.download(window);
            currentDownloads.remove(plugin);
            window.removeDownload(plugin);
            updateFutureList.clear();
        }
    }

    /**
     * Cancels all currently downloading sources if the cancel button
     * is pressed. If the downloading source is a scheduled source, then
     * it is rescheduled.
     */
    public void cancelDownloads() {

        System.out.println("SCHED: " + schedFutureList.size());
        System.out.println("UPDATE: " + updateFutureList.size());
        for (NewsPlugin p: currentDownloads) {
            if (updateFutureList.isEmpty()) {
                if (schedFutureList.containsKey(p)) {
                    schedFutureList.remove(p).cancel(true);
                    System.out.println("CANCELLED SCHEDULED " + p.getName());
                    scheduleDownload(p, p.getUpdateFrequency());
                }
            }
            else {
                if (updateFutureList.containsKey(p)) {
                    updateFutureList.remove(p).cancel(true);
                    System.out.println("CANCELLED UPDATE" + p.getName());
                }
            }
        }
    }

    /**
     * Method that forces an immediate download from all news sources
     * if the update button is pressed.
     */
    public void updateDownloads() {
        updateFutureList.clear();
        ExecutorService update = Executors.newCachedThreadPool();
        for (NewsPlugin p: newsPages) {
            //Check the downloading set to see if the website is
            //currently downloading. This is to prevent simultaneous
            //downloads from the same source.
            if (!currentDownloads.contains(p)) {
                // download
                Future future = update.submit(new Runnable() {
                    @Override
                    public void run() {
                        download(p);
                    }
                });
                System.out.println("Adding to updatelist: " + p);
                updateFutureList.put(p, future);
            }
        }
    }
}
