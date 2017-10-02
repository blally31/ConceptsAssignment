import com.softwareconcepts.Model.NewsPlugin;

import java.net.MalformedURLException;
import java.net.URL;

public class ARSPlugin extends NewsPlugin {

    public ARSPlugin() {

        this.name = "ARSPlugin";
        this.updateFrequency = 10;
        this.data = new StringBuilder();

        try {
            this.url = new URL("https://arstechnica.com/");
        }
        catch (MalformedURLException e) {
            //Log exception
            e.printStackTrace();
        }
    }
}
