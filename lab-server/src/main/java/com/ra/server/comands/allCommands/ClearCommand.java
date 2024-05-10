package com.ra.server.comands.allCommands;


import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.collection.CollectionManager;
import com.ra.server.comands.UpperCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс команды отчистки всей коллекции
 * @author Захарченко Роман
 */
public class ClearCommand extends UpperCommand {
    CollectionManager cm = new CollectionManager();
    private static final Logger logger = LogManager.getLogger(ClearCommand.class);

    public ClearCommand() {
        super(new CommandType(0, false), "clear", "clear the collection");
    }

    @Override
    public Response execute(Request request) {
        cm.clear();
        logger.info("Collection cleared");
        return new Response("Done!");
    }
}
