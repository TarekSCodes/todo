package todo;

import java.time.LocalDateTime;

public class TodoModel {

    private final int todoID;
    private final String description;
    private boolean todoDone;
    private final LocalDateTime createdAt;
    
    
    public TodoModel(int todoID, String description, boolean todoDone, LocalDateTime createdAt) {
        this.todoID = todoID;
        this.description = description;
        this.todoDone = todoDone;
        this.createdAt = createdAt;
    }
    
    public int getTodoID() {
        return todoID;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isDone() {
        return this.todoDone;
    }

    public void setDone() {
        this.todoDone = true;
    }
}
