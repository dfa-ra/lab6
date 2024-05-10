package com.ra.server.comands.allCommands;


import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.collection.CollectionManager;
import com.ra.server.comands.UpperCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс команды сгруппировки элементов коллекции по значению поля type и вывода количества элементов в каждой группе
 * @author Захарченко Роман
 */
public class GroupCountingByTypeCommand extends UpperCommand {

    private static final Logger logger = LogManager.getLogger(GroupCountingByTypeCommand.class);
    CollectionManager cm = new CollectionManager();

    public GroupCountingByTypeCommand() {
        super(new CommandType(0, false), "group_counting_by_type", "group the collection elements by the value of the type field, display the number of elements in each group");
    }

    @Override
    public Response execute(Request request) {
        String str = cm.groupCountingByType();
        logger.info("Done!");
        return new Response(str);
    }
}
