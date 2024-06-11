package com.ra.client.view.mainPagePanels;

import com.ra.client.view.sours.CustomComboBox;
import com.ra.client.view.sours.RoundedButton;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
@Getter
public class SettingsPanel extends JPanel {
    private final JLabel languageLabel;
    private final RoundedButton userButton;
    private final RoundedButton settingsButton;
    private final RoundedButton homeButton;
    private final JComboBox<String> languageComboBox;

    public SettingsPanel(CardLayout cardLayout) {
        super(cardLayout);
        Font font = null;
        try{
            font = Font.createFont(Font.TRUETYPE_FONT, new File("lab-client/src/main/resources/ZCOOLKuaiLe-Regular.ttf")).deriveFont(Font.BOLD,70f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }


        // Основная панель
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Верхняя панель с кнопками
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
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

        // Центральная панель с заголовком и выпадающим списком
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("settings");
        titleLabel.setFont(font);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        languageLabel = new JLabel("Language:");
        languageLabel.setName("Language");
        languageLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        languageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] languages = { "English (Australia)", "Русский", "Български", "Português" };
        languageComboBox = new CustomComboBox<>(languages);
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
}
