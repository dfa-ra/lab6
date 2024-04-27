package com.ra.server.Comands.AllCommands;


import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.Collection.CollectionManager;
import com.ra.server.Comands.UpperCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс команды вывода коллекции в текстовом виде в стандартный поток вывода
 * @author Захарченко Роман
 */
public class ShowCommand extends UpperCommand {
    private static final Logger logger = LogManager.getLogger(ShowCommand.class);
    CollectionManager cm = new CollectionManager();

    public ShowCommand() {
        super(new CommandType(0, false), "show", "Print to standard output all the elements of the collection in string representation");
    }

    @Override
    public Response execute(Request request) {
        logger.info("Collection shown");
        return new Response(cm.show(), "Done!\n");
    }

}
