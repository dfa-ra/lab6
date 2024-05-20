package com.ra.server.comands.allCommands;


import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.collection.CollectionManager;
import com.ra.server.comands.UpperCommand;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

@Setter
@Getter
public class AddCommand extends UpperCommand {

    private static final Logger logger = LogManager.getLogger(AddCommand.class);
    CollectionManager cm = new CollectionManager();

    public AddCommand() {
        super(new CommandType(0, 1, true), "add", "display help on available commands");
    }

    @Override
    public Response execute(Request request) throws SQLException {
        if(request.getArgumentsCommand().isEmpty()) {
            cm.add(1L, request.getTicket(), request.getLogin(), request.getPassword());
            logger.info("Added ticket");
            return new Response("Done!");
        }
        else {
            try{
                if (Long.parseLong(request.getArgumentsCommand()) > 0) {
                    cm.add(Long.parseLong(request.getArgumentsCommand()), request.getTicket(), request.getLogin(), request.getPassword());
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
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
