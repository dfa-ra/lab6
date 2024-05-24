package com.ra.server.comands.allCommands.dbCommand;

import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.collection.dbManager.ConnectionBaseSQL;
import com.ra.server.collection.dbManager.DBManager;
import com.ra.server.comands.UpperCommand;

public class CheckTicketUserCommand extends UpperCommand {
    public CheckTicketUserCommand() {
        super(new CommandType(-1, false, false), "check_permission_update", "this command adds a new user");
    }

    @Override
    public Response execute(Request request) throws Exception {
        return new Response(new DBManager(ConnectionBaseSQL.getInstance().getConnection()).checkTicketUser(Integer.parseInt(request.getArgumentsCommand()), request.getLogin(), request.getPassword()));
    }
}
