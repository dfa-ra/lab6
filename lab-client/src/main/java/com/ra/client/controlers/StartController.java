package com.ra.client.controlers;

import com.ra.client.Connection;
import com.ra.client.Handler;
import com.ra.client.view.SignInView;
import com.ra.client.view.SignUpView;
import com.ra.client.view.StartView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StartController {
    private Handler connectHendler = new Connection().connection();

    public StartController(StartView view) throws IOException {
        view.getSignInButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.goInvisible();
                new SignInController(new SignInView(), connectHendler);
            }
        });
        view.getSignUpButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.goInvisible();
                new SignUpController(new SignUpView(), connectHendler);
            }
        });
    }
}
