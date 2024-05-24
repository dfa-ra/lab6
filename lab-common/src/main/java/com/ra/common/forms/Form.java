package com.ra.common.forms;

import com.ra.common.formvalidator.Validators;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import javafx.util.Pair;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Form implements Serializable {
    private List<String> answer = new ArrayList<>();
    private final List<Pair<Message, List<Validators>>> form;
    private final List<HelpForm> helpForm = new ArrayList<>();

    @Setter
    static private boolean abilityToCorrectError = false;
    private int helpFormIterator = 0;


    public Form(HelpForm MainForm){
        form = MainForm.getForm();
    }
    public void formReady(){
        answer.clear();
        helpFormIterator = 0;
    }
    public void setHelpForm(HelpForm helpForm) {
        this.helpForm.add(helpForm);
    }
    public List<String> collectInfo(BufferedReader bufferReader) {
        for (Pair<Message, List<Validators>> question : form) {
            Sender.send(question.getKey());
                do {
                    try {
                        String tmp = bufferReader.readLine();
                        if (!abilityToCorrectError) System.out.println(tmp);
                        if (!checkAnswer(tmp, question, bufferReader)) {
                            answer.add(tmp);
                            if (abilityToCorrectError) break;
                        }
                    } catch (Exception ignored){}
                }while (abilityToCorrectError);
        }
        return answer;
    }

    public boolean checkAnswer(String tmp, Pair<Message , List<Validators>> question, BufferedReader bufferReader) throws ParseException {
        for (Validators v : question.getValue()) {
            if (v.validate(tmp) == 0) {
                return true;
            }
            if (v.validate(tmp) == 2) {
                Form form1 = new Form(helpForm.get(helpFormIterator));
                form1.formReady();
                answer = Stream.concat(answer.stream(), form1.collectInfo(bufferReader).stream()).collect(Collectors.toList());
                helpFormIterator++;
            }
        }
        return false;
    }

}
