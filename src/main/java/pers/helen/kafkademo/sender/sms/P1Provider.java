package pers.helen.kafkademo.sender.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.helen.kafkademo.sender.util.HttpUtil;
import java.util.HashMap;
import java.util.Map;

public class P1Provider extends AbstractProvider {

    private static final String SEND_URL = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

    private static final Logger logger = LoggerFactory.getLogger(P1Provider.class);

    private String appkey;

    private String appsecret;

    public P1Provider(){
    }

    public P1Provider(String appkey, String appsecret){
        this.appkey = appkey;
        this.appsecret = appsecret;
    }

    public String getAppkey(){
        return this.appkey;
    }

    public void setAppkey(String appkey){
        this.appkey = appkey;
    }

    public String getAppsecret(){
        return this.appsecret;
    }

    public void setAppsecret(String appsecret){
        this.appsecret = appsecret;
    }

    @Override
    public Response doSendSms(int smsType, String phoneNumber, String message){
        Map<String, String> data = new HashMap(4);
        data.put("account", this.getAppkey());
        data.put("password", this.getAppsecret());
        data.put("mobile", this.getMobile());
        data.put("content", this.getContent());

        try{
            String response = HttpUtil.sendPostRequest(SEND_URL, data);
            //            // 解析响应结果
            if(response.contains("success")){
                // 短信发送成功
                System.out.println("短信发送成功");
            }else{
                // 短信发送失败，可以根据需要处理异常情况
                System.out.println("短信发送失败：" + response);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return new Response();
    }
}
