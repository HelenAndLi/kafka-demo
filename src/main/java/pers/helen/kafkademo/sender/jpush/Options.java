package pers.helen.kafkademo.sender.jpush;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Options implements Serializable {

    private static final long serialVersionUID = -5088071783029254778L;

    @JsonProperty("apns_production")
    private Boolean apnsProduction;
}
