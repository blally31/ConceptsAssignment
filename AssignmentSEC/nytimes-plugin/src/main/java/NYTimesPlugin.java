import com.softwareconcepts.Model.Headline;
import com.softwareconcepts.Model.NewsPlugin;
import com.softwareconcepts.View.NFWindow;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class NYTimesPlugin extends NewsPlugin {

    public NYTimesPlugin() {

        this.name = "nytimes.com";
        this.updateFrequency = 1;
        this.data = new StringBuilder();
        this.currentHeadlines = new HashMap<>();
        this.previousHeadlines = new HashMap<>();

        try {
            this.url = new URL("https://www.nytimes.com/");
        }
        catch (MalformedURLException e) {
            //Log exception
            e.printStackTrace();
        }
    }

    /**
     * Implemented abstract method to parse a html string.
     *
     * @param window    A reference to the View (MVC).
     * @param html      The html to parse formatted as a string.
     */
    public void parseHTML(NFWindow window, String html) {
        currentHeadlines.clear();

        String str[] = html.split("<h[1|2]");
        for (String s: str) {
            if (s.contains("class=\"story-heading\"")) {
                Pattern p = Pattern.compile("<a href=\"(.*?)\">(.*?)</a>",
                        Pattern.MULTILINE);
                Matcher m = p.matcher(s);
                if (m.find() && !m.group(2).contains("&raquo;")) {
                    //Create new Headline object and add to window
                    Headline headline = new Headline(this.name, m.group(2).trim());
                    if (previousHeadlines.isEmpty()) {
                        previousHeadlines.put(headline.getHeadLine(), headline);
                    }
                    currentHeadlines.put(headline.getHeadLine(), headline);
                }
            }
        }
        checkHeadlines();
        window.addHeadlines(previousHeadlines);
    }
}
