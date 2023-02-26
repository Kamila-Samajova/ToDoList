package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.data.DataAccessObject;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Abstract class for representing an entity in the table.
 *
 * @param <E> Table entity
 */
abstract class AbstractEntityTableModel<E> extends AbstractTableModel {

    private final List<Column<E, ?>> columns;
    private final DataAccessObject<E> dataAccessObject;

    protected AbstractEntityTableModel(List<Column<E, ?>> columns, DataAccessObject<E> dataAccessObject) {
        this.columns = columns;
        this.dataAccessObject = dataAccessObject;
    }

    public abstract E getEntity(int rowIndex);

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var entity = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(entity);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getColumn(columnIndex).getColumnClass();
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        var entity = getEntity(rowIndex);
        getColumn(columnIndex).setValue(value, entity);
        dataAccessObject.update(entity);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return getColumn(columnIndex).getColumnName();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return getColumn(columnIndex).isEditable();
    }

    public Column<E, ?> getColumn(int columnIndex) {
        return columns.get(columnIndex);
    }
}
