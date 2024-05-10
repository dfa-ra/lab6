package com.ra.common.formvalidator.valid;

import com.ra.common.formvalidator.Validators;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.messageType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeValid implements Validators {
    @Override
    public int validate(String value){
        try{
            Date date = new SimpleDateFormat("dd.MM.yyyy").parse(value);
            return 1;
        } catch (ParseException e){
            Sender.send(new Message(messageType.ERROR,"Error! Invalid data type. Try again."));
            return 0;
        }
    }
}
