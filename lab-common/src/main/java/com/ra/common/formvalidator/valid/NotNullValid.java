package com.ra.common.formvalidator.valid;

import com.ra.common.formvalidator.Validators;

public class NotNullValid implements Validators {
    @Override
    public int validate(String value) {
        if (value.isEmpty())
            return 0;
        return 1;
    }
}
