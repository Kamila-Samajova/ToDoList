package cz.muni.fi.pv168.project.ui.model;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Base abstract class for representing a column in the table.
 * @param <E> Table entity
 * @param <T> Column value type
 *
 * @author Radomír Dedek
 */
abstract class Column<E, T> {

    private final String name;
    private final Class<T> columnClass;
    private final Function<E, T> valueGetter;

    public Column(String name, Class<T> columnClass, Function<E, T> valueGetter) {
        this.name = name;
        this.columnClass = columnClass;
        this.valueGetter = valueGetter;
    }

    static <E, T> Column<E, T> readOnly(String name, Class<T> columnClass, Function<E, T> valueGetter) {
        return new ReadOnlyColumn<>(name, columnClass, valueGetter);
    }

    static <E, T> Column<E, T> editable(String name, Class<T> columnClass,
                                        Function<E, T> valueGetter, BiConsumer<E, T> valueSetter) {
        return new EditableColumn<>(name, columnClass, valueGetter, valueSetter);
    }

    public String getColumnName() {
        return name;
    }

    public Class<T> getColumnClass() {
        return columnClass;
    }

    public Object getValue(E entity) {
        return valueGetter.apply(entity);
    }

    abstract void setValue(Object value, E entity);

    abstract boolean isEditable();


}
