module todo {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens todo to javafx.fxml;
    exports todo;
}
