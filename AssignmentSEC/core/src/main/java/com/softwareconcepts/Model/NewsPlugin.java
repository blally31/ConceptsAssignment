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

            }

        }
        catch (ClosedByInterruptException e) {

        }
        catch (IOException e) {

        }
    }
}
