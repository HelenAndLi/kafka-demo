package pers.helen.kafkademo.sender.sms;

import lombok.Data;

@Data
public class Response {
    private int code;
    private String msg;
    private Object data;
}
