package pers.helen.kafkademo.sender.jpush;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class Audience implements Serializable {
    private static final long serialVersionUID = -314500536486972779L;
    private Set<String> alias;

}
