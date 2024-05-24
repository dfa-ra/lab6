package com.ra.client;


import com.ra.client.Utils.Config;
import com.ra.common.ToBytes;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.MessageType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class Handler {

    private final DatagramSocket socket;
    private final InetAddress sreverAddress;

    public Handler(String address) throws UnknownHostException, SocketException {
        socket = new DatagramSocket();
        sreverAddress = InetAddress.getByName(address);
    }

    public boolean connect(int port){
        try{
            socket.connect(sreverAddress, port);
            return socket.isConnected();
        } catch (IllegalArgumentException e){
            Sender.send(new Message(MessageType.ERROR,"Port out of range"));
            return false;
        }
    }

    public void closeConnect() {
        socket.close();
    }

    public void sendRequest(Request request){
        try {
            byte[] data = ToBytes.toBytes(request);
            DatagramPacket packet = new DatagramPacket(data, data.length);
            // Отправка дейтаграммы через сокет
            socket.send(packet);

        } catch (Exception e) {
            Sender.send(new Message(MessageType.ERROR,"Error sending request"));
        }
    }

    public Response dataReception() {
        try {
            int BUFFER_SIZE = 1048576;
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.setSoTimeout(Config.getRESPONSE_TIMEOUT_VALUE());
            socket.receive(packet);
            return validResponse(packet);
        } catch (Exception e) {
            return null;
        }
    }
    public Response validResponse(DatagramPacket packet) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (Response) ois.readObject();
    }
}
