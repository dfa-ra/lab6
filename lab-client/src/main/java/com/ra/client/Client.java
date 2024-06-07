package com.ra.client;

import com.ra.client.command.InvokerClient;
import com.ra.client.controlers.HomeController;
import com.ra.client.controlers.StartController;
import com.ra.client.view.*;
import com.ra.common.User;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.MessageType;

import javax.swing.*;


public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }
    public static void main(String[] args) throws Exception {
        Sender.send(new Message(MessageType.INFO,"Start client"));
        new UserView();
//        new StartController(new StartView());
//        InvokerClient invokerClient = new InvokerClient();
//        invokerClient.commandSelection();
    }
}
