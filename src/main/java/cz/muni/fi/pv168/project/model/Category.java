package cz.muni.fi.pv168.project.model;

import java.util.Objects;

/**
 * Class representing the task category.
 *
 * @author Patrik Mažári
 */
public class Category {

    private String name;
    private Long id;
    private final boolean main;

    public Category(String name) {
        this.name = name;
        this.main = false;
    }

    public Category(String name, boolean main) {
        this.name = name;
        this.main = main;
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
        this.main = false;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isMain() {
        return main;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
