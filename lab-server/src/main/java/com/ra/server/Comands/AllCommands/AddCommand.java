package com.ra.server.Comands.AllCommands;


import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.Collection.CollectionManager;
import com.ra.server.Comands.UpperClass;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Setter
@Getter
public class AddCommand extends UpperClass {

    private static final Logger logger = LogManager.getLogger(AddCommand.class);
    CollectionManager cm = new CollectionManager();

    public AddCommand() {
        super(new CommandType(0, 1, true), "add", "display help on available commands");
    }

    @Override
    public Response execute(Request request) {
        if(request.getArgumentsCommand().isEmpty()) {
            cm.add(1L, request.getTicket());
            logger.info("Added ticket");
            return new Response("Done!");
        }
        else {
            try{
                if (Long.parseLong(request.getArgumentsCommand()) > 0) {
                    cm.add(Long.parseLong(request.getArgumentsCommand()), request.getTicket());
                    logger.info("Added ticket");
                    return new Response("Done!");
                }
                else {
                    logger.warn("id > 0");
                    return new Response("Warning! id > 0");
                }
            }catch (NumberFormatException nfe) {
                logger.error(nfe.getMessage());
                return new Response("Argument type error. Try again!");
            }
        }
    }
}
