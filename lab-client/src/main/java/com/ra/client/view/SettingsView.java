package com.ra.client.view;

import com.ra.client.view.sours.CustomComboBox;
import com.ra.client.view.sours.RoundedButton;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
@Getter
public class SettingsView extends JFrame {
    private final JLabel languageLabel;
    private final RoundedButton userButton;
    private final RoundedButton settingsButton;
    private final RoundedButton homeButton;

    public SettingsView() {
        Font font = null;
        try{
            font = Font.createFont(Font.TRUETYPE_FONT, new File("lab-client/src/main/resources/ZCOOLKuaiLe-Regular.ttf")).deriveFont(Font.BOLD,100f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        setTitle("Settings");
        setSize(1980, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Основная панель
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Верхняя панель с кнопками
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        userButton = new RoundedButton("USER", 20);
        userButton.setPreferredSize(new Dimension(99, 29));
        settingsButton = new RoundedButton("Settings", 20);
        settingsButton.setPreferredSize(new Dimension(99, 29));
        homeButton = new RoundedButton("Home", 20);
        homeButton.setPreferredSize(new Dimension(99, 29));

        topPanel.add(userButton);
        topPanel.add(settingsButton);
        topPanel.add(homeButton);

        // Центральная панель с заголовком и выпадающим списком
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("settings");
        titleLabel.setFont(font);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        languageLabel = new JLabel("Language:");
        languageLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        languageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] languages = { "English (Australia)", "Русский", "Български", "Português" };
        JComboBox<String> languageComboBox = new CustomComboBox<>(languages);
        languageComboBox.setFont(new Font("Serif", Font.PLAIN, 24));
        languageComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel languagePanel = new JPanel();
        languagePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        languagePanel.add(languageLabel);
        languagePanel.add(languageComboBox);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 220)));
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(languagePanel);
        // Добавление панелей в основную панель
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Добавление основной панели в окно
        add(mainPanel);
    }
    public void goVisible(){
        setVisible(true);
    }
    public void goInvisible(){
        setVisible(false);
    }
}
