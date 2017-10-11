import com.softwareconcepts.Controller.NFController;
import com.softwareconcepts.Model.NewsPlugin;
import com.softwareconcepts.PluginLoader;
import com.softwareconcepts.View.NFWindow;

import javax.swing.*;

public class NewsFeed {

    public static void main(String[] args) {

        PluginLoader loader = new PluginLoader();
        NFController controller = new NFController();

        for (String plugin : args) {
            try {
                NewsPlugin p = loader.loadPlugin(plugin);
                controller.addPlugin(p);
            }
            catch (ClassNotFoundException e) {

            }
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("Starting GUI");
                NFWindow window = new NFWindow(controller);
                controller.setWindow(window);
                window.setVisible(true);
                // Start download timers
                controller.startDownloads();
            }
        });
    }
}
