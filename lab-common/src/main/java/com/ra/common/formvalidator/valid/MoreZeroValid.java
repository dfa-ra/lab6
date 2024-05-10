package com.ra.common.formvalidator.valid;

import com.ra.common.formvalidator.Validators;

public class MoreZeroValid implements Validators {
    @Override
    public int validate(String value) {

        if (Double.parseDouble(value) > 0) {
            return 1;
        }
        return 0;
    }
}
