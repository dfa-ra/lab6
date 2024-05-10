package com.ra.client.forms;

import com.ra.common.formvalidator.Validators;
import com.ra.common.message.Message;
import javafx.util.Pair;

import java.util.List;

public interface HelpForm {
    List<Pair<Message, List<Validators>>> getForm();
}
