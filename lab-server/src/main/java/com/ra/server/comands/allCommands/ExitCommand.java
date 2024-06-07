package com.ra.server.comands.allCommands;

import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.collection.CollectionManager;
import com.ra.server.comands.UpperCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс команды выхода
 * @author Захарченко Роман
 */
public class ExitCommand extends UpperCommand {

    private static final Logger logger = LogManager.getLogger(ExitCommand.class);

    public ExitCommand() {super(new CommandType(-1, false, false), "", "");}
    CollectionManager cm = new CollectionManager();
    @Override
    public Response execute(Request request) throws Exception {
        logger.info("Server closed");
        System.exit(0);
        return new Response(true, "");
    }
}
