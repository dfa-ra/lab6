package com.ra.server.comands.allCommands.dbCommand;

import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.collection.dbManager.ConnectionBaseSQL;
import com.ra.server.collection.dbManager.DBManager;
import com.ra.server.comands.UpperCommand;

public class GetUserByLoginAndPasswordCommand extends UpperCommand {
    public GetUserByLoginAndPasswordCommand() {
        super(new CommandType(-1, false, false), "get_user_by_login_and_password", "");
    }

    @Override
    public Response execute(Request request) throws Exception {
        System.out.println("here");
        return new Response(true, new DBManager(ConnectionBaseSQL.getInstance().getConnection()).getUser(request.getLogin(), request.getPassword()));
    }
}