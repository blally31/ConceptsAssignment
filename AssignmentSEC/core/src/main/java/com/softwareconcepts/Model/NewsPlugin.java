package com.softwareconcepts.Model;

import com.softwareconcepts.View.NFWindow;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ReadableByteChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class NewsPlugin {

    protected String name;
    protected URL url;
    protected int updateFrequency;
    protected StringBuilder data;


    public String getName() {
        return name;
    }

    public URL getURL() {
        return url;
    }

    public int getUpdateFrequency() {
        return updateFrequency * 60000 ;
    }

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
            }
            parseHTML(window, data.toString());
        }
        catch (ClosedByInterruptException e) {

        }
        catch (IOException e) {

        }
    }

    private void parseHTML(NFWindow window, String html) {

        String str[] = html.split("<h[1|2]");
        //System.out.println("SIZE: " + str.length);
        for (String s: str) {
            if (s.contains("class=\"heading\"")) {
                Pattern p = Pattern.compile("<a href=\"(.*?)\">(.*?)</a>", Pattern.MULTILINE);
                Matcher m = p.matcher(s);
                if (m.find()) {
                    System.out.println("string: " + m.group(2));
                    //Add to list of headlines
                    window.addHeadline(this.name + ": " + m.group(2));
                }
            }
        }
    }
}
