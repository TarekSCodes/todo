package todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConnector {

    private static DBConnector INSTANCE;

    private DBConnector() {
        createTableIfNotExists();
    }

    public static DBConnector getINSTANCE() {

        if (INSTANCE == null) {
            INSTANCE = new DBConnector();
        }
        return INSTANCE;
    }

    private void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS todo ("
                + "todoID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "description TEXT NOT NULL, "
                + "todoDone INTEGER DEFAULT 0 CHECK(todoDone IN (0,1)), "
                + "createdAt datetime DEFAULT CURRENT_TIMESTAMP"
                + ");";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/todo/todo.db");
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Erstellung der Tabelle fehlgeschlagen: " + e.getMessage());
        }
    }

    public void addTodoToDB(String description) {

        try (
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/todo/todo.db");
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO todo (description) VALUES(?)");)
        {
            pstmt.setString(1, description);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Eintrag in die Datenbank fehlgeschlagen" + e.getMessage());
        }
    }

    public List<TodoModel> getTodosFromDB() {

        List<TodoModel> todoList = new ArrayList<>();
        String selectSQL = "SELECT * FROM todo";
        
        try (
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/todo/todo.db");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectSQL);)
        {
            while (rs.next()) {

                boolean todoDone = rs.getInt("todoDone") == 1;

                todoList.add(new TodoModel(
                    rs.getInt("todoID"),
                    rs.getString("description"),
                    todoDone,
                    rs.getTimestamp("createdAt").toLocalDateTime()));
            }

        } catch (SQLException e) {
            System.err.println("Lesen der Datenbank fehlgeschlagen" + e.getMessage());
        }
        return todoList;
    }
}
