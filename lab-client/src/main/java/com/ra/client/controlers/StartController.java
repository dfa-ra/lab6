package com.ra.client.controlers;

import com.ra.client.Connection;
import com.ra.client.Handler;
import com.ra.client.localizator.StartLocalization;
import com.ra.client.view.MainPageView;
import com.ra.client.view.StartView;
import com.ra.common.User;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.common.message.Message;
import com.ra.common.message.MessageType;
import com.ra.common.message.Sender;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

public class StartController {
    private final Handler connectHendler = new Connection().connection();
    private final User user = new User();
    public StartController(StartView view, Locale locale) throws IOException {

        System.out.println(view.getSignUpPanel().getUsernameLabel().getName());
        new StartLocalization(view, locale);
        view.getStartPanel().getSignInButton().addActionListener(e -> {
            view.getCardLayout().show(view.getMainPanel(), "signIn");
        });
        view.getStartPanel().getSignUpButton().addActionListener(e -> {
            view.getCardLayout().show(view.getMainPanel(), "signUp");
        });
        view.getSignUpPanel().getLoginButton().addActionListener(e -> {
            Response response;
            String login = view.getSignUpPanel().getUsernameField().getText();
            String password = new String(view.getSignUpPanel().getPasswordField().getPassword());
            String name = view.getSignUpPanel().getNameField().getText();
            String surname = view.getSignUpPanel().getSurnameField().getText();
            String email = view.getSignUpPanel().getEmailField().getText();

            Request request = new Request("sign_up", "");
            request.setUser(new User(login, password, name, surname, email));
            connectHendler.sendRequest(request);
            response = connectHendler.dataReception();
            if (response.getAdditional().equals("ok")) {
                view.getSignUpPanel().getLoginButton().setBackground(new Color(37, 230, 37, 40));
                user.setLogin(login);
                user.setPassword(password);
                try {
                    new MainPageController(new MainPageView(), user, locale);
                } catch (IOException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
                view.dispose();
            }
            else {
                Sender.send(new Message(MessageType.WARNING, response.getAdditional(), "\n"));
                view.getSignUpPanel().getLoginButton().setBackground(new Color(230, 37, 37, 40));
            }
        });
        view.getSignInPanel().getLoginButton().addActionListener(e -> {
            Response response;
            String login = view.getSignInPanel().getUsernameField().getText();
            String password = new String(view.getSignInPanel().getPasswordField().getPassword());

            Request request = new Request("sign_in", "");
            request.setUser(new User(login, password));
            connectHendler.sendRequest(request);
            response = connectHendler.dataReception();
            if (response.getAdditional().equals("ok")) {
                view.getSignInPanel().getLoginButton().setBackground(new Color(37, 230, 37, 40));
                user.setLogin(login);
                user.setPassword(password);
                try {
                    new MainPageController(new MainPageView(), user, locale);
                } catch (IOException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
                view.dispose();
            }
            else {
                Sender.send(new Message(MessageType.WARNING, response.getAdditional(), "\n"));
                view.getSignInPanel().getLoginButton().setBackground(new Color(230, 37, 37, 40));
            }
        });
    }
}
