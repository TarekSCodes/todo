module todo {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.sql;

    opens todo to javafx.fxml;
    exports todo;
}
