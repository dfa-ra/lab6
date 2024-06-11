package com.ra.server.comands;

import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.MessageType;
import com.ra.server.comands.allCommands.*;
import com.ra.server.comands.allCommands.dbCommand.AddNewUserCommand;
import com.ra.server.comands.allCommands.dbCommand.CheckExistsUserCommand;
import com.ra.server.comands.allCommands.dbCommand.CheckTicketUserCommand;
import com.ra.server.comands.allCommands.dbCommand.GetUserByLoginAndPasswordCommand;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Getter
public class Invoker {
    private static final Logger logger = LogManager.getLogger(Command.class);
    /**
     * Список всех введённых команд
     */
    protected List<String> history = new ArrayList<>();
    /**
     * Коллекция типа ключ значение ключ='названию команды' значение='класс исполнитель команды'
     */
    protected Map<String, Command> commands = new HashMap<>();

    /**
     * Конструктор в котором определяются все команды
     */
    public Invoker(){
        commands.put("help", new HelpCommand());
        commands.put("info", new InfoCommand());
        commands.put("exit", new ExitCommand());
        commands.put("show", new ShowCommand());
        commands.put("add", new AddCommand());
        commands.put("clear", new ClearCommand());
        commands.put("remove_by_id", new RemoveByIdCommand());
        commands.put("update", new UpdateCommand());
        commands.put("remove_greater", new RemoveGreaterCommand());
        commands.put("remove_lower", new RemoveLowerCommand());
        commands.put("print_ascending_id", new PrintAscendingCommand());
        commands.put("print_ascending_type", new PrintAscendingTypeCommand());
        commands.put("sign_up", new AddNewUserCommand());
        commands.put("sign_in", new CheckExistsUserCommand());
        commands.put("check_permission_update", new CheckTicketUserCommand());
        commands.put("get_user_by_login_and_password", new GetUserByLoginAndPasswordCommand());
    }

    public Response commandSelectionByStr(Request request, boolean clientCommand) throws Exception {
        if (commands.containsKey(request.getNameCommand())){
            System.out.println(request.getNameCommand());
            Command command = commands.get(request.getNameCommand());
            Response response = command.execute(request);
            if (!clientCommand) Sender.send(new Message(MessageType.DEFAULT, response.toString() + "\n"));
            return response;
        }
        else {
            Sender.send(new Message(MessageType.WARNING, "Command not found"));
            return new Response(false, "This command does not exist. Using the 'help' command you can see all available commands.");
        }
    }

    public HashMap<String, CommandType> getAllCommand(){
        HashMap<String, CommandType> allCommand = new HashMap<>();
        for (Map.Entry<String, Command> entry : commands.entrySet()){
            if (entry.getValue().getCommandType().getArgumentCount_max() != -1)
                allCommand.put(entry.getKey(), entry.getValue().getCommandType());
        }
        Sender.send(new Message(MessageType.DEFAULT, "All resolved commands are issued to the client \n"));
        logger.info("Information about commands was issued to the client");
        return allCommand;
    }
}
