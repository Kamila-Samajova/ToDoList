package cz.muni.fi.pv168.project.ui.resources;

import java.awt.Color;

/**
 * Enum class for representing the colors used in the app.
 *
 * @author Patrik Mažári
 */
public enum AppColor {

    MAIN_BLUE("#D2DCE6"),
    PLANNED("#B9E1FF"),
    IN_PROGRESS("#FBECB3"),
    FINISHED("#BEFAD1"),
    VALIDATION("#4E74F7");

    public final Color color;

    AppColor(String colorCode) {
        this.color = Color.decode(colorCode);
    }

    public Color getColor() {
        return color;
    }
}
