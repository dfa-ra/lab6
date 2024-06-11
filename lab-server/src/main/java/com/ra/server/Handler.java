package com.ra.server;

import com.ra.common.ToBytes;
import com.ra.common.User;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.MessageType;
import com.ra.server.comands.Invoker;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Handler {
    private static final int BUFFER_SIZE = 1048576;
    private static final int THREAD_POOL_SIZE = 5;

    private final DatagramChannel channel;
    private User sreverUser = new User("Aliisthebestpra", "ctitioner", "", "", "");
    Invoker invk = new Invoker();

    private final ExecutorService readThreadPool;
    private final ExecutorService processThreadPool;
    private final ExecutorService sendThreadPool;


    public Handler() throws IOException {
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        int port;
        try {
            port = Integer.parseInt(System.getenv("LAB6SERVERPORT"));
        }catch (NumberFormatException e){
            Sender.send(new Message(MessageType.ERROR, "Invalid port number"));
            Sender.send(new Message(MessageType.WARNING, "Start on 1095 port"));
            port = 1095;
        }
        InetSocketAddress socketAddress = new InetSocketAddress(port);
        channel.bind(socketAddress);

        readThreadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        processThreadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        sendThreadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public class ReadThread implements Runnable {

        @Override
        public void run() {
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            while (true){
                buffer.clear();
                InetSocketAddress senderAddress;
                try {
                    senderAddress = (InetSocketAddress) channel.receive(buffer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (senderAddress != null) {
                    Sender.send(new Message (MessageType.INFO, "Server received: " + senderAddress));
                    buffer.flip();
                    byte[] data = new byte[buffer.remaining()];
                    buffer.get(data);
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    processThreadPool.submit(new ProcessThread(packet, senderAddress));
                }
            }
        }
    }

    public class ProcessThread implements Runnable{

        Response response;
        DatagramPacket packet;
        InetSocketAddress socketAddress;
        public ProcessThread(DatagramPacket packet, InetSocketAddress socketAddress){
            this.packet = packet;
            this.socketAddress = socketAddress;
        }
        @Override
        public void run() {
            try {
                response = validResponse(packet);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println(response.getInfoCommand());
            sendThreadPool.submit(new SendThread(response, socketAddress));
        }
    }

    public class SendThread implements Runnable{

        Response response;

        InetSocketAddress senderAddress;

        public SendThread(Response response, InetSocketAddress senderAddress){
            this.response = response;
            this.senderAddress = senderAddress;
        }

        @Override
        public void run() {
            try {
                System.out.println("=======");
                System.out.println(response.getAdditional());
                System.out.println("=======");
                channel.send(ByteBuffer.wrap(ToBytes.toBytes(response)), senderAddress);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void dataReceptionAndSend() throws Exception {
//
//        readThreadPool.submit(new ReadThread());

        new Thread(new ReadThread()).start();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


        new Thread(() -> {
            while (true)
            {
                try {
                    if (in.ready()) {
                        String command = in.readLine();
                        Sender.send(new Message(MessageType.INPUT, "Server command: " + command));
                        Request request = new Request(command, "");
                        request.setUser(sreverUser);
                        invk.commandSelectionByStr(request, false);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }


    private Response validResponse(DatagramPacket packet) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData());
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        Request request = (Request) obj;
        ois.close();
        if (request.getNameCommand().equals("getAllCommand")) {
            return new Response(true,invk.getAllCommand());
        } else{
            Sender.send(new Message(MessageType.INFO, "The server executes the command '" + request.getNameCommand() + "' sent from the client "));
            return invk.commandSelectionByStr(request, true);
        }

    }
}