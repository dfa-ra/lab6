package com.ra.server;

import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.messageType;
import com.ra.server.collection.dbManager.ConnectionBaseSQL;
import com.ra.server.collection.dbManager.DBManager;
import com.ra.server.collection.dbManager.XmlManager;
import com.ra.server.comands.Invoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Server {
    public static final Logger logger = LogManager.getLogger(Server.class);

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        try {
            Sender.send(new Message(messageType.INFO, "Server started"));
            logger.info("Starting server...");

            XmlManager.myParser();
            Invoker invk = new Invoker();
            Handler handler = new Handler();

            new DBManager(ConnectionBaseSQL.getInstance().getConnection()).dbInit();

            handler.dataReceptionAndSend();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
