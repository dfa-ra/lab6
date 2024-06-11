package com.ra.client;

import com.ra.client.controlers.StartController;
import com.ra.client.view.MainPageView;
import com.ra.client.view.StartView;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.MessageType;

import java.util.Locale;


public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }
    public static void main(String[] args) throws Exception {
        Sender.send(new Message(MessageType.INFO,"Start client"));
//        new StartView();
        new StartController(new StartView(), new Locale("en"));

//        InvokerClient invokerClient = new InvokerClient();
//        invokerClient.commandSelection();
    }
}
