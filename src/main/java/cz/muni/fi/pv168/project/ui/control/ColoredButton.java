package cz.muni.fi.pv168.project.ui.control;

import javax.swing.JButton;
import java.awt.Color;

/**
 * Class for representing the colored button.
 *
 * @author Patrik Mažári
 */
public class ColoredButton extends JButton {

    public ColoredButton(String label, Color color) {
        super(label);
        setBackground(color);
    }
}
