package pers.helen.kafkademo.sender.sms;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;


@ConfigurationProperties(prefix = "sms")
public class SmsPropertiesBatch {

    @NestedConfigurationProperty
    private final Map<String,Verification> verification = new HashMap<>();

    public Map<String,Verification> getVerification(){
        return verification;
    }

    public final static class Verification {

        private String appkey;

        private String appsecret;

        private Class<? extends Provider> provider;

        public String getAppkey(){
            return appkey;
        }

        public void setAppkey(String appkey){
            this.appkey = appkey;
        }

        public String getAppsecret(){
            return appsecret;
        }

        public void setAppsecret(String appsecret){
            this.appsecret = appsecret;
        }

        public Class<? extends Provider> getProvider(){
            return provider;
        }

        public void setProvider(Class<? extends Provider> provider){
            this.provider = provider;
        }

    }

}
