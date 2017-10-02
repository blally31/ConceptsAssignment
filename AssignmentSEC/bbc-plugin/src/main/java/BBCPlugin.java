import com.softwareconcepts.Model.NewsPlugin;

import java.net.MalformedURLException;
import java.net.URL;

public class BBCPlugin extends NewsPlugin {

    public BBCPlugin() {

        this.name = "BBCPlugin";
        this.updateFrequency = 15;
        this.data = new StringBuilder();

        try {
            this.url = new URL("http://www.bbc.co.uk/news/");
        }
        catch (MalformedURLException e) {
            //Log exception
            e.printStackTrace();
        }
    }
}
