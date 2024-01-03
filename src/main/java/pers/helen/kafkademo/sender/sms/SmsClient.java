package pers.helen.kafkademo.sender.sms;

public interface SmsClient {
    Response send(String var1, int smsType, String var3);
}
