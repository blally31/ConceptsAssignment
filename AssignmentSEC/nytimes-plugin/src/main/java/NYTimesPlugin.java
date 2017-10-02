import com.softwareconcepts.Model.NewsPlugin;

import java.net.MalformedURLException;
import java.net.URL;

public class NYTimesPlugin extends NewsPlugin {

    public NYTimesPlugin() {

        this.name = "NYTimesPlugin";
        this.updateFrequency = 20;

        try {
            this.url = new URL("https://www.nytimes.com/");
        }
        catch (MalformedURLException e) {
            //Log exception
            e.printStackTrace();
        }
    }
}
