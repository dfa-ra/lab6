package com.ra.client.forms;

import com.ra.common.formvalidator.*;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import javafx.util.Pair;
import lombok.Setter;

import java.io.BufferedReader;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Form {
    private List<String> answer = new ArrayList<>();
    private List<Pair<Message, List<Validators>>> form = new ArrayList<>();
    private List<HelpForm> helpForm = new ArrayList<>();

    @Setter
    static private boolean abilityToCorrectErrorn = false;
    private int helpFormIterator = 0;


    public Form(HelpForm MainForm){
        form = MainForm.getForm();
    }
    public void setHelpForm(HelpForm helpForm) {
        this.helpForm.add(helpForm);
    }
    public List<String> collectInfo(BufferedReader bufferReader) throws ParseException {

        for (Pair<Message, List<Validators>> question : form) {
            Sender.send(question.getKey());
                do {
                    try {
                        String tmp = bufferReader.readLine();
                        System.out.println(tmp);
                        if (!checkAnswer(tmp, question, bufferReader)) {
                            answer.add(tmp);
                            if (abilityToCorrectErrorn) break;
                        }
                    } catch (Exception ignored){}
                }while (abilityToCorrectErrorn);
        }
        return answer;
    }

    public List<String> parseInfo() throws ParseException {
        return null;
    }

    public boolean checkAnswer(String tmp, Pair<Message , List<Validators>> question, BufferedReader bufferReader) throws ParseException {
        for (Validators v : question.getValue()) {
            if (v.validate(tmp) == 0) {
                return true;
            }
            if (v.validate(tmp) == 2) {
                Form form1 = new Form(helpForm.get(helpFormIterator));
                answer = Stream.concat(answer.stream(), form1.collectInfo(bufferReader).stream()).collect(Collectors.toList());
                helpFormIterator++;
            }
        }
        return false;
    }

}
