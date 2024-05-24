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
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

@Setter
@Getter
public class AddCommand extends UpperCommand {

    private static final Logger logger = LogManager.getLogger(AddCommand.class);
    CollectionManager cm = new CollectionManager();


    public AddCommand() {
        super(new CommandType(0, 0, true, new TicketForm(), new PersonForm(), false), "add", "display help on available commands");
    }

    @Override
    public Response execute(Request request){
        cm.add(request.getTicket(), request.getLogin(), request.getPassword());
        logger.info("Added ticket");
        return new Response("Done!");
    }
}
