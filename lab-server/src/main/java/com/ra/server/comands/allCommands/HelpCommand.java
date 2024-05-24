package com.ra.server.comands.allCommands;


import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.server.comands.Command;
import com.ra.server.comands.Invoker;
import com.ra.server.comands.UpperCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Класс команды help(помощи)
 * @author Захарченко Роман
 */
public class HelpCommand extends UpperCommand {
    private static final Logger logger = LogManager.getLogger(HelpCommand.class);
    public HelpCommand() {
        super(new CommandType(0, false, false), "help", "display help on available commands");
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
        str += "===========================================";
        logger.info("Done!");
        return new Response(str);
    }
}
