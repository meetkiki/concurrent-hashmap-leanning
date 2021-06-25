package com.meetkiki.conrrent.mqs;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

public class SimpleProducer {

    public static void main(String[] args) throws Exception {
        sendSync();
    }

    public static void sendSync() throws Exception {
        // 指定生产组名为my-producer
        DefaultMQProducer producer = new DefaultMQProducer("my-producer");
        // 配置namesrv地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setSendMsgTimeout(60000);
        // 启动Producer
        producer.start();

        String topic = "myTopic001";
        // 创建消息对象的集合，用于批量发送
        List<Message> msgs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Message msg = new Message(topic, "Tag" + (i % 2 == 0 ? "A" : "B"), ("hello world1" + i).getBytes());
            msgs.add(msg);
        }
        // 批量发送的api的也是send()，只是他的重载方法支持List<Message>，同样是同步发送。
        SendResult result = producer.send(msgs);
        System.out.println("发送消息成功！result is : " + result);
        // 关闭Producer
        producer.shutdown();
        System.out.println("生产者 shutdown！");
    }
}