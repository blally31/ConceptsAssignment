package com.softwareconcepts.Controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedList;
import com.softwareconcepts.Model.NewsPlugin;

public class PageDownloader {

    LinkedList<NewsPlugin> pagesList;

    public PageDownloader() {

        pagesList = new LinkedList<>();
    }

    public void addPage(NewsPlugin plugin) {

        pagesList.add(plugin);
    }

    public void startDownloads() {
        for (NewsPlugin page : pagesList) {


        }
    }

    public void download() {

    }
}
