package com.softwareconcepts;

import com.softwareconcepts.Model.NewsPlugin;

import java.nio.file.Files;
import java.nio.file.Paths;

public class PluginLoader extends ClassLoader {

    public NewsPlugin loadPlugin(String filename) throws ClassNotFoundException{

        try {
            byte[] classData = Files.readAllBytes(Paths.get(filename));
            Class<?> cls = defineClass(null, classData, 0, classData.length);
            return (NewsPlugin)cls.newInstance();
        }
        catch (Exception e) {
            //Log exception (Unable to load plugin)
            throw new ClassNotFoundException(
                    String.format("Could not load: '%s': %s", filename, e.getMessage()), e);
        }
    }
}
