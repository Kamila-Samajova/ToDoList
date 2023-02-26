package cz.muni.fi.pv168.project.ui.control;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Class for setting the layout.
 *
 * @author Patrik Mažári
 */
public class LayoutSettings extends GridBagConstraints {

    private final int row;
    private final int col;
    private final int colSpan;
    private final Insets padding;

    public LayoutSettings(int row, int col, int colSpan) {
        this(row, col, colSpan, false,
                new Insets(5, 10, 5, 10));
    }

    public LayoutSettings(int row, int col, int colSpan, boolean isLastRow, Insets insets) {
        this.row = row;
        this.col = col;
        this.colSpan = colSpan;
        this.padding = insets;
        if (!isLastRow) {
            initialize();
        } else {
            initializeLastRow();
        }
    }

    private void initialize() {
        gridx = col;
        weightx = 1.0;
        gridwidth = colSpan;
        gridy = row;
        fill = GridBagConstraints.HORIZONTAL;
        insets = padding;
    }

    private void initializeLastRow() {
        initialize();
        weighty = 1.0;
        anchor = GridBagConstraints.PAGE_START;
        fill = GridBagConstraints.BOTH;
    }
}
