package com.ra.client.view;

import com.ra.client.view.mainPagePanels.HomePanel;
import com.ra.client.view.mainPagePanels.SettingsPanel;
import com.ra.client.view.mainPagePanels.UserPanel;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class MainPageView extends JFrame {
    private final HomePanel homePanel;
    private final SettingsPanel settingsPanel;
    private final UserPanel userPanel;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    public MainPageView() {
        setTitle("Ra Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1980, 1080);

        cardLayout = new CardLayout();

        mainPanel = new JPanel(cardLayout);

        homePanel = new HomePanel(cardLayout);
        settingsPanel = new SettingsPanel(cardLayout);
        userPanel = new UserPanel(cardLayout);

        mainPanel.add(homePanel, "home");
        mainPanel.add(userPanel, "user");
        mainPanel.add(settingsPanel, "settings");
        add(mainPanel);

        setVisible(true);
    }
}
