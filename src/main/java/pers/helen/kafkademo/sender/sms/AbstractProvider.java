package pers.helen.kafkademo.sender.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import pers.helen.kafkademo.sender.util.PhoneNumberValidator;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public abstract class AbstractProvider implements Provider {

    private String mobile;

    private int smsType;

    private String content;

    private static final Logger logger = LoggerFactory.getLogger(AbstractProvider.class);

    public AbstractProvider(){
    }

    public Response send(String mobile, int smsType, String content){
//        Assert.isTrue(!(PhoneNumberValidator.isValid(mobile)), "手机号格式错误！");

        this.mobile = mobile;
        this.content = content;
        this.smsType = smsType;
        return this.doSendSms(smsType, mobile, content);
    }


    public String getMobile(){
        return this.mobile;
    }


    public int getSmsType(){
        return this.smsType;
    }

    public String getContent(){
        return this.content;
    }

    protected abstract Response doSendSms(int smsType, String mobile, String content);


}
