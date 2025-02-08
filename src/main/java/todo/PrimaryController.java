package todo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PrimaryController {

    private final List<TodoModel> todoList = new ArrayList<>();
    DBConnector dbConnector = DBConnector.getINSTANCE();
    Alert alertDeleteTodo = new Alert(AlertType.CONFIRMATION);
    

    @FXML
    private TextField todoEntryField;
    @FXML
    private ListView<String> listview;
    @FXML
    private RadioMenuItem alwaysOnTopButton;

    @FXML
    public void initialize() {
        
        initializeTodoListView();
    }

    /**
     * Liefert die aktuelle Stage (das Hauptfenster)
     * 
     * @return Die aktuelle Stage, die das Fenster repräsentiert.
     * @throws IOException
     */
    public Stage getStage() {

        return (Stage) todoEntryField.getScene().getWindow();

    }

    /**
     * Liest den String aus dem Eingabefeld, erstellt damit eines neues
     * TodoModel Objekt und aktuallisiert die Listview.
     */
    @FXML
    public void addTodo() {
        
        String description = todoEntryField.getText();

        if (validateUserInput(description)) {

            dbConnector.addTodoToDB(description);

            initializeTodoListView();
        }
        todoEntryField.clear();
    }

    private void initializeTodoListView() {

        listview.getItems().clear();
        todoList.clear();

        dbConnector.getTodosFromDB().forEach(todoList::add);

        for (TodoModel todo : todoList) {

            listview.getItems().add(todo.getDescription());
        }
    }

    /**
     * Erkennt bei aktivem Textfeld das Drücken von Tasten, ist die
     * gedrückte Taste die Enter-Taste löst sie die addTodo() Methode aus.
     * @param event
     */
    @FXML
    public void enterPressed(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            addTodo();
        }
    }

    /**
     * Prüft ob das Eingabefeld leer ist oder nur Leerzeichen enthält.
     * true wenn ein String vorhanden ist.
     * @param input
     * @return boolean
     */
    private boolean validateUserInput(String input) {

        return input != null && !input.trim().isEmpty();
    }

    @FXML
    public void deleteTodo(MouseEvent event) throws IOException {
        
        if (event.getButton() == MouseButton.SECONDARY) {

            // Damit das Message Fenster nicht verdeckt bleibt
            getStage().setAlwaysOnTop(false);

            alertDeleteTodo.setContentText("Eintrag löschen?");
            Optional<ButtonType> result = alertDeleteTodo.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK) {
                
                int selectedItemIndex = listview.getSelectionModel().getSelectedIndex();
                
                if (selectedItemIndex >= 0) {
                    todoList.remove(selectedItemIndex);
                    listview.getItems().remove(selectedItemIndex);
                }
            }
            // Nach schließen der Messagebox vorherigen Zustand des Hauptfenster wiederherstellen
            alwaysOnTop();
        }
    }

    /**
     * Fragt den Status des alwaysOnTopButtons im Settings Menü ab
     * und setzt die Eigenschaft des Hauptfensters dementsprechend
     */
    @FXML
    public void alwaysOnTop() {
        
        getStage().setAlwaysOnTop(alwaysOnTopButton.isSelected());

    }

    /**
     * Beendet das Programm
     */
    @FXML
    public void quitApp() {

        getStage().close();

    }
}
