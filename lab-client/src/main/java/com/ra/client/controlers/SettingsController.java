package com.ra.client.controlers;

import com.ra.client.view.SettingsView;

public class SettingsController {
    private final SettingsView view;

    public SettingsController(SettingsView view) {
        this.view = view;
        action();
    }

    private void action(){
        view.getHomeButton().addActionListener(e -> {
            view.goInvisible();
        });
        view.getUserButton().addActionListener(e -> {
            view.goInvisible();
        });
    }

    public void setVisible(){
        view.goVisible();
    }
}
