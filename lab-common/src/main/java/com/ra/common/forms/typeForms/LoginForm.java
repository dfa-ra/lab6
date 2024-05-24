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

@Getter
@AllArgsConstructor
public class LoginForm implements HelpForm, Serializable {
    private List<Pair<Message, List<Validators>>> form = new ArrayList<>();
    public LoginForm() {
        form.add(new Pair<>(
                new Message(MessageType.INPUT, "Login: ", ""),
                new ArrayList<>() {{
                    add(new NotNullValid());
                }}));
        form.add(new Pair<>(
                new Message(MessageType.INPUT, "Password: ", ""),
                new ArrayList<>() {{
                    add(new NotNullValid());
                }}));
    }
}
