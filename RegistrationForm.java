import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RegistrationForm extends JFrame {
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public RegistrationForm() {
        setTitle("NMU Digital Museum - Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);

        // Set custom colors
        Color backgroundColor = new Color(240, 244, 248);
        Color headerColor = new Color(0, 86, 179);
        Color buttonColor = new Color(40, 167, 69);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(backgroundColor);

        // Registration container panel
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setBackground(Color.WHITE);
        containerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(headerColor);
        JLabel headerLabel = new JLabel("Create an account");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(headerLabel);

        // Form fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 1, 0, 15));
        formPanel.setBackground(Color.WHITE);

        emailField = new JTextField();
        setTextFieldStyle(emailField, "Email");

        usernameField = new JTextField();
        setTextFieldStyle(usernameField, "Username");

        passwordField = new JPasswordField();
        addPasswordPlaceholder(passwordField, "Password");

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(buttonColor);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setOpaque(true);
        registerButton.addActionListener(e -> handleRegistration());

        // Add components to form panel
        formPanel.add(emailField);
        formPanel.add(usernameField);
        formPanel.add(passwordField);

        // Add components to container
        containerPanel.add(headerPanel);
        containerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        containerPanel.add(formPanel);
        containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        containerPanel.add(registerButton);

        // Add container to main panel
        mainPanel.add(containerPanel, BorderLayout.CENTER);

        // Add main panel to frame
        add(mainPanel);
    }

    private void setTextFieldStyle(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        textField.setPreferredSize(new Dimension(300, 30));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void addPasswordPlaceholder(JPasswordField passwordField, String placeholder) {
        passwordField.setEchoChar((char) 0);
        passwordField.setText(placeholder);
        passwordField.setForeground(Color.GRAY);
        passwordField.setPreferredSize(new Dimension(300, 30));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setEchoChar('\u2022');
                    passwordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText(placeholder);
                    passwordField.setEchoChar((char) 0);
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void handleRegistration() {
        String email = emailField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || email.equals("Email") ||
            username.isEmpty() || username.equals("Username") ||
            password.isEmpty() || password.equals("Password")) {
            JOptionPane.showMessageDialog(this,
                "Please complete all fields",
                "Incomplete Form",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this,
                "Password must be at least 6 characters long",
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
            "Registration Successful!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            RegistrationForm form = new RegistrationForm();
            form.setVisible(true);
        });
    }
}
