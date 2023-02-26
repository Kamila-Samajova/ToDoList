package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.data.CategoryDao;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.ui.i18n.I18N;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.AbstractListModel;

/**
 * Class for representing the category model.
 *
 * @author Radomír Dedek
 */
public class CategoryModel extends AbstractListModel<Category> {

    private final List<Category> categories;
    private static final cz.muni.fi.pv168.project.ui.i18n.I18N I18N = new I18N(CategoryModel.class);

    public CategoryModel(CategoryDao categoryDao) {
        this(categoryDao, false);
    }

    public CategoryModel(CategoryDao categoryDao, boolean includeAllCategories) {
        var categoryList = categoryDao.findAll();
        if (includeAllCategories) {
            categoryList.add(0, new Category(I18N.getString("all_cats"), true));
        }

        categories = Stream.of(categoryList)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public int getSize() {
        return categories.size();
    }

    @Override
    public Category getElementAt(int index) {
        return categories.get(index);
    }
}
