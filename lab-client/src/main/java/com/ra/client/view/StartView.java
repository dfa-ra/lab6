package com.ra.client.view;

import com.ra.client.view.sours.RoundedButton;
import com.ra.client.view.startPanels.SignInPanel;
import com.ra.client.view.startPanels.SignUpPanel;
import com.ra.client.view.startPanels.StartPanel;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Getter
public class StartView extends JFrame {
    private final JPanel     mainPanel;
    private final CardLayout cardLayout;
    private final SignUpPanel signUpPanel;
    private final SignInPanel signInPanel;
    private final StartPanel startPanel;



    public StartView(){
        setTitle("Ticket");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1980, 1080);

        // Создаем метку
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        signInPanel = new SignInPanel(cardLayout);
        signUpPanel = new SignUpPanel(cardLayout);
        startPanel = new StartPanel(cardLayout);

        mainPanel.add(startPanel, "start");
        mainPanel.add(signInPanel, "signIn");
        mainPanel.add(signUpPanel, "signUp");
        System.out.println(Arrays.toString(mainPanel.getComponents()));
        add(mainPanel);
        setVisible(true);
    }
}
