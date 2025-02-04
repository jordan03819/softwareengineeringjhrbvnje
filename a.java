import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.net.*;

public class NavBar extends JPanel {
	private JFrame frame;
	private JPanel contentPanel;
	private String session;

	public NavbarPanel(JPanel contentPanel, User user) {
		this.contentPanel = contentPanel;
		session = user.getUserType();
		this.frame = frame;

		this.setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel("MMU Digital Museum");
		this.add(titleLabel, BorderLayout.WEST);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JButton homeBtn = new JButton("Home"); // home button
		homeBtn.addActionListener(e -> gotoPage("Home");
		JButton articlesBtn = new JButton("Articles"); // articles button
		articlesBtn.addActionListener(e -> gotoPage("Articles");
		JButton accBtn = createAccButton(); // account button (changes for guest)

		buttonPanel.add(homeBtn);
		buttonPanel.add(articlesBtn);
		buttonPanel.add(accBtn);

		this.add(buttonPanel, BorderLayout.EAST);
	}
	
	private JButton createAccButton() { // for different acc buttons
		JButton btn;
		if (!session.equals("guest")) {
			acc = new JButton("" + user.getUsername());
			acc.addActionListener(e -> showAccDropdown());
		} else {
			acc = new JButton ("Login/Register");
			acc.addActionListener(e -> gotoPage("Login"));
		}
		return btn;
	}

	private void gotoPage(String page) {
		frame.getContentPane().remove(1); // clear page
		switch(page) {
			case "Home":
				frame.add(new HomePage, user);
				break;
				//
			case "Articles":
				frame.add(new ArticlePage, user);
				break;
			case "Login":
				frame.add(new LoginPage, user);
				break;
			case "UserMgmt":
				frame.add(new UserMgmtPage, user);
				break;
			case "NewArticle":
				frame.add(new NewArticlePage, user);
				break;
			case "Drafts":
				frame.add(new DraftPage, user);
				break;
		}
	}

	private void showAccDropdown() {
		JPopupMenu dropdown = new JPopupMenu();
		JMenuItem logoutItem = new JMenuItem("Welcome Back" + user.getUsername();
		switch(user.getUserType()) {
			case "Member":
				// dashbaord?
				break;
			case "Admin":
				JMenuItem option1 = new JMenuItem("Manage users");
				option1.addActionListener(e -> gotoPage("UserMgmt");

				JMenuItem option2 = new JMenuItem("Manage comments");
				option2.addActionListener(e -> gotoPage("CommentMgmt");

				break;
			case "Editor":
				JMenuItem option1 = new JMenuItem("New article");
				option1.addActionListener(e -> gotoPage("NewArticle");
				
				JMenuItem option2 = new JMenuItem("View drafts");
				option2.addActionListener(e -> gotoPage("Drafts");
			
				break;
		}
		JMenuItem logoutItem = new JMenuItem("Logout");
	}
}


public class Main(String[] args) {
	String serverAddress = "localhost";
	int port = 12346;

        try (Socket socket = new Socket(serverAddress, port)) {
            // Input and Output streams for communication
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // Send a message to the server
            output.println("Hello from client!");

            // Receive a response from the server
            String serverResponse = input.readLine();
            System.out.println("Received from server: " + serverResponse);

            // Close the connection
            socket.close();
        } catch (IOException e) {
            System.err.println("Client exception: " + e.getMessage());
        }
}
