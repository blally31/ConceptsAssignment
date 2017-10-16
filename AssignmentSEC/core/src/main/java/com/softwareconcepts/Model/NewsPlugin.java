package com.softwareconcepts.Model;

import com.softwareconcepts.View.NFWindow;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ReadableByteChannel;

public abstract class NewsPlugin {

    protected String name;
    protected URL url;
    protected int updateFrequency; //Minutes
    protected StringBuilder data;

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
                Thread.sleep(2000);
            }
            //System.out.println(data);
            parseHTML(window, data.toString());
            data.setLength(0);
        }
        catch (ClosedByInterruptException e) {
            Thread.currentThread().interrupt();
        }
        catch (IOException e) {

        }
        catch (InterruptedException e) {
            e.printStackTrace();
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
