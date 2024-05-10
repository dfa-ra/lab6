package com.ra.client.command;

import com.ra.client.Connection;
import com.ra.client.Handler;
import com.ra.client.ValidateRequest;
import com.ra.client.forms.Form;
import com.ra.common.Splitter;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.messageType;

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
    public InvokerClient() throws IOException {
        clienCommands.put("exit", (a) -> System.exit(0));
        clienCommands.put("execute_script", this::commandScriptSelection);
        connectHendler = new Connection().connection();
        getAllCommands();
    }

    public void getAllCommands() throws IOException {
        Request request = new Request("getAllCommand");
        connectHendler.sendRequest(request);
        Response response = connectHendler.dataReception();
        if (response != null) {
            validateRequest = new ValidateRequest(response.getInfoCommand());
            Sender.send(new Message(messageType.INFO,"All commands have been received. The application is ready to work"));
        }else {
            Sender.send(new Message(messageType.ERROR,"The server went out to smoke...\n" +
                    "(check that the port and host are correct)"));
            connectHendler = new Connection().connection();
            getAllCommands();
        }

    }
    public void commandScriptSelection(String fileName) throws IOException, ParseException {
        if (!new File(fileName).exists()) {
            Sender.send(new Message(messageType.ERROR, "file does not exist"));
            return;
        }
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        Form.setAbilityToCorrectErrorn(false);
        String line = br.readLine();
        while (line != null) {
            Sender.send(new Message(messageType.INPUT,"Input command: " + line, "\n"));
            creationReqest(line, br);
            line = br.readLine();
        }

        br.close();
    }
    public void commandSelection() throws Exception {
        String str;
        List<String> tokens;
        Scanner in = new Scanner(System.in);
        while (true) {
            try{
                Sender.send(new Message(messageType.INPUT,"Input command(for help write 'help'): ", ""));
                str = in.nextLine();
                Form.setAbilityToCorrectErrorn(true);
                creationReqest(str, new BufferedReader(new InputStreamReader(System.in)));
            } catch (NoSuchElementException e) {
                Sender.send(new Message(messageType.ERROR,"CTRL+D entered - program terminated"));
                clienCommands.get("exit").execute("");
            }
        }
    }
    public void creationReqest(String str, BufferedReader reader) throws IOException, ParseException {
        List<String> tokens = Splitter.mySplit(str);
        if (clienCommands.containsKey(tokens.get(0))){
            clienCommands.get(tokens.get(0)).execute(tokens.get(1));
            return;
        }
        Request request = validateRequest.Validate(tokens.get(0), tokens.get(1), reader);
        if (request != null){
            connectHendler.sendRequest(request);
            Response response = connectHendler.dataReception();
            if (response != null) Sender.send(new Message(messageType.DEFAULT,response.toString() + "\n"));
            else {
                Sender.send(new Message(messageType.ERROR,"The server went out to smoke... Try to reconnect.", "\n\n"));
                connectHendler = new Connection().connection();
                getAllCommands();
            }
        }
        else Sender.send(new Message(messageType.WARNING,"invalid command"));
    }
}
