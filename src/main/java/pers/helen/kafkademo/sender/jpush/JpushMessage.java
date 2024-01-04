package pers.helen.kafkademo.sender.jpush;

import lombok.Data;
import pers.helen.kafkademo.sender.KafkaMsgDto;

import java.util.Map;
import java.util.Set;

@Data
public class JpushMessage extends KafkaMsgDto {

    private String title;

    private String alert;

    private Map<String, Object> extras;

    private Set<String> alias;
}
