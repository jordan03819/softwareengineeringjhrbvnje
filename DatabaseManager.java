import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:mmu_museum.db";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDB() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Create users table
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL," +
                "fullname TEXT NOT NULL," +
                "user_type TEXT NOT NULL)");

            // Create articles table
            stmt.execute("CREATE TABLE IF NOT EXISTS articles (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "content TEXT NOT NULL," +
                "author_id INTEGER NOT NULL," +
                "publish_date DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY(author_id) REFERENCES users(id))");

            stmt.execute("CREATE TABLE IF NOT EXISTS comments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "content TEXT NOT NULL," +
                "author_id INTEGER NOT NULL," + // -1 for guests, or user ID
                "article_id INTEGER NOT NULL," +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY(article_id) REFERENCES articles(id))");

            stmt.execute("CREATE TABLE IF NOT EXISTS comment_likes (" +
                "comment_id INTEGER NOT NULL," +
                "user_id INTEGER," + // NULL for guests (use session_id instead)
                "session_id TEXT," + // For guests
                "UNIQUE(comment_id, user_id, session_id)," + // Prevent duplicate likes
                "FOREIGN KEY(comment_id) REFERENCES comments(id))");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
