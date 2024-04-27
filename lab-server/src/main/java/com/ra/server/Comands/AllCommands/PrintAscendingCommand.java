package com.ra.server.Comands.AllCommands;

import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.Collection.CollectionManager;
import com.ra.server.Comands.UpperClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс команды вывода элементов коллекции в порядке возрастания id
 * @author Захарченко Роман
 */
public class PrintAscendingCommand extends UpperClass {
    private static final Logger logger = LogManager.getLogger(PrintAscendingCommand.class);
    CollectionManager cm = new CollectionManager();

    public PrintAscendingCommand() {
        super(new CommandType(0, false),"print_ascending", "Print the collection elements in ascending order");
    }

    @Override
    public Response execute(Request request) {
        String str = cm.printAscending();
        logger.info("Done");
        return new Response(str);
    }
}
