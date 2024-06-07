package com.ra.client.command;

import com.ra.client.Connection;
import com.ra.client.Handler;
import com.ra.common.User;
import com.ra.client.ValidateRequest;
import com.ra.common.Splitter;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.MessageType;

import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * Класс реализующий взаимодействие с пользователем.
 * @author Захарченко Роман
 */
public class InvokerClient {
    private final Scanner in = new Scanner(System.in);
    protected String[] tokens;
    private Handler connectHendler;

    private ValidateRequest validateRequest;
    private final HashMap<String, Command> clienCommands = new HashMap<>();
    public InvokerClient(){
        clienCommands.put("exit", (a) -> System.exit(0));
//        clienCommands.put("execute_script", this::commandScriptSelection);
        try {
            connectHendler = new Connection().connection();
            getAllCommands();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        };
    }



    public void getAllCommands() throws IOException {
        Request request = new Request("getAllCommand");
        connectHendler.sendRequest(request);
        Response response = connectHendler.dataReception();
        if (response != null) {
            validateRequest = new ValidateRequest(response.getInfoCommand());
            Sender.send(new Message(MessageType.INFO,"All commands have been received. The application is ready to work"));
        }else {
            Sender.send(new Message(MessageType.ERROR,"The server went out to smoke...\n" +
                    "(check that the port and host are correct)"));
            connectHendler = new Connection().connection();
            getAllCommands();
        }

    }
//    public void commandScriptSelection(User user, String fileName) throws IOException, ParseException {
//        if (!new File(fileName).exists()) {
//            Sender.send(new Message(MessageType.ERROR, "file does not exist"));
//            return;
//        }
//        BufferedReader br = new BufferedReader(new FileReader(fileName));
//        Form.setAbilityToCorrectError(false);
//        String line = br.readLine();
//        while (line != null) {
//            Sender.send(new Message(MessageType.INPUT,"Input command: " + line, "\n"));
//            creationReqest(user, line, br);
//            line = br.readLine();
//        }
//
//        br.close();
//    }

    public Response creationReqest(User user, String str, BufferedReader reader) {
        List<String> tokens = Splitter.mySplit(str);
        if (clienCommands.containsKey(tokens.get(0))){
            clienCommands.get(tokens.get(0)).execute(tokens.get(1));
            return null;
        }
        if (validateRequest.getCommandType().containsKey(tokens.get(0)))
            if (validateRequest.getCommandType().get(tokens.get(0)).isPermissionUpdate())
                if (!checkPermission(user, tokens.get(1))) return null;

        Request request = validateRequest.Validate(tokens.get(0), tokens.get(1), reader);
        if (request != null){
            request.setUser(user);
            connectHendler.sendRequest(request);
            Response response = connectHendler.dataReception();
            if (response != null) {
                Sender.send(new Message(MessageType.DEFAULT, response.toString() + "\n"));
                return response;
            }
            else {
                Sender.send(new Message(MessageType.ERROR,"The server went out to smoke... Try to reconnect.", "\n\n"));
                try {
                    connectHendler = new Connection().connection();
                    getAllCommands();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        else Sender.send(new Message(MessageType.WARNING,"invalid command"));
        return null;
    }

    public boolean checkPermission(User user, String id){
        Request request = new Request("check_permission_update", id);
        request.setUser(user);
        connectHendler.sendRequest(request);
        Response response = connectHendler.dataReception();
        if (response.getAdditional().equals("ok")) {
            return true;
        }
        Sender.send(new Message(MessageType.WARNING, response.getAdditional(), "\n"));
        return false;
    }
}
