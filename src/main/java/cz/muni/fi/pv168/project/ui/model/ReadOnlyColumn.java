package cz.muni.fi.pv168.project.ui.model;

import java.util.function.Function;

/**
 * Class for representing a read-only column in the table.
 * @param <E> Table entity
 * @param <T> Column value type
 *
 * @author Radomír Dedek
 */
public class ReadOnlyColumn<E, T> extends Column<E, T>{

    protected ReadOnlyColumn(String name, Class<T> columnClass, Function<E, T> valueGetter) {
        super(name, columnClass, valueGetter);
    }

    @Override
    void setValue(Object value, E entity) {
        throw new UnsupportedOperationException("Column " + super.getColumnName() + " is not editable.");
    }

    @Override
    boolean isEditable() {
        return false;
    }
}
