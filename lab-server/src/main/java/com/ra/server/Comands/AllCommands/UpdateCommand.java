package com.ra.server.Comands.AllCommands;


import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.Collection.CollectionManager;
import com.ra.server.Comands.UpperCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс команды изменения элемента коллекции по его id.
 * @author Захарченко Роман
 */
public class UpdateCommand extends UpperCommand {
    private static final Logger logger = LogManager.getLogger(UpperCommand.class);
    CollectionManager cm = new CollectionManager();

    public UpdateCommand() {super(new CommandType(1, true), "update id", "update the value of a collection element whose id is equal to the given one");}

    @Override
    public Response execute(Request request) {
        try {
            if (Long.parseLong(request.getArgumentsCommand()) > 0) {
                String str = cm.update(Long.parseLong(request.getArgumentsCommand()), request.getTicket());
                logger.info("Update element with id" + request.getArgumentsCommand());
                return new Response(str);
            }
            else {
                logger.warn("id > 0");
                return new Response("Warning! id > 0");
            }
        }catch (NumberFormatException e) {
            logger.error(e.getMessage());
            return new Response("Argument error. Try again!");
        }
    }
}
