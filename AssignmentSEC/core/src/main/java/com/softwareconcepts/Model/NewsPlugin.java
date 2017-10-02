package com.softwareconcepts.Model;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ReadableByteChannel;

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
        return updateFrequency;
    }

    public void download() {

        try(ReadableByteChannel channel = Channels.newChannel(url.openStream())) {

            ByteBuffer buffer = ByteBuffer.allocate(65536);
            byte[] array = buffer.array();

            int bytesRead = channel.read(buffer);
            while (bytesRead != -1) {
                String str = new String(array, "UTF-8");
                data.append(str);
                buffer.clear();
                bytesRead = channel.read(buffer);
            }
            System.out.println(data);
        }
        catch (ClosedByInterruptException e) {

        }
        catch (IOException e) {

        }
    }
}
