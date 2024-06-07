package com.ra.client.view;

import com.ra.client.controlers.SignInController;
import com.ra.client.view.sours.RoundedButton;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

@Getter
public class StartView extends JFrame {

    private final JButton signInButton;
    private final JButton signUpButton;

    public StartView(){
        setTitle("Ticket");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1980, 1080);

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
        if (font != null) {
            System.out.println("here");
            font.deriveFont(200f);
            label.setFont(font);
        }
        centerPanel.add(label);

        JPanel topRightPanel = new JPanel();
        topRightPanel.setLayout(new FlowLayout());
        signInButton = new RoundedButton("sign in", 20);
        signInButton.setPreferredSize(new Dimension(100, 35));
        signUpButton = new RoundedButton("sign up", 20);
        signUpButton.setPreferredSize(new Dimension(100, 35));

        topRightPanel.add(signInButton);
        topRightPanel.add(signUpButton);

        JPanel topPanel = new JPanel(new BorderLayout());

        topPanel.add(topRightPanel, BorderLayout.EAST);
        // Добавляем метку в окно
        add(centerPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        setVisible(true);
    }

    public void goInvisible(){
        setVisible(false);
    }

}
