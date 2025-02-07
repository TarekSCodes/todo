package todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBConnector {

    private static DBConnector dbConnector;

    private DBConnector() {}

    public DBConnector getDbConnector() {

        if (dbConnector == null) {
            dbConnector = new DBConnector();
        }
        return dbConnector;
    }

    public List<TodoModel> addTodoToDB() {

        List<TodoModel> todoList = new ArrayList<>();


        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:todo.db")) 
        {
            
            
        } catch (SQLException e) {
            System.err.println("Eintrag in die Datenbank fehlgeschlagen" + e.getMessage());
        }





        return todoList;
    }

}
