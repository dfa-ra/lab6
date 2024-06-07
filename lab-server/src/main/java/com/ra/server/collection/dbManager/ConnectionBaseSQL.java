package com.ra.server.collection.dbManager;

import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.MessageType;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class ConnectionBaseSQL {
    private static ConnectionBaseSQL connectionBaseSQL;
    private Connection connection;

    private static final String URL = "jdbc:postgresql://localhost:5432/studs";
    private static final String USER = "ra";
    private static final String PASSWORD = "P@$SW0RD";

//    private static final String URL = "jdbc:postgresql://pg:5432/studs";
//    private static final String USER = "s408648";
//    private static final String PASSWORD = "VlUqYMUDQqmkOSRs";

    private ConnectionBaseSQL() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Sender.send(new Message(MessageType.INFO, "psql connect", "\n"));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static ConnectionBaseSQL getInstance(){
        if (connectionBaseSQL == null) {
            connectionBaseSQL = new ConnectionBaseSQL();
        }
        return connectionBaseSQL;
    }
}
