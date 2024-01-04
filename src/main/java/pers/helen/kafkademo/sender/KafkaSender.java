package pers.helen.kafkademo.sender;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pers.helen.kafkademo.sender.jpush.JpushMessage;
import pers.helen.kafkademo.sender.util.JsonUtils;
import pers.helen.kafkademo.sender.voicemessage.VoiceMessage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Component
public class KafkaSender {

    @Autowired(required = false)
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPICNAME = "mytopic";


    public void send(String key, String jsonStr){
        //发送消息
        kafkaTemplate.send(TOPICNAME, key, jsonStr);
    }

    public static void main(String[] args){
        Properties p = new Properties();
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.101.0.214:30901,10.101.0.214:30902,10.101.0.214:30903");
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(p);
        try{

            // 短信
            //            SmsMessage sms = new SmsMessage();
            //            sms.setType(1);
            //            List<String> toMobiles = new ArrayList<>();
            //            toMobiles.add("1xxxxxxxxxx");
            //            sms.setToMobiles(toMobiles);
            //            ProducerRecord<String, String> record = new ProducerRecord<>("topic_sms", JsonUtils.obj2Str
            //            (sms));
            //            kafkaProducer.send(record);
            //            System.out.println("发送成功");

            // 极光
            //        JpushMessage jpush = new JpushMessage();
            //        jpush.setAlert("xxx，请及时处理！");
            //        jpush.setTitle("xxx，请及时处理！");
            //        jpush.setPlatform(1);
            //        Set<String> alias = new HashSet<>();
            //        alias.add("alias_xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxxx".replace("-", ""));
            //        jpush.setAlias(alias);
            //        Map<String, Object> extras = new HashMap<>(6);
            //        extras.put("module", "NOTICE");
            //        jpush.setExtras(extras);
            //        ProducerRecord<String, String> record = new ProducerRecord<>("topic_jpush", JsonUtils.obj2Str
            //        (jpush));
            //        kafkaProducer.send(record);
            //        System.out.println("发送成功");
            //        }finally{
            //            kafkaProducer.close();
            //        }

            //语音
            VoiceMessage vm = new VoiceMessage();
            vm.setType(1);
            vm.setPlatform(2);
            vm.setCallNumber(new String[]{"xxx","xxxx"});
            vm.setToMobile("xxx");
            vm.setTtsCode("xxx");
            vm.setMsg("你好");
            ProducerRecord<String, String> record = new ProducerRecord<>("topic_vm", JsonUtils.obj2Str(vm));
            kafkaProducer.send(record);
            System.out.println("发送成功");

        }finally{
            kafkaProducer.close();
        }

    }
}
