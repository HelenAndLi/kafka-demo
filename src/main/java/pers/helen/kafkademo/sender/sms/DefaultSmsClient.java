package pers.helen.kafkademo.sender.sms;

public class DefaultSmsClient extends AbstractSmsClient {

    public DefaultSmsClient(){
    }

    public DefaultSmsClient(Provider provider){
        super(provider);
    }
}
