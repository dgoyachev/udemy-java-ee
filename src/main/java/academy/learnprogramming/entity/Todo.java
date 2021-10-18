package academy.learnprogramming.entity;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@NamedQuery(name = Todo.FIND_TODO_BY_TASK, query = "select t from Todo t where t.task like :task and t.todoOwner.email = :email")
@NamedQuery(name = Todo.FIND_ALL_TODOS_BY_USER, query = "select t from Todo t where t.todoOwner.email = :email")
@NamedQuery(name = Todo.FIND_TODO_BY_ID, query = "select t from Todo t where t.id = :id and t.todoOwner.email = :email")
public class Todo extends AbstractEntity {

    public static final String FIND_TODO_BY_TASK = "Todo.findByTask";
    public static final String FIND_ALL_TODOS_BY_USER = "Todo.findByUser";
    public static final String FIND_TODO_BY_ID = "Todo.findById";

    @NotEmpty(message = "Task must be set")
    @Size(min = 10, message = "Task should not be less than 10 characters")
    private String task;

    @NotNull(message = "Due must be set")
    @FutureOrPresent(message = "Dude must be in the present or future")
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private LocalDate dueDate;

    private boolean isCompleted;
    private LocalDate dateCompleted;
    private LocalDate dateCreated;

    @ManyToOne
    private User todoOwner;

    @PrePersist
    private void init() {
        setDateCreated(LocalDate.now());
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public LocalDate getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(LocalDate dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getTodoOwner() {
        return todoOwner;
    }

    public void setTodoOwner(User todoOwner) {
        this.todoOwner = todoOwner;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", dueDate=" + dueDate +
                ", isCompleted=" + isCompleted +
                ", dateCompleted=" + dateCompleted +
                ", dateCreated=" + dateCreated +
                ", todoOwner=" + todoOwner +
                '}';
    }
}