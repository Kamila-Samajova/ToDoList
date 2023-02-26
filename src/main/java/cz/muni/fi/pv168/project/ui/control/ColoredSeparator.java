package cz.muni.fi.pv168.project.ui.control;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 * Class for representing the colored separator.
 *
 * @author Patrik Mažári
 */
public class ColoredSeparator extends JSeparator {

    public ColoredSeparator(int orientation) {
        super(orientation);
        setForeground(Color.DARK_GRAY);
        setBackground(Color.LIGHT_GRAY);
        if (orientation == SwingConstants.VERTICAL) {
            setPreferredSize(new Dimension(2, 18));
        }
    }
}
