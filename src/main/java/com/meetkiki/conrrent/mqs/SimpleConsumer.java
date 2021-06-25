package com.meetkiki.conrrent.mqs;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

public class SimpleConsumer {
    public static void pushConsume(final String instanceName, final String group, final String topic, final String tag) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe(topic, tag);
        consumer.setConsumeTimeout(60000);
        consumer.setInstanceName(instanceName);
        consumer.registerMessageListener((MessageListenerOrderly) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                System.out.println("[" + instanceName + "," + group + "," + topic + "," + tag + "] consume: " + new String(msg.getBody()));
            }
            return ConsumeOrderlyStatus.SUCCESS;
        });
        consumer.start();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t2 = new Thread(() -> {
            try {
                SimpleConsumer.pushConsume("A", "GroupA", "myTopic001", "TagA");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t2.start();

        Thread t3 = new Thread(() -> {
            try {
                SimpleConsumer.pushConsume("B", "GroupA", "myTopic001", "TagB");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t3.start();
        t2.join();
        t3.join();
    }
}