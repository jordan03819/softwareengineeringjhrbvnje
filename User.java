import java.sql.*;

public abstract class User {
	private int id;
	private String username;
	private String password;
	private String fullname;
	private String userType;

	public User(int id, String username, String password, String fullname, String userType) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.userType = userType;
	}

	// Getters and setters
	public int getId() { return id; }
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getFullname() { return fullname; }
	public void setFullname(String fullname) { this.fullname = fullname; }
	public String getUserType() { return userType; }
	public void setUserType(String userType) { this.userType = userType; }

	public abstract void viewMenu();

	public static User login(String username, String password) throws SQLException {
		try (Connection conn = DatabaseManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(
				 "SELECT * FROM users WHERE username = ? AND password = ?")) {

			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return UserFactory.createUser(
					rs.getString("user_type"),
					rs.getInt("id"),
					rs.getString("username"),
					rs.getString("password"),
					rs.getString("fullname")
				);
			}
			return new Guest();
		}
	}
}
