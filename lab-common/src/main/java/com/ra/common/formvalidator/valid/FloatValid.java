package com.ra.common.formvalidator.valid;

import com.ra.common.formvalidator.Validators;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.messageType;
import org.codehaus.stax2.ri.typed.ValueDecoderFactory;

public class FloatValid implements Validators {

    @Override
    public int validate(String value) {
        try{
            Float.parseFloat(value);
            return 1;
        } catch (NumberFormatException e) {
            Sender.send(new Message(messageType.ERROR,"Error! Invalid data type. Try again."));
            return 0;
        }
    }
}
