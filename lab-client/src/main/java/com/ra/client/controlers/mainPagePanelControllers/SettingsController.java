package com.ra.client.controlers.mainPagePanelControllers;

import com.ra.client.view.mainPagePanels.SettingsPanel;

import javax.swing.*;
import java.awt.*;

public class SettingsController {
    private final SettingsPanel view;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    public SettingsController(SettingsPanel view, CardLayout cardLayout, JPanel mainPanel) {
        this.view = view;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        action();
    }

    private void action(){
        view.getHomeButton().addActionListener(e -> {
            cardLayout.show(mainPanel, "home");
        });
        view.getUserButton().addActionListener(e -> {
            cardLayout.show(mainPanel, "user");
        });
    }

//
//    public void setVisible(){
//        view.goVisible();
//    }
}
