package com.ra.client.view.startPanels;

import com.ra.client.view.sours.RoundedButton;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

@Getter
public class SignInPanel extends JPanel {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JLabel passwordLabel;
    private final JLabel usernameLabel;
    public SignInPanel(CardLayout cardLayout){
        super(cardLayout);
        Font font = null;
        try{
            font = Font.createFont(Font.TRUETYPE_FONT, new File("lab-client/src/main/resources/ZCOOLKuaiLe-Regular.ttf")).deriveFont(Font.BOLD,100f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        setLayout(new GridBagLayout());

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

        // Создаем метку и поле ввода для логина
        usernameLabel = new JLabel("Login",  SwingConstants.CENTER);
        usernameLabel.setName("Login");
        usernameLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        usernameLabel.setPreferredSize(new Dimension(490, 49));
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(590, 49));

        // Создаем метку и поле ввода для пароля
        passwordLabel = new JLabel("Password", SwingConstants.CENTER);
        passwordLabel.setName("Password");
        passwordLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        passwordLabel.setPreferredSize(new Dimension(490, 49));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(590, 49));

        // Создаем кнопку входа
        loginButton = new RoundedButton("sign_in", 20);
        loginButton.setName("sign_in");
        loginButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        loginButton.setPreferredSize(new Dimension(156, 49));
//        loginButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//            }
//        });

        // Добавляем компоненты на панель с помощью GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(loginButton, gbc);

        add(panel);
        // Добавляем панель на основное окно и центрируем панель
    }
}
