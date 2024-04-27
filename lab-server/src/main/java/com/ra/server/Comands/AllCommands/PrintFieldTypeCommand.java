package com.ra.server.Comands.AllCommands;


import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.Collection.CollectionManager;
import com.ra.server.Comands.UpperClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс команды вывода id всех типов билетов по группам
 * @author Захарченко Роман
 */
public class PrintFieldTypeCommand extends UpperClass {
    private static final Logger logger = LogManager.getLogger(PrintFieldTypeCommand.class);
    CollectionManager cm = new CollectionManager();

    public PrintFieldTypeCommand() {super(new CommandType(0, false), "print_field_type", "print sorted id of all collection elements with the same ticket types");}

    @Override
    public Response execute(Request request){
        //String str = cm.printFieldType();
        logger.info("Done print_field_type");
        //return new Response(str);
        return null;
    }
}
