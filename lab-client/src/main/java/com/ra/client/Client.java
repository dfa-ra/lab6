package com.ra.client;

import com.ra.client.command.InvokerClient;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.messageType;

public final class Client {
    public static String login = "Roman";
    public static String password = "rom(ZAH)2";
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws Exception {
        Sender.send(new Message(messageType.INFO,"Start client"));
        InvokerClient invokerClient = new InvokerClient();
        invokerClient.commandSelection();
    }
}
