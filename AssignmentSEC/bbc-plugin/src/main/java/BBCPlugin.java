import com.softwareconcepts.Model.Headline;
import com.softwareconcepts.Model.NewsPlugin;
import com.softwareconcepts.View.NFWindow;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Implemented abstract method to parse a html string.
     *
     * @param window    A reference to the View (MVC).
     * @param html      The html to parse formatted as a string.
     */
    public void parseHTML(NFWindow window, String html) {

        String str[] = html.split("<h[1|2]");
        //System.out.println("SIZE: " + str.length);
        for (String s: str) {
            if (s.contains("class=\"heading\"")) {
                Pattern p = Pattern.compile("<a href=\"(.*?)\">(.*?)</a>",
                        Pattern.MULTILINE);
                Matcher m = p.matcher(s);
                if (m.find()) {
                    System.out.println("string: " + m.group(2));
                    //Create new Headline object and add to window
                    Headline headline = new Headline(this.name, m.group(2));
                    //Add to list of headlines
                    window.addHeadline(headline);
                }
            }
        }
    }
}
