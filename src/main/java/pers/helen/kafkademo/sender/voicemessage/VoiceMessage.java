package pers.helen.kafkademo.sender.voicemessage;

import lombok.Data;
import pers.helen.kafkademo.sender.KafkaMsgDto;

@Data
public class VoiceMessage extends KafkaMsgDto {

    private int type;

    private String[] callNumber;

    private String toMobile;

    private String ttsCode;

    private String msg;

}
