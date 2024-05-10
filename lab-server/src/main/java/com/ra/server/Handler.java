package com.ra.server;

import com.ra.common.ToBytes;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.messageType;
import com.ra.server.comands.Invoker;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Handler {
    private final DatagramChannel channel;
    private final int BUFFER_SIZE = 65536;
    Invoker invk = new Invoker();

    public Handler() throws IOException {
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        int port;
        try {
            port = Integer.parseInt(System.getenv("LAB6SERVERPORT"));
        }catch (NumberFormatException e){
            Sender.send(new Message(messageType.ERROR, "Invalid port number"));
            Sender.send(new Message(messageType.WARNING, "Start on 1095 port"));
            port = 1095;
        }
        InetSocketAddress socketAddress = new InetSocketAddress(port);
        channel.bind(socketAddress);
    }

    public void dataReceptionAndSend() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        long startTime = System.currentTimeMillis();
        while (true) {
            buffer.clear();
            InetSocketAddress senderAddress = (InetSocketAddress) channel.receive(buffer);
            if (senderAddress != null) {
                Sender.send(new Message (messageType.INFO, "Server received: " + senderAddress));
                buffer.flip();
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                DatagramPacket packet = new DatagramPacket(data, data.length);
                channel.send(ByteBuffer.wrap(ToBytes.toBytes(validResponse(packet))), senderAddress);
            }
            while ((System.currentTimeMillis() - startTime) < 1000 && !in.ready()) {}
            if (in.ready()) {
                String command = in.readLine();

                Sender.send(new Message(messageType.INPUT, "Server command: " + command));
                invk.commandSelectionByStr(new Request(command, ""), false);
            }
        }
    }


    private Response validResponse(DatagramPacket packet) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData());
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        Request request = (Request) obj;
        ois.close();
        if (request.getNameCommand().equals("getAllCommand")) {
            return new Response(invk.getAllCommand());
        } else{
            Sender.send(new Message(messageType.INFO, "The server executes the command '" + request.getNameCommand() + "' sent from the client "));
            return invk.commandSelectionByStr(request, true);
        }

    }
}