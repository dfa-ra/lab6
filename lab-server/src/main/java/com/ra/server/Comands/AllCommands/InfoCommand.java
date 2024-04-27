package com.ra.server.Comands.AllCommands;

import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.Collection.CollectionManager;
import com.ra.server.Comands.UpperCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс команды вывода информации о коллекции
 * @author Захарченко Роман
 */
public class InfoCommand extends UpperCommand {
    private static final Logger logger = LogManager.getLogger(InfoCommand.class);
    CollectionManager cm = new CollectionManager();

    public InfoCommand() {
        super(new CommandType(0, false), "info", "print information about the collection (type, initialization date, number of elements, etc.) to standard output");
    }

    @Override
    public Response execute(Request request) {
        String str = cm.info();
        logger.info("Done");
        return new Response(str);
    }
}
