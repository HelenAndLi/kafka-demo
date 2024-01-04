package pers.helen.kafkademo.sender;

import okhttp3.MediaType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.tomcat.util.codec.binary.Base64;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pers.helen.kafkademo.sender.jpush.Audience;
import pers.helen.kafkademo.sender.jpush.JPushMsg;
import pers.helen.kafkademo.sender.jpush.JpushConfig;
import pers.helen.kafkademo.sender.jpush.JpushMessage;
import pers.helen.kafkademo.sender.jpush.Notification;
import pers.helen.kafkademo.sender.jpush.Options;
import pers.helen.kafkademo.sender.jpush.Platform;
import pers.helen.kafkademo.sender.sms.SmsClient;
import pers.helen.kafkademo.sender.util.JsonUtils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaReceiver {
    private static final Logger logger = LoggerFactory.getLogger(KafkaReceiver.class);

    @Autowired
    private SmsClient smsClient1;

    @Autowired
    private SmsClient smsClient2;

    @Resource(name = "c1Jpush")
    private JpushConfig wxwpPush;

    @Resource(name = "zypdJpush")
    private JpushConfig zypdPush;

    @KafkaListener(topics = {"mytopic"})
    public void listen(ConsumerRecord<?, ?> record){
        System.out.println("topic名称:" + record.topic() + "\n" + "分区位置:" + record.partition() + "\n" + "key:" + record.key() + "\n" + "偏移量:" + record.offset() + "\n" + "消息内容:" + record.value());
    }

    @KafkaListener(topics = {"topic_jpush"})
    public void listenJPush(ConsumerRecord<?, String> record){
        System.out.println("消费极光消息");
        JpushMessage bo = JsonUtils.str2Obj(record.value(), JpushMessage.class);
        JPushMsg msg = new JPushMsg();
        msg.setPlatform(Platform.all);
        //        msg.setCid(UUID.randomUUID().toString());

        Notification notification = new Notification();
        //android
        Notification.Android android = new Notification.Android();

        android.setAlert(bo.getAlert());
        android.setTitle(bo.getTitle());
        android.setExtras(bo.getExtras());
        notification.setAndroid(android);

        // IOS
        Notification.Ios ios = new Notification.Ios();
        Notification.Ios.Alert alert = new Notification.Ios.Alert();
        alert.setTitle(bo.getTitle());
        alert.setBody(bo.getTitle());
        ios.setAlert(alert);
        ios.setExtras(bo.getExtras());
        notification.setIos(ios);

        msg.setNotification(notification);

        //        if(pushConfig.getEnvironment().compareTo(Environment.TEST) == 0){
        Options options = new Options();
        options.setApnsProduction(false);
        msg.setOptions(options);
        //        }else{
        //            Options options = new Options();
        //            options.setApnsProduction(true);
        //            msg.setOptions(options);
        //        }

        Audience audience = new Audience();

        audience.setAlias(bo.getAlias());


        msg.setAudience(audience);

        logger.info("req body:{}", JsonUtils.obj2Str(msg));
        //start
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("Authorization",
                "Basic " + Base64.encodeBase64String((wxwpPush.getAppkey() + ":" + wxwpPush.getAppsecret()).getBytes(StandardCharsets.UTF_8)));
        String url = "https://api.jpush.cn/v3/push";
        RequestBody requestBody = RequestBody.create(JsonUtils.obj2Str(msg), mediaType);

        Request.Builder builder =
                new Request.Builder().url(url).post(requestBody);
        if(!headerMap.isEmpty()){
            for(Map.Entry<String, Object> key : headerMap.entrySet()){
                if(key.getValue() != null){
                    builder.addHeader(key.getKey(), key.getValue().toString());
                }
            }
        }
        builder.addHeader("Content-Type", "application/json");
        Request request = builder.build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e){
                logger.warn("push fail.", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException{
                logger.warn("push reponse header:{}, body:{}", response.headers(), response.body().string());

                response.close();
            }
        });
    }

    @KafkaListener(topics = {"topic_sms"})
    public void listenSms(ConsumerRecord<?, String> record){
        System.out.println("消费短信消息");

        SmsMessage msg = JsonUtils.str2Obj(record.value(), SmsMessage.class);
        if(msg.getType() == 1){
            for(String mobile : msg.getToMobiles()){
                smsClient1.send(mobile, 1,
                        "试试短信平台1");
                smsClient2.send(mobile, 1,
                        "试试短信平台2");
            }
        }else if(msg.getType() == 2){
            // 提醒未执行
            for(String mobile : msg.getToMobiles()){
                // 其他消息处理逻辑
            }
        }
    }

    @KafkaListener(topics = {"topic_vm"})
    public void listenVm(ConsumerRecord<?, ?> record){
        System.out.println("消费语音消息");
        System.out.println("topic名称:" + record.topic() + "\n" + "分区位置:" + record.partition() + "\n" + "key:" + record.key
                () + "\n" + "偏移量:" + record.offset() + "\n" + "消息内容:" + record.value());
    }

    //    public static void main(String[] args){
    //        Properties p = new Properties();
    //        p.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.xxx:30901,192.168.0.xxx:30902,192.168.0.xxx:30903");
    //        p.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    //        p.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    //        p.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
    //        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(p);
    //        kafkaConsumer.subscribe(Collections.singletonList("mytopic"));
    //        while(true){
    //            ConsumerRecords<String, String> record = kafkaConsumer.poll(100);
    //            for(ConsumerRecord<String, String> record1 : record){
    //                System.out.println("=================================================================");
    //                System.out.println(record1.topic() + "  " + record1.offset() + "  " + record1.value());
    //            }
    //        }
    //
    //    }
}
