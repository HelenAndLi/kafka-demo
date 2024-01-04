package pers.helen.kafkademo.sender;

import lombok.Data;

@Data
public class KafkaMsgDto {

    /**
     * 平台：1-c1，2-c2
     */
    private int platform;
}
