package pers.helen.kafkademo.sender.sms;

import lombok.Data;

@Data
public class Config {

    private String appkey;

    private String appsecret;

    private Class<? extends Provider> provider;
}
