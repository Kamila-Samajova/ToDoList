package cz.muni.fi.pv168.project.ui.resources;

import javax.swing.ImageIcon;
import java.net.URL;

/**
 * Class for loading icons used in renderers. Icons are stored in project resources.
 *
 * @author Radomír Dedek
 * @author Kamila Šamajová
 */
public enum Icons {

    PLANNED_ICON("planned_icon.png"),
    IN_PROGRESS_ICON("in_progress_icon.png"),
    FINISHED_ICON("finished_icon.png"),

    TODAY_ICON("today.png");

    public final String path;

    Icons(String path) {
        this.path = path;
    }

    public ImageIcon createIcon() {
        URL url = Icons.class.getClassLoader().getResource(this.path);
        if (url == null) {
            throw new IllegalArgumentException("Icon resource not found on classpath: " + this.path);
        }
        return new ImageIcon(url);
    }
}
