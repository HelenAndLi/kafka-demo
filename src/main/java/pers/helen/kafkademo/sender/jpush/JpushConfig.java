package pers.helen.kafkademo.sender.jpush;

import lombok.Data;

@Data
public class JpushConfig {

    private String appkey;

    private String appsecret;

    private String environment;

}
