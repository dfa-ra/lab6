package com.ra.client.controlers.mainPagePanelControllers;

import com.ra.client.command.InvokerClient;
import com.ra.client.view.mainPagePanels.UserPanel;
import com.ra.common.User;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;

import javax.swing.*;
import java.awt.*;

public class UserController {
    private final UserPanel view;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    public UserController(UserPanel view, User user, InvokerClient invk, CardLayout cardLayout, JPanel mainPanel) {
        this.view = view;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        Request request = new Request("get_user_by_login_and_password");
        request.setUser(user);
        invk.getConnectHendler().sendRequest(request);
        Response response = invk.getConnectHendler().dataReception();
        String[] str = response.getAdditional().split(" ");
        view.getLogin().setText(user.getLogin());
        view.getUserName().setText(str[0]);
        view.getSurname().setText(str[1]);
        view.getEmail().setText(str[2]);
        action();
    }

    private void action(){
        view.getHomeButton().addActionListener(e -> {
            cardLayout.show(mainPanel, "home");
        });
        view.getSettingsButton().addActionListener(e -> {
            cardLayout.show(mainPanel, "settings");
        });
    }

}

