package com.ra.common.formvalidator.valid;

import com.ra.common.formvalidator.Validators;

public class MoreM785Valid implements Validators {
    @Override
    public int validate(String value) {
        if (Double.parseDouble(value) > -785) {
            return 1;
        }
        return 0;
    }
}
