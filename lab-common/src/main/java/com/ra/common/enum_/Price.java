package com.ra.common.enum_;

import lombok.Getter;

@Getter
public enum Price {

    MAX_PRICE(10000.0);

    private double price;

    Price(double price) {
        this.price = price;
    }
}
