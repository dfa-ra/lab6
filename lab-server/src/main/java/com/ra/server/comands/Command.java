package com.ra.server.comands;

import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;

/**
 * Интерфейс команд
 * @author Захарченко Роман
 */

public interface Command {

    Response execute(Request request) throws Exception;
    CommandType getCommandType();
    String getName();
    String getInfo();
}
