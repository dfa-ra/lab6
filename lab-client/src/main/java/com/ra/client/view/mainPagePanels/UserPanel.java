package com.ra.client.view.mainPagePanels;

import com.ra.client.view.sours.RoundedButton;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

@Getter
public class UserPanel extends JPanel {
    private final JLabel login;
    private final JLabel userName;
    private final JLabel surname;
    private final JLabel email;


    private final RoundedButton userButton;
    private final RoundedButton settingsButton;
    private final RoundedButton homeButton;
    private final RoundedButton signOutButton;

    public UserPanel(CardLayout cardLayout) {
        super(cardLayout);
        Font font = null;
        try{
            font = Font.createFont(Font.TRUETYPE_FONT, new File("lab-client/src/main/resources/ZCOOLKuaiLe-Regular.ttf")).deriveFont(Font.BOLD,70f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        // Панель для размещения компонентов
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Настройка шрифта для заголовков
        Font labelFont = new Font("Serif", Font.PLAIN, 20);

        // Заголовок
        JLabel titleLabel = new JLabel("user information");
        titleLabel.setFont(font);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        // Метка и поле ввода для логина
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel loginLabel = new JLabel("Login:");
        loginLabel.setName("Login");
        loginLabel.setFont(labelFont);
        panel.add(loginLabel, gbc);


        // Метка и поле ввода для имени
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setName("Name");
        nameLabel.setFont(labelFont);
        panel.add(nameLabel, gbc);

        // Метка и поле ввода для фамилии
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel surnameLabel = new JLabel("Surname:");
        surnameLabel.setName("Surname");
        surnameLabel.setFont(labelFont);
        panel.add(surnameLabel, gbc);

        // Метка и поле ввода для e-mail
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel emailLabel = new JLabel("email:");
        emailLabel.setName("email");
        emailLabel.setFont(labelFont);
        panel.add(emailLabel, gbc);

        gbc.anchor = GridBagConstraints.CENTER  ;
        gbc.gridx = 1;
        gbc.gridy = 1;
        login = new JLabel("ra");
        login.setFont(labelFont);
        panel.add(login, gbc);


        gbc.gridx = 1;
        gbc.gridy = 2;
        userName = new JLabel("Захарченко");
        userName.setFont(labelFont);
        panel.add(userName, gbc);


        gbc.gridx = 1;
        gbc.gridy = 3;
        surname = new JLabel("Роман");
        surname.setFont(labelFont);
        panel.add(surname, gbc);


        gbc.gridx = 1;
        gbc.gridy = 4;
        email = new JLabel("ilsdajvscadkljbn@gmai.com");
        email.setFont(labelFont);
        panel.add(email, gbc);

        // Кнопка "Sign Out"
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        signOutButton = new RoundedButton("sign_out", 20);
        signOutButton.setFont(new Font("Serif",Font.BOLD, 20));
        panel.add(signOutButton, gbc);

        // Панель для кнопок в правом верхнем углу
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        userButton = new RoundedButton("USER", 20);
        userButton.setName("USER");
        userButton.setPreferredSize(new Dimension(99, 29));
        settingsButton = new RoundedButton("Settings", 20);
        settingsButton.setName("settings");
        settingsButton.setPreferredSize(new Dimension(99, 29));
        homeButton = new RoundedButton("Home", 20);
        homeButton.setName("Home");
        homeButton.setPreferredSize(new Dimension(99, 29));
        topPanel.add(userButton);
        topPanel.add(settingsButton);
        topPanel.add(homeButton);

        // Добавление панелей в основное окно
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }
}
