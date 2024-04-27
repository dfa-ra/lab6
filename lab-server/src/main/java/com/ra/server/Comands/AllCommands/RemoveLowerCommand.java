package com.ra.server.Comands.AllCommands;

import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.Collection.CollectionManager;
import com.ra.server.Comands.UpperClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс команды удаления из коллекции всех элементов, которые меньше чем заданный по id
 * @author Захарченко Роман
 */
public class RemoveLowerCommand extends UpperClass {

    private static final Logger logger = LogManager.getLogger(RemoveLowerCommand.class);
    CollectionManager cm = new CollectionManager();

    public RemoveLowerCommand() {super(new CommandType(1, false), "remove_lower", "remove all elements from the collection that are smaller than the given one");}

    @Override
    public Response execute(Request request) {
        try{
            if (Long.parseLong(request.getArgumentsCommand()) > 0) {
                String str = cm.removeLower(Long.parseLong(request.getArgumentsCommand()));
                logger.warn("Remove element lower id = " + request.getArgumentsCommand());
                return new Response(str);
            }
            else {
                logger.warn("id > 0");
                return new Response("Warning! id > 0");
            }
        }catch (NumberFormatException e) {
            logger.error(e.getMessage());
            return new Response("Argument type error. Try again!");
        }
    }
}
