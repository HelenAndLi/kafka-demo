package pers.helen.kafkademo.sender;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pers.helen.kafkademo.sender.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.xxx:30901,192.168.0.xxx:30902,192.168.0.xxx:30903");
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(p);
        try{
            SmsMessage sms = new SmsMessage();
            sms.setType(1);
            List<String> toMobiles = new ArrayList<>();
            toMobiles.add("1xxxxxxxxxx");
            sms.setToMobiles(toMobiles);
            ProducerRecord<String, String> record = new ProducerRecord<>("topic_sms", JsonUtils.obj2Str(sms));
            kafkaProducer.send(record);
            System.out.println("发送成功");

        }finally{
            kafkaProducer.close();
        }
    }


}
