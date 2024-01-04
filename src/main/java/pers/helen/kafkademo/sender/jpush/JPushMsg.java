package pers.helen.kafkademo.sender.jpush;

import lombok.Data;

import java.io.Serializable;

@Data
public class JPushMsg implements Serializable {

    private static final long serialVersionUID = 5640601871308337276L;

    /**
     * 推送平台设置，必填
     */
    private Platform platform;

    /**
     * 推送设备指定，必填
     */
    private Audience audience;

    /**
     * 通知内容体
     */
    private Notification notification;

    /**
     * 推送参数
     */
    private Options options;

}
