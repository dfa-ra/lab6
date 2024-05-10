package com.ra.common.formvalidator.valid;

import com.ra.common.formvalidator.Validators;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.messageType;

public class DoubleValid implements Validators {
    @Override
    public int validate(String value) {
        try{
            Double.parseDouble(value);
            return 1;
        } catch (NumberFormatException e) {
            Sender.send(new Message(messageType.ERROR,"Error! Invalid data type. Try again."));
            return 0;
        }
    }
}
