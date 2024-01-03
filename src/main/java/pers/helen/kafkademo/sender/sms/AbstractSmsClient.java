package pers.helen.kafkademo.sender.sms;

public class AbstractSmsClient implements SmsClient{
    private Provider provider;

    public AbstractSmsClient() {
    }

    public AbstractSmsClient(Provider provider) {
        this.provider = provider;
    }

    public Provider getProvider() {
        return this.provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }


    @Override
    public Response send(String var1, int smsType, String var3){
        return this.getProvider().send(var1, smsType, var3);
    }
}
