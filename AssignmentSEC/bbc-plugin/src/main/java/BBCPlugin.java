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
public class BBCPlugin extends NewsPlugin {

    public BBCPlugin() {

        this.name = "BBCPlugin";
        this.updateFrequency = 1;
        this.data = new StringBuilder();
        this.currentHeadlines = new HashMap<>();
        this.previousHeadlines = new HashMap<>();

        try {
            this.url = new URL("http://www.bbc.co.uk/news/");
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

        String str[] = html.split("<div class");
        for (String s: str) {
            if (s.contains("<h3 class=\"gs-c")) {
                Pattern p = Pattern.compile("<h3 class=\"(.*?)\">(.*?)</h3>",
                        Pattern.MULTILINE);
                Matcher m = p.matcher(s);
                if (m.find()) {
                    Headline headline;
                    if (m.group(2).contains("&#x27;"))
                    {
                        String line = m.group(2).replace("&#x27;", "'");
                        //Create new Headline object and add to window
                        headline = new Headline(this.name, line);
                    }
                    else {
                        headline = new Headline(this.name, m.group(2));
                    }
                    if (previousHeadlines.isEmpty()) {
                        previousHeadlines.put(headline.getHeadLine(), headline);
                    }
                    currentHeadlines.put(headline.getHeadLine(), headline);
                }
            }
        }
        window.addHeadlines(currentHeadlines);
    }
}
