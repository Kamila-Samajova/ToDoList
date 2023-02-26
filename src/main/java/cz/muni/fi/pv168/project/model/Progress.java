package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.ui.i18n.I18N;

/**
 * Enum class representing the progress.
 *
 * @author Patrik Mažári
 */
public enum Progress {

    PLANNED("planned"),
    IN_PROGRESS("in_progress"),
    FINISHED("finished");

    public final String displayName;
    private static final cz.muni.fi.pv168.project.ui.i18n.I18N I18N = new I18N(Progress.class);

    Progress(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return I18N.getString(displayName);
    }
}
