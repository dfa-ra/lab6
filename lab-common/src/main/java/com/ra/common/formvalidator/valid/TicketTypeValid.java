package com.ra.common.formvalidator.valid;

import com.ra.common.enum_.TicketType;
import com.ra.common.formvalidator.Validators;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.MessageType;

public class TicketTypeValid implements Validators {
    @Override
    public int validate(String value) {
        try{
            TicketType ticketType = TicketType.valueOf(value);
            return 1;
        } catch (IllegalArgumentException e) {
            Sender.send(new Message(MessageType.ERROR,"Select an option from the list above!"));
            return 0;
        }
    }
}
