package com.ra.common.formvalidator.valid;
import com.ra.common.enum_.Price;
import com.ra.common.formvalidator.Validators;

public class LessM10000Valid implements Validators {
    @Override
    public int validate(String value) {
        if (Double.parseDouble(value) <= Price.MAX_PRICE.getPrice()) {
            return 1;
        }
        return 0;
    }
}

