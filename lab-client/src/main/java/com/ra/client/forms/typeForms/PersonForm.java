package com.ra.client.forms.typeForms;

import com.ra.client.forms.HelpForm;
import com.ra.common.formvalidator.Validators;
import com.ra.common.formvalidator.valid.*;
import com.ra.common.message.Message;
import com.ra.common.message.messageType;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class PersonForm implements HelpForm {
    private List<Pair<Message, List<Validators>>> form = new ArrayList<>();
    public PersonForm() {
        form.add(new Pair<>(
                new Message(messageType.INPUT,"your birthday (dd.mm.yyyy): ", ""),
                new ArrayList<>(){{
                    add(new NotNullValid());
                    add(new DateTimeValid());
                }}));
        form.add(new Pair<>(
                new Message(messageType.INPUT,"Enter one of these hair color: ", ""),
                new ArrayList<>(){{
                    add(new ColorValid());
                }}));
        form.add(new Pair<>(
                new Message(messageType.INPUT,"location x (not null): ", ""),
                new ArrayList<>(){{
                    add(new DoubleValid());
                }}));
        form.add(new Pair<>(
                new Message(messageType.INPUT,"location y: ", ""),
                new ArrayList<>(){{
                    add(new FloatValid());
                }}));
        form.add(new Pair<>(
                new Message(messageType.INPUT,"location z: ", ""),
                new ArrayList<>(){{
                    add(new IntegerValid());
                }}));
        form.add(new Pair<>(
                new Message(messageType.INPUT, "location name: ", ""),
                new ArrayList<>(){{
                    add(new DefaultValid());
                }}));
    }

}
