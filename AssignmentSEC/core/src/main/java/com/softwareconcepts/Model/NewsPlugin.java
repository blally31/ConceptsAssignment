package com.softwareconcepts.Model;

import com.softwareconcepts.View.NFWindow;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Abstract model class that represents a NewsFeed plugin.
 *
 */
public abstract class NewsPlugin {

    protected String name; // Website name
    protected URL url; // Website URL
    protected int updateFrequency; // Minutes
    protected StringBuilder data;
    protected HashMap<String, Headline> currentHeadlines; // List of current headlines.
    protected HashMap<String, Headline> previousHeadlines; // List of current headlines.

    public String getName() {
        return name;
    }

    public URL getURL() {
        return url;
    }

    public int getUpdateFrequency() {
        return updateFrequency * 60;
    }

    /**
     * Downloads the html for a given URL and formats it as a single string.
     *
     * @param window    The html to parse formatted as a string.
     */
    public void download(NFWindow window) {

        try(ReadableByteChannel channel = Channels.newChannel(url.openStream())) {

            ByteBuffer buffer = ByteBuffer.allocate(65536);
            byte[] array = buffer.array();
            byte[] tmp;
            System.out.println("Reading website");
            int bytesRead = channel.read(buffer);
            while (bytesRead != -1) {
                String str;
                //This step is required to remove blank space that will
                // occur if there is excess space left in the array after
                // reading a chunk of data.
                if (array.length > bytesRead) {
                    tmp = Arrays.copyOf(array, bytesRead);
                    str = new String(tmp, "UTF-8");
                }
                else {
                    str = new String(array, "UTF-8");
                }
                data.append(str);
                buffer.clear();
                bytesRead = channel.read(buffer);
            }
            //System.out.println(data);
            parseHTML(window, data.toString());
            data.setLength(0);
        }
        // ClosedByInterruptException is thrown if thread is cancelled in
        // read() function.
        catch (ClosedByInterruptException e) {
            System.out.println(Thread.currentThread().getName() +
                    " ClosedByInterruptException");
            data.setLength(0);
        }
        catch (IOException e) {

        }
    }

    /**
     * Checks whether the downloaded headlines need to be added or
     * removed before updating the view's list model.
     */
    protected void checkHeadlines() {
        ArrayList<String> found = new ArrayList<>();
        //Remove headlines that are no longer displayed
        for (String head : previousHeadlines.keySet()) {
            if (!currentHeadlines.containsKey(head)) {
                System.out.println("Removing Headline: " + head);
                found.add(head);
            }
        }
        //Intermediate step for removing expired headlines
        for (String str: found) {
            previousHeadlines.remove(str);
        }
        //Add new headlines
        for (String head : currentHeadlines.keySet()) {
            if (!previousHeadlines.containsKey(head)) {
                System.out.println("Adding Headline: " + head);
                previousHeadlines.put(head, currentHeadlines.get(head));
            }
        }
    }

    public String toString(){
        return name;
    }


    /**
     * An abstract helper function that parses html into a Headline object
     * which is then added to the View list and displayed.
     *
     * @param window    A reference to the View (MVC).
     * @param html      The html to parse formatted as a string.
     */
    public abstract void parseHTML(NFWindow window, String html);
}
