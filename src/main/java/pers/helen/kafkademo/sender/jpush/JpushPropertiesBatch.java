package pers.helen.kafkademo.sender.jpush;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "message")
public class JpushPropertiesBatch {

    @NestedConfigurationProperty
    private final Map<String, Conf> conf = new HashMap<>();

    public Map<String, Conf> getConf(){
        return conf;
    }

    public final static class Conf {

        private String appkey;

        private String appsecret;

        private String environment;

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

        public String getEnvironment(){
            return environment;
        }

        public void setEnvironment(String environment){
            this.environment = environment;
        }

    }

}
