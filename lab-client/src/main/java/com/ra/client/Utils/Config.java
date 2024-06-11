package com.ra.client.Utils;

import lombok.Getter;


public class Config {
    @Getter
    private static final int MAX_PORT_VALUE = Integer.MAX_VALUE;
    @Getter
    private static final int RESPONSE_TIMEOUT_VALUE = 15000;

    private Config(){}
}
