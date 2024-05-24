package com.ra.server.comands.allCommands;

import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.collection.CollectionManager;
import com.ra.server.comands.UpperCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Класс команды удаления из коллекции всех элементов, которые меньше чем заданный по id
 * @author Захарченко Роман
 */
public class RemoveLowerCommand extends UpperCommand {

    private static final Logger logger = LogManager.getLogger(RemoveLowerCommand.class);
    CollectionManager cm = new CollectionManager();

    public RemoveLowerCommand() {super(new CommandType(1, false, false), "remove_lower", "remove all elements from the collection that are smaller than the given one");}

    @Override
    public Response execute(Request request) {
        try{
            if (Long.parseLong(request.getArgumentsCommand()) > 0) {
                String str = cm.removeLower(Long.parseLong(request.getArgumentsCommand()), request.getLogin(), request.getPassword());
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
