package com.ra.server.Comands.AllCommands;

import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.Collection.CollectionManager;
import com.ra.server.Comands.UpperClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс команды сохранения коллекции
 * @author Захарченко Роман
 */
public class SaveCommand extends UpperClass {

    private static final Logger logger = LogManager.getLogger(SaveCommand.class);
    CollectionManager cm = new CollectionManager();

    public SaveCommand() {super(new CommandType(-1, false), "","");}
    @Override
    public Response execute(Request request) throws Exception {
        cm.saveCollection();
        logger.info("Saved collection");
        return new Response("Collection Saved!");
    }
}
