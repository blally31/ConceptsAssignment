package com.softwareconcepts.Controller;

import com.softwareconcepts.Model.Headline;
import com.softwareconcepts.Model.NewsPlugin;
import com.softwareconcepts.View.NFWindow;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.*;

/**
 *
 */
public class NFController {

    private NFWindow window = null;
    private Object mutex;
    //The controller has instances of the models (plugins) so it can control when and how they are called.
    private LinkedList<NewsPlugin> newsPages;
    private HashSet<NewsPlugin> currentDownloads; // May need to synchronise!!!!!
    private ScheduledExecutorService scheduler;
    private LinkedList<Future> futureList;
    private boolean isScheduled;

    /**
     *  Default constructor.
     */
    public NFController() {
        this.newsPages = new LinkedList<>();
        this.currentDownloads = new HashSet<>();
        this.scheduler = Executors.newScheduledThreadPool(10);
        futureList = new LinkedList<>();
        this.isScheduled = false;
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
     * Method that schedules periodic downloads from the set of sources
     * provided at runtime. Scheduling frequency is provided by the
     * individual source plugin classes.
     */
    public void initDownloads() {

        futureList.clear();
        for (NewsPlugin p: newsPages) {

            ScheduledFuture future = scheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {

                    currentDownloads.add(p);
                    isScheduled = true;
                    window.addDownload(p);
                    System.out.println("Starting download: " + p.getName());
                    System.out.println("URL: " + p.getURL());
                    p.download(window);
                    currentDownloads.remove(p);
                    isScheduled = false;
                    window.removeDownload(p);
                }
            }, 1, p.getUpdateFrequency(), TimeUnit.SECONDS);
            futureList.add(future);
        }
    }

    /**
     * Method that cancels all currently downloading sources if the
     * cancel button is pressed. If the downloading source is a scheduled
     * source, then it is rescheduled.
     */
    public void cancelDownloads() { //Add a strategy pattern classes so don't need future isScheduled flag.

        if (!currentDownloads.isEmpty()) {
            System.out.println("Cancelling Downloads. Future list size: " + futureList.size());
            for (Future f: futureList) {
                f.cancel(true);
            }
        }
        //If the downloads to be cancelled are the scheduled downloads,
        //need to reschedule.
        if (isScheduled) {
            System.out.println("Rescheduling Downloads");
            initDownloads();
        }
    }

    /**
     * Method that forces an immediate download from all news sources
     * if the update button is pressed.
     *
     */
    public void updateDownloads() {

        ExecutorService update = Executors.newCachedThreadPool();
        futureList.clear();
        for (NewsPlugin p: newsPages) {

            //Check the downloading set to see if the website is
            // currently downloading
            if (!currentDownloads.contains(p)) {
                // download
                Future future = update.submit(new Runnable() {
                    @Override
                    public void run() {
                        currentDownloads.add(p);
                        window.addDownload(p);
                        p.download(window);
                        currentDownloads.remove(p);
                        window.removeDownload(p);
                    }
                });
                futureList.add(future);
            }
            else {
                System.out.println("plugin already downloading");
            }
        }
    }
}
