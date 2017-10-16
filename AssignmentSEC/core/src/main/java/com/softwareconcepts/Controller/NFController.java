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
    boolean cancel;

    /**
     *  Default constructor.
     */
    public NFController() {
        this.newsPages = new LinkedList<>();
        this.currentDownloads = new HashSet<>();
        this.scheduler = Executors.newScheduledThreadPool(10);
        this.cancel = false;
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

            scheduler.scheduleAtFixedRate(new Runnable() {
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
            }, 1, p.getUpdateFrequency(), TimeUnit.SECONDS);
        }
    }

    public void cancelDownloads() {
        cancel = true;
    }

    public void forceDownload() {

        ExecutorService update = Executors.newCachedThreadPool();

        for (NewsPlugin p: newsPages) {

            //Check the downloading set to see if the website is currently downloading
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
                if (cancel) {
                    future.cancel(true);
                    System.out.println("canceled future");
                }
                else {
                    try {
                        System.out.println("getting future");
                        future.get();
                    }
                    catch (ExecutionException e) {

                    }
                    catch (InterruptedException e) {

                    }
                }
            }
            else {
                System.out.println("plugin already downloading");
            }
        }
        System.out.println("FORCE HERE");
        cancel = false; //reset cancel
        /*Headline h1 = new Headline("ars", "headline");
        Headline h2 = new Headline("ars", "headline1");
        Headline h3 = new Headline("ars", "headline2");
        Headline h4 = new Headline("ars", "headline3");
        Headline h5 = new Headline("ars", "headline4");
        window.addHeadline(h1);
        window.addHeadline(h2);
        window.addHeadline(h3);
        window.addHeadline(h4);
        window.addHeadline(h5);*/
    }

}
