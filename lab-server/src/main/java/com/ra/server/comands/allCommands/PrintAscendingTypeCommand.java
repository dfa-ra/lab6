package com.ra.server.comands.allCommands;

import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.collection.CollectionManager;
import com.ra.server.comands.UpperCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс вывода значения поля type всех элементов в порядке убывания
 */
public class PrintAscendingTypeCommand extends UpperCommand {
    private static final Logger logger = LogManager.getLogger(PrintAscendingTypeCommand.class);
    CollectionManager cm = new CollectionManager();

    public PrintAscendingTypeCommand() {super(new CommandType(0, false, false), "print_ascending_type", "print the type field values of all elements in descending order");}

    @Override
    public Response execute(Request request) throws Exception {
        logger.info("Done!");
        return new Response(true, cm.printFieldDescendingType(), "Done\n");
    }
}