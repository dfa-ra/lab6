package com.ra.common.formvalidator.valid;

import com.ra.common.formvalidator.Validators;

public class NextForm implements Validators {
    @Override
    public int validate(String value) {
        if (value.equals("Y")) {
            return 2;
        }
        return 1;
    }
}
