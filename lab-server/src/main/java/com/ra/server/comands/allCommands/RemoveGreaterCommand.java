package com.ra.server.comands.allCommands;

import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.collection.CollectionManager;
import com.ra.server.comands.UpperCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс команды удаления из коллекции всех элементов, превышающие заданный по id
 * @author Захарченко Роман
 */
public class RemoveGreaterCommand extends UpperCommand {

    private static final Logger logger = LogManager.getLogger(RemoveGreaterCommand.class);
    CollectionManager cm = new CollectionManager();

    public RemoveGreaterCommand() {super(new CommandType(1, false, false), "remove_greater", "remove from the collection all elements greater than the given one");}

    @Override
    public Response execute(Request request) {
        try{
            if (Long.parseLong(request.getArgumentsCommand()) > 0) {
                boolean str = cm.removeGreater(Long.parseLong(request.getArgumentsCommand()), request.getLogin(), request.getPassword());
                logger.info("Remove element greater id = " + request.getArgumentsCommand());
                if (str)
                    return new Response(true);
                else
                    return new Response(false, "No IDs were found that matched the requirements. Try again!");

            }
            else {
                logger.warn("id > 0");
                return new Response(false, "Warning! id > 0");
            }
        }catch (NumberFormatException e) {
            logger.error(e.getMessage());
            return new Response(false,"Argument type error. Try again!");
        }
    }
}
