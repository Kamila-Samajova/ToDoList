package cz.muni.fi.pv168.project.ui.model;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Class for representing an editable column in the table.
 * @param <E> Table entity
 * @param <T> Column value type
 *
 * @author Radomír Dedek
 */
public class EditableColumn<E, T> extends Column<E, T>{

    private final BiConsumer<E, T> valueSetter;

    public EditableColumn(String name, Class<T> columnClass, Function<E, T> valueGetter,
                          BiConsumer<E, T> valueSetter) {
        super(name, columnClass, valueGetter);
        this.valueSetter = valueSetter;
    }

    @Override
    void setValue(Object value, E entity) {
        valueSetter.accept(entity, getColumnClass().cast(value));
    }

    @Override
    boolean isEditable() {
        return true;
    }
}
