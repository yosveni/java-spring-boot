package com.linkapital.core.services.notification.contract.to;

import lombok.Getter;

@Getter
public class WebsocketPayloadTO {

    private String message;
    private Object data;

    public WebsocketPayloadTO withMessage(String message) {
        this.message = message;
        return this;
    }

    public WebsocketPayloadTO withData(Object data) {
        this.data = data;
        return this;
    }

}
