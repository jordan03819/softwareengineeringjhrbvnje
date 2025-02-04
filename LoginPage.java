import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class LoginPage extends JPanel {
	JFrame frame;

	this.setLayout;//;
	
	public LoginPage(JFrame parentFrame, User user) {
		this.frame = parentFrame;

		JTextField usernameField = new JTextField();
		JPasswordField passwordField = new JPasswordField();
		JButton loginButton = new JButton("Login");
		JButton registerButton = new JButton("Register");
	
		loginButton.addActionListener(e -> {
			String username = usernameField.getText();
			String password = passwordField.getPassword();


			if validateLogin(username, password) {
				frame.getContentPane().remove(0);
				frame.add(new NavBar(frame, user), BorderLayout.NORTH);
				frame.getContentPane().remove(1);
				frame.add(new HomePage(user), BorderLayout.CENTER);
				frame.revalidate();
				frame.repaint();
			} else {
			} // error message
		});


		registerButton.addActionListener(e -> {
			frame.gotoPage("RegisterPage");
		}

	}

	private boolean validateLogin(String username, String pwd) {
	        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
	
	        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db");
	        	PreparedStatement pstmt = conn.prepareStatement(query)) {
	
	        	pstmt.setString(1, username);
	        	pstmt.setString(2, password);
	        	ResultSet rs = pstmt.executeQuery();
	
	        	return rs.next(); // True if a matching user is found
	        } catch (SQLException e) {
	        	e.printStackTrace();
			return false;
		}
	}



