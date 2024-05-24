package com.ra.common.forms.typeForms;

import com.ra.common.forms.HelpForm;
import com.ra.common.formvalidator.Validators;
import com.ra.common.formvalidator.valid.*;
import com.ra.common.message.Message;
import com.ra.common.message.MessageType;
import javafx.util.Pair;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Getter
public class TicketForm implements HelpForm, Serializable {
    private List<Pair<Message, List<Validators>>> form = new ArrayList<>();
    public TicketForm() {
        form.add(new Pair<>(
                new Message(MessageType.INPUT, "Name: ", ""),
                new ArrayList<>(){{
                    add(new NotNullValid());
                }}));
        form.add(new Pair<>(
                new Message(MessageType.INPUT,"coordinates x (>-785): ", ""),
                new ArrayList<>(){{
                    add(new NotNullValid());
                    add(new IntegerValid());
                    add(new MoreM785Valid());
                }}));
        form.add(new Pair<>(
                new Message(MessageType.INPUT,"coordinates y: ", ""),
                new ArrayList<>(){{
                    add(new DoubleValid());
                }}));
        form.add(new Pair<>(
                new Message(MessageType.INPUT,"price x (>0): ", ""),
                new ArrayList<>(){{
                    add(new NotNullValid());
                    add(new FloatValid());
                    add(new MoreZeroValid());
                }}));
        form.add(new Pair<>(
                new Message(MessageType.INPUT,"comment: ", ""),
                new ArrayList<>(){{
                    add(new DefaultValid());
                }}));
        form.add(new Pair<>(
                new Message(MessageType.INPUT,"refundable(can be null): ", ""),
                new ArrayList<>(){{
                    add(new RefundableValid());
                }}));
        form.add(new Pair<>(
                new Message(MessageType.INPUT,"Enter one of these ticket types: ", ""),
                new ArrayList<>(){{
                    add(new NotNullValid());
                    add(new TicketTypeValid());
                }}));
        form.add(new Pair<>(
                new Message(MessageType.INPUT, "Do you want to enter user information? (N/Y) ", ""),
                new ArrayList<>(){{
                    add(new NextForm());
                }}));
    }
}
