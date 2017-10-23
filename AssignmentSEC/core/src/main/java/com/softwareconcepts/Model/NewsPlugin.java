package com.softwareconcepts.Model;

import com.softwareconcepts.View.NFWindow;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;

/**
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

            System.out.println("Reading website");
            int bytesRead = channel.read(buffer);
            while (bytesRead != -1) {
                String str = new String(array, "UTF-8");
                data.append(str);
                buffer.clear();
                bytesRead = channel.read(buffer);
                //Thread.sleep(2000);
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
            //Thread.currentThread().interrupt();
        }
        catch (IOException e) {

        }
        /*catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() +
                    " InterruptedException");
            e.printStackTrace();
        }*/
    }

    protected void checkHeadlines() {

        for (String head : previousHeadlines.keySet()) {
            if (!currentHeadlines.containsKey(head)) {
                System.out.println("Removing Headline: " + head);
                previousHeadlines.remove(head);
            }
        }
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
