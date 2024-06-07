package com.ra.client;

import com.ra.common.User;
import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.enum_.Color;
import com.ra.common.enum_.TicketType;
import com.ra.common.forms.Form;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.MessageType;
import com.ra.common.sample.Coordinates;
import com.ra.common.sample.Location;
import com.ra.common.sample.Person;
import com.ra.common.sample.Ticket;
import lombok.Getter;

import java.io.BufferedReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ValidateRequest {
    @Getter
    private final HashMap<String, CommandType> commandType;
    private Scanner in = new Scanner(System.in);

    public ValidateRequest(HashMap<String, CommandType> commandType) {
        this.commandType = commandType;
    }
    public Request Validate(String commandName, String commandArg, BufferedReader reader) {
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
            Form form = new Form(commandType.get(commandName).getForm());
            form.setHelpForm(commandType.get(commandName).getHelpForm());
            List<String> answer = form.collectInfo(reader);
            if (Objects.equals(commandName, "sign_up")){
                return new Request(commandName, commandArg);
            }
            Person person = null;
            try {
                System.out.println(answer);
                if (!(answer.get(7).equals("-") ||
                        answer.get(8).equals("-") ||
                        answer.get(9).equals("-") ||
                        answer.get(10).equals("-") ||
                        answer.get(11).equals("-") ||
                        answer.get(12).equals("-")))
                    person = new Person(new SimpleDateFormat("dd.MM.yyyy").parse(answer.get(7)), Color.valueOf(answer.get(8)), new Location(answer.get(9), answer.get(10), answer.get(11), answer.get(12)));
                return new Request(commandName, commandArg,
                        new Ticket(Long.valueOf(0), answer.get(0), new Coordinates(answer.get(1), answer.get(2)), null, Double.parseDouble(answer.get(3)), answer.get(4), Boolean.parseBoolean(answer.get(5)), TicketType.valueOf(answer.get(6)), person));
            }catch (Exception e){
                System.out.println(e.getMessage());
                Sender.send(new Message(MessageType.ERROR, "There were technical problems due to the form not being read correctly from the file. Check your script!"));
                return null;
            }
        }

        return new Request(commandName, commandArg);
    }
}
