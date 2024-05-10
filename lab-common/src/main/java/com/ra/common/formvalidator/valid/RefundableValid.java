package com.ra.common.formvalidator.valid;

import com.ra.common.formvalidator.Validators;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.messageType;

public class RefundableValid implements Validators {
    @Override
    public int validate(String value) {
        if (value.equals("true") || value.equals("false")) {
            return 1;
        }
        else {
            Sender.send(new Message(messageType.ERROR,"Available answers are true or false!"));
            return 0;
        }
    }
}
