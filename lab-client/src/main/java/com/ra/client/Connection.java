package com.ra.client;

import com.ra.client.Utils.Config;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.MessageType;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Connection {
    private final Scanner in = new Scanner(System.in);
    private Handler connectHendler;

    public Handler connection() throws IOException {
        connectHendler = setHost();
        setPort();
        return connectHendler;
    }

    private Handler setHost() throws SocketException, UnknownHostException {
        try{
            Sender.send(new Message(MessageType.INPUT,"Enter host: ", ""));
            return new Handler(in.next());
        } catch (UnknownHostException e) {
            Sender.send(new Message(MessageType.ERROR,"Unknown host"));
            setHost();
        } catch (SocketException e) {
            Sender.send(new Message(MessageType.ERROR,"Address do not found"));
            setHost();
        }
        return null;
    }

    private void setPort(){
        int port;
        try {
            Sender.send(new Message(MessageType.INPUT,"Entered port: ", ""));
            port = Integer.parseInt(in.next());
            if (connectHendler != null) {
                if (port < Config.getMAX_PORT_VALUE() && port > 0 && connectHendler.connect(port));
                else {
                    Sender.send(new Message(MessageType.WARNING,"Incorrect port number"));
                    setPort();
                }
            } else {
                Sender.send(new Message(MessageType.INFO,"Port busy"));
                setPort();
            }
        } catch (NumberFormatException e) {
            Sender.send(new Message(MessageType.ERROR,"Incorrect port type."));
            setPort();
        }

    }

}
