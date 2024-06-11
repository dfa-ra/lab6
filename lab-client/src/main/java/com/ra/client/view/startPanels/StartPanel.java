package com.ra.client.view.startPanels;

import com.ra.client.view.sours.RoundedButton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


@Log4j2
@Getter
public class StartPanel extends JPanel {

    private final RoundedButton signInButton;
    private final RoundedButton signUpButton;

    public StartPanel(CardLayout cardLayout) {
        super(cardLayout);
        setLayout(new BorderLayout());
        Font font = null;
        try{
            font = Font.createFont(Font.TRUETYPE_FONT, new File("lab-client/src/main/resources/ZCOOLKuaiLe-Regular.ttf")).deriveFont(Font.BOLD,200f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        // Создаем метку

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout());
        JLabel label = new JLabel("TickeT", SwingConstants.CENTER);
        label.setName("TickeT");
        if (font != null) {
            System.out.println("here");
            font.deriveFont(200f);
            label.setFont(font);
        }
        centerPanel.add(label);

        JPanel topRightPanel = new JPanel();
        topRightPanel.setLayout(new FlowLayout());
        signInButton = new RoundedButton("sign_in", 20);
        signInButton.setName("sign_in");
        signInButton.setPreferredSize(new Dimension(100, 35));
        signUpButton = new RoundedButton("sign_up", 20);
        signUpButton.setName("sign_up");
        signUpButton.setPreferredSize(new Dimension(100, 35));

        topRightPanel.add(signInButton);
        topRightPanel.add(signUpButton);

        JPanel topPanel = new JPanel(new BorderLayout());

        topPanel.add(topRightPanel, BorderLayout.EAST);
        // Добавляем метку в окно
        add(centerPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
    }
}
