package pers.helen.kafkademo.sender;

import lombok.Data;

import java.util.List;

@Data
public class SmsMessage {

    private int type;

    private List<String> toMobiles;
}
