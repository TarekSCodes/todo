package todo;

public class TodoModel {

    private final String description;
    private boolean todoDone;

    public TodoModel(String todotext) {
        this.description = todotext;
        this.todoDone = false;
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
