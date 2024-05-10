package com.ra.common.formvalidator.valid;

import com.ra.common.formvalidator.Validators;

public class DefaultValid implements Validators {
    @Override
    public int validate(String value) {
        return 1;
    }
}
