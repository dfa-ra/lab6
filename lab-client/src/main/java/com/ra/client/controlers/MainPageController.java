package com.ra.client.controlers;

import com.ra.client.command.InvokerClient;
import com.ra.client.controlers.mainPagePanelControllers.HomeController;
import com.ra.client.controlers.mainPagePanelControllers.SettingsController;
import com.ra.client.controlers.mainPagePanelControllers.UserController;
import com.ra.client.localizator.MainPageLocalization;
import com.ra.client.view.MainPageView;
import com.ra.client.view.StartView;
import com.ra.common.User;
import lombok.Setter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

public class MainPageController {
    private final InvokerClient invk = new InvokerClient();

    public MainPageController(MainPageView view, User user, Locale locale) throws IOException, ParseException {
        MainPageLocalization mainPageLocalization = new MainPageLocalization(view, locale);
        new HomeController(view.getHomePanel(), invk, user, view.getCardLayout(), view.getMainPanel());
        HomeController.setLocale(locale);
        new SettingsController(view.getSettingsPanel(), view.getCardLayout(), view.getMainPanel());
        new UserController(view.getUserPanel(), user,invk, view.getCardLayout(), view.getMainPanel());

        view.getSettingsPanel().getLanguageComboBox().addActionListener(e -> {
            mainPageLocalization.updateLocation();
            HomeController.setLocale(mainPageLocalization.getLocale());
            view.getHomePanel().revalidate();
            view.getHomePanel().repaint();
            System.out.println(mainPageLocalization.getLocale());
        });
        view.getUserPanel().getSignOutButton().addActionListener(e -> {
            System.out.println(mainPageLocalization.getLocale());
            try {
                new StartController(new StartView(), mainPageLocalization.getLocale());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            view.dispose();
        });
    }
}
