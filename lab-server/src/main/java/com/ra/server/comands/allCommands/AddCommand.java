package com.ra.server.comands.allCommands;


import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.common.forms.typeForms.TicketForm;
import com.ra.common.message.Message;
import com.ra.common.message.MessageType;
import com.ra.common.message.Sender;
import com.ra.server.collection.CollectionManager;
import com.ra.server.comands.UpperCommand;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Setter
@Getter
public class AddCommand extends UpperCommand {

    private static final Logger logger = LogManager.getLogger(AddCommand.class);
    CollectionManager cm = new CollectionManager();


    public AddCommand() {
        super(new CommandType(0, 0, true, new TicketForm(), null, false), "add", "display help on available commands");
    }

    @Override
    public Response execute(Request request){
        logger.info("Added ticket");
        Sender.send(new Message(MessageType.INFO, "add", "\n"));
        return new Response(true, String.valueOf(cm.add(request.getTicket(), request.getLogin(), request.getPassword())));
    }
}
