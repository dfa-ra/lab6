package com.ra.common.formvalidator.valid;

import com.ra.common.enum_.TicketType;
import com.ra.common.formvalidator.Validators;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.messageType;

public class TicketTypeValid implements Validators {
    @Override
    public int validate(String value) {
        try{
            TicketType ticketType = TicketType.valueOf(value);
            return 1;
        } catch (IllegalArgumentException e) {
            Sender.send(new Message(messageType.ERROR,"Select an option from the list above!"));
            return 0;
        }
    }
}
