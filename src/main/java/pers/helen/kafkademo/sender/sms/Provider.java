package pers.helen.kafkademo.sender.sms;

public interface Provider {
    Response send(String var1, int var2, String var3);
}
