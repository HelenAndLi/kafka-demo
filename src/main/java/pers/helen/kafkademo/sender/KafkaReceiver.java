package pers.helen.kafkademo.sender;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pers.helen.kafkademo.sender.sms.SmsClient;
import pers.helen.kafkademo.sender.util.JsonUtils;

@Component
public class KafkaReceiver {

    @Autowired
    private SmsClient smsClient1;

    @Autowired
    private SmsClient smsClient2;

    @KafkaListener(topics = {"mytopic"})
    public void listen(ConsumerRecord<?, ?> record){
        System.out.println("topic名称:" + record.topic() + "\n" + "分区位置:" + record.partition() + "\n" + "key:" + record.key() + "\n" + "偏移量:" + record.offset() + "\n" + "消息内容:" + record.value());
    }

    @KafkaListener(topics = {"topic_jpush"})
    public void listenJPush(ConsumerRecord<?, ?> record){
        System.out.println("消费极光消息");
        System.out.println("topic名称:" + record.topic() + "\n" + "分区位置:" + record.partition() + "\n" + "key:" + record.key
                () + "\n" + "偏移量:" + record.offset() + "\n" + "消息内容:" + record.value());
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
