package com.ra.common.formvalidator;

import java.io.Serializable;
import java.text.ParseException;

public interface Validators extends Serializable {
    int validate(String value) throws ParseException;
}
