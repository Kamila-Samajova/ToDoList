package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.data.DataAccessObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for representing editable table model.
 * @param <E> an entity of the table.
 *
 * @author Radomír Dedek
 */
public class EditableEntityTableModel<E> extends AbstractEntityTableModel<E> implements EditableModel<E> {

    private final DataAccessObject<E> dataAccessObject;
    private final List<E> rows;

    public EditableEntityTableModel(List<Column<E, ?>> columns, DataAccessObject<E> dataAccessObject) {
        super(columns, dataAccessObject);
        this.dataAccessObject = dataAccessObject;
        this.rows = new ArrayList<>(dataAccessObject.findAll());
    }

    @Override
    public void deleteRow(int rowIndex) {
        dataAccessObject.delete(rows.get(rowIndex));
        rows.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    @Override
    public void addRow(E entity) {
        int newRowIndex = rows.size();
        this.dataAccessObject.create(entity);
        rows.add(entity);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }


    @Override
    public void updateRow(E entity) {
        dataAccessObject.update(entity);
        int rowIndex = rows.indexOf(entity);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    @Override
    public E getEntity(int rowIndex) {
        return this.rows.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }
}
