package com.ra.common.formvalidator.valid;

import com.ra.common.formvalidator.Validators;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.MessageType;

public class IntegerValid implements Validators {
    @Override
    public int validate(String value) {
        try{
            Integer.parseInt(value);
            return 1;
        } catch (NumberFormatException e) {
            Sender.send(new Message(MessageType.ERROR,"Error! Invalid data type. Try again."));
            return 0;
        }
    }
}
