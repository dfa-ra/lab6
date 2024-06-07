package com.ra.server.comands.allCommands.dbCommand;

import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.collection.dbManager.ConnectionBaseSQL;
import com.ra.server.collection.dbManager.DBManager;
import com.ra.server.comands.UpperCommand;

import java.sql.SQLException;

public class AddNewUserCommand extends UpperCommand {

    public AddNewUserCommand() {
        super(new CommandType(-1, false, false), "sign_up", "this command adds a new user");
    }

    @Override
    public Response execute(Request request) throws SQLException {
        Response response = new Response(true, new DBManager(ConnectionBaseSQL.getInstance().getConnection()).addUser(request.getLogin(), request.getPassword()));
        System.out.println(response.getAdditional());
        return response;
    }
}
