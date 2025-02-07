package todo;

import java.time.LocalDateTime;

public class TodoModel {

    private final int todoID;
    private final String description;
    private boolean todoDone;
    private final LocalDateTime createdAt;
    
    
    public TodoModel(int todoID, String todotext) {
        this.todoID = todoID;
        this.description = todotext;
        this.todoDone = false;
        this.createdAt = LocalDateTime.now();
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
