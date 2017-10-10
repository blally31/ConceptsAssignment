package com.softwareconcepts.Model;

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

    public void download() {

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
            //System.out.println(data);
            parseHTML(data.toString());
        }
        catch (ClosedByInterruptException e) {

        }
        catch (IOException e) {

        }
    }

    public static void parseHTML(String html) {
        //Add each news headline into a list/container
        //Pattern p = Pattern.compile("<h1 class=\"heading\"><a href=\"(.*?)\">(\\w+)</a></h2>", Pattern.MULTILINE);
        Pattern p = Pattern.compile("<h1 class=\"heading\"><a href=\"(.*?)\"", Pattern.MULTILINE);
        Matcher m = p.matcher(html);
        //String str[] = html.split();
        //System.out.println("SIZE: " + str.length);
        /*for (String s: str) {
            System.out.println(s);
        }*/

        while (m.find()) {
            System.out.println(m.group(1));
        }
    }
}
