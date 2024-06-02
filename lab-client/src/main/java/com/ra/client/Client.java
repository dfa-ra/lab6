package com.ra.client;

import com.ra.client.command.InvokerClient;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.MessageType;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }
    public static void main(String[] args) throws Exception {
        Sender.send(new Message(MessageType.INFO,"Start client"));
        System.out.println("8 чудо лаба");
        InvokerClient invokerClient = new InvokerClient();
        invokerClient.commandSelection();
    }
}
