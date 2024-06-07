package com.ra.server.comands.allCommands.dbCommand;

import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.collection.dbManager.ConnectionBaseSQL;
import com.ra.server.collection.dbManager.DBManager;
import com.ra.server.comands.Command;
import com.ra.server.comands.UpperCommand;

public class CheckExistsUserCommand extends UpperCommand {
    public CheckExistsUserCommand() {
        super(new CommandType(-1, false, false), "sign_in", "this command adds a new user");
    }

    @Override
    public Response execute(Request request) throws Exception {
       return new Response(true, new DBManager(ConnectionBaseSQL.getInstance().getConnection()).checkExistsUser(request.getLogin(), request.getPassword()));
    }
}
