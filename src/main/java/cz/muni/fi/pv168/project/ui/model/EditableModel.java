package cz.muni.fi.pv168.project.ui.model;

/**
 * Generic interface for operations with editable table.
 *
 * @param <E> type of the entity this EditableModel operates on
 */
public interface EditableModel<E> {

    /**
     * Deletes the specified row from the table.
     *
     * @param rowIndex index of the row to be deleted
     */
    void deleteRow(int rowIndex);

    /**
     * Adds an entity to the table.
     *
     * @param entity entity to be added
     */
    void addRow(E entity);

    /**
     * Updates an entity in the table.
     *
     * @param entity entity to be updated
     */
    void updateRow(E entity);

    /**
     * Returns an entity at the specified index.
     *
     * @param rowIndex index of the row to be returned
     * @return entity on a given index
     */
    E getEntity(int rowIndex);
}
