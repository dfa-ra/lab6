package com.ra.server.comands.allCommands;


import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.common.forms.Form;
import com.ra.common.forms.HelpForm;
import com.ra.common.forms.typeForms.PersonForm;
import com.ra.common.forms.typeForms.TicketForm;
import com.ra.server.collection.CollectionManager;
import com.ra.server.comands.UpperCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс команды изменения элемента коллекции по его id.
 * @author Захарченко Роман
 */
public class UpdateCommand extends UpperCommand {
    private static final Logger logger = LogManager.getLogger(UpperCommand.class);
    CollectionManager cm = new CollectionManager();
    static private Form form = new Form(new TicketForm());

    static {
        form.setHelpForm(new PersonForm());
    }

    public UpdateCommand() {
        super(new CommandType(1, true,  new TicketForm(), new PersonForm(), true), "update id", "update the value of a collection element whose id is equal to the given one");}

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
