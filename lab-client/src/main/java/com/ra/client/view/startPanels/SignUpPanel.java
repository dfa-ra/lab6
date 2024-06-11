package com.ra.client.view.startPanels;

import com.ra.client.view.sours.RoundedButton;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
@Getter
public class SignUpPanel extends JPanel {
    private final JTextField usernameField;
    private final JTextField nameField;
    private final JTextField surnameField;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JButton loginButton;

    private final JLabel usernameLabel;
    private final JLabel passwordLabel;
    private final JLabel nameLabel;
    private final JLabel surnameLabel;
    private final JLabel emailLabel;

    public SignUpPanel(CardLayout cardLayout){
        super(cardLayout);

        Font font = null;
        try{
            font = Font.createFont(Font.TRUETYPE_FONT, new File("lab-client/src/main/resources/ZCOOLKuaiLe-Regular.ttf")).deriveFont(Font.BOLD,50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }


        // Создаем панель для логина и пароля
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5); // Отступы вокруг компонентов


        JLabel label = new JLabel("TickeT", SwingConstants.CENTER);
        if (font != null) {
            System.out.println("here");
            label.setFont(font);
        }
        Font lablefont = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
        Dimension dmLabel = new Dimension(100, 39);

        Dimension dmField = new Dimension(390, 39);
        // Создаем метку и поле ввода для логина
        usernameLabel = new JLabel("Login",  SwingConstants.CENTER);
        usernameLabel.setName("Login");
        usernameLabel.setFont(lablefont);
        usernameLabel.setPreferredSize(dmLabel);
        usernameField = new JTextField();
        usernameField.setPreferredSize(dmField);
        // Создаем метку и поле ввода для пароля
        passwordLabel = new JLabel("Password", SwingConstants.CENTER);
        passwordLabel.setName("Password");
        passwordLabel.setFont(lablefont);
        passwordLabel.setPreferredSize(dmLabel);
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(dmField);
        nameLabel = new JLabel("Name", SwingConstants.CENTER);
        nameLabel.setName("Name");
        nameLabel.setFont(lablefont);
        nameLabel.setPreferredSize(dmLabel);
        nameField = new JTextField();
        nameField.setPreferredSize(dmField);
        surnameLabel = new JLabel("Surname", SwingConstants.CENTER);
        surnameLabel.setName("Surname");
        surnameLabel.setFont(lablefont);
        surnameLabel.setPreferredSize(dmLabel);
        surnameField = new JTextField();
        surnameField.setPreferredSize(dmField);
        emailLabel = new JLabel("email", SwingConstants.CENTER);
        emailLabel.setName("email");
        emailLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        emailLabel.setPreferredSize(dmLabel);
        emailField = new JTextField();
        emailField.setPreferredSize(dmField);

        // Создаем кнопку входа
        loginButton = new RoundedButton("sign_up", 20);
        loginButton.setName("sign_up");
        loginButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        loginButton.setPreferredSize(new Dimension(200, 29));

        // Добавляем компоненты на панель с помощью GridBagLayout
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(surnameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(surnameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(emailField, gbc);

        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(loginButton, gbc);

        add(panel);
        // Добавляем панель на основное окно и центрируем панель
    }
}
