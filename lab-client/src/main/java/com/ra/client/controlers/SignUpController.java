package com.ra.client.controlers;

import com.ra.client.Handler;
import com.ra.client.view.Home;
import com.ra.client.view.SignUpView;
import com.ra.common.User;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.common.message.Message;
import com.ra.common.message.MessageType;
import com.ra.common.message.Sender;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

public class SignUpController {
    private User user = new User();

    public SignUpController(SignUpView view, Handler handler) {
        view.getLoginButton().addActionListener(e -> {
            Response response;
            String login = view.getUsernameField().getText();
            String password = new String(view.getPasswordField().getPassword());

            Request request = new Request("sign_up", "");
            request.setUser(new User(login, password));
            handler.sendRequest(request);
            response = handler.dataReception();
            if (response.getAdditional().equals("ok")) {
                view.getLoginButton().setBackground(new Color(37, 230, 37, 40));
                user.setLogin(login);
                user.setPassword(password);
                view.goInvisible();
                try {
                    new HomeController(new Home(), handler, user);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else {
                Sender.send(new Message(MessageType.WARNING, response.getAdditional(), "\n"));
                view.getLoginButton().setBackground(new Color(230, 37, 37, 40));
            }
        });
    }
}
