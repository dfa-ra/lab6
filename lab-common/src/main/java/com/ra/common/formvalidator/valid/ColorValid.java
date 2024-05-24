package com.ra.common.formvalidator.valid;

import com.ra.common.enum_.Color;
import com.ra.common.formvalidator.Validators;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.MessageType;

public class ColorValid implements Validators {
    @Override
    public int validate(String value) {
        try{
            Color ticketType = Color.valueOf(value);
            return 1;
        } catch (IllegalArgumentException e) {
            Sender.send(new Message(MessageType.ERROR,"Select an option from the list above!"));
            return 0;
        }
    }
}
