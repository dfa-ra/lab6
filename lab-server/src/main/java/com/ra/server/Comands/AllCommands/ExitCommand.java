package com.ra.server.Comands.AllCommands;

import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.Collection.CollectionManager;
import com.ra.server.Comands.UpperCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс команды выхода
 * @author Захарченко Роман
 */
public class ExitCommand extends UpperCommand {

    private static final Logger logger = LogManager.getLogger(ExitCommand.class);

    public ExitCommand() {super(new CommandType(-1, false), "", "");}
    CollectionManager cm = new CollectionManager();
    @Override
    public Response execute(Request request) throws Exception {
        cm.saveCollection();
        logger.info("Server closed");
        System.exit(0);
        return new Response("");
    }
}
