package com.ra.server.comands.allCommands;

import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.common.message.Message;
import com.ra.common.message.MessageType;
import com.ra.common.message.Sender;
import com.ra.server.collection.CollectionManager;
import com.ra.server.comands.UpperCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс команды удаления элемента из коллекции по его id
 * @author Захарченко Роман
 */
public class RemoveByIdCommand extends UpperCommand {
    private static final Logger logger = LogManager.getLogger(RemoveByIdCommand.class);
    CollectionManager cm = new CollectionManager();

    public RemoveByIdCommand() {super(new CommandType(1, false, true), "remove_by_id", "remove an element from a collection by its id");}

    @Override
    public Response execute(Request request) {
        try{
            if (Long.parseLong(request.getArgumentsCommand()) > 0) {

                boolean str = cm.removeById(Long.parseLong(request.getArgumentsCommand()), request.getLogin(), request.getPassword());
                Sender.send(new Message(MessageType.INFO, "Remove element with id " + request.getArgumentsCommand() + " " + str, "\n"));
                logger.info("Remove element with id " + request.getArgumentsCommand());
                return new Response(str);
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
