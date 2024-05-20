package com.ra.server.collection.dbManager;

import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.messageType;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class ConnectionBaseSQL {
    private static ConnectionBaseSQL connectionBaseSQL;
    private final Connection connection;

    private static final String URL = "jdbc:postgresql://localhost:5432/studs";
    private static final String USER = "ra";
    private static final String PASSWORD = "P@$SW0RD";

    private ConnectionBaseSQL() throws SQLException {
        this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        Sender.send(new Message(messageType.INFO, "psql connect", "\n"));
    }

    public static ConnectionBaseSQL getInstance() throws SQLException {
        if (connectionBaseSQL == null) {
            connectionBaseSQL = new ConnectionBaseSQL();
        }
        return connectionBaseSQL;
    }
}
