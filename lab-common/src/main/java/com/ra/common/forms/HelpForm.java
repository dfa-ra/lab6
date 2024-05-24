package com.ra.common.forms;

import com.ra.common.formvalidator.Validators;
import com.ra.common.message.Message;
import javafx.util.Pair;


import java.io.Serializable;
import java.util.List;

public interface HelpForm extends Serializable {
    List<Pair<Message, List<Validators>>> getForm();
}
