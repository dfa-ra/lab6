package com.ra.client;

import com.ra.client.forms.Form;
import com.ra.client.forms.typeForms.TicketForm;
import com.ra.client.forms.typeForms.PersonForm;
import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.enum_.Color;
import com.ra.common.enum_.TicketType;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.messageType;
import com.ra.common.sample.Coordinates;
import com.ra.common.sample.Location;
import com.ra.common.sample.Person;
import com.ra.common.sample.Ticket;

import java.io.BufferedReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ValidateRequest {
    private HashMap<String, CommandType> commandType;
    private Scanner in = new Scanner(System.in);

    public ValidateRequest(HashMap<String, CommandType> commandType) {
        this.commandType = commandType;
    }
    public Request Validate(String commandName, String commandArg, BufferedReader reader) throws ParseException {
        if (!commandType.containsKey(commandName)) return null;

        int len;
        if (commandArg.isEmpty())
            len = 0;
        else{
            String[] commandArgs = commandArg.split(" ");
            len = commandArgs.length;
        }

        if (!(commandType.get(commandName).getArgumentCount_min() <= len &&
                len <= commandType.get(commandName).getArgumentCount_max())) {
            return null;
        }
        if (commandType.get(commandName).isWithForm())
        {
            Form form = new Form(new TicketForm());
            form.setHelpForm(new PersonForm());
            List<String> answer = form.collectInfo(reader);
            Person person = null;
            try {
                if (Objects.equals(answer.get(answer.size() - 1), "Y")) {
                    person = new Person(new SimpleDateFormat("dd.MM.yyyy").parse(answer.get(7)), Color.valueOf(answer.get(8)), new Location(answer.get(9), answer.get(10), answer.get(11), answer.get(12)));
                }
                return new Request(commandName, commandArg,
                        new Ticket(Long.valueOf(0), answer.get(0), new Coordinates(answer.get(1), answer.get(2)), null, Double.parseDouble(answer.get(3)), answer.get(4), Boolean.getBoolean(answer.get(5)), TicketType.valueOf(answer.get(6)), person));
            }catch (Exception e){
                Sender.send(new Message(messageType.ERROR, "There were technical problems due to the form not being read correctly from the file. Check your script!"));
                return null;
            }
        }

        return new Request(commandName, commandArg);
    }
}
