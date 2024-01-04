package pers.helen.kafkademo.sender.voicemessage;

import lombok.Data;

@Data
public class VmConfig {

    private String aliyun_key;

    private String aliyun_secret;

    private String aliyun_url;

    private String huyi_key;

    private String huyi_secret;

    private String huyi_url;
}
