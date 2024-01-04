package pers.helen.kafkademo.sender.voicemessage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "voicemsg")
public class VmPropertiesBatch {

    @NestedConfigurationProperty
    private final Map<String, Access> access = new HashMap<>();

    public Map<String, Access> getAccess(){
        return access;
    }

    public final static class Access {
        private String aliyun_key;

        private String aliyun_secret;

        private String aliyun_url;

        private String huyi_key;

        private String huyi_secret;

        private String huyi_url;

        public String getAliyun_key(){
            return aliyun_key;
        }

        public void setAliyun_key(String aliyun_key){
            this.aliyun_key = aliyun_key;
        }

        public String getAliyun_secret(){
            return aliyun_secret;
        }

        public void setAliyun_secret(String aliyun_secret){
            this.aliyun_secret = aliyun_secret;
        }

        public String getAliyun_url(){
            return aliyun_url;
        }

        public void setAliyun_url(String aliyun_url){
            this.aliyun_url = aliyun_url;
        }

        public String getHuyi_key(){
            return huyi_key;
        }

        public void setHuyi_key(String huyi_key){
            this.huyi_key = huyi_key;
        }

        public String getHuyi_secret(){
            return huyi_secret;
        }

        public void setHuyi_secret(String huyi_secret){
            this.huyi_secret = huyi_secret;
        }

        public String getHuyi_url(){
            return huyi_url;
        }

        public void setHuyi_url(String huyi_url){
            this.huyi_url = huyi_url;
        }
    }

}
