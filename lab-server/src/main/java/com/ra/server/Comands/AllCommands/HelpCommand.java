package com.ra.server.Comands.AllCommands;


import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.Comands.Command;
import com.ra.server.Comands.Invoker;
import com.ra.server.Comands.UpperClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Класс команды help(помощи)
 * @author Захарченко Роман
 */
public class HelpCommand extends UpperClass {
    private static final Logger logger = LogManager.getLogger(HelpCommand.class);
    public HelpCommand() {
        super(new CommandType(0, false), "help", "display help on available commands");
    }

    @Override
    public Response execute(Request request) {

        Invoker invoker = new Invoker();
        String str = "";
        str += "===========================================\n";
        str += "\tCommands:\n";


        for (Map.Entry<String, Command> entry : invoker.getCommands().entrySet()){
            if (entry.getValue().getCommandType().getArgumentCount_max() != -1)
                str += "-- " + entry.getValue().getName() + " - " + entry.getValue().getInfo() + "\n";
        }
        str += "===========================================\n";
        logger.info("Done!");
        return new Response(str);
    }
}
