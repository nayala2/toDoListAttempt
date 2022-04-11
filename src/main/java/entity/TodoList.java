package entity;

import javax.persistence.*;

@Entity
public class TodoList {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "toDoItem")
    private String toDoItem;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToDoItem() {
        return toDoItem;
    }

    public void setToDoItem(String toDoItem) {
        this.toDoItem = toDoItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TodoList todoList = (TodoList) o;

        if (id != todoList.id) return false;
        if (toDoItem != null ? !toDoItem.equals(todoList.toDoItem) : todoList.toDoItem != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (toDoItem != null ? toDoItem.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return toDoItem;
    }
}
